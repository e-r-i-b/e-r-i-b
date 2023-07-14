package com.rssl.phizic.messaging;

import com.rssl.phizic.module.ModuleManager;
import com.rssl.phizic.module.ModuleStaticManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rtischeva
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class MessageCoordinator
{
	private final Map<Class, MessageProcessor> processors = new HashMap<Class, MessageProcessor>();

	protected static final ModuleManager moduleManager = ModuleStaticManager.getInstance();

	protected void registerProcessor(Class requestClass, MessageProcessor processor)
	{
		processors.put(requestClass, processor);
	}

	/**
	 * ���������� ��������� �� ����������� xml-�������
	 * @param request - xml-������
	 * @return
	 */
	public MessageProcessor getProcessor(XmlMessage request)
	{
		return processors.get(request.getRequestClass());
	}
}
