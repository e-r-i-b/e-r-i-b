package com.rssl.phizic.web.chargeOff;

import com.rssl.phizic.web.common.ListLimitActionForm;
import com.rssl.phizic.business.chargeoff.ChargeOffLog;
import com.rssl.phizic.business.persons.ActivePerson;

import java.util.List;

/**
 * @author egorova
 * @ created 21.01.2009
 * @ $Author$
 * @ $Revision$
 */
public class ChargeOffListForm  extends ListLimitActionForm
{
	private List<ChargeOffLog> chargeOffLogs;
	private ActivePerson activePerson;
	private Boolean    modified=false;

	public Boolean getModified()
	{
		return modified;
	}

	public void setModified(Boolean modified)
	{
		this.modified = modified;
	}
	
	public List<ChargeOffLog> getChargeOffLogs()
	{
		return chargeOffLogs;
	}

	public void setChargeOffLogs(List<ChargeOffLog> chargeOffLogs)
	{
		this.chargeOffLogs = chargeOffLogs;
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}
}
