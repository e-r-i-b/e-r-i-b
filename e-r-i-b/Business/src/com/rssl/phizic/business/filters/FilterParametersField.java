package com.rssl.phizic.business.filters;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 17.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class FilterParametersField
{
	private Long id;
	private long filterId;
	private String parameter;
	private String value;
	private FilterParametersType type;
	
	public FilterParametersType getType()
	{
		return type;
	}

	public void setType(FilterParametersType type)
	{
		this.type = type;
	}


	/**
     * 4hibernate
     */
    @SuppressWarnings({"UNUSED_SYMBOL"})
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	/*public Long getFilterId()
	{
		return filterId;
	}

	public void setFilterId(Long filterId)
	{
		this.filterId = filterId;
	}*/

	public String getParameter()
	{
		return parameter;
	}

	public void setParameter(String parameter)
	{
		this.parameter = parameter;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
