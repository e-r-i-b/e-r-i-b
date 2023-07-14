package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.Date;

/**
 * @author Omeliyanchuk
 * @ created 22.08.2006
 * @ $Author$
 * @ $Revision$
 */
public class PrintRecessionForm extends ActionFormBase
{
	private boolean isProlongation = false;
	private ActivePerson activePerson;
	private Date currentDate;
	private Long person;

	public void setIsProlongation(boolean isProlongation)
	{
		this.isProlongation = isProlongation;
	}

	public boolean getIsProlongation()
	{
		return isProlongation;
	}

	public Long getPerson() {
		return person;
	}

	public void setPerson(Long person) {
		this.person = person;
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}
	   public void setCurrentDate(Date currentDate) {
	   this.currentDate = currentDate;
	}

	public Date getCurrentDate (){
		return currentDate;
	}
}
