package com.rssl.phizic.operations.departments.templates;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.restrictions.DepartmentRestriction;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.operations.config.EditPropertiesOperation;

import java.util.Set;

/**
 * Настройка использования кратности суммы при оплате по шаблону в full-версии mAPI
 * @author Dorzhinov
 * @ created 15.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class EditUseTemplateFactorInFullMAPIOperation extends EditPropertiesOperation<DepartmentRestriction>
{
	public void initialize(PropertyCategory category, Set<String> propertyKeys, Long departmentId) throws BusinessException, BusinessLogicException
	{
		if (!getRestriction().accept(departmentId))
			throw new RestrictionViolationException(String.format("Подразделение: id = %s не является доступным для текущего пользователя", departmentId));

		super.initialize(category, propertyKeys);
	}
}
