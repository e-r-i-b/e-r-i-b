package com.rssl.phizic.security.password;

import com.rssl.phizic.auth.Login;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 01.09.2005
 * Time: 21:17:33
 */
public class OneTimePasswordERMB
{
	public static final String STATE_NEW     = "N"; // неиспользованный пароль
	public static final String STATE_BLOCKED = "B"; // заблокированный пароль (кончились попытки ввода)
	public static final String STATE_USED    = "U"; // использованный пароль (была успешная попытка ввода)

	private Long   id;

	private String hash;
	private Calendar issueDate;
	private Calendar expireDate;
	private Long   wrongAttempts;
	private String phone;

	public OneTimePasswordERMB()
	{
	}

	public OneTimePasswordERMB(Calendar issueDate, Calendar expireDate, String hash, String phone) {
		this.expireDate = expireDate;
		this.issueDate = issueDate;
		this.hash    = hash;
		this.wrongAttempts = 0L;
		this.phone = phone;

	}

	public Calendar getIssueDate()
	{
		return issueDate;
	}

	public void setIssueDate(Calendar issueDate)
	{
		this.issueDate = issueDate;
	}

	public Calendar getExpireDate()
	{
		return expireDate;
	}

	public void setExpireDate(Calendar expireDate)
	{
		this.expireDate = expireDate;
	}

	public String getHash()
	{
		return hash;
	}

	public void setHash(String hash)
	{
		this.hash = hash;
	}

	public Long getWrongAttempts()
	{
		return wrongAttempts;
	}

	public void setWrongAttempts(Long wrongAttempts)
	{
		this.wrongAttempts = wrongAttempts;
	}

	public Long getId()
	{
		return id;
	}



	public void setId(Long id)
	{
		this.id = id;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}
}
