package com.antsirs.core.util.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

public class ExceptionConvert {
	private static final Logger logger = Logger.getLogger(ExceptionConvert.class.getName());
	
	public static String getErrorInfoFromException(Exception e) {
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return IOUtils.LINE_SEPARATOR + sw.toString() + IOUtils.LINE_SEPARATOR;
		} catch (Exception ex) {
			logger.severe(ex.getMessage());
			return "bad getErrorInfoFromException";
		}
	}
}
