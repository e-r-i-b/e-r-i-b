package com.rssl.phizic.business.dictionaries.bankcells;

import java.util.Set;

/**
 * @author Kidyaev
 * @ created 08.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class CellType
{
	private Long   id;
	private String description;
	private Set<TermOfLease> termsOfLease;

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

	public Set<TermOfLease> getTermsOfLease()
	{
		return termsOfLease;
	}

	public void setTermsOfLease(Set<TermOfLease> termsOfLease)
	{
		this.termsOfLease = termsOfLease;
	}

	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CellType cellType = (CellType) o;

		if (description != null ? !description.equals(cellType.description) : cellType.description != null)
			return false;
		if (id != null ? !id.equals(cellType.id) : cellType.id != null) return false;

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
