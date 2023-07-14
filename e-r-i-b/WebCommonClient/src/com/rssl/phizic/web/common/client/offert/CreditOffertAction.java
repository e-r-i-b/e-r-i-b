package com.rssl.phizic.web.common.client.offert;

import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.iPasSmsPasswordConfirmRequest;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.operations.ext.sbrf.strategy.iPasCapConfirmRequest;
import com.rssl.phizic.operations.loans.offert.AcceptCreditOffertOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Moshenko
 * @ created 29.06.15
 * @ $Author$
 * @ $Revision$
 */
public class CreditOffertAction extends OperationalActionBase
{
	protected static final String FORWARD_SUCCESS = "Success";

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	    if (StringHelper.isNotEmpty((String)request.getSession().getAttribute("offerAccepted")))
		    return  mapping.findForward(FORWARD_SUCCESS);

		AcceptCreditOffertOperation operation = createOperation("AcceptCreditOffertOperation","ExtendedLoanClaim");
		CreditOffertForm frm = (CreditOffertForm) form;
		if (frm.getClaimId() != null && StringHelper.isNotEmpty(frm.getAppNum()))
		{  //за€вка ериб
			operation.initialize(frm.getAppNum(), frm.getClaimId());

			frm.setLoanOffer (operation.getLoanOffer());
			frm.setOffertErib(operation.getOfferEribPrior());
			frm.setPdpOfferTemplate(operation.getPdpOfferTemplate());
		}
		else if (StringHelper.isNotEmpty(frm.getAppNum()))
		{  //за€вка не ериб
			operation.initialize(frm.getAppNum());
			frm.setOffertOffice(operation.getOfferOfficePrior());
		}
		else
			throw new Exception("Ќе передан ни appNum ни claimId");

		frm.setEnsuring(operation.isEnsuring());
		frm.setProductName(operation.getProductName());
		frm.setEnrollAccount(operation.getEnrollAccount());
		frm.setClaimDrawDepartment(operation.getClaimDrawDepartment());
		frm.setOffertTemplate(operation.getOffertTemplate());

		boolean isOneTimePassword = operation.isOneTimePassword();

		if (isOneTimePassword)
			frm.setOneTimePassword(operation.isOneTimePassword());
		else if (isConfirmEnter(operation))
			frm.setOneTimePassword(true);

		operation.resetConfirmStrategy();
		ConfirmationManager.sendRequest(operation);
		frm.setConfirmStrategy(operation.getConfirmStrategy());
		frm.setConfirmableObject(operation.getConfirmableObject());
		saveOperation(request,operation);
		return mapping.findForward(FORWARD_START);
	}

	//подтверждали или нет вход на страницу
	protected boolean isConfirmEnter(AcceptCreditOffertOperation op)  throws BusinessLogicException, BusinessException
	{
		if (ConfirmationManager.currentConfirmRequest(op.getConfirmableObject()) == null)
		{
			return  false;
		}

		ConfirmRequest confirmRequest = ConfirmationManager.currentConfirmRequest(op.getConfirmableObject());

		if (confirmRequest instanceof iPasSmsPasswordConfirmRequest)
			return ((iPasSmsPasswordConfirmRequest)confirmRequest).isConfirmEnter();
		else
			return ((iPasCapConfirmRequest)confirmRequest).isConfirmEnter();
	}

}
