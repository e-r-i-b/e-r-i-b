package com.rssl.phizic.business.persons.cancelation;

import com.rssl.phizic.person.Person;
import com.rssl.phizic.gate.clients.CancelationCallBack;

/**
 * @author Omeliyanchuk
 * @ created 31.07.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * Объект для связи персоны с его коллбеком для разсторжения договора
 * см. RegistartionClientService
 */
public class CancelationCallBackLink
{
	Long id;
	Person person;
	String callBackId;

	public CancelationCallBackLink()
	{

	}

	public CancelationCallBackLink(Person person, CancelationCallBack callback)
	{
		this.person = person;
		this.callBackId = callback.getId();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Person getPerson()
	{
		return person;
	}

	public void setPerson(Person person)
	{
		this.person = person;
	}

	public String getCallBackId()
	{
		return callBackId;
	}

	public void setCallBackId(String callBackId)
	{
		this.callBackId = callBackId;
	}

	public void setCallBack(CancelationCallBack callback)
	{
		this.callBackId = callback.getId();
	}
}
