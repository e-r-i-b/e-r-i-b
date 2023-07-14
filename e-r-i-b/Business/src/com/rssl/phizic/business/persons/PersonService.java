package com.rssl.phizic.business.persons;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.GUID;
import com.rssl.phizic.gate.clients.User;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 07.09.2005
 * Time: 16:51:57
 */
public class PersonService extends MultiInstancePersonService
{

	public void add(ActivePerson person) throws BusinessException
	{
		person.setLastUpdateDate(new GregorianCalendar());
		super.add(person, null);
	}

	public void createLogin(ActivePerson person) throws BusinessException
	{
		super.createLogin(person, null);
	}

	public List<Person> find(final Person person) throws BusinessException
	{
		return super.find(person, null);
	}

	public ActivePerson findByClientId(final String clientId) throws BusinessException
	{
		return super.findByClientId(clientId, null);
	}

	public Person findById(final Long personId) throws BusinessException
	{
		return super.findById(personId, null);
	}

	public Person findByIdNotNull(Long id) throws BusinessException, BusinessLogicException
	{
		return super.findByIdNotNull(id, null);
	}

	public ActivePerson findByLogin(final Login login) throws BusinessException
	{
		return super.findByLogin(login, null);
	}

	public ActivePerson findByLogin(Long loginId) throws BusinessException
	{
		return super.findByLogin(loginId, null);
	}

	public DeletedPerson findDeletedByLogin(final Login login) throws BusinessException
	{
		return super.findDeletedByLogin(login, null);
	}

	public Person findPersonByLogin(final Login login) throws BusinessException
	{
		return super.findPersonByLogin(login, null);
	}

	/**
	 * ���������� ��������� ������������ �� ID ������
	 * @param loginId - ID ������
	 * @return ������� ��������� ������������ ���� null, ���� ������ ������������ ���
	 * @throws BusinessException
	 */
	public ActivePerson findByLoginId(Long loginId) throws BusinessException
	{
		return super.findByLoginId(loginId, null);
	}

	public List<ActivePerson> getEmpoweredPersons(final Person owner) throws BusinessException
	{
		return super.getEmpoweredPersons(owner, null);
	}

	public List<Person> getAllEmpoweredPersons(final Person owner, String instanceName) throws BusinessException
	{
		return super.getAllEmpoweredPersons(owner, null);
	}

	public DeletedPerson markDeleted(ActivePerson person) throws BusinessException
	{
		person.setLastUpdateDate(new GregorianCalendar());
		return super.markDeleted(person, null);
	}

	/**
	 * �������� ������� � ������� ��� ����� �� ��������
	 * ������ ������������ ������ � ������ ���������� ����-������� �� ����������.
	 * @param person ������� ��� ����������
	 */
	public void updateAndRemoveLinks(ActivePerson person) throws BusinessException
	{
		super.updateAndRemoveLinks(person, null);
	}

	public void update(Person person) throws BusinessException
	{
		person.setLastUpdateDate(new GregorianCalendar());
		super.update(person, null);    
	}

	public void delete(final ActivePerson person,final String instanceName) throws BusinessException
	{
		super.delete(person, null);
	}

	/**
	 * ����� ������� �� ��� � ��� (��������, �������������� ��������)
	 * @param surName - �������
	 * @param name - ���
	 * @param patrName - ��������, ���� ��� null
	 * @param docSeries - ����� ���������, ���� ��� null
	 * @param docNumber - ����� ���������
	 * @param birthDate - ���� �������� �������
	 * @param codeRegion - ��, ���� ��� ������������� � ������ � ������� �� ���������� null
	 * @return ��������� �������, ���� ������� ������ �����, �������� ����������, ���� �� ������� ������ - null.
	 * @throws BusinessException, BusinessLogicException
	 */
	public ActivePerson getByFIOAndDocUnique(String surName, String name, String patrName, String docSeries, String docNumber, Calendar birthDate, String codeRegion) throws BusinessException, BusinessLogicException
	{
		List<ActivePerson> persons = getByFIOAndDoc(surName, name, patrName, docSeries, docNumber, birthDate, codeRegion);
		if (persons==null || persons.isEmpty())
			return null;

		if (persons.size()>1)
			throw new BusinessLogicException("�� ����� ���� ���������� �������� � ����������� ���, ���������� � ����� ��������.");

		return persons.get(0);
	}

	/**
	 * ����� ������� �� ��� � ��� (��������, �������������� ��������)
	 * @param surName - �������
	 * @param name - ���
	 * @param patrName - ��������, ���� ��� null
	 * @param docSeries - ����� ���������, ���� ��� null
	 * @param docNumber - ����� ���������
	 * @param birthDate - ���� �������� �������
	 * @param codeRegion - ��, ���� ��� ������������� � ������ � ������� �� ���������� null
	 * @return ������ ���������� ������
	 * @throws BusinessException
	 */
	public List<ActivePerson> getByFIOAndDoc(String surName, String name, String patrName, String docSeries, String docNumber, Calendar birthDate, String codeRegion) throws BusinessException
	{
		String FIO = (surName + name + StringHelper.getEmptyIfNull(patrName));
		String doc = (StringHelper.getEmptyIfNull(docSeries) + StringHelper.getEmptyIfNull(docNumber));
		return super.getByFIOAndDoc(FIO,doc,null, birthDate, codeRegion);
	}

	/**
	 * ����� �������������� ������� �� ��� � ��� (��������, �������������� ��������)
	 * @param surName - �������
	 * @param name - ���
	 * @param patrName - ��������, ���� ��� null
	 * @param docSeries - ����� ���������, ���� ��� null
	 * @param docNumber - ����� ���������
	 * @param birthDate - ���� �������� �������
	 * @param codeRegion - ��, ���� ��� ������������� � ������ � ������� �� ���������� null
	 * @return ������������� ������
	 * @throws BusinessException
	 */
	public ActivePerson getPotentialByFIOAndDoc(String surName, String name, String patrName, String docSeries, String docNumber, Calendar birthDate, String codeRegion) throws BusinessException
	{
		String FIO = (surName + name + StringHelper.getEmptyIfNull(patrName));
		String doc = (StringHelper.getEmptyIfNull(docSeries) + StringHelper.getEmptyIfNull(docNumber));
		return super.getPotentialByFIOAndDoc(FIO, doc, null, birthDate, codeRegion);
	}

	/**
	 * ����� �������� ������� �� ������ �������
	 * @param client - ������, ��� �������� ���� ������ � ��� � ��
	 * @return ������� �� ��, null - ���� ������� ���
	 */
	public List<ActivePerson> getByClient(Client client) throws BusinessException
	{
		List<? extends ClientDocument>  docs = client.getDocuments();
		//���� ������ ���������� ��������. ������ ������������ ������ ����, ���� ����� ����� ���������� �������� ���������� � ������������ � ������-������������ � �����������
		ClientDocument document =  docs.get(0);
		return getByFIOAndDoc(client.getSurName(), client.getFirstName(), client.getPatrName(), document.getDocSeries(), document.getDocNumber(), client.getBirthDay(), client.getOffice().getCode().getFields().get("region"));
	}

	/**
	 * ����� ������� �� �������� ��������������
	 * @param guid ������� �������������
	 * @return �������
	 * @throws BusinessException
	 */
	public ActivePerson findByGUID(GUID guid) throws BusinessException
	{
		List<ActivePerson> persons = super.getByFIOAndDoc(guid.getSurName() + guid.getFirstName() + StringHelper.getEmptyIfNull(guid.getPatrName()), guid.getPassport(), null, guid.getBirthDay(), guid.getTb());
		if (CollectionUtils.isEmpty(persons))
		{
			return null;
		}

		if (persons.size() != 1)
		{
			throw new BusinessException("�� �������� ���, ���, ��, �� ��������� " + persons.size() + " ������(�)�� ��������.");
		}

		return persons.get(0);
	}


	/**
	 *  ����� �������� ������� �� ������ � ���� ���������� ��������
	 * @param agreementNumber - ����� ��������
	 * @param agreementDate - ���� ��������
	 * @param codeRegion - ��, ���� ��� ������������� � ������ � ������� �� ���������� null
	 * @return ������� �� ��, null - ���� ������� ���
	 * @throws BusinessException
	 */
	public List<ActivePerson> getByAgreement(String agreementNumber, Calendar agreementDate, String codeRegion) throws BusinessException
	{
		return super.getByAgreement(agreementNumber, agreementDate, codeRegion, null);
	}

	/**
	 * ����� ������� �� �����, �������, �������� � ���� ��������
	 * @param firstName - ���
	 * @param surName - �������
	 * @param patrName - ��������
	 * @param birthDate - ���� ��������
	 * @param codeRegion - ��, ���� ��� ������������� � ������ � ������� �� ���������� null
	 * @return �������
	 * @throws BusinessException
	 */
	public List<Person> getByAttribute(final String firstName, final String surName, final String patrName, final Calendar birthDate, String codeRegion) throws BusinessException
	{
		return super.getByAttribute(firstName, surName, patrName, birthDate, codeRegion, null);
	}

	/**
	 * �������� ������ �������� �� ������ �����, �� ������� ��� ��������� ��������� ����
	 * @param cardNumber - ������ �����, �� ������� ��� ��������� ��������� ����
	 * @return ������ ��������
	 * @throws BusinessException
	 */
	public List<ActivePerson> findByLasLogonCardNumber(String cardNumber) throws BusinessException
	{
		return super.findByLasLogonCardNumber(cardNumber, null);
	}

	/**
	 * �������� ������� �� �������������� �� ���, ��� ������� ��� ��������� ��������� ����
	 * @param csaUserId - ������������� �� ���, ��� ������� ��� ��������� ��������� ����
	 * @return ������
	 * @throws BusinessException
	 */
	public List<ActivePerson> findByCSAUserId(String csaUserId) throws BusinessException
	{
		return super.findByCSAUserId(csaUserId, null);
	}


	/**
	 * ��������� �������� �� �������
	 * @param personId - ID �������
	 * @return �������� ������� ��� null, ���� �� �������
	 */
	public PersonTiming getPersonTimings(final long personId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<PersonTiming>()
			{
				public PersonTiming run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.clients.getPersonTimings");
					query.setLong("personId", personId);
					return (PersonTiming) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ ������ ������� �� ������
	 * @param loginId ������������� ������
	 * @return ������ ������ �������
	 * @throws BusinessException
	 */
	public LightPerson getLightPersonByLogin(Long loginId) throws BusinessException
	{
		return getLightPersonByLogin(loginId, null);
	}

	/**
	 * �������� ��������� �������
	 * @param personId ������������� �������
	 * @return ������ ����������
	 * @throws BusinessException
	 */
	public List<PersonDocument> getPersonDocuments(Long personId) throws BusinessException
	{
		return getPersonDocuments(personId, null);
	}

	/**
	 * �������� ������������� ������ � ������������� �������������� ��� �������
	 * @param loginId - ID ������ �������
	 * @return true - ����������, false - ���
	 */
	public boolean existsNullableSmsAutoAlias(final long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.clients.findNullableSmsAutoAlias");
					query.setLong("loginId", loginId);
					return query.uniqueResult() != null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ������ ������� ���� <login_id, email, mail_format>
	 * @param terbank - ����� ��, � �������� ����������� �������
	 * @param mailCount - ���������� ����� ��� �������
	 * @return - ������ ������� ���� <login_id, email, mail_format>
	 */
	public List<Object[]> getClientEmails(final String terbank, final int mailCount, final long lastId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Object[]>>()
			{
				public List<Object[]> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.clients.getNewsDistributionEmailPack");
					query.setString("terbank", terbank);
					query.setLong("lastId", lastId);
					query.setMaxResults(mailCount);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������������� ������� �� �������������� ������
	 * @param loginId ������������� ������
	 * @return ������������ ������� ��� null
	 */
	public Department getDepartmentByLoginId(final Long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Department>()
			{
				public Department run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.persons.PersonService.getDepartmentByLoginId");
					query.setParameter("loginId", loginId);
					return (Department) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������������� ������� �� �������������� ������
	 * @param personId ������������� ������
	 * @return ������������ ������� ��� null
	 */
	public Department getDepartmentByPersonId(final Long personId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Department>()
			{
				public Department run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.persons.PersonService.getDepartmentByPersonId");
					query.setParameter("personId", personId);
					return (Department) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
