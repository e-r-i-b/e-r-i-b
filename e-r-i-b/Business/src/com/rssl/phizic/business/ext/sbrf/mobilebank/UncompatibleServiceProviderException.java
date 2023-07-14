package com.rssl.phizic.business.ext.sbrf.mobilebank;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;

/**
 * @author Erkin
 * @ created 02.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class UncompatibleServiceProviderException extends BusinessException
{
    private BillingServiceProvider provider;

	public UncompatibleServiceProviderException(String message)
	{
		super(message);
	}

    public UncompatibleServiceProviderException(String message, BillingServiceProvider provider)
    {
        super(message);
        this.provider = provider;
    }

    public String getMessage()
    {
        StringBuilder sb = new StringBuilder(super.getMessage());
        if (provider != null)
            sb.append(" ID поставщика: ").append(provider.getId()).append(".");
        return sb.toString();
    }

    public String getShortMessage()
    {
        return super.getMessage();
    }
}
