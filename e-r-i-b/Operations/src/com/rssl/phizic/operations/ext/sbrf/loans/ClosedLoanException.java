package com.rssl.phizic.operations.ext.sbrf.loans;

import com.rssl.phizic.business.BusinessException;

/**
 * Исключение, выбрасываемое при попытке получения детальной информации или кредитного графика по закрытому кредиту.
 * Используется в мобильном API.
 * @author Dorzhinov
 * @ created 15.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class ClosedLoanException extends BusinessException
{
    public ClosedLoanException(String message)
    {
        super(message);
    }

    public ClosedLoanException(Throwable cause)
    {
        super(cause);
    }

    public ClosedLoanException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
