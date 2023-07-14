package com.rssl.phizic.web.ermb.migration.list.report;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.ermb.migration.list.Segment;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * @author Gulov
 * @ created 06.12.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Форма выгрузки отчета для клиентских менеджеров
 */
public class ClientManagerForm extends ListFormBase
{
	private final Set<Segment> segments = EnumSet.allOf(Segment.class);
	private String[] selectedSegments = new String[]{};
	private List<Department> departments;
	private String[] selectedDepartments = new String[]{};

	public Set<Segment> getSegments()
	{
		return segments;
	}

	public String[] getSelectedSegments()
	{
		return selectedSegments;
	}

	public void setSelectedSegments(String[] selectedSegments)
	{
		this.selectedSegments = selectedSegments;
	}

	public void setDepartments(List<Department> departments)
	{
		this.departments = departments;
	}

	public List<Department> getDepartments()
	{
		return departments;
	}

	public String[] getSelectedDepartments()
	{
		return selectedDepartments;
	}

	public void setSelectedDepartments(String[] selectedDepartments)
	{
		this.selectedDepartments = selectedDepartments;
	}
}
