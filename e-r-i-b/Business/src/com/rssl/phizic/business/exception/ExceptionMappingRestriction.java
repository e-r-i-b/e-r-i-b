package com.rssl.phizic.business.exception;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;

import java.io.Serializable;

/**
 * @author komarov
 * @ created 03.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class ExceptionMappingRestriction extends MultiBlockDictionaryRecordBase implements Serializable
{
	private String application;
	private String tb;

	/**
	 * @return ����������
	 */
	public String getApplication()
	{
		return application;
	}

	/**
	 * @param application ����������
	 */
	public void setApplication(String application)
	{
		this.application = application;
	}

	/**
	 * @return �������
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @param tb �������
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}
}
