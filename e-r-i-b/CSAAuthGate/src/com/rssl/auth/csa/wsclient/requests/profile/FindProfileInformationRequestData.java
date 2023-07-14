package com.rssl.auth.csa.wsclient.requests.profile;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 30.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Данные для запроса поиска информации о профиле клиента
 */
public class FindProfileInformationRequestData extends FindOrCreateProfileInformationRequestDataBase
{
	private static final String REQUEST_NAME = "findProfileInformationRq";

	/**
	 * Конструктор
	 * @param surName - фамилия
     * @param firstName - имя
	 * @param patrName - отчество
	 * @param passport - серия и номер документа
	 * @param birthdate - дата рождения
	 * @param tb - тербанк
	 */
	public FindProfileInformationRequestData(String surName, String firstName, String patrName, String passport, Calendar birthdate, String tb)
	{
		super(surName,firstName,patrName,passport,birthdate,tb);
	}

	public String getName()
	{
		return REQUEST_NAME;
	}


}
