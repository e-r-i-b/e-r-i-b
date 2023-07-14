package com.rssl.phizic.web.client.userprofile;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.messaging.info.UserNotificationType;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.ErmbProductLink;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.operations.userprofile.SetupNotificationOperation;
import com.rssl.phizic.operations.userprofile.SetupResourcesSmsNotificationOperation;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.NoActiveOperationException;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.security.SecurityMessages;
import com.rssl.phizic.web.util.MobileApplicationsUtil;
import org.apache.struts.action.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.rssl.phizic.operations.userprofile.SetupNotificationOperation.NONE_CHANGE_MESSAGE;

/**
 * @author gladishev
 * @ created 09.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditUserNotificationsAction extends EditUserProfileActionBase
{
	private static final String FORWARD_START = "Start";

	private static final String ATTENTION_MESSAGE = "Обратите внимание, если Вы перейдете на другую страницу системы без подтверждения изменения настроек SMS-паролем, то Ваши изменения не будут сохранены в системе.";
	private static final String SAVE_MESSAGE = "Данные успешно сохранены.";
	private static final String SERVICE_MESSAGE = "Сервис временно недоступен, попробуйте позже.";

	private static final String EMPTY_CHANGE_MESSAGE = "Вы не внесли никаких изменений в настройки SMS-оповещений по продуктам.";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> keys = new HashMap<String,String>();
		//Настройка уведомлений о входе на личную страницу
		keys.put("button.save", "saveLoginNotification");
		keys.put("button.preConfirmNotificationSMS", "preConfirmSMS");
		keys.put("button.preConfirmNotificationPush", "preConfirmPush");
		keys.put("button.preConfirmNotificationCap", "preConfirmCAP");
		keys.put("button.confirm", "confirm");

		//Настройка оповещений из службы помощи
		keys.put("button.saveMailNotificationSettings", "saveMailNotificationSettings");
		keys.put("button.preConfirmMailSMS", "preConfirmSMS");
		keys.put("button.preConfirmMailPush", "preConfirmPush");
		keys.put("button.preConfirmMailCap", "preConfirmCAP");
		keys.put("button.confirmMail", "confirm");

		//Настройка оповещений об исполнении операций
		keys.put("button.saveDeliveryNotificationSettings", "saveDeliveryNotificationSettings");
		keys.put("button.preConfirmDeliveryNotificationSMS", "preConfirmSMS");
		keys.put("button.preConfirmDeliveryNotificationPush", "preConfirmPush");
		keys.put("button.preConfirmDeliveryNotificationCap", "preConfirmCAP");
		keys.put("button.confirmDelivery", "confirm");

		//Настройка рыссылки новостей банка
		keys.put("button.saveBankNewsNotificationSettings", "saveBankNewsNotificationSettings");
		keys.put("button.preConfirmBankNewsNotificationSMS", "preConfirmSMS");
		keys.put("button.preConfirmBankNewsNotificationPush", "preConfirmPush");
		keys.put("button.preConfirmBankNewsNotificationCap", "preConfirmCAP");
		keys.put("button.confirmBankNews", "confirm");

		//Одновременная настройка всех уведомлений
		keys.put("button.saveAllNotification", "saveAllNotification");
		keys.put("button.preConfirmAllNotificationSMS", "preConfirmSMS");
		keys.put("button.preConfirmAllNotificationPush", "preConfirmPush");
		keys.put("button.preConfirmAllNotificationCap", "preConfirmCAP");
		keys.put("button.confirmAll", "confirm");

		//Настройка смс-оповещений по продуктам
		keys.put("button.saveProductsSmsNotificationSettings", "saveProductsSmsNotificationSettings");
		keys.put("button.preConfirmProductsSmsNotificationSMS", "resourcesSmsNotificationPreConfirmSMS");
		keys.put("button.preConfirmProductsSmsNotificationPush", "resourcesSmsNotificationPreConfirmPush");
		keys.put("button.preConfirmProductsSmsNotificationCap", "preConfirmCAP");
		keys.put("button.confirmProductsSms", "resourcesSmsNotificationConfirm");

		keys.put("button.nextStage", "doNextStage");
		return keys;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupNotificationsForm frm = (SetupNotificationsForm) form;

		SetupNotificationOperation operation = createOperation(SetupNotificationOperation.class);
		operation.initialize(UserNotificationType.values());
		try
		{
			frm.setField(SetupNotificationsForm.PHONE_FIELD_NAME, PhoneNumberUtil.getCutPhoneNumbers(operation.getMobilePhonesForNotification()));
			frm.setField(SetupNotificationsForm.PHONE_FOR_MAIL_FIELD_NAME, PhoneNumberUtil.getCutPhoneNumbers(operation.getMobilePhonesForMail()));
		}
		catch (InactiveExternalSystemException ignored){}//игнорируем ошибку, просто не отображаем номер телефона
		frm.setField(SetupNotificationsForm.EMAIL_FIELD_NAME, operation.getPerson().getEmail());
		frm.setField(SetupNotificationsForm.EMAIL_FORMAT_FIELD_NAME, operation.getPerson().getMailFormat() != null ? operation.getPerson().getMailFormat().getDescription() : null);
		frm.setField(SetupNotificationsForm.MOBILE_DEVICES_FIELD_NAME, MobileApplicationsUtil.getMobileDevices(operation.getAllMobileDevices()));
		frm.setField(SetupNotificationsForm.PUSH_FIELD_NAME, MobileApplicationsUtil.getMobileDevices(operation.getMobileDevices()));

		frm.setField(UserNotificationType.loginNotification.name(),    operation.getChannel(UserNotificationType.loginNotification));
		frm.setField(UserNotificationType.mailNotification.name(),     operation.getChannel(UserNotificationType.mailNotification));
		frm.setField(UserNotificationType.operationNotification.name(),operation.getChannel(UserNotificationType.operationNotification));
		frm.setField(UserNotificationType.newsNotification.name(),     operation.getChannel(UserNotificationType.newsNotification));

		updateProducts(frm);

		frm.setSelectedAccountIds(getSelectedProductIds(frm.getAccounts()));
		frm.setSelectedCardIds(getSelectedProductIds(frm.getCards()));
		frm.setSelectedLoanIds(getSelectedProductIds(frm.getLoans()));

		return getActionForward(mapping);
	}

	public ActionForward saveMailNotificationSettings(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return saveSettings(mapping, form, request, UserNotificationType.mailNotification);
	}

	public ActionForward saveAllNotification(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return saveSettings(mapping, form, request, UserNotificationType.values());
	}

	private ActionForward saveSettings(ActionMapping mapping, ActionForm form, HttpServletRequest request, UserNotificationType ... types) throws Exception
	{
		SetupNotificationOperation operation = createOperation(SetupNotificationOperation.class);
		operation.initialize(types);
		SetupNotificationsForm frm = (SetupNotificationsForm)form;
		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());

		boolean changeExist = false;
		for (UserNotificationType type : types)
			if (!StringHelper.equals(valuesSource.getValue(type.name()),operation.getChannel(type)))
			{
				changeExist = true;
				break;
			}

		if (!changeExist)
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(NONE_CHANGE_MESSAGE, false));
			saveErrors(request, message);
			return getActionForward(mapping);
		}

		for (UserNotificationType type : types)
		{
			FormProcessor<ActionMessages, ?> formProcessor = getFormProcessor(type, valuesSource);
			if (!formProcessor.process())
			{
				saveErrors(request, formProcessor.getErrors());
				return getActionForward(mapping);
			}

			updateOperationData(operation, formProcessor.getResult(), type);
		}

		operation.resetConfirmStrategy();
		ConfirmationManager.sendRequest(operation);
		frm.setNotificationConfirmStrategy(operation.getConfirmStrategy());
		frm.setConfirmableObject(operation.getConfirmableObject());
		frm.setConfirmedType(types.length > 1 ? "allNotification" : types[0].name());
		updateProducts(frm);

		ActionMessages message = new ActionMessages();
		message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ATTENTION_MESSAGE, false));
		saveMessages(request, message);
		saveOperation(request,operation);
		return getActionForward(mapping);
	}

	private ActionForward getActionForward(ActionMapping mapping)
	{
		return PersonHelper.availableNewProfile() ? mapping.findForward(FORWARD_START_NEW) : mapping.findForward(FORWARD_START);
	}

	public ActionForward saveDeliveryNotificationSettings(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return saveSettings(mapping, form, request, UserNotificationType.operationNotification);
	}

	public ActionForward saveLoginNotification(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return saveSettings(mapping, form, request, UserNotificationType.loginNotification);
	}

	/**
	 * Сохранение настроек рассылки новостей банка
	 * @param mapping - маппинг экшенов, из которого в итоге будем возвращать форвард
	 * @param form - форма настройки оповещений (SetupNotificationsForm)
	 * @param request - http-запрос
	 * @param response - http - ответ
	 * @return - экшен-форвард, в итоге в любом случае вернёмся на ту же страницу,
	 * но с воможным отображением кнопки подтверждения наших действий ( если в данном методе не было выявлено ошибок)
	 * @throws Exception
	 */
	public ActionForward saveBankNewsNotificationSettings(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return saveSettings(mapping, form, request, UserNotificationType.newsNotification);
	}

	public ActionForward saveProductsSmsNotificationSettings(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupResourcesSmsNotificationOperation operation = createOperation(SetupResourcesSmsNotificationOperation.class);
		operation.initialize();

		SetupNotificationsForm frm = (SetupNotificationsForm) form;

		operation.updateResourcesNotificationSettings(frm.getSelectedAccountIds(), frm.getSelectedCardIds(), frm.getSelectedLoanIds(), frm.isNewProductsNotification());
		operation.resetConfirmStrategy();

		if (operation.noChanges())
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(EMPTY_CHANGE_MESSAGE, false));
			saveErrors(request, message);
			return start(mapping, form, request, response);
		}

		smsNotificationUpdateFormData(operation, frm);
		frm.setProductsSmsNotificationConfirmableObject(operation.getConfirmableObject());
		frm.setProductsSmsNotificationConfirmStrategy(operation.getConfirmStrategy());

		ConfirmationManager.sendRequest(operation);
		setMessage(request);
		saveOperation(request,operation);

		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Подтверждение изменений
	 */
	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupNotificationsForm frm = (SetupNotificationsForm) form;
		SetupNotificationOperation notificationOperation = getOperation(request);
		try
		{
			FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());
			UserNotificationType[] useOperations = notificationOperation.getTypes();
			for (UserNotificationType useOperation : useOperations)
			{
				FormProcessor<ActionMessages, ?> formProcessor = getFormProcessor(useOperation, valuesSource);

				if (!formProcessor.process())
				{
					saveErrors(request, formProcessor.getErrors());
					frm.setCurrentConfirmableObject(notificationOperation);
					return mapping.findForward(FORWARD_START);
				}

				updateOperationData(notificationOperation, formProcessor.getResult(), useOperation);
			}
			List<String> errors = ConfirmationManager.readResponse(notificationOperation, new RequestValuesSource(request));
			frm.setConfirmedType(useOperations.length > 1 ? "allNotification" : useOperations[0].name());
			if (!errors.isEmpty() )
			{
				notificationOperation.getRequest().setErrorMessage(errors.get(0));
				frm.setCurrentConfirmableObject(notificationOperation);
				return mapping.findForward(FORWARD_START);
			}
			else
			{
				notificationOperation.confirm();
				if(isAjax())
					saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(SAVE_MESSAGE, false), null);
				else
				{
					ActionMessages message = new ActionMessages();
					message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(SAVE_MESSAGE, false));
					saveMessages(request, message);
				}
				return doNextStage(mapping, form, request, response);
			}
		}
		catch (BusinessLogicException e) // ошибка подтверждения
		{
			notificationOperation.getRequest().setErrorMessage(e.getMessage());
			frm.setCurrentConfirmableObject(notificationOperation);
			return mapping.findForward(FORWARD_START);
		}
		catch (SecurityLogicException e) // ошибка подтверждения
		{
			notificationOperation.getRequest().setErrorMessage(e.getMessage());
			frm.setCurrentConfirmableObject(notificationOperation);
			return mapping.findForward(FORWARD_START);
		}
		catch (SecurityException e) //упал сервис
		{
			saveSessionError(ActionMessages.GLOBAL_MESSAGE,new ActionMessage(SERVICE_MESSAGE,false), null);
			frm.setCurrentConfirmableObject(notificationOperation);
			return mapping.findForward(FORWARD_START);
		}
	}

	public ActionForward resourcesSmsNotificationConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupNotificationsForm frm = (SetupNotificationsForm)form;
		SetupResourcesSmsNotificationOperation operation = getOperation(request);
		smsNotificationUpdateFormData(operation, frm);
		frm.setProductsSmsNotificationConfirmableObject(operation.getConfirmableObject());
		frm.setProductsSmsNotificationConfirmStrategy(operation.getConfirmStrategy());
		return confirmSettings(operation, mapping, frm, request, response);
	}

	private ActionForward confirmSettings(SetupResourcesSmsNotificationOperation operation, ActionMapping mapping, SetupNotificationsForm frm, HttpServletRequest request, HttpServletResponse response)  throws Exception
	{
		try
		{
			List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));
			if (!errors.isEmpty() )
			{
				operation.getRequest().setErrorMessage(errors.get(0));
				frm.setConfirmableObject(operation.getConfirmableObject());
				return mapping.findForward(FORWARD_START);
			}
			else
			{
				operation.confirm();
				operation.sendSmsNotification(true);
				if(isAjax())
					if (operation.getNotUpdatedLinks() == null || operation.getNotUpdatedLinks().isEmpty())
						saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Данные успешно сохранены.", false), null);
					else
						saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, getInfoMessage(operation.getNotUpdatedLinks()), null);
				else
				{
					ActionMessages actionMessages = new ActionMessages();
					if (operation.getNotUpdatedLinks() == null || operation.getNotUpdatedLinks().isEmpty())
						actionMessages.add(ActionMessages.GLOBAL_MESSAGE, (new ActionMessage("Данные успешно сохранены.", false)));
					else
						actionMessages.add(ActionMessages.GLOBAL_MESSAGE, getInfoMessage(operation.getNotUpdatedLinks()));
					saveMessages(request, actionMessages);
				}
				return doNextStage(mapping, frm, request, response);
			}
		}
		catch (BusinessLogicException ble)
		{
			saveError(request, SecurityMessages.translateException(ble));
			frm.setConfirmableObject(operation.getConfirmableObject());
			return mapping.findForward(FORWARD_START);
		}
		catch (SecurityLogicException e) // ошибка подтверждения
		{
			operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
			frm.setConfirmableObject(operation.getConfirmableObject());
			return mapping.findForward(FORWARD_START);
		}
		catch (SecurityException e) //упал сервис
		{
			operation.getRequest().setErrorMessage("Сервис временно недоступен, попробуйте позже");
			frm.setConfirmableObject(operation.getConfirmableObject());
			return mapping.findForward(FORWARD_START);
		}
		catch (InactiveExternalSystemException e) // ошибка неактивна внешняя система
		{
			operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
			frm.setConfirmableObject(operation.getConfirmableObject());
			return mapping.findForward(FORWARD_START);
		}
	}

	private ActionMessage getInfoMessage(List<AccountLink> links)
	{
		StringBuilder linkNames = new StringBuilder();
		for (AccountLink link : links)
		{
			linkNames.append("\"" + link.getName() + " " + link.getNumber() + "\"" + ", ");
		}
		linkNames = linkNames.delete(linkNames.length()-1, linkNames.length());
		return new ActionMessage(String.format(NONE_CHANGE_MESSAGE, linkNames), false);
	}

	private FormProcessor<ActionMessages, ?> getFormProcessor(UserNotificationType type, FieldValuesSource valuesSource)
	{
		Form form = null;
		boolean clientProfilePush = PermissionUtil.impliesService("ClientProfilePush");
		if (type == UserNotificationType.loginNotification)
		{
			form = clientProfilePush ? SetupNotificationsForm.EDIT_FORM_WITH_PUSH
					                 : SetupNotificationsForm.EDIT_FORM;
		}
		else if (type == UserNotificationType.mailNotification)
		{
			form = clientProfilePush ? SetupNotificationsForm.EDIT_MAIL_NOTIFICATION_SETTINGS_FORM_WITH_PUSH
					                 : SetupNotificationsForm.EDIT_MAIL_NOTIFICATION_SETTINGS_FORM;
		}
		else if (type == UserNotificationType.newsNotification)
		{
			form = SetupNotificationsForm.EDIT_BANK_NEWS_NOTIFICATION_SETTINGS_FORM;
		}
		else
		{
			form = clientProfilePush ? SetupNotificationsForm.EDIT_DELIVERY_NOTIFICATION_SETTINGS_FORM_WITH_PUSH
									 : SetupNotificationsForm.EDIT_DELIVERY_NOTIFICATION_SETTINGS_FORM ;
		}

		return createFormProcessor(valuesSource, form);
	}

	public ActionForward doNextStage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return start(mapping, form, request, response);
	}

	/**
	 * Активация окна, подтверждения по SMS
	 */
	public ActionForward preConfirmSMS( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return preConfirm(mapping, (SetupNotificationsForm) form, request, ConfirmStrategyType.sms);
	}

	/**
	 * Активация окна, подтверждения по push
	 */
	public ActionForward preConfirmPush( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		return preConfirm(mapping, (SetupNotificationsForm) form, request, ConfirmStrategyType.push);
	}

	private ActionForward preConfirm(ActionMapping mapping, SetupNotificationsForm form, HttpServletRequest request, ConfirmStrategyType confirmStrategy) throws NoActiveOperationException, BusinessException, BusinessLogicException
	{
		ConfirmableOperationBase operation = getOperation(request);

		SetupNotificationsForm frm   = (SetupNotificationsForm) form;
		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());
		SetupNotificationOperation notificationOperation = (SetupNotificationOperation) operation;
		ActivePerson person = notificationOperation.getPerson();
		UserNotificationType[] useOperations = notificationOperation.getTypes();

		for (UserNotificationType useOperation : useOperations)
		{
			FormProcessor<ActionMessages, ?> formProcessor = getFormProcessor(useOperation, valuesSource);

			if (!formProcessor.process())
			{
				saveErrors(request, formProcessor.getErrors());
				return mapping.findForward(FORWARD_START);
			}

			updateOperationData(notificationOperation, formProcessor.getResult(), useOperation);
		}
        frm.setCurrentConfirmableObject(notificationOperation);
		frm.setConfirmedType(useOperations.length > 1 ? "allNotification" : useOperations[0].name());
		operation.setUserStrategyType(confirmStrategy);
		ConfirmationManager.sendRequest(operation);
		try
		{
			CallBackHandler callBackHandler = createCallBackHandler(confirmStrategy, person.getLogin(), operation.getConfirmableObject());
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

		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Активация окна, подтверждения по SMS
	 */
	public ActionForward resourcesSmsNotificationPreConfirmSMS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return resourcesSmsNotificationPreConfirm(mapping, (SetupNotificationsForm) form, request, ConfirmStrategyType.sms);
	}

	/**
	 * Активация окна, подтверждения по push
	 */
	public ActionForward resourcesSmsNotificationPreConfirmPush(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return resourcesSmsNotificationPreConfirm(mapping, (SetupNotificationsForm) form, request, ConfirmStrategyType.push);
	}

	private ActionForward resourcesSmsNotificationPreConfirm(ActionMapping mapping, SetupNotificationsForm form, HttpServletRequest request, ConfirmStrategyType confirmStrategy) throws NoActiveOperationException, BusinessException, BusinessLogicException
	{
		SetupResourcesSmsNotificationOperation operation = getOperation(request);
		SetupNotificationsForm frm   = (SetupNotificationsForm) form;

		operation.setUserStrategyType(confirmStrategy);

		ConfirmationManager.sendRequest(operation);

		smsNotificationUpdateFormData(operation, frm);
		frm.setProductsSmsNotificationConfirmableObject(operation.getConfirmableObject());
		frm.setProductsSmsNotificationConfirmStrategy(operation.getConfirmStrategy());

		try
		{
			operation.getRequest().setPreConfirm(true);
			PreConfirmObject preConfirmObject = operation.preConfirm(createCallBackHandler(confirmStrategy, operation.getLogin(), operation.getConfirmableObject()));
			operation.getRequest().addMessage(ConfirmHelper.getPreConfirmString(preConfirmObject));
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		}
		catch (SecurityLogicException e)
		{
            operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
		}
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Активация окна, подтверждения по CAP
	 */
	public ActionForward preConfirmCAP( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		ConfirmableOperationBase operation = getOperation(request);

		SetupNotificationsForm frm   = (SetupNotificationsForm) form;
		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());
		SetupNotificationOperation notificationOperation = (SetupNotificationOperation) operation;
		UserNotificationType[] useOperations = notificationOperation.getTypes();
		for (UserNotificationType useOperation : useOperations)
		{
			FormProcessor<ActionMessages, ?> formProcessor = getFormProcessor(useOperation, valuesSource);

			if (!formProcessor.process())
			{
				saveErrors(request, formProcessor.getErrors());
				return mapping.findForward(FORWARD_START);
			}

			updateOperationData(notificationOperation, formProcessor.getResult(), useOperation);
		}
		frm.setCurrentConfirmableObject(notificationOperation);
		frm.setConfirmedType(useOperations.length > 1 ? "allNotification" : useOperations[0].name());
		operation.setUserStrategyType(ConfirmStrategyType.cap);
		ConfirmationManager.sendRequest(operation);
		operation.getRequest().setPreConfirm(true);
		operation.getRequest().addMessage(ConfirmHelper.getPreConfirmCapString());
		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));

		return mapping.findForward(FORWARD_START);
	}

	private void updateOperationData(SetupNotificationOperation operation, Map<String, Object> result, UserNotificationType type) throws BusinessException, BusinessLogicException
	{
		operation.setChannel(type, (String) result.get(type.name()));
	}

	private void setMessage(HttpServletRequest request)
	{
		ActionMessages message = new ActionMessages();
		message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ATTENTION_MESSAGE, false));
		saveMessages(request, message);
	}

	private String[] getSelectedProductIds(List<? extends ErmbProductLink> productLinks)
	{
		List<String> selectedProductIds = new ArrayList<String>();
		for (ErmbProductLink productLink : productLinks)
		{
			if (productLink.getErmbNotification())
				selectedProductIds.add(productLink.getId().toString());
		}
		return selectedProductIds.toArray(new String[0]);
	}

	private void updateProducts(SetupNotificationsForm frm) throws BusinessException, BusinessLogicException
	{
		SetupResourcesSmsNotificationOperation productsSmsNotificationOperation = createOperation(SetupResourcesSmsNotificationOperation.class);
		productsSmsNotificationOperation.initialize();
		smsNotificationUpdateFormData(productsSmsNotificationOperation, frm);
	}

	private void smsNotificationUpdateFormData(SetupResourcesSmsNotificationOperation operation, SetupNotificationsForm frm) throws BusinessException
	{
		frm.setAccounts(operation.getClientAccounts());
		frm.setCards(operation.getClientCards());
		frm.setLoans(operation.getClientLoans());
		frm.setShowResourcesSmsNotificationBlock(operation.isShowResourcesSmsNotificationBlock());
		frm.setTariffAllowAccountSmsNotification(operation.isTariffAllowAccountSmsNotification());
		frm.setTariffAllowCardSmsNotification(operation.isTariffAllowCardSmsNotification());
		frm.setNewProductsNotification(operation.isNewProductNotification());
	}

}
