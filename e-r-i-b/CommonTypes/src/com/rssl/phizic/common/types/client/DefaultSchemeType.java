package com.rssl.phizic.common.types.client;

import com.rssl.phizic.common.types.csa.ProfileType;

/**
 * @author komarov
 * @ created 18.07.2014
 * @ $Author$
 * @ $Revision$
 *  ��� ����� ���� �� ���������
 */
public enum DefaultSchemeType
{
	//����� ���� ����.
	SBOL,
	//������ ���� ���������� �������.
	CARD,
	//����� ���� ��� ����.
	UDBO,
	//����� ���� ���� ������� � ��������� �����.
	UDBO_TEMPORARY,
	//����� ���� ���������� ������� � ��������� �����.
	CARD_TEMPORARY,
	//����� ���� ��������� ����� ��� ���������� ������ �� ������, ��������� �����
	GUEST;

	/**
	 * ���������� ��� ����� ���� �� ��������� �� ���� ��������, �� �������� ��������� ������
	 * @param type ��� ��������, �� �������� ��������� ������
	 * @return ��� ����� ���� �� ���������
	 */
	public static DefaultSchemeType getDefaultSchemeType(CreationType type)
	{
		switch (type)
		{
			case UDBO: return DefaultSchemeType.UDBO;
			case SBOL: return DefaultSchemeType.SBOL;
			case CARD: return DefaultSchemeType.CARD;
			default: throw new IllegalArgumentException("�������� �������� ��������� type:" + type);
		}
	}

	/**
	 * ���������� ��� ����� ���� �� ��������� �� ���� ��������, �� �������� ��������� ������ � ���� �������
	 * @param type ��� ��������, �� �������� ��������� ������
	 * @param profileType ��� �������
	 * @return ��� ����� ���� �� ���������
	 */
	public static DefaultSchemeType getDefaultSchemeType(CreationType type, ProfileType profileType)
	{
		if(profileType == ProfileType.TEMPORARY)
			return type == CreationType.UDBO ? UDBO_TEMPORARY: CARD_TEMPORARY;
		return getDefaultSchemeType(type);
	}
}
