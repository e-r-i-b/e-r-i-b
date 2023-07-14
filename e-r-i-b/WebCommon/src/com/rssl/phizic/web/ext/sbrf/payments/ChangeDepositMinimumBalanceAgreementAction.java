package com.rssl.phizic.web.ext.sbrf.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.deposits.GetDepositCollateralAgreementsOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * Для просмотра доп. соглашения по заявке на изменение неснижаемого остатка
 *
 * @author Balovtsev
 * @version 24.09.13 9:30
 */
public class ChangeDepositMinimumBalanceAgreementAction extends OperationalActionBase
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ChangeDepositMinimumBalanceAgreementForm form      = (ChangeDepositMinimumBalanceAgreementForm) frm;
		GetDepositCollateralAgreementsOperation  operation = getDepositCollateralAgreementsOperation();
		operation.initialize(form.getDocumentId());

		form.setCollateralAgreement( operation.getCollateralAgreement() );

		return mapping.findForward(FORWARD_START);
	}

	protected GetDepositCollateralAgreementsOperation getDepositCollateralAgreementsOperation() throws BusinessException
	{
		return createOperation(GetDepositCollateralAgreementsOperation.class, "ChangeDepositMinimumBalanceClaim");
	}
}
