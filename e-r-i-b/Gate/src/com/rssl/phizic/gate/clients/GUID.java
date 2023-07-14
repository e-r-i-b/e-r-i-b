package com.rssl.phizic.gate.clients;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Интерфейс внешнего идентификатора клиента (ФИО+ДУЛ+ДР+ТБ)
 *
 * @author khudyakov
 * @ created 17.06.14
 * @ $Author$
 * @ $Revision$
 */
public interface GUID extends Serializable
{
	/**
	 * @return фамилия
	 */
	String getSurName();

	/**
	 * @return имя
	 */
	String getFirstName();

	/**
	 * @return отчество
	 */
	String getPatrName();

	/**
	 * @return паспорт
	 */
	String getPassport();

	/**
	 * @return дата рождения
	 */
	Calendar getBirthDay();

	/**
	 * @return тербанк департамента обслуживания
	 */
	String getTb();
}
