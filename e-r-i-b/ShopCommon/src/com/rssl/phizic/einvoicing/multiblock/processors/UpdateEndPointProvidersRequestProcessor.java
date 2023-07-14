package com.rssl.phizic.einvoicing.multiblock.processors;

import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseBuilder;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.BooleanUtils;
import org.w3c.dom.Element;

/**
 * обработка запроса на обновление свойств всех КПУ для фасилитатора.
 *
 * @author gladishev
 * @ created 30.01.2015
 * @ $Author$
 * @ $Revision$
 */

public class UpdateEndPointProvidersRequestProcessor extends EInvoicingRequestProcessorBase
{
	@Override
	protected String getResponceType()
	{
		return "UpdateEndPointProvidersResponce";
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Element documentElement = requestInfo.getBody().getDocumentElement();
		String facilitatorCode = XmlHelper.getSimpleElementValue(documentElement, Constants.PROVIDER_CODE_TAG);
		Boolean eInvoicingEnabled = BooleanUtils.toBooleanObject(XmlHelper.getSimpleElementValue(documentElement, Constants.EINVOICING_ENABLED_TAG));
		Boolean mcheckoutEnabled = BooleanUtils.toBooleanObject(XmlHelper.getSimpleElementValue(documentElement, Constants.MOBILE_CHECKOUT_ENABLED_TAG));
		Boolean mbCheckEnabled = BooleanUtils.toBooleanObject(XmlHelper.getSimpleElementValue(documentElement, Constants.MB_CHECK_ENABLED_TAG));

		service.updateEndPointProviders(facilitatorCode, mcheckoutEnabled, eInvoicingEnabled, mbCheckEnabled);

		ResponseBuilder responseBuilder = getSuccessResponseBuilder();
		return responseBuilder.end().getResponceInfo();
	}
}
