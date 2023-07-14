package com.rssl.phizic.operations.person.search.multinode;

import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.authgate.AuthentificationSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.RestrictionException;
import com.rssl.phizic.business.clients.ClientDocumentImpl;
import com.rssl.phizic.business.login.LoginHelper;
import com.rssl.phizic.business.login.exceptions.DegradationFromUDBOToCardException;
import com.rssl.phizic.business.login.exceptions.StopClientSynchronizationException;
import com.rssl.phizic.business.login.exceptions.StopClientsSynchronizationException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.business.persons.clients.DepartmentNotFoundException;
import com.rssl.phizic.business.persons.csa.ProfileService;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankProductTypeWrapper;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.csa.Profile;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.gate.utils.OfflineExternalSystemException;
import com.rssl.phizic.operations.person.search.SearchPersonOperationBase;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.csaadmin.service.authentication.CSAAdminAuthService;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @author mihaylov
 * @ created 30.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Поиск клиент в многоблочном режиме
 */
public class SearchPersonMultiNodeOperation extends SearchPersonOperationBase
{
	private static final String NOT_FOUND_PERSON_MESSAGE    = "Не найдено ни одного клиента по заданным параметрам. Пожалуйста, проверьте указанные сведения, а также номер территориального банка, в котором обслуживается клиент.";
	private static final String AUTH_DATA_KEY               = "authData";
	private static final String CEDBO_CLIENT_KEY            = "cedboClient";

	private static final ProfileService profileService = new ProfileService();
	private static final CSAAdminAuthService csaAdminAuthService = new CSAAdminAuthService();

	private Map<String, Object> identityData = new HashMap<String, Object>();

	public void initialize() throws BusinessException, BusinessLogicException
	{

	}

	@Override
	public void initialize(Map<String, Object> identityData, UserVisitingMode userVisitingMode) throws BusinessException, BusinessLogicException
	{
		setUserVisitingMode(userVisitingMode);
		this.identityData.putAll(identityData);
		updateAdditionalData(this.identityData);
		authorizeClient(this.identityData);
	}

	public void continueSearch(UserVisitingMode userVisitingMode) throws BusinessException, BusinessLogicException
	{
		setUserVisitingMode(userVisitingMode);
		try
		{
			Map<String,Object> operationContext = csaAdminAuthService.getOperationContext();
			AuthData authData = (AuthData) operationContext.get(AUTH_DATA_KEY);
			Client cedboClient = (Client) operationContext.get(CEDBO_CLIENT_KEY);
			authorizeClient(authData, cedboClient);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}

	}

	private void authorizeClient(Map<String, Object> identityData)  throws BusinessException, BusinessLogicException
	{
		Profile profileTemplate = fillProfileTemplate(identityData);
		Profile profile = profileService.findProfileWithHistory(profileTemplate);

		if(profile == null)
		{
			authorizeNewClient(identityData);
		}
		else
		{
			AuthData authData = createAuthData(this.identityData);
			checkNode(profile.getNodeId(), Collections.<String, Object>singletonMap(AUTH_DATA_KEY, authData));
			authorizeClient(authData, null);
		}
	}

	private void authorizeNewClient(Map<String, Object> identityData) throws BusinessException, BusinessLogicException
	{
		Client client = findClient(identityData);

		if (client != null && client.isUDBO())
		{
			Profile profile = createCSAProfile(client);

			if (profile != null)
			{
				AuthData authData = createAuthData(this.identityData);

				Map<String, Object> context = new HashMap<String, Object>();
				context.put(AUTH_DATA_KEY, authData);
				context.put(CEDBO_CLIENT_KEY, client);

				checkNode(profile.getNodeId(), context);
				authorizeClient(authData, client);
			}
		}

	}

	private void checkNode(Long profileNodeId, Map<String, Object> context) throws BusinessException, BusinessLogicException
	{
		if (!ConfigFactory.getConfig(NodeInfoConfig.class).getNode(profileNodeId).isAdminAvailable())
			throw new BusinessLogicException("Профиль отсутствует.");

		if(!ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber().equals(profileNodeId))
		{
			try
			{
				csaAdminAuthService.saveOperationContext(context);
			}
			catch (GateException e)
			{
				throw new BusinessException(e);
			}
			catch (GateLogicException e)
			{
				throw new BusinessLogicException(e);
			}
			throw new ChangeNodeLogicException("Пользователь найден в другом блоке системы",profileNodeId);
		}
	}

	private void authorizeClient(AuthData authData, Client cedboClient) throws BusinessLogicException, BusinessException
	{
		try
		{
			String authToken  = RandomHelper.rand(KEY_LENGTH, RandomHelper.ENGLISH_LETTERS + RandomHelper.DIGITS);
			ActivePerson person = findClient(authData, authToken, cedboClient);
			if(person == null)
			{
				throw new BusinessLogicException("Клиент не найден");
			}
			checkPerson(person);
			setPerson(person);
			updatePerson(getPerson(), identityData, authData);
		}
		catch (DegradationFromUDBOToCardException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (StopClientSynchronizationException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (StopClientsSynchronizationException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (DepartmentNotFoundException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (RestrictionException e)
		{
			String message = getRestrictionMessage();
			if (StringHelper.isEmpty(message))
				throw e;

			throw new BusinessLogicException(message, e);
		}
	}

	protected String getRestrictionMessage()
	{
		return StringUtils.EMPTY;
	}

	private ActivePerson findClient(AuthData authData, String authToken, Client cedboClient) throws BusinessException, BusinessLogicException
	{
		return (ActivePerson) LoginHelper.synchronizeByCEDBOClient(authData, authToken, AuthentificationSource.full_version, getUserVisitingMode(), getRestriction(), cedboClient);
	}

	private Client findClient(Map<String, Object> identityData) throws BusinessException, BusinessLogicException
	{
		ClientService clientService = GateSingleton.getFactory().service(ClientService.class);
		ClientImpl clientTemplate = new ClientImpl();
		clientTemplate.setSurName((String) identityData.get("surName"));
		clientTemplate.setFirstName((String) identityData.get("firstName"));
		clientTemplate.setPatrName((String) identityData.get("patrName"));
		clientTemplate.setBirthDay(DateHelper.toCalendar((Date) identityData.get("birthDay")));
		String rbTbBranchId = identityData.get("region") + "000000";
		clientTemplate.setId(rbTbBranchId);
		List<ClientDocument> documents = new ArrayList<ClientDocument>(1);
		ClientDocumentType documentType = ClientDocumentType.valueOf((String)identityData.get("documentType"));
		String documentSeries = (String) identityData.get("documentSeries");
		String documentNumber = (String) identityData.get("documentNumber");
		if (ClientDocumentType.PASSPORT_WAY == documentType)
			//в контексте паспорт way хранится в номере  (c) /com/rssl/phizic/business/login/LoginHelper.java:832
			documents.add(new ClientDocumentImpl(documentNumber, documentType));
		else
			documents.add(new ClientDocumentImpl(documentSeries, documentNumber, documentType));

		clientTemplate.setDocuments(documents);
		String cardNumber = (String)identityData.get("cardNumber");
		try
		{
			Client client = clientService.fillFullInfo(clientTemplate);
			if(client == null && StringUtils.isNotEmpty(cardNumber))
				client = clientService.getClientByCardNumber(rbTbBranchId, cardNumber);
			return client;
		}
		catch (OfflineExternalSystemException e)
		{
			throw new BusinessLogicException("Во время выполнения запроса произошла ошибка. Повторите попытку позже.", e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Создаем профиль клиента в ЦСА
	 * @param client - данные по клиенту из шины
	 * @return профиль клиента из ЦСА
	 */
	private Profile createCSAProfile(Client client) throws BusinessException, BusinessLogicException
	{
		return profileService.createProfileByClient(client);
	}

	private Profile fillProfileTemplate(Map<String, Object> identityData)
	{
		Profile profileTemplate = new Profile();
		profileTemplate.setSurName((String) identityData.get("surName"));
		profileTemplate.setFirstName((String) identityData.get("firstName"));
		profileTemplate.setPatrName((String) identityData.get("patrName"));
		profileTemplate.setPassport(StringUtils.deleteWhitespace(StringHelper.getEmptyIfNull(identityData.get("documentSeries")) + StringHelper.getEmptyIfNull(identityData.get("documentNumber"))));
		profileTemplate.setBirthDay(DateHelper.toCalendar((Date) identityData.get("birthDay")));
		profileTemplate.setTb((String) identityData.get("region"));
		return profileTemplate;
	}

	@Override
	public ActivePerson getEntity() throws BusinessException, BusinessLogicException
	{
		ActivePerson person = getPerson();
		if(person == null)
			throw new BusinessLogicException(getNotFoundPersonMessage());
		return person;
	}

	protected String getNotFoundPersonMessage()
	{
		return NOT_FOUND_PERSON_MESSAGE;
	}

	@Override
	protected void updatePerson(ActivePerson person, Map<String, Object> identityData, AuthData authData) throws BusinessException, BusinessLogicException
	{
	}

	protected List<Class> getResourcesClasses(ActivePerson person) throws BusinessException
	{
		try
		{
			ExternalSystemHelper.check(getExternalSystem(person, BankProductTypeWrapper.getBankProductType(Card.class)));
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}

		return Collections.<Class>singletonList(Card.class);
	}

	protected Map<String, Object> getIdentityData()
	{
		return Collections.unmodifiableMap(identityData);
	}

}
