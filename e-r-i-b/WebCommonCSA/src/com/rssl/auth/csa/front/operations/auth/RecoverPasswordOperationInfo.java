package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.phizic.web.auth.Stage;

/**
 * Информация об операции восстановления пароля
 * @author niculichev
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class RecoverPasswordOperationInfo extends OperationInfoBase
{
	private ConnectorInfo.Type connectorType;        //тип коннектора
	private String userId;                           //идентификатор пользователя
	private String tb;                               //тербанк
	private String login;                            //логин

	public RecoverPasswordOperationInfo(Stage stage)
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

	public ConnectorInfo.Type getConnectorType()
	{
		return connectorType;
	}

	public void setConnectorType(ConnectorInfo.Type connectorType)
	{
		this.connectorType = connectorType;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getTB()
	{
		return tb;
	}

	public void setTB(String tb)
	{
		this.tb = tb;
	}
}
