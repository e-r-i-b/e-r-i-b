package com.rssl.phizgate.common.payments;

import com.rssl.phizic.gate.documents.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.GateFactory;

import java.util.Map;

/**
 * @author krenev
 * @ created 12.07.2010
 * @ $Author$
 * @ $Revision$
 * Сендер и CommissionCalculator, ограничивающий функциональность. Каждый метод
 * выкидывает GateLogicException с текстовкой из параметра message
 */
public class RestrictionSender extends AbstractService implements DocumentSender, CommissionCalculator, DocumentUpdater
{
	private static final String MESSAGE_PARAMETER_NAME = "message";
	private String message;

	public RestrictionSender(GateFactory factory)
	{
		super(factory);
	}

	public void setParameters(Map<String, ?> params)
	{
		message = (String) params.get(MESSAGE_PARAMETER_NAME);
		if (message == null)
		{
			throw new IllegalStateException("Не задан " + MESSAGE_PARAMETER_NAME);
		}
	}

	public void calcCommission(GateDocument transfer) throws GateException, GateLogicException
	{
		throw new GateLogicException(message);
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		throw new GateLogicException(message);
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		throw new GateLogicException(message);
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		throw new GateLogicException(message);
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new GateLogicException(message);
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		throw new GateLogicException(message);
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		throw new GateLogicException(message);
	}

	public StateUpdateInfo execute(GateDocument document) throws GateException, GateLogicException
	{
		throw new GateException("Операция не поддерживается");
	}
}
