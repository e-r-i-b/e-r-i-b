package com.rssl.phizicgate.mdm.common;

import com.rssl.phizic.common.types.client.ClientDocumentType;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 15.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �� ������� ��� �������� � ���
 */

public class CreateNotificationClientInfo
{
	private String mdmId;
	private Long innerId;
	private String lastName;
	private String firstName;
	private String middleName;
	private Calendar birthday;
	private String documentSeries;
	private String documentNumber;
	private ClientDocumentType documentType;

	/**
	 * @return ������������� ���
	 */
	public String getMdmId()
	{
		return mdmId;
	}

	/**
	 * ������ ������������� ���
	 * @param mdmId �������������
	 */
	public void setMdmId(String mdmId)
	{
		this.mdmId = mdmId;
	}

	/**
	 * @return ���������� ������������� �������
	 */
	public Long getInnerId()
	{
		return innerId;
	}

	/**
	 * ������ ���������� ������������� �������
	 * @param innerId �������������
	 */
	public void setInnerId(Long innerId)
	{
		this.innerId = innerId;
	}

	/**
	 * @return �������
	 */
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * ������ �������
	 * @param lastName �������
	 */
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	/**
	 * @return ���
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * ������ ���
	 * @param firstName ���
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return ��������
	 */
	public String getMiddleName()
	{
		return middleName;
	}

	/**
	 * ������ ��������
	 * @param middleName ��������
	 */
	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	/**
	 * @return ���� ��������
	 */
	public Calendar getBirthday()
	{
		return birthday;
	}

	/**
	 * ������ ���� ��������
	 * @param birthday ����
	 */
	public void setBirthday(Calendar birthday)
	{
		this.birthday = birthday;
	}

	/**
	 * @return ����� ���������
	 */
	public String getDocumentSeries()
	{
		return documentSeries;
	}

	/**
	 * ������ ����� ���������
	 * @param documentSeries ����� ���������
	 */
	public void setDocumentSeries(String documentSeries)
	{
		this.documentSeries = documentSeries;
	}

	/**
	 * @return ����� ���������
	 */
	public String getDocumentNumber()
	{
		return documentNumber;
	}

	/**
	 * ������ ����� ���������
	 * @param documentNumber ����� ���������
	 */
	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	/**
	 * @return ��� ���������
	 */
	public ClientDocumentType getDocumentType()
	{
		return documentType;
	}

	/**
	 * ������ ��� ���������
	 * @param documentType ��� ���������
	 */
	public void setDocumentType(ClientDocumentType documentType)
	{
		this.documentType = documentType;
	}
}
