package com.rssl.phizic.business.accounts;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

/**
 @author Pankin
 @ created 23.11.2010
 @ $Author$
 @ $Revision$
 */
public abstract class AccountsConfig extends Config
{
	public static final String ACCOUNT_NUMBER_MASK = "com.rssl.iccs.accounts.accountnumber.mask";
	public static final String ACCOUNT_NUMBER_REGEXP = "com.rssl.iccs.accounts.accountnumber.regexp";

	protected AccountsConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 *
	 * @return результирующий вид номера счета
	 */
	public abstract String getAccountNumberMask();

	/**
	 *
	 * @return рег. выражение для обработки номера счета
	 */
	public abstract String getAccountNumberRegexp();
}
