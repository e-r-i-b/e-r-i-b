package com.rssl.phizic.payment;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.ValidationStrategy;
import com.rssl.phizic.engine.Engine;

/**
 * @author Erkin
 * @ created 31.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Движок платежей
 */
public interface PaymentEngine extends Engine
{

	/**
	 * @return канал, в котором создаётся платёж (never null)
	 */
	CreationType getDocumentCreationType();

	/**
	 * @return стратегия валидация документов, используемая для всех платежей движка (never null)
	 */
	ValidationStrategy getDocumentValidationStrategy();
}
