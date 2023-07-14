package com.rssl.phizic.web.cards.limits;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.cardAmountStep.CardAmountStepListOperation;
import com.rssl.phizic.operations.cardAmountStep.CardAmountStepOperationDelete;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.*;
import org.hibernate.exception.ConstraintViolationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Moshenko
 * Date: 06.06.2011
 * Time: 12:27:27
 * Ёкшен дл€ отображени€ списка лимитов по картам
 */
public class ListCardLimitsAction extends ListActionBase
{
	private static final String ERROR_MESSAGE = "¬ы не можете удалить лимит, который используетс€ в " +
		"описании условий кредитного продукта. ќтредактируйте услови€ продукта и повторите операцию";
    protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException {
        return createOperation(CardAmountStepListOperation.class);
    }

    protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException {
        return createOperation(CardAmountStepOperationDelete.class);
    }

    protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation) {
        return FormBuilder.EMPTY_FORM;
    }

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			return super.remove(mapping, form, request, response);
		}
		catch (ConstraintViolationException e)
		{
			ActionMessages errors = new ActionMessages();

			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ERROR_MESSAGE, false));
			saveErrors(currentRequest(), errors);
		}
		return filter(mapping, form, request, response);
	}
}
