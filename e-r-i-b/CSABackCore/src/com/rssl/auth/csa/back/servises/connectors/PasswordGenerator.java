package com.rssl.auth.csa.back.servises.connectors;

/**
 * @author krenev
 * @ created 24.12.2012
 * @ $Author$
 * @ $Revision$
 *
 * генератор паролей
 */
public interface PasswordGenerator
{
	/**
	 * —гененрировать пароль.
	 * @throws Exception
	 */

	public void generatePassword() throws Exception;
}
