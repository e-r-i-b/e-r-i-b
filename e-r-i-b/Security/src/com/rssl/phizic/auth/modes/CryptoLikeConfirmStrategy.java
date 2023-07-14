package com.rssl.phizic.auth.modes;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.common.forms.doc.DocumentSignature;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.modes.readers.CryptoConfirmResponseReader;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.security.*;
import com.rssl.phizic.security.config.SecurityFactory;
import com.rssl.phizic.security.crypto.CryptoService;
import com.rssl.phizic.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 21.05.2007
 * @ $Author: erkin $
 * @ $Revision: 48487 $
 */

public class CryptoLikeConfirmStrategy implements ConfirmStrategy
{
	private Exception warning;

	public ConfirmStrategyType getType()
	{
		return ConfirmStrategyType.sbrf_custom;
	}

	public boolean hasSignature()
	{
		return true;
	}

	public ConfirmRequest createRequest(CommonLogin login, ConfirmableObject value, String clientSessionId,PreConfirmObject preConfirm) throws SecurityException, SecurityLogicException
	{
		try
		{
			// данные для подписи
			byte[] bytes = value.getSignableObject();
			String textToSign = StringUtils.toHexString(bytes);

			// случайное число
			byte[] serverRandom = new byte[8];
			SecureRandom random = new SecureRandom();
			random.nextBytes(serverRandom);
			String operationId = StringUtils.toHexString(serverRandom);

			// ID сессии
			String sessionIdString = clientSessionId;
			byte[] sessionId = sessionIdString.getBytes("ASCII");

			// хеш пароля
			String passwordHash = new SecurityService().getPasswordHash(AuthModule.getAuthModule().getPrincipal().getLogin());
			byte[] passwordHashBytes = StringUtils.fromHexString(passwordHash);

			// считаем ключ hash(serverRandom + sessionId + passwordHashBytes)
			CryptoService cryptoService = SecurityFactory.cryptoService();
			byte[] sum = new byte[serverRandom.length + sessionId.length + passwordHashBytes.length];
			System.arraycopy(serverRandom, 0, sum, 0, serverRandom.length);
			System.arraycopy(sessionId, 0, sum, serverRandom.length, sessionId.length);
			System.arraycopy(passwordHashBytes, 0, sum, serverRandom.length + sessionId.length, passwordHashBytes.length);
			byte[] keyBytes = cryptoService.hash(sum);

			return new CryptoLikeConfirmRequest(operationId, sessionIdString, StringUtils.toHexString(keyBytes), textToSign);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new SecurityException(e);
		}
		catch (SecurityDbException e)
		{
			throw new SecurityException(e);
		}
	}

	public ConfirmStrategyResult validate(CommonLogin login, ConfirmRequest request, ConfirmResponse response) throws SecurityLogicException, SecurityException
	{
		CryptoLikeConfirmRequest confirmRequest  = (CryptoLikeConfirmRequest) request;
		CryptoConfirmResponse    confirmResponse = (CryptoConfirmResponse) response;

		String privateKey   = confirmRequest.getPrivateKey();
		String stringToSign = confirmRequest.getStringToSign();

		String receivedSignature = confirmResponse.getSignature();

		byte[] calculatedSignature = GOSTUtils.hmac(StringUtils.fromHexString(privateKey), StringUtils.fromHexString(stringToSign));

		if( !Arrays.equals(calculatedSignature, StringUtils.fromHexString(receivedSignature)) )
			throw new SecurityLogicException("Нарушена целостность сообщения");

		return new ConfirmStrategyResult(false);
	}

	public PreConfirmObject preConfirmActions(CallBackHandler callBackHandler) throws SecurityLogicException, SecurityException
	{
		// ничего делать не надо
		return null;
	}

	/**
	 * Получить SignatureCreator
	 */
	public DocumentSignature createSignature(ConfirmRequest request, SignatureConfirmResponse confirmResponse) throws SecurityLogicException, SecurityException
	{
		return new SbrfSignatureCreator().create(request, confirmResponse);
	}

	public ConfirmResponseReader getConfirmResponseReader()
	{
		return new CryptoConfirmResponseReader();
	}

	public void reset(CommonLogin login, ConfirmableObject confirmableObject) throws SecurityDbException
	{
		//nothing
	}

	public Exception getWarning()
	{
		return warning;
	}

	public void setWarning(Exception warning)
	{
		this.warning = warning;
	}
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	public boolean filter(Map<ConfirmStrategyType, List<StrategyCondition>> conditions, String userChoice, ConfirmableObject object)
	{
		if(conditions!=null)
		{
			if (conditions.get(getType())!=null)
				{
					for (StrategyCondition condition: conditions.get(getType()))
					{
						if (!condition.checkCondition(object))
						{
							if (condition.getWarning()!=null)
								setWarning(new SecurityLogicException(condition.getWarning()));
							return false;
						}
					}
				}
		}
		return true;
	}
}
