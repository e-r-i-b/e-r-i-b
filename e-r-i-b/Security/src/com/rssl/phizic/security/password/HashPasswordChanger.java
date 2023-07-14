package com.rssl.phizic.security.password;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.security.SecurityDbException;

/**
 * @author Kosyakov
 * @ created 12.10.2006
 * @ $Author: lepihina $
 * @ $Revision: 56873 $
 */
public class HashPasswordChanger extends PasswordChangerBase
{
	public HashPasswordChanger (boolean needPasswordChange)
	{
		setNeedChangePassword(needPasswordChange);
		calculateExpireDate();
	}

	public void changePassword ( CommonLogin login, char[] passwordHash, String instanceName ) throws SecurityDbException
	{
		saveNewPassword(login, passwordHash, instanceName);
	}
}
