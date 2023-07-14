package com.rssl.phizic.business.dictionaries.offices.extended.replication;

import com.rssl.phizic.business.operations.background.BackgroundTaskBase;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Фоновая задача репликации подразделений
 * @author niculichev
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class ReplicateDepartmentsBackgroundTask extends BackgroundTaskBase<ReplicationDepartmentsTaskResult>
{
	public static final String OPERATION_NAME = "com.rssl.phizic.operations.ext.sbrf.departments.ReplicateDepartmentsBackgroundOperation";

	private String departmentIdsInternal; //идентфикаторы подразделений для репликации
	private ReplicationDepartmentsMode replicationMode; // режим репликации

	public String getOperationClassName()
	{
		return OPERATION_NAME;
	}

	/**
	 * @return внутреннее представление идентификаторов департаментов
	 */
	public String getDepartmentIdsInternal()
	{
		return departmentIdsInternal;
	}

	/**
	 * Установить внетреннее представление идентификаторов департаментов
	 * @param departmentIdsInternal идентификаторы департаментов
	 */
	public void setDepartmentIdsInternal(String departmentIdsInternal)
	{
		this.departmentIdsInternal = departmentIdsInternal;
	}

	/**
	 * @return идентфикаторы биллингов для репликации
	 */
	public List<Long> getDepartmentIds()
	{
		if (departmentIdsInternal == null)
			return null;

		String[] strings = StringUtils.split(departmentIdsInternal, ';');
		List<Long> result = new ArrayList<Long>(strings.length);
		for (String s : strings)
			result.add(Long.valueOf(s));

		return result;
	}

	/**
	 * @return режим репликации
	 */
	public ReplicationDepartmentsMode getReplicationMode()
	{
		return replicationMode;
	}

	/**
	 * Установить режим репликации
	 * @param replicationMode режим репликации
	 */
	public void setReplicationMode(ReplicationDepartmentsMode replicationMode)
	{
		this.replicationMode = replicationMode;
	}
}
