package com.rssl.phizic.web.persons;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.BlockingReasonDescriptor;
import com.rssl.phizic.auth.BlockingReasonType;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.InvalidPersonStateException;
import com.rssl.phizic.business.persons.clients.UserImpl;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.gate.clients.User;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.access.*;
import com.rssl.phizic.operations.person.GetPersonsListOperation;
import com.rssl.phizic.operations.person.RemovePersonOperation;
import com.rssl.phizic.operations.person.RemovePersonOperationBase;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.password.UserBlocksValidator;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.LockForm;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 08.09.2005
 * Time: 21:17:45
 * todo: перейти на формы
 */
@SuppressWarnings({"JavaDoc"})
public class ListPersonsAction extends SaveFilterParameterAction
{
	private static final String NOT_AVAILABLE_STATE = "com.rssl.phizic.web.persons.lock.change.fail";
	private static final String NOT_AVAILABLE_UNLOCK = "com.rssl.phizic.web.persons.lock.info.reason.not.unlock";

	private static final UserBlocksValidator USER_BLOCKS_VALIDATOR = new UserBlocksValidator();

	private static final Map<String, String> STATE_MAPPING = new HashMap<String, String>(2);
	private static final Map<Boolean, String> EXIST_LOCK_MESSAGE_MAPPING = new HashMap<Boolean, String>(2);

	static
	{
		STATE_MAPPING.put(Person.TEMPLATE, "Подключение");
		STATE_MAPPING.put(Person.SIGN_AGREEMENT, "Подписание договора");
		EXIST_LOCK_MESSAGE_MAPPING.put(true, "Данный клиент заблокирован. Снимите существующую блокировку и назначьте клиенту бессрочную блокировку.");
		EXIST_LOCK_MESSAGE_MAPPING.put(false, "Данный клиент заблокирован. Снимите существующую блокировку и назначьте клиенту временную блокировку.");
	}

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();

		map.put("button.assignScheme.simple", "assignSimpleScheme");
		map.put("button.assignScheme.secure", "assignSecureScheme");
		map.put("button.assignScheme", "assignScheme");
		map.put("button.unlock", "unlock");
		map.put("button.lock", "lock");
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		GetPersonsListOperation operation = null;
		if (checkAccess(GetPersonsListOperation.class, "PersonsViewing"))
			operation = createOperation(GetPersonsListOperation.class, "PersonsViewing");
		else if (checkAccess(GetPersonsListOperation.class, "PersonManagement"))
			operation = createOperation(GetPersonsListOperation.class, "PersonManagement");
		else
			operation = createOperation(GetPersonsListOperation.class, "MailManagment");
		return operation;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemovePersonOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListPersonsForm.FILTER_FORM;
	}
	/**
	 * Удаление выбранных пользователей
	 */
	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		try
		{
	        ((RemovePersonOperationBase) operation).setCurrentUser(getCurrentUser());
			super.doRemove(operation, id);
		}
		catch (InvalidPersonStateException ex)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.persons.cantDeleteActiveUser", ex.getPerson().getFullName()));
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}
		return msgs;
	}

	private User getCurrentUser()
	{
		try
		{
			Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
			UserImpl user = new UserImpl();
			user.fillFromEmployee(employee);
			return user;
		}
		catch (BusinessException e)
		{
			log.error("Ошибка при попытке определить данные текущего пользователя.", e);
			return null;
		}
	}

	private void addNoSelectedUserMessage(HttpServletRequest request)
	{
		saveError(request, new ActionMessage("com.rssl.phizic.web.persons.noSelectedUsers"));
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
        Date curTime = Calendar.getInstance().getTime();
		String state = (String) filterParams.get("state");

		String filterState = null;
		Integer blocked = null;

		if (state == null || state.equals(""))
		{
			filterState = null;
			blocked = null;
		}
		else if (state.equals("0"))
		{
			blocked = 0;
			filterState = Person.ACTIVE;
		}
		else if (state.equals("1"))
		{
			blocked = 1;
			filterState = Person.ACTIVE;
		}
		else
		{
			filterState = state;
		}

		query.setParameter("blockedUntil",curTime);
		query.setParameter("pinEnvelopeNumber", StringHelper.getEmptyIfNull(filterParams.get("pinEnvelopeNumber")));
		query.setParameter("blocked",blocked);
		query.setParameter("state",filterState);
		query.setParameter("agreementNumber",   filterParams.get("agreementNumber"));
		query.setParameter("patrName",          filterParams.get("patrName"));
		query.setParameter("firstName",         filterParams.get("firstName"));
		query.setParameter("surName",           filterParams.get("surName"));
		query.setParameter("documentNumber",    filterParams.get("documentNumber"));

		String  documentSeries = (String)filterParams.get("documentSeries");
		query.setParameter("documentSeries",    documentSeries == null ? documentSeries : documentSeries.replace(" ", ""));
		
		query.setParameter("documentType",      filterParams.get("documentType"));
		query.setParameter("creationType",      filterParams.get("creationType"));
		query.setParameter("birthDay",          filterParams.get("birthDay"));
		query.setParameter("terBank",           filterParams.get("terBank"));
		query.setMaxResults(webPageConfig().getListLimit()+1);
	}


	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException
	{
		ListPersonsForm frm = (ListPersonsForm) form;
		frm.setAccessSchemes(CategoryAssignAccessHelper.createClient().getSchemes());
	}

	/**
	 * Назначить выбранным пользователям шаблон прав доступа (упрощенный доступ)
	 * @throws Exception
	 */
	public ActionForward assignSimpleScheme(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		assignScheme(form, request, AccessType.simple);
		return filter(mapping,form,request,response);
	}

	/**
	 * Назначить выбранным пользователям шаблон прав доступа (защищенный доступ)
	 * @throws Exception
	 */
	public ActionForward assignSecureScheme(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		assignScheme(form, request, AccessType.secure);
		return filter(mapping,form,request,response);
	}


	/**
	 * Назначить выбранным пользователям шаблон прав доступа (упрощенный доступ)
	 * @throws Exception
	 */
	public ActionForward assignScheme(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListPersonsForm frm = (ListPersonsForm) form;
		if (frm.getAccessSchemeId().startsWith("secure_"))
		{
			assignScheme(form, request, AccessType.secure);
		}
		else
		{
			assignScheme(form, request, AccessType.simple);
		}
		return filter(mapping,form,request,response);
	}

	private void assignScheme(ActionForm form, HttpServletRequest request, AccessType accessType)
			throws BusinessException, BusinessLogicException
	{
		ListPersonsForm frm = (ListPersonsForm) form;
		String[] selectedIds = frm.getSelectedIds();
		String schemeStringId = frm.getAccessSchemeId();

		Long schemeId = null;

		if (!StringHelper.isEmpty(schemeStringId))
		{
			if (schemeStringId.startsWith("secure_") || schemeStringId.startsWith("simple_"))
			{
				schemeStringId = schemeStringId.substring(7);
				schemeId = (Long.parseLong(schemeStringId)==0)?null:Long.parseLong(schemeStringId);
			}
			else
			{
				throw new BusinessException("Неизвестный тип схемы прав");
			}
		}
		if (selectedIds.length == 0)
		{
			addNoSelectedUserMessage(request);
		}
		else
		{

			for (String selectedId : selectedIds)
			{
				Long personId = Long.parseLong(selectedId);
				AssignAccessOperation operation = createAssignAccessSchemeOperation(personId, accessType);

				try
				{
					operation.setNewAccessSchemeId(schemeId);
				}
				catch (BusinessLogicException e)
				{
					saveSessionError(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false), null);
					frm.clearSelection();
					return;
				}
				
				operation.assign();

				addLogParameters(new CompositeLogParametersReader(
						new BeanLogParemetersReader("Обрабатываемая сущность", ((AssignPersonAccessOperation) operation).getPerson()),
						new SimpleLogParametersReader("Шаблон прав доступа", (operation.getNewAccessScheme()==null)? "Нет схемы прав":operation.getNewAccessScheme().getName())
					));
			}
		}

		frm.clearSelection();
	}

	private void processLock(Long personId, Date startDate, Date endDate, String reason) throws BusinessException, BusinessLogicException
	{
		ChangeLockPersonOperation operation = createOperation(ChangeLockPersonOperation.class);
		PersonLoginSource loginSource = new PersonLoginSource(personId);
		ActivePerson person = loginSource.getPerson();
		String state = person.getStatus();
		String stateDescription = STATE_MAPPING.get(state);
		if (StringHelper.isNotEmpty(stateDescription))
		{
			saveError(currentRequest(), new ActionMessage(NOT_AVAILABLE_STATE, person.getFullName(), stateDescription));
			return;
		}
		try
		{
			operation.initialize(loginSource);
			if (!USER_BLOCKS_VALIDATOR.checkIfBlockOfThisTypeDoesntExist(loginSource.getLogin(), BlockingReasonType.employee))
				saveError(currentRequest(), EXIST_LOCK_MESSAGE_MAPPING.get(endDate == null));
			else
				operation.lock(reason, startDate, endDate);
		}
		catch (BusinessLogicException e)
		{
			saveError(currentRequest(), e.getMessage());
		}
		finally
		{
			addLogParameters(new SimpleLogParametersReader(operation.getLogInfo()));
		}
	}

	public ActionForward lock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListPersonsForm frm = (ListPersonsForm) form;
		String[] selectedIds = frm.getSelectedIds();

		if (selectedIds.length == 0)
		{
			addNoSelectedUserMessage(request);
			return start(mapping, form, request, response);
		}

		Map<String, String> valuesForSource = new HashMap<String, String>();
		valuesForSource.put(LockForm.BLOCK_START_DATE_FIELD_NAME,  frm.getBlockStartDate());
		valuesForSource.put(LockForm.BLOCK_END_DATE_FIELD_NAME,    frm.getBlockEndDate());
		valuesForSource.put(LockForm.BLOCK_REASON_FIELD_NAME,      frm.getBlockReason());
		MapValuesSource valuesSource = new MapValuesSource(valuesForSource);

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, LockForm.createForm());

		if(processor.process())
		{
			Map<String, Object> processorResult = processor.getResult();
			Date startDate = (Date) processorResult.get(LockForm.BLOCK_START_DATE_FIELD_NAME);
			Date endDate = (Date) processorResult.get(LockForm.BLOCK_END_DATE_FIELD_NAME);
			String reason = (String) processorResult.get(LockForm.BLOCK_REASON_FIELD_NAME);

			for (String selectedId : selectedIds)
			{
				Long personId = Long.valueOf(selectedId);
				processLock(personId, startDate, endDate, reason);
			}
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return start(mapping, form, request, response);
	}

	private void processUnlock(Long personId) throws BusinessException, BusinessLogicException
	{
		ChangeLockPersonOperation operation = createOperation(ChangeLockPersonOperation.class);
		PersonLoginSource loginSource = new PersonLoginSource(personId);
		ActivePerson person = loginSource.getPerson();
		String state = person.getStatus();
		String stateDescription = STATE_MAPPING.get(state);
		if (StringHelper.isNotEmpty(stateDescription))
		{
			saveError(currentRequest(), new ActionMessage(NOT_AVAILABLE_STATE, person.getFullName(), stateDescription));
			return;
		}
		try
		{
			operation.initialize(loginSource);
			if (USER_BLOCKS_VALIDATOR.unpossibleUnlock(person.getStatus(), loginSource.getLogin(), BlockingReasonType.system))
				saveError(currentRequest(), new ActionMessage(NOT_AVAILABLE_UNLOCK, person.getFullName(), BlockingReasonDescriptor.getReasonText(BlockingReasonType.system)));
			else
				operation.unlock();
		}
		catch (BusinessLogicException e)
		{
			saveError(currentRequest(), e.getMessage());
		}
		finally
		{
			addLogParameters(new SimpleLogParametersReader(operation.getLogInfo()));
		}
	}

	public ActionForward unlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListPersonsForm frm = (ListPersonsForm) form;
		String[] selectedIds = frm.getSelectedIds();

		if (selectedIds.length == 0)
		{
			addNoSelectedUserMessage(request);
			return start(mapping, form, request, response);
		}

		for (String selectedId : selectedIds)
		{
			Long personId = Long.valueOf(selectedId);
			processUnlock(personId);
		}
		return start(mapping, form, request, response);
	}

	private AssignAccessOperation createAssignAccessSchemeOperation(Long personId, AccessType accessType) throws BusinessException, BusinessLogicException
	{
		AssignPersonAccessOperation operation = createOperation(AssignPersonAccessOperation.class);
		AssignAccessHelper assignAccessHelper = CategoryAssignAccessHelper.createClient();
		operation.initialize(new PersonLoginSource(personId), accessType, assignAccessHelper);
		return operation;
	}
}
