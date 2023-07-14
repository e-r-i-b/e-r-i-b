package com.rssl.auth.csa.front.exceptions;

/**
 * Front'овое представление исключения, сигнализирующего о невозможности отправки СМС из-за подмены SIM-карты
 * @author Jatsky
 * @ created 02.12.14
 * @ $Author$
 * @ $Revision$
 */

public class SendSmsMessageFrontException extends FrontLogicException
{
	private static final String messagePrefix = "Регистрация приложения не может быть завершена, так как Вы заменили SIM-карту для номера телефона ";
	private static final String messagePostfix = ". В целях безопасности обратитесь в Контактный Центр Сбербанка по телефону 8 800 555-55-50 для подтверждения замены SIM-карты.";
	private String phones;

	public SendSmsMessageFrontException(String phones)
	{
		super(messagePrefix + phones + messagePostfix);
		this.phones = phones;
	}

	public String getPhones()
	{
		return phones;
	}
}
