package com.rssl.phizic.business.dictionaries.offices.extended.replication;

import com.rssl.phizic.business.dictionaries.offices.extended.DepartmentAutoType;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.sources.DepartmentSubbranchReplicaSource;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.tree.DepartmentEntity;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.tree.DepartmentPath;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.tree.DepartmentsTree;
import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.dictionaries.Replicator;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * @author niculichev
 * @ created 09.09.13
 * @ $Author$
 * @ $Revision$
 */
public class DepartmentsTreeReplicator implements Replicator
{
	private static final String NOT_FOUND_PARENT = "���������� ����� �������� ��� ������ � �� = %s ��� = %s ��� = %s";
	private static final String NOT_FOUND_ROOT_ENTITY = "�� ������� �������� ��������� �������� � ������ ��� tb = %s osb = %s vsp = %s.";
	private static final String ERROR_DEFINE_REPLICA_TYPE = "�� ������� ���������� ����� ����������, �� : ��� ������������� - %s, ��� ���� = %s.";

	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private ReplicaSource<ExtendedDepartment> source;
	private DepartmentReplicaDestination destination;
	private ReplicationDepartmentsTaskResult taskResult;
	private DepartmentsTree departmentsTree;

	private List<DepartmentPath> foundedDepartmentsPathList = new ArrayList<DepartmentPath>();
	private List<DepartmentEntity> activeDepartmentsList = new ArrayList<DepartmentEntity>();

	public DepartmentsTreeReplicator(DepartmentSubbranchReplicaSource source, DepartmentReplicaDestination destination, ReplicationDepartmentsTaskResult taskResult)
	{
		this.source = source;
		this.destination = destination;
		this.departmentsTree = destination.getDepartmentsTree();
		this.taskResult = taskResult;
		// ����� ����� ������ ��� ��������, �.�. ����������� ������
		destination.addErrors(source.getErrors());
	}

	public void replicate() throws GateException, GateLogicException
	{
		Iterator<ExtendedDepartment> iterator = source.iterator();

		while (iterator.hasNext())
		{
			ExtendedDepartment sourceDepartment = iterator.next();

			DepartmentPath destinationPath = departmentsTree.getPath(sourceDepartment.getSynchKey());
			// ������ ��������� ���� ����
			if (destinationPath == null)
				destinationPath = DepartmentsTree.createPath(sourceDepartment.getCode());

			foundedDepartmentsPathList.add(destinationPath);

			doReplicate(sourceDepartment, destinationPath);
		}
		updateActivityParam();
	}

	/**
	 * �������� ������� ���������� �������������.
	 * 1. ����� ������������� ���������� �� ����� ���������� (���������� ��� ���������� ������������� � �� ����).
	 * 2. ���������� �������� ��� �������� �� �������������, ������ � ������� ���� � �� ���� � ������� � �����������, ���� ���� ��� �� �����������.
	 * 3. � ����� �������� ��� ���������� �� �������������, ������ � ������� ���� � �� ����, �� ������� �� ���� ������� � ����������� �����������.
	 * @throws GateException
	 */
	private void updateActivityParam() throws GateException
	{
		// 2. �������������, ������ � ������� ���� � �� ���� � ������� � �����������, �� �� ����������.
		destination.setDepartmentActive(activeDepartmentsList, true);

		// 3. ������� �� ������ ������������� ��, ��� ���� ������� � �����������. � �������� ���������� ��� ����������.
		departmentsTree.removeEntities(foundedDepartmentsPathList);
		Map<DepartmentPath, DepartmentEntity> departmentEntitiesMap = departmentsTree.getEntityList();
		if (!departmentEntitiesMap.isEmpty())
			destination.setDepartmentActive(departmentEntitiesMap.values(), false);
	}

	/**
	 * ������ ���� �� �������������, ������� ���� � ���� � � �����������, �� ������� �� ����� �����������. � ������� ���������� ���������� ���� ��� �����.
	 * @param destinationEntity - ��������, ������� ��������� ��������, ���� ������� ���������� == false
	 */
	private void addDepartmentToSetActive(DepartmentEntity destinationEntity)
	{
		if (destinationEntity == null)
			return;

		if (!destinationEntity.getDepartment().isActive())
			activeDepartmentsList.add(destinationEntity);
	}

	private void doReplicate(ExtendedDepartment sourceDepartment, DepartmentPath destinationPath) throws GateException
	{
		DepartmentEntity destinationEntity = departmentsTree.getNode(destinationPath);
		DepartmentPath sourcePath = DepartmentsTree.createPath(sourceDepartment.getCode());
		ExtendedCodeImpl sourceCode = new ExtendedCodeImpl(sourceDepartment.getCode());

		// ������� �������� �������
		DepartmentEntity rootEntity = getRootEntity(sourcePath, destinationEntity);
		if (!checkRoot(destinationPath, destinationEntity, rootEntity))
		{
			addDepartmentToSetActive(destinationEntity);
			return;
		}

		// ������� ������������ �������
		DepartmentEntity parentEntity = getParentEntity(sourcePath);
		if (!checkParent(sourcePath.getParentPath(), parentEntity, sourceDepartment, destinationEntity))
		{
			addDepartmentToSetActive(destinationEntity);
			return;
		}

		// ���������� ����� ����������
		ReplicationExecutorMode mode = getReplicationMode(destinationEntity, parentEntity, rootEntity);
		if (mode == null)
		{
			addDepartmentToSetActive(destinationEntity);
			return;
		}

		switch (mode)
		{
			// ����� ��� ��������������������� ����
			case DECENTR_COD:
			{
				if (destinationEntity == null)
				{
					// ��� ���������� ��� � ������������ ��� ����� ������ �� �����
					if (!(parentEntity.isExisted() && StringHelper.isNotEmpty(sourceCode.getOffice())))
						taskResult.addToDestinationInseredDecentrReport(sourceDepartment.getCode(), sourceDepartment.getName());

					DepartmentEntity resEntity = DepartmentsTree.createNewEntity(sourceDepartment, parentEntity.getAdapterUUID());
					destination.add(resEntity, sourcePath, parentEntity);
				}
				else
					destination.update(destinationEntity, sourceDepartment, sourcePath, parentEntity);

				break;
			}

			// ����� ��� ������������������� ����
			case CENTR_COD:
			{
				if (destinationEntity == null)
				{
					DepartmentEntity resEntity = DepartmentsTree.createNewEntity(sourceDepartment, rootEntity.getAdapterUUID());
					destination.add(resEntity, sourcePath, parentEntity);
				}
				else
					destination.update(destinationEntity, sourceDepartment, sourcePath, parentEntity);

				break;
			}
		}
	}

	private ReplicationExecutorMode getReplicationMode(DepartmentEntity entity, DepartmentEntity parentEntity, DepartmentEntity rootEntity) throws GateException
	{
		DepartmentAutoType autoType = rootEntity.getDepartment().getAutomationType();

		// �� ����� ���������� ����� ����������
		if (autoType == null)
			return null;

		return autoType == DepartmentAutoType.centralized ?
				ReplicationExecutorMode.CENTR_COD : ReplicationExecutorMode.DECENTR_COD;
	}

	private DepartmentEntity getParentEntity(DepartmentPath sourcePath)
	{
		// ���������� ����������������� �������� ��� ��������
		DepartmentPath parentPath = sourcePath.getParentPath();
		// �������� ������
		if (parentPath == null)
			return null;

		return departmentsTree.getNode(parentPath);
	}

	private boolean checkParent(DepartmentPath parentPath, DepartmentEntity parentEntity, ExtendedDepartment sourceDepartment, DepartmentEntity destinationEntity)
	{
		if (parentEntity != null)
			return true;

		// ���� �������� ������� � �������� ���������
		if (parentPath == null && destinationEntity == null)
		{
			log.error("����������� �� ���������� ��");
			return false;
		}

		// ���� ��������� ��
		if (parentPath == null && destinationEntity != null)
			return true;

		Code parentCode = parentPath.getCode();

		// ���� �������� � �������, ��������� � ������� �������� ��� � �������
		if (destination.getMapErrors().containsKey(parentCode))
		{
			taskResult.addToErrorParentReport(sourceDepartment.getCode(), sourceDepartment.getName());
		}
		else
		{
			ExtendedCodeImpl extendedCode = new ExtendedCodeImpl(sourceDepartment.getCode());
			taskResult.addToReport(String.format(NOT_FOUND_PARENT, extendedCode.getRegion(), extendedCode.getBranch(), extendedCode.getOffice()) + Constants.DELIMITER);
		}

		return false;
	}

	private DepartmentEntity getRootEntity(DepartmentPath path, DepartmentEntity entity)
	{
		// ���������� ������ ������ ��� ������������� ��������
		DepartmentPath rootPath = path.getRootPath();

		// ������ ��� �������� ������
		if (rootPath == null && entity != null)
			return entity;

		// ���� ��� � �����, � �������� ���
		if (rootPath == null && entity == null)
			return null;

		return departmentsTree.getNode(rootPath);
	}

	private boolean checkRoot(DepartmentPath path, DepartmentEntity entity, DepartmentEntity rootEntity)
	{
		if (rootEntity != null)
			return true;

		log.error(String.format(NOT_FOUND_ROOT_ENTITY, path.getTb(), path.getOsb(), path.getOsb()));
		return false;
	}
}

