package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.utils.StringHelper;

/**
 * результаты проверки IMSI
 * @author gladishev
 * @ created 18.02.2012
 * @ $Author$
 * @ $Revision$
 */
public enum ImsiCheckResult
{
	//сообщение отправлено
	send("сообщение отправлено"),
	//сообщение находится в процессе отправки и результат пока не известен
	yet_not_send("сообщение находится в процессе отправки"),
	//не удалось получить IMSI по вине абонента ОСС
	client_error("не удалось получить IMSI по вине абонента ОСС"),
	//IMSI не совпал
	imsi_error("IMSI не совпал"),
	//неизвестный код
	UNKNOWN("неизвестный код"),
	imsi_check_ok("проверка IMSI успешна"),
	quarantine("на карантине после Обновления"),
	blocked("заблокирован после проверки"),
	error("техническая ошибка"),
	imsi_ok("IMSI совпал"),
	imsi_fail("IMSI не совпал при проверке"),
	msg_not_found("в АС МБК нет комбинации @iExternalSystemID + @iMessage"),
	yet_not_check("АС МБК в настоящее время взаимодействует с MFM IMSI WS, чтобы обслужить запрос");

	private final String description;

	/**
	 * @return описание
	 */
	public String getDescription()
	{
		return description;
	}

	private ImsiCheckResult(String description)
	{
		if (StringHelper.isEmpty(description))
			throw new IllegalArgumentException("Argument 'description' cannot be null");

		this.description = description;
	}
}
