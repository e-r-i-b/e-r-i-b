package com.rssl.phizic.business.ermb.sms.payment;

import com.rssl.phizic.payment.PaymentEngineBase;
import com.rssl.phizic.engine.EngineManager;
import com.rssl.common.forms.processing.ValidationStrategy;
import com.rssl.common.forms.validators.strategy.SmsDocumentValidationStrategy;
import com.rssl.common.forms.doc.CreationType;

/**
 * @author Erkin
 * @ created 09.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Движок платежей для СМС-канала ЕРМБ
 */
public class ErmbPaymentEngine extends PaymentEngineBase
{
	/**
	 * ctor
	 * @param manager - менеджер по движкам
	 */
	public ErmbPaymentEngine(EngineManager manager)
	{
		super(manager);
	}

	public CreationType getDocumentCreationType()
	{
		return CreationType.sms;
	}

	public ValidationStrategy getDocumentValidationStrategy()
	{
		return SmsDocumentValidationStrategy.getInstance();
	}
}
