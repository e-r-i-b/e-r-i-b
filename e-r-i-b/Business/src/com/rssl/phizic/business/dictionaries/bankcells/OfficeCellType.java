package com.rssl.phizic.business.dictionaries.bankcells;

import com.rssl.phizic.business.departments.Department;

/**
 * Связь офиса и типа типа ячейки
 * @author Kidyaev
 * @ created 08.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class OfficeCellType
{
	private Long     id;
	private Department department;
	private CellType cellType;
	private boolean  presence = false;

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

	public boolean getPresence()
	{
		return presence;
	}

	public void setPresence(boolean presence)
	{
		this.presence = presence;
	}

	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}
}
