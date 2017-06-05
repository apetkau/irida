package ca.corefacility.bioinformatics.irida.service.impl.snapshot;

import java.nio.file.Path;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import ca.corefacility.bioinformatics.irida.model.RemoteAPI;
import ca.corefacility.bioinformatics.irida.model.sequenceFile.SequenceFile;
import ca.corefacility.bioinformatics.irida.model.sequenceFile.SequenceFileSnapshot;
import ca.corefacility.bioinformatics.irida.repositories.RemoteAPIRepository;
import ca.corefacility.bioinformatics.irida.repositories.remote.SequenceFileRemoteRepository;
import ca.corefacility.bioinformatics.irida.repositories.sequencefile.SequenceFileSnapshotRepository;
import ca.corefacility.bioinformatics.irida.service.CRUDService;
import ca.corefacility.bioinformatics.irida.service.impl.CRUDServiceImpl;
import ca.corefacility.bioinformatics.irida.service.snapshot.SequenceFileSnapshotService;

/**
 * {@link CRUDService} implementation of {@link SequenceFileSnapshotService}
 */
@Service
public class SequenceFileSnapshotServiceImpl extends CRUDServiceImpl<Long, SequenceFileSnapshot> implements
		SequenceFileSnapshotService {

	SequenceFileRemoteRepository remoteRepository;
	RemoteAPIRepository remoteApiRepo;

	@Autowired
	public SequenceFileSnapshotServiceImpl(SequenceFileSnapshotRepository repository,
			SequenceFileRemoteRepository remoteRepository, RemoteAPIRepository remoteApiRepo, Validator validator) {
		super(repository, validator, SequenceFileSnapshot.class);
		this.remoteRepository = remoteRepository;
		this.remoteApiRepo = remoteApiRepo;
	}

	/**
	 * {@inheritDoc}
	 */
	@PreAuthorize("permitAll")
	public SequenceFileSnapshot mirrorFile(SequenceFile file) {

		SequenceFileSnapshot mirror = new SequenceFileSnapshot(file);

		return create(mirror);
	}

	/**
	 * {@inheritDoc}
	 */
	@PreAuthorize("permitAll")
	@Override
	public SequenceFileSnapshot mirrorFileContent(SequenceFileSnapshot snapshot) {
		RemoteAPI remoteAPIForUrl = remoteApiRepo.getRemoteAPIForUrl(snapshot.getRemoteURI());

		Path downloadRemoteSequenceFile = remoteRepository.downloadRemoteSequenceFile(snapshot.getRemoteURI(),
				remoteAPIForUrl);

		snapshot.setFile(downloadRemoteSequenceFile);
		return update(snapshot);
	}

}