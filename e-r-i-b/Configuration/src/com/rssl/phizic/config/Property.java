package com.rssl.phizic.config;

/**
 * @author Roshka
 * @ created 06.02.2006
 * @ $Author: gladishev $
 * @ $Revision: 45776 $
 * @noinspection UNUSED_SYMBOL
 */
public class Property
{
    private Long id;

    private String key;
    private String value;
	private String category;

	public Property() {}

	public Property(String key, String value, String category)
	{
		this.key = key;
		this.value = value;
		this.category = category;
	}

	public Long getId()
    {
        return id;
    }

    private void setId(Long id)
    {
        this.id = id;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}
}
