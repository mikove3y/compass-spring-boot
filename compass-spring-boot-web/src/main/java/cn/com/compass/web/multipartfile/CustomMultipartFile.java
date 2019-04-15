/**
 * 
 */
package cn.com.compass.web.multipartfile;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo 自定义MultipartFile
 * @date 2018年10月15日 下午1:10:27
 * 
 */
public class CustomMultipartFile implements MultipartFile {
	
	private final byte[] fileContent;

	private String fileName;

	private String contentType;

	private File file;

	private String destPath = System.getProperty("java.io.tmpdir");

	private FileOutputStream fileOutputStream;

	public CustomMultipartFile(byte[] fileData, String name) {
	    this.fileContent = fileData;
	    this.fileName = name;
	    file = new File(destPath + fileName);

	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
	    fileOutputStream = new FileOutputStream(dest);
	    fileOutputStream.write(fileContent);
	    clearOutStreams();
	}

	public void clearOutStreams() throws IOException {
	if (null != fileOutputStream) {
	        fileOutputStream.flush();
	        fileOutputStream.close();
	        file.deleteOnExit();
	    }
	}

	@Override
	public byte[] getBytes() throws IOException {
	    return fileContent;
	}

	@Override
	public InputStream getInputStream() throws IOException {
	    return new ByteArrayInputStream(fileContent);
	}

	@Override
	public String getName() {
		return fileName;
	}

	@Override
	public String getOriginalFilename() {
		return null;
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public long getSize() {
		return fileContent!=null?fileContent.length:0;
	}

}
