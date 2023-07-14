package com.rssl.phizic.utils.xml.jaxb;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * @author akrenev
 * @ created 13.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������� ���������� JAXB
 */

public final class JAXBContextFactory
{
	private static final JAXBContextFactory INSTANCE = new JAXBContextFactory();
	private final Map<String, JAXBContext> contextMap = new HashMap<String, JAXBContext>();

	private JAXBContextFactory(){}

	/**
	 * @return ������� �������
	 */
	public static JAXBContextFactory getInstance()
	{
		return INSTANCE;
	}

	/**
	 * �������� �������� �� �����
	 * @param key �����
	 * @return ��������
	 * @throws JAXBException
	 */
	public final JAXBContext getContext(String key) throws JAXBException
	{
		JAXBContext context = contextMap.get(key);
		if (context != null)
			return context;

		synchronized (contextMap)
		{
			context = contextMap.get(key);
			if (context != null)
				return context;

			context = JAXBContext.newInstance(key);
			contextMap.put(key, context);
		}
		return context;
	}
}
