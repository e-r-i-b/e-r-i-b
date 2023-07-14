package com.rssl.phizic.business.connectUdbo;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;

import java.io.Serializable;
import java.util.Calendar;

/**
 * �������� ��� �������� ������� ��������� �� ����������� ����
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class UDBOClaimRules extends MultiBlockDictionaryRecordBase implements Serializable
{
	private Long id;                                //������������� �������
	private Calendar startDate;                     //���� ������ ��������
	private UDBOClaimRulesStatus status;            //������
	private String rulesText;                       //����� ������ � ������� html

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ ������������� �������
	 * @param id - �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ���� ������ ��������
	 */
	public Calendar getStartDate()
	{
		return startDate;
	}

	/**
	 * ������ ���� ������ �������� �������
	 * @param startDate - ����
	 */
	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @return ������ �������
	 */
	public UDBOClaimRulesStatus getStatus()
	{
		return status;
	}

	/**
	 * ������ ������ �������
	 * @param status - ������
	 */
	public void setStatus(UDBOClaimRulesStatus status)
	{
		this.status = status;
	}

	/**
	 * @return ����� ������
	 */
	public String getRulesText()
	{
		return rulesText;
	}

	/**
	 * ������ ����� �������
	 * @param rulesText - ����� ������ � ������� html
	 */
	public void setRulesText(String rulesText)
	{
		this.rulesText = rulesText;
	}
}
