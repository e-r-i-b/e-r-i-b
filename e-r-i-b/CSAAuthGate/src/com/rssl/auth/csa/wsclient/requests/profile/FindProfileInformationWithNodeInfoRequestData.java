package com.rssl.auth.csa.wsclient.requests.profile;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 13.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Данные для запроса поиска информации о профиле клиента с полной информацией об узлах в которых работал клиент
 */

public class FindProfileInformationWithNodeInfoRequestData extends FindProfileInformationRequestData
{
	private static final String REQUEST_NAME = "findProfileInformationWithNodeInfoRq";

	/**
	 * Конструктор
	 * @param surName - фамилия
	 * @param firstName - имя
	 * @param patrName - отчество
	 * @param passport - серия и номер документа
	 * @param birthdate - дата рождения
	 * @param tb - тербанк
	 */
	public FindProfileInformationWithNodeInfoRequestData(String surName, String firstName, String patrName, String passport, Calendar birthdate, String tb)
	{
		super(surName, firstName, patrName, passport, birthdate, tb);
	}

	@Override
	public String getName()
	{
		return REQUEST_NAME;
	}
}
