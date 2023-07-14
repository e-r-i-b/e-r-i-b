package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.security.PermissionUtil;

/**
 * ¬алидатор проверки доступности сервиса клиенту.
 *
 * @author bogdanov
 * @ created 03.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class CheckImpliesServiceValidator extends FieldValidatorBase
{
	private static final String SERVICE_NAME_PARAMETER = "serviceName"; 
	private String serviceName;

	public void setParameter(String name, String value)
	{
		if ( name.equalsIgnoreCase(SERVICE_NAME_PARAMETER) )
		{
			serviceName = value;
		}
	}

    public String getParameter(String name)
    {
	    if ( name.equalsIgnoreCase(SERVICE_NAME_PARAMETER) )
	    {
		    return serviceName;
	    }

        return null;
    }

	public boolean validate(String value) throws TemporalDocumentException
	{
		return PermissionUtil.impliesService(getParameter(SERVICE_NAME_PARAMETER));
	}
}
