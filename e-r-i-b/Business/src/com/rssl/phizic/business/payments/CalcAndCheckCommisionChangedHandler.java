package com.rssl.phizic.business.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.payments.forms.meta.CheckCommissionSumAction;
import com.rssl.phizic.common.types.Money;

/**
 * Хендлер для вычисления комиссии и регистрации изменения, если она изменилась
 * @author niculichev
 * @ created 25.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class CalcAndCheckCommisionChangedHandler extends CheckCommissionSumAction
{
	private static final String INFO_MESSAGE = "Обратите внимание! Изменились значения выделенных полей.";
	private static final String FIELD_NAME = "commission";

	protected void processFailCheck(Money newCommision, Money oldCommision, AbstractPaymentDocument document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		stateMachineEvent.registerChangedField(FIELD_NAME, newCommision, oldCommision);
		stateMachineEvent.addMessage(INFO_MESSAGE);
	}
}
