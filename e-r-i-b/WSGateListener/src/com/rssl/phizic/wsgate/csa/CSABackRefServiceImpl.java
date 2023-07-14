package com.rssl.phizic.wsgate.csa;

import com.rssl.phizic.*;
import com.rssl.phizic.auth.modes.UserRegistrationMode;
import com.rssl.phizic.business.clients.ClientDocumentImpl;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.confirmation.ConfirmationInfo;
import com.rssl.phizic.gate.confirmation.ConfirmationInfoService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.ClientIdentity;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.security.Constants;
import com.rssl.phizic.wsgate.csa.generated.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 09.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Обратный сервис общения ЦСА-Бек с ЕРИБом
 * Клиентская часть в ЦСА_Бек: com.rssl.auth.csa.back.integration.erib
 */

public class CSABackRefServiceImpl implements CSABackRefService
{
	private static final String MOBILE_TEL_TYPE_NAME = "MobileTel";
	private static final String WORK_TEL_TYPE_NAME = "WorkTel";
	private static final String HOME_TEL_TYPE_NAME = "HomeTel";
	private static final String PRIVATE_EMAIL_TYPE_NAME = "PrivateEmail";
	private static final String LEGAL_ADDRESS_TYPE = "1";
	private static final String REAL_ADDRESS_TYPE = "2";

	/**
	 * Получение информации о подтверждении операций клиентом.
	 * @param template шаблон клиента для поиска
	 * @return информация о подтверждении
	 * @throws RemoteException
	 */
	public ConfirmationInformation getConfirmationInformation(ClientTemplate template) throws RemoteException
	{
		try
		{
			ConfirmationInfoService confirmationInfoService = GateSingleton.getFactory().service(com.rssl.phizic.gate.confirmation.ConfirmationInfoService.class);
			final ConfirmationInfo confirmationInfo = confirmationInfoService.getConfirmationInfo(template.getFirstName(), template.getLastName(), template.getMiddleName(), template.getPassport(), template.getBirthDate(), template.getTb());
			return BeanHelper.copyObject(confirmationInfo, TypesCorrelation.getTypes());
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void removeErmbPhone(ClientTemplate template, String phoneNumber) throws RemoteException
	{
		try
		{
			RemoveErmbPhoneRequest request = new RemoveErmbPhoneRequest();
			request.clientIdentity = new ClientIdentity(
					template.getLastName(), template.getFirstName(), template.getMiddleName(),
					template.getPassport(),
					template.getBirthDate().getTime(),
					template.getTb()
			);
			request.phone = PhoneNumber.fromString(phoneNumber);

			PhizMBRequestCoordinator requestCoordinator = RequestCoordinatorSingleton.getPhizMBRequestCoordinator();
			RequestProcessor<RemoveErmbPhoneRequest, ?> processor = requestCoordinator.getProcessor(request);

			processor.processRequest(request);
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public ErmbInfo getErmbInformation(ClientTemplate template) throws RemoteException
	{
		try
		{
			ErmbInfoRequest request = new ErmbInfoRequest();
			request.clientIdentity = new ClientIdentity(
					template.getLastName(), template.getFirstName(), template.getMiddleName(),
					template.getPassport(),
					template.getBirthDate().getTime(),
					template.getTb()
			);

			PhizMBRequestCoordinator requestCoordinator = RequestCoordinatorSingleton.getPhizMBRequestCoordinator();
			RequestProcessor<ErmbInfoRequest, ?> processor = requestCoordinator.getProcessor(request);

			return BeanHelper.copyObject(processor.processRequest(request), TypesCorrelation.getTypes());
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public List getErmbInformationList(List templateList) throws RemoteException
	{
		try
		{
			List<ErmbInfo> result = new ArrayList<ErmbInfo>(templateList.size());
			for (Object templateRequest : templateList)
			{
				ClientTemplate template = (ClientTemplate) templateRequest;
				result.add(getErmbInformation(template));
			}
			return result;
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage(), e);
		}
	}

	/**
	 * Получение детальной информации о клиенте
	 * @param template шаблон клиента для поиска
	 * @return детальняа информация о клиенте
	 * @throws RemoteException
	 */
	public ClientInformation getClientInformation(ClientTemplate template) throws RemoteException
	{
		try
		{
			// сделали запрос CEDBO
			Client client = findClientInESB(template);

			// CEDBO не вернул ничего, ищем в ЕРИБе
			if (client == null || !client.isUDBO())
				client = findClientInERIB(template);

			// и в ЕРИБе не нашли, значит возвращать нечего
			if (client == null)
				return null;

			ClientInformation clientInformation = new ClientInformation();
			clientInformation.setFirstname(client.getFirstName());
			clientInformation.setPatrname(client.getPatrName());
			clientInformation.setSurname(client.getSurName());
			clientInformation.setBirthdate(client.getBirthDay());
			clientInformation.setDocuments(getDocuments(client));
			clientInformation.setPostAddr(getAddresses(client));
			clientInformation.setContactData(getContacts(client));
			clientInformation.setUDBO(client.isUDBO());
			return clientInformation;
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	/**
	 * Получить индивидуальный режим самостоятельной регистрации для клиента
	 * @param template шаблон клиента для поиска
	 * @return индивидуальный режим самостоятельной регистрации
	 * @throws RemoteException
	 */
	public String getUserRegistrationMode(ClientTemplate template) throws RemoteException
	{
		try
		{
			UserRegistrationMode mode = findUserRegistrationModeInERIB(template);
			if (mode == null)
				return null;
			return mode.name();
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}


	private Client findClientInERIB(ClientTemplate template) throws GateException, GateLogicException
	{
		com.rssl.phizic.gate.clients.BackRefClientService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.clients.BackRefClientService.class);
		return service.getClientByFIOAndDoc(template.getFirstName(), template.getLastName(), template.getMiddleName(), "", template.getPassport(), template.getBirthDate(), template.getTb());
	}

	private UserRegistrationMode findUserRegistrationModeInERIB(ClientTemplate template) throws GateException, GateLogicException
	{
		com.rssl.phizic.gate.clients.AdditionalClientInfoService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.clients.AdditionalClientInfoService.class);
		return service.getUserRegistrationMode(template.getFirstName(), template.getLastName(), template.getMiddleName(), template.getPassport(), template.getBirthDate(), template.getTb());
	}

	@SuppressWarnings("MethodWithTooManyParameters")
	private Client findClientInESB(ClientTemplate template) throws GateException, GateLogicException
	{
		try
		{
			ClientService clientService = GateSingleton.getFactory().service(ClientService.class);
			ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);
			//перед отправкой запроса УДБО проверяем внешнюю систему на активность
			ExternalSystemHelper.check(externalSystemGateService.findByCodeTB(template.getTb()));

			ClientImpl client = new ClientImpl();
			client.setId(template.getTb() + "000000");       //потому что com.rssl.phizicgate.esberibgate.clients.ClientServiceImpl.fillFullInfo
			client.setBirthDay(template.getBirthDate());
			client.setFirstName(template.getFirstName());
			client.setSurName(template.getLastName());
			client.setPatrName(template.getMiddleName());
			List<ClientDocument> documents = new ArrayList<ClientDocument>(1);
			documents.add(new ClientDocumentImpl(template.getPassport(), ClientDocumentType.PASSPORT_WAY));
			client.setDocuments(documents);
			// запрашиваем CEDBO по ФИО, ДУЛ, и дате рождения
			return clientService.fillFullInfo(client);
		}
		catch (Exception ignore)
		{
			//не смогли получить CEDBO -- не беда
			return null;
		}
	}

	private List<Document> getDocuments(com.rssl.phizic.gate.clients.Client client) throws Exception
	{
		return BeanHelper.copyObject(client.getDocuments(), TypesCorrelation.getTypes());
	}

	private Address getAddress(com.rssl.phizic.gate.clients.Address addressSource, String type) throws Exception
	{
		Address address = BeanHelper.copyObject(addressSource, TypesCorrelation.getTypes());
		address.setType(type);
		return address;
	}

	/**
	 * Типы адресов:
	 *	 1 – адрес регистрации,
	 *	 2 – адрес проживания,
	 *
	 * @param client клиент
	 * @return список адресов клиента
	 * @throws Exception
	 */
	private List<Address> getAddresses(com.rssl.phizic.gate.clients.Client client) throws Exception
	{
		List<Address> addresses = new ArrayList<Address>();
		com.rssl.phizic.gate.clients.Address legalAddress = client.getLegalAddress();
		if (legalAddress != null)
			addresses.add(getAddress(legalAddress, LEGAL_ADDRESS_TYPE));
		com.rssl.phizic.gate.clients.Address realAddress = client.getRealAddress();
		if (realAddress != null)
			addresses.add(getAddress(realAddress, REAL_ADDRESS_TYPE));
		return addresses;
	}

	private Contact createContact(String type, String num)
	{
		Contact contact = new Contact();
		contact.setContactType(type);
		contact.setContactNum(num);
		return contact;
	}

	private List<Contact> getContacts(com.rssl.phizic.gate.clients.Client client)
	{
		List<Contact> contacts = new ArrayList<Contact>();
		String mobilePhone = client.getMobilePhone();
		if (StringHelper.isNotEmpty(mobilePhone))
			contacts.add(createContact(MOBILE_TEL_TYPE_NAME, mobilePhone));
		String jobPhone = client.getJobPhone();
		if (StringHelper.isNotEmpty(jobPhone))
			contacts.add(createContact(WORK_TEL_TYPE_NAME, jobPhone));
		String homePhone = client.getHomePhone();
		if (StringHelper.isNotEmpty(homePhone))
			contacts.add(createContact(HOME_TEL_TYPE_NAME, homePhone));
		String email = client.getEmail();
		if (StringHelper.isNotEmpty(email))
			contacts.add(createContact(PRIVATE_EMAIL_TYPE_NAME, email));
		return contacts;
	}

	public CardInformation __forGenerateCardInformation(){return null;}

	public Document __forGenerateDocument(){return null;}

	public Address __forGenerateAddress(){return null;}

	public Contact __forGenerateContact(){return null;}
}