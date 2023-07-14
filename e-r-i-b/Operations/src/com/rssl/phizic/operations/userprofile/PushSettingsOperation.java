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
 * �������� ��� ��������� ��������� ����������� �� push-�����������
 * @author basharin
 * @ created 14.08.13
 * @ $Author$
 * @ $Revision$
 */

public class PushSettingsOperation extends OperationBase
{
	/**
	 * ����������� Push-�����������
	 * @param authenticationContext - ������ �������
	 * @param mguid - ������������� ���������� ����������
	 * @param securityToken - ������ ������ ������������, �������������� ��������� �����������
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
	 * ��������������� Push-�����������
	 * @param authenticationContext - ������ �������
	 * @param mguid - ������������� ���������� ����������
	 * @param securityToken - ������ ������ ������������, �������������� ��������� �����������
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
	 * ���������� Push-�����������
	 * @param authenticationContext - ������ �������
	 * @param mguid - ������������� ���������� ����������
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
