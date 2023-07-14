package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.person.EmployeeInvoicesListOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tisov
 * @ created 28.05.15
 * @ $Author$
 * @ $Revision$
 * Ёкшн просмотра списка инвойсов сотрудником
 */
public class EmployeeInvoicesListAction extends ViewActionBase
{
	@Override
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EmployeeInvoicesListOperation operation = createOperation(EmployeeInvoicesListOperation.class);
		EmployeeInvoicesListForm form = (EmployeeInvoicesListForm)frm;

		boolean showAllCommonInvoices = form.isShowAllCommonInvoices() == null ? false : form.isShowAllCommonInvoices();
		boolean showAllDelayedInvoices = form.isShowAllDelayedInvoices() == null ? false : form.isShowAllDelayedInvoices();

		operation.initialize(form.getPerson(), showAllCommonInvoices, showAllDelayedInvoices);
		return operation;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward forward = super.start(mapping, form, request, response);
		EmployeeInvoicesListForm frm = (EmployeeInvoicesListForm)form;
		if (!frm.getInvoiceData().getErrors().isEmpty())
		{
			ActionMessages msg = new ActionMessages();
			for (String errorText: frm.getInvoiceData().getErrors())
			{
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(errorText, false));
			}
		}
		return forward;
	}

	@Override
	protected void updateFormData(ViewEntityOperation operation, EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EmployeeInvoicesListForm form = (EmployeeInvoicesListForm)frm;
		EmployeeInvoicesListOperation op = (EmployeeInvoicesListOperation)operation;
		form.setInvoiceData(op.getEntity());
		form.setActivePerson(op.getPerson());
	}
}
