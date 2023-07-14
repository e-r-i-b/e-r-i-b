package com.rssl.phizic.web.employees;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeListFilterParameters;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.gate.login.LoginBlock;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import com.rssl.phizic.logging.operations.LogParametersReader;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.access.AssignEmployeeAccessOperation;
import com.rssl.phizic.operations.access.ChangeLockEmployeeOperation;
import com.rssl.phizic.operations.employees.GetEmployeeListOperation;
import com.rssl.phizic.operations.employees.RemoveEmployeeOperation;
import com.rssl.phizic.operations.scheme.AccessHelper;
import com.rssl.phizic.web.LockForm;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.log.CollectionLogParametersReader;
import com.rssl.phizic.web.log.FormLogParametersReader;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Roshka
 * @ created 29.09.2005
 * @ $Author$
 * @ $Revision$
 */

@SuppressWarnings({"JavaDoc"})
public class EmployeeListAction extends SaveFilterParameterAction
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();

		map.put("button.unlock", "unlock");
		map.put("button.lock", "lock");
		map.put("button.assignScheme", "assignScheme");

		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetEmployeeListOperation.class);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
	    return createOperation(RemoveEmployeeOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return EmployeeListForm.FILTER_FORM;
	}

	@Override
	protected void doStart(ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		updateFormAdditionalData(frm, operation);
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		GetEmployeeListOperation listOperation = (GetEmployeeListOperation) operation;
		EmployeeListForm form = (EmployeeListForm)frm;
		Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		boolean allTbAcces = EmployeeContext.getEmployeeDataProvider().getEmployeeData().isAllTbAccess();

		int maxResults = form.getPaginationSize() + 1;
		int firstResult = form.getPaginationOffset();
		EmployeeListFilterParameters filterParameters = new EmployeeListFilterParameters(filterParams, employee.getLogin(), allTbAcces, firstResult, maxResults);
		frm.setData(listOperation.getAll(filterParameters));
		frm.setFilters(filterParams);
		updateFormAdditionalData(frm, operation);
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException
	{
		EmployeeListForm frm = (EmployeeListForm) form;
		boolean isCaAdmin = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().isCAAdmin();
		frm.setHelpers(AccessHelper.createAssignAccessHelpers(SecurityService.SCOPE_EMPLOYEE, isCaAdmin));

		GetEmployeeListOperation op = (GetEmployeeListOperation) operation;
		frm.setAllowedTB(op.getAllowedTB());
	}

	private ChangeLockEmployeeOperation getChangeLockEmployeeOperation(Long personId) throws BusinessException, BusinessLogicException
	{
		ChangeLockEmployeeOperation operation = createOperation(ChangeLockEmployeeOperation.class);
		operation.initialize(personId);
		return operation;
	}

	public ActionForward lock(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EmployeeListForm form = (EmployeeListForm) frm;
		String[] selectedIds = form.getSelectedIds();
		if (selectedIds.length == 0)
		{
			saveError(ActionMessages.GLOBAL_MESSAGE, "com.rssl.phizic.web.employees.noSelected", request);
			return start(mapping, form, request, response);
		}
		Map<String, Object> valuesForSource = new HashMap<String, Object>();
		valuesForSource.put(LockForm.BLOCK_START_DATE_FIELD_NAME, form.getBlockStartDate());
		valuesForSource.put(LockForm.BLOCK_END_DATE_FIELD_NAME, form.getBlockEndDate());
		valuesForSource.put(LockForm.BLOCK_REASON_FIELD_NAME, form.getBlockReason());

		MapValuesSource valuesSource = new MapValuesSource(valuesForSource);

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, LockForm.LOCK_FORM);

		if(processor.process())
		{

			Map<String, Object> processorResult = processor.getResult();
			Date startDate = (Date) processorResult.get(LockForm.BLOCK_START_DATE_FIELD_NAME);
			Date endDate = (Date) processorResult.get(LockForm.BLOCK_END_DATE_FIELD_NAME);
			String reason = (String) processorResult.get(LockForm.BLOCK_REASON_FIELD_NAME);
			for (int i = 0; i < selectedIds.length; i++)
			{
				ChangeLockEmployeeOperation operation = getChangeLockEmployeeOperation(Long.valueOf(selectedIds[i]));
				try
				{
					operation.lock(reason, startDate, endDate);
				}
				catch (BusinessLogicException e)
				{
					saveError(currentRequest(), e.getMessage());
				}
				finally
				{
					LogParametersReader beanLogReader = new BeanLogParemetersReader("Информация о блокируемом", operation.getEntity());
					FormLogParametersReader formLogParametersReader = new FormLogParametersReader("Параметры блокировки", LockForm.LOCK_FORM, valuesForSource);
					addLogParameters(new CompositeLogParametersReader(beanLogReader, formLogParametersReader));
				}
			}
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return start(mapping, form, request, response);
	}

	public ActionForward unlock(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EmployeeListForm form = (EmployeeListForm) frm;
		String[] selectedIds = form.getSelectedIds();
		if (selectedIds.length == 0)
		{
			saveError(ActionMessages.GLOBAL_MESSAGE, "com.rssl.phizic.web.employees.noSelected", request);
			return start(mapping, form, request, response);
		}

		for (int i = 0; i < selectedIds.length; i++)
		{
			ChangeLockEmployeeOperation operation = getChangeLockEmployeeOperation(Long.valueOf(selectedIds[i]));
			try
			{
				operation.unlock();
			}
			catch (BusinessLogicException e)
			{
				saveError(currentRequest(), e.getMessage());
			}
			finally
			{
				LogParametersReader beanLogReader = new BeanLogParemetersReader("Информация о разблокируемом", operation.getEntity());
				List<LoginBlock> blocks = operation.getBlocks();
				LogParametersReader blocksReader;
				if (CollectionUtils.isEmpty(blocks))
					blocksReader = new SimpleLogParametersReader("Снятые блокировки", "нет");
				else
					blocksReader = new CollectionLogParametersReader("Снятые блокировки", blocks);
				addLogParameters(new CompositeLogParametersReader(beanLogReader, blocksReader));
			}
		}
		return start(mapping, form, request, response);
	}

	/**
	 * Назначить выбранным пользователям шаблон прав доступа
	 * @throws Exception
	 */
	public ActionForward assignScheme(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		EmployeeListForm frm = (EmployeeListForm) form;
		String[] selectedIds = frm.getSelectedIds();
		Long schemeId = frm.getAccessSchemeId();

		if (schemeId == 0)
			schemeId = null;

		ActionMessages msgs = new ActionMessages();

		if (selectedIds.length == 0)
		{
			saveError(ActionMessages.GLOBAL_MESSAGE, "com.rssl.phizic.web.employees.noSelected", request);
		}
		else
		{
			for (int i = 0; i < selectedIds.length; i++)
			{
				Long personId = Long.parseLong(selectedIds[i]);
				AssignEmployeeAccessOperation operation = createOperation(AssignEmployeeAccessOperation.class);
				operation.initialize(personId);
				try
				{
					operation.setNewAccessSchemeId(schemeId);
					operation.assign();
				}
				catch(BusinessLogicException e)
				{
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
					saveErrors(request, msgs);
					return start(mapping, frm, request, response);
				}

				addLogParameters(new CompositeLogParametersReader(
						new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()),
						new SimpleLogParametersReader("Старое значение", "Название схемы прав", (operation.getOldAccessScheme()==null)? "Нет схемы прав" : operation.getOldAccessScheme().getName()),
						new SimpleLogParametersReader("Новое значение", "Название схемы прав",  (operation.getNewAccessScheme()==null)? "Нет схемы прав" : operation.getNewAccessScheme().getName())
						));
			}
		}
		saveErrors(request, msgs);
		frm.clearSelection();
		return start(mapping, frm, request, response);
	}
}
