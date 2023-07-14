package com.rssl.phizic.operations.userprofile;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.push.PushDeviceStatus;
import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.messaging.push.PushTransportService;
import com.rssl.phizic.operations.OperationBase;

/**
 * Операция для изменения устройств подписанных на push-уведомления
 * @author basharin
 * @ created 14.08.13
 * @ $Author$
 * @ $Revision$
 */

public class PushSettingsOperation extends OperationBase
{
	/**
	 * Подключение Push-уведомлений
	 * @param authenticationContext - данные клиента
	 * @param mguid - Идентификатор мобильного приложения
	 * @param securityToken - Строка токена безопасности, сформированная мобильным приложением
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	@Transactional
	public void add(AuthenticationContext authenticationContext, String mguid, String securityToken) throws BusinessLogicException, BusinessException
	{
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		PushTransportService pushTransportService = MessagingSingleton.getInstance().getPushTransportService();
		try
		{
			pushTransportService.registerEvent(authenticationContext.getDeviceId(), person.asClientData(), PushDeviceStatus.ADD, securityToken, mguid);
			CSABackRequestHelper.sendChangePushSupportedRq(mguid, null, true, securityToken);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
		catch (IKFLMessagingException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Перерегистрация Push-уведомлений
	 * @param authenticationContext - данные клиента
	 * @param mguid - Идентификатор мобильного приложения
	 * @param securityToken - Строка токена безопасности, сформированная мобильным приложением
	 * @throws BusinessException
	 */
	public void update(AuthenticationContext authenticationContext, String mguid, String securityToken) throws BusinessLogicException, BusinessException
	{
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		PushTransportService pushTransportService = MessagingSingleton.getInstance().getPushTransportService();
		try
		{
			pushTransportService.registerEvent(authenticationContext.getDeviceId(), person.asClientData(), PushDeviceStatus.UPDATE, securityToken, mguid);
			CSABackRequestHelper.sendChangePushSupportedRq(mguid, null, true, securityToken);
		}
		catch (IKFLMessagingException e)
		{
			throw new BusinessException(e);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Отключение Push-уведомлений
	 * @param authenticationContext - данные клиента
	 * @param mguid - Идентификатор мобильного приложения
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	@Transactional
	public void remove(AuthenticationContext authenticationContext, String mguid) throws BusinessLogicException, BusinessException
	{
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		PushTransportService pushTransportService = MessagingSingleton.getInstance().getPushTransportService();
		try
		{
			pushTransportService.registerEvent(authenticationContext.getDeviceId(), person.asClientData(), PushDeviceStatus.DELETE, null, mguid);
			CSABackRequestHelper.sendChangePushSupportedRq(mguid, null, false, null);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
		catch (IKFLMessagingException e)
		{
			throw new BusinessException(e);
		}
	}
}
