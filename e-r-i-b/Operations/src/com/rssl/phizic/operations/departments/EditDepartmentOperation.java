package com.rssl.phizic.operations.departments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.departments.DublicateDepartmentException;
import com.rssl.phizic.business.departments.SendSMSPreferredMethod;
import com.rssl.phizic.business.dictionaries.billing.BillingService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.dictionaries.offices.extended.OfficeHelper;
import com.rssl.phizic.business.operations.restrictions.DepartmentRestriction;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.manager.GateManager;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.routing.AdapterService;
import com.rssl.phizicgate.manager.services.IDHelper;
import org.hibernate.Session;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 17.08.2006
 * @ $Author: Omeliyanchuk $
 * @ $Revision: 2299 $
 *
 * �������� �������������� �������������
 */

public class EditDepartmentOperation extends EditDictionaryEntityOperationBase implements EditEntityOperation
{
	private static final SimpleService simpleService = new SimpleService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final AdapterService adapterService = new AdapterService();
	private static final BillingService billingService = new BillingService();

	private static final String DEFAULT_BEGIN_TIME = "09:00:00";
	private static final String DEFAULT_END_TIME = "17:00:00";

	private ExtendedDepartment department;
	private ExtendedDepartment parent;
	private Comparable oldOfficeKey;

	private List<Department> departments;

	/**
	 * ������������� �������� �������������� �������������
	 * @param departmentId ������������� �������������� �������������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long departmentId) throws BusinessException, BusinessLogicException
	{
		checkRestriction((DepartmentRestriction) getRestriction(), departmentId);
		department = (ExtendedDepartment) departmentService.findById(departmentId,getInstanceName());
		parent = (ExtendedDepartment) departmentService.getParent(department,getInstanceName());
		oldOfficeKey = department.getSynchKey();
	}

	/**
	 * ������������� �������� �������� �������������
	 * @param parentId ������������� ������������� �������������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initializeNew(Long parentId) throws BusinessException, BusinessLogicException
	{
		if (parentId != null)
		{
			checkRestriction((DepartmentRestriction) getRestriction(), parentId);
			parent = (ExtendedDepartment) departmentService.findById(parentId, getInstanceName());
		}

		department = getNewDepartment();

		if(parent != null)
			updateDepartmentByParent(department, parent);
	}

	/**
	 * ������� �����������
	 * @param restriction �����������
	 * @param parentDepartmentId id ������������� �������������
	 * @throws com.rssl.phizic.business.operations.restrictions.RestrictionViolationException ���� ������������ �� �������� �����������
	 */
	public void checkRestriction(DepartmentRestriction restriction, Long parentDepartmentId) throws RestrictionViolationException, BusinessException
	{
		if (!restriction.accept(parentDepartmentId))
			throw new RestrictionViolationException(" ������������� ID= " + parentDepartmentId);
	}

	private ExtendedDepartment getNewDepartment()
	{
		ExtendedDepartment newDepartment = new ExtendedDepartment();
		newDepartment.buildCode(Collections.<String,Object>emptyMap());
		newDepartment.setWeekOperationTimeBegin(Time.valueOf(DEFAULT_BEGIN_TIME));
		newDepartment.setWeekOperationTimeEnd(Time.valueOf(DEFAULT_END_TIME));
		newDepartment.setFridayOperationTimeBegin(Time.valueOf(DEFAULT_BEGIN_TIME));
		newDepartment.setFridayOperationTimeEnd(Time.valueOf(DEFAULT_END_TIME));
		if (parent == null)
			newDepartment.setSendSMSPreferredMethod(SendSMSPreferredMethod.MOBILE_BANK_ONLY);
		// ��������� ��������� ����  ������.
		newDepartment.setTimeZone(1);
		return newDepartment;
	}

	private void updateDepartmentByParent(ExtendedDepartment department, ExtendedDepartment parentDepartment)
	{
		department.setCode(parentDepartment.getCode());
		department.setAdapterUUID(parentDepartment.getAdapterUUID());
		department.setWeekOperationTimeBegin(parentDepartment.getWeekOperationTimeBegin());
		department.setWeekOperationTimeEnd(parentDepartment.getWeekOperationTimeEnd());
		department.setFridayOperationTimeBegin(parentDepartment.getFridayOperationTimeBegin());
		department.setFridayOperationTimeEnd(parentDepartment.getFridayOperationTimeEnd());
		department.setWeekendOperationTimeBegin(parentDepartment.getWeekendOperationTimeBegin());
		department.setWeekendOperationTimeEnd(parentDepartment.getWeekendOperationTimeEnd());
		department.setConnectionCharge(parentDepartment.getConnectionCharge());
		department.setMonthlyCharge(parentDepartment.getMonthlyCharge());
		department.setTimeZone(parentDepartment.getTimeZone());
		department.setEsbSupported(parentDepartment.isEsbSupported());
	}

	/**
	 * @return ������� ������� �������������
	 * @throws BusinessException
	 */
	public Adapter getAdapter() throws BusinessException
	{
		String adapterUUID = department.getAdapterUUID();
		if (StringHelper.isEmpty(adapterUUID))
			return null;

		try
		{
			return adapterService.getAdapterByUUID(adapterUUID);
		}
		catch (GateException e)
		{
			throw new BusinessException("������ ��������� �������� " + adapterUUID, e);
		}
	}

	/**
	 * @return ����������� ������� �������������
	 * @throws BusinessException
	 */
	public Billing getBilling() throws BusinessException
	{
		Long billingId = department.getBillingId();
		if (billingId == null)
			return null;

		return billingService.getById(billingId);
	}

	public Department getEntity()
	{
		return department;
	}

	/**
	 * @return ������������ �������������
	 */
	public Department getParent()
	{
		return parent;
	}

	/**
	 * �������� ������������� �����
	 */
	public void updateOfficeId() throws BusinessException
	{
		if (isExternalSystemOffice())
			return;

		String officeId = OfficeHelper.buildSynchKey(department.getRegion(), department.getOSB(), department.getVSP());
		String adapterUUID = department.getAdapterUUID();

		if(StringHelper.isNotEmpty(adapterUUID))
			officeId = IDHelper.storeRouteInfo(officeId, adapterUUID);

		department.setSynchKey(officeId);
	}

	/**
	 * ��� ��������� ����� ����������, ����� ��� ������ ������� ������� ������������ ���� ����
	 * �������� �������������.
	 * @param externalSystemUUID - ���� ������� �������
	 * @return ����� �������� ������������� ��� ������ ������� �������
	 */
	public int getMainDepartmentsCount(String externalSystemUUID) throws BusinessException
	{
		return departmentService.getMainDepartmentsCount(externalSystemUUID, getInstanceName());
	}

	/**
	 * @return true - ������������ ��������� ��������� ������ �� ������� �������
	 */
	public boolean isExternalSystemOffice() throws BusinessException
	{
		return OfficeHelper.isAdapterSupportExternalOffice(department.getAdapterUUID());
	}

	private OfficeGateService getOfficeGateService() throws BusinessException, BusinessLogicException
	{
		try
		{
			//��� ��������� uuid �������� ������ �������� �� ���������� ������� �������
			String uuid = ExternalSystemHelper.getCode(department.getAdapterUUID());
			return GateManager.getInstance().getService(uuid, OfficeGateService.class);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return ��� ����� (�� ������� �������)
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public String getOfficeName() throws BusinessException, BusinessLogicException
	{
		try
		{
			return getOfficeGateService().getOfficeById((String) department.getSynchKey()).getName();
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� ������������� ��������� �� �����.
	 */
	private void updateDepartmentData() throws BusinessException, BusinessLogicException
	{
		try
		{
			Office office = getOfficeGateService().getOfficeById((String) department.getSynchKey());
			department.setBIC(office.getBIC());
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	protected void doSave() throws BusinessException, BusinessLogicException
	{
		if(!departmentService.isExistsBranch(getEntity(), getInstanceName()))
		{
			throw new BusinessLogicException("��� � ��������� ������� �� �������� � �������. ����������, �������� ��������� � ����� �� ������� �������� ����������� ���.");
		}

		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Object>()
			{
				public Object run(Session session) throws Exception
				{
					if (department.getId() == null && departmentService.findByCode(department.getCode()) != null)
					{
						throw new DublicateDepartmentException("����� ������������� ��� ���������");
					}

					if (isExternalSystemOffice() && !department.getSynchKey().equals(oldOfficeKey))
					{
						updateDepartmentData();
					}

					String oldSynchKey = oldOfficeKey == null ? null : oldOfficeKey.toString();
					departmentService.addOrUpdate(department,getInstanceName(), oldSynchKey);

					return null;
				}
			});
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException("������ ���������� �������������.", e);
		}
	}


	/**
	 * ������������� �������� ��� ���������� ������� �������� ���
	 * @param departmentSendMethods ����� ������������ <id ������������� : ���������������� ������ �������� ���>
	 */
	public void initializeSendSMSMethods(Map<String, String> departmentSendMethods) throws BusinessException
	{
		List<Long> departmenIds = new ArrayList<Long>(departmentSendMethods.size());
		for (String departmentId : departmentSendMethods.keySet())
		{
			departmenIds.add(Long.valueOf(departmentId));
		}
		departments = simpleService.findByIds(Department.class, departmenIds);
		for (Department extDepartment : departments)
		{
			SendSMSPreferredMethod sendSMSPreferredMethod = SendSMSPreferredMethod.valueOf(departmentSendMethods.get(extDepartment.getId().toString()));
			((ExtendedDepartment) extDepartment).setSendSMSPreferredMethod(sendSMSPreferredMethod);
		}
	}

	/**
	 * @return ������ �������������
	 */
	public List<Department> getDepartments()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return departments;
	}

	/**
	 * ��������� ��������� ����������������� ������� �������� SMS-���������
	 */
	public void saveSendSMSMethods() throws BusinessException
	{
		simpleService.addOrUpdateList(departments);
	}
}