package com.rssl.phizic.web.client.ext.sbrf.mobilebank.ermb;

import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.type.TimeZone;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.DaysOfWeek;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.account.GetAccountsOperation;
import com.rssl.phizic.operations.card.GetCardsOperation;
import com.rssl.phizic.operations.ermb.person.EditErmbOperation;
import com.rssl.phizic.operations.ermb.person.ErmbProfileOperationEntity;
import com.rssl.phizic.operations.loans.loan.GetLoanListOperation;
import com.rssl.phizic.operations.push.CallBackHandlerPushImpl;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.PhoneEncodeUtils;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;

import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author EgorovaA
 * @ created 25.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class ErmbListRegistrationsAction extends OperationalActionBase
{
	protected static final String FORWARD_SUCCESS = "Success";
	private static final String EMPTY_CHANGE_MESSAGE = "Вы не внесли никаких изменений в настройки подключения мобильного банка.";
	private static final String EMPTY_PHONE_MESSAGE = "Нужно добавить хотябы один телефон.";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> keys = new HashMap<String,String>();
		keys.put("button.save", "saveChanges");
		keys.put("button.preConfirmSMS", "preConfirmSMS");
		keys.put("button.preConfirmPush", "preConfirmPush");
		keys.put("button.confirm", "confirmChanges");
		keys.put("button.nextStage", "doNextStage");
		return keys;
	}

	public ActionForward preConfirmSMS( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		ErmbListRegistrationsForm frm = (ErmbListRegistrationsForm) form;
		EditErmbOperation operation = getOperation(request);
		operation.setUserStrategyType(ConfirmStrategyType.sms);
		frm.setConfirmableObject(operation.getConfirmableObject());
		updateForm(frm, operation);
		preConfirmAction(operation, request, new CallBackHandlerSmsImpl());
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward preConfirmPush( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		ErmbListRegistrationsForm frm = (ErmbListRegistrationsForm) form;
		EditErmbOperation operation = getOperation(request);
		operation.setUserStrategyType(ConfirmStrategyType.push);
		frm.setConfirmableObject(operation.getConfirmableObject());
		updateForm(frm, operation);
		preConfirmAction(operation, request, new CallBackHandlerPushImpl());
		return mapping.findForward(FORWARD_START);
	}

	private void preConfirmAction(EditErmbOperation operation, HttpServletRequest request, CallBackHandler callBackHandler) throws BusinessException, BusinessLogicException
	{
		PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
		PersonData personData = dataProvider.getPersonData();

		callBackHandler.setLogin(personData.getLogin());
		callBackHandler.setConfirmableObject(operation.getConfirmableObject());
		try
		{
			ConfirmationManager.sendRequest(operation);
			PreConfirmObject preConfirmObject = operation.preConfirm(callBackHandler);
			operation.getRequest().setPreConfirm(true);
			operation.getRequest().addMessage(ConfirmHelper.getPreConfirmString(preConfirmObject));
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		}
		catch (SecurityException e)
		{
			ActionMessages errors = new ActionMessages();
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
            saveErrors(request, errors);
		}
		catch (SecurityLogicException e)
		{
			operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
			operation.getRequest().setPreConfirm(true);
		}
	}

	public ActionForward confirmChanges(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ErmbListRegistrationsForm frm = (ErmbListRegistrationsForm) form;
		EditErmbOperation operation = getOperation(request);
		List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));
		if (!errors.isEmpty() )
		{
			operation.getRequest().setErrorMessage(errors.get(0));
			frm.setConfirmableObject(operation.getConfirmableObject());
			return mapping.findForward(FORWARD_START);
		}
		else
		{
			try
			{
				operation.confirm();
			}
			catch (SecurityLogicException e) // ошибка подтверждения
			{
				errors.add(e.getMessage());
				ConfirmHelper.saveConfirmErrors(operation.getRequest(), errors);
				frm.setConfirmableObject(operation.getConfirmableObject());
				return mapping.findForward(FORWARD_START);
			}

			if (isAjax())
				saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Данные успешно сохранены.", false), null);
			else
			{
				ActionMessages actionMessages = new ActionMessages();
				actionMessages.add(ActionMessages.GLOBAL_MESSAGE, (new ActionMessage("Данные успешно сохранены.", false)));
				saveMessages(request, actionMessages);
			}
			return doNextStage(mapping, frm, request, response);
		}
	}

	public ActionForward doNextStage(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws BusinessException, BusinessLogicException
	{
		saveSessionMessages(request, getMessages(request));
		return mapping.findForward(FORWARD_SUCCESS);
	}

	private EditErmbOperation createOperation() throws BusinessException, BusinessLogicException
	{
		EditErmbOperation operation = createOperation(EditErmbOperation.class);
		operation.initialize();
		return operation;
	}

	public ActionForward saveChanges(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ErmbListRegistrationsForm frm = (ErmbListRegistrationsForm) form;

		if (frm.getCodesToPhoneNumber().isEmpty())
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(EMPTY_PHONE_MESSAGE, false));
			saveErrors(request, message);
			return start(mapping, form, request, response);
		}

		EditErmbOperation operation = createOperation();
		updateOperation(operation, frm);
		operation.resetConfirmStrategy();
		updateForm(frm, operation);

		if (operation.noChanges())
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(EMPTY_CHANGE_MESSAGE, false));
			saveErrors(request, message);
			return start(mapping, form, request, response);
		}
		frm.setConfirmStrategy(operation.getConfirmStrategy());
		frm.setConfirmableObject(operation.getConfirmableObject());
		ConfirmationManager.sendRequest(operation);
		setMessage(request);
		saveOperation(request,operation);

		return mapping.findForward(FORWARD_START);
	}

	private void setMessage(HttpServletRequest request)
	{
		ActionMessages message = new ActionMessages();
		message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Обратите внимание, если Вы перейдете на другую страницу системы без " +
																						"подтверждения изменения настроек SMS-паролем, " +
																						"то Ваши изменения не будут сохранены в системе.", false));
		saveMessages(request, message);
	}

	protected void updateOperation(EditEntityOperation editOperation, EditFormBase editForm) throws BusinessException, BusinessLogicException
	{
		ErmbListRegistrationsForm frm = (ErmbListRegistrationsForm) editForm;
		EditErmbOperation operation =(EditErmbOperation) editOperation;
		ErmbProfileOperationEntity entity = (ErmbProfileOperationEntity) operation.getEntity();

		ErmbProfileImpl profile = entity.getProfile();
		profile.setTimeZone(frm.getTimeZone());
		profile.setNotificationStartTime(Time.valueOf(frm.getNtfStartTimeString().concat(":00")));
		profile.setNotificationEndTime(Time.valueOf(frm.getNtfEndTimeString().concat(":00")));

		if (StringHelper.isNotEmpty(frm.getNotificationDays()))
		{
			String[] nfrDays = frm.getNotificationDays().split(",");
			profile.setDaysOfWeek(new DaysOfWeek(nfrDays, false));
		}
		else
			profile.setDaysOfWeek(new DaysOfWeek());

		entity.setSelectedTarif(frm.getSelectedTarif());
		entity.setMainCardId(frm.getMainCardId());

		Set<String> initialPhoneNumbers = entity.getPhonesNumber();
		if (StringHelper.isNotEmpty(frm.getMainPhoneNumberCode()))
			entity.setMainPhoneNumber(PhoneEncodeUtils.decodePhoneNumber(initialPhoneNumbers, frm.getMainPhoneNumberCode(), false));

		Set<String> phoneCodes = frm.getCodesToPhoneNumber().keySet();
		entity.setPhonesNumber(PhoneEncodeUtils.decodePhoneNumbers(phoneCodes, initialPhoneNumbers, false));

		profile.setDepositsTransfer(frm.getDepositsTransfer());
		profile.setFastServiceAvailable(frm.getFastServiceAvailable());
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ErmbListRegistrationsForm frm = (ErmbListRegistrationsForm) form;
		EditErmbOperation operation = createOperation();

		operation.resetConfirmStrategy();
		ConfirmationManager.sendRequest(operation);
		saveOperation(request,operation);
		updateForm(frm, operation);
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Обновить форму данными из операции
	 * @param form - форма
	 * @param op - операция
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected void updateForm(EditFormBase form, EditEntityOperation op)  throws BusinessException, BusinessLogicException
	{
		ErmbListRegistrationsForm frm = (ErmbListRegistrationsForm) form;
		EditErmbOperation operation = (EditErmbOperation) op;
		ErmbProfileOperationEntity entity = (ErmbProfileOperationEntity) operation.getEntity();
		ErmbProfileImpl profile = entity.getProfile();
		frm.setProfile(profile);

		if (checkAccess(GetCardsOperation.class))
		{
			GetCardsOperation operationCards = createOperation(GetCardsOperation.class);
			frm.setCards(operationCards.getErmbActiveCards());
			frm.setPossiblePaymentCards(operationCards.getErmbAvailablePaymentCards());
		}
		if (checkAccess(GetAccountsOperation.class))
		{
			GetAccountsOperation operationAccounts = createOperation(GetAccountsOperation.class);
			frm.setAccounts(operationAccounts.getErmbActiveAccounts());
		}
		if (checkAccess(GetLoanListOperation.class))
		{
			GetLoanListOperation operationLoans = createOperation(GetLoanListOperation.class);
			frm.setLoans(operationLoans.getErmbActiveLoans());
		}

		frm.setMainCardId(entity.getMainCardId());
		frm.setTarifs(entity.getTarifs());
		frm.setSelectedTarif(entity.getSelectedTarif());
		frm.setGracePeriodEndDate(operation.getGracePeriodEndDate());

		Time endTime = profile.getNotificationEndTime();
		if (endTime!=null)
			frm.setNtfEndTimeString(getFormattedTime(profile.getNotificationEndTime()));
		Time startTime = profile.getNotificationStartTime();
		if (startTime!=null)
			frm.setNtfStartTimeString(getFormattedTime(profile.getNotificationStartTime()));

		long clientTimeZone = profile.getTimeZone();
		frm.setTimeZone(clientTimeZone);
		frm.setTimeZoneList(TimeZone.getTimeZoneList(clientTimeZone));

		frm.setNotificationDays(operation.getDaysOfWeek());

		Set<String> phoneNumbers = entity.getPhonesNumber();
		if (!CollectionUtils.isEmpty(phoneNumbers))
		{
			frm.setMainPhoneNumberCode(PhoneEncodeUtils.encodePhoneNumber(phoneNumbers, entity.getMainPhoneNumber()));
			frm.setCodesToPhoneNumber(PhoneEncodeUtils.encodePhoneNumbers(phoneNumbers));
		}

		frm.setDepositsTransfer(profile.getDepositsTransfer());
		frm.setFastServiceAvailable(profile.getFastServiceAvailable());
	}

	/**
	 * Форматировать время для jsp (откинуть секунды)
	 * @param time - время
	 * @return строка вида чч:мм
	 */
	private String getFormattedTime(Time time)
	{
		String fullTime = time.toString();
		return fullTime.substring(0, fullTime.length()-3);
	}


}
