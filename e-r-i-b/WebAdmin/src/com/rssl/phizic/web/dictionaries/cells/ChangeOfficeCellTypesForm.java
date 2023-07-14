package com.rssl.phizic.web.dictionaries.cells;

import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.business.dictionaries.bankcells.CellType;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.*;

/**
 * @author Kidyaev
 * @ created 08.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class ChangeOfficeCellTypesForm extends EditFormBase
{
	private List<Department>         offices   = new ArrayList<Department>();
	private List<CellType>       cellTypes = new ArrayList<CellType>();
	private Map<String,String[]> cells    = new HashMap<String, String[]>();


	public List<Department> getOffices()
	{
		return Collections.unmodifiableList(offices);
	}

	public void setOffices(List<Department> offices)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.offices = offices;
	}

	public List<CellType> getCellTypes()
	{
		return Collections.unmodifiableList(cellTypes);
	}

	public void setCellTypes(List<CellType> cellTypes)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.cellTypes = cellTypes;
	}

	public String[] getCells(String key)
	{
	    return cells.get(key);
	}

	public void setCells(String key, String[] selectedIds)
	{
	    cells.put(key, selectedIds);
	}
}
