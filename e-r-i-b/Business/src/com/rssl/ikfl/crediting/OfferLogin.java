package com.rssl.ikfl.crediting;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Erkin
 * @ created 26.12.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Таблица входов.
 * Здесь по каждому клиенту регистрируется факт запроса и приёмки предодобренных предложений.
 */
@PlainOldJavaObject
@SuppressWarnings("PackageVisibleField")
class OfferLogin implements Serializable
{
	/**
	 * Идентификатор входа [PK]
	 */
	Long id;

	/**
	 * Фамилия
	 */
	String surName;

	/**
	 * Имя
	 */
	String firstName;

	/**
	 * Отчество
	 */
	String patrName;

	/**
	 * День рождения
	 */
	Calendar birthDay;

	/**
	 * Серия документа
	 */
	String docSeries;

	/**
	 * Номер документа
	 */
	String docNumber;

	/**
	 * Код тербанка
 	 */
	String tb;

	/**
	 * UID последнего запроса в CRM [1]
	 */
	String lastRqUID;

	/**
	 * Дата+Время последнего запроса в CRM [1]
	 */
	Calendar lastRqTime;

	/**
	 * Статус выполнения последнего запроса в CRM [1]
	 */
	OfferRequestStatus lastRqStatus;
}
