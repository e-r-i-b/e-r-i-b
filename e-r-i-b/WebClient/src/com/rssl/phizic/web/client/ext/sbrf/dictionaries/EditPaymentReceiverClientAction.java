package com.rssl.phizic.web.client.ext.sbrf.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;
import com.rssl.phizic.business.ext.sbrf.receivers.PaymentReceiverPhizSBRF;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.operations.dictionaries.receivers.EditPaymentReceiverOperationBase;

import java.util.Map;

/**
 * @author akrenev
 * @ created 21.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditPaymentReceiverClientAction extends com.rssl.phizic.web.client.dictionaries.EditPaymentReceiverClientAction
{
	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		super.updateEntity(entity, data);
		PaymentReceiverBase paymentReceiver = (PaymentReceiverBase) entity;
		if (paymentReceiver instanceof PaymentReceiverPhizSBRF)
		{
		    PaymentReceiverPhizSBRF receiverSBRF = (PaymentReceiverPhizSBRF) paymentReceiver;
			SBRFOfficeCodeAdapter code = new SBRFOfficeCodeAdapter((String) data.get("region"), (String) data.get("branch"), (String) data.get("office"));
		    receiverSBRF.setCode(code);
		}
	}

	protected String getReceiverKind(EditPaymentReceiverOperationBase operation) throws BusinessException
	{
		return "PJ";
	}
}
