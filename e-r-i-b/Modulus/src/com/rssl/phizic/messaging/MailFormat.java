package com.rssl.phizic.messaging;

/**
 * ������ �������� ��������� ������� �� ����������� �����
 * �������� ��� ��������: ������� ����� ��� html
 *
 * @author tisov
 * @ created 11.10.13
 * @ $Author$
 * @ $Revision$
 */

public enum MailFormat
{
	/**
	 * ���������� ��������� ������� ������� - �������� �� ���������
	 */
	PLAIN_TEXT("plainText", "�����"),

	/**
	 * ���������� ��������� � ������� html �������
	 */
	HTML("htmlText", "HTML-������");

	private String key;
	private String description;

	private MailFormat(String key, String description)
	{
		this.key = key;
		this.description = description;
	}

	public String getKey()
	{
		return key;
	}

	public String getDescription()
	{
		return description;
	}
}
