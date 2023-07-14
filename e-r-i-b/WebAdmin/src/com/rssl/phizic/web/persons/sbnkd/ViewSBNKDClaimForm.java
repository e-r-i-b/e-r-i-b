package com.rssl.phizic.web.persons.sbnkd;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * просмотр заявки СБНКД
 * @author basharin
 * @ created 19.02.15
 * @ $Author$
 * @ $Revision$
 */

public class ViewSBNKDClaimForm extends EditFormBase
{
	private ActivePerson activePerson;
	private Boolean modified = false;
	private Long person;
	private IssueCardDocumentImpl claim;

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public Boolean getModified()
	{
		return modified;
	}

	public void setModified(Boolean modified)
	{
		this.modified = modified;
	}

	public Long getPerson()
	{
		return person;
	}

	public void setPerson(Long person)
	{
		this.person = person;
	}

	public IssueCardDocumentImpl getClaim()
	{
		return claim;
	}

	public void setClaim(IssueCardDocumentImpl claim)
	{
		this.claim = claim;
	}
}
