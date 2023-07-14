package com.rssl.phizic.gate.employee;

/**
 * @author akrenev
 * @ created 17.07.2014
 * @ $Author$
 * @ $Revision$
 *
 * Информация о персональном менеджере
 */

public interface ManagerInfo
{
	/**
	 * @return имя менеждера
	 */
	public String getName();

	/**
	 * @return адрес электронной почты
	 */
	public String getEmail();

	/**
	 * @return номер телефона
	 */
	public String getPhone();
}
