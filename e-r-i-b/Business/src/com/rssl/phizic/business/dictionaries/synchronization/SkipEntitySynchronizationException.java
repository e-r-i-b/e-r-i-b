package com.rssl.phizic.business.dictionaries.synchronization;

import com.rssl.phizic.business.BusinessException;

/**
 * @author mihaylov
 * @ created 14.03.14
 * @ $Author$
 * @ $Revision$
 *
 * Исключение при синхронизации справочников, синхронизирующее о необходимости пропустить текущую сущность
 * ВАЖНО!!! Не используйте его пожалуйста, синхронизация должна идти по порядку записей в логе.
 * Любые пропуски сущностей череваты неконсистентным состоянием справочников в блоке.
 *
 */
public class SkipEntitySynchronizationException extends BusinessException
{

	/**
	 * @param message сообщение для записи в лог
	 */
	public SkipEntitySynchronizationException(String message)
	{
		super(message);
	}
}
