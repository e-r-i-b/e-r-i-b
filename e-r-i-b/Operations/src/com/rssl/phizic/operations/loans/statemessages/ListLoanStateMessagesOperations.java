package com.rssl.phizic.operations.loans.statemessages;

import com.rssl.phizic.business.loans.ClaimStateMessagesService;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 23.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class ListLoanStateMessagesOperations extends OperationBase implements ListEntitiesOperation
{
	private static final String prefix = "claim.state.";
	private Properties properties;

	public void initialize()
	{
	}

	public Properties getProperties()
	{
		properties = ConfigFactory.getConfig(ClaimStateMessagesService.class).getProperties(prefix);
	    return properties;
	}

	public String getValue(String key)
	{
		return ConfigFactory.getConfig(ClaimStateMessagesService.class).getProperty(key);
	}
}
