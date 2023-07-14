package com.rssl.phizic.csaadmin.operation;

import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.AdminLogicException;
import com.rssl.phizic.csaadmin.business.session.Session;

import java.util.Calendar;

/**
 * @author lepihina
 * @ created 20.12.13
 * @ $Author$
 * @ $Revision$
 */
public abstract class OpenSessionOperationBase extends AuthenticationOperationBase
{
	/**
	 * Открыть сессию пользователя
	 * @return сессия
	 * @throws com.rssl.phizic.csaadmin.business.common.AdminException
	 */
	public Session openSession() throws AdminLogicException, AdminException
	{
		session = new Session(login);
		sessionService.save(session);
		NodeInfoConfig nodeConfig = ConfigFactory.getConfig(NodeInfoConfig.class);
		currentNode = nodeConfig.getNode(login.getNodeId());
		if (currentNode != null && currentNode.isAdminAvailable())
			return session;

		for (NodeInfo nodeInfo : nodeConfig.getNodes())
		{
			currentNode = nodeInfo;
			if (currentNode.isAdminAvailable())
			{
				login.setLastLogonDate(Calendar.getInstance());
				loginService.changeNode(login, currentNode.getId());
				return session;
			}
		}
		throw new AdminLogicException("Не найдено доступного блока.");
	}
}
