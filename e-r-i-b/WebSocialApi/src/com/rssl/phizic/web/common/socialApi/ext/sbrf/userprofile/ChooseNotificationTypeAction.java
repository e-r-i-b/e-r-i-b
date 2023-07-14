package com.rssl.phizic.web.common.socialApi.ext.sbrf.userprofile;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.messaging.info.UserNotificationType;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.operations.userprofile.SetupNotificationOperation;
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
			return setNotification(mapping, request, operation, notificationType,  phone);
		}

		String phoneForMail = (PhoneNumberUtil.getCutPhoneNumbers(operation.getMobilePhonesForMail()));
		if (StringHelper.equalsNullIgnore(notificationSetting, "help"))
			return setMailNotification(mapping, request, notificationType, phoneForMail);

		if (StringHelper.equalsNullIgnore(notificationSetting, "operations"))
			return setDeliveryNotification(mapping, request, notificationType, phoneForMail);

		return mapping.findForward(FORWARD_START);
	}

	// Установить тип оповещения о входе с в систему
	private ActionForward setNotification(ActionMapping mapping, HttpServletRequest request, SetupNotificationOperation operation, String notificationType, String phone) throws BusinessException, BusinessLogicException, SecurityLogicException
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

		operation.setChannel(notificationType);
		operation.saveNotification();

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

		SetupNotificationOperation mailOperation = createOperation(SetupNotificationOperation.class);
		mailOperation.initialize(UserNotificationType.mailNotification);
		mailOperation.setChannel(notificationType);
		mailOperation.saveNotification();

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

		SetupNotificationOperation deliveryOperation = createOperation(SetupNotificationOperation.class);
		deliveryOperation.initialize(UserNotificationType.operationNotification);
		deliveryOperation.setChannel(notificationType);
		deliveryOperation.saveNotification();

		return mapping.findForward(FORWARD_START);
	}

	private ActionForward errorForward(ActionMapping mapping, HttpServletRequest request, String message)
	{
		saveError(request, new ActionMessage(message, false));
		return mapping.findForward(FORWARD_START);
	}

}
