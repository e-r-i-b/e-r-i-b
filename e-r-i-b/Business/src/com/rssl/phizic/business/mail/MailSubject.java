package com.rssl.phizic.business.mail;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;

/**
 * �������� �������� ���������
 * @author komarov
 * @ created 17.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class MailSubject extends MultiBlockDictionaryRecordBase
{
	private Long id;            //������������� ��������
	private String description; //��������.

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}
