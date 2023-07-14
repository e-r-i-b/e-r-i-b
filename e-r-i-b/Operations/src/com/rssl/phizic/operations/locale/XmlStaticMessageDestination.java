package com.rssl.phizic.operations.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.locale.replicator.PropertyDestenation;

/**
 * @author koptyaev
 * @ created 15.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class XmlStaticMessageDestination extends PropertyDestenation
{
	/**
	 * Создаёт дестинейшен для загрузки из файла
	 * @param localeId идентификатор локали
	 * @throws BusinessException
	 */
	public XmlStaticMessageDestination(String localeId, String instance) throws SystemException
	{
		super(localeId, null, instance);
	}

	@Override
	protected void initialize(String localeId) throws BusinessException
	{
		try
		{
			list = ERIB_STATIC_MESSAGE_SERVICE.getMessagesForReplication(localeId, getInstance());
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
	}
}
