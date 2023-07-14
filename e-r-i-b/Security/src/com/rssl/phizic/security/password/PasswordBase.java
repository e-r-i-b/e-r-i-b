package com.rssl.phizic.security.password;

import com.rssl.phizic.auth.CommonLogin;

import java.util.Date;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 01.09.2005
 * Time: 20:03:36
 */
abstract class PasswordBase implements Password
{
    private Long        id;
    private Long loginId;
    private Calendar  issueDate;
    private Calendar  expireDate;
    private char[]      value;
	private Boolean needChange;

    public abstract void setValid(boolean value);

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

    public char[] getValue()
    {
        return nullSafeClone(value);
    }

    public void setValue(char[] value)
    {
	    this.value = nullSafeClone(value);
    }

	/**
	 * Клонирует массив с учетом null
	 * @param arr
	 * @return clone or null
	 */
	private static char[] nullSafeClone(char[] arr)
	{
		return arr == null ? null : arr.clone();
	}

	/** @noinspection UNUSED_SYMBOL*/
	private void setStringValue(String value)
	{
	    this.value = value == null ? null : value.toCharArray();
	}

    /** @noinspection UNUSED_SYMBOL*/
    protected String getStringValue()
    {
        return value == null ? null : String.valueOf(value);
    }

	public Boolean getNeedChange ()
	{
		return needChange;
	}

	public void setNeedChange ( Boolean needChange )
	{
		this.needChange=needChange;
	}
}
