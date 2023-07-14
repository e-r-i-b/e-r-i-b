package com.rssl.phizic.test.web.mobile.fund;

import com.rssl.phizic.test.web.mobile.TestMobileForm;

/**
 * @author vagin
 * @ created 12.12.14
 * @ $Author$
 * @ $Revision$
 * Тестовая форма создания запроса на сбор средств.
 */
public class TestMobileCreateFundForm extends TestMobileForm
{
	private String message;
	private String closeDate;
	private String requiredSum;
	private String reccomendSum;
	private String resource;
	private String phones;


	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getCloseDate()
	{
		return closeDate;
	}

	public void setCloseDate(String closeDate)
	{
		this.closeDate = closeDate;
	}

	public String getRequiredSum()
	{
		return requiredSum;
	}

	public void setRequiredSum(String requiredSum)
	{
		this.requiredSum = requiredSum;
	}

	public String getReccomendSum()
	{
		return reccomendSum;
	}

	public void setReccomendSum(String reccomendSum)
	{
		this.reccomendSum = reccomendSum;
	}

	public String getResource()
	{
		return resource;
	}

	public void setResource(String resource)
	{
		this.resource = resource;
	}

	public String getPhones()
	{
		return phones;
	}

	public void setPhones(String phones)
	{
		this.phones = phones;
	}
}
