package com.rssl.phizic.business.payments;

import com.rssl.common.forms.state.StateObject;

/**
 * @author gladishev
 * @ created 26.04.2011
 * @ $Author$
 * @ $Revision$
 */
public class IQWaveSystemNameResolver implements DocumentSystemNameResolver
{
	public String getSystemName(StateObject document)
	{
		return "IQWave";
	}
}