package com.rssl.phizic.logging.logon;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 29.06.15
 * @ $Author$
 * @ $Revision$
 */
public interface LogonLogWriter
{
	/**
	 * Запись события "поиск профиля" в журнал регистрации входов
	 * @param loginId логин
	 * @param firstName имя
	 * @param patrName  отчество
	 * @param surName фамилия
	 * @param birthday ДР
	 * @param cardNumber номер карты входа
	 * @param docSeries серия ДУЛ
	 * @param docNumber номер ДУЛ
	 * @param browserInfo информация об устройстве
	 */
	void writeFindProfile(Long loginId, String firstName, String patrName, String surName, Calendar birthday, String cardNumber, String docSeries, String docNumber, String browserInfo);

	/**
	 * Запись события "вход" в журнал регистрации входов
	 * @param loginId логин
	 * @param firstName имя
	 * @param patrName  отчество
	 * @param surName фамилия
	 * @param birthday ДР
	 * @param cardNumber номер карты входа
	 * @param docSeries серия ДУЛ
	 * @param docNumber номер ДУЛ
	 * @param browserInfo информация об устройстве
	 */
	void writeLogon(Long loginId, String firstName, String patrName, String surName, Calendar birthday, String cardNumber, String docSeries, String docNumber, String browserInfo);
}
