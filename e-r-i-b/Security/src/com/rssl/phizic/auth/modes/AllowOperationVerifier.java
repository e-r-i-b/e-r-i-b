package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.CommonLogin;

import java.io.Serializable;

/**
 * User: Moshenko
 * Date: 01.06.12
 * Time: 14:17
 * Условие на разрешение опперации(allow-operation), в client-authentication-modes
 */
public interface AllowOperationVerifier extends Serializable
{
	/**
	 *
	 * @param operationClassName имя класса операции.
	 * @param login
	 * @return загружать ли операцию
	 */
	boolean isAllow(String operationClassName, CommonLogin login);
}
