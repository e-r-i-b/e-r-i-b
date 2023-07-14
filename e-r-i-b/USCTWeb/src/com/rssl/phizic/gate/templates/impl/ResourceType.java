package com.rssl.phizic.gate.templates.impl;

/**
 * ��� ������� ��������/����������
 *
 * @author khudyakov
 * @ created 23.04.14
 * @ $Author$
 * @ $Revision$
 */
public enum ResourceType
{
	/**
	 * ���� �������
	 */
	ACCOUNT("com.rssl.phizic.business.resources.external.AccountLink"),

	/**
	 * ����� ����
	 */
	EXTERNAL_ACCOUNT(null),

	/**
	 * ����� �������
	 */
	CARD("com.rssl.phizic.business.resources.external.CardLink"),

	/**
	 * ����� �����
	 */
	EXTERNAL_CARD(null),

	/**
	 * ������
	 */
	LOAN("com.rssl.phizic.business.resources.external.LoanLink"),

	/**
	 * ���� ����
	 */
	DEPO_ACCOUNT("com.rssl.phizic.business.resources.external.DepoAccountLink"),

	/**
	 * ��� (������������ ������������� ����)
	 */
	IM_ACCOUNT("com.rssl.phizic.business.resources.external.IMAccountLink"),

	/**
	 * ������ ������
	 */
	NULL(null);


	private String stringType;

	ResourceType(String stringType)
	{
		this.stringType = stringType;
	}

	String getStringType()
	{
		return stringType;
	}

	/**
	 * ������������ ��� ������� ��������/���������� �� ������
	 * �����������: �� �������� ��� ������� ����������: EXTERNAL_CARD, EXTERNAL_ACCOUNT
	 *
	 * @param type ��� ������� ��������/���������� ��� ������
	 * @return ��� ������� ��������/����������
	 */
	public static ResourceType fromValue(String type)
	{
		if (ACCOUNT.getStringType().equals(type))
		{
			return ACCOUNT;
		}
		if (CARD.getStringType().equals(type))
		{
			return CARD;
		}
		if (LOAN.getStringType().equals(type))
		{
			return LOAN;
		}
		if (DEPO_ACCOUNT.getStringType().equals(type))
		{
			return DEPO_ACCOUNT;
		}
		if (IM_ACCOUNT.getStringType().equals(type))
		{
			return IM_ACCOUNT;
		}
		return NULL;
	}
}
