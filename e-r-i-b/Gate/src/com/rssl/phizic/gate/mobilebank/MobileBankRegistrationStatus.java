package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;

/**
 * @author Erkin
 * @ created 20.12.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ����������� (�����������) � ��
 */
public enum MobileBankRegistrationStatus implements Serializable
{
	/**
	 * ����������� �������
	 */
	ACTIVE("A"),

	/**
	 * ����������� ��������������
	 */
	INACTIVE("I"),

	/**
	 * ����������� �������������
	 */
	BLOCKED("PB"),

	/**
	 * ����������� ������������� (��������������� ����� ��������)
	 */
	STOLEN("CB");

	/**
	 * ��������� ������� �� ����
	 * @param code ��� ������� � ��
	 * @return ������-������
	 */
	public static MobileBankRegistrationStatus forCode(String code)
	{
		if (code == null)
			return null;
		for (MobileBankRegistrationStatus status : values()) {
			if (code.equalsIgnoreCase(status.code))
				return status;
		}
		return null;
	}

	/**
	 * @return ��������� ������������� �������
	 */
	public String getCode()
	{
		return code;
	}

	private MobileBankRegistrationStatus(String code)
	{
		if (StringHelper.isEmpty(code))
			throw new IllegalArgumentException("�������� 'code' �� ����� ���� ������");

		this.code = code;
	}

	private final String code;
}
