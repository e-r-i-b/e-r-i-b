package com.rssl.phizic.gate.ermb;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;

/**
 * ����� ����������� � ���
 * @author Puzikov
 * @ created 22.07.14
 * @ $Author$
 * @ $Revision$
 */

public enum MbkTariff
{
	//1 � ������ �����; 0 � ��������� �����
	//"����� 3" (������������� ���������� ������ ����)
	FULL("full", 1),
	SAVING("saving", 0),
	PACKAGE3("saving", 3),
	;

	private final String stringCode;
	private final int mbkCode;

	MbkTariff(String stringCode, int mbkCode)
	{
		this.stringCode = stringCode;
		this.mbkCode = mbkCode;
	}

	/**
	 * @return ������� ��� ������
	 * @see com.rssl.phizic.business.ermb.ErmbTariff#code
	 */
	public String getStringCode()
	{
		return stringCode;
	}

	/**
	 * @return ��� ������ � ���
	 */
	public int getMbkCode()
	{
		return mbkCode;
	}

	/**
	 * @param mbkCode ��� ������ � ���
	 * @return �����
	 */
	public static MbkTariff fromMbkCode(int mbkCode)
	{
		for (MbkTariff mbkTariff : values())
		{
			if (mbkTariff.getMbkCode() == mbkCode)
				return mbkTariff;
		}

		throw new IllegalArgumentException("����������� ��� ������ � ���: " + mbkCode);
	}

	/**
	 * @param stringCode ���������� ��� ������
	 * @see com.rssl.phizic.business.ermb.ErmbTariff#code
	 * @return �����
	 */
	public static MbkTariff fromStringCode(String stringCode)
	{
		for (MbkTariff mbkTariff : values())
		{
			if (mbkTariff.getStringCode().equals(stringCode))
				return mbkTariff;
		}

		throw new IllegalArgumentException("����������� ���������� ��� ������: " + stringCode);
	}
}
