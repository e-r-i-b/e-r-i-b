package com.rssl.phizic.auth.modes;

/**
 * @author Omeliyanchuk
 * @ created 22.04.2008
 * @ $Author$
 * @ $Revision$
 */

public interface SignatureConfirmResponse extends ConfirmResponse
{
	/**
	 * @return подпись
	 */
	String getSignature();
}
