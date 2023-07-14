package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author osminin
 * @ created 08.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditMobileWalletForm extends EditFormBase
{
	private Long personId;
	private Money totalAmount;
	private ActivePerson activePerson;
	private boolean modified;

	public Long getPerson()
	{
		return personId;
	}

	public void setPerson(Long personId)
	{
		this.personId = personId;
	}

	public Money getTotalAmount()
	{
		return totalAmount;
	}

	public void setTotalAmount(Money totalAmount)
	{
		this.totalAmount = totalAmount;
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public boolean isModified()
	{
		return modified;
	}

	public void setModified(boolean modified)
	{
		this.modified = modified;
	}
}
