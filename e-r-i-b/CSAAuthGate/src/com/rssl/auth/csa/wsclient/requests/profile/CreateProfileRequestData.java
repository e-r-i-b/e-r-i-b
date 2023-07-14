package com.rssl.auth.csa.wsclient.requests.profile;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 10.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Данные для запроса создания профиля клиента в ЦСА
 */
public class CreateProfileRequestData extends FindOrCreateProfileInformationRequestDataBase
{
	private static final String REQUEST_NAME = "createProfileRq";

	/**
	 * Конструктор
	 * @param surName - фамилия
     * @param firstName - имя
	 * @param patrName - отчество
	 * @param passport - серия и номер документа
	 * @param birthdate - дата рождения
	 * @param tb - тербанк
	 */
	public CreateProfileRequestData(String surName, String firstName, String patrName, String passport, Calendar birthdate, String tb)
	{
		super(surName, firstName, patrName, passport, birthdate, tb);
	}

	public String getName()
	{
		return REQUEST_NAME;
	}
}
