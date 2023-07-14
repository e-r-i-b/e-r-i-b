package com.rssl.phizic.auth.modes;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.common.forms.doc.DocumentSignature;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.readers.CryptoConfirmResponseReader;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.certification.CertificateOwnService;
import com.rssl.phizic.security.crypto.*;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 02.01.2007
 * @ $Author: rtishcheva $
 * @ $Revision: 51671 $
 */

@Deprecated
//метод validate не реализован с учетом использования certId
public class CryptoConfirmStrategy implements ConfirmStrategy
{
	private CertificateOwnService certificateOwnService = new CertificateOwnService();
	private Exception warning;
	public ConfirmStrategyType getType()
	{
		return ConfirmStrategyType.crypto;
	}

	public boolean hasSignature()
	{
		return true;
	}

	public ConfirmRequest createRequest(CommonLogin login, ConfirmableObject value, String sessionId,PreConfirmObject preConfirm) throws SecurityException, SecurityLogicException
	{
		Certificate certificate;

		try
		{
			certificate = certificateOwnService.findActive(login);
		}
		catch (SecurityDbException e)
		{
			throw new SecurityException(e);
		}

		if(certificate == null)
		{
			return new ErrorConfirmRequest(ConfirmStrategyType.crypto, "Не установлен активный сертификат");
		}

		String str;

		try
		{
			byte[] arr = value.getSignableObject();
			byte[] bytes = Base64.encodeBase64(arr);
			str = new String(bytes, 0, bytes.length, "ASCII");
		}
		catch (UnsupportedEncodingException e)
		{
			throw new SecurityException(e);
		}

		return new CryptoConfirmRequest(certificate, str);
	}

	public ConfirmStrategyResult validate(CommonLogin login, ConfirmRequest request, ConfirmResponse response) throws SecurityException, SecurityLogicException
	{
		throw new UnsupportedOperationException("не умею работать с certId");
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
		return (new SimpleSignatureCreator()).create(request, confirmResponse);
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
		if (conditions!=null)
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
