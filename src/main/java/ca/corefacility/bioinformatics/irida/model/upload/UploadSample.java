package ca.corefacility.bioinformatics.irida.model.upload;

import java.nio.file.Path;
import java.util.List;

/**
 * Represents a sample to upload to a remote site.
 * 
 * @author Aaron Petkau <aaron.petkau@phac-aspc.gc.ca>
 * 
 */
public interface UploadSample {
	/**
	 * Gets the name of the sample represented by this object.
	 * 
	 * @return An UploadObjectName representing the name of this sample.
	 */
	public UploadObjectName getSampleName();

	/**
	 * Sets the name of the sample represented by this object.
	 * 
	 * @param sampleName
	 *            The name of the sample.
	 */
	public void setSampleName(UploadObjectName sampleName);

	/**
	 * Gets the list of files associated with this sample.
	 * 
	 * @return A list of files associated with this sample.
	 */
	public List<Path> getSampleFiles();

	/**
	 * Set the list of files associated with this sample.
	 * 
	 * @param sampleFiles
	 *            The list of files associated with this sample.
	 */
	public void setSampleFiles(List<Path> sampleFiles);
}