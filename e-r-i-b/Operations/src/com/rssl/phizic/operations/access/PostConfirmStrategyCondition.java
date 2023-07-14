package com.rssl.phizic.operations.access;

import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.ConfirmableObject;

import java.util.List;

/**
 * @author vagin
 * @ created 01.07.14
 * @ $Author$
 * @ $Revision$
 * Кондишен по получении сумм комиссий после подтверждения.
 */
public class PostConfirmStrategyCondition extends NoStrategyCondition
{
	public boolean checkCondition(ConfirmableObject object)
	{
		if (object instanceof BusinessDocumentBase)
		{
			List<WriteDownOperation> writeDownOperations = ((BusinessDocumentBase) object).getWriteDownOperations();
			if (writeDownOperations != null && !writeDownOperations.isEmpty())
            {
	            //для закрытия вклада есть только 1 шаг подтверждения, нужно всегда потверждать если включена настройка.
                if (((BusinessDocumentBase) object).getFormName().equals(FormConstants.ACCOUNT_CLOSING_PAYMENT_FORM))
                    return ConfigFactory.getConfig(PaymentsConfig.class).isNeedConfirmSelfAccountClosingPayment();
                else
                //для подтвержденных документов с полученной из ЦОД комиссией нет необходимости подтверждать еще раз.
                    return false;
            }
		}
		return true;
	}
}
