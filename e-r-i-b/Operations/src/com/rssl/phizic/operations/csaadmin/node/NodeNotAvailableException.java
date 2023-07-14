package com.rssl.phizic.operations.csaadmin.node;

import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author akrenev
 * @ created 15.10.2014
 * @ $Author$
 * @ $Revision$
 *
 * ����������, ��������� ���������� � ������������ �����
 */

public class NodeNotAvailableException extends BusinessLogicException
{
	private final NodeInfo nodeInfo;

	/**
	 * �����������
	 * @param node ���������� � ����������� �����
	 */
	public NodeNotAvailableException(NodeInfo node)
	{
		super("���� \"" + node.getName() + "\" �� ��������!");
		nodeInfo = node;
	}

	/**
	 * @return ���������� � ����������� �����
	 */
	public NodeInfo getNodeInfo()
	{
		return nodeInfo;
	}
}
