package com.rssl.phizic.gate.ima;

import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * �� ������ ���
 * @author Pankin
 * @ created 01.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class IMAccountNotFoundException extends GateLogicException
{
	private String imAccountNumber;

	/**
	 *
	 * @param imAccountNumber �����
	 */
	public IMAccountNotFoundException(String imAccountNumber)
	{
		super("������������� ���� � " + imAccountNumber + " �� ������");
		this.imAccountNumber = imAccountNumber;
	}

	public String getIMAccountNumber()
	{
		return imAccountNumber;
	}
}
