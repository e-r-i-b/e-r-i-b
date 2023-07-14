package com.rssl.phizic.web.departments;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.ReplicationDepartmentsMode;
import com.rssl.phizic.web.common.background.CreateBackgroundTaskFormBase;
import org.apache.struts.upload.FormFile;

import java.util.List;

/**
 * Форма создания фоновой задачи репликации подразделений
 * @author niculichev
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class ReplicateDepartmentsForm extends CreateBackgroundTaskFormBase
{
	private String[] selectedIds = new String[]{}; // массив выбранных на форме подразделений
	private String ids; // пришедшие выбранные подразделения в виде строки

	private String replicationMode = ReplicationDepartmentsMode.report.name();
	private List<Department> selectedDepartments;
	private FormFile file;
	private String type;  //тип репликации: все подразделения(all) или только выбранные(selected)

	public String[] getSelectedIds()
	{
		return selectedIds;
	}

	public void setSelectedIds(String[] selectedIds)
	{
		this.selectedIds = selectedIds;
	}

	public FormFile getFile()
	{
		return file;
	}

	public void setFile(FormFile file)
	{
		this.file = file;
	}

	public boolean isBackground()
	{
		// для подразделений только фоновый режим
		return true;
	}

	/**
	 * @return режим репликации
	 */
	public String getReplicationMode()
	{
		return replicationMode;
	}

	/**
	 * установить режим репликации
	 * @param replicationMode режим репликации
	 */
	public void setReplicationMode(String replicationMode)
	{
		this.replicationMode = replicationMode;
	}

	public List<Department> getSelectedDepartments()
	{
		return selectedDepartments;
	}

	public void setSelectedDepartments(List<Department> selectedDepartments)
	{
		this.selectedDepartments = selectedDepartments;
	}

	public String getIds()
	{
		return ids;
	}

	public void setIds(String ids)
	{
		this.ids = ids;
	}

	/**
	 * Тип репликации
	 * @return all/selected
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Установить тип репликации: все подразделения или только выбранные
	 * @param type all/selected
	 */
	public void setType(String type)
	{
		this.type = type;
	}
}
