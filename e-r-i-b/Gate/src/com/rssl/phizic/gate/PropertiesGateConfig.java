package com.rssl.phizic.gate;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 07.10.2005 Time: 15:01:45 */
public class PropertiesGateConfig extends GateConfig
{
    private static final String GATE_KEY = "com.rssl.iccs.retail.gate";
	private static final String MBK_LOADER_CALL_BACK = "com.rssl.iccs.mbkloader.callback";

	public PropertiesGateConfig(PropertyReader reader)
	{
		super(reader);
	}

	public String getGateClass()
    {
        return getProperty(GATE_KEY);
    }

	/**
	 * Обработчик сообщений при обновлении номеров телеофнов.
	 *
	 * @return обработчик сообщейний или null.
	 * @throws GateException
	 */
	public Object getMBKPhoneLoaderCallback() throws GateException
	{
		String str = getProperty(MBK_LOADER_CALL_BACK);
		if (StringHelper.isEmpty(str))
			return null;

		try
		{
			Class clazz = ClassHelper.loadClass(str);
			return clazz.newInstance();
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

    public Gate buildGate()
    {
        String gateClass = getGateClass();
        if(gateClass == null)
        {
            throw new RuntimeException("В настройках приложения не указан параметр Gate");
        }

        try
        {
            Class aClass = Class.forName(gateClass);
            Gate gate = (Gate) aClass.newInstance();
            gate.initialize();
            return gate;
        }
        catch(Exception e)
        {
	        PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE).error(e);
            throw new RuntimeException("Ошибка при создании гейта", e);
        }
    }

	/**
	 * Возвращает время ожидания ответа от шлюза(для основного приложения)
	 * @return время ожидания (в миллисекундах)
	 */
	public int getTimeout()
	{
		String timeout = getProperty(TimeoutHttpTransport.GATE_WRAPPER_CONNECTION_TIMEOUT);
		return StringHelper.isEmpty(timeout) ? 0 : Integer.parseInt(timeout);
	}

	public void doRefresh() throws ConfigurationException
	{
	}
}
