package com.rssl.phizic.web.autopayments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionPaymentInfoOperation;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.ShowAutoSubscriptionPaymentInfoActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Базовый класс детальной информации по подписке (админка)
 *
 * @author khudyakov
 * @ created 02.11.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class ShowAutoSubscriptionPaymentInfoEmployeeActionBase extends ShowAutoSubscriptionPaymentInfoActionBase
{
	protected abstract GetAutoSubscriptionPaymentInfoOperation createSimpleViewEntityOperation() throws BusinessException, BusinessLogicException;

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowAutoSubscriptionPaymentInfoForEmployeeForm frm = (ShowAutoSubscriptionPaymentInfoForEmployeeForm) form;

		try
		{
			ViewEntityOperation operation = createViewEntityOperation(frm);
			addLogParameters(new BeanLogParemetersReader("Данные просматриваемой сущности", operation.getEntity()));
			updateFormData(operation, frm);
		}
		catch (BusinessLogicException ex)
		{
			//инициализированная персона в левом меню должна остаться
			GetAutoSubscriptionPaymentInfoOperation operation = createSimpleViewEntityOperation();
			frm.setActivePerson(operation.getPerson());

			saveError(request, ex);
		}

		return mapping.findForward(FORWARD_START);
	}

	@Override
	protected void updateFormData(ViewEntityOperation operation, EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		super.updateFormData(operation, frm);

		ShowAutoSubscriptionPaymentInfoForEmployeeForm form = (ShowAutoSubscriptionPaymentInfoForEmployeeForm) frm;
		GetAutoSubscriptionPaymentInfoOperation op = (GetAutoSubscriptionPaymentInfoOperation) operation;

		form.setActivePerson(op.getPerson());
	}
}
