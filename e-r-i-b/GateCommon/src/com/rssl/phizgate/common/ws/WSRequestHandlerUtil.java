package com.rssl.phizgate.common.ws;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.*;

/**
 * @author akrenev
 * @ created 20.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class WSRequestHandlerUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);


	/**
	 * ��������� � ������ WSRequestHandler(��� �������� � ��� ������ �������� OperUID � SessionId)
	 * @param service ������
	 */
	public static void addWSRequestHandlerToService(Service service)
	{
		addWSRequestHandlerToService(service, WSRequestHandler.class);
	}

	/**
	 * ��������� � ������ ������� ��� �������� � ��� ������ ���. ����������
	 * @param service
	 * @param genericHandlerClass
	 */
	public static void addWSRequestHandlerToService(Service service, Class<? extends GenericHandler> genericHandlerClass)
	{
		Iterator ports = null;
		try
		{
			ports = service.getPorts();
		}
		catch (ServiceException e)
		{
			log.error("���������� �������� ���� ��� ������� " +  service.getServiceName(), e);
			return;
		}
		HandlerRegistry handlerRegistry = service.getHandlerRegistry();
		while (ports.hasNext())
		{
			QName qName = (QName) ports.next();
			List handlerInfos = handlerRegistry.getHandlerChain(qName);
			HandlerInfo handlerInfo = new HandlerInfo(genericHandlerClass, new HashMap(), null);
			handlerInfos.add(handlerInfo);
		}
	}
}
