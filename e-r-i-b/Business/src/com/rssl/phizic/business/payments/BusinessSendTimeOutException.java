package com.rssl.phizic.business.payments;

/**
 * Ислючение, возникающее при таймауте при отправке документа(send())
 * @author vagin
 * @ created 26.03.14
 * @ $Author$
 * @ $Revision$
 */
public class BusinessSendTimeOutException extends BusinessTimeOutException
{
	public BusinessSendTimeOutException(Throwable cause)
    {
        super(cause);
    }
}
