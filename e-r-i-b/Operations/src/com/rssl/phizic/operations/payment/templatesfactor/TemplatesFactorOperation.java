package com.rssl.phizic.operations.payment.templatesfactor;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.operations.restrictions.DepartmentRestriction;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import org.apache.commons.collections.MapUtils;

import java.util.Set;

/**
 * @author vagin
 * @ created 06.06.2012
 * @ $Author$
 * @ $Revision$
 * ќпераци€ дл€ задани€ кратности суммы дл€ шаблонов
 */
public class TemplatesFactorOperation extends EditPropertiesOperation<DepartmentRestriction>
{
	public void initialize(PropertyCategory category, Set<String> propertyKeys, Long departmentId) throws BusinessException, BusinessLogicException
	{
		if (!getRestriction().accept(departmentId))
			throw new RestrictionViolationException(String.format("ѕодразделение: id = %s не €вл€етс€ доступным дл€ текущего пользовател€", departmentId));

		super.initialize(category, propertyKeys);

		if (MapUtils.isEmpty(getEntity()))
			getEntity().put(propertyKeys.iterator().next(), String.valueOf(DocumentHelper.TEMPLATE_FACTOR_DEFAULT_VALUE));
	}
}
