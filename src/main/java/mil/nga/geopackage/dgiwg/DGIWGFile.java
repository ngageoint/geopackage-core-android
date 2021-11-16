package mil.nga.geopackage.dgiwg;

import java.io.File;

/**
 * DGIWG (Defence Geospatial Information Working Group) GeoPackage File
 * 
 * @author osbornb
 * @since 6.1.2
 */
public class DGIWGFile {

	/**
	 * GeoPackage File
	 */
	private File file;

	/**
	 * DGIWG File Name
	 */
	private DGIWGFileName fileName;

	/**
	 * Constructor
	 * 
	 * @param file
	 *            GeoPackage file
	 * @param fileName
	 *            DGIWG File Name
	 */
	public DGIWGFile(File file, DGIWGFileName fileName) {
		this.file = file;
		this.fileName = fileName;
	}

	/**
	 * Get the GeoPackage file
	 * 
	 * @return GeoPackage file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Get the DGIWG file name
	 * 
	 * @return DGIWG file name
	 */
	public DGIWGFileName getFileName() {
		return fileName;
	}

}
