package com.rssl.phizgate.common.services;

import com.rssl.phizic.gate.GateConfiguration;
import com.rssl.phizic.gate.ConnectMode;

/**
 * @author akrenev
 * @ created 22.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class GateConfigurationImpl implements GateConfiguration
{
	private String userName;
	private Long connectionTimeout;
	private ConnectMode connectMode;

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setConnectionTimeout(Long connectionTimeout)
	{
		this.connectionTimeout = connectionTimeout;
	}

	public Long	getConnectionTimeout()
	{
		return connectionTimeout;
	}

	public void setConnectMode(ConnectMode connectMode)
	{
		this.connectMode = connectMode;
	}

	/**
	 *  Нужен для копирования данных в BeanHelper
	 *
	 * */
	public void setConnectMode(String connectMode)
	{
		this.connectMode = ConnectMode.valueOf(connectMode);
	}

	public ConnectMode getConnectMode()
	{
		return connectMode;
	}
}

