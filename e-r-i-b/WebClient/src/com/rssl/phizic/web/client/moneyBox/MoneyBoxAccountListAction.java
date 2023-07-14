package com.rssl.phizic.web.client.moneyBox;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.ext.sbrf.payment.MoneyBoxListOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author vagin
 * @ created 29.09.14
 * @ $Author$
 * @ $Revision$
 * Ёкшен просмотра списка копилок дл€ вклада.
 */
public class MoneyBoxAccountListAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MoneyBoxListOperation operation = createOperation("MoneyBoxListOperation","MoneyBoxManagement");
		MoneyBoxListForm form = (MoneyBoxListForm) frm;
		AccountLink accountLink = PersonContext.getPersonDataProvider().getPersonData().getAccount(form.getId());
		operation.initialize(accountLink);
		form.setData(operation.getData());
		updateFormData(form, operation);

		return mapping.findForward(FORWARD_START);
	}

	protected void updateFormData(MoneyBoxListForm form, MoneyBoxListOperation operation) throws BusinessException, BusinessLogicException
	{
		AccountLink accountLink = PersonContext.getPersonDataProvider().getPersonData().getAccount(form.getId());
		form.setAccountLink(accountLink);
	}
}
