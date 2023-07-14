package com.rssl.phizicgate.wsgate.services.einvoicing.requests;

import com.rssl.phizgate.messaging.internalws.client.RequestDataBase;
import com.rssl.phizgate.messaging.internalws.server.protocol.InternalMessageBuilder;
import com.rssl.phizic.gate.einvoicing.ShopProfile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import static com.rssl.phizic.business.einvoicing.Constants.*;

/**
 * @author gladishev
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */
abstract class InvoiceRequestDataBase extends RequestDataBase
{
	private String orderUUID;

	InvoiceRequestDataBase(String orderUUID)
	{
		this.orderUUID = orderUUID;
	}

	public Document getBody() throws Exception
	{
		InternalMessageBuilder builder = createBuilder();
		fillOrderUUID(builder);
		return builder.closeTag().toDocument();
	}

	protected void fillOrderUUID(InternalMessageBuilder builder) throws Exception
	{
		builder.addParameter(UUID_TAG, orderUUID);
	}

	protected void addProfileElement(ShopProfile profile, InternalMessageBuilder builder) throws SAXException
	{
		builder.openTag(PROFILE_TAG)
		.addParameter(FIRST_NAME_TAG, profile.getFirstName())
		.addParameter(SUR_NAME_TAG, profile.getSurName())
		.addParameterIfNotEmpty(PATR_NAME_TAG, profile.getPatrName())
		.addParameter(PASSPORT_TAG, profile.getPassport())
		.addParameter(BIRTHDATE_TAG, format(profile.getBirthdate()))
		.addParameter(TB_TAG, profile.getTb())
		.closeTag();
	}
}
