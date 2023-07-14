package com.rssl.phizic.business.dictionaries.bankcells;

/**
 * —рок аренды сейфовой €чейки
 * @author Kidyaev
 * @ created 13.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class TermOfLease
{
	private Long   id;
	private String description;
	private Long sortOrder;

	public Long getSortOrder()
	{
		return sortOrder;
	}

	public void setSortOrder(Long sortOrder)
	{
		this.sortOrder = sortOrder;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TermOfLease that = (TermOfLease) o;

		if (description != null ? !description.equals(that.description) : that.description != null)
			return false;
		if (id != null ? !id.equals(that.id) : that.id != null) return false;

		return true;
	}

	public int hashCode()
	{
		int result;
		result = (id != null ? id.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}
}
