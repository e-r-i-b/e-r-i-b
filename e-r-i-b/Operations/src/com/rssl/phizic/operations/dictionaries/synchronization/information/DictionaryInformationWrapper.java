package com.rssl.phizic.operations.dictionaries.synchronization.information;

import com.rssl.phizic.business.dictionaries.synchronization.information.DictionaryInformation;
import com.rssl.phizic.business.dictionaries.synchronization.information.DictionaryState;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 04.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * ���������� � ��������� ������������
 */

public class DictionaryInformationWrapper
{
	private String nodeName;
	private Calendar lastUpdateDate;
	private DictionaryState state;
	private String errorDetail;

	/**
	 * �����������
	 * @param nodeName �������� �����
	 */
	public DictionaryInformationWrapper(String nodeName)
	{
		this.nodeName = nodeName;
	}

	/**
	 * �����������
	 * @param nodeName �������� �����
	 * @param information ���������� � ��������� ������������
	 */
	public DictionaryInformationWrapper(String nodeName, DictionaryInformation information)
	{
		this(nodeName);
		this.lastUpdateDate = information.getLastUpdateDate();
		this.state = information.getState();
		this.errorDetail = information.getErrorDetail();
	}

	/**
	 * @return �������� �����
	 */
	public String getNodeName()
	{
		return nodeName;
	}

	/**
	 * @return ���� ���������� ����������
	 */
	public Calendar getLastUpdateDate()
	{
		return lastUpdateDate;
	}

	/**
	 * @return ��������� ������������
	 */
	public DictionaryState getState()
	{
		return state;
	}

	/**
	 * @return �������� ������
	 */
	public String getErrorDetail()
	{
		return errorDetail;
	}
}
