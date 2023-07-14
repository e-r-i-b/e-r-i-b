package com.rssl.phizicgate.wsgate.services.einvoicing.requests;

import com.rssl.phizgate.messaging.internalws.server.protocol.InternalMessageBuilder;
import com.rssl.phizic.business.einvoicing.Constants;
import org.w3c.dom.Document;

/**
 * ������ �� ���������� ������� ���� ��� ������������
 * @author gladishev
 * @ created 30.01.2015
 * @ $Author$
 * @ $Revision$
 */

public class UpdateEndPointProvidersRequestData extends InvoiceRequestDataBase
{
	private final String facilitatorCode;
	private final Boolean mcheckoutEnabled;
	private final Boolean eInvoicingEnabled;
	private final Boolean mbCheckEnabled;

	/**
	 * ctor
	 * @param facilitatorCode - ��� ������������
	 * @param eInvoicingEnabled - ����������� �� e-invoicing
	 * @param mcheckoutEnabled - ����������� �� mobile-checkout
	 * @param mbCheckEnabled - ����������� ��� �������� � ��
	 */
	public UpdateEndPointProvidersRequestData(String facilitatorCode, Boolean eInvoicingEnabled, Boolean mcheckoutEnabled, Boolean mbCheckEnabled)
	{
		super(null);
		this.facilitatorCode = facilitatorCode;
		this.eInvoicingEnabled = eInvoicingEnabled;
		this.mcheckoutEnabled = mcheckoutEnabled;
		this.mbCheckEnabled = mbCheckEnabled;
	}

	public String getName()
	{
		return "UpdateEndPointProvidersRequest";
	}

	public Document getBody() throws Exception
	{
		InternalMessageBuilder builder = createBuilder();

		builder.addParameter(Constants.PROVIDER_CODE_TAG, facilitatorCode);
		if (eInvoicingEnabled != null)
			builder.addParameter(Constants.EINVOICING_ENABLED_TAG, eInvoicingEnabled);

		if (mbCheckEnabled != null)
			builder.addParameter(Constants.MB_CHECK_ENABLED_TAG, mbCheckEnabled);

		if (mcheckoutEnabled != null)
			builder.addParameter(Constants.MOBILE_CHECKOUT_ENABLED_TAG, mcheckoutEnabled);

		return builder.closeTag().toDocument();
	}
}