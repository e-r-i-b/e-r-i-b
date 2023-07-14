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
 * обработка запроса на поиск КПУ по имени.
 *
 * @author bogdanov
 * @ created 29.12.14
 * @ $Author$
 * @ $Revision$
 */

public class GetEndPointProvidersByNameRequestProcessor extends EInvoicingRequestProcessorBase
{
	@Override
	protected String getResponceType()
	{
		return "GetEndPointProvidersByNameResponce";
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Element documentElement = requestInfo.getBody().getDocumentElement();
		String name = XmlHelper.getSimpleElementValue(documentElement, Constants.NAME_TAG);
		String inn = XmlHelper.getSimpleElementValue(documentElement, Constants.INN_TAG);
		int firstResult = Integer.parseInt(XmlHelper.getSimpleElementValue(documentElement, Constants.FIRST_RESULT_TAG));
		int maxResult = Integer.parseInt(XmlHelper.getSimpleElementValue(documentElement, Constants.MAX_RESULT_TAG));

		List<FacilitatorProvider> providerList = service.findEndPointProviderByName(name, inn, firstResult, maxResult);

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
