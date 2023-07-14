package com.rssl.phizicgate.manager.services.routable.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.routable.RoutableServiceBase;

/**
 * @author krenev
 * @ created 07.12.2010
 * @ $Author$
 * @ $Revision$
 * Абстрактный маршрутизирующий сервис, реализующий интерфейсы  DocumentService, DocumentSender, CommissionCalculator
 * для всех методов используется вызов делегата. правила получения делегата определяют наследники.
 */
public abstract class AbstractRoutableDocumentService extends RoutableServiceBase implements DocumentService, DocumentSender, DocumentUpdater, CommissionCalculator
{
	public AbstractRoutableDocumentService(GateFactory factory)
	{
		super(factory);
	}

	public void calcCommission(GateDocument document) throws GateException, GateLogicException
	{
		getDelegate(document).calcCommission(document);
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		getDelegate(document).send(document);
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		getDelegate(document).repeatSend(document);
	}

	public StateUpdateInfo update(GateDocument document) throws GateException, GateLogicException
	{
		return getDelegate(document).update(document);
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		getDelegate(document).prepare(document);
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		recall(document);
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		getDelegate(document).confirm(document);
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		getDelegate(document).validate(document);
	}

	public void recall(GateDocument document) throws GateException, GateLogicException
	{
		getDelegate(document).recall(document);
	}

	public StateUpdateInfo execute(GateDocument document) throws GateException, GateLogicException
	{
		return getDelegate(document).update(document);
	}

	/**
	 * Получение делегата по документу
	 * @param document документ
	 * @return делегат
	 * @throws GateException
	 */
	protected abstract DocumentService getDelegate(GateDocument document) throws GateException, GateLogicException;
}
