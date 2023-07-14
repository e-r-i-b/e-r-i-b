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
	 * Возвращает активного пользователя по ID логина
	 * @param loginId - ID логина
	 * @return инстанс активного пользователя либо null, если такого пользователя нет
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
	 * Обновить персону и удалить все линки на продукты
	 * Сейчас используется только в случае обновления УДБО-клиента до карточного.
	 * @param person персона для обновления
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
	 * Найти клиента по ФИО и ДУЛ (документ, удостоверяющий личность)
	 * @param surName - фамилия
	 * @param name - имя
	 * @param patrName - отчество, если нет null
	 * @param docSeries - серия документа, если нет null
	 * @param docNumber - номер документа
	 * @param birthDate - дата рождения клиента
	 * @param codeRegion - ТБ, если нет необходимости в поиске в разрезе ТБ передавать null
	 * @return Найденную персону, если найдено больше одной, кидается исключение, если не найдено вообще - null.
	 * @throws BusinessException, BusinessLogicException
	 */
	public ActivePerson getByFIOAndDocUnique(String surName, String name, String patrName, String docSeries, String docNumber, Calendar birthDate, String codeRegion) throws BusinessException, BusinessLogicException
	{
		List<ActivePerson> persons = getByFIOAndDoc(surName, name, patrName, docSeries, docNumber, birthDate, codeRegion);
		if (persons==null || persons.isEmpty())
			return null;

		if (persons.size()>1)
			throw new BusinessLogicException("Не может быть нескольких клиентов с совпадающим ФИО, документом и датой рождения.");

		return persons.get(0);
	}

	/**
	 * Найти клиента по ФИО и ДУЛ (документ, удостоверяющий личность)
	 * @param surName - фамилия
	 * @param name - имя
	 * @param patrName - отчество, если нет null
	 * @param docSeries - серия документа, если нет null
	 * @param docNumber - номер документа
	 * @param birthDate - дата рождения клиента
	 * @param codeRegion - ТБ, если нет необходимости в поиске в разрезе ТБ передавать null
	 * @return Список подходящих персон
	 * @throws BusinessException
	 */
	public List<ActivePerson> getByFIOAndDoc(String surName, String name, String patrName, String docSeries, String docNumber, Calendar birthDate, String codeRegion) throws BusinessException
	{
		String FIO = (surName + name + StringHelper.getEmptyIfNull(patrName));
		String doc = (StringHelper.getEmptyIfNull(docSeries) + StringHelper.getEmptyIfNull(docNumber));
		return super.getByFIOAndDoc(FIO,doc,null, birthDate, codeRegion);
	}

	/**
	 * Найти потенциального клиента по ФИО и ДУЛ (документ, удостоверяющий личность)
	 * @param surName - фамилия
	 * @param name - имя
	 * @param patrName - отчество, если нет null
	 * @param docSeries - серия документа, если нет null
	 * @param docNumber - номер документа
	 * @param birthDate - дата рождения клиента
	 * @param codeRegion - ТБ, если нет необходимости в поиске в разрезе ТБ передавать null
	 * @return потенциальный клиент
	 * @throws BusinessException
	 */
	public ActivePerson getPotentialByFIOAndDoc(String surName, String name, String patrName, String docSeries, String docNumber, Calendar birthDate, String codeRegion) throws BusinessException
	{
		String FIO = (surName + name + StringHelper.getEmptyIfNull(patrName));
		String doc = (StringHelper.getEmptyIfNull(docSeries) + StringHelper.getEmptyIfNull(docNumber));
		return super.getPotentialByFIOAndDoc(FIO, doc, null, birthDate, codeRegion);
	}

	/**
	 * Поиск активной персоны по данным клиента
	 * @param client - клиент, для которого ищем запись у нас в БД
	 * @return персону из БД, null - если персоны нет
	 */
	public List<ActivePerson> getByClient(Client client) throws BusinessException
	{
		List<? extends ClientDocument>  docs = client.getDocuments();
		//Берём первый попавшийся документ. Сейчас возвращается только один, если будет много необходимо написать компаратор в соответствии с бизнес-требованиями и сортировать
		ClientDocument document =  docs.get(0);
		return getByFIOAndDoc(client.getSurName(), client.getFirstName(), client.getPatrName(), document.getDocSeries(), document.getDocNumber(), client.getBirthDay(), client.getOffice().getCode().getFields().get("region"));
	}

	/**
	 * Поиск персоны по внешнему идентификатору
	 * @param guid внешний идентификатор
	 * @return персона
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
			throw new BusinessException("По заданным ФИО, ДУЛ, ДР, ТБ вернулось " + persons.size() + " профил(я)ей клиентов.");
		}

		return persons.get(0);
	}


	/**
	 *  Поиск активной персоны по номеру и дате заключения договора
	 * @param agreementNumber - номер договора
	 * @param agreementDate - дата договора
	 * @param codeRegion - ТБ, если нет необходимости в поиске в разрезе ТБ передавать null
	 * @return персоны из БД, null - если персоны нет
	 * @throws BusinessException
	 */
	public List<ActivePerson> getByAgreement(String agreementNumber, Calendar agreementDate, String codeRegion) throws BusinessException
	{
		return super.getByAgreement(agreementNumber, agreementDate, codeRegion, null);
	}

	/**
	 * Поиск персоны по имени, фамилии, отчеству и дате рождения
	 * @param firstName - имя
	 * @param surName - фамилия
	 * @param patrName - отчество
	 * @param birthDate - дата рождения
	 * @param codeRegion - ТБ, если нет необходимости в поиске в разрезе ТБ передавать null
	 * @return персона
	 * @throws BusinessException
	 */
	public List<Person> getByAttribute(final String firstName, final String surName, final String patrName, final Calendar birthDate, String codeRegion) throws BusinessException
	{
		return super.getByAttribute(firstName, surName, patrName, birthDate, codeRegion, null);
	}

	/**
	 * Получить список клиентов по номеру карты, по который был произведён последний вход
	 * @param cardNumber - номеру карты, по который был произведён последний вход
	 * @return список клиентов
	 * @throws BusinessException
	 */
	public List<ActivePerson> findByLasLogonCardNumber(String cardNumber) throws BusinessException
	{
		return super.findByLasLogonCardNumber(cardNumber, null);
	}

	/**
	 * Получить клиента по идентификатору из ЦСА, под которым был произведён последний вход
	 * @param csaUserId - идентификатор из ЦСА, под которым был произведён последний вход
	 * @return клиент
	 * @throws BusinessException
	 */
	public List<ActivePerson> findByCSAUserId(String csaUserId) throws BusinessException
	{
		return super.findByCSAUserId(csaUserId, null);
	}


	/**
	 * Загрузить тайминги по клиенту
	 * @param personId - ID клиента
	 * @return тайминги клиента или null, если не найдено
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
	 * Получить легкую версию клиента по логину
	 * @param loginId идентификатор логина
	 * @return легкая версия клиента
	 * @throws BusinessException
	 */
	public LightPerson getLightPersonByLogin(Long loginId) throws BusinessException
	{
		return getLightPersonByLogin(loginId, null);
	}

	/**
	 * Получить документы клиента
	 * @param personId идентификатор клиента
	 * @return список документов
	 * @throws BusinessException
	 */
	public List<PersonDocument> getPersonDocuments(Long personId) throws BusinessException
	{
		return getPersonDocuments(personId, null);
	}

	/**
	 * Проверка существования линков с незаполненным автоматическим смс алиасом
	 * @param loginId - ID логина клиента
	 * @return true - существуют, false - нет
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
	 * Возвращает список записей вида <login_id, email, mail_format>
	 * @param terbank - номер ТБ, к которому принадлежат клиенты
	 * @param mailCount - количество писем для выборки
	 * @return - список записей вида <login_id, email, mail_format>
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
	 * Получить подразделение клиента по идентификатору логина
	 * @param loginId идентификатор логина
	 * @return Подраздление клиента или null
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
	 * Получить подразделение клиента по идентификатору логина
	 * @param personId идентификатор логина
	 * @return Подраздление клиента или null
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
