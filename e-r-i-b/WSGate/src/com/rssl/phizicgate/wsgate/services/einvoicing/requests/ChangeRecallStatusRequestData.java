package com.rssl.phizicgate.wsgate.services.einvoicing.requests;

import com.rssl.phizgate.messaging.internalws.server.protocol.InternalMessageBuilder;
import com.rssl.phizic.gate.einvoicing.OrderState;
import com.rssl.phizic.gate.einvoicing.RecallState;
import com.rssl.phizic.gate.einvoicing.RecallType;
import org.w3c.dom.Document;

import static com.rssl.phizic.business.einvoicing.Constants.*;

/**
 * Запрос изменения статуса возвратов.
 *
 * @author bogdanov
 * @ created 17.03.14
 * @ $Author$
 * @ $Revision$
 */

public class ChangeRecallStatusRequestData extends InvoiceRequestDataBase
{
	private RecallState newState;
	private RecallType recallType;
	private String utrrno;

	public ChangeRecallStatusRequestData(String recallUUID, RecallState newState, String utrrno, RecallType recallType)
	{
		super(recallUUID);
		this.newState = newState;
		this.utrrno = utrrno;
		this.recallType = recallType;
	}

	public String getName()
	{
		return "ChangeRecallStatusRequest";
	}

	@Override
	public Document getBody() throws Exception
	{
		InternalMessageBuilder builder = createBuilder();
		fillOrderUUID(builder);
		builder.addParameter(UTRRNO_TAG, utrrno);
		builder.addParameterIfNotEmpty(STATE_TAG, newState);
		builder.addParameterIfNotEmpty(PAYMENT_TYPE_TAG, recallType.name());
		return builder.closeTag().toDocument();
	}
}
