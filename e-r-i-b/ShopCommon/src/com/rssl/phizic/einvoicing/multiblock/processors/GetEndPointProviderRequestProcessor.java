package com.rssl.phizic.einvoicing.multiblock.processors;

import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseBuilder;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;
import com.rssl.phizic.einvoicing.exceptions.ProviderNotFoundException;
import com.rssl.phizic.gate.einvoicing.FacilitatorProvider;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Element;

import java.util.List;

/**
 * обработка запроса на получение КПУ по имени.
 *
 * @author bogdanov
 * @ created 29.12.14
 * @ $Author$
 * @ $Revision$
 */

public class GetEndPointProviderRequestProcessor extends EInvoicingRequestProcessorBase
{
	@Override
	protected String getResponceType()
	{
		return "GetEndPointProviderResponce";
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Element documentElement = requestInfo.getBody().getDocumentElement();
		long providerId = Long.parseLong(XmlHelper.getSimpleElementValue(documentElement, Constants.ID_TAG));

		FacilitatorProvider provider = service.getEndPointProvider(providerId);

		ResponseBuilder responseBuilder = getSuccessResponseBuilder();
		if (provider == null)
			throw new ProviderNotFoundException();

		ResponseBuilderHelper.addFacilitatorProvider(provider, responseBuilder);
		return responseBuilder.end().getResponceInfo();
	}
}
