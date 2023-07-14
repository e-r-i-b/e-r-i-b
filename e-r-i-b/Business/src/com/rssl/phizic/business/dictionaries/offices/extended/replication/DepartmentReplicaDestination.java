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
	private static final String ADD_ERROR_MESSAGE = "�� ������� ��������� ������������� � �� = %s ��� = %s ��� = %s : %s";
	private static final String UPDATE_ERROR_MESSAGE = "�� ������� �������� ������������� � �� = %s ��� = %s ��� = %s : %s";

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
		// �������� ������� ������������� �� ������� "DEPARTMENTS"
		// ������������ ��� ������������� ����������� ����� � ������������� ������� ��
		throw new UnsupportedOperationException("� ������ ������ �� ������������.");
	}

	public Iterator<ExtendedDepartment> iterator() throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("� ������ ������ �� ������������.");
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
	 * ���������� �������� �������������
	 * @param oldValueEntity ������ ��������
	 * @param newValue ����� ��������
	 * @param newPath ���� ��� ����� ��������
	 * @param parentEntity ���������������� �������� ����� ��������
	 * @throws GateException
	 */
	public void update(DepartmentEntity oldValueEntity, ExtendedDepartment newValue, DepartmentPath newPath, DepartmentEntity parentEntity) throws GateException
	{
		ExtendedDepartment oldValue = oldValueEntity.getDepartment();

		if (oldValue.updateFrom(newValue))
		{
			DepartmentPath oldPath = DepartmentsTree.createPath(oldValue.getCode());
			boolean isNewPath = !oldPath.equals(newPath);
			// ���� ���� ���������, ��������� ��������
			if (isNewPath && parentEntity != null)
			{
				// ��������� ��������
				setParentInfo(oldValueEntity, parentEntity, false);
			}

			try
			{
				oldValue.setActive(true);
				// ���������� � ����
				update(oldValue, newValue);
				// ��������� ������ � ��������
				if (isNewPath)
					departmentsTree.moveNode(oldPath, newPath);
				// ��������� � �����;
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
			// ������������� ������ ���� ��������. ���������, ���� ���������.
			if (!oldValue.isActive())
			{
				oldValue.setActive(true);
				update(oldValue, newValue);
			}
		}
	}

	/**
	 * �������� �������� �������������
	 * @param entity ��������
	 * @param path ���� ��� ����������
	 * @param parent ���������������� ��������
	 * @throws GateException
	 */
	public void add(DepartmentEntity entity, DepartmentPath path, DepartmentEntity parent) throws GateException
	{
		ExtendedDepartment department = entity.getDepartment();
		// ������������� �������� � ������������ � ���������
		if (parent != null)
		{
			setParentInfo(entity, parent, true);
		}

		try
		{
			department.setActive(true);
			// ��������� � ����
			add(department);
			// ��������� � ������, ������� � ��������
			departmentsTree.addNode(path, entity);
			// ��������� � �����
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

			department.setWeekOperationTimeEnd(parentDepartment.getWeekOperationTimeEnd());         // ������������ ����� � ������� ��� (����������)
			department.setWeekOperationTimeBegin(parentDepartment.getWeekOperationTimeBegin());     // ������������ ����� � ������� ��� (������)
			department.setFridayOperationTimeBegin(parentDepartment.getFridayOperationTimeBegin()); // ������������ ����� � ������������ � ��������������� ���  (������)
			department.setFridayOperationTimeEnd(parentDepartment.getFridayOperationTimeEnd());     // ������������ ����� � ������������ � ��������������� ���  (����������)
			department.setConnectionCharge(parentDepartment.getConnectionCharge());                 // ����� �� ����������� ���������� ������������
			department.setMonthlyCharge(parentDepartment.getMonthlyCharge());                       // ����������� �����
			department.setAdapterUUID(parentDepartment.getAdapterUUID());                           // �������

			//����� ����� ������ ���� ����������� ���������: ������� ���� � ����� ������ ���������� �� ��������� �������������, ������������ ����� � ������� ��� � ������  - "������������ ��������� ������������".
			//��� ���� ����� (����������) ��� ���������� ������������� ������ ���� ����������� ����� "���������� � �������� ������" (� ���, � ���).
			department.setService(true);
			//����� ����� ������ ���� ����������� ���������: ������� ���� �� ��������� �������������. ������������ �� ������������� �������������.
			department.setTimeZone(parentDepartment.getTimeZone());
			//����������� ������������ ��������� ��������� �� � ���.
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
	 * ���������� �������� �������� ���������� ��� ������������� � ��������� � ��, ���� �������� �������� �� ��������� � �����������
	 * @param departmentEntities - ��������� �������������
	 * @param paramValue - �������� �������� ����������
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
