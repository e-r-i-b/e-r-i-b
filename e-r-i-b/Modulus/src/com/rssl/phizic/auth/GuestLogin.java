package com.rssl.phizic.auth;

/**
 * Гостеовй логин
 * @author niculichev
 * @ created 27.12.14
 * @ $Author$
 * @ $Revision$
 */
public interface GuestLogin extends Login
{
	/**
	 * @return телефон аутентификации
	 */
	String getAuthPhone();

	/**
	 * @return уникальный код гостя
	 */
	Long getGuestCode();
}
