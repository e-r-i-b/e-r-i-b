package com.rssl.phizic.auth.modes;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.utils.store.StoreManager;

import java.security.SecureRandom;

/**
 * @author Gainanov
 * @ created 14.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class CryptoLikeCSAConfirmStrategy extends CryptoLikeConfirmStrategy
{
	private static final String ASP_KEY_KEY = "ASP_KEY";

	public ConfirmRequest createRequest(CommonLogin login, ConfirmableObject value, String clientSessionId,PreConfirmObject preConfirm) throws SecurityException, SecurityLogicException
	{
		// данные для подписи
		byte[] bytes = value.getSignableObject();
		String textToSign = StringUtils.toHexString(bytes);

		// случайное число
		byte[] serverRandom = new byte[8];
		SecureRandom random = new SecureRandom();
		random.nextBytes(serverRandom);
		String operationId = StringUtils.toHexString(serverRandom);

		String ASPKey = (String) StoreManager.getCurrentStore().restore(ASP_KEY_KEY);
		byte[] hex = StringUtils.fromHexString(ASPKey);
		return new CryptoLikeConfirmRequest(operationId, clientSessionId, StringUtils.toHexString(hex), textToSign);
	}
}
