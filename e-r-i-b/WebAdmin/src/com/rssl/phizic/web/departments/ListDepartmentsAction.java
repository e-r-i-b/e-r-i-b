package com.rssl.phizic.web.departments;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.departments.ListDepartmentsOperation;
import com.rssl.phizic.operations.departments.RemoveDepartmentOperation;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.List;
import java.util.Map;

/**
 * @ created 28.12.2005
 * @ $Author: krenev $
 * @ $Revision: 57762 $
 */
public class ListDepartmentsAction extends SaveFilterParameterAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListDepartmentsForm form = (ListDepartmentsForm) frm;
		ListDepartmentsOperation operation = createOperation(ListDepartmentsOperation.class);
		operation.initialize(form.getId());
		return operation;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveDepartmentOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListDepartmentsForm.FILTER_FORM;
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		RemoveDepartmentOperation op = (RemoveDepartmentOperation) operation;
		super.doRemove(operation, id);
		List<String> errors = op.getErrors();
		if (!errors.isEmpty())
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("ќшибки, возникшие при удалении подразделени€ "+ op.getEntity().getName() + " : ", false));
			for (String error : errors)
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(error, false));
		}
		return msgs;
	}

	/**
	 * ѕолучение данных и обновление формы.
	 * @param filterParams параметры фильтрации.
	 * @param operation операци€ дл€ получени€ данных
	 * @param frm форма дл€ обновлени€.
	 */
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		ListDepartmentsOperation op = (ListDepartmentsOperation) operation;
		ListDepartmentsForm form = (ListDepartmentsForm) frm;

		// заполн€ем параметры отбора подразделений
		Query query = operation.createQuery(getQueryName(form));

		boolean needCheckChild = isNeedCheckChild(op.getParentDepartment(), form.getLevel());
		query.setParameter("checkChild", needCheckChild);
		query.setParameter("parent", form.getId());
		query.setParameter("name", filterParams.get("name"));

		form.setChildren(query.executeList());
		form.setFilters(filterParams);
	}

	private boolean isNeedCheckChild(Department parentDepartment, Long level)
	{
		int departmentsAllowedLevel = ConfigFactory.getConfig(SecurityConfig.class).getDepartmentsAllowedLevel();
		long maxLevel = level != null ? Math.min(level, departmentsAllowedLevel) : departmentsAllowedLevel; //ћаксимально допустимый уровень.
		if (parentDepartment == null)
		{
			return maxLevel > 1;
		}
		if (DepartmentService.isTB(parentDepartment))
		{
			return maxLevel > 2;
		}
		return false;
	}

	/**
	 * @param frm форма
	 * @return ¬озвращаетс€ им€ запроса, прив€занного к операции
	 */
	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "GetTreeChildren";
	}

}