package com.rssl.auth.csa.wsclient.exceptions;

import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;

/**
 * @author krenev
 * @ created 04.02.2013
 * @ $Author$
 * @ $Revision$
 * ����������, ��������������� � ������������� ������� iPas. 
 */
public class IPasUnavailableException extends BackLogicException
{
	private final ConnectorInfo connectorInfo;

	public IPasUnavailableException(ConnectorInfo connectorInfo)
	{

		this.connectorInfo = connectorInfo;
	}

	/**
	 * @return ���������� � ����������, � ������ �������� ����������� �������������� � iPas
	 */
	public ConnectorInfo getConnectorInfo()
	{
		return connectorInfo;
	}
}
