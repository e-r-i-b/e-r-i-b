package com.rssl.phizic.operations.mobileApplications;

import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.csa.ConnectorsService;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.push.PushDeviceStatus;
import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.person.Person;

import java.util.*;

/**
 * @ author: Gololobov
 * @ created: 06.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class MobileApplicationsLockOperation extends OperationBase implements ListEntitiesOperation
{
	private ActivePerson activePerson;
	private static final PersonService personService = new PersonService();
	private static final MobilePlatformService mobilePlatformService = new MobilePlatformService();
	private List<ConnectorInfo> mapiConnectors;
	private List<ConnectorInfo> socialConnectors = new ArrayList<ConnectorInfo>();

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void initialize(Long personId) throws BusinessException, BusinessLogicException
	{
		Person person = personService.findById(personId);
		if (person == null)
			throw new IllegalArgumentException("������ �� ����� ���� null");
		this.activePerson = (ActivePerson) person;
		mapiConnectors = ConnectorsService.getClientMAPIConnectors(activePerson);
	}

	/**
	 * ���������������� �������� ��������������� ������ �������
	 * @param sid ������������ ����� �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(String sid) throws BusinessException, BusinessLogicException
	{
		if (!PersonContext.isAvailable())
			throw new BusinessException("PersonContext �� ������������������ ��������.");

		this.activePerson = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		if (sid == null)
			throw new IllegalArgumentException("SID �� ����� ���� null");
		mapiConnectors = ConnectorsService.getClientMAPIConnectors(sid);
		Set<String> socialPlatformIds = mobilePlatformService.getSocialPlatformIds();
		for (ConnectorInfo mapiConnector : mapiConnectors)
		{
			if (!socialPlatformIds.contains(mapiConnector.getDeviceInfo()))
				socialConnectors.add(mapiConnector);
		}
	}

	/**
	 * ������ ������������ ��������� ��������� ������������
	 * @return ������ ������������ ��������� ��������� ������������
	 */
	public List<ConnectorInfo> getClientMobileApplications() throws BusinessException, BusinessLogicException
	{
		return mapiConnectors;
	}

	/**
	 * ������ ������������ ��������� ��������� ������������
	 * @return ������ ������������ ��������� ��������� ������������. ��� ������ ����.
	 */
	public List<ConnectorInfo> getClientSocialApplications() throws BusinessException, BusinessLogicException
	{
		return socialConnectors;
	}

	private void doCancel(ConnectorInfo application) throws BusinessException, BusinessLogicException
	{
		if (application == null)
		{
			throw new IllegalArgumentException("���������� �� ����� ��� null");
		}
		try
		{
			if (application.isPushSupported())
				MessagingSingleton.getInstance().getPushTransportService().registerEvent(application.getDeviceID(), activePerson.asClientData(), PushDeviceStatus.DELETE, null, application.getGuid());

			ConnectorsService.closeMobileConnector(application.getGuid());
		}
		catch (IKFLMessagingException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ���������� ����������
	 * @param appId ����������� ����������.
	 */
	@Transactional
	public void cancelMobileApplication(Long appId) throws BusinessException, BusinessLogicException
	{
		if (appId == null)
		{
			throw new IllegalArgumentException("������������ ���������� �� ����� ���� null");
		}
		for (ConnectorInfo clientMAPIConnector : getClientMobileApplications())
		{
			if (appId.equals(clientMAPIConnector.getId()))
			{
				doCancel(clientMAPIConnector);
				return;
			}
		}
	}

	/**
	 * ��������� ��� ���������� ������������.
	 */
	@Transactional
	public void cancelAllMobileApplications() throws BusinessLogicException, BusinessException
	{
		for (ConnectorInfo clientMAPIConnector : getClientMobileApplications())
		{
			doCancel(clientMAPIConnector);
		}
	}
}
