package com.rssl.phizic.web.dictionaries.cells;

import com.rssl.phizic.business.dictionaries.bankcells.OfficeCellType;
import com.rssl.phizic.web.common.EditFormBase;

import com.rssl.phizic.business.departments.Department;
import org.apache.struts.action.ActionForm;

import java.util.*;

/**
 * @author Kidyaev
 * @ created 08.11.2006
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter", "ReturnOfCollectionOrArrayField"})
public class ChangeCellTypesPresenceForm extends EditFormBase
{
	private Map<Department, List<OfficeCellType>> cellTypesByOffice = new HashMap<Department, List<OfficeCellType>>();
	private String[]                          selectedIds       = new String[0];


	public Map<Department, List<OfficeCellType>> getCellTypesByOffice()
	{
		return Collections.unmodifiableMap(cellTypesByOffice);
	}

	public void setCellTypesByOffice(Map<Department, List<OfficeCellType>> cellTypesByOffice)
	{
		this.cellTypesByOffice = cellTypesByOffice;
	}
	public Set<Map.Entry<Department, List<OfficeCellType>>> getCellTypesByOfficeSet()
	{
		return cellTypesByOffice.entrySet();
	}

	public String[] getSelectedIds()
	{
		return selectedIds;
	}

	public void setSelectedIds(String[] selectedIds)
	{
		this.selectedIds = selectedIds;
	}
}
