package com.antsirs.core.util.zip;

import java.io.*;
import java.util.zip.*;

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

}
