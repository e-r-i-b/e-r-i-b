package com.rssl.phizic.web.common.socialApi.accounts;

import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.operations.account.AccountBankDetailsOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Получение реквизитов счета вклада
 *
 * @author EgorovaA
 * @ created 27.11.13
 * @ $Author$
 * @ $Revision$
 */
public class ShowAccountBankDetailsAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowAccountBankDetailsForm frm = (ShowAccountBankDetailsForm) form;
		Long linkId = frm.getId();

		AccountBankDetailsOperation operation = createOperation(AccountBankDetailsOperation.class);
		operation.initialize(linkId, getLinkClassType());

		frm.setAccountLink(operation.getAccount());
		frm.setCardLink(operation.getCardLink());
		frm.addFields(operation.getDetails());

		return mapping.findForward(FORWARD_SHOW);
	}

	protected Class getLinkClassType()
	{
		return AccountLink.class;
	}
}
