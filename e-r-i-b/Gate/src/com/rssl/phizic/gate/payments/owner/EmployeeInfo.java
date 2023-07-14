package com.rssl.phizic.gate.payments.owner;

import com.rssl.phizic.gate.dictionaries.officies.Office;

/**
 * @author osminin
 * @ created 21.12.2012
 * @ $Author$
 * @ $Revision$
 * Информация о сотруднике
 */
public interface EmployeeInfo
{
	/**
	 * @return внешний идентификатор
	 */
	String getGuid();

	/**
	 * @return логин сотрудника
	 */
	String getLogin();

	/**
	 * @return ФИО сотрудника
	 */
	PersonName getPersonName();

	/**
	 * @return офис сотрудника
	 */
	Office getEmployeeOffice();
}
