package com.rssl.auth.csa.wsclient;

import com.rssl.auth.csa.utils.CSAResponseUtils;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ������ ��� ��������� ���������� � ������ �������
 *
 * @author gladishev
 * @ created 08.10.13
 * @ $Author$
 * @ $Revision$
 */
public class NodeInfoConfig extends Config
{
	private Map<Long, NodeInfo> nodesInfo = null;

	public NodeInfoConfig(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh()
	{
		try
		{
			nodesInfo = new HashMap<Long, NodeInfo>();
			List<NodeInfo> nodes = CSAResponseUtils.getNodesInfo();
			for (NodeInfo node : nodes)
				nodesInfo.put(node.getId(), node);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * ���������� ����� ����� �� ������
	 * @param nodeId - ����� �����
	 * @return ����� ����� �� ������
	 */
	public String getNodeHostName(final Long nodeId) throws ConfigurationException
	{
		return nodesInfo.get(nodeId).getHostname();
	}

	/**
	 * ���������� ���������� ������ �����
	 * @return ������ �����
	 */
	public Collection<NodeInfo> getNodes() throws ConfigurationException
	{
		return nodesInfo.values();
	}

	/**
	 * ���������� ���������� ���� �� ��������������
	 * @param nodeId - ����� �����
	 * @return ����
	 */
	public NodeInfo getNode(final Long nodeId) throws ConfigurationException
	{
		return nodesInfo.get(nodeId);
	}
}
