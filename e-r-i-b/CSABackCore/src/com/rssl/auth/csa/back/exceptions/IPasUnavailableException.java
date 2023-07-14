package com.rssl.auth.csa.back.exceptions;

import com.rssl.auth.csa.back.servises.Connector;

/**
 * @author krenev
 * @ created 04.02.2013
 * @ $Author$
 * @ $Revision$
 * ����������, ��������������� � ������������� ������� iPas
 */
public class IPasUnavailableException extends ServiceUnavailableException
{
	private final Connector connector;

	public IPasUnavailableException(Exception e, Connector connector)
	{
		super(e);
		this.connector = connector;
	}

	/**
	 * @return ���������, � ������ �������� ����������� �������������� � iPas
	 */
	public Connector getConnector()
	{
		return connector;
	}
}
