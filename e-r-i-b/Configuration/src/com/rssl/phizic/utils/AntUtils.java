package com.rssl.phizic.utils;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.selectors.FilenameSelector;

import java.io.File;

/**
 * @author Erkin
 * @ created 03.01.2013
 * @ $Author$
 * @ $Revision$
 */

public class AntUtils
{
	/**
	 * ���������� ���� � ����� �������
	 * @param project - ������
	 * @return ���� � �������� ���������� �������
	 */
	public static File getProjectRoot(Project project)
	{
		return new File(project.getProperty("project.root"));
	}

	/**
	 * ���������� ���������� �������� ����������
	 * @param confDir - ���������� ������������
	 * @param appName - ��� ����������
	 * @return ���� � ���������� �������� ����������
	 */
	public static File getAppConfDir(File confDir, String appName)
	{
		// � ���������� PhizIC ���������� �������� ���������� ��������� � ����������� �������� ������������
		return appName.equalsIgnoreCase("PhizIC") ? confDir : new File(confDir, appName);
	}

	/**
	 * ������ �������� ��������� ������ ��� ����� org.apache.tools.ant.taskdefs.Java
	 * @param jvmArgsBean - ���������
	 * @return
	 */
	public static String getJvmArgs(JvmArgsBean jvmArgsBean)
	{
		// fix ���
		// Error: Missing ormi[s]://<host>:<port>
		return "-Djava.net.preferIPv4Stack=true";
	}

	/**
	 * ���������� FilenameSelector �� �������� ������
	 * @param path
	 * @return
	 */
	public static FilenameSelector createFilenameSelector(String path)
	{
		FilenameSelector selector = new FilenameSelector();
		selector.setName(path);
		return selector;
	}
}
