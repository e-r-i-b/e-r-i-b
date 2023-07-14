package com.rssl.phizic.business.incognitoDictionary;

/**
 * Справочник "инкогнито"
 *
 * @author EgorovaA
 * @ created 24.10.13
 * @ $Author$
 * @ $Revision$
 */
public class IncognitoPhone
{
	private Long id;
	private String phoneNumber;
	private Long loginId;

	/**
	 * ctor
	 */
	public IncognitoPhone()
	{
	}

	/**
	 * ctor
	 * @param phoneNumber номер телефона
	 */
	public IncognitoPhone(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}
}
