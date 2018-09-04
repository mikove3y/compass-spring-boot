package cn.com.compass.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo md5加密工具类
 * @date 2018年9月4日 上午9:50:09
 *
 */
public class Md5Util {

	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	private static MessageDigest messagedigest = null;

	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取文件的MD5值
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String encodeFile(File file) throws IOException {
		return encodeFile(file, 0, -1);
	}

	/**
	 * 获取文件的MD5值
	 *
	 * @param file
	 * @param start
	 * @param length
	 * @return
	 * @throws IOException
	 */
	public static String encodeFile(File file, long start, long length) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			FileChannel ch = fis.getChannel();

			long remain = file.length() - start;

			if (length < 0 || length > remain) {
				length = remain;
			}

			MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, start, length);

			messagedigest.update(byteBuffer);
			return bufferToHex(messagedigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}
	}

	/**
	 * 获取字符串的MD5加密的结果
	 *
	 * @param s
	 * @return
	 */
	public static String encodeString(String s) {
		return encodeString(s.getBytes());
	}

	public static String encodeString(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

}
