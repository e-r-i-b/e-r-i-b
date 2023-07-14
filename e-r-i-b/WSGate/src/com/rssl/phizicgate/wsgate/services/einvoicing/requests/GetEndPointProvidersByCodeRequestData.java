package com.rssl.phizicgate.wsgate.services.einvoicing.requests;

import com.rssl.phizgate.messaging.internalws.server.protocol.InternalMessageBuilder;
import org.w3c.dom.Document;

import static com.rssl.phizic.business.einvoicing.Constants.*;

/**
 * Запрос на получение КПУ по коду.
 *
 * @author bogdanov
 * @ created 29.12.14
 * @ $Author$
 * @ $Revision$
 */

public class GetEndPointProvidersByCodeRequestData extends InvoiceRequestDataBase
{
	private final String facilitatorCode;
	private final int firstResult;
	private final int maxResult;

	public GetEndPointProvidersByCodeRequestData(String facilitatorCode, int firstResult, int maxResult)
	{
		super(null);
		this.facilitatorCode = facilitatorCode;
		this.maxResult = maxResult;
		this.firstResult = firstResult;
	}

	public String getName()
	{
		return "GetEndPointProvidersByCodeRequest";
	}

	public Document getBody() throws Exception
	{
		InternalMessageBuilder builder = createBuilder();

		builder.addParameter(FACILITATOR_CODE_TAG, facilitatorCode);
		builder.addParameter(MAX_RESULT_TAG, maxResult);
		builder.addParameter(FIRST_RESULT_TAG, firstResult);

		return builder.closeTag().toDocument();
	}
}
