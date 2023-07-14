package com.rssl.phizic.business.dictionaries.synchronization.processors.departments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.MultiInstanceDepartmentService;
import com.rssl.phizic.business.dictionaries.synchronization.SkipEntitySynchronizationException;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.business.receptiontimes.ReceptionTime;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author mihaylov
 * @ created 02.02.14
 * @ $Author$
 * @ $Revision$
 */
public class ReceptionTimeProcessor extends ProcessorBase<ReceptionTime>
{
	private static final String UUID_DELIMITER = "\\^";
	private static final MultiInstanceDepartmentService departmentService = new MultiInstanceDepartmentService();

	@Override
	protected Class<ReceptionTime> getEntityClass()
	{
		return ReceptionTime.class;
	}

	@Override
	protected ReceptionTime getNewEntity()
	{
		return new ReceptionTime();
	}

	@Override
	protected ReceptionTime getEntity(String uuid) throws BusinessException
	{
		String[] parameters = uuid.split(UUID_DELIMITER);
		Long destinationDepartmentId = getDestinationDepartmentId(Long.parseLong(parameters[0]));
		String paymentType = parameters[1];
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass())
				.add(Expression.eq("departmentId", destinationDepartmentId))
				.add(Expression.eq("paymentType", paymentType));
		return simpleService.findSingle(criteria);
	}

	@Override
	protected void update(ReceptionTime source, ReceptionTime destination) throws BusinessException
	{
		destination.setDepartmentId(getDestinationDepartmentId(source.getDepartmentId()));
		destination.setPaymentType(source.getPaymentType());
		destination.setCalendar(getLocalVersionByGlobal(source.getCalendar(), WorkCalendarProcessor.MULTI_BLOCK_RECORD_ID_FIELD_NAME));
		destination.setReceptionTimeStart(source.getReceptionTimeStart());
		destination.setReceptionTimeEnd(source.getReceptionTimeEnd());
		destination.setUseParentSettings(source.isUseParentSettings());
		destination.setPaymentTypeDescription(source.getPaymentTypeDescription());
	}

	private Long getDestinationDepartmentId(Long sourceDepartmentId) throws BusinessException
	{
		if(sourceDepartmentId == null)
			return null;
		Department sourceDepartment = departmentService.findById(sourceDepartmentId,CSA_ADMIN_DB_INSTANCE_NAME);
		if(sourceDepartment == null)
			throw new SkipEntitySynchronizationException("Департамент с идентификатором " + sourceDepartmentId +" не найден в БД " + CSA_ADMIN_DB_INSTANCE_NAME);
		Department destinationDepartment = departmentService.findBySynchKey((String)sourceDepartment.getSynchKey(),null);
		return destinationDepartment.getId();
	}
}
