package com.rssl.phizicgate.iqwave.messaging;

import com.rssl.phizgate.common.messaging.StringMessageData;

/**
 * @author gladishev
 * @ created 02.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class IQWaveMessageData extends StringMessageData
{
	public IQWaveMessageData (String data)
	{
		setBody(data);
	}
}
