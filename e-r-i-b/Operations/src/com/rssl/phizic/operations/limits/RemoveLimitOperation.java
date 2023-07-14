package com.rssl.phizic.operations.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.limits.LimitService;
import com.rssl.phizic.business.limits.LimitType;
import com.rssl.phizic.business.limits.Status;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.business.operations.restrictions.TbNumberRestriction;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;

/**
 * @author basharin
 * @ created 09.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class RemoveLimitOperation extends RemoveDictionaryEntityOperationBase<Limit, TbNumberRestriction>
{
	private static final String NOT_ACCESS_DEPARTMENT_ERROR_MESSAGE         = "Подразделение: %s не является доступным для текущего пользователя";
	private static final String CAN_REMOVE_LIMIT_STATUS_ENTERED             = "Вы не можете удалить данный лимит. Можно удалять лимиты со статусом \"Введен\" или \"Черновик\"";
	private static final DepartmentService departmentService = new DepartmentService();

	private static LimitService limitService = new LimitService();

	private Limit limit;

	/**
	 * Уникальный код подразделения
	 */

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
        initialize(id, true);
	}

    public void initialize(Long id, boolean useTbRestriction) throws BusinessException, BusinessLogicException
    {
        limit = limitService.findById(id, getInstanceName());
        if (limit == null)
        {
            throw new ResourceNotFoundBusinessException("Не найдена группа риска с указаным id", Limit.class);
        }

        if (useTbRestriction && !getRestriction().accept(limit.getTb()))
            throw new RestrictionViolationException(String.format(NOT_ACCESS_DEPARTMENT_ERROR_MESSAGE, departmentService.getDepartment(limit.getTb(), null, null, getInstanceName()).getName() ));

        if (limit.getType() != LimitType.OVERALL_AMOUNT_PER_DAY && limit.getStatus() != Status.ENTERED && limit.getStatus() != Status.DRAFT)
            throw new BusinessLogicException(CAN_REMOVE_LIMIT_STATUS_ENTERED);
    }

	public void doRemove() throws BusinessException
	{
		limitService.remove(limit, getInstanceName());
	}

	public Limit getEntity()
	{
		return limit;
	}
}