package com.rssl.phizic.business.persons;

import com.rssl.phizic.auth.modes.UserRegistrationMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.business.resources.external.EditableExternalResourceLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.person.Person;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 07.09.2005
 * Time: 14:15:21
 */
public class ActivePerson extends PersonBase
{
	private String status = TEMPLATE;
	//Мапа хранит шаг раскрытия списков в личном меню (key- id списка, value- шаг)
	private Map<String,String> stepShowPersonalMenuLinkListMap = new HashMap<String,String>();
	// Хранение открытые/скрытые операции
	private Set<String> showOperations =  new HashSet<String>();
	//Доступно ли начать ПФП
	//null  -- не определено
	//true  -- доступно
	//false -- не доступно
	private Boolean availableStartPFP;

	private Long checkLoginCount;           //количество попыток подбора логина.
	private Calendar lastFailureLoginCheck; //время последней неуданочной попытки.
	private UserRegistrationMode userRegistrationMode; //Индивидуальный для клиента режим самостоятельной регистрации

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		if (!(status.equals(Person.ACTIVE)  ||
			  status.equals(Person.TEMPLATE) ||
			  status.equals(Person.ERROE_CANCELATION) ||
			  status.equals(Person.WAIT_CANCELATION) ||
			  status.equals(Person.SIGN_AGREEMENT)))
			throw new IllegalArgumentException("Person can be only: ACTIVE, TEMPLATE, ERROE_CANCELATION, WAIT_CANCELATION, SIGN_AGREEMENT.");
		this.status = status;
	}

	public Map<String, String> getStepShowPersonalMenuLinkListMap()
	{
		return stepShowPersonalMenuLinkListMap;
	}

	public void setStepShowPersonalMenuLinkListMap(Map<String, String> stepShowPersonalMenuLinkListMap)
	{
		this.stepShowPersonalMenuLinkListMap = stepShowPersonalMenuLinkListMap;
	}

	public Set<String> getShowOperations()
	{
		return showOperations;
	}

	public void setShowOperations(Set<String> showOperations)
	{
		this.showOperations = showOperations;
	}

	public EditableExternalResourceLink findShowOperationsById(Long id, Class linkClass)
	{
		Iterator iterator = showOperations.iterator();
        while(iterator.hasNext())
        {
            EditableExternalResourceLink  link = (EditableExternalResourceLink) iterator.next();
	        Class<? extends ExternalResourceLink> resourceLink =  link.getResourceType().getResourceLinkClass();
			if (linkClass.toString().equals(resourceLink.toString()) && link.getId().equals(id))
				return link;
        }
		return null;
	}

	public String getDiscriminator()
	{
		return Person.ACTIVE;
	}

	public ActivePerson()
	{
		super();
	}	

	public ActivePerson(Person person)
	{
		super(person);
		this.status = person.getStatus();
	}

	public ActivePerson copy(Person person)
	{
		return new ActivePerson(person);
	}

	private void addChanges(Map<String, Object> fields, String field2, Map<String, Object> changes, String name, String strName)
	{
		String currentFieldValue = (String)fields.get(name);
		if(!StringHelper.getEmptyIfNull(currentFieldValue).equals(StringHelper.getEmptyIfNull(field2)))
			changes.put(strName, currentFieldValue);
	}

	/**
	 * Определяем были ли сделаны изменения в адресе
	 * @param fields - новые поля, заполненные в форме
	 * @return мап с изменениями <название поля, новое значение поля>, если не было изменений пустой мап
	 */
	public Map<String, Object> getChangedFields(Map<String, Object> fields)
	{
		Map<String, Object> changes = new HashMap<String, Object>();

		addChanges(fields, getEmail(), changes, "email", "E-mail");
		addChanges(fields, getHomePhone(), changes, "homePhone", "Домашний телефон");
		addChanges(fields, getJobPhone(), changes, "jobPhone", "Рабочий телефон");

		addChanges(fields, getSNILS(), changes, "SNILS", "Страховой Номер Индивидуального Лицевого Счёта");

		return changes;
	}

	/**
	 * Атаптировать ActivePerson к Client
	 * @return представление персоны в виде клиента
	 */
	public Client asClient() throws BusinessException
	{
		return new ClientImpl(this);
	}

	public Boolean isAvailableStartPFP()
	{
		return availableStartPFP;
	}

	public void setAvailableStartPFP(Boolean availableStartPFP)
	{
		this.availableStartPFP = availableStartPFP;
	}

	public Long getCheckLoginCount()
	{
		return checkLoginCount;
	}

	public void setCheckLoginCount(Long checkLoginCount)
	{
		this.checkLoginCount = checkLoginCount;
	}

	public Calendar getLastFailureLoginCheck()
	{
		return lastFailureLoginCheck;
	}

	public void setLastFailureLoginCheck(Calendar lastFailureLoginCheck)
	{
		this.lastFailureLoginCheck = lastFailureLoginCheck;
	}

	public UserRegistrationMode getUserRegistrationMode()
	{
		return userRegistrationMode;
	}

	public void setUserRegistrationMode(UserRegistrationMode userRegistrationMode)
	{
		this.userRegistrationMode = userRegistrationMode;
	}
}
