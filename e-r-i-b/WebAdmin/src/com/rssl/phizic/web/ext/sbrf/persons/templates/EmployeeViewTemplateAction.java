package com.rssl.phizic.web.ext.sbrf.persons.templates;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.templates.validators.NullTemplateValidator;
import com.rssl.phizic.business.documents.templates.validators.TemplateValidator;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.documents.templates.RemoveBankTemplateOperation;
import com.rssl.phizic.operations.documents.templates.RemoveTemplateOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.templates.EmployeeViewTemplateOperation;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.actions.payments.forms.ViewTemplateActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ author: Vagin
 * @ created: 16.04.2013
 * @ $Author
 * @ $Revision
 * Просмотр формы шаблона клиента
 */
public class EmployeeViewTemplateAction extends ViewTemplateActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.remove", "remove");
		map.put("button.recover", "recover");
		map.put("button.confirm", "confirm");
		return map;
	}

	/**
	 * Удаление шаблона.
	 * @throws Exception
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EmployeeViewTemplateForm frm = (EmployeeViewTemplateForm) form;

		RemoveTemplateOperation operation = createRemoveEntityOperation(frm);
		operation.remove();

		saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Шаблон успешно удален.", false), null);

		ActionForward forward = new ActionForward(getCurrentMapping().findForward("List"));
		forward.setPath(forward.getPath() + "?person=" + frm.getPerson());
		return forward;
	}

	/**
	 * Восстановление удаленного клиентом шаблона.
	 * @throws Exception
	 */
	public ActionForward recover(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EmployeeViewTemplateForm frm = (EmployeeViewTemplateForm) form;

		EmployeeViewTemplateOperation operation = createViewEntityOperation(frm);
		operation.recover();

		updateFormData(operation, frm);

		saveMessage(request, "Шаблон успешно восстановлен.");

		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Подтверждение шаблона - первод в сверхлимитный.
	 * @throws Exception
	 */
	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EmployeeViewTemplateForm frm = (EmployeeViewTemplateForm) form;
		EmployeeViewTemplateOperation operation = createViewEntityOperation(frm);

		try
		{
			operation.confirm();
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e.getMessage());
			updateFormData(operation, frm);

			return mapping.findForward(FORWARD_START);
		}

		saveMessage(request, "Шаблон успешно стал доверенным.");
		updateFormData(operation, frm);

		return mapping.findForward(FORWARD_START);
	}

	@Override
	protected EmployeeViewTemplateOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EmployeeViewTemplateForm frm = (EmployeeViewTemplateForm) form;
		EmployeeViewTemplateOperation operation = createOperation("EmployeeViewTemplateOperation", "EmployeeTemplateManagement");
		operation.initialize(frm.getPerson(), getTemplateSource(form));
		return operation;
	}

	@Override
	protected RemoveTemplateOperation createRemoveEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		RemoveBankTemplateOperation operation = createOperation("RemoveBankTemplateOperation", "EmployeeTemplateManagement");
		operation.initialize(getTemplateSource(form));
		return operation;
	}

	@Override
	protected TemplateValidator[] getTemplateValidators() throws BusinessException
	{
		return new TemplateValidator[]{new NullTemplateValidator()};
	}

	@Override
	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EmployeeViewTemplateForm frm = (EmployeeViewTemplateForm) form;
		EmployeeViewTemplateOperation viewOperation = (EmployeeViewTemplateOperation) operation;

		frm.setActivePerson(viewOperation.getPerson());

		super.updateFormData(operation, form);
	}

	@Override
	protected FormInfo getFormInfo()
	{
		return new FormInfo(WebContext.getCurrentRequest().getContextPath(), currentServletContext().getInitParameter("resourcesRealPath"), null);
	}
}
