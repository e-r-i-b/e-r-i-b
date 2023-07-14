package com.rssl.phizicgate.mdm.common;

import com.rssl.phizic.common.types.client.ClientDocumentType;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 15.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ��������
 */

public class DocumentInfo
{
	private Long id;
	private ClientDocumentType type;
	private String series;
	private String number;
	private String issuedBy;
	private Calendar issuedDate;

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ �������������
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ���
	 */
	public ClientDocumentType getType()
	{
		return type;
	}

	/**
	 * ������ ���
	 * @param type ���
	 */
	public void setType(ClientDocumentType type)
	{
		this.type = type;
	}

	/**
	 * @return �����
	 */
	public String getSeries()
	{
		return series;
	}

	/**
	 * ������ �����
	 * @param series �����
	 */
	public void setSeries(String series)
	{
		this.series = series;
	}

	/**
	 * @return �����
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * ������ �����
	 * @param number �����
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}

	/**
	 * @return ��� �����
	 */
	public String getIssuedBy()
	{
		return issuedBy;
	}

	/**
	 * ������ ��� �����
	 * @param issuedBy ��� �����
	 */
	public void setIssuedBy(String issuedBy)
	{
		this.issuedBy = issuedBy;
	}

	/**
	 * @return ���� ������
	 */
	public Calendar getIssuedDate()
	{
		return issuedDate;
	}

	/**
	 * ������ ���� ������
	 * @param issuedDate ����
	 */
	public void setIssuedDate(Calendar issuedDate)
	{
		this.issuedDate = issuedDate;
	}
}
