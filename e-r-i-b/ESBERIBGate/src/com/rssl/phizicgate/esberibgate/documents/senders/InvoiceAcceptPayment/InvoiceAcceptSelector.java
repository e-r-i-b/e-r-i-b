package com.rssl.phizicgate.esberibgate.documents.senders.InvoiceAcceptPayment;

import com.rssl.phizic.BasketPaymentsListenerConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.DocumentSender;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * Селектор для выбора взаимодействия через шину или напрямую в автопоэй
 * @author niculichev
 * @ created 01.06.15
 * @ $Author$
 * @ $Revision$
 */
public class InvoiceAcceptSelector extends AbstractService implements DocumentSender
{
	private static final String ESB_DELEGATE_PARAMETER_NAME  = "esb-mq-delegate";
	private static final String AUTOPAY_DELEGATE_PARAMETER_NAME  = "autopay-mq-delegate";

	private DocumentSender esbSender;
	private DocumentSender autoPaySender;

	public InvoiceAcceptSelector(GateFactory factory)
	{
		super(factory);
	}

	public void setParameters(Map<String, ?> params)
	{
		String esbSenderClassName = (String) params.get(ESB_DELEGATE_PARAMETER_NAME);
		if (StringHelper.isEmpty(esbSenderClassName))
			throw new IllegalStateException("Не задан " + ESB_DELEGATE_PARAMETER_NAME);

		String autoPaySenderClassName = (String) params.get(AUTOPAY_DELEGATE_PARAMETER_NAME);
		if (StringHelper.isEmpty(autoPaySenderClassName))
			throw new IllegalStateException("Не задан " + AUTOPAY_DELEGATE_PARAMETER_NAME);


		esbSender = loadDelegate(esbSenderClassName);
		autoPaySender = loadDelegate(autoPaySenderClassName);
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		getDelegate().send(document);
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		getDelegate().repeatSend(document);
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		getDelegate().prepare(document);
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		getDelegate().rollback(document);
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		getDelegate().confirm(document);
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		getDelegate().validate(document);
	}

	private DocumentSender getDelegate()
	{
		BasketPaymentsListenerConfig config = ConfigFactory.getConfig(BasketPaymentsListenerConfig.class);
		if(config.getWorkingMode() == BasketPaymentsListenerConfig.WorkingMode.esb)
		{
			return esbSender;
		}

		return autoPaySender;
	}

	private DocumentSender loadDelegate(String className)
	{
		try
		{
			Class<DocumentSender> senderClass = ClassHelper.loadClass(className);
			Constructor<DocumentSender> constructor = senderClass.getConstructor(GateFactory.class);
			return constructor.newInstance(getFactory());
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
