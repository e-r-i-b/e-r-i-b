package com.rssl.phizicgate.manager.ext.sbrf.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * @author krenev
 * @ created 12.07.2010
 * @ $Author$
 * @ $Revision$
 * выбирает в зависимости от офиса, в который посылаетс€ документ, необходимость его посылки
 * на шину (esb-erib-delegate) или на обычный узел(default-delegate)
 */
public abstract class ESBERIBPaymetDelegateSelector<T> extends AbstractService
{
	private static final String ESB_ERIB_DELEGATE_PARAMETER_NAME = "esb-erib-delegate";
	private static final String DEFAULT_DELEGATE_PARAMETER_NAME = "default-delegate";
	protected T esbDelegate;
	protected T defaultDelegate;

	protected ESBERIBPaymetDelegateSelector(GateFactory factory)
	{
		super(factory);
	}

	public void setParameters(Map<String, ?> params)
	{
		String sbPaymentDelegateClassName = (String) params.get(ESB_ERIB_DELEGATE_PARAMETER_NAME);
		if (sbPaymentDelegateClassName == null)
		{
			throw new IllegalStateException("Ќе задан " + ESB_ERIB_DELEGATE_PARAMETER_NAME);
		}
		esbDelegate = loadDelegate(sbPaymentDelegateClassName);
		String simpleDelegateClassName = (String) params.get(DEFAULT_DELEGATE_PARAMETER_NAME);
		if (simpleDelegateClassName == null)
		{
			throw new IllegalStateException("Ќе задан " + DEFAULT_DELEGATE_PARAMETER_NAME);
		}
		defaultDelegate = loadDelegate(simpleDelegateClassName);

		params.remove(ESB_ERIB_DELEGATE_PARAMETER_NAME);
		params.remove(DEFAULT_DELEGATE_PARAMETER_NAME);
	}

	/**
	 * загрузить делегат по имени класса
	 * @param className fqn сендра
	 * @return инстанс сендера.
	 */
	private T loadDelegate(String className)
	{
		try
		{
			Class<T> senderClass = ClassHelper.loadClass(className);
			Constructor<T> constructor = senderClass.getConstructor(GateFactory.class);
			return constructor.newInstance(getFactory());
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	protected final T getDelegate(GateDocument document) throws GateException, GateLogicException
	{
		if (esbSupported(document))
		{
			return esbDelegate;
		}
		return defaultDelegate;
	}

	protected boolean esbSupported(GateDocument document) throws GateException
	{
		return ESBHelper.isESBSupported(document);
	}
}
