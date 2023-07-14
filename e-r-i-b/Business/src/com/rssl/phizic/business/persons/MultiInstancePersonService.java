package com.rssl.phizic.business.persons;

import com.rssl.phizic.auth.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.business.NotFoundException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.userDocuments.UserDocument;
import com.rssl.phizic.business.userDocuments.UserDocumentService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.password.UserPassword;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 09.07.2008
 * @ $Author$
 * @ $Revision$
 */

public class MultiInstancePersonService
{
	//*********************************************************************//
	//***************************  CLASS MEMBERS  *************************//
	//*********************************************************************//
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private final static String QUERY_PREFIX = PersonService.class.getName() + ".";

	private final static ProfileService profileService = new ProfileService();
	private final static MultiInstanceSimpleService simpleService = new MultiInstanceSimpleService();
	private final static MultiInstanceSecurityService securityService = new MultiInstanceSecurityService();
	protected static final ExternalResourceService externalResourceService = new ExternalResourceService();
	public static final String PERSON_SHADOW_INSTANCE_NAME = "Shadow";
	public static final DepartmentService departmentService = new DepartmentService();

	//*********************************************************************//
	//**************************  INSTANCE MEMBERS  ***********************//
	//*********************************************************************//

	/**
	 * �������� ������� ����� ������������
	 * @param personId ������������� ������������
	 * todo ����� �������� �� ������� �������, ���������� ��� ����������� ����� ��*  
	 * @return
	 * @throws BusinessException
	 */
	public PersonOperationMode getPersonMode(Long personId) throws BusinessException
	{
		PersonCreateConfig flowConfig = ConfigFactory.getConfig(PersonCreateConfig.class);
		//���� ��������� �������������� �� �����, �� ����� direct
		if (!flowConfig.getRegisterChangesEnable())
			return PersonOperationMode.direct;
		Person person = findById(personId,PERSON_SHADOW_INSTANCE_NAME);
		return person==null?PersonOperationMode.direct:PersonOperationMode.shadow;
	}

	/**
	 * �������� ������� ����� ������������
	 * @param personId ������������� ������������
	 * todo ����� �������� �� ������� �������, ���������� ��� ����������� ����� ��
	 * @return
	 * @throws BusinessException
	 */
	public String getPersonInstanceName(Long personId) throws BusinessException
	{

		if( PersonOperationMode.direct.equals(getPersonMode(personId)))
			return null;
		else
			return MultiInstancePersonService.PERSON_SHADOW_INSTANCE_NAME;
	}

	/**
	 * �������� ������ ������������.
	 * @param person
	 * @return
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void add(final ActivePerson person, final String instanceName) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					if (person.getRegistrationAddress() != null)
						simpleService.addOrUpdate(person.getRegistrationAddress(),instanceName);
					if (person.getResidenceAddress() != null)
						simpleService.addOrUpdate(person.getResidenceAddress(),instanceName);
					simpleService.add(person, instanceName);

					if (person.getPersonDocuments() != null)
						for(PersonDocument document : person.getPersonDocuments()){
							simpleService.addOrUpdate(document, instanceName);
					}

					Profile profile = new Profile();
					profile.setLoginId(person.getLogin().getId());
					profileService.add(profile);

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������� ������� � �������� ��������� � ��� ������(�����, �����, ����������)
	 * @param person
	 * @param instanceName
	 * @throws BusinessException
	 */
	public void delete(final ActivePerson person, final String instanceName) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Profile profile = profileService.findByLogin(person.getLogin());
					profileService.remove(profile, instanceName);

					simpleService.remove(person, instanceName);

					for (UserDocument doc : UserDocumentService.get().getUserDocumentByLogin(person.getLogin().getId()))
						UserDocumentService.get().remove(doc);

					CommonLogin login = securityService.findById(person.getLogin().getId(), instanceName);
					List<LoginBlock> blocks = securityService.getBlocksForLogin(login,new GregorianCalendar().getTime(),null,instanceName);
					for (LoginBlock block : blocks)
					{
						simpleService.remove(block, instanceName);
					}
					securityService.remove(login, instanceName);
					simpleService.remove(person.getRegistrationAddress(),instanceName);
					simpleService.remove(person.getResidenceAddress(), instanceName);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}


	/**
	 *
	 * @param person
	 * @throws BusinessException
	 */
	public void update(final Person person, final String instanceName) throws BusinessException
	{

		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					ActivePerson personToUpdate = (ActivePerson)session.merge(person);
					if (personToUpdate.getRegistrationAddress() != null)
						simpleService.addOrUpdate(personToUpdate.getRegistrationAddress(),instanceName);
					if (personToUpdate.getResidenceAddress() != null)
						simpleService.addOrUpdate(personToUpdate.getResidenceAddress(), instanceName);
					simpleService.addOrUpdate(personToUpdate,instanceName);
					List<LoginBlock> loginBlocks = securityService.getBlocksForLogin(personToUpdate.getLogin(),new GregorianCalendar().getTime(),null,instanceName);
					//������ �� �������� �������
					UserPassword userPassword = securityService.getPassword(personToUpdate.getLogin(), null);
					if (userPassword != null && instanceName!=null)
						simpleService.replicate(userPassword, instanceName);
					if (personToUpdate.getPersonDocuments() != null)
						for(PersonDocument document : personToUpdate.getPersonDocuments()){
							simpleService.addOrUpdate(document, instanceName);
					}
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� ������������ � ���������. ����� ����� ����������� ������� ����������� � ActivePerson!!!!
	 * @param person
	 * @throws BusinessException
	 */
	public DeletedPerson markDeleted(final ActivePerson person,final String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<DeletedPerson>()
			{
				public DeletedPerson run(Session session) throws Exception
				{
					DeletedPerson deletedPerson = DeletedPerson.create(person);
					session.evict(person);
					securityService.markDeleted(person.getLogin(), instanceName);
					simpleService.update(deletedPerson, instanceName);
					for (UserDocument doc : UserDocumentService.get().getUserDocumentByLogin(person.getLogin().getId()))
						UserDocumentService.get().remove(doc);
					externalResourceService.removeAll(person.getLogin());
					return deletedPerson;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������� � ������� ��� ����� �� ��������
	 * ������ ������������ ������ � ������ ���������� ����-������� �� ����������.
	 * @param person ������� ��� ����������
	 * @param instanceName ��� ���������� ��
	 */
	public void updateAndRemoveLinks(final ActivePerson person, final String instanceName) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					simpleService.update(person, instanceName);
					for (UserDocument doc : UserDocumentService.get().getUserDocumentByLogin(person.getLogin().getId()))
						UserDocumentService.get().remove(doc);
					externalResourceService.removeAll(person.getLogin());
					log.info("������ � ID=" + person.getId() + " ��������. ������ �� ��� �������� �������.");
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ����� ������������ ������ � ������ � �� �� ���������, �.�. ����� �������� � �������������������
	 * ������� ������ ���� �������� �������������.
	 * @return
	 * @throws BusinessException
	 */
	@Deprecated
	public List<ActivePerson> getAll() throws BusinessException
	{
		return simpleService.getAll(ActivePerson.class, null);
	}

	/**
	* ����� ����� ������������ ������ � ������ � �� �� ���������, �.�. ����� �������� � �������������������
	 */
	@Deprecated
	public List<DeletedPerson> getAllDeleted() throws BusinessException
	{
		return simpleService.getAll(DeletedPerson.class, null);
	}

	/**
	 * ���������� ������ Person
	 * @param person �������� ������ (����� �� �������)
	 * @return
	 * @throws BusinessException
	 */
	public List<Person> find(final Person person, String instanceName) throws BusinessException
	{
		return simpleService.find(person,instanceName);
	}

	/**
	 * @param personId
	 * @return ����� ������������ �� id, ���� ������ �� ����� ���������� null.
	 * @throws BusinessException
	 */
	public Person findById(final Long personId, String instanceName) throws BusinessException
	{
		return simpleService.findById(ActivePerson.class, personId, instanceName);
	}

	/**
	 * ����� ������������ �� id
	 * @param id
	 * @throws com.rssl.phizic.business.BusinessLogicException ������������ �� ������
	 */
	public Person findByIdNotNull(Long id, String instanceName) throws BusinessException, BusinessLogicException
	{
		Person temp = findById(id, instanceName);

		if (temp == null)
			throw new BusinessLogicException("������������ � id " + id + " �� ������");

		return temp;
	}

	public ActivePerson findByClientId(final String clientId, String instanceName) throws BusinessException
	{
		ActivePerson criteria = new ActivePerson();
		criteria.setClientId(clientId);

		return simpleService.executeQuerySingle(QUERY_PREFIX + "findByClientId", criteria,instanceName);
	}

	public ActivePerson findByLogin(final Login login, String instanceName) throws BusinessException
	{
		return findByLogin(login.getId(), instanceName);
	}

	public ActivePerson findByLogin(final Long loginId, String instanceName) throws BusinessException
	{
		List<ActivePerson> list;
		try
		{
			list = HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<ActivePerson>>()
			{
				public List<ActivePerson> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.persons.PersonService.findByLoginId");
					query.setParameter("loginId", loginId);
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		if (list.size() == 1)
			return list.get(0);
		else if (list.size() > 0)
			throw new BusinessException("������� ������ ������ ������������  ��� ������ " + loginId);
		else
			throw new NotFoundException("�� ������� �������� ������������� ��� ������ " + loginId);
	}

	/**
	 * �������� ������ ������ ������� �� ������
	 * @param loginId ������������� ������
	 * @param instanceName �������
	 * @return ������ ������ �������
	 * @throws BusinessException
	 */
	public LightPerson getLightPersonByLogin(final Long loginId, String instanceName) throws BusinessException
	{
		if (loginId == null)
		{
			throw new IllegalArgumentException("������������� ������ �� ����� ���� null.");
		}
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<LightPerson>()
			{
				public LightPerson run(Session session) throws Exception
				{
					return (LightPerson) session.getNamedQuery("com.rssl.phizic.business.persons.PersonService.getLightPersonByLogin")
							.setParameter("login_id", loginId)
							.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ��������� �������
	 * @param personId ������������� �������
	 * @param instanceName �������
	 * @return ������ ����������
	 * @throws BusinessException
	 */
	public List<PersonDocument> getPersonDocuments(final Long personId, String instanceName) throws BusinessException
	{
		if (personId == null)
		{
			throw new IllegalArgumentException("������������� ������� �� ����� ���� null.");
		}
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<PersonDocument>>()
			{
				public List<PersonDocument> run(Session session) throws Exception
				{
					return (List<PersonDocument>) session.getNamedQuery("com.rssl.phizic.business.persons.PersonService.getPersonDocuments")
							.setParameter("person_id", personId)
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ���������� ������������ �� ������
	 * @param login
	 * @param instanceName
	 * @return
	 * @throws BusinessException
	 */
	public DeletedPerson findDeletedByLogin(final Login login, String instanceName) throws BusinessException
	{
		List<DeletedPerson> list;
		try
		{
			list = HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<DeletedPerson>>()
			{
				public List<DeletedPerson> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.persons.PersonService.findDeletedByLogin");
					query.setParameter("login", login);
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		if (list.size() == 1)
			return list.get(0);
		else if (list.size() > 0)
			throw new BusinessException("������� ������ ������ ������������  ��� ������ " + login.getUserId());
		else
			throw new NotFoundException("�� ������� ��������� ������������� ��� ������ " + login.getUserId());
	}

	/**
	 * ����� ������� �� ������ �� �������� �� ���� ������ �� ��� ���
	 * @param login
	 * @param instanceName
	 * @return
	 * @throws BusinessException
	 */
	public Person findPersonByLogin(final Login login, String instanceName) throws BusinessException
	{
		List<Person> list;
		try
		{
			list = HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<Person>>()
			{
				public List<Person> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.persons.PersonService.findPersonByLogin");
					query.setParameter("login", login);
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		if (list.size() == 1)
			return list.get(0);
		else if (list.size() > 0)
			throw new BusinessException("������� ������ ������ ������������  ��� ������ " + login.getUserId());
		else
			throw new NotFoundException("�� ������� ������������� ��� ������ " + login.getUserId());
	}


	/**
	 * ���������� ��������� ������������ �� ID ������
	 * @param loginId - ID ������
	 * @param instanceName
	 * @return ������� ��������� ������������ ���� null, ���� ������ ������������ ���
	 * @throws BusinessException
	 */
	public ActivePerson findByLoginId(final Long loginId, String instanceName) throws BusinessException
	{
		if (loginId == null)
			throw new NullPointerException("�������� 'loginId' �� ����� ���� null");

		List<ActivePerson> list;
		try
		{
			list = HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<ActivePerson>>()
			{
				public List<ActivePerson> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.persons.PersonService.findByLoginId");
					query.setParameter("loginId", loginId);
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		if (list.size() == 1)
			return list.get(0);
		else if (list.size() > 0)
			throw new BusinessException("������� ������ ������ ������������  ��� LOGIN_ID " + loginId);
		else
			return null;
	}

	public void createLogin(ActivePerson person, String instanceName) throws BusinessException
	{
		try
		{
			ClientLoginGenerator clientLoginGenerator = new ClientLoginGenerator(instanceName);
			Login login = clientLoginGenerator.generate();
			person.setLogin(login);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
		catch (DublicateUserIdException e)
		{
			throw new BusinessException(e); // ��� ������������ ��� �������� �����������
		}
	}

	/**
	 * �������� ������ �� ��������� �������������� �������
	 * @param owner ������
	 * @param instanceName ��� ���������� ��
	 * @return ������ ��������������
	 */
	public List<ActivePerson> getEmpoweredPersons(final Person owner, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<ActivePerson>>()
			{
				public List<ActivePerson> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.operations.person.GetEmpoweredPersonsListOperation.list");
					query.setParameter("trustingPersonId",owner.getId() );
					return (List<ActivePerson>)query.list();
				}
			});
		}
		catch(Exception ex)
		{
			throw new BusinessException("������ ��� ��������� ������ ��������������",ex);
		}
	}

	/**
	 * �������� ���� �������������� ���������� �� �������
	 * @param owner ����������
	 * @param instanceName ��� ���������� ��
	 * @return ������ �������������
	 * @throws BusinessException
	 */
	public List<Person> getAllEmpoweredPersons(final Person owner, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<Person>>()
			{
				public List<Person> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.operations.person.GetEmpoweredPersonsListOperation.getAllt");
					query.setParameter("trustingPersonId",owner.getId() );
					return (List<Person>)query.list();
				}
			});
		}
		catch(Exception ex)
		{
			throw new BusinessException("������ ��� ��������� ������ ��������������",ex);
		}
	}

	/**
	 * �������� ������� �� ��� � ���������
	 * ��� ��� ��������, �������� ���� (����� � ����� ������ ��� ��������)
	 * @param FIO ���
	 * @param doc ����� � ����� ���������
	 * @param birthDate - ���� �������� �������
	 * @param codeRegion - ��� ��. ��� ������������� ������ ������� �� � ������� �� ���������� null
	 * @param instanceName -  ��� ���������� ��
	 * @return ������ ��������� ��������
	 */
	public List<ActivePerson> getByFIOAndDoc(String FIO, String doc, String instanceName, final Calendar birthDate, final String codeRegion) throws BusinessException
	{
		final String clientFIO = FIO.replace(" ", "").toUpperCase();
		final String clientDOC = doc.replace(" ", "");

		final String queryName = StringHelper.isEmpty(codeRegion) ?
				"com.rssl.phizic.business.persons.PersonService.findByFIOAndDoc" :
				"com.rssl.phizic.business.persons.PersonService.findByFIOAndDocInTB";
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<ActivePerson>>()
			{
				public List<ActivePerson> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(queryName);
					query.setParameter("FIO",clientFIO );
					query.setParameter("Doc", clientDOC);
					query.setParameter("birthDay", birthDate);
					if (!StringHelper.isEmpty(codeRegion))
					{
						query.setParameterList("regions", DepartmentService.getCorrectTBCodes(codeRegion));
					}
					return (List<ActivePerson>)query.list();
				}
			});
		}
		catch(Exception ex)
		{
			throw new BusinessException("������ ��� ��������� �������",ex);
		}
	}

	/**
	 * �������� �������������� ������� �� ��� � ���������
	 * ��� ��� ��������, �������� ���� (����� � ����� ������ ��� ��������)
	 * @param FIO ���
	 * @param doc ����� � ����� ���������
	 * @param birthDate - ���� �������� �������
	 * @param codeRegion - ��� ��. ��� ������������� ������ ������� �� � ������� �� ���������� null
	 * @param instanceName -  ��� ���������� ��
	 * @return ������������� ������, ���� ����
	 */
	public ActivePerson getPotentialByFIOAndDoc(String FIO, String doc, String instanceName, final Calendar birthDate, final String codeRegion) throws BusinessException
	{
		final String clientFIO = FIO.replace(" ", "").toUpperCase();
		final String clientDOC = doc.replace(" ", "");

		final String queryName = StringHelper.isEmpty(codeRegion) ?
				"com.rssl.phizic.business.persons.PersonService.findPotentialByFIOAndDoc" :
				"com.rssl.phizic.business.persons.PersonService.findPotentialByFIOAndDocInTB";
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<ActivePerson>()
			{
				public ActivePerson run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(queryName);
					query.setParameter("FIO",clientFIO );
					query.setParameter("Doc", clientDOC);
					query.setParameter("birthDay", birthDate);
					if (!StringHelper.isEmpty(codeRegion))
					{
						query.setParameterList("regions", DepartmentService.getCorrectTBCodes(codeRegion));
					}
					return (ActivePerson) query.uniqueResult();
				}
			});
		}
		catch(Exception ex)
		{
			throw new BusinessException("������ ��� ��������� �������",ex);
		}
	}

	/**
	 * �������� ������� ��������
	 * @param firstName - ��� �������
	 * @param surName - ������� �������
	 * @param patrName - �������� �������
	 * @param birthDate - ���� �������� �������
	 * @param codeRegion - ��� ��. ��� ������������� ������ ������� �� � ������� �� ���������� null
	 * @param instanceName -  ��� ���������� ��
	 * @return ������
	 * @throws BusinessException
	 */
	public List<Person> getByAttribute(final String firstName, final String surName, final String patrName, final Calendar birthDate, final String codeRegion, String instanceName) throws BusinessException
	{
		final String queryName = StringHelper.isEmpty(codeRegion) ?
				"com.rssl.phizic.business.persons.PersonService.findByAttributes" :
				"com.rssl.phizic.business.persons.PersonService.findByAttributesInTB";
		 try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<Person>>()
			{
				public List<Person> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(queryName);
					query.setParameter("firstName", firstName);
					query.setParameter("surName", surName);
					query.setParameter("patrName", patrName);
					query.setParameter("birthDay", birthDate);
					if (!StringHelper.isEmpty(codeRegion))
						query.setParameter("region", codeRegion);
					return (List<Person>)query.list();
				}								
			});
		}
		catch(Exception ex)
		{
			throw new BusinessException("������ ��� ��������� �������",ex);
		}
	}

	/**
	 *  ����� �������� ������� �� ������ � ���� ���������� ��������
	 * @param agreementNumber - ����� ��������
	 * @param agreementDate - ���� ��������
	 * @param codeRegion - ��, ���� ��� ������������� � ������ � ������� �� ���������� null
	 * @param instanceName -  ��� ���������� ��
	 * @return ������� �� ��, null - ���� ������� ���
	 * @throws BusinessException
	 */
	public List<ActivePerson> getByAgreement(final String agreementNumber, final Calendar agreementDate, final String codeRegion, String instanceName) throws BusinessException
	{
		final String queryName = StringHelper.isEmpty(codeRegion) ?
				"com.rssl.phizic.business.persons.PersonService.getByAgreement" :
				"com.rssl.phizic.business.persons.PersonService.getByAgreementInTB";
		 try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<ActivePerson>>()
			{
				public List<ActivePerson> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(queryName);
					query.setParameter("agreementNumber", agreementNumber);
					query.setParameter("agreementDate", agreementDate);
					if (!StringHelper.isEmpty(codeRegion))
					{
						query.setParameterList("regions", DepartmentService.getCorrectTBCodes(codeRegion));
					}
					return (List<ActivePerson>)query.list();
				}
			});
		}
		catch(Exception ex)
		{
			throw new BusinessException("������ ��� ��������� �������",ex);
		}		
	}

	/**
	 * �������� ������ �������� �� ������ �����, �� ������� ��� ��������� ��������� ����
	 * @param cardNumber - ������ �����, �� ������� ��� ��������� ��������� ����
	 * @param instanceName -  ��� ���������� ��
	 * @return ������ ��������
	 * @throws BusinessException
	 */
	public List<ActivePerson> findByLasLogonCardNumber(final String cardNumber, String instanceName) throws BusinessException
	{
		 try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<ActivePerson>>()
			{
				public List<ActivePerson> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.persons.PersonService.findByLasLogonCardNumber");
					query.setParameter("cardNumber", cardNumber);
					return (List<ActivePerson>)query.list();
				}
			});
		}
		catch(Exception ex)
		{
			throw new BusinessException("������ ��� ��������� �������",ex);
		}
	}

	/**
	 * �������� ������� �� �������������� �� ���, ��� ������� ��� ��������� ��������� ����
	 * @param csaUserId - ������������� �� ���, ��� ������� ��� ��������� ��������� ����
	 * @param instanceName -  ��� ���������� ��
	 * @return ������
	 * @throws BusinessException
	 */
	public List<ActivePerson> findByCSAUserId(final String csaUserId, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<ActivePerson>>()
			{
				public List<ActivePerson> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.persons.PersonService.findByCSAUserId");
					query.setParameter("csaUserId", csaUserId);
					return (List<ActivePerson>) query.list();
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException("������ ��� ��������� �������", ex);
		}
	}

	/**
	 * �������� �������� �� ��������������� �� ���
	 * @param csaUserIds - �������������� ���
	 * @return ������ ��������
	 * @throws BusinessException
	 */
	public List<ActivePerson> findByCSAUserIds(final List<String> csaUserIds) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ActivePerson>>()
			{
				public List<ActivePerson> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.persons.PersonService.findByCSAUserIds");
					query.setParameterList("csaUserIds", csaUserIds);
					return (List<ActivePerson>) query.list();
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException("������ ��� ��������� ������ ��������", ex);
		}
	}




}
