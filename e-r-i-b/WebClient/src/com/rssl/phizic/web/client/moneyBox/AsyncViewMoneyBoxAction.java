package com.rssl.phizic.web.client.moneyBox;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.ViewMoneyBoxOperation;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.ViewMoneyBoxForm;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** детальная информация по копилке
 * @author saharnova
 * @ created 29.09.14
 * @ $Author$
 * @ $Revision$
 * Асинхронный экшен просмотра копилки.
 */

public class AsyncViewMoneyBoxAction extends ViewActionBase
{
	protected static final String FORWARD_RESOURCE_NOT_FOUND = "ResourceNotFound";

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewMoneyBoxForm frm = (ViewMoneyBoxForm) form;
		ViewMoneyBoxOperation op = (ViewMoneyBoxOperation) operation;
		frm.setLink(op.getEntity());
		frm.setCardLink(op.getCardLink());
		frm.setHasMbConnection(op.hasConnectionToMB());
		frm.setAccountLink(op.getAccountLink());
		if (!op.isUpdateSheduleItemsError())
			frm.setScheduleItems(op.getScheduleItems());
		else
			frm.setTextUpdateSheduleItemsError(op.getTextUpdateSheduleItemsError());
	}

	public ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewMoneyBoxForm frm = (ViewMoneyBoxForm) form;
		ViewMoneyBoxOperation operation= createOperation(ViewMoneyBoxOperation.class, "MoneyBoxManagement");
		if(frm.getLinkId() != null)
		{
			operation.initialize(frm.getLinkId(), true);
		}
		else if(frm.getClaimId() != null)
		{
			operation.initialize(frm.getClaimId(), false);
		}
		else
			throw new BusinessException("Не пришел id ни копилки, ни заявки на копилку.");
		return operation;
	}

	protected boolean isAjax()
	{
		return true;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditFormBase frm = (EditFormBase) form;

		try
		{
			ViewEntityOperation operation = createViewEntityOperation(frm);
			addLogParameters(new BeanLogParemetersReader("Данные просматриваемой сущности", operation.getEntity()));
			updateFormData(operation, frm);
			ViewMoneyBoxForm viewMoneyBoxForm = (ViewMoneyBoxForm) form;
			if (viewMoneyBoxForm.getAccountLink()==null)
				return forwardResourceNotFound(mapping);
			return forwardSuccessShow(mapping, operation);
		}
		catch (ResourceNotFoundBusinessException ex)
		{
			return forwardResourceNotFound(mapping);
		}
		catch (BusinessLogicException ex)
		{
			saveError(request, ex);
			return forwardFailShow(mapping);
		}
	}

	protected ActionForward forwardResourceNotFound(ActionMapping mapping)
	{
		return mapping.findForward(FORWARD_RESOURCE_NOT_FOUND);
	}
}