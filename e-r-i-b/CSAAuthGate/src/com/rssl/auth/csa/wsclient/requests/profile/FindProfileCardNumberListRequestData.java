package com.rssl.auth.csa.wsclient.requests.profile;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 27.05.14
 * @ $Author$
 * @ $Revision$
 *
 * Данные для запроса получения списка карт клиента из коннекторов ЦСА по профилю
 */
public class FindProfileCardNumberListRequestData extends FindOrCreateProfileInformationRequestDataBase
{
	private static final String REQUEST_NAME = "findProfileCardNumberListRq";

	/**
	 * Конструктор
	 * @param surName - фамилия
	 * @param firstName - имя
	 * @param patrName - отчество
	 * @param passport - серия и номер документа
	 * @param birthdate - дата рождения
	 * @param tb - тербанк
	 */
	public FindProfileCardNumberListRequestData(String surName, String firstName, String patrName, String passport, Calendar birthdate, String tb)
	{
		super(surName, firstName, patrName, passport, birthdate, tb);
	}

	public String getName()
	{
		return REQUEST_NAME;
	}
}
