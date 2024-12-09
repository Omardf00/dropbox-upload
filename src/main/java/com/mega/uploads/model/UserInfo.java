package com.mega.uploads.model;

public class UserInfo {
	
	private String url;
	
	private String properties;
	
	private String driver;
	
	private String token;
	
	private String dropBoxPath;
	
	private String localPath;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDropBoxPath() {
		return dropBoxPath;
	}

	public void setDropBoxPath(String dropBoxPath) {
		this.dropBoxPath = dropBoxPath;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
	
}
