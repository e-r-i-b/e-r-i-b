package com.rssl.phizic.operations.messageTranslate;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.logging.translateMessages.MessageTranslate;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * @author Mescheryakova
 * @ created 15.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class MessageTranslateListOperation extends OperationBase implements ListEntitiesOperation, RemoveEntityOperation
{
	private static final SimpleService simpleService = new SimpleService();
	private MessageTranslate messageTranslate;
	/**
	 * Инициализация операции
	 * @param id идентификатор сущности для удаления.
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		messageTranslate = simpleService.findById(MessageTranslate.class, id, getInstanceName());
	}

	/**
	 *  Удалить сущность.
	 */
	public void remove() throws BusinessException
	{
		simpleService.remove(messageTranslate, getInstanceName());
	}

	/**
	 * @return удаляемую/удаленную сущность.
	 */
	public MessageTranslate getEntity()
	{
		return messageTranslate;
	}

	protected String getInstanceName()
	{
		return Constants.DB_INSTANCE_NAME;
	}
}
