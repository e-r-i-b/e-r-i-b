package com.rssl.phizic.business.dictionaries.offices.extended.replication;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.tree.DepartmentEntity;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.tree.DepartmentPath;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.tree.DepartmentsTree;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.ReplicaDestination;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ExceptionUtil;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.*;

/**
 * @author niculichev
 * @ created 09.09.13
 * @ $Author$
 * @ $Revision$
 */
public class DepartmentReplicaDestination implements ReplicaDestination<ExtendedDepartment>
{
	private static final String ADD_ERROR_MESSAGE = "Не удалось сохранить подразделение с тб = %s осб = %s всп = %s : %s";
	private static final String UPDATE_ERROR_MESSAGE = "Не удалось обновить подразделение с тб = %s осб = %s всп = %s : %s";

	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	private static final DepartmentService departmentService = new DepartmentService();

	private Map<Code, String> errors = new HashMap<Code, String>();

	private Session session;
	private DepartmentsTree departmentsTree;
	private ReplicationDepartmentsTaskResult taskResult;

	public DepartmentReplicaDestination(List<Long> ids, ReplicationDepartmentsTaskResult taskResult) throws BusinessException
	{
		this.taskResult = taskResult;
		departmentsTree = new DepartmentsTree();

		List<ExtendedDepartment> departments = departmentService.getTreeDepartments(ids);
		if (CollectionUtils.isEmpty(departments))
			return;

		for (ExtendedDepartment department : departments)
		{
			departmentsTree.addNode(
					DepartmentsTree.createPath(department.getCode()),
					DepartmentsTree.createEntity(department));
		}
	}

	public void add(ExtendedDepartment newValue) throws GateException
	{
		try
		{
			if (session != null)
			{
				departmentService.addOrUpdate(newValue);
				session.flush();
			}
		}
		catch (ConstraintViolationException e)
		{
			log.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			throw new GateException(
					taskResult.getReport() + Constants.DELIMITER + ExceptionUtil.getStackTraceMessages(e, Constants.DELIMITER), e);
		}
		finally
		{
			if (session != null)
				session.clear();
		}
	}

	public void update(ExtendedDepartment oldValue, ExtendedDepartment newValue) throws GateException
	{
		try
		{
			if (session != null)
			{
				departmentService.addOrUpdate(oldValue);
				session.flush();
			}
		}
		catch (ConstraintViolationException e)
		{
			log.error(e.getMessage(), e);
			throw e;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			throw new GateException(
					taskResult.getReport() + Constants.DELIMITER + ExceptionUtil.getStackTraceMessages(e, Constants.DELIMITER), e);
		}
		finally
		{
			if (session != null)
				session.clear();
		}
	}

	public void remove(ExtendedDepartment oldValue) throws GateException
	{
		// удаление записей подразделений из таблицы "DEPARTMENTS"
		// производится при необходимости Сотрудником банка с испльзованием средств БД
		throw new UnsupportedOperationException("В данном случае не используется.");
	}

	public Iterator<ExtendedDepartment> iterator() throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("В данном случае не используется.");
	}

	public Map<Code, String> getMapErrors()
	{
		return errors;
	}

	public List<String> getErrors()
	{
		return new ArrayList<String>(errors.values());
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public void close()
	{
	}

	/**
	 * Обновление сущности подразеделния
	 * @param oldValueEntity старая сущность
	 * @param newValue новая сущность
	 * @param newPath путь для новой сущности
	 * @param parentEntity непосредственный родитель новой сущности
	 * @throws GateException
	 */
	public void update(DepartmentEntity oldValueEntity, ExtendedDepartment newValue, DepartmentPath newPath, DepartmentEntity parentEntity) throws GateException
	{
		ExtendedDepartment oldValue = oldValueEntity.getDepartment();

		if (oldValue.updateFrom(newValue))
		{
			DepartmentPath oldPath = DepartmentsTree.createPath(oldValue.getCode());
			boolean isNewPath = !oldPath.equals(newPath);
			// если путь изменился, обновляем родителя
			if (isNewPath && parentEntity != null)
			{
				// обновляем родителя
				setParentInfo(oldValueEntity, parentEntity, false);
			}

			try
			{
				oldValue.setActive(true);
				// обновлеяем в базе
				update(oldValue, newValue);
				// обновляем дерево в рантайме
				if (isNewPath)
					departmentsTree.moveNode(oldPath, newPath);
				// добавляем в отчет;
				taskResult.addToDestinationUpdatedReport(oldValue.getCode(), oldValue.getName());
			}
			catch (ConstraintViolationException e)
			{
				ExtendedCodeImpl extendedCode = new ExtendedCodeImpl(newValue.getCode());
				String reportMessage = e.getSQLException() != null ? e.getSQLException().getMessage() : e.getMessage();

				taskResult.addToReport(String.format(UPDATE_ERROR_MESSAGE, extendedCode.getRegion(), extendedCode.getBranch(), extendedCode.getOffice(), reportMessage));
			}
		}
		else
		{
			// Подразделение должно быть активным. Обновляем, если требуется.
			if (!oldValue.isActive())
			{
				oldValue.setActive(true);
				update(oldValue, newValue);
			}
		}
	}

	/**
	 * Добавить сущность подразделения
	 * @param entity сущность
	 * @param path путь для добавления
	 * @param parent непосредственный родитель
	 * @throws GateException
	 */
	public void add(DepartmentEntity entity, DepartmentPath path, DepartmentEntity parent) throws GateException
	{
		ExtendedDepartment department = entity.getDepartment();
		// устанавливаем родителя в соответствии с иерархией
		if (parent != null)
		{
			setParentInfo(entity, parent, true);
		}

		try
		{
			department.setActive(true);
			// добавляем в базу
			add(department);
			// добавляем в дерево, которое в рантайме
			departmentsTree.addNode(path, entity);
			// добавляем в отчет
			taskResult.addToDestinationInseredReport(department.getCode(), department.getName());
		}
		catch (ConstraintViolationException e)
		{
			ExtendedCodeImpl extendedCode = new ExtendedCodeImpl(department.getCode());
			String reportMessage = e.getSQLException() != null ? e.getSQLException().getMessage() : e.getMessage();

			taskResult.addToReport(String.format(ADD_ERROR_MESSAGE, extendedCode.getRegion(), extendedCode.getBranch(), extendedCode.getOffice(), reportMessage));
		}
	}

	private void setParentInfo(DepartmentEntity entity, DepartmentEntity parent, boolean isEdded) throws GateException
	{
		ExtendedDepartment department = entity.getDepartment();

		if (isEdded)
		{
			department.storeRouteInfo(entity.getAdapterUUID());

			ExtendedDepartment parentDepartment = parent.getDepartment();

			department.setWeekOperationTimeEnd(parentDepartment.getWeekOperationTimeEnd());         // Операционное время в рабочие дни (завершение)
			department.setWeekOperationTimeBegin(parentDepartment.getWeekOperationTimeBegin());     // Операционное время в рабочие дни (начало)
			department.setFridayOperationTimeBegin(parentDepartment.getFridayOperationTimeBegin()); // Операционное время в предвыходные и предпраздничные дни  (начало)
			department.setFridayOperationTimeEnd(parentDepartment.getFridayOperationTimeEnd());     // Операционное время в предвыходные и предпраздничные дни  (завершение)
			department.setConnectionCharge(parentDepartment.getConnectionCharge());                 // Тариф за организацию расчетного обслуживания
			department.setMonthlyCharge(parentDepartment.getMonthlyCharge());                       // Ежемесячная плата
			department.setAdapterUUID(parentDepartment.getAdapterUUID());                           // адаптер

			//Кроме этого должны быть установлены настройки: часовой пояс и время приема документов от головного подразделения, операционное время в рабочие дни и тарифы  - "Использовать настройку вышестоящего".
			//Для всех новых (добавленых) при репликации подразделений должна быть установлена галка "Подключено к Сбербанк Онлайн" (и ОСБ, и ВСП).
			department.setService(true);
			//Кроме этого должны быть установлены настройки: часовой пояс от головного подразделения. Договорились от родительского подразделения.
			department.setTimeZone(parentDepartment.getTimeZone());
			//Проставляем родительские настройки поддрежки БП и МДМ.
			department.setEsbSupported(parentDepartment.isEsbSupported());

			department.setMonthlyCharge(parentDepartment.getMonthlyCharge());
			department.setConnectionCharge(parentDepartment.getConnectionCharge());

			department.setWeekOperationTimeBegin(parentDepartment.getWeekOperationTimeBegin());
			department.setWeekOperationTimeEnd(parentDepartment.getWeekOperationTimeEnd());
			department.setFridayOperationTimeBegin(parentDepartment.getFridayOperationTimeBegin());
			department.setFridayOperationTimeEnd(parentDepartment.getFridayOperationTimeEnd());
			department.setWeekendOperationTimeBegin(parentDepartment.getWeekendOperationTimeBegin());
			department.setFridayOperationTimeEnd(parentDepartment.getWeekendOperationTimeEnd());
		}
	}

	/**
	 * Установить значение признака активности для подразделения и сохранить в БД, если значение признака не совпадает с сохраненным
	 * @param departmentEntities - коллекция подразделений
	 * @param paramValue - значения признака активности
	 * @throws GateException
	 */
	public void setDepartmentActive(Collection<DepartmentEntity> departmentEntities, boolean paramValue) throws GateException
	{
		for (DepartmentEntity entity : departmentEntities)
		{
			ExtendedDepartment department = entity.getDepartment();
			if (department.isActive() != paramValue)
			{
				department.setActive(paramValue);
				update(department, null);
			}
		}
	}

	public DepartmentsTree getDepartmentsTree()
	{
		return departmentsTree;
	}

	public void addErrors(Map<Code, String> codes)
	{
		this.errors.putAll(codes);
	}

	public void addError(Code code, String message)
	{
		this.errors.put(code, message);
	}

	public void setSession(Session session)
	{
		this.session = session;
	}
}
