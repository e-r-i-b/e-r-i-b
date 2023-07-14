package com.rssl.phizic.operations.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.limits.LimitState;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;

/**
 * @author niculichev
 * @ created 27.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmLimitOperation extends LimitOperationBase
{
	private static final String NOT_ACCESS_DEPARTMENT_ERROR_MESSAGE = "ѕодразделение: %s не €вл€етс€ доступным дл€ текущего пользовател€";
	private static final String NOT_FOUND_LIMIT_ERROR_MESSAGE       = " умул€тивный лимит с id: %d не найден";
	private static final String INVALID_LIMIT_ID_ERROR_MESSAGE      = "Ќе задан идентификатор лимита дл€ подтверждени€";
	private static final String NOT_DRAFT_LIMIT_STATE_ERROR_MESSAGE = "Ћимит находитс€ не в статусе \"„ерновик\"";
	private static final DepartmentService departmentService = new DepartmentService();

	public void initialize(Long id, ChannelType channelType) throws BusinessException
	{
		if(id == null)
			throw new BusinessException(INVALID_LIMIT_ID_ERROR_MESSAGE);

		Limit tempLimit = limitService.findById(id, getInstanceName());

		if(tempLimit == null)
			throw new BusinessException(String.format(NOT_FOUND_LIMIT_ERROR_MESSAGE, id));

		if (!getRestriction().accept(tempLimit.getTb()))
			throw new RestrictionViolationException(String.format(NOT_ACCESS_DEPARTMENT_ERROR_MESSAGE, departmentService.getDepartment(limit.getTb(), null, null, getInstanceName()).getName() ));

		// подтверждаем только лимиты в статусе черновик
		if(tempLimit.getState() != LimitState.DRAFT)
			throw new BusinessException(NOT_DRAFT_LIMIT_STATE_ERROR_MESSAGE);

		if (channelType != tempLimit.getChannelType())
			throw new BusinessException("»нициализируемый лимит id = " + id + " несоответствует передаваемому каналу channelType = " + channelType.name());

		this.limit = tempLimit;
	}

	protected boolean isNewLimit()
	{
		return false;
	}
}
