package com.rssl.phizic.business.ermb.migration;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clients.ClientDocumentImpl;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.auxiliary.cod.CODWebService;
import com.rssl.phizic.business.persons.PersonDocumentImpl;
import com.rssl.phizic.business.persons.PersonDocumentTypeComparator;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.clients.DocumentTypeComparator;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.way4u.UserInfoImpl;
import com.rssl.phizicgate.way4u.messaging.Way4uUserInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * ���������� ������� ������ � ����������� ����
 * @author Puzikov
 * @ created 20.06.14
 * @ $Author$
 * @ $Revision$
 */

public class CodWayErmbConnectionSender
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	private static final ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();

	private final boolean sendCod;
	private final boolean sendWay;

	/**
	 * ctor
	 * ���������� ��������� � ��� (���� �����)
	 * ���������� ��������� � Way (���� �����) (��� ���������� ��������� � �������)
	 */
	public CodWayErmbConnectionSender()
	{
		ErmbConfig ermbConfig = ConfigFactory.getConfig(ErmbConfig.class);

		this.sendCod = true;
		this.sendWay = ermbConfig.isActivateErmbToWay4();
	}

	/**
	 * ��������� ��������� � ����������� � ���� �� ������� ������� (WAY/���)
	 * ���� �������� ��������, �� ����������� ���������� ��� ��������� �������.
	 * ��������� ������� � ��
	 * @param profile ���� �������
	 */
	public void sendErmbConnected(ErmbProfileImpl profile)
	{
		sendErmbConnectedFlag(profile, true);
	}

	/**
	 * ��������� ��������� �� ���������� ���� �� ������� ������� (WAY/���)
	 * ������ �������� ������������
	 * @param profile ���� �������
	 */
	public void sendErmbDisconnected(ErmbProfileImpl profile)
	{
		sendErmbConnectedFlag(profile, false);
	}

	private void sendErmbConnectedFlag(ErmbProfileImpl profile, boolean connectionFlag)
	{
		Person person = profile.getPerson();

		boolean sendWaySuccess = profile.isWayActivated();
		if (sendWay && !sendWaySuccess)
		{
			try
			{
				sendErmbConnectedWay(person, connectionFlag);
				sendWaySuccess = true;
			}
			catch (BusinessException e)
			{
				log.error("������ �������� ���������� � Way �� ��������� ������� ����������� ����. id=" + profile.getId(), e);
			}
		}

		boolean sendCodSuccess = profile.isCodActivated();
		if (sendCod && !sendCodSuccess)
		{
			try
			{
				PersonDocument codDocument = findDocumentForCod(person);
				sendErmbConnectedCod(person, codDocument, connectionFlag);
				sendCodSuccess = true;
			}
			catch (BusinessException e)
			{
				log.error("������ �������� ���������� � ��� �� ��������� ������� ����������� ����. id=" + profile.getId(), e);
			}
			catch (BusinessLogicException e)
			{
				log.warn("���������� ��������� ���������� � ��� �� ��������� ������� ����������� ����: " + e.getMessage() + " id=" + profile.getId());
			}
		}

		//��������� ���������� ������ ��� �����������
		if (connectionFlag)
		{
			profile.setWayActivated(sendWaySuccess);
			profile.setCodActivated(sendCodSuccess);
			profile.setActivationTryDate(Calendar.getInstance());
			try
			{
				profileService.addOrUpdate(profile);
			}
			catch (BusinessException e)
			{
				log.error("�� ������� �������� ���������� � ������� �������� ������� ����������� ���� � Way/���", e);
			}
		}
	}

	private void sendErmbConnectedWay(Person person, boolean isErmbConnected) throws BusinessException
	{
		UserInfoImpl userInfo = new UserInfoImpl();
		userInfo.setFirstname(person.getFirstName());
		userInfo.setSurname(person.getSurName());
		userInfo.setPatrname(person.getPatrName());
		userInfo.setBirthdate(person.getBirthDay());
		PersonDocument document = PersonHelper.getOrMakePersonPassportWay(person);
		if (document == null)
		{
			throw new BusinessException("�������� �������� ����������� ������� � ���� � WAY4 ����������. � ������� ����������� ������� WAY.");
		}
		userInfo.setPassport(StringHelper.getEmptyIfNull(document.getDocumentSeries()) + StringHelper.getEmptyIfNull(document.getDocumentNumber()));
		try
		{
			Way4uUserInfoService.getInstance().sendErmbConnectInfo(userInfo, isErmbConnected);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
		catch (GateTimeOutException e)
		{
			throw new BusinessException(e);
		}
	}

	private void sendErmbConnectedCod(Person person, PersonDocument codDocument, boolean isErmbConnected) throws BusinessException
	{
		CODWebService service = new CODWebService();
		service.connectClient(isErmbConnected, person, codDocument);
	}

	/**
	 * ���������� ������ ��� ���, �� � ����������� �������� Way
	 */
	private PersonDocument findDocumentForCod(Person person) throws BusinessException, BusinessLogicException
	{
		//��������� � ��
		List<PersonDocument> personDocuments = filterNoWayDocuments(new ArrayList<PersonDocument>(person.getPersonDocuments()));
		Collections.sort(personDocuments, new PersonDocumentTypeComparator());
		if (CollectionUtils.isNotEmpty(personDocuments))
			return personDocuments.get(0);

		//��������� �� CEDBO
		List<? extends ClientDocument> clientDocuments = filterNoWayDocuments(checkUdboAndGetDocuments(person));
		Collections.sort(clientDocuments, new DocumentTypeComparator());
		if (CollectionUtils.isEmpty(clientDocuments))
			throw new BusinessLogicException("�� ������ ������� ��� �������");

		return new PersonDocumentImpl(clientDocuments.get(0));
	}

	private <T> List<T> filterNoWayDocuments(List<T> documents)
	{
		CollectionUtils.filter(documents, new Predicate()
		{
			public boolean evaluate(Object document)
			{
				if (document instanceof PersonDocument)
					return ((PersonDocument) document).getDocumentType() != PersonDocumentType.PASSPORT_WAY;
				else if (document instanceof ClientDocument)
					return ((ClientDocument) document).getDocumentType() != ClientDocumentType.PASSPORT_WAY;
				else
					return false;
			}
		});
		return documents;
	}

	private List<? extends ClientDocument> checkUdboAndGetDocuments(Person person) throws BusinessException, BusinessLogicException
	{
		try
		{
			String tb = PersonHelper.getPersonTb(person);
			ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);
			ExternalSystemHelper.check(externalSystemGateService.findByCodeTB(tb));

			ClientService clientService = GateSingleton.getFactory().service(ClientService.class);
			ClientImpl temp = new ClientImpl();
			temp.setId(tb + "000000");
			temp.setFirstName(person.getFirstName());
			temp.setPatrName(person.getPatrName());
			temp.setSurName(person.getSurName());
			List<ClientDocument> documents = new ArrayList<ClientDocument>(1);
			for (PersonDocument document : person.getPersonDocuments())
			{
				if(document.getDocumentType() == PersonDocumentType.PASSPORT_WAY)
				{
					ClientDocumentImpl doc = new ClientDocumentImpl();
					doc.setDocSeries(document.getDocumentSeries());
					doc.setDocumentType(ClientDocumentType.PASSPORT_WAY);
					documents.add(doc);
				}
				else
				{
					documents.add(new ClientDocumentImpl(document));
				}
			}
			temp.setDocuments(documents);
			temp.setBirthDay(person.getBirthDay());

			Client client = clientService.fillFullInfo(temp);
			if (client == null || !client.isUDBO())
				throw new BusinessLogicException("� ������� �� �������� ����");

			return client.getDocuments();
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return �������� � ���
	 */
	public boolean isSendCod()
	{
		return sendCod;
	}

	/**
	 * @return �������� � Way
	 */
	public boolean isSendWay()
	{
		return sendWay;
	}
}
