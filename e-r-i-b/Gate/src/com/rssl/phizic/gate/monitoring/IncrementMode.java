package com.rssl.phizic.gate.monitoring;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author akrenev
 * @ created 18.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Режим увеличения счетчика
 */
public enum IncrementMode
{
	EMPTY()
	{
		public IncrementMode add(MonitoringGateState state)
		{
			switch (state)
			{
				case DEGRADATION: return DEGRADATION;
				case INACCESSIBLE: return INACCESSIBLE;
			}
			return super.add(state);
		}
	},
	INACCESSIBLE(MonitoringGateState.INACCESSIBLE)
	{
		public IncrementMode add(MonitoringGateState state)
		{
			if(state == MonitoringGateState.DEGRADATION)
				return BOTH;
			return super.add(state);
		}
	},
	DEGRADATION(MonitoringGateState.DEGRADATION)
	{
		public IncrementMode add(MonitoringGateState state)
		{
			if(state == MonitoringGateState.INACCESSIBLE)
				return BOTH;
			return super.add(state);
		}
	},
	BOTH(MonitoringGateState.INACCESSIBLE, MonitoringGateState.DEGRADATION);

	private MonitoringGateState[] states;

	private IncrementMode(MonitoringGateState... states)
	{
		this.states = states;
	}

	public IncrementMode add(MonitoringGateState state)
	{
		return this;
	}

	public boolean checkForState(MonitoringGateState state)
	{
		return ArrayUtils.contains(states, state);
	}	
}
