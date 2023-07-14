package com.rssl.phizic.operations.mail.archive;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mail.*;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.BooleanUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author komarov
 * @ created 24.10.2011
 * @ $Author$
 * @ $Revision$
 */

public class RecipientUnArchiveValidator
{
	private static final PersonService personService = new PersonService();	

	private Map<String,Object> parameters; // параметры фильтра разархивации писем: дата письма, статус, тема etc.
	private Set<RecipientMailState> mailStates; // статусы письма

	public RecipientUnArchiveValidator(Map<String, Object> parameters)
	{
		this.parameters = parameters;
		mailStates = getStatesFromParameters(parameters);
	}

	public Boolean validate(Mail mail, Recipient recipient) throws BusinessException
	{
		if(!checkMailByState(mail, recipient, parameters, mailStates))
			return false;


		//провер€ем ‘»ќ клиента на соответствие фильтру
		String userFirstName = (String)parameters.get("userFirstName");
		String userSurName = (String)parameters.get("userSurName");
		String userPatrName = (String)parameters.get("userPatrName");
		if(!StringHelper.isEmpty(userFirstName) || !StringHelper.isEmpty(userSurName) || !StringHelper.isEmpty(userPatrName))
		{
			if(mail.getDirection() == MailDirection.CLIENT)
			{
				Person person = personService.findByLoginId(recipient.getRecipientId());
				if(checkPersonByFilter(person,userFirstName,userSurName,userPatrName))
					return true;
			}
		}
		return true;
	}


	//функци€ провер€юща€ на совпадение
	// возвращает true, если matched пусто или содержитс€ в str
	private boolean checkMatches(String str, String matched)
	{
		return StringHelper.isEmpty(matched) || str.indexOf(matched)!=-1;
	}

	private boolean checkPersonByFilter(Person person, String firstName, String surName, String patrName)
	{
		//если не нашли отправител€, то не можем проверить соответствует ли данное письмо фильтру -> из архива не восстанавливаем
		if(person == null)
			return false;
		if(!checkMatches(person.getFirstName(),firstName))
			return false;
		if(!checkMatches(person.getSurName(),surName))
			return false;
		if(!checkMatches(person.getPatrName(),patrName))
			return false;
		return true;
	}

	//проверка статуса письма на соответствие фильтру
	private boolean checkMailByState(Mail mail, Recipient recipient, Map<String,Object> parameters, Set<RecipientMailState> mailStates)
	{
		if(MailDirection.CLIENT == mail.getDirection())

			return ( BooleanUtils.toBoolean((Boolean)parameters.get("NEW_EPLOYEE_MAIL")) &&
				   mail.getState() == MailState.NEW ||
				   BooleanUtils.toBoolean((Boolean)parameters.get("NONE")) &&
				   mail.getState() == MailState.EMPLOYEE_DRAFT
			       ) &&  mail.getParentId() == null ||
				   BooleanUtils.toBoolean((Boolean)parameters.get("ANSWER_EPLOYEE_MAIL")) &&
				   mail.getParentId() != null && mail.getState() == MailState.NEW;

		else if(MailDirection.ADMIN == mail.getDirection())
		{
			if(mailStates.contains(recipient.getState()))
				return true;
		}
		return false;
	}

	private Set<RecipientMailState> getStatesFromParameters(Map<String, Object> parameters)
	{
		Set<RecipientMailState> states = new HashSet<RecipientMailState>();
		if(parameters.get("ANSWER") != null && (Boolean)parameters.get("ANSWER"))
			states.add(RecipientMailState.ANSWER);
		if(parameters.get("READ") != null && (Boolean)parameters.get("READ"))
			states.add(RecipientMailState.READ);
		if(parameters.get("DRAFT") != null && (Boolean)parameters.get("DRAFT"))
			states.add(RecipientMailState.DRAFT);
		return states;
	}
}
