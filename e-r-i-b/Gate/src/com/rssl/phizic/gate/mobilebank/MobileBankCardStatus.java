package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;

/**
 * @author Erkin
 * @ created 20.04.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ����� � ��.
 */
public enum MobileBankCardStatus implements Serializable
{
	/**
	 * ��������
	 */
	ACTIVE("A"),

	/**
	 * ��������������
	 */
	INACTIVE("I");

	/**
	 * ��������� ������� �� ����
	 * @param code ��� ������� � ��
	 * @return ������-������
	 */
	public static MobileBankCardStatus forCode(String code)
	{
		if (code == null)
			return null;
		for (MobileBankCardStatus status : values()) {
			if (code.equalsIgnoreCase(status.code))
				return status;
		}
		throw new IllegalArgumentException("Unknown card status' code " + code);
	}

	/**
	 * @return ��������� ������������� �������
	 */
	public String getCode()
	{
		return code;
	}

	MobileBankCardStatus(String code)
	{
		if (StringHelper.isEmpty(code))
			throw new IllegalArgumentException("Argument 'code' cannot be null");

		this.code = code;
	}

	private final String code;
}