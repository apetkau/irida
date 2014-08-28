package ca.corefacility.bioinformatics.irida.service.analysis.execution.galaxy;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ca.corefacility.bioinformatics.irida.exceptions.EntityNotFoundException;
import ca.corefacility.bioinformatics.irida.exceptions.ExecutionManagerException;
import ca.corefacility.bioinformatics.irida.exceptions.WorkflowException;
import ca.corefacility.bioinformatics.irida.exceptions.galaxy.WorkflowChecksumInvalidException;
import ca.corefacility.bioinformatics.irida.model.enums.AnalysisState;
import ca.corefacility.bioinformatics.irida.model.workflow.WorkflowStatus;
import ca.corefacility.bioinformatics.irida.model.workflow.analysis.Analysis;
import ca.corefacility.bioinformatics.irida.model.workflow.galaxy.PreparedWorkflowGalaxy;
import ca.corefacility.bioinformatics.irida.model.workflow.galaxy.RemoteWorkflowGalaxy;
import ca.corefacility.bioinformatics.irida.model.workflow.galaxy.WorkflowInputsGalaxy;
import ca.corefacility.bioinformatics.irida.model.workflow.submission.AnalysisSubmission;
import ca.corefacility.bioinformatics.irida.model.workflow.submission.galaxy.AnalysisSubmissionGalaxy;
import ca.corefacility.bioinformatics.irida.pipeline.upload.galaxy.GalaxyHistoriesService;
import ca.corefacility.bioinformatics.irida.pipeline.upload.galaxy.GalaxyWorkflowService;
import ca.corefacility.bioinformatics.irida.service.AnalysisService;
import ca.corefacility.bioinformatics.irida.service.AnalysisSubmissionService;
import ca.corefacility.bioinformatics.irida.service.analysis.execution.AnalysisExecutionService;
import ca.corefacility.bioinformatics.irida.service.analysis.workspace.galaxy.AnalysisWorkspaceServiceGalaxy;

/**
 * Service for performing analyses within a Galaxy execution manager.
 * @author Aaron Petkau <aaron.petkau@phac-aspc.gc.ca>
 *
 * @param <A> The type of Analysis expected to be performed.
 * @param <P> The type of AnalysisWorkspaceServiceGalaxy to use.
 * @param <R> The type of RemoteWorkflow to use.
 * @param <S> The type of AnalysisSubmissionGalaxy to perform.
 */
public abstract class AnalysisExecutionServiceGalaxy
	<A extends Analysis, W extends AnalysisWorkspaceServiceGalaxy<R,S,A>, 
	R extends RemoteWorkflowGalaxy, S extends AnalysisSubmissionGalaxy<R>>
	implements AnalysisExecutionService<A,S> {
	
	private static final Logger logger = LoggerFactory.getLogger(AnalysisExecutionServiceGalaxy.class);
	
	@Autowired
	private AnalysisSubmissionService analysisSubmissionService;
	
	@Autowired
	private AnalysisService analysisService;
	
	private W workspaceService;
	
	protected GalaxyHistoriesService galaxyHistoriesService;
	protected GalaxyWorkflowService galaxyWorkflowService;
	
	/**
	 * Builds a new AnalysisExecutionServiceGalaxy with the given information.
	 * @param analysisSubmissionService  A service for analysis submissions.
	 * @param analysisService  A service for analysis results.
	 * @param galaxyWorkflowService  A service for Galaxy workflows.
	 * @param galaxyHistoriesService  A service for Galaxy histories.
	 * @param workspaceService  A service for a workflow workspace.
	 */
	public AnalysisExecutionServiceGalaxy(AnalysisSubmissionService analysisSubmissionService,
			AnalysisService analysisService, GalaxyWorkflowService galaxyWorkflowService,
			GalaxyHistoriesService galaxyHistoriesService, W workspaceService) {
		this.analysisSubmissionService = analysisSubmissionService;
		this.analysisService = analysisService;
		this.galaxyWorkflowService = galaxyWorkflowService;
		this.galaxyHistoriesService = galaxyHistoriesService;
		this.workspaceService = workspaceService;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public S prepareSubmission(S analysisSubmission)
			throws ExecutionManagerException {
		checkNotNull(analysisSubmission, "analysisSubmission is null");
		checkArgument(null == analysisSubmission.getRemoteAnalysisId(),
				"remote analyis id should be null");
		checkArgument(null == analysisSubmission.getAnalysisState(),
				"analysis state should be null");
		
		String analysisName = analysisSubmission.getClass().getSimpleName();
		RemoteWorkflowGalaxy remoteWorkflow = analysisSubmission.getRemoteWorkflow();
		logger.debug("Preparing submission for " + analysisName + ": " + remoteWorkflow);
		
		logger.trace("Validating " + analysisName + ": " + remoteWorkflow);
		validateWorkflow(analysisSubmission.getRemoteWorkflow());
		
		String analysisId = workspaceService.prepareAnalysisWorkspace(analysisSubmission);
		logger.trace("Created id for " + analysisName + " id=" + analysisId + 
				", workflow=" + remoteWorkflow);
		
		analysisSubmission.setRemoteAnalysisId(analysisId);
		analysisSubmission.setAnalysisState(AnalysisState.SUBMITTED);
		logger.trace("Changed state of analysis " + analysisName + ", id=" + analysisId + " to " + AnalysisState.SUBMITTED);
		
		return (S)analysisSubmissionService.create(analysisSubmission);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public S executeAnalysis(S analysisSubmission)
					throws ExecutionManagerException {
		checkNotNull(analysisSubmission, "analysisSubmission is null");
		checkNotNull(analysisSubmission.getRemoteAnalysisId(), "remote analyis id is null");
		checkArgument(AnalysisState.SUBMITTED.equals(analysisSubmission.getAnalysisState()), 
				" analysis should be submitted");
		
		String analysisName = analysisSubmission.getClass().getSimpleName();
		RemoteWorkflowGalaxy remoteWorkflow = analysisSubmission.getRemoteWorkflow();
		
		logger.debug("Running submission for " + analysisName + ": " + remoteWorkflow);
		analysisSubmissionService.setStateForAnalysisSubmission(analysisSubmission.getRemoteAnalysisId(),
				AnalysisState.RUNNING);
		
		logger.trace("Preparing files for " + analysisName + ": " + remoteWorkflow);
		PreparedWorkflowGalaxy preparedWorkflow = workspaceService.prepareAnalysisFiles(analysisSubmission);
		WorkflowInputsGalaxy input = preparedWorkflow.getWorkflowInputs();
		
		logger.trace("Executing " + analysisName + ": " + remoteWorkflow);
		galaxyWorkflowService.runWorkflow(input);
		analysisSubmission.setRemoteAnalysisId(preparedWorkflow.getRemoteAnalysisId());
		
		logger.trace("Saving submission " +  analysisName + ": " + remoteWorkflow);
		
		return analysisSubmission;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public A transferAnalysisResults(S submittedAnalysis)
			throws ExecutionManagerException, IOException {
		checkNotNull(submittedAnalysis, "submittedAnalysis is null");
		checkNotNull(submittedAnalysis.getRemoteAnalysisId(), "remoteAnalysisId is null");
		checkArgument(AnalysisState.RUNNING.equals(submittedAnalysis.getAnalysisState()),
				" analysis should be running");
		verifyAnalysisSubmissionExists(submittedAnalysis);
		
		String analysisName = submittedAnalysis.getClass().getSimpleName();
		
		logger.debug("Getting results for " + analysisName + ": " + submittedAnalysis.getRemoteAnalysisId());
		A analysisResults = workspaceService.getAnalysisResults(submittedAnalysis);
		
		analysisSubmissionService.setStateForAnalysisSubmission(submittedAnalysis.getRemoteAnalysisId(),
				AnalysisState.COMPLETED);
		
		logger.trace("Saving results " +  analysisName + ": " + submittedAnalysis.getRemoteAnalysisId());
		return (A)analysisService.create(analysisResults);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WorkflowStatus getWorkflowStatus(S submittedAnalysis)
			throws ExecutionManagerException {
		checkNotNull(submittedAnalysis, "submittedAnalysis is null");
		checkNotNull(submittedAnalysis.getRemoteAnalysisId(), "remote analysis id is null");
		
		String analysisId = submittedAnalysis.getRemoteAnalysisId();	
		return galaxyHistoriesService.getStatusForHistory(analysisId);
	}
	
	/**
	 * Validates the given workflow.
	 * @param remoteWorkflow  The Galaxy workflow to validate.
	 * @throws WorkflowException  If there was an issue validating the workflow.
	 */
	public void validateWorkflow(RemoteWorkflowGalaxy remoteWorkflow) throws WorkflowException {
		checkNotNull(remoteWorkflow, "remoteWorkflow is null");
		
		if (!galaxyWorkflowService.validateWorkflowByChecksum(
				remoteWorkflow.getWorkflowChecksum(), remoteWorkflow.getWorkflowId())) {
			throw new WorkflowChecksumInvalidException("passed workflow with id=" +
					remoteWorkflow.getWorkflowId() + " does not have correct checksum " + 
					remoteWorkflow.getWorkflowChecksum());
		}
	}

	/**
	 * Verifies if the analysis submission exists.
	 * @param submission  The submission to check.
	 * @throws EntityNotFoundException  If the given analysis submission does not exist in the database.
	 */
	private void verifyAnalysisSubmissionExists(AnalysisSubmission submission) 
			throws EntityNotFoundException {
		if (!analysisSubmissionService.exists(submission.getRemoteAnalysisId())) {
			throw new EntityNotFoundException("Could not find analysis submission for " + 
					submission);
		}
	}
}
