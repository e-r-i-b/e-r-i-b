package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.LoanPayment;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loans.LoansService;
import com.rssl.phizic.gate.loans.ScheduleItem;

import java.math.BigDecimal;

/**
 * ’ендлер дл€ получени€ разбивки дл€ платежа из внешней системы
 * и установки в платеж
 * @author gladishev
 * @ created 20.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class LoanPaymentHandler extends BusinessDocumentHandlerBase
{
	private static final String ANNUITY_ERROR_TEXT = "¬ы не можете погасить задолженность по аннуитентному кредиту.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof LoanPayment))
			throw new DocumentException("Ќеверный тип платежа. ќжидаетс€ LoanPayment");

		LoanPayment payment = (LoanPayment) document;

		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			LoanLink link = personData.findLoan(payment.getAccountNumber());

			if (link.getLoan().getIsAnnuity())
				throw new DocumentLogicException(ANNUITY_ERROR_TEXT);

			LoansService loanService = GateSingleton.getFactory().service(com.rssl.phizic.gate.loans.LoansService.class);
			ScheduleItem item = loanService.getNextScheduleItem(link.getLoan(), payment.getChargeOffAmount());

			//если вернулась 0-а€ обща€ сумма платежа, то не даем оплачивать данный кредит
			if(item.getTotalPaymentAmount().getDecimal().compareTo(BigDecimal.ZERO) == 0)
					throw new DocumentLogicException("ќбща€ сумма платежа не может быть равна 0");
			//сохран€ем полученную разбивку платежа дл€ отображени€ клиенту
			payment.addAmountSplitting(item);
		}
		catch (GateException ge)
		{
			throw new DocumentException(ge);
		}
		catch (GateLogicException gle)
		{
			throw new DocumentLogicException(gle);
		}
		catch (BusinessException be)
		{
			throw new DocumentException(be);
		}
	}
}
