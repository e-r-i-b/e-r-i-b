package com.rssl.phizic.security.password;

import com.rssl.phizic.security.GOSTUtils;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.SecurityService;

/**
 * Версия для СБРФ
 * @author Evgrafov
 * @ created 16.02.2007
 * @ $Author: Roshka $
 * @ $Revision: 3960 $
 */
public class WierdUserPasswordValidator extends UserPasswordValidator
{
	private String clientRnd;
	private String serverRnd;
	private boolean init = false;

	public WierdUserPasswordValidator()
	{
		super();
	}

	public WierdUserPasswordValidator(String scope)
	{
		super(scope);
	}

	/**
	 * @param userPassword пароль из базы
	 * @return true == проверки прошли успешно
	 */
	protected boolean checkValidity(UserPassword userPassword, char[] password)
	{
		if(!init)
			throw new RuntimeException("Валидатор паролей не проинициализирован.");

		if(!userPassword.isValid())
			return false;

		String recievedHmac = new String(password);

		byte[] passwordHash = StringUtils.fromHexString(String.valueOf(userPassword.getValue()));
		byte[] dataBytes = StringUtils.fromHexString(serverRnd + clientRnd);
        byte[] hmacBytes = GOSTUtils.hmac(passwordHash, dataBytes);

		String hmacHex = StringUtils.toHexString(hmacBytes);

		return userPassword.isValid() && hmacHex.equals(recievedHmac);
	}

	public void initialize(String clientRnd, String serverRnd)
	{
		this.clientRnd = clientRnd;
		this.serverRnd = serverRnd;
		init = true;
	}
}