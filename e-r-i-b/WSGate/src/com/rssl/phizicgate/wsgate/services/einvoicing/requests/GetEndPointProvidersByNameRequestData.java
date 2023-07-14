package com.rssl.phizicgate.wsgate.services.einvoicing.requests;

import com.rssl.phizgate.messaging.internalws.server.protocol.InternalMessageBuilder;
import org.w3c.dom.Document;

import static com.rssl.phizic.business.einvoicing.Constants.*;

/**
 * Запрос поиска КПУ по имени или ИНН.
 *
 * @author bogdanov
 * @ created 29.12.14
 * @ $Author$
 * @ $Revision$
 */

public class GetEndPointProvidersByNameRequestData extends InvoiceRequestDataBase
{
	private final String name;
	private final String inn;
	private final int firstResult;
	private final int maxResult;

	public GetEndPointProvidersByNameRequestData(String name, String inn, int firstResult, int maxResult) {
		super(null);
		this.firstResult = firstResult;
		this.maxResult = maxResult;
		this.name = name;
		this.inn = inn;
	}

	public String getName()
	{
		return "GetEndPointProvidersByNameRequest";
	}

	public Document getBody() throws Exception
	{
		InternalMessageBuilder builder = createBuilder();

		builder.addParameter(NAME_TAG, name);
		builder.addParameter(INN_TAG, inn);
		builder.addParameter(FIRST_RESULT_TAG, firstResult);
		builder.addParameter(MAX_RESULT_TAG, maxResult);

		return builder.closeTag().toDocument();
	}
}
