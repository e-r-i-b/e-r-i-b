package com.rssl.phizic.business.dictionaries.pfp.personData;

/**
 * @author mihaylov
 * @ created 01.03.2012
 * @ $Author$
 * @ $Revision$
 */

/* �������� ��������� */
public enum MaritalStatus
{
	MARRIED("�����/�������"),
	NOT_MARRIED("������/�� �������");

	private String description;

	MaritalStatus(String description)
	{
		this.description = description;
	}

	/**
	 * @return �������� ��������� ���������
	 */
	public String getDescription()
	{
		return description;
	}
}
