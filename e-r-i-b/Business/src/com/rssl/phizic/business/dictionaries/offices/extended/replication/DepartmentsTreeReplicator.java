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
	private static final String NOT_FOUND_PARENT = "Невозможно найти родителя для записи с тб = %s осб = %s всп = %s";
	private static final String NOT_FOUND_ROOT_ENTITY = "Не найдена сущность корневого элемента в дереве для tb = %s osb = %s vsp = %s.";
	private static final String ERROR_DEFINE_REPLICA_TYPE = "Не удалось определить режим репликации, ТБ : тип автоматизации - %s, тип узла = %s.";

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
		// нужно знать ошибки при парсинге, т.к. реплицируем дерево
		destination.addErrors(source.getErrors());
	}

	public void replicate() throws GateException, GateLogicException
	{
		Iterator<ExtendedDepartment> iterator = source.iterator();

		while (iterator.hasNext())
		{
			ExtendedDepartment sourceDepartment = iterator.next();

			DepartmentPath destinationPath = departmentsTree.getPath(sourceDepartment.getSynchKey());
			// значит формируем путь сами
			if (destinationPath == null)
				destinationPath = DepartmentsTree.createPath(sourceDepartment.getCode());

			foundedDepartmentsPathList.add(destinationPath);

			doReplicate(sourceDepartment, destinationPath);
		}
		updateActivityParam();
	}

	/**
	 * Обновить признак активности подразделений.
	 * 1. Часть подразделений обработана во время репликации (обновление или добавление подразделения в БД ЕРИБ).
	 * 2. Необходимо отметить как активные те подразделения, записи о которых есть в БД ЕРИБ и найдены в справочнике, даже если они не обновляются.
	 * 3. А также отметить как неактивные те подразделения, записи о которых есть в БД ЕРИБ, но которые не были найдены в загружаемом справочнике.
	 * @throws GateException
	 */
	private void updateActivityParam() throws GateException
	{
		// 2. Подразделения, записи о которых есть в БД ЕРИБ и найдены в справочнике, но не обновилась.
		destination.setDepartmentActive(activeDepartmentsList, true);

		// 3. Удалили из списка подразделений те, что были найдены в справочнике. И отметили оставшиеся как неактивные.
		departmentsTree.removeEntities(foundedDepartmentsPathList);
		Map<DepartmentPath, DepartmentEntity> departmentEntitiesMap = departmentsTree.getEntityList();
		if (!departmentEntitiesMap.isEmpty())
			destination.setDepartmentActive(departmentEntitiesMap.values(), false);
	}

	/**
	 * Кладем сюда те подразделения, которые есть в ЕРИБ и в справочнике, но которые не будут обновляться. А признак активности проставить надо все равно.
	 * @param destinationEntity - сущность, которую требуется обновить, если признак активности == false
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

		// находим корневой элемент
		DepartmentEntity rootEntity = getRootEntity(sourcePath, destinationEntity);
		if (!checkRoot(destinationPath, destinationEntity, rootEntity))
		{
			addDepartmentToSetActive(destinationEntity);
			return;
		}

		// находим родительский элемент
		DepartmentEntity parentEntity = getParentEntity(sourcePath);
		if (!checkParent(sourcePath.getParentPath(), parentEntity, sourceDepartment, destinationEntity))
		{
			addDepartmentToSetActive(destinationEntity);
			return;
		}

		// определяем режим репликации
		ReplicationExecutorMode mode = getReplicationMode(destinationEntity, parentEntity, rootEntity);
		if (mode == null)
		{
			addDepartmentToSetActive(destinationEntity);
			return;
		}

		switch (mode)
		{
			// режим для децентрализированного цода
			case DECENTR_COD:
			{
				if (destinationEntity == null)
				{
					// При добавлении ВСП в существующее ОСБ такая запись не нужна
					if (!(parentEntity.isExisted() && StringHelper.isNotEmpty(sourceCode.getOffice())))
						taskResult.addToDestinationInseredDecentrReport(sourceDepartment.getCode(), sourceDepartment.getName());

					DepartmentEntity resEntity = DepartmentsTree.createNewEntity(sourceDepartment, parentEntity.getAdapterUUID());
					destination.add(resEntity, sourcePath, parentEntity);
				}
				else
					destination.update(destinationEntity, sourceDepartment, sourcePath, parentEntity);

				break;
			}

			// режим для централизированного цода
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

		// не можем определить режим репликации
		if (autoType == null)
			return null;

		return autoType == DepartmentAutoType.centralized ?
				ReplicationExecutorMode.CENTR_COD : ReplicationExecutorMode.DECENTR_COD;
	}

	private DepartmentEntity getParentEntity(DepartmentPath sourcePath)
	{
		// определяем непосредственного родителя для сущности
		DepartmentPath parentPath = sourcePath.getParentPath();
		// является корнем
		if (parentPath == null)
			return null;

		return departmentsTree.getNode(parentPath);
	}

	private boolean checkParent(DepartmentPath parentPath, DepartmentEntity parentEntity, ExtendedDepartment sourceDepartment, DepartmentEntity destinationEntity)
	{
		if (parentEntity != null)
			return true;

		// если корневой элемент и пытаемся доблавить
		if (parentPath == null && destinationEntity == null)
		{
			log.error("Ограничение на добавление ТБ");
			return false;
		}

		// если обновляем ТБ
		if (parentPath == null && destinationEntity != null)
			return true;

		Code parentCode = parentPath.getCode();

		// если родидель с ошибкой, добавляем и текущую сущность как с ошибкой
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
		// определяем корень дерева для реплицируемой сущности
		DepartmentPath rootPath = path.getRootPath();

		// значит сам является корнем
		if (rootPath == null && entity != null)
			return entity;

		// путь как у корня, а сущности нет
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

