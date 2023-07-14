package com.rssl.phizic.web.common.dictionaries;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.dictionaries.ReceiverState;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author Kidyaev
 * @ created 30.11.2005
 * @ $Author$
 * @ $Revision$
 */
public class EditPaymentReceiverForm extends EditFormBase
{
	private String       kind   = "";
	private Long         person;
    private ActivePerson activePerson;
	private ReceiverState state;

	private Boolean    modified=false;

	public String getKind ()
	{
		return kind;
	}

	public void setKind ( String kind )
	{
		this.kind = kind;
	}

	public Long getPerson ()
	{
		return person;
	}

	public void setPerson ( Long person )
	{
		this.person = person;
	}

    public ActivePerson getActivePerson ()
	{
		return activePerson;
	}

	public void setActivePerson ( ActivePerson activePerson )
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

	public void setState(ReceiverState state)
	{
		this.state = state;
	}
	
	public ReceiverState getState()
	{
		return state;
	}

}
