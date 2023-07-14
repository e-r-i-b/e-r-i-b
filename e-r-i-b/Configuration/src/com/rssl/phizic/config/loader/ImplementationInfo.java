package com.rssl.phizic.config.loader;

import com.rssl.phizic.common.types.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * ���������� � ���������� �������.
 *
 * @author bogdanov
 * @ created 18.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class ImplementationInfo
{
	/**
	 * ��� ������ ����������.
	 */
	private String clazz;
	/**
	 * �������� ���������� ��� null, ��� �������� ���������� ��������� ����������.
	 */
	private Application application;
	/**
	 * ���� ��� ������ ��������.
	 */
	private String fileName;

	/**
	 * ���� ������ ��� ������������� ��������.
	 */
	private DbInfo dbInfo;

	/**
	 * ������������ ������ ����� ������ CompositePropertyReader.
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
