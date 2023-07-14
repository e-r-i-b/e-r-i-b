package com.rssl.phizic.web.employees;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.dictionaries.officies.AllowedDepartment;
import com.rssl.phizic.operations.employees.EditAllowedDepartmentsOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 02.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Экшен редактирования доступных подразделений сотрудника
 */

public class EditAllowedDepartmentsAction extends OperationalActionBase
{
	private static final Pattern KEY_PATTERN = Pattern.compile("(\\d+|\\*)\\|(\\d+|\\*)\\|(\\d+|\\*)");

	protected Map<String, String> getKeyMethodMap()
	{
		return Collections.singletonMap("button.save", "save");
	}

	private EditAllowedDepartmentsOperation getEditAllowedDepartmentsOperation(EditAllowedDepartmentsForm form) throws BusinessException, BusinessLogicException
	{
		EditAllowedDepartmentsOperation operation = createOperation(EditAllowedDepartmentsOperation.class);
		operation.initialize(form.getEmployeeId());
		return operation;
	}

	private ActionForward goToStart(ActionMapping mapping, EditAllowedDepartmentsForm form, EditAllowedDepartmentsOperation operation) throws BusinessException, BusinessLogicException
	{
		form.setAllowedDepartments(operation.getAllowedDepartments());
		form.setAdminHasAllTBAccess(operation.isAdminHasAllTBAccess());
		return mapping.findForward(FORWARD_START);
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditAllowedDepartmentsForm form = (EditAllowedDepartmentsForm) frm;
		EditAllowedDepartmentsOperation operation = getEditAllowedDepartmentsOperation(form);
		return goToStart(mapping, form, operation);
	}

	/**
	 * сохранение списка доступных подразделений
	 * @param mapping   сратс-маппинг
	 * @param frm       сратс-форма
	 * @param request   запрос
	 * @param response  ответ
	 * @return          сратс-форвард
	 * @throws Exception
	 */
	@SuppressWarnings("UnusedParameters")
	public ActionForward save(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditAllowedDepartmentsForm form = (EditAllowedDepartmentsForm) frm;
		EditAllowedDepartmentsOperation operation = getEditAllowedDepartmentsOperation(form);
		try
		{
			operation.saveAllowedDepartments(getAllowedDepartments(form.getAddedDepartment()), getAllowedDepartments(form.getRemovedDepartment()));
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e.getMessage());
		}
		return goToStart(mapping, form, operation);
	}

	private List<AllowedDepartment> getAllowedDepartments(String[] keys) throws BusinessLogicException
	{
		List<AllowedDepartment> allowedDepartments = new ArrayList<AllowedDepartment>();
		if (ArrayUtils.isEmpty(keys))
			return allowedDepartments;
		for (String key : keys)
		{
			Matcher matcher = KEY_PATTERN.matcher(key);
			if (!matcher.find())
				throw new BusinessLogicException("Неизвестное подразделение " + key);
			allowedDepartments.add(new AllowedDepartment(matcher.group(1), matcher.group(2), matcher.group(3)));
		}
		return allowedDepartments;
	}
}
