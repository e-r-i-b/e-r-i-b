package com.rssl.auth.csa.wsclient.exceptions;

import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;

import java.util.List;

/**
 * Исключение, возникающее при дополнительном требовании актуализации информации о входе
 * @author niculichev
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class TooManyConnectorsException extends BackLogicException
{
	final private List<ConnectorInfo> connectorInfos;
	final private String ouid;

	public TooManyConnectorsException(String ouid, List<ConnectorInfo> connectorInfos)
	{
		this.ouid = ouid;
		this.connectorInfos = connectorInfos;
	}

	/**
	 * @return список найденных коннекторов
	 */
	public List<ConnectorInfo> getConnectorInfos()
	{
		return connectorInfos;
	}

	public String getOuid()
	{
		return ouid;
	}
}
