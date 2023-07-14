package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.phizic.web.auth.Stage;

import java.util.List;

/**
 * @author niculichev
 * @ created 21.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class LoginOperationInfo extends OperationInfoBase
{
	private String login;
	private String password;
	private List<ConnectorInfo> connectorInfos; // множетсво информаций о коннекторах для дополнительной идентификации

	public LoginOperationInfo(Stage stage)
	{
		super(stage);
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public List<ConnectorInfo> getConnectorInfos()
	{
		return connectorInfos;
	}

	public void setConnectorInfos(List<ConnectorInfo> connectorInfos)
	{
		this.connectorInfos = connectorInfos;
	}
}
