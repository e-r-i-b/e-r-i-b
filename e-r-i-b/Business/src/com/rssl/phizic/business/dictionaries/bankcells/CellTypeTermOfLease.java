package com.rssl.phizic.business.dictionaries.bankcells;

/**
 * @author Kidyaev
 * @ created 16.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class CellTypeTermOfLease
{
	private Long        id;
	private CellType    cellType;
	private TermOfLease termOfLease;


	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public CellType getCellType()
	{
		return cellType;
	}

	public void setCellType(CellType cellType)
	{
		this.cellType = cellType;
	}

	public TermOfLease getTermOfLease()
	{
		return termOfLease;
	}

	public void setTermOfLease(TermOfLease termOfLease)
	{
		this.termOfLease = termOfLease;
	}

	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CellTypeTermOfLease that = (CellTypeTermOfLease) o;

		if (cellType != null ? !cellType.equals(that.cellType) : that.cellType != null) return false;
		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (termOfLease != null ? !termOfLease.equals(that.termOfLease) : that.termOfLease != null)
			return false;

		return true;
	}

	public int hashCode()
	{
		int result = (id != null ? id.hashCode() : 0);
		result = 31 * result + (cellType != null ? cellType.hashCode() : 0);
		result = 31 * result + (termOfLease != null ? termOfLease.hashCode() : 0);
		return result;
	}
}
