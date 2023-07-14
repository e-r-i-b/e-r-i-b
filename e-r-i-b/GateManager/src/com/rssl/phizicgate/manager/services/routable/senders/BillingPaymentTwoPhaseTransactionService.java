package com.rssl.phizicgate.manager.services.routable.senders;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.exceptions.GateWrapperTimeOutException;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizicgate.manager.services.routable.RoutableServiceBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 19.01.2010
 * @ $Author$
 * @ $Revision$
 * сендер - реализация двухфазных транзакций биллинговых платежей.
 */
public class BillingPaymentTwoPhaseTransactionService extends RoutableServiceBase implements DocumentSender, DocumentUpdater, CommissionCalculator
{
	public BillingPaymentTwoPhaseTransactionService(GateFactory factory)
	{
		super(factory);
	}

	public void setParameters(Map<String, ?> params) {}

	public void calcCommission(GateDocument transfer) throws GateException, GateLogicException
	{
		getBillingDelegate(transfer).calcCommission(transfer);
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		getABSDelegate(document).send(document);
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		getBillingDelegate(document).prepare(document);
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Отзыв биллингового платежа не поддерживается");
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Повторная отправка биллингового платежа не поддерживается");
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		UpdateDocumentService updateService = getFactory().service(UpdateDocumentService.class);
		try
		{
			getBillingDelegate(document).confirm(document);
			//BUG028971 - требуется обновлять в документе кроместатуса еще и некотроые поля(например код код операции во внешней системе)
			updateService.update((SynchronizableDocument) document);
			updateService.update((SynchronizableDocument) document, new DocumentCommand(DocumentEvent.EXECUTE, new HashMap<String, Object>()));
		}
		catch (GateException e)
		{
			recallPayment(document);
		}
		catch (GateWrapperTimeOutException e)
		{
			updateService.update((SynchronizableDocument) document, new DocumentCommand(DocumentEvent.BILLING_CONFIRM_TIMEOUT, new HashMap<String, Object>()));
		}
		catch (GateTimeOutException e)
		{
			updateService.update((SynchronizableDocument) document, new DocumentCommand(DocumentEvent.BILLING_GATE_CONFIRM_TIMEOUT, new HashMap<String, Object>()));
		}
		catch (GateLogicException e)
		{
			recallPayment(document);
		}
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		getABSDelegate(document).validate(document);
		getBillingDelegate(document).validate(document);
	}

	private void recallPayment(GateDocument document) throws GateException, GateLogicException
	{
		UpdateDocumentService updateService = getFactory().service(UpdateDocumentService.class);
		try
		{
			getABSDelegate(document).recall(document);
			UpdateDocumentService updateDocumentService = getFactory().service(UpdateDocumentService.class);
			SynchronizableDocument synchronizableDocument = updateDocumentService.find(String.valueOf(((AbstractPaymentSystemPayment) document).getExternalId()));
			updateDocumentService.update(synchronizableDocument, new DocumentCommand(DocumentEvent.REFUSE, new HashMap<String, Object>()));
		}
		catch (GateWrapperTimeOutException e)
		{
			updateService.update((SynchronizableDocument) document, new DocumentCommand(DocumentEvent.ABS_RECALL_TIMEOUT, new HashMap<String, Object>()));
		}
		catch (GateTimeOutException e)
		{
			updateService.update((SynchronizableDocument) document, new DocumentCommand(DocumentEvent.ABS_GATE_RECALL_TIMEOUT, new HashMap<String, Object>()));
		}
	}

	/**
	 * Получить биллингового делегата сервиса
	 * @param transfer перевод
	 * @return делегат
	 * @throws GateException
	 */
	private DocumentService getBillingDelegate(GateDocument transfer) throws GateException, GateLogicException
	{
		return getDelegateFactory(((AbstractPaymentSystemPayment) transfer).getReceiverPointCode()).service(DocumentService.class);
	}

	/**
	 * получить АБС-ного делегата сервиса
	 * @param document перевод
	 * @return делегат
	 * @throws GateException
	 * @throws GateLogicException
	 */
	private DocumentService getABSDelegate(GateDocument document) throws GateException, GateLogicException
	{
		return getDelegateFactory(document).service(DocumentService.class);
	}

	public StateUpdateInfo execute(GateDocument document) throws GateException, GateLogicException
	{
		return getABSDelegate(document).update(document);
	}
}
