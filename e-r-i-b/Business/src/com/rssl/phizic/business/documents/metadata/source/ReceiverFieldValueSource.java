package com.rssl.phizic.business.documents.metadata.source;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.P2PAutoTransferClaimBase;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.ExternalCardsTransferToOurBank;
import com.rssl.phizic.gate.payments.InternalCardsTransfer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Источник данных получателей платежа
 *
 * @author khudyakov
 * @ created 30.10.14
 * @ $Author$
 * @ $Revision$
 */
public class ReceiverFieldValueSource implements FieldValuesSource
{
	private Map<String, String> source = new HashMap<String, String>();


	public ReceiverFieldValueSource(BusinessDocument document)
	{
		String receiverType = getReceiverType(document);
		source.put(Constants.RECEIVER_TYPE_ATTRIBUTE_NAME, receiverType);

		if (P2PAutoTransferClaimBase.SEVERAL_RECEIVER_TYPE_VALUE.equals(receiverType))
		{
			source.put(Constants.RECEIVER_SUB_TYPE_ATTRIBUTE_NAME, P2PAutoTransferClaimBase.SEVERAL_RECEIVER_SUB_TYPE_VALUE);
		}
	}

	private String getReceiverType(BusinessDocument document)
	{
		if (!(document instanceof GateExecutableDocument))
		{
			return null;
		}

		GateExecutableDocument executableDocument = (GateExecutableDocument) document;
		Class<? extends GateDocument> type = executableDocument.asGateDocument().getType();

		if (ExternalCardsTransferToOurBank.class == type)
		{
			return RurPayment.PHIZ_RECEIVER_TYPE_VALUE;
		}
		if (InternalCardsTransfer.class == type)
		{
			return P2PAutoTransferClaimBase.SEVERAL_RECEIVER_TYPE_VALUE;
		}
		return null;
	}

	public String getValue(String name)
	{
		return source.get(name);
	}

	public Map<String, String> getAllValues()
	{
		return Collections.unmodifiableMap(source);
	}

	public boolean isChanged(String name)
	{
		return false;
	}

	public boolean isEmpty()
	{
		return false;
	}

	public boolean isMasked(String name)
	{
		return false;
	}
}
