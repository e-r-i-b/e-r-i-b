package com.rssl.phizic.web.actions.payments.forms.autosubscription;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionPaymentInfoOperation;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Базовый класс детальной информации по подписке (клиент)
 *
 * @author khudyakov
 * @ created 02.11.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class ShowAutoSubscriptionPaymentInfoActionBase extends ViewActionBase
{
	private static final String FORWARD_BACK                = "Back";

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowAutoSubscriptionPaymentInfoForm frm = (ShowAutoSubscriptionPaymentInfoForm) form;

		try
		{
			ViewEntityOperation operation = createViewEntityOperation(frm);
			addLogParameters(new BeanLogParemetersReader("Данные просматриваемой сущности", operation.getEntity()));
			updateFormData(operation, frm);
		}
		catch (BusinessLogicException ex)
		{
			saveError(request, ex);
			return StrutsUtils.createDefaultByIdForward(mapping.findForward(FORWARD_BACK).getPath(), frm.getSubscriptionId());
		}
		return mapping.findForward(FORWARD_START);
	}

	@Override
	protected void updateFormData(ViewEntityOperation operation, EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ShowAutoSubscriptionPaymentInfoForm form = (ShowAutoSubscriptionPaymentInfoForm) frm;
		GetAutoSubscriptionPaymentInfoOperation op = (GetAutoSubscriptionPaymentInfoOperation) operation;

		form.setPayment(op.getEntity());
		form.setSubscriptionLink(op.getSubscriptionLink());
		form.setCardLink(op.getPersonData().findCard(op.getEntity().getChargeOffCard()));
	}
}
