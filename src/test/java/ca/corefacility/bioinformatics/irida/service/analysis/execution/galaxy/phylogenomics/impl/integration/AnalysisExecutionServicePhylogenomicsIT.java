package ca.corefacility.bioinformatics.irida.service.analysis.execution.galaxy.phylogenomics.impl.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExcecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import ca.corefacility.bioinformatics.irida.config.IridaApiGalaxyTestConfig;
import ca.corefacility.bioinformatics.irida.config.conditions.WindowsPlatformCondition;
import ca.corefacility.bioinformatics.irida.exceptions.EntityNotFoundException;
import ca.corefacility.bioinformatics.irida.exceptions.ExecutionManagerException;
import ca.corefacility.bioinformatics.irida.exceptions.WorkflowException;
import ca.corefacility.bioinformatics.irida.exceptions.galaxy.WorkflowChecksumInvalidException;
import ca.corefacility.bioinformatics.irida.model.enums.AnalysisState;
import ca.corefacility.bioinformatics.irida.model.workflow.WorkflowStatus;
import ca.corefacility.bioinformatics.irida.model.workflow.analysis.Analysis;
import ca.corefacility.bioinformatics.irida.model.workflow.analysis.AnalysisOutputFile;
import ca.corefacility.bioinformatics.irida.model.workflow.analysis.AnalysisPhylogenomicsPipeline;
import ca.corefacility.bioinformatics.irida.model.workflow.galaxy.phylogenomics.RemoteWorkflowPhylogenomics;
import ca.corefacility.bioinformatics.irida.model.workflow.submission.galaxy.phylogenomics.AnalysisSubmissionPhylogenomics;
import ca.corefacility.bioinformatics.irida.pipeline.upload.galaxy.integration.LocalGalaxy;
import ca.corefacility.bioinformatics.irida.repositories.analysis.submission.AnalysisSubmissionRepository;
import ca.corefacility.bioinformatics.irida.service.AnalysisService;
import ca.corefacility.bioinformatics.irida.service.AnalysisSubmissionService;
import ca.corefacility.bioinformatics.irida.service.DatabaseSetupGalaxyITService;
import ca.corefacility.bioinformatics.irida.service.analysis.execution.galaxy.phylogenomics.impl.AnalysisExecutionServicePhylogenomics;
import ca.corefacility.bioinformatics.irida.service.workflow.galaxy.phylogenomics.impl.RemoteWorkflowServicePhylogenomics;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

/**
 * Tests out the analysis service for the phylogenomic pipeline.
 * @author Aaron Petkau <aaron.petkau@phac-aspc.gc.ca>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { IridaApiGalaxyTestConfig.class})
@ActiveProfiles("test")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DbUnitTestExecutionListener.class, WithSecurityContextTestExcecutionListener.class })
@DatabaseSetup("/ca/corefacility/bioinformatics/irida/repositories/analysis/AnalysisRepositoryIT.xml")
@DatabaseTearDown("/ca/corefacility/bioinformatics/irida/test/integration/TableReset.xml")
public class AnalysisExecutionServicePhylogenomicsIT {
	
	@Autowired
	private DatabaseSetupGalaxyITService analysisExecutionGalaxyITService;
	
	@Autowired
	private LocalGalaxy localGalaxy;
	
	@Autowired
	private AnalysisSubmissionRepository analysisSubmissionRepository;
	
	@Autowired
	private AnalysisSubmissionService analysisSubmissionService;
	
	@Autowired
	private AnalysisService analysisService;
	
	@Autowired
	private AnalysisExecutionServicePhylogenomics analysisExecutionServicePhylogenomics;
	
	@Autowired
	private RemoteWorkflowServicePhylogenomics
		remoteWorkflowServicePhylogenomics;
	
	@Autowired
	private RemoteWorkflowServicePhylogenomics
		remoteWorkflowServicePhylogenomicsInvalidId;
	
	@Autowired
	private RemoteWorkflowServicePhylogenomics
		remoteWorkflowServicePhylogenomicsInvalidChecksum;
		
	private Path sequenceFilePath;
	private Path referenceFilePath;
	
	private Path expectedSnpMatrix;
	private Path expectedSnpTable;
	private Path expectedTree;
		
	/**
	 * Sets up variables for testing.
	 * @throws URISyntaxException
	 * @throws IOException 
	 */
	@Before
	public void setup() throws URISyntaxException, IOException {
		Assume.assumeFalse(WindowsPlatformCondition.isWindows());
		
		Path sequenceFilePathReal = Paths.get(DatabaseSetupGalaxyITService.class.getResource(
				"testData1.fastq").toURI());		
		Path referenceFilePathReal = Paths.get(DatabaseSetupGalaxyITService.class.getResource(
				"testReference.fasta").toURI());
		
		sequenceFilePath = Files.createTempFile("testData1", ".fastq");
		Files.delete(sequenceFilePath);
		Files.copy(sequenceFilePathReal, sequenceFilePath);
		
		referenceFilePath = Files.createTempFile("testReference", ".fasta");
		Files.delete(referenceFilePath);
		Files.copy(referenceFilePathReal, referenceFilePath);
		
		expectedSnpMatrix = localGalaxy.getWorkflowCorePipelineTestMatrix();
		expectedSnpTable = localGalaxy.getWorkflowCorePipelineTestSnpTable();
		expectedTree = localGalaxy.getWorkflowCorePipelineTestTree();
	}
	
	/**
	 * Tests out successfully submitting a workflow for execution.
	 * @throws InterruptedException 
	 * @throws ExecutionManagerException 
	 */
	@Test
	@WithMockUser(username = "aaron", roles = "ADMIN")
	public void testExecuteAnalysisSuccess() throws InterruptedException, ExecutionManagerException {
		RemoteWorkflowPhylogenomics remoteWorkflowUnsaved = 
				remoteWorkflowServicePhylogenomics.getCurrentWorkflow();
		
		AnalysisSubmissionPhylogenomics analysisSubmission = 
				analysisExecutionGalaxyITService.setupSubmissionInDatabase(1L,
						sequenceFilePath, referenceFilePath, remoteWorkflowUnsaved);
		
		analysisSubmission.setAnalysisState(AnalysisState.PREPARING);
		AnalysisSubmissionPhylogenomics analysisSubmitted = 
				analysisExecutionServicePhylogenomics.prepareSubmission(analysisSubmission);
		
		analysisSubmitted.setAnalysisState(AnalysisState.SUBMITTING);
		AnalysisSubmissionPhylogenomics analysisExecuted = 
				analysisExecutionServicePhylogenomics.executeAnalysis(analysisSubmitted);
		assertNotNull("analysisExecuted is null", analysisExecuted);
		assertNotNull("remoteAnalysisId is null", analysisExecuted.getRemoteAnalysisId());

		WorkflowStatus status = 
				analysisExecutionServicePhylogenomics.getWorkflowStatus(analysisExecuted);
		analysisExecutionGalaxyITService.assertValidStatus(status);
		
		AnalysisSubmissionPhylogenomics savedSubmission = analysisSubmissionRepository.getByType(analysisExecuted.getId(),
				AnalysisSubmissionPhylogenomics.class);
		
		assertEquals(analysisExecuted.getRemoteAnalysisId(), savedSubmission.getRemoteAnalysisId());
		assertEquals(analysisExecuted.getRemoteWorkflow(), savedSubmission.getRemoteWorkflow());
		assertEquals(analysisExecuted.getInputFiles(), savedSubmission.getInputFiles());
		assertEquals(analysisExecuted.getReferenceFile(), savedSubmission.getReferenceFile());
	}
	
	/**
	 * Tests out attempting to execute an analysis with an invalid initial state..
	 * @throws IllegalArgumentException 
	 */
	@Test(expected=IllegalArgumentException.class)
	@WithMockUser(username = "aaron", roles = "ADMIN")
	public void testExecuteAnalysisFailState() throws ExecutionManagerException {
		RemoteWorkflowPhylogenomics remoteWorkflowUnsaved = 
				remoteWorkflowServicePhylogenomics.getCurrentWorkflow();
		
		AnalysisSubmissionPhylogenomics analysisSubmission 
			= analysisExecutionGalaxyITService.setupSubmissionInDatabase(1L,
					sequenceFilePath, referenceFilePath, remoteWorkflowUnsaved);
		
		analysisSubmission.setAnalysisState(AnalysisState.PREPARING);
		AnalysisSubmissionPhylogenomics analysisSubmitted = 
				analysisExecutionServicePhylogenomics.prepareSubmission(analysisSubmission);
		
		analysisSubmitted.setAnalysisState(AnalysisState.NEW);
		analysisExecutionServicePhylogenomics.executeAnalysis(analysisSubmitted);
	}
	
	/**
	 * Tests out successfully preparing a workflow submission.
	 * @throws InterruptedException 
	 * @throws ExecutionManagerException 
	 */
	@Test
	@WithMockUser(username = "aaron", roles = "ADMIN")
	public void testPrepareSubmissionSuccess() throws InterruptedException, ExecutionManagerException {
		RemoteWorkflowPhylogenomics remoteWorkflowUnsaved = 
				remoteWorkflowServicePhylogenomics.getCurrentWorkflow();
		
		AnalysisSubmissionPhylogenomics analysisSubmission = 
				analysisExecutionGalaxyITService.setupSubmissionInDatabase(1L,
						sequenceFilePath, referenceFilePath, remoteWorkflowUnsaved);
		
		analysisSubmission.setAnalysisState(AnalysisState.PREPARING);
		AnalysisSubmissionPhylogenomics analysisSubmitted = 
				analysisExecutionServicePhylogenomics.prepareSubmission(analysisSubmission);
		assertNotNull("analysisSubmitted is null", analysisSubmitted);
		assertNotNull("remoteAnalysisId is null", analysisSubmitted.getRemoteAnalysisId());
	}

	/**
	 * Tests out attempting to prepare a workflow with an invalid id for execution.
	 * @throws ExecutionManagerException 
	 */
	@Test(expected=WorkflowException.class)
	@WithMockUser(username = "aaron", roles = "ADMIN")
	public void testPrepareSubmissionFailInvalidWorkflow() throws ExecutionManagerException {
		RemoteWorkflowPhylogenomics remoteWorkflowUnsaved = 
				remoteWorkflowServicePhylogenomicsInvalidId.getCurrentWorkflow();
		
		AnalysisSubmissionPhylogenomics analysisSubmission = 
				analysisExecutionGalaxyITService.setupSubmissionInDatabase(1L,
						sequenceFilePath, referenceFilePath, remoteWorkflowUnsaved);
		
		analysisSubmission.setAnalysisState(AnalysisState.PREPARING);
		analysisExecutionServicePhylogenomics.prepareSubmission(analysisSubmission);
	}
	
	/**
	 * Tests out attempting to submit a workflow with an invalid checksum for execution.
	 * @throws ExecutionManagerException 
	 */
	@Test(expected=WorkflowChecksumInvalidException.class)
	@WithMockUser(username = "aaron", roles = "ADMIN")
	public void testPrepareSubmissionFailInvalidChecksum() throws ExecutionManagerException {
		RemoteWorkflowPhylogenomics remoteWorkflowUnsaved = 
				remoteWorkflowServicePhylogenomicsInvalidChecksum.getCurrentWorkflow();
		
		AnalysisSubmissionPhylogenomics analysisSubmission = 
				analysisExecutionGalaxyITService.setupSubmissionInDatabase(1L,
						sequenceFilePath, referenceFilePath, remoteWorkflowUnsaved);
		
		analysisSubmission.setAnalysisState(AnalysisState.PREPARING);
		analysisExecutionServicePhylogenomics.prepareSubmission(analysisSubmission);
	}
	
	/**
	 * Tests out getting analysis results successfully.
	 * @throws Exception 
	 */
	@Test
	@WithMockUser(username = "aaron", roles = "ADMIN")
	public void testGetAnalysisResultsSuccess() throws Exception {	
		RemoteWorkflowPhylogenomics remoteWorkflowUnsaved = 
				remoteWorkflowServicePhylogenomics.getCurrentWorkflow();
		
		AnalysisSubmissionPhylogenomics analysisSubmission = 
				analysisExecutionGalaxyITService.setupSubmissionInDatabase(1L,
						sequenceFilePath, referenceFilePath, remoteWorkflowUnsaved);
		
		analysisSubmission.setAnalysisState(AnalysisState.PREPARING);
		AnalysisSubmissionPhylogenomics analysisSubmitted = analysisExecutionServicePhylogenomics
				.prepareSubmission(analysisSubmission);
		
		analysisSubmitted.setAnalysisState(AnalysisState.SUBMITTING);
		AnalysisSubmissionPhylogenomics analysisExecuted = analysisExecutionServicePhylogenomics
				.executeAnalysis(analysisSubmitted);

		analysisExecutionGalaxyITService.waitUntilSubmissionComplete(analysisExecuted);

		analysisExecuted.setAnalysisState(AnalysisState.FINISHED_RUNNING);
		AnalysisPhylogenomicsPipeline analysisResults = analysisExecutionServicePhylogenomics
				.transferAnalysisResults(analysisExecuted);

		String analysisId = analysisExecuted.getRemoteAnalysisId();
		assertEquals("id should be set properly for analysis",
				analysisId,
				analysisResults.getExecutionManagerAnalysisId());

		assertEquals("inputFiles should be the same for submission and results",
				analysisExecuted.getInputFiles(), analysisResults.getInputSequenceFiles());
		
		assertEquals(3, analysisResults.getAnalysisOutputFiles().size());
		AnalysisOutputFile phylogeneticTree = analysisResults
				.getPhylogeneticTree();
		AnalysisOutputFile snpMatrix = analysisResults.getSnpMatrix();
		AnalysisOutputFile snpTable = analysisResults.getSnpTable();

		assertTrue("phylogenetic trees should be equal",
				com.google.common.io.Files.equal(expectedTree.toFile(),
						phylogeneticTree.getFile().toFile()));
		assertEquals(expectedTree.toFile().getName(), phylogeneticTree.getFile().toFile().getName());
		
		assertTrue("snp matrices should be correct",
				com.google.common.io.Files.equal(expectedSnpMatrix.toFile(),
						snpMatrix.getFile().toFile()));
		assertEquals(expectedSnpMatrix.toFile().getName(), snpMatrix.getFile().toFile().getName());
		
		assertTrue("snpTable should be correct",
				com.google.common.io.Files.equal(expectedSnpTable.toFile(),
						snpTable.getFile().toFile()));
		assertEquals(expectedSnpTable.toFile().getName(), snpTable.getFile().toFile().getName());

		AnalysisSubmissionPhylogenomics finalSubmission = analysisSubmissionRepository
				.getByType(analysisExecuted.getId(),
						AnalysisSubmissionPhylogenomics.class);
		Analysis analysis = finalSubmission.getAnalysis();
		assertNotNull(analysis);

		Analysis savedAnalysisFromDatabase = analysisService
				.read(analysisResults.getId());
		assertTrue(savedAnalysisFromDatabase instanceof AnalysisPhylogenomicsPipeline);
		AnalysisPhylogenomicsPipeline savedPhylogenomics = (AnalysisPhylogenomicsPipeline) savedAnalysisFromDatabase;

		assertEquals(
				"Analysis from submission and from database should be the same",
				savedAnalysisFromDatabase.getId(), analysis.getId());

		assertEquals(analysisResults.getId(), savedPhylogenomics.getId());
		assertEquals(analysisResults.getPhylogeneticTree().getFile(), savedPhylogenomics.getPhylogeneticTree().getFile());
		assertEquals(analysisResults.getSnpMatrix().getFile(), savedPhylogenomics.getSnpMatrix().getFile());
		assertEquals(analysisResults.getSnpTable().getFile(), savedPhylogenomics.getSnpTable().getFile());
		assertEquals(analysisResults.getInputSequenceFiles(), savedPhylogenomics.getInputSequenceFiles());
	}
	
	/**
	 * Tests out failing to get analysis results due to analysis not being submitted.
	 * @throws Exception 
	 */
	@Test(expected=EntityNotFoundException.class)
	@WithMockUser(username = "aaron", roles = "ADMIN")
	public void testGetAnalysisResultsFail() throws Exception {	
		RemoteWorkflowPhylogenomics remoteWorkflowUnsaved = 
				remoteWorkflowServicePhylogenomics.getCurrentWorkflow();
		
		AnalysisSubmissionPhylogenomics analysisSubmission = 
				analysisExecutionGalaxyITService.setupSubmissionInDatabase(1L,
						sequenceFilePath, referenceFilePath, remoteWorkflowUnsaved);
		
		analysisSubmission.setAnalysisState(AnalysisState.PREPARING);
		AnalysisSubmissionPhylogenomics analysisSubmitted = analysisExecutionServicePhylogenomics
				.prepareSubmission(analysisSubmission);
		
		analysisSubmitted.setAnalysisState(AnalysisState.SUBMITTING);
		AnalysisSubmissionPhylogenomics analysisExecuted = analysisExecutionServicePhylogenomics
				.executeAnalysis(analysisSubmitted);

		analysisExecutionGalaxyITService.waitUntilSubmissionComplete(analysisExecuted);

		analysisExecuted.setId(555l);
		analysisExecuted.setAnalysisState(AnalysisState.FINISHED_RUNNING);
		analysisExecutionServicePhylogenomics
				.transferAnalysisResults(analysisExecuted);
	}
}