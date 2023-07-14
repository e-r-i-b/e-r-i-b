package com.rssl.phizicgate.manager.services.selectors.way;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * @author akrenev
 * @ created 18.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * базовый селектор метода доставки сообщений во внешнюю систему
 */

public abstract class AbstractWaySelector<I> extends AbstractService
{
	private static final String WS_DELEGATE_PARAMETER_NAME  = "ws-delegate";
	private static final String MQ_DELEGATE_PARAMETER_NAME  = "mq-delegate";

	private I wsDelegate;
	private I mqDelegate;

	protected AbstractWaySelector(GateFactory factory)
	{
		super(factory);
	}

	protected abstract void initializeDelegate(I delegate, Map<String, ?> params);

	protected abstract Class getServiceType();

	/**
	 * ”становить параметры
	 * @param params параметры
	 */
	public void setParameters(Map<String, ?> params)
	{
		String wsDelegateClassName = (String) params.get(WS_DELEGATE_PARAMETER_NAME);
		if (StringHelper.isEmpty(wsDelegateClassName))
		    throw new IllegalStateException("Ќе задан " + WS_DELEGATE_PARAMETER_NAME);

		wsDelegate = loadInstance(wsDelegateClassName);
		initializeDelegate(wsDelegate, params);

		String mqDelegateClassName = (String) params.get(MQ_DELEGATE_PARAMETER_NAME);
		if (StringHelper.isEmpty(mqDelegateClassName))
			throw new IllegalStateException("Ќе задан " + MQ_DELEGATE_PARAMETER_NAME);

		mqDelegate = loadInstance(mqDelegateClassName);
		initializeDelegate(mqDelegate, params);
	}

	protected final I getServiceDelegate(GateDocument document) throws GateException
	{
		return useMQDelegate(document, getServiceType()) ? getMQDelegate(): getWSDelegate();
	}

	/**
	 * использовать ли интеграцию через MQ дл€ документа
	 * @param document документ
	 * @param serviceType тип сервиса
	 * @return использовать ли интеграцию через MQ дл€ документа
	 * @throws GateException
	 */
	public static boolean useMQDelegate(GateDocument document, Class serviceType) throws GateException
	{
		return WaySelectorHelper.useMQDelegate(document, serviceType);
	}

	protected final I getMQDelegate()
	{
		return mqDelegate;
	}

	protected final I getWSDelegate()
	{
		return wsDelegate;
	}

	private I loadInstance(String className)
	{
		try
		{
			Class<I> senderClass = ClassHelper.loadClass(className);
			Constructor<I> constructor = senderClass.getConstructor(GateFactory.class);
			return constructor.newInstance(getFactory());
		}
		catch (Exception e)
		{
			throw new RuntimeException("ќшибка при загрузке инстанса " + className, e);
		}
	}
}
