package com.rssl.auth.csa.back.protocol.handlers.nodes;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.RequestProcessorBase;
import com.rssl.auth.csa.back.servises.ActiveRecord;
import com.rssl.auth.csa.back.servises.nodes.Node;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.hibernate.Session;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 17.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запроса на изменение информации о состоянии блоков
 */

public class ChangeNodesAvailabilityInfoRequestProcessor extends RequestProcessorBase
{
	public static final String REQUEST_TYPE = "changeNodesAvailabilityInfoRq";
	public static final String RESPONSE_TYPE = "changeNodesAvailabilityInfoRs";

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override
	protected String getResponceType()
	{
		return RESPONSE_TYPE;
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		final List<Node> destinationList = Node.getAll();
		final Map<Long, Node> sourceMap = getSource(requestInfo);

		ActiveRecord.executeAtomic(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				for (Node destination : destinationList)
				{
					Node source = sourceMap.get(destination.getId());
					destination.setNewUsersAllowed(source.isNewUsersAllowed());
					destination.setExistingUsersAllowed(source.isExistingUsersAllowed());
					destination.setTemporaryUsersAllowed(source.isTemporaryUsersAllowed());
					destination.setUsersTransferAllowed(source.isUsersTransferAllowed());
					destination.setAdminAvailable(source.isAdminAvailable());
					destination.setGuestAvailable(source.isGuestAvailable());
					destination.save();
				}
				return null;
			}
		});
		return getSuccessResponseBuilder().addNodesInfo(destinationList).end().getResponceInfo();
	}

	private static boolean getBoolean(Element element, String name)
	{
		return Boolean.parseBoolean(XmlHelper.getSimpleElementValue(element, name));
	}

	private static String getString(Element element, String name)
	{
		return XmlHelper.getSimpleElementValue(element, name);
	}

	private static Long getLong(Element element, String name)
	{
		return Long.valueOf(XmlHelper.getSimpleElementValue(element, name));
	}

	private Node parseNode(Element element)
	{
		Node node = new Node();
		node.setId(getLong(element, Constants.NODE_INFO_ID_NODE_NAME));
		node.setName(getString(element, Constants.NODE_INFO_NAME_NODE_NAME));
		node.setNewUsersAllowed(getBoolean(element, Constants.NODE_INFO_NEW_USERS_ALLOWED_NODE_NAME));
		node.setExistingUsersAllowed(getBoolean(element, Constants.NODE_INFO_EXISTING_USERS_ALLOWED_NODE_NAME));
		node.setTemporaryUsersAllowed(getBoolean(element, Constants.NODE_INFO_TEMPORARY_USERS_ALLOWED_NODE_NAME));
		node.setUsersTransferAllowed(getBoolean(element, Constants.NODE_INFO_USERS_TRANSFER_ALLOWED_NODE_NAME));
		node.setAdminAvailable(getBoolean(element, Constants.NODE_INFO_ADMIN_AVAILABLE_NODE_NAME));
		node.setGuestAvailable(getBoolean(element, Constants.NODE_INFO_GUEST_AVAILABLE_NODE_NAME));
		return node;
	}

	private Map<Long, Node> getSource(RequestInfo requestInfo) throws Exception
	{
		final Map<Long, Node> result = new HashMap<Long, Node>();

		Element nodeInfoListElement = XmlHelper.selectSingleNode(requestInfo.getBody().getDocumentElement(), Constants.NODES_INFO_NODE_NAME);
		if (nodeInfoListElement != null)
		{
			XmlHelper.foreach(nodeInfoListElement, Constants.NODE_INFO_NODE_NAME, new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					Node node = parseNode(element);
					result.put(node.getId(), node);
				}
			});
		}
		return result;
	}
}
