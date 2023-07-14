package com.rssl.phizic.security.crypto;

import java.util.Date;

/**
 * Результат проверки.
 *
 * @author Evgrafov
 * @ created 21.12.2006
 * @ $Author: mescheryakova $
 * @ $Revision: 22429 $
 */

public class CheckSignatureResult
{
	private boolean isSuccessful;

	private Certificate certificate;

	private Date signTime;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @return результат проверки true == подпись соответствует данным
	 */
	public boolean isSuccessful()
	{
		return isSuccessful;
	}

	/**
	 * @return сертификат с помощью которого было все подписано.
	 */
	public Certificate getCertificate()
	{
		return certificate;
	}

	/**
	 * @return когда подписано по (GMT)
	 */
	public Date getSignTime()
	{
		return signTime;
	}

	public void setSuccessful(boolean isSuccessful)
	{
		this.isSuccessful = isSuccessful;
	}

	public void setCertificate(Certificate certificate)
	{
		this.certificate = certificate;
	}

	public void setSignTime(Date signTime)
	{
		this.signTime = signTime;
	}
}
