package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.resources.external.IMAccountFilter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.ima.IMAccount;

import java.util.Map;

/**
 * Разрешает использование закэшированных ОМС в платежах
 * @author Pankin
 * @ created 10.02.2013
 * @ $Author$
 * @ $Revision$
 */

public class StoredIMAccountListSource extends IMAccountListSource
{
	public StoredIMAccountListSource(EntityListDefinition definition)
	{
		super(definition);
	}

	public StoredIMAccountListSource(EntityListDefinition definition, IMAccountFilter imaccountFilter)
	{
		super(definition, imaccountFilter);
	}

	public StoredIMAccountListSource(EntityListDefinition definition, Map parameters) throws BusinessException
	{
		super(definition, parameters);
	}

	protected boolean skipStoredResource(IMAccount imAccount)
	{
		return true;
	}
}
