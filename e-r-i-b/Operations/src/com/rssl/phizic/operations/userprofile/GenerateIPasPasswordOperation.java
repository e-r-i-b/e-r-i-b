package com.rssl.phizic.operations.userprofile;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.AuthenticationFailedException;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.ConnectorBlockedException;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.fraudMonitoring.FraudMonitoringSendersFactory;
import com.rssl.phizic.business.fraudMonitoring.exceptions.ProhibitionOperationFraudException;
import com.rssl.phizic.business.fraudMonitoring.senders.events.data.ChangePasswordInitializationData;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.config.ConfigFactory;
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
import com.rssl.phizic.utils.CardsConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author basharin
 * @ created 13.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class GenerateIPasPasswordOperation extends ConfirmableOperationBase
{
	private static final String INVALIDE_OLD_PASSWORD_ERROR_MESSAGE = "Вы указали неправильный пароль.";

	private String oldPassword;
	private String sid;
	private String login;
	private boolean needLogoff = false;

	public void initialize(String oldPassword) throws BusinessException, BusinessLogicException
	{
		//проверка подключения карты идентификатора в качестве платежной к МБК. CHG054074
		if (ConfigFactory.getConfig(CardsConfig.class).isNeedAdditionalCheckMbCard())
		{
			String cardNumber = PersonHelper.getLastClientLogin().getLastLogonCardNumber();
			if (!MobileBankManager.hasMB(cardNumber))
				throw new BusinessLogicException("Уважаемый клиент! Для смены пароля в Сбербанк Онлайн Ваша карта, по которой получен идентификатор, должна быть подключена к услуге «Мобильный банк».");
		}

		AuthenticationContext authenticationContext = AuthenticationContext.getContext();
		this.sid   = authenticationContext.getCSA_SID();
		this.login = authenticationContext.getUserId();
		this.oldPassword = oldPassword; 
	}

	/**
	 * Проверка пароля
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 * @throws SecurityLogicException
	 */
	public void checkOldPassword() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		try
		{
			//отсылаем сообщение о проверке старого пароля. нужно только клиенту, в APM сотрудника выполнять не надо.
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

		//запрос на генерацию пароля
		try
		{
			CSABackRequestHelper.sendGeneratePasswordRq(login, null, true);
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
		return new IPasPasswordSettings(changes);
	}

	public String getSuccessfulMessage()
	{
		return "Новый пароль выслан Вам в SMS-сообщении.";
	}

	public boolean isNeedLogoff()
	{
		return needLogoff;
	}
}