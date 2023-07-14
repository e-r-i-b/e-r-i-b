package com.rssl.phizic.messaging.mail.messagemaking.push;

/**
 * ��� ����������� push-���������
 * @author basharin
 * @ created 08.10.13
 * @ $Author$
 * @ $Revision$
 */

public enum PushPrivacyType
{
	/**
	 * ���������
	 */
	PUBLIC("Y"),

	/**
	 * ��������
	 */
	PRIVATE("N");

	PushPrivacyType(String value)
	{
		this.value = value;
	}

	/**
	 * ��� �������
	 */
	private final String value;

	public String toValue()
	{
		return value;
	}
}

