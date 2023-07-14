package com.rssl.phizic.operations.person.search;

import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.authgate.AuthentificationSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clients.ClientResourcesService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.business.login.LoginHelper;
import com.rssl.phizic.business.login.exceptions.DegradationFromUDBOToCardException;
import com.rssl.phizic.business.login.exceptions.StopClientSynchronizationException;
import com.rssl.phizic.business.login.exceptions.StopClientsSynchronizationException;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.business.operations.OperationFactoryImpl;
import com.rssl.phizic.business.operations.restrictions.ClientRestriction;
import com.rssl.phizic.business.operations.restrictions.RestrictionProviderImpl;
import com.rssl.phizic.business.payments.forms.meta.TestClientBlockedHandler;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.clients.DepartmentNotFoundException;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.utils.ExternalSystem;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 26.04.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class SearchPersonOperationBase extends OperationBase<ClientRestriction> implements SearchPersonOperation<ClientRestriction>
{
	protected static final String GREGORIAN_DATE_FORMAT = "%1$tY-%1$tm-%1$td";        //������ ����
	protected static final int KEY_LENGTH = 32;                                       //������ �����

	protected static final DepartmentService departmentService = new DepartmentService();
	private static final ClientResourcesService clientResourcesService = new ClientResourcesService();
	private static final SecurityService securityService = new SecurityService();
	private static final PersonService personService = new PersonService();

	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private ActivePerson person;
	private UserVisitingMode userVisitingMode;

	/**
	 *
	 * ���������� ������ ������� ������ �� ������� � ���� ��������
	 * @param person ������
	 * @param productType ��� ��������
	 * @return ������ ������� ������
	 * @throws BusinessException
	 */
	protected List<? extends ExternalSystem> getExternalSystem(ActivePerson person, BankProductType productType) throws BusinessException
	{
		try
		{
			ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);
			return externalSystemGateService.findByProduct(departmentService.findById(person.getDepartmentId()), productType);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return ������ ���������� ���������, ������� ����� ��������
	 * @param person
	 */
	protected abstract List<Class> getResourcesClasses(ActivePerson person) throws BusinessException;

	/**
	 * ������������� �������� �� ���������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize() throws BusinessException, BusinessLogicException
	{
		if (!PersonContext.isAvailable())
			throw new BusinessException("PersonContext �� ������������������ ��������.");

		PersonData temp = PersonContext.getPersonDataProvider().getPersonData();
		if (temp.getPerson() == null)
			throw new BusinessException("PersonContext �� ������������������ ��������.");
	}

	/**
	 * ������������� ��������
	 * @param identityData - ����������������� ������ �������
	 * @param userVisitingMode - ����� ����� �������
	 */
	public void initialize(Map<String, Object> identityData, UserVisitingMode userVisitingMode) throws BusinessException, BusinessLogicException
	{
		try
		{
			this.userVisitingMode = userVisitingMode;
			updateAdditionalData(identityData);
			AuthData authData = createAuthData(identityData);
			String authToken  = RandomHelper.rand(KEY_LENGTH, RandomHelper.ENGLISH_LETTERS + RandomHelper.DIGITS);
			person = findClient(authData, authToken);
			checkPerson(person);
			updatePerson(person, identityData, authData);
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
	}

	protected ActivePerson findClient(AuthData authData, String authToken) throws BusinessException, BusinessLogicException
	{
        return (ActivePerson) LoginHelper.synchronize(authData, authToken, AuthentificationSource.full_version, getUserVisitingMode(), getRestriction());
	}

	/**
	 * @return ��������� � �� �������
	 */
	public ActivePerson getEntity() throws BusinessException, BusinessLogicException
	{
		return person;
	}

	protected ActivePerson getPerson()
	{
		return person;
	}

	protected void setPerson(ActivePerson person)
	{
		this.person = person;
	}

	/**
	 * �������� �� ����������� ������ �� ���� �� (� ������� ������������ ��)
	 * @return true - ����� �� �� ��������
	 * @throws BusinessException
	 */
	private boolean needAdditionalSearch() throws BusinessException
	{
		OperationFactory operationFactory = new OperationFactoryImpl(new RestrictionProviderImpl());
		return operationFactory.checkAccess("SearchPersonOperation", "SeachClientsByTB");
	}

	/**
	 * ���������� �������� ������ ��������������� �������
	 * @param identityData �������� ������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected void updateAdditionalData(Map<String, Object> identityData) throws BusinessException, BusinessLogicException
	{
		//��������� ���������� ���� �� ����������� ������ �������� �� ���� ��
		if (!needAdditionalSearch())
		{
			identityData.put("region", LoginHelper.getEmployeeOfficeRegion());
			return;
		}

		//1. ���� ���� �� ���������, �� ����� ����� ������ �� ����� ��
		if (StringHelper.isNotEmpty((String) identityData.get("region")))
			return;

		//2. ���� � ���� ���� �������� � ������� ���� ��
		List<ActivePerson> persons = personService.getByFIOAndDoc((String) identityData.get("surName"), (String) identityData.get("firstName"), (String) identityData.get("patrName"),
				(String) identityData.get("documentSeries"), (String) identityData.get("documentNumber"), DateHelper.toCalendar((Date) identityData.get("birthDay")), null);
		//2.1. ���� ������ �������� ����, ��������� ��� ������ �� ����������
		if (CollectionUtils.isEmpty(persons))
		{
			identityData.put("region", LoginHelper.getEmployeeOfficeRegion());
			return;
		}

		//2.2. ���� ������ 1� ��� ����� ��������
		Department department = departmentService.findById(persons.get(0).getDepartmentId());
		if (department != null)
		{
			SBRFOfficeCodeAdapter code = new SBRFOfficeCodeAdapter(department.getCode());
			identityData.put("region", code.getRegion());
		}
	}

	/**
	 * ��������� ������ �������������� ������� �� ������ � �����
	 * @param identityData ������ �������������� � ����� ������
	 * @return ������ ��������������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected AuthData createAuthData(Map<String, Object> identityData) throws BusinessException, BusinessLogicException
	{
		AuthData authData = new AuthData();

		authData.setLastName((String) identityData.get("surName"));
		authData.setFirstName((String) identityData.get("firstName"));
		authData.setMiddleName((String) identityData.get("patrName"));
		authData.setDocument((String) identityData.get("documentNumber"));
		authData.setSeries((String) identityData.get("documentSeries"));
		authData.setDocumentType((String) identityData.get("documentType"));
		authData.setBirthDate(String.format(GREGORIAN_DATE_FORMAT, (Date) identityData.get("birthDay")));
		authData.setCB_CODE(identityData.get("region") + "000000");
		authData.setSecurityType(SecurityType.LOW); //��� ������ ������� ������ ������ ������ ������� ������������ (���� ����� ���)
		authData.setPAN((String)identityData.get("cardNumber"));

		//��������� ������ ������������ �������� � SystemLog ������� ERROR
		log.error("�������� ������ ������� ������������: ������ ������ ����� ���.");

		return authData;
	}

	protected void checkPerson(Person person) throws BusinessLogicException, BusinessException
	{
		if (!Person.ACTIVE.equals(person.getStatus()))
			throw new BusinessLogicException("������� ������� �� �����������, ������ � ��� ���������.");

		if (securityService.isLoginBlocked(person.getLogin(), Calendar.getInstance().getTime()))
			throw new BusinessLogicException(TestClientBlockedHandler.CLIENT_BLOCKED_MESSAGE);
	}

	protected abstract void updatePerson(ActivePerson person, Map<String, Object> identityData, AuthData authData) throws BusinessException, BusinessLogicException;

	protected void loadProducts(ActivePerson person) throws BusinessException
	{
		List<Class> resources = getResourcesClasses(person);
		if (CollectionUtils.isNotEmpty(resources))
			clientResourcesService.updateResources(person, false, resources.toArray(new Class[resources.size()]));
	}

	public void continueSearch(UserVisitingMode userVisitingMode) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException("����������� ������ �������� ������ � ������������ ������");
	}

	protected void setUserVisitingMode(UserVisitingMode userVisitingMode)
	{
		this.userVisitingMode = userVisitingMode;
	}

	protected UserVisitingMode getUserVisitingMode()
	{
		return userVisitingMode;
	}
}
