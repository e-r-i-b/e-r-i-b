package com.rssl.phizic.operations.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.limits.*;
import com.rssl.phizic.business.operations.restrictions.DepartmentRestriction;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * @author osminin
 * @ created 30.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditMobileWalletLimitOperation extends EditDictionaryEntityOperationBase<Limit, DepartmentRestriction>
{
	private static final DepartmentService departmentService = new DepartmentService();
	private static final LimitService limitService = new LimitService();

	private Limit limit;

	public void initialize(Long departmentId) throws BusinessException
	{
		if (!getRestriction().accept(departmentId))
			throw new RestrictionViolationException("Подразделение ID=" + departmentId);

		this.limit = findLimit(departmentId);
	}

	public Limit getEntity()
	{
		return limit;
	}

	public void doSave() throws BusinessException
	{
		limitService.addOrUpdate(limit, getInstanceName());
	}

	private Limit findLimit(Long departmentId) throws BusinessException
	{
		Department department = departmentService.findById(departmentId, getInstanceName());
		List<Limit> limits = limitService.findLimits(department, LimitType.USER_POUCH, ChannelType.MOBILE_API, null, Status.ACTIVE, null, getInstanceName());
		if (CollectionUtils.isEmpty(limits))
		{
			return new Limit(department.getRegion(), LimitType.USER_POUCH, ChannelType.MOBILE_API, OperationType.IMPOSSIBLE_PERFORM_OPERATION, RestrictionType.DESCENDING, Calendar.getInstance(), new Money(BigDecimal.ZERO, MoneyUtil.getNationalCurrency()));
		}
		if (limits.size() == 1)
		{
			return limits.get(0);
		}
		throw new BusinessException("Для тербанка departmentId = " + departmentId + " задано больше одного лимита Мобильный кошелек");
	}
}
