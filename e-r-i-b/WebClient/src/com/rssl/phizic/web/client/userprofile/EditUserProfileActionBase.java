package com.rssl.phizic.web.client.userprofile;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.messaging.OperationType;
import com.rssl.phizic.operations.push.CallBackHandlerPushImpl;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.web.actions.OperationalActionBase;

/**
 * @author osminin
 * @ created 14.01.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������� ����� �������������� �������� ������� �������� �������
 */
public abstract class EditUserProfileActionBase extends OperationalActionBase
{
	protected static final String FORWARD_START_NEW = "NewStart";
	/**
	 * ������� CallBackHandler �� �������� � ������
	 * @param confirmStrategy ��������� �������������
	 * @param login ����� �������
	 * @param login ����� �������
	 * @param object ������
	 * @return CallBackHandler
	 */
	protected CallBackHandler createCallBackHandler(ConfirmStrategyType confirmStrategy, Login login, ConfirmableObject object)
	{
		CallBackHandler callBackHandler;
		if (confirmStrategy == ConfirmStrategyType.push)
			callBackHandler = new CallBackHandlerPushImpl();
		else
			callBackHandler = new CallBackHandlerSmsImpl();
		callBackHandler.setLogin(login);
		callBackHandler.setConfirmableObject(object);
		callBackHandler.setOperationType(OperationType.EDIT_USER_SETTINGS);
		callBackHandler.setAdditionalCheck();

		return callBackHandler;
	}
}
