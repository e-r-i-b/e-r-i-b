package com.rssl.phizic.web.autopayments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionInfoOperation;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.ShowAutoSubscriptionInfoActionBase;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.ShowAutoSubscriptionInfoForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ѕазовый класс детальной информации по автоплатежам, автопереводам, копилкам
 *
 * @author khudyakov
 * @ created 23.10.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class ShowAutoSubscriptionInfoEmployeeActionBase extends ShowAutoSubscriptionInfoActionBase
{
	protected abstract GetAutoSubscriptionInfoOperation createSimpleViewEntityOperation() throws BusinessException, BusinessLogicException;

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowAutoSubscriptionInfoForEmployeeForm frm = (ShowAutoSubscriptionInfoForEmployeeForm) form;

		try
		{
			ViewEntityOperation operation = createViewEntityOperation(frm);
			addLogParameters(new BeanLogParemetersReader("ƒанные просматриваемой сущности", operation.getEntity()));
			updateFormData(operation, frm);
		}
		catch (BusinessLogicException ex)
		{
			//инициализированна€ персона в левом меню должна остатьс€
			GetAutoSubscriptionInfoOperation operation = createSimpleViewEntityOperation();
			frm.setActivePerson(operation.getPerson());

			saveError(request, ex);
			return mapping.findForward("List");
		}
		catch(InactiveExternalSystemException e)
		{
			saveInactiveESMessage(currentRequest(),e);
			return mapping.findForward("List");
		}

		return mapping.findForward(FORWARD_START);
	}

	public void updateForm(GetAutoSubscriptionInfoOperation operation, ShowAutoSubscriptionInfoForm form) throws BusinessException, BusinessLogicException
	{
		super.updateForm(operation, form);

		ShowAutoSubscriptionInfoForEmployeeForm frm = (ShowAutoSubscriptionInfoForEmployeeForm) form;
		frm.setActivePerson(operation.getPerson());
	}
}

