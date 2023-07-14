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
	 * @param signature �������
	 */
	public CryptoConfirmResponse(String signature)
	{
		this.signature = signature;
	}

	/**
	 * @return �������
	 */
	public String getSignature()
	{
		return signature;
	}
}