package it.csi.gestionepazienti.gestionepazientiweb.mapper.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class MapperUtil {
	
	private MapperUtil() {
		// private empty constructor
	}
	
	
	
	public static List<Long> toListLong(String s, String regexSeparator, List<Long> defaultResult) {
		if (StringUtils.isBlank(s)) {
			return defaultResult;
		}
		String[] split = s.split(regexSeparator);
		List<Long> result = new ArrayList<>();
		for (String sp : split) {
			Long l = toLong(sp, null);
			if (l != null) {
				result.add(l);
			}
		}
		if(result.isEmpty()) {
			return defaultResult;
		}
		return result;
	}
	
	public static String toLike(String str) {
		return toLike(str, null);
	}
	public static String toLike(String str, String def) {
		if(StringUtils.isBlank(str)) {
			return def;
		}
		return "%"+str+"%";
	}

	public static Long toLong(String str) {
		return toLong(str, null);
	}
	
	public static Long toLong(String str, Long def) {
		if(StringUtils.isBlank(str)) {
			return def;
		}
		try {
			return Long.valueOf(str);
		} catch (NumberFormatException nfe) {
			return def;
		}
	}
	
	public static Integer toInteger(String str) {
		return toInteger(str, null);
	}
	
	public static Integer toInteger(String str, Integer def) {
		if(StringUtils.isBlank(str)) {
			return def;
		}
		try {
			return Integer.valueOf(str);
		} catch (NumberFormatException nfe) {
			return def;
		}
	}
	
	public static boolean toBoolean(String s) {
		return "true".equalsIgnoreCase(s) || "s".equalsIgnoreCase(s) || "yes".equalsIgnoreCase(s);
	}
	
	public static String toFlag(String s, String trueString, String falseString, String defaultString) {
		if("true".equalsIgnoreCase(s) || "s".equalsIgnoreCase(s) || "yes".equalsIgnoreCase(s)) {
			return trueString;
		} else if("false".equalsIgnoreCase(s) || "n".equalsIgnoreCase(s) || "no".equalsIgnoreCase(s)) {
			return falseString;
		}
		
		return defaultString;
	}

}
