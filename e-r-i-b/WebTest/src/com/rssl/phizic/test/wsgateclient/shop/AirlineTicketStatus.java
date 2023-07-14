package com.rssl.phizic.test.wsgateclient.shop;

/**
 * @author Erkin
* @ created 23.05.2012
* @ $Author$
* @ $Revision$
*/
public enum AirlineTicketStatus
{
	OK(0, "Билеты были выпущены успешно."),
	BAD_TICKETS(-1, "платеж принят, билеты не выпущены"),
	DOUBLE_PAYMENT(-2, "заказ был уже оплачен ранее"),
	FATAL(-3, "произошла фатальная системная ошибка");

	public static AirlineTicketStatus fromCode(String codeAsString)
	{
		int code = Integer.parseInt(codeAsString);
		for (AirlineTicketStatus status : values()) {
			if (status.getCode() == code)
				return status;
		}
		throw new IllegalArgumentException("Неожиданный код статуса: " + code);
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
