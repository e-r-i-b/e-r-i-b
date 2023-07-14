package com.rssl.phizic.web.common.client.ext.sbrf.security;

import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.web.common.client.security.ConfirmSmsLoginForm;

import java.util.Map;
import java.util.HashMap;

/**
 * Author: Moshenko
 * Date: 13.04.2010
 * Time: 19:28:27
 */
public class ConfirmWay4PasswordForm extends ConfirmSmsLoginForm
{

	private String ReceiptNo = null;
	private String PasswordNo = null;
	private String PasswordsLeft = null;
	private String SID = null;
	private String password = null;
	private String confirmSelect ;
	private ConfirmRequest confirmRequest;
	private boolean anotherStrategyAvailable;
	private boolean hasCapButton = false;
	private Map<String,Object> fields = new HashMap<String, Object>();
	private ConfirmStrategy confirmStrategy;

	public boolean isHasCapButton()
	{
		return hasCapButton;
	}

	public void setHasCapButton(boolean hasCapButton)
	{
		this.hasCapButton = hasCapButton;
	}

	public String getPassword()
	{
		return password;
	}

	public String getConfirmSelect()
	{
		return confirmSelect;
	}

	public void setConfirmSelect(String confirmSelect)
	{
		this.confirmSelect = confirmSelect;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getSID()
	{
		return SID;
	}

	public void setSID(String SID)
	{
		this.SID = SID;
	}

	public String getReceiptNo()
	{
		return ReceiptNo;
	}

	public void setReceiptNo(String receiptNo)
	{
		ReceiptNo = receiptNo;
	}

	public String getPasswordNo()
	{
		return PasswordNo;
	}

	public void setPasswordNo(String passwordNo)
	{
		PasswordNo = passwordNo;
	}

	public String getPasswordsLeft()
	{
		return PasswordsLeft;
	}

	public void setPasswordsLeft(String passwordsLeft)
	{
		PasswordsLeft = passwordsLeft;
	}

	public ConfirmRequest getConfirmRequest()
	{
		return confirmRequest;
	}

	public void setConfirmRequest(ConfirmRequest confirmRequest)
	{
		this.confirmRequest = confirmRequest;
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

	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
	}
}
