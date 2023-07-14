package com.rssl.phizicgate.sofia.messaging;

import com.rssl.phizgate.common.messaging.StringMessageData;

/**
 * @author gladishev
 * @ created 07.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class SofiaMessageData extends StringMessageData
{
	public SofiaMessageData(String data)
	{
		setBody(data);
	}
}
