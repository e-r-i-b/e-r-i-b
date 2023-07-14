package com.rssl.phizic.operations.log;

import com.rssl.phizic.business.documents.templates.ConfigImpl;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.query.QueryParameter;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.ListForEmployeeOperation;
import com.rssl.phizic.operations.ViewEntityOperation;

/**
 * @author malafeevsky
 * @ created 11.08.2009
 * @ $Author$
 * @ $Revision$
 */
public abstract class LogFilterOperationBase<R extends Restriction> extends ListForEmployeeOperation<R> implements ListEntitiesOperation<R>, ViewEntityOperation
{
	protected String getInstanceName()
	{
		return Constants.DB_INSTANCE_NAME;
	}

	/**
	 * @return имя линка дб.
	 */
	@QueryParameter
	public String getLinkName()
	{
		return ConfigFactory.getConfig(ConfigImpl.class).getDbLinkName();
	}
}
