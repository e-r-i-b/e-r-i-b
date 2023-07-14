package com.rssl.phizic.gate.mobilebank;

import java.util.Calendar;

/**
 * @author Gulov
 * @ created 09.09.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Отключенный от ОСС телефон. Возвращается из заглушки МБ.
 */
public class DisconnectedPhoneResult
{
	/**
	 * идентификатор записи
	 */
	private int id;

	/**
	 * Номер телефона, о котором ОСС сообщил оботключении в 11 значном формате. Всегда начинается на 7.
	 */
	private String phoneNumber;

	/**
	 * Причина отключения указанная ОСС
	 * 1 – смена абонентом номера MSISDN
	 * 2 – закрытие абонена по инициативе ОСС
	 * 3 – расторжение абонентом договора с ОСС
	 * 4 – смена владельца MSISDN c физ.лица на физ.лицо
	 * 5 – смена владельца MSISDN с физ. Лица на юр.лицо и с юр.лица на физ.лицо
	 */
	private PhoneDisconnectionReason reason;
	/**
	 * Источник откуда получена информация об отключении:
	 * 1 – Сервис Мегафон
	 * 2 – Списки МТС
	 */
	private int disconnectedPhoneSource;

	/**
	 * Дата создания АС МБК записи в таблице dbo.ERMB_DisconnectedPhones
	 */
	private Calendar createdAt;

	/**
	 * Когда информация об отключении стала доступной АС МБК. Не для всех источников эта информация есть.
	 */
	private Calendar receivedAt;

	/**
	 * 1 – Обработано ЕРИБ
	 * 0 – Не обработано ЕРИБ
	 * Устанавливается ЕРИБ.
	 */
	private boolean state;

	/**
	 * Время обработки записи ЕРИБ. Устанавливается ЕРИБ при изменении статуса.
	 */
	private Calendar processedTime;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public PhoneDisconnectionReason getReason()
	{
		return reason;
	}

	public void setReason(PhoneDisconnectionReason reason)
	{
		this.reason = reason;
	}

	public void setReason(String reason)
	{
		if(reason == null || reason.trim().length() == 0)
			return;
		this.reason = Enum.valueOf(PhoneDisconnectionReason.class, reason);
	}

	public int getDisconnectedPhoneSource()
	{
		return disconnectedPhoneSource;
	}

	public void setDisconnectedPhoneSource(int disconnectedPhoneSource)
	{
		this.disconnectedPhoneSource = disconnectedPhoneSource;
	}

	public Calendar getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt)
	{
		this.createdAt = createdAt;
	}

	public Calendar getReceivedAt()
	{
		return receivedAt;
	}

	public void setReceivedAt(Calendar receivedAt)
	{
		this.receivedAt = receivedAt;
	}

	public boolean isState()
	{
		return state;
	}

	public void setState(boolean state)
	{
		this.state = state;
	}

	public Calendar getProcessedTime()
	{
		return processedTime;
	}

	public void setProcessedTime(Calendar processedTime)
	{
		this.processedTime = processedTime;
	}
}
