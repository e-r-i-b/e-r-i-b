package com.rssl.phizic.web.persons.list;

import com.rssl.auth.csa.wsclient.exceptions.CheckIMSIException;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.ermb.ErmbStatus;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.csaadmin.node.LastNodeInfoOperation;
import com.rssl.phizic.operations.ext.sbrf.person.RestoreClientPasswordOperation;
import com.rssl.phizic.operations.person.list.ChangeLockClientOperation;
import com.rssl.phizic.operations.person.list.ListClientOperation;
import com.rssl.phizic.operations.person.list.TooManyActivePersonsException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.LockForm;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.SaveFilterParameterHelper;
import org.apache.commons.collections.MapUtils;
import org.apache.struts.action.*;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 24.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Экшен получения списка клиентов из ЦСА Бек
 */

public class ListClientAction extends OperationalActionBase
{
	public static final String PAGINATION_SIZE_PARAMETER = "$$pagination_size0";
	public static final String PAGINATION_OFFSET_PARAMETER = "$$pagination_offset0";
	public static final int DEFAULT_SIZE_VALUE = 20;
	public static final int DEFAULT_OFFSET_VALUE = 0;
	public static final String DATE_FORMAT = "dd.MM.yyyy";
	private static final String CHANGE_NODE_FORWARD = "ChangeNode";
	private static final String CHOOSE_PERSON_FORWARD = "ChoosePerson";


	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.restore.password", "checkIMSI");
		map.put("button.ignoreIMSICheck", "restorePassword");
		map.put("button.unlock", "unlock");
		map.put("button.lock", "lock");
		map.put("button.filter", "filter");
		return map;
	}

	public final ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListClientForm frm = (ListClientForm) form;
		if(frm.isLock())
		{
			frm.setLock(false);
			restoreFormParameters(frm);
			return lock(mapping,form,request,response);
		}
		if(frm.isUnlock())
		{
			frm.setUnlock(false);
			restoreFormParameters(frm);
			return unlock(mapping, form, request, response);
		}

		frm.setFromStart(true);
		ListClientOperation operation = createListOperation();
		updateFormAdditionalData(frm, operation);
		frm.setFilters(SaveFilterParameterHelper.loadFilterParameters(frm, operation));
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Восстановление пароля
	 * @param mapping   сратс-маппинг
	 * @param form       сратс-форма
	 * @param request   запрос
	 * @param response  ответ
	 * @return          сратс-форвард
	 * @throws Exception
	 */
	public final ActionForward filter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListClientForm frm = (ListClientForm) form;
		ListClientOperation operation = createListOperation();
		Map<String, Object> filterParams = frm.getFilters();
		if(MapUtils.isEmpty(filterParams))
		{
			filterParams = SaveFilterParameterHelper.loadFilterParameters(frm, operation);
		}
		FieldValuesSource fieldValuesSource = new MapValuesSource(filterParams);
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(fieldValuesSource, ListClientForm.FILTER_FORM);
		if(processor.process())
		{
			doFilter(processor.getResult(), operation, frm);
			SaveFilterParameterHelper.saveFilterParameters(processor.getResult());
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return mapping.findForward(FORWARD_START);
	}


	private ListClientOperation createListOperation() throws BusinessException
	{
		ListClientOperation operation = createOperation(ListClientOperation.class);
		operation.initialize();
		return operation;
	}

	private void updateFormAdditionalData(ListClientForm form, ListClientOperation operation) throws Exception
	{
		form.setAllowedTB(operation.getAllowedTB());
	}

	private void doFilter(Map<String, Object> filterParams, ListClientOperation operation, ListClientForm form) throws Exception
	{
		operation.setFio((String) filterParams.get(ListClientForm.FIO_FIELD_NAME));
		operation.setDocument((String) filterParams.get(ListClientForm.DOCUMENT_FIELD_NAME));
		operation.setBirthday(DateHelper.toCalendar((Date) filterParams.get(ListClientForm.BIRTHDAY_FIELD_NAME)));
		operation.setLogin((String) filterParams.get(ListClientForm.LOGIN_FIELD_NAME));
		operation.setAgreementNumber((String) filterParams.get(ListClientForm.AGREEMENT_NUMBER_FIELD_NAME));
		operation.setCreationType((CreationType) filterParams.get(ListClientForm.CREATION_TYPE_FIELD_NAME));
		operation.setPhoneNumber((String) filterParams.get(ListClientForm.MOBILE_PHONE_FIELD_NAME));
		operation.setTb((String) filterParams.get(ListClientForm.TB_FIELD_NAME));
		operation.setMaxResults(getPaginationParameter(PAGINATION_SIZE_PARAMETER, DEFAULT_SIZE_VALUE) + 1);
		operation.setFirstResult(getPaginationParameter(PAGINATION_OFFSET_PARAMETER, DEFAULT_OFFSET_VALUE));
		form.setData(operation.getClientList());
		form.setFilters(filterParams);
		updateFormAdditionalData(form, operation);
	}

	private int getPaginationParameter(String key, int defaultValue)
	{
		String parameter = (String) currentRequest().getAttribute(key);
		if (StringHelper.isEmpty(parameter))
			return defaultValue;

		return Math.max(Integer.parseInt(parameter), 0);
	}

	/**
	 * Восстановление пароля
	 * @param mapping   сратс-маппинг
	 * @param frm       сратс-форма
	 * @param request   запрос
	 * @param response  ответ
	 * @return          сратс-форвард
	 * @throws Exception
	 */
	@SuppressWarnings("UnusedDeclaration")
	public ActionForward checkIMSI(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListClientForm form = (ListClientForm) frm;
		String id = form.getSelectedIds()[0];
		String login = (String) form.getField("login" + id);
		RestoreClientPasswordOperation operation = createOperation(RestoreClientPasswordOperation.class);

		if (ErmbStatus.NOT_CONNECTED == getErmbStatus((String) form.getField("ermbStatus")))
		{
			return restorePassword(mapping, form, request, response);
		}

		try
		{
			operation.checkIMSI(login);
		}
		catch (CheckIMSIException e)
		{
			log.warn("Ошибка проверки IMSI", e);
			form.setFailureIMSICheck(true);
			form.setSelectedIds(new String[]{id});
			return filter(mapping, form, request, response);
		}
		catch(BusinessLogicException e)
		{
			saveError(request,e.getMessage());
			return filter(mapping, form, request, response);
		}

		return restorePassword(mapping, form, request, response);
	}

	private ErmbStatus getErmbStatus(String ermbStatus)
	{
		if (StringHelper.isEmpty(ermbStatus))
		{
			return ErmbStatus.NOT_CONNECTED;
		}
		return ErmbStatus.valueOf(ermbStatus);
	}

	/**
	 * Восстановление пароля
	 * @param mapping   сратс-маппинг
	 * @param frm       сратс-форма
	 * @param request   запрос
	 * @param response  ответ
	 * @return сратс-форвард
	 * @throws Exception
	 */
	public ActionForward restorePassword(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListClientForm form = (ListClientForm)frm;
		String id = form.getSelectedIds()[0];
		String login = (String) form.getField("login" + id);
		boolean failureIMSICheck = Boolean.parseBoolean((String)form.getField("failureIMSICheck"));
		RestoreClientPasswordOperation operation = createOperation(RestoreClientPasswordOperation.class);
		try
		{
			operation.restorePassword(login, failureIMSICheck);
		}
		catch(BusinessLogicException e)
		{
			saveError(request,e.getMessage());
			return start(mapping, form, request, response);
		}
		addLogParameters(new SimpleLogParametersReader("Логин востанавливаемого пароля",login));
		saveMessage(request, "Новый пароль будет отправлен клиенту по SMS");
		return start(mapping, form, request, response);
	}

	private UserInfo getUserInfo(Long profileId, ListClientForm form) throws BusinessException
	{
		try
		{
			UserInfo userInfo = new UserInfo();
			userInfo.setFirstname((String)form.getField("firstName" + profileId));
			userInfo.setPatrname((String) form.getField("patrname" + profileId));
			userInfo.setSurname((String) form.getField("surname" + profileId));
			String dateString = (String) form.getField("birthDate" + profileId);
			userInfo.setBirthdate(StringHelper.isEmpty(dateString)? null: DateHelper.parseCalendar(dateString, DATE_FORMAT));
			userInfo.setPassport((String) form.getField("passport" + profileId));
			userInfo.setTb((String) form.getField("tb" + profileId));
			return userInfo;
		}
		catch (ParseException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * блокировка клиентов
	 * @param mapping   сратс-маппинг
	 * @param frm       сратс-форма
	 * @param request   запрос
	 * @param response  ответ
	 * @return          сратс-форвард
	 * @throws Exception
	 */
	public ActionForward lock(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListClientForm form = (ListClientForm) frm;
		String[] selectedIds = form.getSelectedIds();

		if (selectedIds.length != 1)
		{
			saveError(ActionMessages.GLOBAL_MESSAGE, "com.rssl.phizic.web.persons.selectOneUser", request);
			return filter(mapping, form, request, response);
		}
		Long selectedProfileId = Long.valueOf(selectedIds[0]);

		Map<String, String> valuesForSource = new HashMap<String, String>();
		valuesForSource.put(LockForm.BLOCK_START_DATE_FIELD_NAME, form.getBlockStartDate());
		valuesForSource.put(LockForm.BLOCK_END_DATE_FIELD_NAME,   form.getBlockEndDate());
		valuesForSource.put(LockForm.BLOCK_REASON_FIELD_NAME,     form.getBlockReason());

		MapValuesSource valuesSource = new MapValuesSource(valuesForSource);
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, LockForm.createForm());

		if(processor.process())
		{
			Long profileNodeId = Long.valueOf((String) form.getField("nodeId" + selectedProfileId));
			if(!checkNode(profileNodeId))
				return redirectToNode(profileNodeId,"lock",form,request);
			Map<String, Object> processorResult = processor.getResult();
			Date startDate = (Date) processorResult.get(LockForm.BLOCK_START_DATE_FIELD_NAME);
			Date endDate = (Date) processorResult.get(LockForm.BLOCK_END_DATE_FIELD_NAME);
			String reason = (String) processorResult.get(LockForm.BLOCK_REASON_FIELD_NAME);

			UserInfo userInfo = getUserInfo(selectedProfileId,form);
			try
			{
				ChangeLockClientOperation operation = createOperation(ChangeLockClientOperation.class);
				operation.initialize(userInfo);
				operation.lock(reason, startDate, endDate);
			}
			catch (TooManyActivePersonsException ignore)
			{
				return redirectToChoosePerson(userInfo, form);
			}
			catch (BusinessLogicException e)
			{
				saveError(currentRequest(), e.getMessage());
			}
			finally
			{
				CompositeLogParametersReader reader = new CompositeLogParametersReader();
				reader.add(new BeanLogParemetersReader("Блокируемый клиент", userInfo));
				String lockParametersLogValue = "Начало:" + form.getBlockStartDate();
				if (StringHelper.isNotEmpty(form.getBlockEndDate()))
					lockParametersLogValue += "; Окончание: " + form.getBlockEndDate();
				lockParametersLogValue += "; Причина: " + form.getBlockReason();
				SimpleLogParametersReader lockParametersLogReader = new SimpleLogParametersReader("Параметры блокировки", lockParametersLogValue);
				reader.add(lockParametersLogReader);
				addLogParameters(reader);
			}
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return filter(mapping, form, request, response);
	}

	/**
	 * разблокировка
	 * @param mapping   сратс-маппинг
	 * @param frm       сратс-форма
	 * @param request   запрос
	 * @param response  ответ
	 * @return          сратс-форвард
	 * @throws Exception
	 */
	public ActionForward unlock(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListClientForm form = (ListClientForm) frm;
		String[] selectedIds = form.getSelectedIds();

		if (selectedIds.length != 1)
		{
			saveError(ActionMessages.GLOBAL_MESSAGE, "com.rssl.phizic.web.persons.selectOneUser", request);
			return filter(mapping, form, request, response);
		}
		Long selectedProfileId = Long.valueOf(selectedIds[0]);

		UserInfo userInfo = getUserInfo(selectedProfileId,form);
		try
		{
			Long profileNodeId = Long.valueOf((String) form.getField("nodeId" + selectedProfileId));
			if(!checkNode(profileNodeId))
				return redirectToNode(profileNodeId,"unlock",form,request);
			ChangeLockClientOperation operation = createOperation(ChangeLockClientOperation.class);
			operation.initialize(userInfo);
			operation.unlock();
		}
		catch(TooManyActivePersonsException ignore)
		{
			return redirectToChoosePerson(userInfo, form);
		}
		catch (BusinessLogicException e)
		{
			saveError(currentRequest(), e.getMessage());
		}
		finally
		{
			addLogParameters(new BeanLogParemetersReader("Разблокируемый клиент", userInfo));
		}

		return filter(mapping, form, request, response);
	}

	private boolean checkNode(Long profileNodeId)
	{
		return ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber().equals(profileNodeId);
	}

	private ActionRedirect redirectToNode(Long profileNodeId, String methodName, ListClientForm form, HttpServletRequest request) throws BusinessException, BusinessLogicException
	{
		LastNodeInfoOperation operation = createOperation(LastNodeInfoOperation.class);
		Map<String, Object> operationContext = new HashMap<String, Object>();
		operationContext.put(LockForm.BLOCK_START_DATE_FIELD_NAME,form.getBlockStartDate());
		operationContext.put(LockForm.BLOCK_END_DATE_FIELD_NAME,form.getBlockEndDate());
		operationContext.put(LockForm.BLOCK_REASON_FIELD_NAME,form.getBlockReason());
		operationContext.put("filters",form.getFilters());
		operationContext.put("fields",form.getFields());
		operationContext.put("selectedIds",form.getSelectedIds());
		operation.saveOperationContext(operationContext);

		ActionRedirect redirect = new ActionRedirect(getCurrentMapping().findForward(CHANGE_NODE_FORWARD));
		redirect.addParameter("nodeId",profileNodeId);
		redirect.addParameter("action",request.getServletPath());
		redirect.addParameter("parameters("+methodName+")","true");
		return redirect;
	}

	private void restoreFormParameters(ListClientForm form) throws BusinessException, BusinessLogicException
	{
		LastNodeInfoOperation operation = createOperation(LastNodeInfoOperation.class);
		Map<String,Object> operationContext = operation.getOperationContext();
		form.setBlockStartDate((String)operationContext.get(LockForm.BLOCK_START_DATE_FIELD_NAME));
		form.setBlockEndDate((String) operationContext.get(LockForm.BLOCK_END_DATE_FIELD_NAME));
		form.setBlockReason((String) operationContext.get(LockForm.BLOCK_REASON_FIELD_NAME));
		form.setFilters((Map<String,Object>)operationContext.get("filters"));
		form.setFields((Map<String, Object>) operationContext.get("fields"));
		form.setSelectedIds((String[]) operationContext.get("selectedIds"));
	}

	private ActionRedirect redirectToChoosePerson(UserInfo userInfo, ListClientForm form)
	{
		UrlBuilder urlBuilder = new UrlBuilder();
		ActionForward forward = getCurrentMapping().findForward(CHOOSE_PERSON_FORWARD);
		urlBuilder.setUrl(forward.getPath());

		urlBuilder.addParameter("field(firstname)",userInfo.getFirstname());
		urlBuilder.addParameter("field(surname)",userInfo.getSurname());
		urlBuilder.addParameter("field(patrname)",userInfo.getPatrname());
		urlBuilder.addParameter("field(birthDay)",DateHelper.toString(userInfo.getBirthdate().getTime()));
		urlBuilder.addParameter("field(passport)",userInfo.getPassport());
		urlBuilder.addParameter("field(tb)",userInfo.getTb());

		urlBuilder.addParameter("field(blockStartDate)", form.getBlockStartDate());
		if(StringHelper.isNotEmpty(form.getBlockEndDate()))
			urlBuilder.addParameter("field(blockEndDate)",   form.getBlockEndDate());
		urlBuilder.addParameter("field(blockReason)",    form.getBlockReason());
		return new ActionRedirect(urlBuilder.getUrl());
	}
}
