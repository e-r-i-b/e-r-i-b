package com.rssl.phizicgate.asbc.messaging;

import com.rssl.phizgate.common.messaging.ByteArrayMessageData;

/**
 * @author Krenev
 * @ created 02.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class ASBCMessageData extends ByteArrayMessageData
{
//кому надо, добавляейте свое состояние

	public ASBCMessageData(byte[] bytes)
	{
		setBody(bytes);
	}
}
