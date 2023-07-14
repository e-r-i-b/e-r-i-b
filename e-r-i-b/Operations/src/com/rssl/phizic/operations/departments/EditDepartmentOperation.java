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
 * Операция редактирования подразделения
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
	 * инициализация операции редактирования подразделения
	 * @param departmentId идентификатор редактируемого подразделения
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
	 * инициализация операции создания подразделения
	 * @param parentId идентификатор родительского подразделения
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
	 * Прверка ограничений
	 * @param restriction ограничение
	 * @param parentDepartmentId id родительского подразделения
	 * @throws com.rssl.phizic.business.operations.restrictions.RestrictionViolationException если пользователь не проходит ограничения
	 */
	public void checkRestriction(DepartmentRestriction restriction, Long parentDepartmentId) throws RestrictionViolationException, BusinessException
	{
		if (!restriction.accept(parentDepartmentId))
			throw new RestrictionViolationException(" Подразделение ID= " + parentDepartmentId);
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
		// указываем временной пояс  Москвы.
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
	 * @return внешняя система подразделения
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
			throw new BusinessException("Ошибка получения адаптера " + adapterUUID, e);
		}
	}

	/**
	 * @return биллинговая система подразделения
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
	 * @return родительское подразделение
	 */
	public Department getParent()
	{
		return parent;
	}

	/**
	 * обновить идентификатор офиса
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
	 * для северного банка необходимо, чтобы для каждой внешней системы существовало лишь одно
	 * головное подразделение.
	 * @param externalSystemUUID - ууид внешней системы
	 * @return число головных подразделений для данной внешней системы
	 */
	public int getMainDepartmentsCount(String externalSystemUUID) throws BusinessException
	{
		return departmentService.getMainDepartmentsCount(externalSystemUUID, getInstanceName());
	}

	/**
	 * @return true - поддерживает интерфейс получения офисов из внешней системы
	 */
	public boolean isExternalSystemOffice() throws BusinessException
	{
		return OfficeHelper.isAdapterSupportExternalOffice(department.getAdapterUUID());
	}

	private OfficeGateService getOfficeGateService() throws BusinessException, BusinessLogicException
	{
		try
		{
			//при получении uuid адаптера делаем проверку на активность внешней системы
			String uuid = ExternalSystemHelper.getCode(department.getAdapterUUID());
			return GateManager.getInstance().getService(uuid, OfficeGateService.class);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return имя офиса (из внешней системы)
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
	 * Обновляем подразделение свойсвами из офиса.
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
			throw new BusinessLogicException("ОСБ с указанным номером не заведено в системе. Пожалуйста, создайте отделение и затем Вы сможете добавить подчиненное ВСП.");
		}

		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Object>()
			{
				public Object run(Session session) throws Exception
				{
					if (department.getId() == null && departmentService.findByCode(department.getCode()) != null)
					{
						throw new DublicateDepartmentException("Такое подразделение уже добавлено");
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
			throw new BusinessException("Ошибка сохранения подразделения.", e);
		}
	}


	/**
	 * Инициализация операции для сохранения способа отправки СМС
	 * @param departmentSendMethods карта соответствия <id подразделения : предпочтительный способ отправки СМС>
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
	 * @return список подразделений
	 */
	public List<Department> getDepartments()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return departments;
	}

	/**
	 * Сохранить настройки предпочтительного способа отправки SMS-сообщений
	 */
	public void saveSendSMSMethods() throws BusinessException
	{
		simpleService.addOrUpdateList(departments);
	}
}