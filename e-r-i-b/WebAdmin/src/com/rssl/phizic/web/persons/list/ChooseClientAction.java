package com.rssl.phizic.web.persons.list;

import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.gate.csa.Profile;
import com.rssl.phizic.operations.person.list.ChangeActivePersonLockOperation;
import com.rssl.phizic.operations.person.list.ChooseClientOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.LockForm;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 01.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Экшен выбора клиента для редактирования
 */

public class ChooseClientAction extends OperationalActionBase
{
	protected static final String PERSON_ID_PARAMETER = "person";
	private static final String PERSON_LIST_FORWARD = "PersonList";
	private static final String DATE_FORMAT = "dd.MM.yyyy";

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMap = new HashMap<String, String>();
		keyMap.put("button.unlock", "unlock");
		keyMap.put("button.lock", "lock");
		return keyMap;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ChooseClientForm frm = (ChooseClientForm) form;
		ChooseClientOperation operation = createOperation(ChooseClientOperation.class);
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()),ChooseClientForm.SEARCH_FORM);
		if(processor.process())
		{
			Profile profile = createUserProfile(processor.getResult());
			operation.initialize(profile);
			List<ActivePerson> clients = operation.getClients();
			frm.setData(clients);
		}
		else
		{
			saveErrors(currentRequest(), processor.getErrors());
		}
		return createActionForward(frm);
	}

	/**
	 * блокировка клиента
	 * @param mapping   сратс-маппинг
	 * @param frm       сратс-форма
	 * @param request   запрос
	 * @param response  ответ
	 * @return          сратс-форвард
	 * @throws Exception
	 */
	public ActionForward lock(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ChooseClientForm form = (ChooseClientForm) frm;
		String[] selectedIds = form.getSelectedIds();
		if (selectedIds.length != 1)
		{
			saveError(ActionMessages.GLOBAL_MESSAGE, "com.rssl.phizic.web.persons.selectOneUser", request);
			return start(mapping, form, request, response);
		}
		Long selectedId = Long.valueOf(selectedIds[0]);
		MapValuesSource valuesSource = new MapValuesSource(form.getFields());
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, LockForm.createForm());
		if(processor.process())
		{
			ChangeActivePersonLockOperation operation = createOperation(ChangeActivePersonLockOperation.class);
			Map<String, Object> processorResult = processor.getResult();
			Date startDate = (Date) processorResult.get(LockForm.BLOCK_START_DATE_FIELD_NAME);
			Date endDate = (Date) processorResult.get(LockForm.BLOCK_END_DATE_FIELD_NAME);
			String reason = (String) processorResult.get(LockForm.BLOCK_REASON_FIELD_NAME);

			UserInfo userInfo = getUserInfo(form);
			operation.initialize(selectedId,userInfo);
			operation.lock(reason,startDate,endDate);
		}
		else
		{
			saveErrors(request, processor.getErrors());
			return mapping.findForward(FORWARD_START);
		}
		return mapping.findForward(PERSON_LIST_FORWARD);
	}

	/**
	 * блокировка клиента
	 * @param mapping   сратс-маппинг
	 * @param frm       сратс-форма
	 * @param request   запрос
	 * @param response  ответ
	 * @return          сратс-форвард
	 * @throws Exception
	 */
	public ActionForward unlock(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ChooseClientForm form = (ChooseClientForm) frm;
		String[] selectedIds = form.getSelectedIds();
		if (selectedIds.length != 1)
		{
			saveError(ActionMessages.GLOBAL_MESSAGE, "com.rssl.phizic.web.persons.selectOneUser", request);
			return start(mapping, form, request, response);
		}
		Long selectedId = Long.valueOf(selectedIds[0]);

		ChangeActivePersonLockOperation operation = createOperation(ChangeActivePersonLockOperation.class);
		UserInfo userInfo = getUserInfo(form);
		operation.initialize(selectedId,userInfo);
		operation.unlock();

		return mapping.findForward(PERSON_LIST_FORWARD);
	}

	private Profile createUserProfile(Map<String,Object> clientInfo)
	{
		Profile profile = new Profile();
		profile.setSurName((String) clientInfo.get("surname"));
		profile.setFirstName((String) clientInfo.get("firstname"));
		profile.setPatrName((String) clientInfo.get("patrname"));
		profile.setPassport((String) clientInfo.get("passport"));
		profile.setBirthDay(DateHelper.toCalendar((Date) clientInfo.get("birthDay")));
		profile.setTb((String)clientInfo.get("tb"));
		return profile;
	}

	private UserInfo getUserInfo(ChooseClientForm form) throws BusinessException
	{
		try
		{
			UserInfo userInfo = new UserInfo();
			userInfo.setFirstname((String)form.getField("firstname"));
			userInfo.setPatrname((String) form.getField("patrname"));
			userInfo.setSurname((String) form.getField("surname"));
			String dateString = (String) form.getField("birthDay");
			userInfo.setBirthdate(StringHelper.isEmpty(dateString)? null: DateHelper.parseCalendar(dateString, DATE_FORMAT));
			userInfo.setPassport((String) form.getField("passport"));
			userInfo.setTb((String) form.getField("tb"));
			return userInfo;
		}
		catch (ParseException e)
		{
			throw new BusinessException(e);
		}
	}

	protected ActionForward createActionForward(ChooseClientForm form)
	{
		List<ActivePerson> clients = form.getData();
		if (clients.size() != 1)
			return getCurrentMapping().findForward(FORWARD_START);

		ActionForward editForward = getCurrentMapping().findForward(FORWARD_SHOW_FORM);
		ActionRedirect redirect = new ActionRedirect(editForward.getPath());
		redirect.addParameter(PERSON_ID_PARAMETER, clients.get(0).getId());
		return redirect;
	}
}
