package com.rssl.phizic.web.ext.sbrf.persons;

import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tisov
 * @ created 29.06.15
 * @ $Author$
 * @ $Revision$
 * ����� ����������� �������� ����-���������
 */
public class AsyncResolveSuspectedDocumentForm extends ActionFormBase
{
	private String verdict;
	private String verdictText;
	private long documentId;
	private long person;

	/**
	 * ������� ��� ���������
	 * @return
	 */
	public String getVerdict()
	{
		return verdict;
	}

	/**
	 * ���������� ������� ��� ���������
	 * @param verdict
	 */
	public void setVerdict(String verdict)
	{
		this.verdict = verdict;
	}

	/**
	 * ��������� ���������� � ��������
	 * @return
	 */
	public String getVerdictText()
	{
		return verdictText;
	}

	/**
	 * ���������� ��������� ���������� � ��������
	 * @param verdictText
	 */
	public void setVerdictText(String verdictText)
	{
		this.verdictText = verdictText;
	}

	/**
	 * �� ���������, �� �������� ��������� �������
	 * @return
	 */
	public long getDocumentId()
	{
		return documentId;
	}

	/**
	 * ���������� �� ���������
	 * @param documentId
	 */
	public void setDocumentId(long documentId)
	{
		this.documentId = documentId;
	}

	/**
	 * ������������� �������
	 * @return
	 */
	public long getPerson()
	{
		return person;
	}

	/**
	 * ������������� �������
	 * @param person
	 */
	public void setPerson(long person)
	{
		this.person = person;
	}
}
