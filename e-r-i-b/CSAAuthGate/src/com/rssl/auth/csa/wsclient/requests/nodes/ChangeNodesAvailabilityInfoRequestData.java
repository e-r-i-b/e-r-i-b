package com.rssl.auth.csa.wsclient.requests.nodes;

import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.auth.csa.wsclient.requests.RequestDataBase;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author akrenev
 * @ created 17.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса на изменение информации о состоянии блоков
 */

public class ChangeNodesAvailabilityInfoRequestData extends RequestDataBase
{
	private static final String REQUEST_NAME = "changeNodesAvailabilityInfoRq";

	private final List<NodeInfo> changedNodesAvailabilityInfo;

	/**
	 * конструктор
	 * @param changedNodesAvailabilityInfo новое состояние блоков
	 */
	public ChangeNodesAvailabilityInfoRequestData(Collection<NodeInfo> changedNodesAvailabilityInfo)
	{
		this.changedNodesAvailabilityInfo = new ArrayList<NodeInfo>(changedNodesAvailabilityInfo);
	}

	public String getName()
	{
		return REQUEST_NAME;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		Element nodesElement = request.createElement(RequestConstants.NODES_INFO_NODE_NAME);
		for (NodeInfo info : changedNodesAvailabilityInfo)
		{
			Element nodeElement = request.createElement(RequestConstants.NODE_INFO_NODE_NAME);

			addTag(nodeElement, RequestConstants.NODE_INFO_ID_NODE_NAME,                      info.getId());
			addTag(nodeElement, RequestConstants.NODE_INFO_NAME_NODE_NAME,                    info.getName());
			addTag(nodeElement, RequestConstants.NODE_INFO_NEW_USERS_ALLOWED_NODE_NAME,       info.isNewUsersAllowed());
			addTag(nodeElement, RequestConstants.NODE_INFO_EXISTING_USERS_ALLOWED_NODE_NAME,  info.isExistingUsersAllowed());
			addTag(nodeElement, RequestConstants.NODE_INFO_TEMPORARY_USERS_ALLOWED_NODE_NAME, info.isTemporaryUsersAllowed());
			addTag(nodeElement, RequestConstants.NODE_INFO_USERS_TRANSFER_ALLOWED_NODE_NAME,  info.isUsersTransferAllowed());
			addTag(nodeElement, RequestConstants.NODE_INFO_ADMIN_AVAILABLE_NODE_NAME,         info.isAdminAvailable());
			addTag(nodeElement, RequestConstants.NODE_INFO_GUEST_AVAILABLE_NODE_NAME,         info.isGuestAvailable());
			nodesElement.appendChild(nodeElement);
		}
		root.appendChild(nodesElement);
		return request;
	}

	private void addTag(Element root, String nodeName, Object value)
	{
		root.appendChild(createTag(root.getOwnerDocument(), nodeName, StringHelper.getNullIfNull(value)));
	}
}
