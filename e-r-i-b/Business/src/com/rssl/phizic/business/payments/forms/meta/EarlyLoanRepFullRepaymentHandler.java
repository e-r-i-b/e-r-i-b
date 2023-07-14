package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.EarlyLoanRepaymentClaimImpl;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.loans.*;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: petuhov
 * Date: 31.07.15
 * Time: 13:30 
 */
public class EarlyLoanRepFullRepaymentHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		EarlyLoanRepaymentClaimImpl doc = (EarlyLoanRepaymentClaimImpl)document;
		if(!doc.isPartial())
		{
			doc.setChargeOffAmount(getFullRepaymentAmountForDay(doc.getLoanLink().getLoan(), doc.getDocumentDate()));
		}
	}

	private Money getFullRepaymentAmountForDay(Loan loan, Calendar date) throws DocumentException
	{
		ScheduleAbstract schedule = GateSingleton.getFactory().service(LoansService.class).getSchedule(0L,null,loan).getResult(loan);
		if(schedule.getYearPayments()!=null)
		{
			for(LoanYearPayment year : schedule.getYearPayments())
			{
				for(LoanMonthPayment month : year.getMonths())
				{
					if(month.getScheduleItem().getDate().getTime().equals(date.getTime()))
					{
						return month.getScheduleItem().getRemainDebt().add(month.getScheduleItem().getTotalPaymentAmount());
					}
				}
			}
		}
		Loan detailedLoan = GateSingleton.getFactory().service(LoansService.class).getLoan(loan.getId()).getResult(loan.getId());
		if(detailedLoan == null)
			throw new TemporalDocumentException("Ќе удалось получить детальные данные по кредиту");
		return detailedLoan.getFullRepaymentAmount();
	}
}
