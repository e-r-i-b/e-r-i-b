package com.rssl.phizic.userSettings;

/**
 * ���������������� ���������.
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
	 * @return �� ���������.
	 */
	public Long getId()
    {
        return id;
    }

	/**
	 * @param id �� ���������.
	 */
    private void setId(Long id)
    {
        this.id = id;
    }

	/**
	 * @return ���� ���������.
	 */
    public String getKey()
    {
        return key;
    }

	/**
	 * @param key ���� ���������.
	 */
    public void setKey(String key)
    {
        this.key = key;
    }

	/**
	 * @return �������� ���������.
	 */
    public String getValue()
    {
        return value;
    }

	/**
	 * @param value �������� ���������.
	 */
    public void setValue(String value)
    {
        this.value = value;
    }

	/**
	 * @param loginId ������������� ������ ������������.
	 */
	public void setLoginId(long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return ������������� ������ ������������.
	 */
	public long getLoginId()
	{
		return loginId;
	}
}
