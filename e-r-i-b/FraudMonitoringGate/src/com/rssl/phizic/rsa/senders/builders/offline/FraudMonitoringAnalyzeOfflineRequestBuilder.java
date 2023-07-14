package com.rssl.phizic.rsa.senders.builders.offline;

import com.rssl.phizic.rsa.integration.ws.control.generated.*;
import com.rssl.phizic.rsa.senders.ClientDefinedFactConstants;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import static com.rssl.phizic.rsa.senders.serializers.support.RSARequestXMLDocumentConstants.*;

/**
 * @author tisov
 * @ created 09.07.15
 * @ $Author$
 * @ $Revision$
 * Билдер аналитических запросов во фрод-мониторинг
 */
public class FraudMonitoringAnalyzeOfflineRequestBuilder extends FraudMonitoringAdaptiveAuthenticationOfflineRequestBuilderBase<AnalyzeRequest>
{
	@Override
	protected AnalyzeRequest createRequest()
	{
		return new AnalyzeRequest();
	}

	private DeviceManagementRequestPayload createDeviceManagementRequest(Node node)
	{
		DeviceManagementRequestPayload result = new DeviceManagementRequestPayload();

		Element elem = (Element) node;

		DeviceData deviceData = new DeviceData();
		deviceData.setBindingType(BindingType.fromValue(XmlHelper.getSimpleElementValue(elem, BINDING_TYPE)));
		result.setDeviceData(deviceData);

		DeviceActionType deviceActionType = (DeviceActionType.fromValue(XmlHelper.getSimpleElementValue(elem, DEVICE_ACTION_TYPES)));
		result.setActionTypeList(new DeviceActionTypeList(new DeviceActionType[]{deviceActionType}));

		return result;
	}

	private ClientDefinedFact[] createClientDefinedAttributes(Node node)
	{
		List<ClientDefinedFact> clientDefinedFactList = new ArrayList<ClientDefinedFact>();

		NodeList attributeNodesList = ((Element)node).getElementsByTagName(ATTRIBUTE);
		for (int i = 0; i < attributeNodesList.getLength(); i++)
		{
			Element attributeElement = (Element)attributeNodesList.item(i);
			ClientDefinedFact attribute = new ClientDefinedFact();
			attribute.setName(XmlHelper.getSimpleElementValue(attributeElement, NAME));
			attribute.setValue(XmlHelper.getSimpleElementValue(attributeElement, VALUE));
			attribute.setDataType(DataType.fromValue(XmlHelper.getSimpleElementValue(attributeElement, DATA_TYPE)));

			if (attribute.getName().equals(ClientDefinedFactConstants.OFFLINE_LOAD_FIELD_NAME))
			{
				attribute.setValue(ClientDefinedFactConstants.YES);
			}

			clientDefinedFactList.add(attribute);
		}

		return clientDefinedFactList.toArray(new ClientDefinedFact[]{});
	}

	protected EventData createEventData(Node node)
	{
		EventData result = new EventData();

		Element elem = (Element) node;

		result.setEventType(EventType.fromValue(XmlHelper.getSimpleElementValue(elem, EVENT_TYPE)));
		result.setClientDefinedEventType(XmlHelper.getSimpleElementValue(elem, CLIENT_DEFINED_EVENT_TYPE));
		result.setEventDescription(XmlHelper.getSimpleElementValue(elem, EVENT_DESCRITPION));
		result.setTimeOfOccurrence(XmlHelper.getSimpleElementValue(elem, TIME_OF_OCCURENCE));

		result.setClientDefinedAttributeList(createClientDefinedAttributes(elem.getElementsByTagName(CLIENT_DEFINED_ATTRIBUTES).item(0)));

		return result;
	}


	@Override
	public AnalyzeRequest build()
	{
		AnalyzeRequest result = super.build();

		result.setDeviceManagementRequest(createDeviceManagementRequest(this.rootNode.getElementsByTagName(DEVICE_MANAGEMENT_REQUEST).item(0)));
		result.setAutoCreateUserFlag(Boolean.valueOf(XmlHelper.getSimpleElementValue(this.rootNode, AUTO_CREATE_USER_FLAG)));
		result.setChannelIndicator(ChannelIndicatorType.fromValue(XmlHelper.getSimpleElementValue(this.rootNode, CHANNEL_INDICATOR)));
		result.setRunRiskType(RunRiskType.fromValue(XmlHelper.getSimpleElementValue(this.rootNode, RUN_RISK_TYPE)));

		result.setEventDataList(new EventData[]{createEventData(this.rootNode.getElementsByTagName(EVENT_DATA).item(0))});

		return result;
	}
}
