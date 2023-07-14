package com.rssl.phizic.web.persons;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.BlockingReasonDescriptor;
import com.rssl.phizic.auth.BlockingReasonType;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.access.ChangeLockPersonOperation;
import com.rssl.phizic.operations.access.PersonLoginSource;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.password.UserBlocksValidator;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.LockForm;
import org.apache.struts.action.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Evgrafov
 * @ created 10.08.2006
 * @ $Author: komarov $
 * @ $Revision: 83946 $
 */

public abstract class EditPersonActionBase extends PersonActionBase
{
	private static final String NOT_AVAILABLE_STATE = "com.rssl.phizic.web.persons.lock.change.fail";
	private static final String NOT_AVAILABLE_UNLOCK = "com.rssl.phizic.web.persons.lock.info.reason.not.unlock";

	private static final PersonFormBuilder personFormBuilder = new PersonFormBuilder();
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

	/**
	 * Сохранить изменения
	 */
	public abstract ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	        throws Exception;

    /**
     * Расторгнуть договор
     */
    public abstract ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception;


	protected PersonFormBuilder getFormBuilder()
	{
		return personFormBuilder;
	}

	/**
	 *  Заблокировать
	 */
	public ActionForward lock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditPersonForm frm = (EditPersonForm) form;
		ChangeLockPersonOperation operation = null;
		try
		{
			operation = createOperation(ChangeLockPersonOperation.class);

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

				PersonLoginSource loginSource = new PersonLoginSource(frm.getEditedPerson());
				ActivePerson person = loginSource.getPerson();
				String state = person.getStatus();
				String stateDescription = STATE_MAPPING.get(state);
				if (StringHelper.isNotEmpty(stateDescription))
				{
					saveError(currentRequest(), new ActionMessage(NOT_AVAILABLE_STATE, person.getFullName(), stateDescription));
					return start(mapping, form, request, response);
				}

				operation.initialize(loginSource);
				if (!USER_BLOCKS_VALIDATOR.checkIfBlockOfThisTypeDoesntExist(loginSource.getLogin(), BlockingReasonType.employee))
					saveError(currentRequest(), EXIST_LOCK_MESSAGE_MAPPING.get(endDate == null));
				else
					operation.lock(reason, startDate, endDate);
			}
			else
			{
				saveErrors(request, processor.getErrors());
			}

		}
		catch(BusinessLogicException e)
		{
			saveError(currentRequest(), e.getMessage());
		}
		finally
		{
			addLogParameters(new SimpleLogParametersReader(operation.getLogInfo()));
		}

		stopLogParameters();
	    return start(mapping, form, request, response);
	}

	/**
	 *  Разблокировать
	 */
	public ActionForward unlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	        throws Exception
	{
	    EditPersonForm frm = (EditPersonForm) form;
		ChangeLockPersonOperation operation = createOperation(ChangeLockPersonOperation.class);
		PersonLoginSource loginSource = new PersonLoginSource(frm.getEditedPerson());
		ActivePerson person = loginSource.getPerson();
		String state = person.getStatus();
		String stateDescription = STATE_MAPPING.get(state);
		if (StringHelper.isNotEmpty(stateDescription))
		{
			saveError(currentRequest(), new ActionMessage(NOT_AVAILABLE_STATE, person.getFullName(), stateDescription));
			return start(mapping, form, request, response);
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
		return start(mapping, form, request, response);
	}

	protected Map<String,String> getAdditionalKeyMethodMap()
	{
	    Map<String,String> map = super.getAdditionalKeyMethodMap();

	    map.put("button.lock"                   , "lock");
	    map.put("button.unlock"                 , "unlock");
	    map.put("button.partly.save.person"     , "save");
	    map.put("button.save.person"            , "save");
		map.put("button.activate.person"        , "activate");
		map.put("button.register.changes.person", "register");		
		map.put("button.remove"                 , "remove");
		map.put("button.remove.template.person" , "removeTemplatePerson");
		map.put("button.sign.agreement"         , "agreementSigned");
		map.put("button.edit.return"            , "returnToEdit");		
		map.put("button.print"                  , "returnToEdit");
		return map;
	}

}