package com.rssl.phizic.web.common.mobile.ext.sbrf.userprofile;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.messaging.info.UserNotificationType;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.operations.userprofile.SetupNotificationOperation;
import com.rssl.phizic.operations.userprofile.SetupSecurityOperation;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.util.MobileApplicationsUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author EgorovaA
 * @ created 14.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Получение настроек оповещений
 */
public class NotificationSettingsAction extends OperationalActionBase
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		NotificationSettingsForm frm = (NotificationSettingsForm) form;

		SetupNotificationOperation operation = createOperation(SetupNotificationOperation.class);
		operation.initialize(UserNotificationType.loginNotification, UserNotificationType.mailNotification, UserNotificationType.operationNotification);

		ActivePerson person = operation.getPerson();
		frm.setPhone(PhoneNumberUtil.getCutPhoneNumbers(operation.getMobilePhonesForNotification()));
		frm.setPhoneForMail(PhoneNumberUtil.getCutPhoneNumbers(operation.getMobilePhonesForMail()));
		frm.setEmail(person.getEmail());
		frm.setPush(MobileApplicationsUtil.getMobileDevices(operation.getMobileDevices()));

		frm.setNotificationType(operation.getChannel(UserNotificationType.loginNotification));
		frm.setMailNotificationType(operation.getChannel(UserNotificationType.mailNotification));
		frm.setDeliveryNotificationType(operation.getChannel(UserNotificationType.operationNotification));

		AuthenticationContext context = AuthenticationContext.getContext();
		SetupSecurityOperation securityOperation = createOperation(SetupSecurityOperation.class);
		securityOperation.initialize(context);
		frm.setNeedLoginConfirm(securityOperation.isOneTimePassword());
		frm.setOperationConfirmNotificationType(securityOperation.getUserConfirmType());

		return mapping.findForward(FORWARD_START);
	}
}
