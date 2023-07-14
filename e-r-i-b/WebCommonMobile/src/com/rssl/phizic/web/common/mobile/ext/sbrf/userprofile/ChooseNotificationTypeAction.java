package com.rssl.phizic.web.common.mobile.ext.sbrf.userprofile;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.fraudMonitoring.FraudMonitoringSendersFactory;
import com.rssl.phizic.business.messaging.info.UserNotificationType;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.operations.userprofile.*;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.rsa.senders.types.EventsType;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.struts.forms.FormHelper;
import com.rssl.phizic.web.util.MobileApplicationsUtil;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author EgorovaA
 * @ created 15.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Выбор способа оповещений
 */
public class ChooseNotificationTypeAction extends OperationalActionBase
{
	private static final String REQUIRED_NOTIFICATION_FIELD_MSG = "Укажите тип оповещения.";

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ChooseNotificationTypeForm frm = (ChooseNotificationTypeForm) form;

		String notificationSetting = frm.getNotification();
		if (StringHelper.isEmpty(notificationSetting))
			return errorForward(mapping, request, REQUIRED_NOTIFICATION_FIELD_MSG);

		String notificationType = frm.getType();

		SetupNotificationOperation operation = createOperation(SetupNotificationOperation.class);
		operation.initialize(UserNotificationType.loginNotification);

		FraudMonitoringSender sender = FraudMonitoringSendersFactory.getInstance().getSender(EventsType.CHANGE_ALERT_SETTINGS);
		sender.send();

		ActivePerson person = operation.getPerson();
		String email = person.getEmail();
		String push = (MobileApplicationsUtil.getMobileDevices(operation.getMobileDevices()));

		if (StringHelper.equals(notificationType, "email") && StringHelper.isEmpty(email))
			return errorForward(mapping, request, getResourceMessage("securityBundle", "empty.email.error.message"));
		if (StringHelper.equals(notificationType, "push") && StringHelper.isEmpty(push))
			return errorForward(mapping, request, getResourceMessage("securityBundle", "empty.device.error.message"));

		if (StringHelper.equals(notificationSetting, "login"))
		{
			String phone = PhoneNumberUtil.getCutPhoneNumbers(operation.getMobilePhonesForNotification());
			return setNotification(mapping, request, notificationType,  phone);
		}

		String phoneForMail = (PhoneNumberUtil.getCutPhoneNumbers(operation.getMobilePhonesForMail()));
		if (StringHelper.equalsNullIgnore(notificationSetting, "help"))
			return setMailNotification(mapping, request, notificationType, phoneForMail);

		if (StringHelper.equalsNullIgnore(notificationSetting, "operations"))
			return setDeliveryNotification(mapping, request, notificationType, phoneForMail);

		if (StringHelper.equalsNullIgnore(notificationSetting, "operationConfirm"))
			return setOperationConfirm(mapping, request, notificationType, phoneForMail);

		if (StringHelper.equalsNullIgnore(notificationSetting, "loginConfirm"))
			return setLoginConfirm(mapping, request, notificationType, phoneForMail);

		return mapping.findForward(FORWARD_START);
	}

	// Установить тип оповещения о входе с в систему
	private ActionForward setNotification(ActionMapping mapping, HttpServletRequest request, String notificationType, String phone) throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		FormProcessor<ActionMessages, ?> formProcessor =
				FormHelper.newInstance(new RequestValuesSource(currentRequest()), ChooseNotificationTypeForm.EDIT_LOGIN_NOTIFICATION_SETTINGS_FORM);
		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_START);
		}

		if (StringHelper.equals(notificationType, "sms") && StringHelper.isEmpty(phone))
			return errorForward(mapping, request, getResourceMessage("securityBundle", "empty.phone.error.message"));

		MApiLoginSetupNotificationOperation operation = createOperation(MApiLoginSetupNotificationOperation.class);
		operation.initialize(UserNotificationType.loginNotification);
		operation.setChannel(notificationType);
		operation.saveNotification();
		operation.sendSmsNotification();

		return mapping.findForward(FORWARD_START);
	}

	// Установить тип оповещения о получении письма из службы помощи
	private ActionForward setMailNotification(ActionMapping mapping, HttpServletRequest request, String notificationType, String phone) throws BusinessException, SecurityLogicException, BusinessLogicException
	{
		FormProcessor<ActionMessages, ?> formProcessor =
				FormHelper.newInstance(new RequestValuesSource(currentRequest()), ChooseNotificationTypeForm.EDIT_NOTIFICATION_SETTINGS_FORM);
		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_START);
		}

		if (StringHelper.equals(notificationType, "sms") && StringHelper.isEmpty(phone))
			return errorForward(mapping, request, getResourceMessage("securityBundle", "empty.phone.error.message"));

		MApiMailSetupNotificationOperation mailOperation = createOperation(MApiMailSetupNotificationOperation.class);
		mailOperation.initialize(UserNotificationType.mailNotification);
		mailOperation.setChannel(notificationType);
		mailOperation.saveNotification();
		mailOperation.sendSmsNotification();

		return mapping.findForward(FORWARD_START);
	}

	// Установить тип оповещения об исполнении операций
	private ActionForward setDeliveryNotification(ActionMapping mapping, HttpServletRequest request, String notificationType, String phone) throws BusinessException, SecurityLogicException, BusinessLogicException
	{
		FormProcessor<ActionMessages, ?> formProcessor =
				FormHelper.newInstance(new RequestValuesSource(currentRequest()), ChooseNotificationTypeForm.EDIT_NOTIFICATION_SETTINGS_FORM);
		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_START);
		}

		if (StringHelper.equals(notificationType, "sms") && StringHelper.isEmpty(phone))
			return errorForward(mapping, request, getResourceMessage("securityBundle", "empty.phone.error.message"));

		MApiOperationSetupNotificationOperation deliveryOperation = createOperation(MApiOperationSetupNotificationOperation.class);
		deliveryOperation.initialize(UserNotificationType.operationNotification);
		deliveryOperation.setChannel(notificationType);
		deliveryOperation.saveNotification();
		deliveryOperation.sendSmsNotification();

		return mapping.findForward(FORWARD_START);
	}

	// Установить канал для подтверждения операций
	private ActionForward setOperationConfirm(ActionMapping mapping, HttpServletRequest request, String notificationType, String phone) throws BusinessException, SecurityLogicException, BusinessLogicException
	{
		FormProcessor<ActionMessages, ?> formProcessor =
				FormHelper.newInstance(new RequestValuesSource(currentRequest()), ChooseNotificationTypeForm.EDIT_OPERATION_CONFIRM_SETTINGS_FORM);
		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_START);
		}

		if (StringHelper.equals(notificationType, "sms") && StringHelper.isEmpty(phone))
			return errorForward(mapping, request, getResourceMessage("securityBundle", "empty.phone.error.message"));

		AuthenticationContext context = AuthenticationContext.getContext();
		SetupSecurityOperation securityOperation = createOperation(SetupSecurityOperation.class);
		securityOperation.initialize(context);
		securityOperation.setUserConfirmType(notificationType);
		securityOperation.updateConfirmationSettings();
		securityOperation.sendSmsNotification();

		return mapping.findForward(FORWARD_START);
	}

	// Установить требуется отправлять сообщение для подтверждения входа
	private ActionForward setLoginConfirm(ActionMapping mapping, HttpServletRequest request, String notificationType, String phone) throws BusinessException, SecurityLogicException, BusinessLogicException
	{
		FormProcessor<ActionMessages, ?> formProcessor =
				FormHelper.newInstance(new RequestValuesSource(currentRequest()), ChooseNotificationTypeForm.EDIT_LOGIN_CONFIRM_SETTINGS_FORM);
		if (!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return mapping.findForward(FORWARD_START);
		}

		AuthenticationContext context = AuthenticationContext.getContext();
		SetupSecurityOperation securityOperation = createOperation(SetupSecurityOperation.class);
		securityOperation.initialize(context);
		securityOperation.setOneTimePassword(!notificationType.equals(ConfirmStrategyType.none.toString()));
		securityOperation.updateConfirmationSettings();

		return mapping.findForward(FORWARD_START);
	}

	private ActionForward errorForward(ActionMapping mapping, HttpServletRequest request, String message)
	{
		saveError(request, new ActionMessage(message, false));
		return mapping.findForward(FORWARD_START);
	}

}
