package com.rssl.phizic.einvoicing.multiblock.processors;

import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseBuilder;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;
import com.rssl.phizic.einvoicing.exceptions.ProviderNotFoundException;
import com.rssl.phizic.gate.einvoicing.FacilitatorProvider;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.BooleanUtils;
import org.w3c.dom.Element;

/**
 * обработка запроса на обновление КПУ.
 *
 * @author bogdanov
 * @ created 29.12.14
 * @ $Author$
 * @ $Revision$
 */

public class UpdateEndPointProviderRequestProcessor extends EInvoicingRequestProcessorBase
{
	@Override
	protected String getResponceType()
	{
		return "UpdateEndPointProviderResponce";
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Element documentElement = requestInfo.getBody().getDocumentElement();
		long providerId = Long.parseLong(XmlHelper.getSimpleElementValue(documentElement, Constants.ID_TAG));
		Boolean eInvoicingEnabled = BooleanUtils.toBooleanObject(XmlHelper.getSimpleElementValue(documentElement, Constants.EINVOICING_ENABLED_TAG));
		Boolean mcheckoutEnabled = BooleanUtils.toBooleanObject(XmlHelper.getSimpleElementValue(documentElement, Constants.MOBILE_CHECKOUT_ENABLED_TAG));
		Boolean mbCheckEnabled = BooleanUtils.toBooleanObject(XmlHelper.getSimpleElementValue(documentElement, Constants.MB_CHECK_ENABLED_TAG));

		service.updateEndPointProvider(providerId, mcheckoutEnabled, eInvoicingEnabled, mbCheckEnabled);

		ResponseBuilder responseBuilder = getSuccessResponseBuilder();
		return responseBuilder.end().getResponceInfo();
	}
}
