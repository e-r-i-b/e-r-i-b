package com.rssl.phizgate.common.payments.documents;

import com.rssl.phizic.gate.documents.StateUpdateInfo;
import com.rssl.phizic.common.types.documents.State;

import java.util.Calendar;

/**
 * @author khudyakov
 * @ created 14.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class StateUpdateInfoImpl implements StateUpdateInfo
{
	private State state;
	private Calendar nextProcessDate;

	public StateUpdateInfoImpl(String code, String description)
	{
		this.state = new State(code, description);
	}

	public StateUpdateInfoImpl(Calendar nextProcessDate)
	{
		this.nextProcessDate = nextProcessDate;
	}

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
