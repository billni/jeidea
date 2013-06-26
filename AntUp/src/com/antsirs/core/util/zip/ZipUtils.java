package com.antsirs.core.util.zip;

import java.io.*;
import java.util.zip.*;

import org.apache.commons.codec.binary.Base64;

public class ZipUtils {

	public static void zipStream() throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(); // 代表内存中的一个输出流对象，大小访问size()方法

		ZipOutputStream zos = new ZipOutputStream(bos); // 建立了有zip能力的输出流

		// BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(zos,
		// "UTF-8"));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(zos,
				"UTF-8"));

		zos.putNextEntry(new ZipEntry("aaa.txt"));
		bw.write("aaa的第1行\r\n");
		bw.write("aaa的第2行\r\n");
		bw.flush();
		zos.closeEntry();
		zos.close();
		bw.close();

		System.out.println("内存流的size为：" + bos.size());
	}

	/**
	 * 字符串的压缩
	 * 
	 * @param str
	 *            待压缩的字符串
	 * @return 返回压缩后的字符串
	 * @throws IOException
	 */
	public static String compress(String str)
			throws IOException {
	    if (str == null || str.length() == 0) {   
	        return str;   
	      }   
	       ByteArrayOutputStream out = new ByteArrayOutputStream();   
	      GZIPOutputStream gzip = new GZIPOutputStream(out);   
	       gzip.write(str.getBytes());   
	       gzip.close();   
	      return out.toString("ISO-8859-1");
	}

	/**
	 * 字符串的解压
	 * 
	 * @param str
	 *            对字符串解压
	 * @return 返回解压缩后的字符串
	 * @throws IOException
	 */
	public static String unCompress(String str, String charsetName) throws IOException {		
	    if (str == null || str.length() == 0) {   
	        return str;   
	    }   
	     ByteArrayOutputStream out = new ByteArrayOutputStream();   
	     ByteArrayInputStream in = new ByteArrayInputStream(str   
	          .getBytes("ISO-8859-1"));   
	      GZIPInputStream gunzip = new GZIPInputStream(in);   
	      byte[] buffer = new byte[256];   
	      int n;   
	     while ((n = gunzip.read(buffer))>= 0) {   
	      out.write(buffer, 0, n);   
	      }   
	      // toString()使用平台默认编码，也可以显式的指定如toString(&quot;GBK&quot;)   
	      return out.toString(charsetName);
	}

	/**
	 * 16进制数字字符集
	 */
	private static String hexString = "0123456789ABCDEF";

	/**
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 */
	public static String encode16(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/**
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 */
	public static String decode16(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String encode64(String str){
		Base64 base64 = new Base64();
		try {
			str = base64.encodeToString(str.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
		}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String decode64(String str) {
		String ret = "";
		try {
			ret = new String(Base64.decodeBase64(str), "UTF-8");
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
		}
		return ret;
	}
}
