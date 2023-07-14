package com.rssl.phizic.rsa.senders.serializers;

import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.EventData;
import com.rssl.phizic.rsa.integration.ws.control.generated.NotifyRequest;
import org.w3c.dom.Node;

import static com.rssl.phizic.rsa.senders.serializers.support.RSARequestXMLDocumentConstants.*;

/**
 * @author tisov
 * @ created 08.07.15
 * @ $Author$
 * @ $Revision$
 * Базовый класс сериализации оповестительных запросов в систему фрод-мониторинга
 */
public abstract class FraudMonitoringNotifyRequestSerializerBase extends FraudMonitoringCommonRequestSerializerBase<NotifyRequest>
{
	@Override
	protected String getAutoCreateUserFlag()
	{
		return getRequest().getAutoCreateUserFlag().toString();
	}

	@Override
	protected String getChannelIndicator()
	{
		return getRequest().getChannelIndicator().getValue();
	}

	@Override
	protected String getRunRiskType()
	{
		return null;
	}

	@Override
	protected String getDeviceActionTypes()
	{
		return getRequest().getDeviceManagementRequest().getActionTypeList().getDeviceActionTypes(0).getValue();
	}

	@Override
	protected String getBindingTypeTag()
	{
		return getRequest().getDeviceManagementRequest().getDeviceData().getBindingType().getValue();
	}

	@Override
	protected Node createEventDataTag()
	{
		Node result = createElement(EVENT_DATA);

		EventData eventData = getRequest().getEventDataList()[0];

		result.appendChild(createSimpleTag(EVENT_TYPE, eventData.getEventType().getValue()));
		appendSimpleTagIfNotNull(result, CLIENT_DEFINED_EVENT_TYPE, eventData.getClientDefinedEventType());
		result.appendChild(createSimpleTag(EVENT_DESCRITPION, eventData.getEventDescription()));
		result.appendChild(createSimpleTag(TIME_OF_OCCURENCE, eventData.getTimeOfOccurrence()));

		result.appendChild(createClientDefinedAttributesTag(eventData.getClientDefinedAttributeList()));

		return result;
	}

	protected Node createClientDefinedAttributesTag(ClientDefinedFact[] clientDefinedFacts)
	{
		Node result = createElement(CLIENT_DEFINED_ATTRIBUTES);

		for (int i = 0; i < clientDefinedFacts.length; i++)
		{
			Node attribute = createElement(ATTRIBUTE);
			ClientDefinedFact fact = clientDefinedFacts[i];

			attribute.appendChild(createSimpleTag(NAME, fact.getName()));
			attribute.appendChild(createSimpleTag(VALUE, fact.getValue()));
			attribute.appendChild(createSimpleTag(DATA_TYPE, fact.getDataType().getValue()));

			result.appendChild(attribute);
		}

		return result;
	}
}
