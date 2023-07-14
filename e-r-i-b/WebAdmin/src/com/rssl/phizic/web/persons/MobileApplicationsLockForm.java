package com.rssl.phizic.web.persons;

import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @ author: Gololobov
 * @ created: 06.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class MobileApplicationsLockForm extends EditFormBase
{
	private Long personId;
	private ActivePerson activePerson;
	private boolean modified;
	//Список подключенных мобильных устройств
	private List<ConnectorInfo> mobileDevices;

	public Long getPerson()
	{
		return personId;
	}

	public void setPerson(Long personId)
	{
		this.personId = personId;
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

	public List<ConnectorInfo> getMobileDevices()
	{
		return mobileDevices;
	}

	public void setMobileDevices(List<ConnectorInfo> mobileDevices)
	{
		this.mobileDevices = mobileDevices;
	}
}
