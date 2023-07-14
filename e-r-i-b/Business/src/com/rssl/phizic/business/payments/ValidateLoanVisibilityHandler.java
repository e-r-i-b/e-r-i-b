package com.rssl.phizic.business.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.LoanPayment;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.gate.loans.LoanState;

/** Проверка кредита на незакрытость и отображаемость в системе,
 * @author akrenev
 * @ created 29.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class ValidateLoanVisibilityHandler extends BusinessDocumentHandlerBase
{
	private static final String ERROR_DISABLED_RESOURCE = "Вы не можете подтвердить данную операцию. " +
						"Для доступа измените настройки видимости Вашего кредита в пункте меню " +
						"«Настройки» – «Настройка безопасности» – «Настройка видимости продуктов».";

	private static final String MESSAGE_CLOSED= "Вы не можете выполнить данную операцию: выбранный кредит закрыт. Пожалуйста, отредактируйте документ.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof LoanPayment))
			throw new DocumentException("Неверный тип платежа. Ожидается LoanPayment");

		LoanPayment loanPaymentDocument = (LoanPayment) document;
		String payerAccount = loanPaymentDocument.getChargeOffAccount();

		if (payerAccount == null)
			throw new DocumentException("Не найден счет получателя для платежа с id:" + loanPaymentDocument.getId());

		LoanLink loanLink = loanPaymentDocument.getLoanLink();

		if (loanLink == null)
			throw new DocumentException("Не найден линк на кредит.");

		if (loanLink.getLoan().getState() == LoanState.closed)
			throw makeValidationFail(MESSAGE_CLOSED);

		if (!loanLink.getShowInSystem())
			throw makeValidationFail(ERROR_DISABLED_RESOURCE);
	}
}
