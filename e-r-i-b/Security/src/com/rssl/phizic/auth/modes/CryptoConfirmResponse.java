package com.rssl.phizic.auth.modes;

/**
 * @author Evgrafov
 * @ created 02.01.2007
 * @ $Author: emakarov $
 * @ $Revision: 9379 $
 */

public class CryptoConfirmResponse implements SignatureConfirmResponse
{
	private String signature;

	/**
	 * @param signature подпись
	 */
	public CryptoConfirmResponse(String signature)
	{
		this.signature = signature;
	}

	/**
	 * @return подпись
	 */
	public String getSignature()
	{
		return signature;
	}
}