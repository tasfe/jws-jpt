package ${package}.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import ${package}.service.FileManager;
import ${package}.util.FileUtil;

public class UploadAction<T extends FileManager> extends BaseAction<T> {

	private static final long serialVersionUID = 4564725003016170484L;

	private String rootPath;

	private File[] files;

	private String[] contentTypes;

	private String[] fileNames;

	private Map entity = new HashMap();

	public void setFile(File[] files) {
		this.files = files;
	}

	public void setFileContentType(String[] contentTypes) {
		this.contentTypes = contentTypes;
	}

	public void setFileFileName(String[] fileNames) {
		this.fileNames = fileNames;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public File[] getFiles() {
		return files;
	}

	public void setFiles(File[] files) {
		this.files = files;
	}

	public String[] getContentTypes() {
		return contentTypes;
	}

	public void setContentTypes(String[] contentTypes) {
		this.contentTypes = contentTypes;
	}

	public String[] getFileNames() {
		return fileNames;
	}

	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}

	public Map getEntity() {
		return entity;
	}

	public void setEntity(Map entity) {
		this.entity = entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		String path = FileUtil.joinUrl(rootPath, String.valueOf(System
				.currentTimeMillis()));
		String realPath = getRealPath(path);
		String[] fileUrls = null;
		if (files != null && files.length > 0) {
			fileUrls = new String[files.length];
			FileUtil.mkdirs(realPath, true);
			for (int i = 0; i < files.length; i++) {
				FileUtil.saveTo(new FileInputStream(files[i]), realPath,
						fileNames[i]);
				fileUrls[i] = FileUtil.joinUrl(path, fileNames[i]);
			}
			manager.saveFile(files, fileUrls, entity);
		}
		root.put("fileUrls", fileUrls);
		root.put("entity", entity);
		return SUCCESS;
	}

}
