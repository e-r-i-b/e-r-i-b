package com.rssl.phizic.web.client.depo;

import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.web.client.ShowAccountsForm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lukina
 * @ created 11.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowDepoAccountsListForm  extends ShowAccountsForm
{
	private boolean registrationError ;
	private ConfirmableObject confirmableObject;
    private boolean anotherStrategyAvailable;
	private ConfirmStrategyType confirmStrategyType;
	private ConfirmStrategy confirmStrategy;
	private Map<String,Object> fields = new HashMap<String, Object>();

	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
	}

	public ConfirmStrategyType getConfirmStrategyType()
	{
		return confirmStrategyType;
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{
		this.confirmStrategyType = confirmStrategyType;
	}

	public ConfirmableObject getConfirmableObject()
	{
		return confirmableObject;
	}

	public void setConfirmableObject(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	public boolean isRegistrationError()
	{
		return registrationError;
	}

	public void setRegistrationError(boolean registrationError)
	{
		this.registrationError = registrationError;
	}
	public boolean isAnotherStrategyAvailable()
	{
		return anotherStrategyAvailable;
	}

	public void setAnotherStrategyAvailable(boolean anotherStrategyAvailable)
	{
		this.anotherStrategyAvailable = anotherStrategyAvailable;
	}
	
	public Map<String, Object> getFields()
	{
		return fields;
	}

	public void setFields(Map<String, Object> fields)
	{
		this.fields = fields;
	}

	 public Object getField(String key)
    {
        return fields.get(key);
    }

    public void setField(String key, Object obj)
    {
        fields.put(key, obj);
    }
}
