package com.rssl.phizic.einvoicing.multiblock.processors;

import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import static com.rssl.phizic.einvoicing.multiblock.processors.Constants.PROFILE_TAG;

/**
 * @author gladishev
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */
public class LinkOrderToClientRequestProcessor extends EInvoicingRequestProcessorBase
{
	protected String getResponceType()
	{
		return "LinkOrderToClientResponce";
	}

	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Element root = requestInfo.getBody().getDocumentElement();
		service.linkOrderToClient(getOrderUUID(root), ResponseBuilderHelper.parseProfile(XmlHelper.selectSingleNode(root, PROFILE_TAG)));
		return getSuccessResponseBuilder().end().getResponceInfo();
	}
}
