package com.rssl.phizic.security.password;

import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.auth.CommonLogin;

/**
 * @author Kidyaev
 * @ created 23.12.2005
 * @ $Author$
 * @ $Revision$
 */
public interface PasswordChanger
{
	void changePassword(CommonLogin login, char[] newPassword, String instanceName) throws SecurityDbException;
	void changePassword(CommonLogin login, char[] newPassword) throws SecurityDbException;
}
