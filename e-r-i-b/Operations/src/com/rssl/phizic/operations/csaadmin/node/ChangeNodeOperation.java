package com.rssl.phizic.operations.csaadmin.node;

import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizicgate.csaadmin.service.authentication.CSAAdminAuthService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Операция смены блока
 */
public class ChangeNodeOperation extends OperationBase
{
	private String nodeUrl;
	private NodeInfo newNode;
	private String action;
	private Map<String,String> parameters = new HashMap<String, String>();

	/**
	 * инициализация операции
	 * @param nodeId идентификатор нового блока
	 * @param action целевой акшен
	 * @param parameters параметры целевого экшена
	 * @throws BusinessException
	 * @throws com.rssl.phizic.operations.csaadmin.node.NodeNotAvailableException -- целевой блок не доступен
	 */
	public void initialize(Long nodeId, String action, Map<String,String> parameters) throws BusinessException, NodeNotAvailableException
	{
		NodeInfoConfig nodeConfig = ConfigFactory.getConfig(NodeInfoConfig.class);
		newNode = nodeConfig.getNode(nodeId);
		if(newNode == null)
			throw new ResourceNotFoundBusinessException("Не найден блок системы с идентификатором  " + nodeId, NodeInfo.class);

		if (!newNode.isAdminAvailable())
			throw new NodeNotAvailableException(newNode);

		this.action = action;
		this.parameters.putAll(parameters);
	}

	/**
	 * Сменить блок
	 * @throws BusinessException
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 */
	public void changeNode() throws BusinessException, BusinessLogicException
	{
		try
		{
			if(ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber().equals(newNode.getId()))
			{
				throw new BusinessLogicException("Переход возможен только во внешний блок");
			}
			nodeUrl = new CSAAdminAuthService().changeEmployeeNode(newNode.getId(),action,parameters);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	/**
	 * @return адресс страницы перехода в новом блоке
	 */
	public String getNodeUrl()
	{
		return nodeUrl;
	}
}
