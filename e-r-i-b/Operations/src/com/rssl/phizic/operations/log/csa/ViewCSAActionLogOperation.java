package com.rssl.phizic.operations.log.csa;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.ProfileNotFoundException;
import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.ListForEmployeeOperation;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.utils.Priority;
import com.rssl.phizic.utils.PriorityComparator;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author vagin
 * @ created 23.10.13
 * @ $Author$
 * @ $Revision$
 * Опреация просмотра списка журнала входов ЦСА.
 */
public class ViewCSAActionLogOperation extends ListForEmployeeOperation implements ListEntitiesOperation
{
	private static final PersonService personService = new PersonService();
	protected static final DepartmentService departmentService = new DepartmentService();

	private ActivePerson person;
	private PersonDocument passport;
	private List<String> clientTB;

	/**
	 * Инициализация операции идентификаторрм клиента
	 * @param id - идентификатор клиента
	 */
	public void initializePerson(Long id) throws BusinessException, BusinessLogicException
	{
		person = (ActivePerson) personService.findById(id);
		passport = resolvePassport();
		if (passport == null)
			throw new BusinessException("У клиента id=" + id + " не найден паспорт WAY. Идентфикация в ЦСА невозможна.");
		Department clientDepartment = departmentService.findById(person.getDepartmentId());
		if(clientDepartment == null)
			throw new BusinessException("У клиента id=" + id + " не найдено подразделение. Идентфикация в ЦСА невозможна.");
		clientTB = ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBWithIdenticalSet(new SBRFOfficeCodeAdapter(clientDepartment.getCode()).getRegion());
		if(clientTB == null)
			throw new BusinessException("Подразделение, в котором обслуживается клиент не подключено к системе.");
	}

	/**
	 * Ищет ЦСА-паспорт среди документов клиента
	 * @return ЦСА-паспорт
	 */
	private PersonDocument resolvePassport() throws BusinessException, BusinessLogicException
	{
		if (CollectionUtils.isEmpty(person.getPersonDocuments()))
			return null;

		// A. Если есть паспорт вей, берём его
		PersonDocument passportWAY = PersonHelper.getPersonDocument(person, PersonDocumentType.PASSPORT_WAY);
		if (passportWAY != null)
			return passportWAY;

		// B. Иначе подбираем документ, который есть в ЦСА
		List<PersonDocument> documents = new ArrayList<PersonDocument>(person.getPersonDocuments());
		// noinspection OverlyComplexAnonymousInnerClass
		ListUtil.sortMaxToMin(documents, new PriorityComparator<PersonDocument>()
		{
			@Override
			protected Priority getPriority(PersonDocument document)
			{
				Priority priority = new Priority();

				if (document.getDocumentType() == PersonDocumentType.REGULAR_PASSPORT_RF)
					priority.p1 = 1;

				if (document.isDocumentIdentify())
					priority.p2 = 1;

				if (document.getDocumentMain())
					priority.p3 = 1;

				return priority;
			}
		});

		String surName = person.getSurName();
		String firstName = person.getFirstName();
		String patrName = person.getPatrName();
		Calendar birthDay = person.getBirthDay();
		String tb = PersonHelper.getPersonTb(person);
		for (PersonDocument document : documents)
		{
			try
			{
				String csaPassport = formatCSAPassport(document);
				CSABackRequestHelper.findProfileInformationRq(surName, firstName, patrName, csaPassport, birthDay, tb);

				// Если исключения нет, значит, документ найден в ЦСА
				return document;
			}
			catch (ProfileNotFoundException ignore)
			{
			}
			catch (BackLogicException e)
			{
				throw new BusinessLogicException(e);
			}
			catch (BackException e)
			{
				throw new BusinessException(e);
			}
		}

		return null;
	}

	public Query createQuery(String name)
	{
		Query query = new BeanQuery(this, ViewCSAActionLogOperation.class.getName() + "." + name, getInstanceName());
		query.setParameter("firstName", person.getFirstName().trim());
		query.setParameter("surName", person.getSurName().trim());
		query.setParameter("patrName", person.getPatrName().trim());
		query.setParameter("birthDate", person.getBirthDay());
		query.setParameter("passport", getPassport());
		query.setParameterList("tb", clientTB);
		return query;
	}

	/**
	 * Пользователь в разрезе коорого запрашивается журнал
	 * @return пользователь.
	 */
	public ActivePerson getPerson()
	{
		return person;
	}

	/**
	 * Номер пасспорта клиента.
	 * @return номер паспорта
	 */
	public String getPassport()
	{
		return formatCSAPassport(passport);
	}

	private String formatCSAPassport(PersonDocument document)
	{
		StringBuilder passportWay = new StringBuilder();
		passportWay.append(StringHelper.getEmptyIfNull(document.getDocumentSeries())).append(StringHelper.getEmptyIfNull(document.getDocumentNumber()));
		return passportWay.toString().replaceAll(" ","");
	}

	protected String getInstanceName()
	{
		return Constants.CSA_DB_LOG_INSTANCE_NAME;
	}
}
