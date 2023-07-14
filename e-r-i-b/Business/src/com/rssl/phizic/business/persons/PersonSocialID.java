package com.rssl.phizic.business.persons;

/**
 * Логин клиента в социальной сети
 * @author Pankin
 * @ created 12.05.14
 * @ $Author$
 * @ $Revision$
 */
public class PersonSocialID
{
	private Long id;
	private String socialNetworkType; // Тип соц. сети (OK, VK, FB)
	private String socialNetworkId; // Логин клиента в соц. сети
	private Long loginId; // Идентификатор логина клиента

	public PersonSocialID()
	{
	}

	public PersonSocialID(String socialNetworkType, String socialNetworkId, Long loginId)
	{
		this.socialNetworkType = socialNetworkType;
		this.socialNetworkId = socialNetworkId;
		this.loginId = loginId;
	}

	public String getSocialNetworkType()
	{
		return socialNetworkType;
	}

	public void setSocialNetworkType(String socialNetworkType)
	{
		this.socialNetworkType = socialNetworkType;
	}

	public String getSocialNetworkId()
	{
		return socialNetworkId;
	}

	public void setSocialNetworkId(String socialNetworkId)
	{
		this.socialNetworkId = socialNetworkId;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
}
