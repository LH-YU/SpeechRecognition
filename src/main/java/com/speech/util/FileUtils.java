package com.speech.util;

import java.io.File;

import java.util.UUID;

public class FileUtils{
	public static String moveFile(UploadFile uploadFile) {
		if (uploadFile == null)
			return null;

		File file = uploadFile.getFile();
		if (!file.exists()) {
			return null;
		}

		String webRoot = detectWebRootPath();

		String uuid = UUID.randomUUID().toString().replace("-", "");

		StringBuilder newFileName = new StringBuilder(webRoot).append(File.separator).append("attachment")
				.append(File.separator).append(uuid)
				.append(FileUtils.getSuffix(file.getName()));

		File newfile = new File(newFileName.toString());

		if (!newfile.getParentFile().exists()) {
			newfile.getParentFile().mkdirs();
		}

		file.renameTo(newfile);

		return FileUtils.removePrefix(newfile.getAbsolutePath(), webRoot);
	}
	public static String removePrefix(String src, String prefix) {
		if (src != null && src.startsWith(prefix)) {
			return src.substring(prefix.length());
		}
		return src;
	}
	
	private static String detectWebRootPath() {
		try {
			String path = FileUtils.class.getResource("/").toURI().getPath();
			return new File(path).getParentFile().getParentFile().getCanonicalPath();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static String getSuffix(String fileName) {
		if (fileName != null && fileName.contains(".")) {
			return fileName.substring(fileName.lastIndexOf("."));
		}
		return null;
	}
     
    
}
