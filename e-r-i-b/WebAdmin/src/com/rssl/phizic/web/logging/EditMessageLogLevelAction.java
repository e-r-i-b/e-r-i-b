package com.rssl.phizic.web.logging;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.logging.EditMessagesLoggingOperation;

import java.util.Map;

/**
 * @author eMakarov
 * @ created 26.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditMessageLogLevelAction extends EditLoggingLevelAction
{
	@Override
	protected EditMessagesLoggingOperation getEditOperation() throws BusinessException
	{
		return createOperation(EditMessagesLoggingOperation.class);
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		((Map)entity).clear();
		super.updateEntity(entity, data);
	}
}