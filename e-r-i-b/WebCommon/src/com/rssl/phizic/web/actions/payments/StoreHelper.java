package com.rssl.phizic.web.actions.payments;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.timeoutsessionrequest.TimeoutSession;
import com.rssl.phizic.business.timeoutsessionrequest.TimeoutSessionService;
import com.rssl.phizic.utils.MapUtil;

import java.util.Map;
import java.util.HashMap;

/**
 * @author krenev
 * @ created 06.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class StoreHelper
{
	private static final TimeoutSessionService sessionService = new TimeoutSessionService();
	private static final String ENTRY_DELIMITER = ";";
	private static final String KEY_VALUE_DELIMITER = "=";

	/**
	 * Сохранить значения полей.
	 * Старые значения затираются новыми
	 * @param fields значения полей
	 */
	public static void storeFields(Map<String, String> fields) throws BusinessException
	{
		saveParameters(fields);
	}

	/**
	 * Восстановить значения ключевых полей
	 * @return знaчения ключевых полей
	 */
	public static Map<String, String> restoreFields() throws BusinessException
	{
		return getParameters();
	}

	/**
	 * Сбросить(очистить) хранилище
	 */
	public static void reset() throws BusinessException
	{
		sessionService.removeByUrl(getKey());
	}

	private static Map<String, String> getParameters() throws BusinessException
	{
		String key = getKey();
		TimeoutSession parameters = sessionService.getByRandomRecordId(key);
		if (parameters == null)
		{
			return new HashMap<String, String>();
		}
		return MapUtil.parse(parameters.getParametres(), KEY_VALUE_DELIMITER, ENTRY_DELIMITER);
	}

	private static void saveParameters(Map<String, String> params) throws BusinessException
	{
		String key = getKey();
		TimeoutSession parameters = sessionService.getByRandomRecordId(key);
		if (parameters == null)
		{
			parameters = new TimeoutSession();
			parameters.setUrl(key);
			parameters.setRandomRecordId(key);
		}
		parameters.setParametres(MapUtil.format(params, KEY_VALUE_DELIMITER, ENTRY_DELIMITER));
		sessionService.addOrUpdate(parameters);
	}

	private static String getKey()
	{
		return AuthModule.getAuthModule().getPrincipal().getLogin().getUserId();
	}
}
