package com.rssl.phizic.business.log.operations.config;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.business.BusinessException;

/**
 * @author Roshka
 * @ created 10.03.2006
 * @ $Author$
 * @ $Revision$
 */
public abstract class OperationDescriptorsConfig extends Config
{
	protected OperationDescriptorsConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * Возвращает описание логируемого действия.
	 * @param clazz - класс действия(экшен)
	 * @param operation - действие(имя метода экшена)
	 * @return описание
	 */
	public abstract LogEntryDescriptor getDescriptor(Class clazz, String operation) throws BusinessException;
}
