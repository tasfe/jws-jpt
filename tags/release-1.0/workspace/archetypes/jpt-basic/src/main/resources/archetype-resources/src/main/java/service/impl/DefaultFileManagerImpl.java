package ${package}.service.impl;

import java.io.File;
import java.util.Map;

import ${package}.service.FileManager;
import ${package}.service.ModelStatics;
import ${package}.util.StringUtil;

public class DefaultFileManagerImpl<T> extends BasicManagerImpl<T> implements
		FileManager, ModelStatics {

	public File getFile(String path, Map parameters) {
		return new File(path);
	}

	public String getFilename(String path, Map parameters) {
		return StringUtil.getString(parameters, "fn");
	}

	public void saveFile(File[] files, String[] fileUrls, Map entity) {
	}

}
