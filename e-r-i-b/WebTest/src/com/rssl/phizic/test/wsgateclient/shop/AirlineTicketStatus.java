package com.rssl.phizic.test.wsgateclient.shop;

/**
 * @author Erkin
* @ created 23.05.2012
* @ $Author$
* @ $Revision$
*/
public enum AirlineTicketStatus
{
	OK(0, "������ ���� �������� �������."),
	BAD_TICKETS(-1, "������ ������, ������ �� ��������"),
	DOUBLE_PAYMENT(-2, "����� ��� ��� ������� �����"),
	FATAL(-3, "��������� ��������� ��������� ������");

	public static AirlineTicketStatus fromCode(String codeAsString)
	{
		int code = Integer.parseInt(codeAsString);
		for (AirlineTicketStatus status : values()) {
			if (status.getCode() == code)
				return status;
		}
		throw new IllegalArgumentException("����������� ��� �������: " + code);
	}

	public int getCode()
	{
		return code;
	}

	public String getText()
	{
		return text;
	}

	private AirlineTicketStatus(int code, String text)
	{
		this.code = code;
		this.text = text;
	}

	private final int code;

	private final String text;
}
