package com.rssl.phizic.operations.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.limits.*;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author gulov
 * @ created 23.08.2010
 * @ $Authors$
 * @ $Revision$
 */
public class EditLimitOperation extends LimitOperationBase
{
	private static final String SUPPORT_NATIONAL_CURRENCY_ERROR_MESSAGE     = "ѕоддерживаетс€ только национальна€ валюта";
	private static final String CAN_NOT_BE_EDITED_ARCHIVAL_LIMIT            = "Ќельз€ редактировать лимиты в статусе \"јрхивный\"";
	private static final String NOT_FOUND_LIMIT_ERROR_MESSAGE               = " умул€тивный лимит с id: %d не найден";
	private static final String NOT_FOUND_DEPARTMENT_ERROR_MESSAGE          = "Ќевозможно создать кумул€тивный лимит потому, что подразделение с id: %d не найдено";
	private static final String NOT_MAIN_DEPARTMENT_ERROR_MESSAGE           = "Ќевозможно создать кумул€тивный лимит потому, что подразделение %s не €вл€етс€ “ербанком";
	private static final String NOT_ACCESS_DEPARTMENT_ERROR_MESSAGE         = "ѕодразделение: %s не €вл€етс€ доступным дл€ текущего пользовател€";

	private static final DepartmentService departmentService = new DepartmentService();

	private boolean isNew;

	public void initialize(Long id, ChannelType channelType) throws BusinessException
	{
		initialize(id, channelType, true);
	}

	public void initialize(Long id, ChannelType channelType, boolean useTbRestriction) throws BusinessException
	{
		try
		{
			limit = limitService.findById(id, getInstanceName());
			if (limit == null)
				throw new BusinessException(String.format(NOT_FOUND_LIMIT_ERROR_MESSAGE, id));

			if (useTbRestriction && !getRestriction().accept(limit.getTb()))
				throw new RestrictionViolationException(String.format(NOT_ACCESS_DEPARTMENT_ERROR_MESSAGE, departmentService.getDepartment(limit.getTb(), null, null, getInstanceName()).getName()));

			if (limit.getStatus() == Status.ARCHIVAL)
				throw new BusinessException(CAN_NOT_BE_EDITED_ARCHIVAL_LIMIT);

			if ((limit.getRestrictionType() == RestrictionType.AMOUNT_IN_DAY || limit.getRestrictionType() == RestrictionType.MIN_AMOUNT)
					&& !(limit.getAmount().getCurrency().compare(GateSingleton.getFactory().service(CurrencyService.class).getNationalCurrency())))
				throw new BusinessException(SUPPORT_NATIONAL_CURRENCY_ERROR_MESSAGE);

			if (channelType != limit.getChannelType())
				throw new BusinessException("»нициализируемый лимит id = " + id + " несоответствует передаваемому каналу channelType = " + channelType.name());

			isNew = false;
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	public void initializeNew(Long departmentId, LimitType limitType, ChannelType channelType, SecurityType securityType) throws BusinessException
	{
		Department department = departmentService.findById(departmentId, getInstanceName());

		if (department == null)
		    throw new BusinessException(String.format(NOT_FOUND_DEPARTMENT_ERROR_MESSAGE, departmentId));

		if (!DepartmentService.isTB(department))
			throw new BusinessException(String.format(NOT_MAIN_DEPARTMENT_ERROR_MESSAGE, department.getName()));

		if (!getRestriction().accept(department.getRegion()))
			throw new RestrictionViolationException(String.format(NOT_ACCESS_DEPARTMENT_ERROR_MESSAGE, department.getName()));

		limit = new Limit(department.getRegion(), limitType, channelType, securityType);
		isNew = true;
	}

	public void initializeNew(LimitType limitType, ChannelType channelType) throws BusinessException
	{
		limit = new Limit(limitType, channelType);
		isNew = true;
	}

	protected boolean isNewLimit()
	{
		return isNew;
	}
}
