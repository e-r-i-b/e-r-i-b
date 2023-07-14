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
	 * Возвращает путь к корню проекта
	 * @param project - проект
	 * @return путь к корневой директории проекта
	 */
	public static File getProjectRoot(Project project)
	{
		return new File(project.getProperty("project.root"));
	}

	/**
	 * Возвращает директорию настроек приложения
	 * @param confDir - директория конфигурации
	 * @param appName - имя приложения
	 * @return путь к директории настроек приложения
	 */
	public static File getAppConfDir(File confDir, String appName)
	{
		// У приложения PhizIC директория настроек приложения совпадает с директорией настроек конфигурации
		return appName.equalsIgnoreCase("PhizIC") ? confDir : new File(confDir, appName);
	}

	/**
	 * Строит аргумент командной строки для таска org.apache.tools.ant.taskdefs.Java
	 * @param jvmArgsBean - параметры
	 * @return
	 */
	public static String getJvmArgs(JvmArgsBean jvmArgsBean)
	{
		// fix для
		// Error: Missing ormi[s]://<host>:<port>
		return "-Djava.net.preferIPv4Stack=true";
	}

	/**
	 * Возвращает FilenameSelector по заданной строке
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
