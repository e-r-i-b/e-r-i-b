package com.rssl.phizic.operations.employees;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.DbPropertyService;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.logging.messaging.System;

import java.util.HashMap;

/**
 * @author shapin
 * @ created 06.11.14
 * @ $Author$
 * @ $Revision$
 */
public class EmployeeSettingsOperation extends EditPropertiesOperation
{
	@Override
	public void save() throws BusinessLogicException, BusinessException
	{
		DbPropertyService.updateProperties(this.getEntity(), PropertyCategory.CSAAdmin, System.CSAAdmin.toValue());
		super.save();
	}
}
