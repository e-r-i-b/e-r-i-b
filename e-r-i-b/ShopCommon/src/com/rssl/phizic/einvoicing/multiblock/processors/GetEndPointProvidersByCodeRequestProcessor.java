package com.rssl.phizic.einvoicing.multiblock.processors;

import com.rssl.phizgate.messaging.internalws.server.protocol.RequestInfo;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseBuilder;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;
import com.rssl.phizic.gate.einvoicing.FacilitatorProvider;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Element;

import java.util.List;

/**
 * Обработка сообщения о поиск КПУ.
 *
 * @author bogdanov
 * @ created 29.12.14
 * @ $Author$
 * @ $Revision$
 */

public class GetEndPointProvidersByCodeRequestProcessor extends EInvoicingRequestProcessorBase
{
	@Override
	protected String getResponceType()
	{
		return "GetEndPointProvidersByCodeResponce";
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Element documentElement = requestInfo.getBody().getDocumentElement();
		String facilitatorCode = XmlHelper.getSimpleElementValue(documentElement, Constants.FACILITATOR_CODE_TAG);
		int firstResult = Integer.parseInt(XmlHelper.getSimpleElementValue(documentElement, Constants.FIRST_RESULT_TAG));
		int maxResult = Integer.parseInt(XmlHelper.getSimpleElementValue(documentElement, Constants.MAX_RESULT_TAG));

		List<FacilitatorProvider> providerList = service.findEndPointProviderByCode(facilitatorCode, firstResult, maxResult);

		ResponseBuilder responseBuilder = getSuccessResponseBuilder();
		responseBuilder.openTag(Constants.FACILITATORS_TAG);
		if (CollectionUtils.isNotEmpty(providerList))
		{
			for (FacilitatorProvider provider : providerList)
				ResponseBuilderHelper.addFacilitatorProvider(provider, responseBuilder);
		}

		responseBuilder.closeTag();
		return responseBuilder.end().getResponceInfo();
	}
}
