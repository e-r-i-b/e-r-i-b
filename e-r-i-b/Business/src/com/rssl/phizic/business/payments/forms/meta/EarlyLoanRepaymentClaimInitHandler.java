package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.EarlyLoanRepaymentClaimImpl;
import com.rssl.phizic.business.loanclaim.LoanClaimHelper;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.payments.loan.EarlyLoanRepaymentClaim;

import java.security.AccessControlException;

/**
 * ќсуществл€ет проверку, возможно ли досрочное погашение при инициализации за€вки <br/>
 * Created with IntelliJ IDEA.
 * User: petuhov
 * Date: 30.06.15
 * Time: 15:29 
 */
public class EarlyLoanRepaymentClaimInitHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		Loan loan = ((EarlyLoanRepaymentClaimImpl)document).getLoanLink().getLoan();
		if(!LoanClaimHelper.isEarlyLoanRepaymentAvailable(loan))
		{
			throw new AccessControlException("ƒосрочное погашение этого кредита невозможно");
		}
		if(!LoanClaimHelper.isEarlyLoanRepaymentTimeLimitationFulfilled(loan))
		{
			throw new DocumentLogicException("¬ы уже отправили в банк за€вку на досрочное погашение кредита.");
		}
	}
}
