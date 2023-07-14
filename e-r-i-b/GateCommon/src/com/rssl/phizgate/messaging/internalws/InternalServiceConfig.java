package com.rssl.phizgate.messaging.internalws;

import com.rssl.phizgate.messaging.internalws.client.DefaultErrorHandler;
import com.rssl.phizgate.messaging.internalws.client.ErrorHandler;
import com.rssl.phizgate.messaging.internalws.server.protocol.handlers.RequestProcessor;
import com.rssl.phizgate.messaging.internalws.server.protocol.handlers.exceptions.ExceptionHandler;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gladishev
 * @ created 17.02.14
 * @ $Author$
 * @ $Revision$
 */
public class InternalServiceConfig
{
	private static final String REQUEST_HANDLERS_TAG = "requestHandlers/handler";
	private static final String DEFAULT_DESCRIPTION_TAG = "defaultDescription";
	private static final String EXCEPTION_HANDLERS_TAG = "exceptionHandlers";
	private static final String DEFAULT_CODE_TAG = "defaultCode";
	private static final String DESCRIPTION_TAG = "description";
	private static final String EXCEPTION_TAG = "exception";
	private static final String ERRORS_TAG = "errors/error";
	private static final String HANDLER_TAG = "handler";
	private static final String REQUEST_TAG = "request";
	private static final String CLIENT_TAG = "client";
	private static final String SERVER_TAG = "server";
	private static final String CLASS_TAG = "class";
	private static final String CODE_TAG = "code";

	private static final Object LOCK = new Object();
	private static InternalServiceConfig instance = null;

	private Map<String, ErrorHandler> errorHandlers; //обработчики ошибок на клиентской стороне сервиса
	private Map<String, RequestProcessor> requestProcessors; //мап обработчиков запросов
	private Map<String, ExceptionHandler> exceptionHandlers; //мап обработчиков исключений возникающих при обработке запросов
	private ExceptionHandler defaultExceptionHandler;

	public static InternalServiceConfig getInstance()
	{
		if (instance != null)
			return instance;

		synchronized (LOCK)
		{
			if (instance != null)
				return instance;

			instance = new InternalServiceConfig();
			return instance;
		}
	}

	private InternalServiceConfig()
	{
		try
		{
			Element root = XmlHelper.loadDocumentFromResource("internal-messaging-service.xml").getDocumentElement();

			Element clientDescription = XmlHelper.selectSingleNode(root, CLIENT_TAG);
			if (clientDescription != null)
			{
				errorHandlers = new HashMap<String, ErrorHandler>();
				XmlHelper.foreach(clientDescription, ERRORS_TAG, new ForeachElementAction()
				{
					public void execute(Element element) throws Exception
					{
						String errorCode = element.getAttribute(CODE_TAG);
						if (element.hasAttribute(EXCEPTION_TAG))
						{
							Class<? extends Exception> aClass = ClassHelper.loadClass(element.getAttribute(EXCEPTION_TAG));
							errorHandlers.put(errorCode, new DefaultErrorHandler(aClass));
						}
						else if (element.hasAttribute(HANDLER_TAG))
						{
							Class<? extends ErrorHandler> aClass = ClassHelper.loadClass(element.getAttribute(HANDLER_TAG));
							errorHandlers.put(errorCode, aClass.newInstance());
						}
						else
							throw new ConfigurationException("Не установлен обработчик для ошибки " + errorCode);
					}
				});
			}
			Element serverDescription = XmlHelper.selectSingleNode(root, SERVER_TAG);
			if (serverDescription != null)
			{
				requestProcessors = new HashMap<String, RequestProcessor>();
				XmlHelper.foreach(serverDescription, REQUEST_HANDLERS_TAG, new ForeachElementAction()
				{
					public void execute(Element element) throws Exception
					{
						Class<RequestProcessor> aClass = ClassHelper.loadClass(element.getAttribute(CLASS_TAG));
						requestProcessors.put(element.getAttribute(REQUEST_TAG), aClass.newInstance());
					}
				});

				Element exceptionHandlersElement = XmlHelper.selectSingleNode(serverDescription, EXCEPTION_HANDLERS_TAG);
				defaultExceptionHandler = new ExceptionHandler(Integer.parseInt(exceptionHandlersElement.getAttribute(DEFAULT_CODE_TAG)),
																				exceptionHandlersElement.getAttribute(DEFAULT_DESCRIPTION_TAG));

				exceptionHandlers = new HashMap<String, ExceptionHandler>();
				XmlHelper.foreach(exceptionHandlersElement, HANDLER_TAG, new ForeachElementAction()
				{
					public void execute(Element element) throws Exception
					{
						Class<ExceptionHandler> aClass = ClassHelper.loadClass(element.getAttribute(CLASS_TAG));
						ExceptionHandler handler = aClass.getConstructor(int.class, String.class)
															.newInstance(Integer.parseInt(element.getAttribute(CODE_TAG)), element.getAttribute(DESCRIPTION_TAG));
						exceptionHandlers.put(element.getAttribute(EXCEPTION_TAG), handler);
					}
				});
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException("Ошибка при чтении файла internal-messaging-service.xml", e);
		}
	}

	public ErrorHandler getErrorHandler(String errorCode)
	{
		return errorHandlers.get(errorCode);
	}

	public RequestProcessor getRequestProcessor(String request)
	{
		return requestProcessors.get(request);
	}

	public ExceptionHandler getExceptionHandler(Exception e)
	{
		return exceptionHandlers.get(e.getClass().getName());
	}

	public ExceptionHandler getDefaultExceptionHandler()
	{
		return defaultExceptionHandler;
	}
}
