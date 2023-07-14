package com.rssl.phizic.business.dictionaries.synchronization.processors.departments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.billing.MultiInstanceBillingService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.dictionaries.synchronization.SkipEntitySynchronizationException;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider.BillingProcessor;

/**
 * @author mihaylov
 * @ created 02.02.14
 * @ $Author$
 * @ $Revision$
 */
public class DepartmentProcessor extends ProcessorBase<ExtendedDepartment>
{
	public static final String MULTI_BLOCK_RECORD_ID_FIELD_NAME = "synchKey";

	private static final DepartmentService departmentService = new DepartmentService();
	private static final MultiInstanceBillingService billingService = new MultiInstanceBillingService();

	@Override
	protected Class<ExtendedDepartment> getEntityClass()
	{
		return ExtendedDepartment.class;
	}

	@Override
	protected ExtendedDepartment getNewEntity()
	{
		return new ExtendedDepartment();
	}

	@Override
	protected ExtendedDepartment getEntity(String uuid) throws BusinessException
	{
		return (ExtendedDepartment)departmentService.findBySynchKey(uuid);
	}

	@Override
	protected void update(ExtendedDepartment source, ExtendedDepartment destination) throws BusinessException
	{
		destination.setSynchKey(source.getSynchKey());
		destination.setCode(source.getCode());

		destination.setName(source.getName());
		destination.setService(source.isService());
		destination.setCity(source.getCity());
		destination.setAddress(source.getAddress());
		destination.setLocation(source.getLocation());
		destination.setTelephone(source.getTelephone());

		destination.setWeekOperationTimeBegin(source.getWeekOperationTimeBegin());
		destination.setWeekOperationTimeEnd(source.getWeekOperationTimeEnd());

		destination.setWeekendOperationTimeBegin(source.getWeekendOperationTimeBegin());
		destination.setWeekendOperationTimeEnd(source.getWeekendOperationTimeEnd());

		destination.setFridayOperationTimeBegin(source.getFridayOperationTimeBegin());
		destination.setFridayOperationTimeEnd(source.getFridayOperationTimeEnd());

		destination.setTimeScale(source.getTimeScale());
		destination.setTimeZone(source.getTimeZone());
		destination.setNotifyContractCancelation(source.getNotifyContractCancelation());

		destination.setConnectionCharge(source.getConnectionCharge());
		destination.setMonthlyCharge(source.getMonthlyCharge());
		destination.setReconnectionCharge(source.getReconnectionCharge());

		destination.setBIC(source.getBIC());
		destination.setAdapterUUID(source.getAdapterUUID());
		destination.setBillingId(getDestinationBillingId(source.getBillingId()));

		destination.setCreditCardOffice(source.isCreditCardOffice());
		destination.setOpenIMAOffice(source.isOpenIMAOffice());

		destination.setSbidnt(source.getSbidnt());
		destination.setEsbSupported(source.isEsbSupported());

		destination.setSendSMSPreferredMethod(source.getSendSMSPreferredMethod());
		destination.setAutomationType(source.getAutomationType());

		destination.setPossibleLoansOperation(source.isPossibleLoansOperation());
		destination.setActive(source.isActive());
	}

	private Long getDestinationBillingId(Long sourceBillingId) throws BusinessException
	{
		if(sourceBillingId == null)
			return null;
		Billing sourceBilling = billingService.getById(sourceBillingId,CSA_ADMIN_DB_INSTANCE_NAME);
		if(sourceBilling == null)
			throw new SkipEntitySynchronizationException("Биллинг с идентификатором " + sourceBillingId +" не найден в БД " + CSA_ADMIN_DB_INSTANCE_NAME);
		Billing destinationBilling = getLocalVersionByGlobal(sourceBilling, BillingProcessor.MULTI_BLOCK_RECORD_ID_FIELD_NAME);
		return destinationBilling.getId();
	}

	@Override
	protected void doRemove(ExtendedDepartment localEntity) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException("Удаление подразделения не поддерживается.");
	}
}
