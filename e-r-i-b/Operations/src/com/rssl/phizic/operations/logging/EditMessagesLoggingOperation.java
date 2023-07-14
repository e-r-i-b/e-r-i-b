package com.rssl.phizic.operations.logging;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.*;
import com.rssl.phizic.logging.messaging.MessageLogConfig;

import java.util.*;

/**
 * Операция для редактирования настроек журнала сообщений
 * @author basharin
 * @ created 21.05.2013
 * @ $Author$
 * @ $Revision$
 */

public class EditMessagesLoggingOperation extends EditLoggingLevelOperation
{
	public static final String EXCLUDED_MESSAGES = "excludedMessages";
	public static final String EXCLUDED_MESSAGES_ID_KEY = "excludedMessagesProperties_Id_";
	public static final String EXCLUDED_MESSAGES_CODE_KEY = "excludedMessagesProperties_message_";

	//справочник табличных настроек и хелперов для их конвертаци
	private static final Map<String, ListPropertiesHelper> tableProperties = new HashMap<String, ListPropertiesHelper>();

	static
	{
		tableProperties.put(EXCLUDED_MESSAGES, new ListPropertiesHelper(EXCLUDED_MESSAGES_ID_KEY, EXCLUDED_MESSAGES_CODE_KEY, "", "^^", "\\^\\^"));
	}

	@Override
	public void initialize(PropertyCategory category, Set<String> keys, String prefix) throws BusinessException, BusinessLogicException
	{
		super.initialize(category, keys, prefix);
		List<Property> excludesMessagesProps = findPropertiesByKey(MessageLogConfig.EXCLUDED_MESSAGES_PROPERTY_KEY);
		addProperties(tableProperties.get(EXCLUDED_MESSAGES).convertToMap(excludesMessagesProps));
	}

	public void initializeReplicate(PropertyCategory category, Set keys) throws BusinessException, BusinessLogicException
	{
		boolean excludedMessages = keys.remove(MessageLogConfig.EXCLUDED_MESSAGES_PROPERTY_KEY);
		super.initialize(category, keys);
		if (excludedMessages)
			initializeListProperties(MessageLogConfig.EXCLUDED_MESSAGES_PROPERTY_KEY);
	}

	@Override
	public void save() throws BusinessException, BusinessLogicException
	{
		List<String> excludeMessagesValues = tableProperties.get(EXCLUDED_MESSAGES).convertToList(getEntity());
		DbPropertyService.updateListProperty(MessageLogConfig.EXCLUDED_MESSAGES_PROPERTY_KEY, excludeMessagesValues, getPropertyCategory(), getDbInstance(getPropertyCategory()));
		super.save();
	}
}
