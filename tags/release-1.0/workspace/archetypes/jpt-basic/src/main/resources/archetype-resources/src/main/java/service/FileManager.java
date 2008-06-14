package ${package}.service;

import java.io.File;
import java.util.Map;

public interface FileManager extends Manager {

	public String getFilename(String path, Map parameters);

	public File getFile(String path, Map parameters);

	public void saveFile(File[] files, String[] fileUrls, Map entity);

}
