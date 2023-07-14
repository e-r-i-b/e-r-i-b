package com.rssl.phizic.security;

/**
 * Статус регистрации клиента в ЦСА.
 *
 * @author bogdanov
 * @ created 29.04.2013
 * @ $Author$
 * @ $Revision$
 */

public enum RegistrationStatus
{
	/**
	 * Клиенту не доступна самостоятельная регистрация.
	 */
	OFF,
	/**
	 * У клиента есть регистрация в ЦСА. Случаи: ЦСА клиент или iPas клиент, который имеет регистрацию в ЦСА, но зашел под iPas логином. 
	 */
	EXIST,
	/**
	 * У клиента нет регистрации в ЦСА, т.е. iPas клиент.
	 */
	NOT_EXIST;
}
