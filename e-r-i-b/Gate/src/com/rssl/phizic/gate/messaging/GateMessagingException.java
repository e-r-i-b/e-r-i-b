package com.rssl.phizic.gate.messaging;

import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Неопознанная ошибка
 * @author Evgrafov
 * @ created 28.08.2006
 * @ $Author: gladishev $
 * @ $Revision: 58629 $
 */

public class GateMessagingException extends GateException
{
    private String errCode;
	private String errDesc;
	
	public GateMessagingException(String message, String code, String desc)
    {
        super(message);
	    errCode = code;
	    errDesc = desc;
    }

	public GateMessagingException(String message)
    {
        super(message);
    }

	public GateMessagingException(Exception e, String code, String desc)
	{
		super(e);
		errCode = code;
	    errDesc = desc;
	}

	public String getErrCode()
	{
		return errCode;
	}

	public String getErrDesc()
	{
		return errDesc;
	}
}