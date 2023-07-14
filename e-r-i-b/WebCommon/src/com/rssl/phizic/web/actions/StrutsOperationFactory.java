package com.rssl.phizic.web.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.messages.MessageConfig;
import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.business.operations.OperationFactoryImpl;
import com.rssl.phizic.business.operations.restrictions.RestrictionProviderImpl;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.operations.OperationBase;

import java.security.AccessControlException;

/**
 * @author Erkin
 * @ created 22.10.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Фабрика операций, которая при создании операции передаёт ей
 * MessageConfig - объект, выдающий сообщения из properties-file-ов по ключу
 */
public class StrutsOperationFactory extends OperationFactoryImpl
{
	private final MessageConfig messageConfig;

	public StrutsOperationFactory(MessageConfig messageConfig)
	{
		super(new RestrictionProviderImpl());
		this.messageConfig = messageConfig;
	}

	protected <T extends Operation> T setupOperation(T operation)
	{
		if (operation instanceof OperationBase)
		{
			OperationBase operationBase = (OperationBase) operation;
			operationBase.setOperationFactory(this);
			operationBase.setMessageConfig(messageConfig);
			operationBase.setClientAPIVersion(MobileApiUtil.getApiVersionNumber());

		}
		return operation;
	}

	public <T extends Operation> T create(Class<T> operationClass)
			throws AccessControlException, BusinessException
	{
		return setupOperation(super.create(operationClass));
	}

	public <T extends Operation> T create(String operationKey)
			throws AccessControlException, BusinessException
	{
		return setupOperation(super.<T>create(operationKey));
	}

	public <T extends Operation> T create(Class<T> operationClass, String serviceKey)
			throws BusinessException
	{
		return setupOperation(super.create(operationClass, serviceKey));
	}

	public <T extends Operation> T create(String operationKey, String serviceKey)
			throws AccessControlException, BusinessException
	{
		return setupOperation(super.<T>create(operationKey, serviceKey));
	}
}
