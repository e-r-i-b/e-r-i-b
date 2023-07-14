package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;

/**
 * Created by IntelliJ IDEA.
 * User: Omeliyanchuk
 * Date: 12.10.2006
 * Time: 8:34:07
 * To change this template use File | Settings | File Templates.
 */
public class PaymentReceiverJEmptyListException extends BusinessException
{
	public PaymentReceiverJEmptyListException(String message)
    {
        super(message);
    }
}
