package com.rssl.phizic.business.persons.oldIdentity;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.business.NotFoundException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.type.PersonIdentity;
import com.rssl.phizic.common.type.PersonOldIdentity;
import com.rssl.phizic.common.type.PersonOldIdentityStatus;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Calendar;
import java.util.List;

/**
 * Сервис для работы с идентификационными данными клиента
 * User: moshenko
 * Date: 18.03.2013
 * Time: 13:54:45
 */
public class PersonIdentityService
{
	private final static MultiInstanceSimpleService SIMPLE_SERVICE = new MultiInstanceSimpleService();
	private final static PersonService PERSON_SERVICE = new PersonService();
	private final static String DUPLICATION = "Пользователь с такими данными уже существует в системе";

	/**
	 * для добавления пользуем только это метод, чтобы не было дублирования.
	 * TODO: после переноса ЕРМБ в отдельную базу реализовать проверку на уникальность на уровне БД
	 * @param identity старые данные клиента
	 */
	public void addOrUpdate(PersonOldIdentity identity) throws BusinessException, DuplicationIdentityException
	{
		try
		{
			SIMPLE_SERVICE.addOrUpdateWithConstraintViolationException(identity, null);
		}
		catch (ConstraintViolationException ex)
		{
			throw new DuplicationIdentityException(ex);
		}
	}

	/**
	 * Проверка уникальности идентификационных данных клиента в БД ЕРИБ
	 * @param surName фамлиия
	 * @param firstName имя
	 * @param patrName отчество
	 * @param docSeries серия ДУЛ
	 * @param docNumber номер ДУЛ
	 * @param birthday ДР
	 * @throws BusinessException
	 * @throws DuplicationIdentityException
	 */
	private void checkUnique(String surName, String firstName, String patrName, String docSeries, String docNumber, Calendar birthday) throws BusinessException, DuplicationIdentityException
	{
		//проверка на дублирования по текущим данным.
		List<ActivePerson> persList = PERSON_SERVICE.getByFIOAndDoc(surName, firstName, patrName, docSeries, docNumber, birthday, null);

		if (!persList.isEmpty())
			throw new DuplicationIdentityException(DUPLICATION);
	}

	/**
	 * @param identity старые данные клиента
	 */
	public void markDelete(PersonOldIdentity identity) throws BusinessException
	{
		identity.setStatus(PersonOldIdentityStatus.DELETED);
		SIMPLE_SERVICE.addOrUpdate(identity, null);
	}

	/**
	 *
	 * @param surName
	 * @param name
	 * @param patrName
	 * @param docSeries
	 * @param docNumber
	 * @param birthDate
	 * @return
	 * @throws BusinessException
	 */
	public PersonOldIdentity getByFIOAndDoc(String surName, String name, String patrName, String docSeries, String docNumber, final Calendar birthDate) throws BusinessException
	{
		String FIO = (surName + name + StringHelper.getEmptyIfNull(patrName)).toUpperCase();
		String doc = (StringHelper.getEmptyIfNull(docSeries) + StringHelper.getEmptyIfNull(docNumber));
		return getByFIOAndDoc(FIO, doc, birthDate);
	}

	public PersonOldIdentity getByFIOAndDoc(final String FIO, final String doc, final Calendar birthDate) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(null).execute(new HibernateAction<PersonOldIdentity>()
			{
				public PersonOldIdentity run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.persons.PersonOldIdentityService.findByFIOAndDoc");
					query.setParameter("FIO", FIO);
					query.setParameter("Doc", doc);
					query.setParameter("birthDay", birthDate);
					return (PersonOldIdentity) query.uniqueResult();
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException("Ошибка при получении старых данных клиента", ex);
		}
	}

	/**
	 * @param person  профиль клиента
	 * @return список  старых данных
	 */
	public List<PersonOldIdentity> getListByProfile(Person person) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(PersonOldIdentity.class);
		criteria.add(Expression.eq("person", person));
		criteria.addOrder(Order.asc("id"));
		return SIMPLE_SERVICE.find(criteria, null);
	}

	/**
	 * Найти personIdentity по идентификатору
	 * @param id данных
	 * @return устаревшие даные
	 */
	public PersonOldIdentity getIdentityById(Long id) throws BusinessException
	{
		return SIMPLE_SERVICE.findById(PersonOldIdentity.class, id, null);
	}

	/**
	 * Обновление идентификационных данных клиента.
	 * Текущая identity помещается в историю, а текущей становится newIdentity.
	 * Старая identity, которую обновляют, обязана быть в БД, иначе выбрасывается NotFoundException
	 * @param personId id персоны (ЕИК)
	 * @param oldIdentity старые идентификационные данные
	 * @param newIdentity новые идентификационные данные
	 * @param employee логин сотрудника, обновляющего данные
	 * @throws BusinessException
	 */
	public void updateIdentity(Long personId, PersonIdentity oldIdentity, PersonIdentity newIdentity, CommonLogin employee) throws BusinessException, DuplicationIdentityException
	{
		Person person = PERSON_SERVICE.findById(personId);
		if (person == null)
			throw new BusinessException(String.format("Персона с id=%s не найдена", personId));
		updateIdentity(person, oldIdentity, newIdentity, null);
	}

	/**
	 * Обновление идентификационных данных клиента.
	 * Текущая identity помещается в историю, а текущей становится newIdentity.
	 * Старая identity, которую обновляют, обязана быть в БД, иначе выбрасывается NotFoundException
	 * @param person персона
	 * @param oldIdentity старые идентификационные данные
	 * @param newIdentity новые идентификационные данные
	 * @param employee логин сотрудника, обновляющего данные
	 * @return обновленная персона
	 * @throws BusinessException
	 */
	@Transactional
	public Person updateIdentity(Person person, PersonIdentity oldIdentity, PersonIdentity newIdentity, CommonLogin employee) throws BusinessException, DuplicationIdentityException
	{
		PersonDocument document = null; //текущий ДУЛ в БД
		for (PersonDocument doc : person.getPersonDocuments())
		{
			if (oldIdentity.getDocType() == doc.getDocumentType()
				&& StringHelper.equalsIgnoreCaseStrip(StringHelper.getEmptyIfNull(oldIdentity.getDocSeries()) + StringHelper.getEmptyIfNull(oldIdentity.getDocNumber()),
					StringHelper.getEmptyIfNull(doc.getDocumentSeries()) + StringHelper.getEmptyIfNull(doc.getDocumentNumber())))
			{
				document = doc;
				break;
			}
		}
		if (document == null)
			throw new NotFoundException(String.format("Не найдена identity с ДУЛ: тип=%s, серия=%s, номер=%s", oldIdentity.getDocType(), oldIdentity.getDocSeries(), oldIdentity.getDocNumber()));

		//1. запоминаем текущую идентити. Сохранение в историю будет сделано после обновления новыми данными во избежание дублирования
		PersonOldIdentity currentIdentity = PersonHelper.rememberCurrentIdentity(person, document, employee);

		//2. новая идентити становится текущей
		Person updatedPerson = refreshCurrentIdentity(newIdentity, person, document);

		//3. сохраняем старую идентити в историю
		addOrUpdate(currentIdentity);

		return updatedPerson;
	}

	private Person refreshCurrentIdentity(PersonIdentity newIdentity, Person person, PersonDocument document) throws BusinessException, DuplicationIdentityException
	{
		//перед обновлением проверяем уникальность новых данных
		checkUnique(newIdentity.getSurName(), newIdentity.getFirstName(), newIdentity.getPatrName(), newIdentity.getDocSeries(), newIdentity.getDocNumber(), newIdentity.getBirthDay());
		person.setFirstName(newIdentity.getFirstName());
		person.setSurName(newIdentity.getSurName());
		person.setPatrName(newIdentity.getPatrName());
		person.setBirthDay(newIdentity.getBirthDay());
		document.setDocumentNumber(newIdentity.getDocNumber());
		document.setDocumentSeries(newIdentity.getDocSeries());
		document.setDocumentIssueDate(newIdentity.getDocIssueDate());
		document.setDocumentIssueBy(newIdentity.getDocIssueBy());
		PERSON_SERVICE.update(person); //обновляет и персону и документ
		return person;
	}
}
