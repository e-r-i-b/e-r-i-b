package com.rssl.phizicgate.wsgate.services.einvoicing.requests;

import com.rssl.phizgate.messaging.internalws.server.protocol.InternalMessageBuilder;
import com.rssl.phizic.business.einvoicing.Constants;
import org.w3c.dom.Document;

/**
 * обновление данных по КПУ.
 *
 * @author bogdanov
 * @ created 29.12.14
 * @ $Author$
 * @ $Revision$
 */

public class UpdateEndPointProviderRequestData extends InvoiceRequestDataBase
{
	private final long providerId;
	private final Boolean mcheckoutEnabled;
	private final Boolean eInvoicingEnabled;
	private final Boolean mbCheckEnabled;

	public UpdateEndPointProviderRequestData(long providerId, Boolean eInvoicingEnabled, Boolean mcheckoutEnabled, Boolean mbCheckEnabled)
	{
		super(null);
		this.eInvoicingEnabled = eInvoicingEnabled;
		this.providerId = providerId;
		this.mcheckoutEnabled = mcheckoutEnabled;
		this.mbCheckEnabled = mbCheckEnabled;
	}

	public String getName()
	{
		return "UpdateEndPointProviderRequest";
	}

	public Document getBody() throws Exception
	{
		InternalMessageBuilder builder = createBuilder();

		builder.addParameter(Constants.ID_TAG, providerId);
		if (eInvoicingEnabled != null)
			builder.addParameter(Constants.EINVOICING_ENABLED_TAG, eInvoicingEnabled);

		if (mbCheckEnabled != null)
			builder.addParameter(Constants.MB_CHECK_ENABLED_TAG, mbCheckEnabled);

		if (mcheckoutEnabled != null)
			builder.addParameter(Constants.MOBILE_CHECKOUT_ENABLED_TAG, mcheckoutEnabled);

		return builder.closeTag().toDocument();
	}
}
