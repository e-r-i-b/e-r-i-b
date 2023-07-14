package com.rssl.phizic.business.dictionaries.regions.exceptions;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author hudyakov
 * @ created 04.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class DublicateRegionException extends BusinessLogicException
{
	public DublicateRegionException(String message)
    {
        super(message);
    }

    public DublicateRegionException(Throwable cause)
    {
        super(cause);
    }

	public DublicateRegionException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
