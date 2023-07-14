package com.rssl.phizic.gate.payments.owner;

/**
 * @author osminin
 * @ created 21.12.2012
 * @ $Author$
 * @ $Revision$
 *  ФИО персоны
 */
public interface PersonName
{
	/**
	 * @return Фамилия
	 */
	String getLastName();

	/**
	 * @return Имя
	 */
	String getFirstName();

	/**
	 * @return Отчество
	 */
	String getMiddleName();

	/**
	 * @return ФИО
	 */
	String getFullName();
}
