package com.rssl.phizic.csaadmin.security;

import com.rssl.phizic.csaadmin.business.login.Login;
import com.rssl.phizic.security.GOSTUtils;
import com.rssl.phizic.utils.StringUtils;

/**
 * @author mihaylov
 * @ created 13.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ¬алидатор парол€ сотрудника
 */
public class EmployeePasswordValidator
{
	private String clientRnd;
	private String serverRnd;

	/**
	 * »нициализаровать валидатор
	 * @param clientRnd - случайное значение сгенерированное на клиенте
	 * @param serverRnd - случайное значение сгенерированное на сервере
	 */
	public void initialize(String clientRnd, String serverRnd)
	{
		this.clientRnd = clientRnd;
		this.serverRnd = serverRnd;
	}

	/**
	 * ѕроверить валидность парол€
	 * @param login - логин, дл€ которого провер€ем пароль
	 * @param password - хеш парол€
	 * @return true/false
	 */
	public boolean checkValidity(Login login, String password)
	{
		byte[] passwordHash = StringUtils.fromHexString(String.valueOf(login.getPassword()));
		byte[] dataBytes = StringUtils.fromHexString(serverRnd + clientRnd);
        byte[] hmacBytes = GOSTUtils.hmac(passwordHash, dataBytes);

		String hmacHex = StringUtils.toHexString(hmacBytes);

		return hmacHex.equals(password);
	}

}
