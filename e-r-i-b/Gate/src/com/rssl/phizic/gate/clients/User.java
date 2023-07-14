package com.rssl.phizic.gate.clients;

/**
 * ќписание пользовател€ системы. “ут можно описывать как клиента так и сотрудника
 *
 * @author egorova
 * @ created 18.03.2011
 * @ $Author$
 * @ $Revision$
 */

public interface User extends Client
{
	/**
	 *  атегори€ пользовател€ - сотрудник или клиент
	 *
	 * @return котегорию пользовател€
	 */
	UserCategory getCategory();
}
