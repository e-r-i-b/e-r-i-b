package com.rssl.phizicgate.wsgate.services.documents.types;

import com.rssl.phizic.gate.documents.StateUpdateInfo;
import com.rssl.phizic.common.types.documents.State;

import java.util.Calendar;

/**
 * @author egorova
 * @ created 04.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class StateUpdateInfoImpl implements StateUpdateInfo
{
	private State state;
	private Calendar nextProcessDate;

	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}

	public Calendar getNextProcessDate()
	{
		return nextProcessDate;
	}

	public void setNextProcessDate(Calendar nextProcessDate)
	{
		this.nextProcessDate = nextProcessDate;
	}
}
