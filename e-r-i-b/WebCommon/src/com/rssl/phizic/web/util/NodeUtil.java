package com.rssl.phizic.web.util;

import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.Collection;
import java.util.Collections;

/**
 * @author mihaylov
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 */
public class NodeUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * @return Список блоков системы
	 */
	public static Collection<NodeInfo> getNodes()
	{
		try
		{
			NodeInfoConfig nodeConfig = ConfigFactory.getConfig(NodeInfoConfig.class);
			return nodeConfig.getNodes();
		}
		catch (ConfigurationException e)
		{
			log.error("Не удалось получить информацию о блоках системы",e);
			return Collections.emptyList();
		}
	}

	/**
	 * @return информация о текущем блоке
	 */
	public static NodeInfo getCurrentNode()
	{
		try
		{
			return getNodeInfo(ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber());
		}
		catch (ConfigurationException e)
		{
			log.error("Не удалось получить информацию о блоках системы",e);
			return null;
		}
	}

	/**
	 * получить информацию о блоке
	 * @param nodeId идентификатор блока
	 * @return информация
	 */
	public static NodeInfo getNodeInfo(Long nodeId)
	{
		return ConfigFactory.getConfig(NodeInfoConfig.class).getNode(nodeId);
	}
}
