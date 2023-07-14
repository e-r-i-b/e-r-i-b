package com.rssl.phizic.userSettings;

/**
 * Пользовательская настройка.
 *
 * @author bogdanov
 * @ created 10.04.14
 * @ $Author$
 * @ $Revision$
 */

public class UserProperty
{
	private long loginId;
	private Long id;
    private String key;
    private String value;

	/**
	 * @return ид настройки.
	 */
	public Long getId()
    {
        return id;
    }

	/**
	 * @param id ид найстроки.
	 */
    private void setId(Long id)
    {
        this.id = id;
    }

	/**
	 * @return ключ настройки.
	 */
    public String getKey()
    {
        return key;
    }

	/**
	 * @param key ключ настройки.
	 */
    public void setKey(String key)
    {
        this.key = key;
    }

	/**
	 * @return значение настройки.
	 */
    public String getValue()
    {
        return value;
    }

	/**
	 * @param value значение настройки.
	 */
    public void setValue(String value)
    {
        this.value = value;
    }

	/**
	 * @param loginId идентификатор логина пользователя.
	 */
	public void setLoginId(long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return идентификатор логина пользователя.
	 */
	public long getLoginId()
	{
		return loginId;
	}
}
