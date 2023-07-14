package com.rssl.phizic.business.dictionaries.offices.extended.replication.tree;

import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;

/**
 * @author niculichev
 * @ created 11.09.13
 * @ $Author$
 * @ $Revision$
 */
public class DepartmentEntity
{
	// �������������
	private ExtendedDepartment department;
	// ��� ��������
	private String adapterUUID;
	// ��������� �� ��������� � ��
	private State state;

	DepartmentEntity(ExtendedDepartment department, State state)
	{
		this.department = department;
		this.adapterUUID = department.getAdapterUUID();
		this.state = state;
	}

	DepartmentEntity(ExtendedDepartment department, String adapterUUID, State state)
	{
		this.department = department;
		this.adapterUUID = adapterUUID;
		this.state = state;
	}

	DepartmentEntity(ExtendedDepartment department, String adapterUUID)
	{
		this(department, adapterUUID, State.TRANSIENT);
	}

	public ExtendedDepartment getDepartment()
	{
		return department;
	}

	public String getAdapterUUID()
	{
		return adapterUUID;
	}

	public State getState()
	{
		return state;
	}

	/**
	 * @return true - �������� ������� � ��
	 */
	public boolean isExisted()
	{
		return state == State.PERSISTENT;
	}

	public static enum State
	{
		/**
		 * �������� ���� � ��
		 */
		PERSISTENT,

		/**
		 * �������� ��� � ��
		 */
		TRANSIENT
	}
}
