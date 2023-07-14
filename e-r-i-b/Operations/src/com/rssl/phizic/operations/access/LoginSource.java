package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.CommonLogin;

/**
 * @author Evgrafov
 * @ created 17.01.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 650 $
 */

public interface LoginSource
{
	CommonLogin getLogin();
}
