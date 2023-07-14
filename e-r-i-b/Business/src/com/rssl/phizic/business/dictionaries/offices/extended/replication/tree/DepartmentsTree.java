package com.rssl.phizic.business.dictionaries.offices.extended.replication.tree;

import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.gate.dictionaries.officies.Code;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ��������� ������������� ������ �������������
 * @author niculichev
 * @ created 18.09.13
 * @ $Author$
 * @ $Revision$
 */
public class DepartmentsTree
{
	//�������� ������������� �� �� ����
	private Map<DepartmentPath, DepartmentEntity> entityByPath = new HashMap<DepartmentPath, DepartmentEntity>();
	// ���� �� ������������� �� ��� �������� �����(����� ��� �������)
	private Map<Comparable, DepartmentPath> pathBySynchKey = new HashMap<Comparable, DepartmentPath>();

	/**
	 * �������� ���� �� ���� �������������
	 * @param code ��� �������������
	 * @return ���� � ������ �������������
	 */
	public static DepartmentPath createPath(Code code)
	{
		return new DepartmentPath(code);
	}

	/**
	 * ������� ��� ������ ������������� ����, � �������� ���� �������������� ������ � ��
	 * @param department �������������
	 * @return �������� ����
	 */
	public static DepartmentEntity createEntity(ExtendedDepartment department)
	{
		return new DepartmentEntity(department, DepartmentEntity.State.PERSISTENT);
	}

	/**
	 * ������� ���� ��� ������ �������������
	 * @param department �������������
	 * @param adapterUUID ��� ��������, � �������� �������� �����������
	 * @return �������� ����
	 */
	public static DepartmentEntity createNewEntity(ExtendedDepartment department, String adapterUUID)
	{
		return new DepartmentEntity(department, adapterUUID);
	}

	/**
	 * �������� ���� � ������
	 * @param path ���� � ����
	 * @param entity �������� ����
	 */
	public void addNode(DepartmentPath path, DepartmentEntity entity)
	{
		// ��������� � ������
		entityByPath.put(path, entity);
	}

	/**
	 * ����������� ���� � ������ �����
	 * @param oldPath ������ �����
	 * @param newPath ����� �����
	 */
	public void moveNode(DepartmentPath oldPath, DepartmentPath newPath)
	{
		// �������� ����
		DepartmentEntity entity = entityByPath.remove(oldPath);
		entityByPath.put(newPath, entity);
	}

	/**
	 * ������� �������� �� �����
	 * @param pathList
	 */
	public void removeEntities(List<DepartmentPath> pathList)
	{
		for (DepartmentPath path : pathList)
		{
			entityByPath.remove(path);
		}
	}

	/**
	 * �������� �������� ������������� �� ����
	 * @param path ����
	 * @return �������� �������������
	 */
	public DepartmentEntity getNode(DepartmentPath path)
	{
		return entityByPath.get(path);
	}

	/**
	 * �������� ���� �� ������������� �� �������� �����
	 * @param synchKey ������� ����
	 * @return ���� �� �������������
	 */
	public DepartmentPath getPath(Comparable synchKey)
	{
		return pathBySynchKey.get(synchKey);
	}

	public Map<DepartmentPath, DepartmentEntity> getEntityList()
	{
		return entityByPath;
	}
}
