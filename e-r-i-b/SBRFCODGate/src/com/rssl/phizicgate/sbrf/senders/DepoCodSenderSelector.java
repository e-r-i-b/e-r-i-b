package com.rssl.phizicgate.sbrf.senders;

import com.rssl.phizgate.common.payments.documents.AbstractDocumentSenderBase;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.GateSettingsConfig;
import com.rssl.phizic.gate.documents.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizicgate.sbrf.senders.depocod.DepoCodSenderBase;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * Селектор отправителя сообщений во ВС
 * @author gladishev
 * @ created 22.04.2014
 * @ $Author$
 * @ $Revision$
 */

public class DepoCodSenderSelector extends AbstractDocumentSenderBase
{
	protected AbstractDocumentSenderBase depoCodDelegate;
	protected AbstractDocumentSenderBase codDelegate;

	/**
	 * ctor
	 * @param factory фабрика гейта
	 */
	public DepoCodSenderSelector(GateFactory factory)
	{
		super(factory);
	}

	@Override
	public void setParameters(Map<String, ?> params)
	{
		depoCodDelegate = loadDelegate((String) params.remove("DepoCod"));
		codDelegate = loadDelegate((String) params.remove("cod"));

		depoCodDelegate.setParameters(params);
		codDelegate.setParameters(params);
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		if (ConfigFactory.getConfig(GateSettingsConfig.class).useDepoCodWS())
			depoCodDelegate.send(document);
		else
			codDelegate.send(document);

	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		codDelegate.repeatSend(document);
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		if (ConfigFactory.getConfig(GateSettingsConfig.class).useDepoCodWS())
			depoCodDelegate.prepare(document);
		else
			codDelegate.prepare(document);
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		codDelegate.rollback(document);
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		codDelegate.confirm(document);
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		codDelegate.validate(document);
	}

	@Override
	public StateUpdateInfo execute(GateDocument document) throws GateException, GateLogicException
	{
		if (document instanceof SynchronizableDocument && ((SynchronizableDocument) document).getExternalId().contains(DepoCodSenderBase.DEPO_COD_KEY))
			return depoCodDelegate.execute(document);

		return super.execute(document);
	}

	private <T extends AbstractDocumentSenderBase> T loadDelegate(String className)
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
}
