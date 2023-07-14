package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.loanclaim.LoanClaimHelper;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.userSettings.UserPropertyService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: petuhov
 * Date: 28.07.15
 * Time: 14:08 
 */
public class EarlyLoanRepaymentClaimConfirmHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		PersonContext.getPersonDataProvider().getPersonData().getUserProperties().setProperty(LoanClaimHelper.LAST_CLAIM_TIME_USER_PROPERTY_KEY, SimpleDateFormat.getInstance().format(Calendar.getInstance().getTime()));
		UserPropertyService.getIt().setSetting(PersonContext.getPersonDataProvider().getPersonData().getLogin().getId(),LoanClaimHelper.LAST_CLAIM_TIME_USER_PROPERTY_KEY, SimpleDateFormat.getInstance().format(Calendar.getInstance().getTime()));
	}
}
