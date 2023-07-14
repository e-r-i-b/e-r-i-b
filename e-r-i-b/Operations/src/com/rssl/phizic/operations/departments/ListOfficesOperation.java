package com.rssl.phizic.operations.departments;

import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 * Опреация получения списка офисов.
 *
 * @author bogdanov
 * @ created 20.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class ListOfficesOperation extends OperationBase implements ListEntitiesOperation
{
   	protected List<Office> offices;
	protected static final DepartmentService departmentService = new DepartmentService();


	public List<Office> getOffices()
	{
		return offices;
	}

	public void setOffices(List<Office> offices)
	{
		this.offices = offices;
	}
}
