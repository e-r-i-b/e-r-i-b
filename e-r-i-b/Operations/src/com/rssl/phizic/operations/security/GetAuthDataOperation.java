package com.rssl.phizic.operations.security;

import com.rssl.phizic.authgate.AuthData;

/**
 * Операция получения данный аутентификации
 *
 * @author khudyakov
 * @ created 28.12.2012
 * @ $Author$
 * @ $Revision$
 */
public interface GetAuthDataOperation
{
	/**
	 * @return данные аутентификации.
	 */
	public AuthData getAuthData();
}
