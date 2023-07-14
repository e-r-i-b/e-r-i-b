package com.rssl.phizic.gate.mobilebank;

/**
 * Причина отключения телефона от услуги ЕРМБ
 * @author Puzikov
 * @ created 13.04.15
 * @ $Author$
 * @ $Revision$
 */

public enum PhoneDisconnectionReason
{
	/**
	 * смена абонентом номера MSISDN
	 */
	CHANGE_MSISDN(1),

	/**
	 * закрытие абонена по инициативе ОСС
	 */
	OSS_DISCONNECT(2),

	/**
	 * расторжение абонентом договора с ОСС
	 */
	ABONENT_DISCONNECT(3),

	/**
	 * смена владельца MSISDN c физ.лица на физ.лицо
	 */
	PHIZ_TRANSFER_MSISDN(4),

	/**
	 * смена владельца MSISDN с физ. Лица на юр.лицо
	 */
	JUR_TRANSFER_MSISDN(5),

	/**
	 * Другая (неизвестная)
	 */
	OTHER(-1),
	;

	public final int code;

	private PhoneDisconnectionReason(int code)
	{
		this.code = code;
	}

	/**
	 * @param reason код причины (МБК)
	 * @return причина
	 */
	public static PhoneDisconnectionReason fromCode(int reason)
	{
		for (PhoneDisconnectionReason value : values())
		{
			if (value.code == reason)
				return value;
		}
		throw new IllegalArgumentException("Неизвестный код причины: " + reason);
	}
}
