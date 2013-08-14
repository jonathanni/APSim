package com.prodp.apsim.lang;

import java.util.ArrayList;

public class InfoSection extends Section {
	public InfoSection(java.lang.String name) {
		super(name);
	}
	
	private java.lang.String scriptName, scriptVersion, apSimVersion, description;
	private ArrayList<java.lang.String> tags = new ArrayList<java.lang.String>();
	
	public java.lang.String getScriptName() {
		return scriptName;
	}

	public void setScriptName(java.lang.String scriptName) {
		this.scriptName = scriptName;
	}

	public java.lang.String getScriptVersion() {
		return scriptVersion;
	}

	public void setScriptVersion(java.lang.String scriptVersion) {
		this.scriptVersion = scriptVersion;
	}

	public java.lang.String getApSimVersion() {
		return apSimVersion;
	}

	public void setApSimVersion(java.lang.String apSimVersion) {
		this.apSimVersion = apSimVersion;
	}

	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public ArrayList<java.lang.String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<java.lang.String> tags) {
		this.tags = tags;
	}
}
