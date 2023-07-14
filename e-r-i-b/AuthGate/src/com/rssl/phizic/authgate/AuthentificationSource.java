package com.rssl.phizic.authgate;

/**
 * @author egorova
 * @ created 13.11.2010
 * @ $Author$
 * @ $Revision$
 */

public enum AuthentificationSource
{
	//������ ������
	full_version("���. ������"),
	//��������� ������ (� �.�. iPhone)
	mobile_version("���. ������"),
	//��� ������
	atm_version("��� ������");

	private String description;

	AuthentificationSource(String description)
	{
	   this.description = description;
	}

	/**
	 * @return �������� ��������� ��������������
	 */
	public String getDescription()
	{
		return description;
	}
}
