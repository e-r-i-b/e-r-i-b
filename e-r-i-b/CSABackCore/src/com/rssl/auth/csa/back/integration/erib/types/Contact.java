package com.rssl.auth.csa.back.integration.erib.types;

/**
 * @author akrenev
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������� �������
 */

public class Contact
{
	private String contactNum;
	private String contactType;

	/**
	 * @return �������� ��������
	 */
	public String getContactNum()
	{
		return contactNum;
	}

	/**
	 * ������ �������� ��������
	 * @param contactNum �������� ��������
	 */
	public void setContactNum(String contactNum)
	{
		this.contactNum = contactNum;
	}

	/**
	 * @return ��� ��������
	 */
	public String getContactType()
	{
		return contactType;
	}

	/**
	 * ������ ��� ��������
	 * @param contactType ��� ��������
	 */
	public void setContactType(String contactType)
	{
		this.contactType = contactType;
	}
}
