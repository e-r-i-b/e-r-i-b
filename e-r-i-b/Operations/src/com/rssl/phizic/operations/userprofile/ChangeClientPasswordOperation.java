package com.rssl.phizic.operations.userprofile;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.AuthenticationFailedException;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.ConnectorBlockedException;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.fraudMonitoring.FraudMonitoringSendersFactory;
import com.rssl.phizic.business.fraudMonitoring.exceptions.ProhibitionOperationFraudException;
import com.rssl.phizic.business.fraudMonitoring.senders.events.data.ChangePasswordInitializationData;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;
import com.rssl.phizic.rsa.exceptions.BlockClientOperationFraudException;
import com.rssl.phizic.rsa.exceptions.ProhibitionOperationFraudGateException;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.rsa.senders.initialization.PhaseInitializationData;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;
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

public class ChangeClientPasswordOperation extends ConfirmableOperationBase
{
	private static final String INVALIDE_OLD_PASSWORD_ERROR_MESSAGE = "Вы указали неправильный пароль.";

	private String newPassword;
	private String oldPassword;
	private String sid;
	private boolean needLogoff = false;

	public void initialize(String sid, String oldPassword, String newPassword) throws BusinessLogicException, BusinessException, SecurityLogicException
	{
		this.sid = sid;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		//проверяем пароль на совпадение со старым.
		checkOldPassword();
		//проверяем пароль на надежность и повторы.
		checkNewPassword();
	}

	public void initialize(String sid, String newPassword) throws BusinessLogicException, BusinessException, SecurityLogicException
	{
		this.sid = sid;
		this.newPassword = newPassword;
		//проверяем пароль на надежность и повторы.
		checkNewPassword();
	}

	/**
	 * Выполняет проверку пароля на надежность и на то, что он не совпадает с паролями, созданными в течении трех месяцев.
	 */
	private void checkNewPassword() throws BusinessLogicException, BusinessException
	{
		try
		{
			CSABackRequestHelper.sendValidatePasswordRq(sid, newPassword);
		}
		catch (BackException e)
		{
			log.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
		catch (BackLogicException e)
		{
			log.error(e.getMessage(), e);
			throw new BusinessLogicException(e);
		}
	}

	/**
	 * Выполняет проверку старого пароля.
	 */
	private void checkOldPassword() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		try
		{
			//отсылаем сообщение о проверке старого пароля
			CSABackRequestHelper.sendCheckPasswordRq(sid, oldPassword);
		}
		catch (ConnectorBlockedException e)
		{
			needLogoff = true;
			throw new BusinessLogicException(e.getMessage(), e);
		}
		catch (AuthenticationFailedException e)
		{
			throw new BusinessLogicException(INVALIDE_OLD_PASSWORD_ERROR_MESSAGE, e);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			log.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
	}

	public void doPreFraudControl() throws BusinessLogicException, BusinessException
	{
		try
		{
			FraudMonitoringSender sender = FraudMonitoringSendersFactory.getInstance().getSender(EventsType.CHANGE_PASSWORD);
			//noinspection unchecked
			sender.initialize(new ChangePasswordInitializationData(ClientDefinedEventType.CHANGE_PASSWORD, null, null, AuthenticationContext.getContext().getCsaProfileId()));
			sender.send();
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}

	public void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		try
		{
			FraudMonitoringSender sender = FraudMonitoringSendersFactory.getInstance().getSender(EventsType.CHANGE_PASSWORD);
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
			//отсылаем сообщение об изменении пароля.
			CSABackRequestHelper.sendChangePasswordRq(sid, newPassword);
		}
		catch (ConnectorBlockedException e)
		{
			needLogoff = true;
			throw new BusinessLogicException(e.getMessage(), e);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			log.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
	}

	public ConfirmableObject getConfirmableObject()
	{
		Map<String, String> changes = new HashMap<String, String>();
		changes.put("newPassword", newPassword);

		return new PasswordSettings(changes);
	}

	public boolean isNeedLogoff()
	{
		return needLogoff;
	}

	public String getSuccessfulMessage()
	{
		return "Пароль успешно изменен.";
	}
}