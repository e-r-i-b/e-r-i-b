package com.rssl.phizic.security.crypto;

import java.util.Date;

/**
 * ��������� ��������.
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
	 * @return ��������� �������� true == ������� ������������� ������
	 */
	public boolean isSuccessful()
	{
		return isSuccessful;
	}

	/**
	 * @return ���������� � ������� �������� ���� ��� ���������.
	 */
	public Certificate getCertificate()
	{
		return certificate;
	}

	/**
	 * @return ����� ��������� �� (GMT)
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
