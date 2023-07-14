package com.rssl.phizic.business.persons;

/**
 * @author Balovtsev
 * @since  15.09.14.
 */
public class UserServiceInfo
{
	private Long   id;
	private Long   loginId;
	private String data;

	public UserServiceInfo()
	{
	}

	public UserServiceInfo(Long id, Long loginId)
	{
		this.id      = id;
		this.loginId = loginId;
	}

	public UserServiceInfo(Long id, Long loginId, String data)
	{
		this.id      = id;
		this.data    = data;
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

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}
}
