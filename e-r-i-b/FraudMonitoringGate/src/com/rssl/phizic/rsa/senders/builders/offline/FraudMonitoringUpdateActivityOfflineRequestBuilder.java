package com.rssl.phizic.rsa.senders.builders.offline;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.notification.generated.ResolutionTypeList;
import com.rssl.phizic.rsa.integration.ws.notification.generated.UpdateActivityRequestType;
import com.rssl.phizic.utils.xml.XmlHelper;

import static com.rssl.phizic.rsa.senders.serializers.support.RSARequestXMLDocumentConstants.*;

/**
 * @author tisov
 * @ created 09.07.15
 * @ $Author$
 * @ $Revision$
 * Билдер оффлайн-запроса UpdateActivity для ActivityEngine
 */
public class FraudMonitoringUpdateActivityOfflineRequestBuilder extends FraudMonitoringOfflineRequestBuilderBase<UpdateActivityRequestType>
{
	public UpdateActivityRequestType build() throws GateLogicException, GateException
	{
		UpdateActivityRequestType result = new UpdateActivityRequestType();

		result.setEventId(XmlHelper.getSimpleElementValue(this.rootNode, EVENT_ID));
		result.setDescription(XmlHelper.getSimpleElementValue(this.rootNode, DESCRIPTION));
		result.setResolution(ResolutionTypeList.fromValue(XmlHelper.getSimpleElementValue(this.rootNode, RESOLUTION)));
		result.setOperatorUserName(XmlHelper.getSimpleElementValue(this.rootNode, OPERATOR_NAME));
		result.setLogin(XmlHelper.getSimpleElementValue(this.rootNode, LOGIN));
		result.setPassword(XmlHelper.getSimpleElementValue(this.rootNode, PASSWORD));

		return result;
	}
}
