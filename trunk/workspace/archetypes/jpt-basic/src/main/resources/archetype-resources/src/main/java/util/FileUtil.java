package ${package}.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtil {

	private static final String TMP_DIR_FLAG_FILE = "__removable__.tmp";

	public static final int BUFFER_SIZE = 4096;

	private static final Log logger = LogFactory.getLog(FileUtil.class);

	public static int copy(InputStream in, OutputStream out) throws IOException {
		try {
			int byteCount = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		} finally {
			try {
				in.close();
			} catch (IOException ex) {
				logger.warn("Could not close InputStream", ex);
			}
			try {
				out.close();
			} catch (IOException ex) {
				logger.warn("Could not close OutputStream", ex);
			}
		}
	}

	public static void saveTo(byte[] data, String filename) throws IOException {
		if (data == null || data.length == 0) {
			return;
		}
		mkdirs(filename.substring(0, filename.lastIndexOf(File.separator)),
				false);
		OutputStream out = new FileOutputStream(filename);
		try {
			out.write(data);
			out.flush();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				logger.warn("Could not close OutputStream", e);
			}
		}
	}

	public static void saveTo(InputStream in, String path, String name)
			throws IOException {
		copy(in, new FileOutputStream(joinPath(path, name)));
	}

	public static void saveTo(String data, String filename) throws Exception {
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(filename), ResourceUtil.RESOURCE_CHARSET));
		try {
			out.write(data);
			out.flush();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				logger.warn("Could not close BufferedWriter", e);
			}
		}
	}

	public static String saveToUnique(InputStream in, String path, String ext)
			throws IOException {
		String name = null;
		File file = null;

		do {
			name = System.currentTimeMillis() + "." + ext;
			file = new File(joinPath(path, name));
		} while (file.exists());

		if (name != null) {
			saveTo(in, path, name);
		}

		return name;
	}

	public static String joinPath(String path1, String path2) {
		return joinPath(path1, path2, File.separator);
	}

	public static String joinUrl(String path1, String path2) {
		return joinPath(path1, path2, "/");
	}

	public static String joinPath(String path1, String path2, String separator) {
		if (!path1.endsWith(separator)) {
			path1 += separator;
		}
		if (path2.startsWith(separator)) {
			path2 = path2.substring(1);
		}
		return path1 + path2;

	}

	public static void mkdirs(String path, boolean tmp) {
		mkdirs(path);
		File tmpdirFlag = new File(joinPath(path, TMP_DIR_FLAG_FILE));
		if (tmp) {
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(tmpdirFlag);
				fos.write("removable".getBytes());
			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e);
				}
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						logger.warn("Could not close OutputStream", e);
					}
				}
			}
		} else {
			if (tmpdirFlag.exists()) {
				tmpdirFlag.delete();
			}
		}
	}

	public static void mkdirs(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

}
