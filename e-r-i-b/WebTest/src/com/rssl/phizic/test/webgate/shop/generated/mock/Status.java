package com.rssl.phizic.test.webgate.shop.generated.mock;

import com.rssl.phizic.test.webgate.shop.generated.StatusType;

import java.util.Random;

/**
 * @author gulov
 * @ created 14.01.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Создает рандомным способом объект статуса документа
 */
public class Status
{
	/**
	 * Код успешного завершения
	 */
	private static final long SUCCESS_CODE = 0;

	/**
	 * Код ошибки
	 */
	private static final long ERROR_CODE = -999;

	/**
	 * Максимум для рандома
	 */
	private static final int MAX_RANDOM = 3;

	/**
	 * Рандомное значение для ERROR_CODE
	 */
	private static final int RANDOM_ERROR_VALUE = 1;

	/**
	 * Сообщение об ошибке
	 */
	private static final String ERROR_TEXT = "Ошибка готовая для отображения пользователю";

	public Status()
	{
	}

	/**
	 * Создать и заполнить объект статуса документа
	 * @return статуса документа
	 */
	public StatusType getStatus()
	{
		StatusType result = new StatusType();

		result.setStatusCode(getStatusCode());
		result.setStatusDesc(getStatusDesc(result.getStatusCode()));

		return result;
	}

	private long getStatusCode()
	{
		return SUCCESS_CODE;
	}

	private String getStatusDesc(long statusCode)
	{
		return statusCode == ERROR_CODE ? ERROR_TEXT : "";
	}
}
