package com.rssl.phizic.business.einvoicing.processors;

import com.rssl.phizgate.einvoicing.Constants;
import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.handlers.RequestProcessorBase;
import com.rssl.phizic.business.shop.ShopHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

/**
 * @author gladishev
 * @ created 07.03.14
 * @ $Author$
 * @ $Revision$
 */
public class SendTicketsRequestProcessor extends RequestProcessorBase
{
	@Override
	protected String getResponceType()
	{
		return "SendTicketsResponce";
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Element element = requestInfo.getBody().getDocumentElement();
		String uuid = XmlHelper.getSimpleElementValue(element, Constants.ORDER_UUID_TAG);
		String info = XmlHelper.getSimpleElementValue(element, Constants.TICKETS_INFO_TAG);
		ShopHelper.get().setTicketsInfo(uuid, info);

		return getSuccessResponseBuilder().end().getResponceInfo();
	}
}
