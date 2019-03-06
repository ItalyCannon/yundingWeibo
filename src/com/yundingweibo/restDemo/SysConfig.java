package com.yundingweibo.restDemo;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class SysConfig {
	private Properties props = null;// config.properties
	private static volatile SysConfig conf;

	private SysConfig() {
		props = new Properties();
		loadConfigProps();
	}

	public static SysConfig getInstance() {
		if (conf == null) {
			synchronized (SysConfig.class) {
				if (conf == null) {
					conf = new SysConfig();
				}
			}
		}
		return conf;
	}

	public void loadConfigProps() {
		InputStream is = null;
		try {
			is = getClass().getResourceAsStream(
					"/WEB-INF/classes/config.properties");
			if (is == null) {
				is = getClass().getClassLoader().getResourceAsStream("/config.properties");
			}
			InputStreamReader reader = null;
			if (is != null) {
				reader = new InputStreamReader(is, StandardCharsets.UTF_8);
			}
			if (reader == null) {
				throw new ExceptionInInitializerError("验证码配置文件读取错误");
			}
			props.load(reader);
			for (String key : props.stringPropertyNames()) {
				props.setProperty(key, props.getProperty(key));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
					is = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	public String getProperty(String key) {
		String tmp = props.getProperty(key);
		if (StringUtils.isNotEmpty(tmp)) {
			return tmp.trim();
		}
		return tmp;
	}

	public String getProperty(String key, String defaultValue) {
		String tmp = props.getProperty(key, defaultValue);
		if (StringUtils.isNotEmpty(tmp)) {
			return tmp.trim();
		}
		return tmp;
	}

	public int getPropertyInt(String key) {
		String tmp = props.getProperty(key);
		if (StringUtils.isNotEmpty(tmp)) {
			return Integer.parseInt(tmp.trim());
		}
		return 0;

	}

	public int getPropertyInt(String key, int defaultValue) {
		String tmp = props.getProperty(key);
		if (StringUtils.isNotEmpty(tmp)) {
			return Integer.parseInt(tmp.trim());
		}
		return defaultValue;
	}

	public long getPropertyLong(String key, long defaultValue) {
		String tmp = props.getProperty(key);
		if (StringUtils.isNotEmpty(tmp)) {
			return Integer.parseInt(tmp.trim());
		}
		return defaultValue;
	}
}
