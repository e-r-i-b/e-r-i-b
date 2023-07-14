package com.rssl.phizic.config.loader;

import com.rssl.phizic.common.types.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Информация о реализации конфига.
 *
 * @author bogdanov
 * @ created 18.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class ImplementationInfo
{
	/**
	 * Имя класса реализации.
	 */
	private String clazz;
	/**
	 * Название приложения или null, для которого необходимо выполнить реализацию.
	 */
	private Application application;
	/**
	 * Файл для чтения настроек.
	 */
	private String fileName;

	/**
	 * База данных для использования конфигом.
	 */
	private DbInfo dbInfo;

	/**
	 * Использовать другой ридер вместо CompositePropertyReader.
	 */
	private String reader;

	public Application getApplication()
	{
		return application;
	}

	public void setApplication(Application application)
	{
		this.application = application;
	}

	public String getClazz()
	{
		return clazz;
	}

	public void setClazz(String clazz)
	{
		this.clazz = clazz;
	}

	public String getFileName()
	{
		return fileName;
	}

	public DbInfo getDbInfo()
	{
		return dbInfo;
	}

	public void setFile(String file)
	{
		fileName = file;
	}

	public void setDbInfo(DbInfo info) {
		dbInfo = info;
	}

	public String getReader()
	{
		return reader;
	}

	public void setReader(String reader)
	{
		this.reader = reader;
	}
}
