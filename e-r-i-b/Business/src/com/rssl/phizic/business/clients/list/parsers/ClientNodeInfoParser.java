package com.rssl.phizic.business.clients.list.parsers;

import com.rssl.phizic.business.clients.list.ClientNodeInfo;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 15.10.2014
 * @ $Author$
 * @ $Revision$
 *
 * Парсер информации о блоке клиента
 */

public class ClientNodeInfoParser extends ParserBase
{
	private static final String NODE_INFO_ID_NODE_NAME = "nodeId";
	private static final String PROFILE_NODE_STATE_TAG = "profileNodeState";
	private static final String PROFILE_NODE_TYPE_TAG = "profileNodeType";

	private List<ClientNodeInfo> nodes = new ArrayList<ClientNodeInfo>();

	/**
	 * @return информация о блоках
	 */
	public List<ClientNodeInfo> getNodes()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return nodes;
	}

	public void execute(Element element) throws Exception
	{
		ClientNodeInfo node = new ClientNodeInfo();
		node.setId(getLongValue(element, NODE_INFO_ID_NODE_NAME));
		node.setType(getEnumTypeValue(element, PROFILE_NODE_TYPE_TAG, ClientNodeInfo.Type.class));
		node.setState(getEnumTypeValue(element, PROFILE_NODE_STATE_TAG, ClientNodeInfo.State.class));
		nodes.add(node);
	}
}
