package com.rssl.phizic.business.login;

import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.authgate.AuthentificationSource;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.utils.StringHelper;

/**
 * Хелпер логирования сообщений при синхронизации клиента в базе
 *
 * @author khudyakov
 * @ created 30.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class LoginInfoHelper
{
	/**
	 * Логируем данные клиента
	 * @param authData - данные аутентификации клиента
	 * @param authToken - ключ аутентификации
	 * @param header - подзаголовок
	 * @param authSrc - источник аутентификации
	 * @return строка с данными клиента для логирования
	 */
	public static StringBuilder getPersonLogInfo(AuthData authData, String authToken, String header, AuthentificationSource authSrc)
	{
		return getLogInfo(authSrc, authToken, header, authData.getLastName(), authData.getFirstName(), authData.getMiddleName(), null, authData.getDocument(), authData.getBirthDate());
	}

	/**
	 * Логируем данные клиента
	 * @param client - клиент
	 * @param authToken - ключ аутентификации
	 * @param header - заголовок
	 * @param authSrc - источник аутентификации
	 * @return строка с данными клиента для логирования
	 */
	public static StringBuilder getPersonLogInfo(Client client, String authToken, String header, AuthentificationSource authSrc)
	{
		ClientDocument document = client.getDocuments().get(0);
		return getLogInfo(authSrc, authToken, header, client.getSurName(), client.getFirstName(), client.getPatrName(), document.getDocSeries(), document.getDocNumber(), String.format("%1$tY-%1$tm-%1$td", client.getBirthDay().getTime()));
	}

	/**
	 * Логируем данные клиента
	 * @param person - персона из нашей БД
	 * @param authToken - ключ аутентификации
	 * @param header - заголовок
	 * @param authSrc - источник аутентификации
	 * @return строка с данными клиента для логирования
	 */
	public static StringBuilder getPersonLogInfo(Person person, String authToken, String header, AuthentificationSource authSrc)
	{
		PersonDocument document = person.getPersonDocuments().iterator().next();
		return getLogInfo(authSrc, authToken, header, person.getSurName(), person.getFirstName(), person.getPatrName(), document.getDocumentSeries(), document.getDocumentNumber(), String.format("%1$tY-%1$tm-%1$td", person.getBirthDay().getTime()));
	}

	/**
	 * Логируем данные клиента
	 * @param client клиент
	 * @param authSrc источник аутентификации
	 * @param authToken ключ аутентификации
	 * @return строка с данными клиента для логирования
	 */
	public static StringBuilder getClientLogInfo(Client client, AuthentificationSource authSrc, String authToken)
	{
		StringBuilder logText = new StringBuilder();
		if (client == null)
		{
			logText = LoginInfoHelper.getMainLogInfo(authSrc, authToken, "Информация по клиенту НЕ получена, клиент входит по кредитной карте, по ФИО+ДУЛ+ДР информации в ЦОД также нет.");
		}
		else
		{
			if (client.isUDBO())
			{
				logText = LoginInfoHelper.getPersonLogInfo(client, authToken, ". Информация по клиенту получена. Клиент УДБО.", authSrc);
			}
			else
			{
				logText = LoginInfoHelper.getMainLogInfo(authSrc, authToken, "Информация по клиенту НЕ получена. Клиент либо Карточный, либо СБОЛ.");
			}
		}

		return logText;
	}

	/**
	 * @param authSrc - источник аутентификации
	 * @param authToken - ключ аутентификации
	 * @param text - текст для вывода пользователю
	 * @return Сформированный стандартный текст для логирования входа
	 */
	public static StringBuilder getMainLogInfo(AuthentificationSource authSrc, String authToken, String text)
	{
		StringBuilder logText = new StringBuilder();
		if (authSrc!= null && !StringHelper.isEmpty(authSrc.getDescription()))
			logText.append("[").append(authSrc.getDescription()).append("] ");
		if (!StringHelper.isEmpty(authToken))
			logText.append("AuthToken = ").append(authToken);
		if (!StringHelper.isEmpty(text))
			logText.append(" ").append(text);
		return logText;
	}

	/**
	 * Формируем строку с данными пользователя для логирования
	 */
	private static StringBuilder getLogInfo(AuthentificationSource authSrc, String authToken, String header, String surName, String firstName, String patrName, String series, String num, String birthDay)
	{
		StringBuilder logText = getMainLogInfo(authSrc, authToken, null);
		if (!StringHelper.isEmpty(header))
				logText.append(" ").append(header);
		logText.append(" ФИО = ").append(surName).append(" ").append(firstName).append(" ").append(patrName);
		if (!StringHelper.isEmpty(series) || !StringHelper.isEmpty(num))
		{
			logText.append(" ДУЛ = ");
			if (!StringHelper.isEmpty(series))
				logText.append(series).append(" ");
			if (!StringHelper.isEmpty(num))
				logText.append(num);
		}
		logText.append(" Дата рождения = ").append(birthDay);
		return logText;
	}
}
