package com.rssl.phizic.business.dictionaries.offices.extended.replication;

import com.rssl.phizic.business.operations.background.BackgroundTaskBase;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ������� ������ ���������� �������������
 * @author niculichev
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class ReplicateDepartmentsBackgroundTask extends BackgroundTaskBase<ReplicationDepartmentsTaskResult>
{
	public static final String OPERATION_NAME = "com.rssl.phizic.operations.ext.sbrf.departments.ReplicateDepartmentsBackgroundOperation";

	private String departmentIdsInternal; //������������� ������������� ��� ����������
	private ReplicationDepartmentsMode replicationMode; // ����� ����������

	public String getOperationClassName()
	{
		return OPERATION_NAME;
	}

	/**
	 * @return ���������� ������������� ��������������� �������������
	 */
	public String getDepartmentIdsInternal()
	{
		return departmentIdsInternal;
	}

	/**
	 * ���������� ���������� ������������� ��������������� �������������
	 * @param departmentIdsInternal �������������� �������������
	 */
	public void setDepartmentIdsInternal(String departmentIdsInternal)
	{
		this.departmentIdsInternal = departmentIdsInternal;
	}

	/**
	 * @return ������������� ��������� ��� ����������
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
	 * @return ����� ����������
	 */
	public ReplicationDepartmentsMode getReplicationMode()
	{
		return replicationMode;
	}

	/**
	 * ���������� ����� ����������
	 * @param replicationMode ����� ����������
	 */
	public void setReplicationMode(ReplicationDepartmentsMode replicationMode)
	{
		this.replicationMode = replicationMode;
	}
}
