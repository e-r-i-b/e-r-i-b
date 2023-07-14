package com.rssl.phizic.gate.einvoicing;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Информация о пользователе, имеющему интернет-заказы.
 *
 * @author bogdanov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */

public interface ShopProfile extends Serializable
{
	/**
	* @return Идентификатор (PK)
	*/
	public Long getId();

	/**
	* @return Имя
	*/
	public String getFirstName();

	/**
	* @return Фамилия
	*/
	public String getSurName();

	/**
	* @return Отчество
	*/
	public String getPatrName();

	/**
	* @return ДУЛ
	*/
	public String getPassport();

	/**
	* @return Дата рождения
	*/
	public Calendar getBirthdate();

	/**
	* @return ТБ
	*/
	public String getTb();
}
