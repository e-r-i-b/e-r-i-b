package com.rssl.phizic.rsa.senders.serializers;

import com.rssl.phizic.rsa.integration.ws.notification.generated.UpdateActivityRequestType;
import com.rssl.phizic.rsa.senders.enumeration.FraudMonitoringRequestType;
import org.w3c.dom.Node;

import static com.rssl.phizic.rsa.senders.serializers.support.RSARequestXMLDocumentConstants.*;

/**
 * @author tisov
 * @ created 07.07.15
 * @ $Author$
 * @ $Revision$
 * Сериализатор запроса UpdateActivity в ActivityEngine
 */
public class FraudMonitoringUpdateActivitySerializer extends FraudMonitoringRequestSerializerBase<UpdateActivityRequestType>
{
	@Override
	protected void fillRootNode(Node rootNode)
	{
		UpdateActivityRequestType request = this.getRequest();

		rootNode.appendChild(createSimpleTag(EVENT_ID, request.getEventId()));
		rootNode.appendChild(createSimpleTag(OPERATOR_NAME, request.getOperatorUserName()));
		rootNode.appendChild(createSimpleTag(DESCRIPTION, request.getDescription()));
		rootNode.appendChild(createSimpleTag(RESOLUTION, request.getResolution().getValue()));
		rootNode.appendChild(createSimpleTag(LOGIN, request.getLogin()));
		rootNode.appendChild(createSimpleTag(PASSWORD, request.getPassword()));

	}

	@Override
	protected FraudMonitoringRequestType getRequestType()
	{
		return FraudMonitoringRequestType.UPDATE_ACTIVITY;
	}
}
