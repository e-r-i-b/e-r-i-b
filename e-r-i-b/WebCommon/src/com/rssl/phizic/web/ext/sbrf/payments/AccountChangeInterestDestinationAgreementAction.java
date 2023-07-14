package com.rssl.phizic.web.ext.sbrf.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.deposits.GetDepositAgreementWithCapitalizationOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшн для просмотра доп. соглашения по заявке на изменение порядка уплаты процентов
 * @author Jatsky
 * @ created 01.10.13
 * @ $Author$
 * @ $Revision$
 */

public class AccountChangeInterestDestinationAgreementAction extends OperationalActionBase
{
	@Override public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AccountChangeInterestDestinationAgreementForm frm = (AccountChangeInterestDestinationAgreementForm) form;
		GetDepositAgreementWithCapitalizationOperation operation = getOperation();
		operation.initialize(frm.getId());

		frm.setCollateralAgreement(operation.getCollateralAgreement());

		return mapping.findForward(FORWARD_START);
	}

	protected GetDepositAgreementWithCapitalizationOperation getOperation() throws BusinessException
	{
		return createOperation(GetDepositAgreementWithCapitalizationOperation.class, "AccountChangeInterestDestinationClaim");
	}
}
