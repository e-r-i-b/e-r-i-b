package com.rssl.phizic.auth.modes;

import com.rssl.common.forms.doc.DocumentSignature;

import java.util.Calendar;

/**
 * @author Omeliyanchuk
 * @ created 22.04.2008
 * @ $Author$
 * @ $Revision$
 */

public class SbrfSignatureCreator implements SignatureCreator
{
	private final static String KEY_DELIMETER = ":";

	public DocumentSignature create(ConfirmRequest request, SignatureConfirmResponse response)
	{
		if( !(request instanceof CryptoLikeConfirmRequest) )
		{
			throw new RuntimeException("Невозможно создать подпись: Тип запроса должен быть CryptoLikeConfirmRequest.");
		}
		CryptoLikeConfirmRequest cryptoRequest = (CryptoLikeConfirmRequest)request;

		DocumentSignatureImpl signature = new DocumentSignatureImpl();
		StringBuffer buffer = new StringBuffer(3);
		buffer.append(cryptoRequest.getPrivateKey());
		buffer.append(KEY_DELIMETER);
		buffer.append(response.getSignature());

		signature.setOperationId(cryptoRequest.getOperationId());
		signature.setSessionId(cryptoRequest.getSessionId());
		signature.setCheckDate(Calendar.getInstance());
		signature.setText( buffer.toString() );
		return signature;
	}
}