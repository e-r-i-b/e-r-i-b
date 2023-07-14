package com.rssl.phizic.operations.payment.templatesconfirmsetting;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.restrictions.DepartmentRestriction;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.operations.config.EditPropertiesOperation;

import java.util.Set;

/**
 * @author vagin
 * @ created 02.10.2012
 * @ $Author$
 * @ $Revision$
 * Операция для настройки подтверждения операций по шаблону СМС-паролем
 */
public class TemplatesConfirmSettingsOperation extends EditPropertiesOperation<DepartmentRestriction>
{
	public void initialize(PropertyCategory category, Set<String> propertyKeys, Long departmentId) throws BusinessException, BusinessLogicException
	{
		if (!getRestriction().accept(departmentId))
			throw new RestrictionViolationException(String.format("Подразделение: id = %s не является доступным для текущего пользователя", departmentId));

		super.initialize(category, propertyKeys);
	}
}
