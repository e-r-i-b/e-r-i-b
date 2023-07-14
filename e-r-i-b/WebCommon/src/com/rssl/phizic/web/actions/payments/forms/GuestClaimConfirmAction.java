package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.operations.payment.GuestClaimConfirmOperation;
import com.rssl.phizic.web.actions.payments.forms.extendedLoanClaims.ExtendedLoanClaimConfirmAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gulov
 * @ created 30.03.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Ёкшн подтверждени€ за€вки на кредит в гостевой сессии
 */
public class GuestClaimConfirmAction extends ExtendedLoanClaimConfirmAction
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return autoConfirm(mapping, form, request, response);
	}

	@Override
	protected ConfirmFormPaymentOperation createConfirmOperation(ExistingSource source)
			throws BusinessException, BusinessLogicException
	{
		return createOperation(GuestClaimConfirmOperation.class, getServiceName(source));
	}

	protected boolean needAutoConfirm(ConfirmFormPaymentOperation operation, HttpServletRequest request) throws BusinessException, BusinessLogicException
	{
		return false;
	}

	protected void addDocumentLimitsInfoMessages(ConfirmFormPaymentOperation operation, HttpServletRequest request) throws BusinessException, BusinessLogicException
	{

	}
}
