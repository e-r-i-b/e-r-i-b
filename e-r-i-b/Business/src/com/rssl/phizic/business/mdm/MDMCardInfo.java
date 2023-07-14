package com.rssl.phizic.business.mdm;

import com.rssl.phizic.person.PersonDocumentType;

import java.util.Calendar;

/**
 * �������� ��� �������� ������ �� ��������
 * @author komarov
 * @ created 21.07.15
 * @ $Author$
 * @ $Revision$
 */
public class MDMCardInfo extends MDMClientInfo
{
	private String citizenship;
	private PersonDocumentType documentKind;
	private String series;
	private String no;
	private String issuedby;
	private Calendar issueday;
	private boolean isActual;
	private String subdivision;
	private Calendar endDay;
	private String entryDay;

	/**
	 * @return ����� ����������
	 */
    public String getCitizenship()
	{
		return citizenship;
	}

	/**
	 * @return ��� ���������
	 */
	public PersonDocumentType getDocumentKind()
	{
		return documentKind;
	}

	/**
	 * @return �����
	 */
	public String getSeries()
	{
		return series;
	}

	/**
	 * @return �����
	 */
	public String getNo()
	{
		return no;
	}

	/**
	 * @return ��� �����
	 */
	public String getIssuedby()
	{
		return issuedby;
	}

	/**
	 * @return ����� �����
	 */
	public Calendar getIssueday()
	{
		return issueday;
	}

	/**
	 * @return ������� ������������
	 */
	public boolean getActual()
	{
		return isActual;
	}

	/**
	 * @return �������������
	 */
	public String getSubdivision()
	{
		return subdivision;
	}

	/**
	 * @return ���� ���������
	 */
	public Calendar getEndDay()
	{
		return endDay;
	}

	/**
	 * @return ����
	 */
	public String getEntryDay()
	{
		return entryDay;
	}
}
