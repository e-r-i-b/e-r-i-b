package com.rssl.phizic.web.ext.sbrf.persons.templates;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.templates.EmployeeListTemplateOperation;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ author: Vagin
 * @ created: 12.04.2013
 * @ $Author
 * @ $Revision
 * Экшен АРМ сотрудника. Настройки видимости шаблонов клиента в разрезе каналов.
 */
public class EmployeeTemplatesShowSettingsAction extends ListActionBase
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.save", "save");
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		EmployeeListTemplateOperation operation = createOperation("EmployeeListTemplateOperation", "EmployeeTemplateVisibilitySettings");
		EmpolyeeListTemplatesForm form = (EmpolyeeListTemplatesForm) frm;
		String channel = (String) form.getField("channel");
		int currentPage = form.getSearchPage();
		int itemsPerPage = form.getItemsPerPage();
		operation.initializeShowSettings(form.getPerson(), channel, currentPage, itemsPerPage);
		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException, BusinessLogicException
	{
		EmployeeListTemplateOperation op = (EmployeeListTemplateOperation) operation;
		EmpolyeeListTemplatesForm frm = (EmpolyeeListTemplatesForm) form;
		frm.setField("channel", op.getChannel());
		frm.setActivePerson(op.getPerson());
	}

	public final ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFormBase frm = (ListFormBase) form;
		EmployeeListTemplateOperation operation = (EmployeeListTemplateOperation) createListOperation(frm);
		List<Long> ids = StrutsUtils.parseIds(frm.getSelectedIds());
		operation.updateTemlateShowSettings(ids);
		return start(mapping, form, request, response);
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws Exception
	{
		EmpolyeeListTemplatesForm frm = (EmpolyeeListTemplatesForm) form;
		EmployeeListTemplateOperation op = (EmployeeListTemplateOperation) operation;
		frm.setData(op.getTemplates());
		updateFormAdditionalData(frm, operation);
	}
}
