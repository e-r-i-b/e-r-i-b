package com.rssl.phizic.locale.replicator;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.locale.entities.ERIBStaticMessage;
import com.rssl.phizic.locale.services.EribStaticMessageService;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Класс для репликации локалей
 * @author komarov
 * @ created 15.09.2014
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings("ProtectedField")
public class PropertyDestenation implements ReplicaDestenation<ERIBStaticMessage>
{
	private static final String KEY_SEPARATOR = "|";
	protected static final EribStaticMessageService ERIB_STATIC_MESSAGE_SERVICE = new EribStaticMessageService();
	protected List<ERIBStaticMessage> list;
	private String instance;

	/**
	 * Инициализирует дестинейшен в разрезе локали
	 * @param localeId иденификатор локали
	 * @param bundleName бандл для которого грузим текстовки
	 * @throws SystemException
	 */
	public PropertyDestenation(String localeId, String bundleName, String instance) throws SystemException
	{
		this.instance = instance;
		if(StringHelper.isEmpty(bundleName))
			initialize(localeId);
		else
			initialize(localeId, bundleName);
	}

	protected void initialize(String localeId) throws SystemException
	{
		list = ERIB_STATIC_MESSAGE_SERVICE.getAll(localeId, instance);
	}

	protected void initialize(String localeId, String bundle) throws SystemException
	{
		list = ERIB_STATIC_MESSAGE_SERVICE.getAll(localeId, bundle, instance);
	}

	public void add(ERIBStaticMessage newValue) throws SystemException
	{
		ERIB_STATIC_MESSAGE_SERVICE.add(newValue, instance);
	}

	public void add(List<ERIBStaticMessage> newValue) throws SystemException
	{
		ERIB_STATIC_MESSAGE_SERVICE.add(newValue, instance);
	}

	public void remove(ERIBStaticMessage oldValue) throws SystemException
	{
		ERIB_STATIC_MESSAGE_SERVICE.delete(oldValue, instance);
	}

	public void remove(List<ERIBStaticMessage> oldValue) throws SystemException
	{
		ERIB_STATIC_MESSAGE_SERVICE.delete(oldValue, instance);
	}

	public void update(ERIBStaticMessage value) throws SystemException
	{
		ERIB_STATIC_MESSAGE_SERVICE.update(value, instance);
	}

	public void update(List<ERIBStaticMessage> value) throws SystemException
	{
		ERIB_STATIC_MESSAGE_SERVICE.update(value, instance);
	}

	public Set<ERIBStaticMessage> iterator()
	{
		return null;
	}

	/**
	 * @return возвращает дестинейшен-мапу
	 */
	public Map<String, ERIBStaticMessage> getDestenation()
	{
		Map<String, ERIBStaticMessage> map = new HashMap<String, ERIBStaticMessage>();
		for(ERIBStaticMessage message : list)
		{
			map.put(message.getBundle() + KEY_SEPARATOR+ message.getKey(), message);
		}
		return map;
	}

	protected String getInstance()
	{
		return instance;
	}
}
