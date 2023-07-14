package com.rssl.phizicgate.cpfl.messaging;

import com.rssl.phizgate.common.messaging.StringMessageData;

/**
 * @author krenev
 * @ created 15.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class CPFLMessageData extends StringMessageData
{
	public CPFLMessageData(String data)
	{
		setBody(data);
	}
}
