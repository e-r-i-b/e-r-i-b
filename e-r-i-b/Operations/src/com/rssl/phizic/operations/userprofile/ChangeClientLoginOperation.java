package com.rssl.phizic.operations.userprofile;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.LoginAlreadyRegisteredException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.fraudMonitoring.FraudMonitoringSendersFactory;
import com.rssl.phizic.business.fraudMonitoring.exceptions.ProhibitionOperationFraudException;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;
import com.rssl.phizic.rsa.exceptions.BlockClientOperationFraudException;
import com.rssl.phizic.rsa.exceptions.ProhibitionOperationFraudGateException;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.rsa.senders.initialization.PhaseInitializationData;
import com.rssl.phizic.rsa.senders.types.EventsType;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityLogicException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author basharin
 * @ created 12.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class ChangeClientLoginOperation extends ConfirmableOperationBase
{
	private static final String LOGIN_ALREADY_REGISTERED_MESSAGE = "Такой логин уже используется в системе. Пожалуйста, укажите другой логин.";

	private String newLogin;
	private String sid;

	public void initialize(String sid, String newLogin) throws BusinessLogicException, BusinessException
	{
		this.sid = sid;
		this.newLogin = newLogin;
	}

	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		try
		{
			FraudMonitoringSender sender = FraudMonitoringSendersFactory.getInstance().getSender(EventsType.CHANGE_LOGIN_ID);
			//noinspection unchecked
			sender.initialize(new PhaseInitializationData(InteractionType.ASYNC, PhaseType.WAITING_FOR_RESPONSE));
			sender.send();
		}
		catch (ProhibitionOperationFraudGateException e)
		{
			throw new ProhibitionOperationFraudException(e.getMessage(), e);
		}
		catch (BlockClientOperationFraudException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			log.error(e);
		}

		try
		{
			//отсылаем сообщение об изменении логина.
			CSABackRequestHelper.sendChangeLoginRq(sid, newLogin);
		}
		catch (LoginAlreadyRegisteredException e)
		{
			throw new BusinessLogicException(LOGIN_ALREADY_REGISTERED_MESSAGE, e);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}

	public ConfirmableObject getConfirmableObject()
	{
		Map<String, String> changes = new HashMap<String, String>();
		changes.put("newLogin", newLogin);

		return new LoginSettings(changes);
	}

	public void doPreFraudControl()
	{
		try
		{
			FraudMonitoringSender sender = FraudMonitoringSendersFactory.getInstance().getSender(EventsType.CHANGE_LOGIN_ID);
			//noinspection unchecked
			sender.initialize(new PhaseInitializationData(InteractionType.ASYNC, PhaseType.SENDING_REQUEST));
			sender.send();
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}

	public void setNewLogin(String newLogin)
	{
		this.newLogin = newLogin;
	}

	public String getNewLogin()
	{
		return newLogin;
	}

	/**
	 * Устанавливает идентификатор сессии.
	 *
	 * @param sid идентификатор сессии.
	 */
	public void setSID(String sid)
	{
		//Для получения sid использовать AuthenticationManager.getContext(currentRequest()).
		this.sid = sid;
	}

	public String getSuccessfulMessage()
	{
		return "Логин успешно изменен.";
	}
}
