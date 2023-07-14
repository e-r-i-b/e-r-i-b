package com.rssl.phizic.operations.access.conditions;

import com.rssl.phizic.auth.modes.StrategyCondition;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.FormConstants;

/**
 * @author Balovtsev
 * @created 18.02.2011
 * @ $Author$
 * @ $Revision$
 */

public class AccountClosingPaymentStrategyCondition implements StrategyCondition
{
	public boolean checkCondition(ConfirmableObject object)
	{
		//закрытие вклада подтверждается по СМС, если установлена соответствующая настройка.
		if (object instanceof BusinessDocument)
		{
			if (((BusinessDocument) object).getFormName().equals(FormConstants.ACCOUNT_CLOSING_PAYMENT_FORM))
			{
				return ConfigFactory.getConfig(PaymentsConfig.class).isNeedConfirmSelfAccountClosingPayment();
			}
		}
		return true;
	}

	public String getWarning()
	{
		return null;
	}
}
