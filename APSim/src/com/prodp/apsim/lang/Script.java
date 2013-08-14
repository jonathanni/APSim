package com.prodp.apsim.lang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import com.prodp.apsim.APMain;

/**
 * @author Jonathan
 * @since 8-2-2013
 * @version 0.0
 * 
 *          Container module for APSL scripts. Automatically caches the script
 *          information as soon as the file is loaded.
 * 
 */
public class Script {

	private File scriptFile;
	private java.lang.String rawCode, strippedCode;

	private Section infoSection, dataSection, initSection, mainSection,
			exitSection;

	private int errorCode = 1;

	/**
	 * 
	 * Creates a new Script container object. Reads the file and parses it.
	 * 
	 * @param file
	 * @throws IOException
	 */

	public Script(java.lang.String file) throws IOException {
		setScriptFile(new File(file));
		BufferedReader in = new BufferedReader(new FileReader(getScriptFile()));

		APMain.debug("[E] error [W] warning [I] info");
		APMain.debug("[I] Parsing APSL script "
				+ getScriptFile().getAbsolutePath());

		StringBuilder rawCodeBuffer = new StringBuilder();
		java.lang.String line;

		while ((line = in.readLine()) != null)
			rawCodeBuffer.append(line).append('\n');

		in.close();

		setRawCode(rawCodeBuffer.toString());

		BufferedReader filter = new BufferedReader(new StringReader(
				getRawCode()));

		StringBuffer strippedCodeBuffer = new StringBuffer();
		// This filters out //comments
		while ((line = filter.readLine()) != null)
			if (!line.startsWith("//"))
				strippedCodeBuffer.append(line).append("\n");
		// Filter out multiline comments

		int fromindex, toindex;
		while ((fromindex = strippedCodeBuffer.indexOf("/*")) != -1)
			if ((toindex = strippedCodeBuffer.indexOf("*/")) != -1)
				strippedCodeBuffer.replace(fromindex, toindex + 2, "");
			else {
				APMain.error("[E] " + getScriptFile().getAbsolutePath() + " ("
						+ fromindex + "): No termination of multiline comment");
				return;
			}

		// Filter out \n and \t

		// index is necessary because characters are deleted
		int index = 0;
		while (index < strippedCodeBuffer.length()) {

			if (strippedCodeBuffer.charAt(index) == '\n'
					|| strippedCodeBuffer.charAt(index) == '\t') {
				strippedCodeBuffer.deleteCharAt(index);
				continue;
			}

			index++;
		}

		setStrippedCode(strippedCodeBuffer.toString());

		setInfoSection(new InfoSection("info"));
		setDataSection(new CodeSection("data"));
		setInitSection(new CodeSection("init"));
		setMainSection(new CodeSection("main"));
		setExitSection(new CodeSection("exit"));

		getInfoSection().setPrevious(null).setNext(getDataSection());
		getDataSection().setPrevious(getInfoSection())
				.setNext(getInitSection());
		getInitSection().setPrevious(getDataSection())
				.setNext(getMainSection());
		getMainSection().setPrevious(getInitSection())
				.setNext(getExitSection());
		getExitSection().setPrevious(getMainSection()).setNext(null);

		buildNodes();

	}

	private void buildNodes() {

		if (getStrippedCode().indexOf("$@apsc") != 0) {
			APMain.error("[E] " + getScriptFile().getAbsolutePath()
					+ "(1): Missing header");
			return;
		}

		if (!getStrippedCode().contains("info{")
				|| !getStrippedCode().contains("data{")
				|| !getStrippedCode().contains("init{")
				|| !getStrippedCode().contains("main{")
				|| !getStrippedCode().contains("exit{")) {
			APMain.error("[E] "
					+ getScriptFile().getAbsolutePath()
					+ ": Missing necessary sections (info, data, init, main, exit)");
			return;
		}

		// Main parsing construct

		int i = getStrippedCode().indexOf("info{") + 5;
		int j;

		if (!getStrippedCode().substring(i - 1, i + 1).equals("{}")) {
			j = getStrippedCode().indexOf("}", 0);

			for (java.lang.String k : getStrippedCode().substring(i, j - 1).split(";"))
				if (k.startsWith("name"))
					((InfoSection) getInfoSection()).setScriptName(k.substring(
							"name".length() + 2, k.length() - 1));
				else if (k.startsWith("script-version"))
					((InfoSection) getInfoSection()).setScriptVersion(k
							.substring("script-version".length() + 2,
									k.length() - 1));
				else if (k.startsWith("apsim-version"))
					((InfoSection) getInfoSection()).setApSimVersion(k
							.substring("apsim-version".length() + 2,
									k.length() - 1));
				else if (k.startsWith("tags"))
					for (java.lang.String l : k.substring("tags".length() + 2,
							k.length() - 1).split(","))
						((InfoSection) getInfoSection()).getTags().add(l);
				else if (k.startsWith("description"))
					((InfoSection) getInfoSection()).setDescription(k
							.substring("description".length() + 2,
									k.length() - 1));
		}

		buildCodeSection((CodeSection) getDataSection());
	}

	private void buildCodeSection(CodeSection section) {

		Section next = (Section) section.getNext();
		// start points to the first character inside {}, end points to the }
		int start = getStrippedCode().indexOf(section.getName()) + 5, end = next == null ? getStrippedCode()
				.length() - 1 : getStrippedCode().indexOf(next.getName()) - 1;

		int i, m = start;

		i = getStrippedCode().indexOf(section.getName() + "{") + 5;

		if (!getStrippedCode().substring(i - 1, i + 1).equals("{}")) {
			do {

				m++;
			} while (m < end);
		}
	}

	public File getScriptFile() {
		return scriptFile;
	}

	public void setScriptFile(File scriptFile) {
		this.scriptFile = scriptFile;
	}

	public java.lang.String getRawCode() {
		return rawCode;
	}

	public void setRawCode(java.lang.String rawCode) {
		this.rawCode = rawCode;
	}

	public java.lang.String getStrippedCode() {
		return strippedCode;
	}

	public void setStrippedCode(java.lang.String strippedCode) {
		this.strippedCode = strippedCode;
	}

	public Section getInfoSection() {
		return infoSection;
	}

	public void setInfoSection(Section infoSection) {
		this.infoSection = infoSection;
	}

	public Section getDataSection() {
		return dataSection;
	}

	public void setDataSection(Section dataSection) {
		this.dataSection = dataSection;
	}

	public Section getInitSection() {
		return initSection;
	}

	public void setInitSection(Section initSection) {
		this.initSection = initSection;
	}

	public Section getMainSection() {
		return mainSection;
	}

	public void setMainSection(Section mainSection) {
		this.mainSection = mainSection;
	}

	public Section getExitSection() {
		return exitSection;
	}

	public void setExitSection(Section exitSection) {
		this.exitSection = exitSection;
	}

	public int getErrorCode() {
		return errorCode;
	}

}
