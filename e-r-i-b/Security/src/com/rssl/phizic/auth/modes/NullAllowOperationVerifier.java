package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.CommonLogin;

/**
 * User: Moshenko
 * Date: 01.06.12
 * Time: 16:43
 */
public class NullAllowOperationVerifier implements AllowOperationVerifier
{
	public boolean isAllow(String operationClassName, CommonLogin login)
	{
		return true;
	}
	public static final AllowOperationVerifier INSTANCE = new NullAllowOperationVerifier();
}
