package com.rssl.phizic.operations.loans.statemessages;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loans.ClaimStateMessagesService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 29.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class EditLoanStateMessagesOperations extends OperationBase implements EditEntityOperation
{
	private String value;
	private String key;

	public void initialize(String key)
	{
		this.key = key;
	}

	public void save() throws BusinessException
	{
		throw new UnsupportedOperationException();
	}

	public String getEntity()
	{
		return ConfigFactory.getConfig(ClaimStateMessagesService.class).getProperty(key);
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}
}

