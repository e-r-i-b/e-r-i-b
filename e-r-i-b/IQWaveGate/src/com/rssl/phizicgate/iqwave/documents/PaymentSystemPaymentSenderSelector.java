package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizgate.common.payments.documents.AbstractDocumentSenderBase;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.DocumentSender;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.utils.ClassHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author krenev
 * @ created 13.05.2010
 * @ $Author$
 * @ $Revision$
 * Sender биллингового платежа.
 * Выбирает по поставщику нужный сендерю если не задан юзает дефолтный
 * (типовая оплата без преобразования информации о плательщике)
 */
public class PaymentSystemPaymentSenderSelector extends AbstractDocumentSenderBase
{
	private static final String DEFAULT_SENDER_PARAMETER_NAME = "default-sender";
	private static final String CUSTOM_SENDER_PARAMETER_PREFIX = "sender-for-";
	private static final Map<String, DocumentSender> senders = new HashMap<String, DocumentSender>();
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock readLock = rwl.readLock();
	private final Lock writeLock = rwl.writeLock();
	private DocumentSender defaultSender;

	/**
	 * ctor
	 * @param factory гейтовая фабрика
	 */
	public PaymentSystemPaymentSenderSelector(GateFactory factory)
	{
		super(factory);
	}

	public void setParameters(Map<String, ?> params)
	{
		//получаем деволтный сендер
		String className = (String) params.remove(DEFAULT_SENDER_PARAMETER_NAME);
		try
		{
			if (className == null)
			{
				throw new GateException("не задан дефолтный сендер");
			}
			defaultSender = loadSender(className);
		}
		catch (GateException e)
		{
			throw new RuntimeException(e);
		}

		super.setParameters(params);
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		getDelegateSender(document).send(document);

		addOfflineDocumentInfo(document);
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		getDelegateSender(document).repeatSend(document);
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		getDelegateSender(document).prepare(document);
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		getDelegateSender(document).rollback(document);
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		getDelegateSender(document).confirm(document);
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		getDelegateSender(document).validate(document);
	}

	/**
	 * получить конкретный сендер по платежу
	 * @param document платед
	 * @return документ
	 * @throws GateException
	 */
	private DocumentSender getDelegateSender(GateDocument document) throws GateException
	{
		boolean isWithdraw = document.getType() == WithdrawDocument.class;
		if (!(isWithdraw && ((WithdrawDocument)document).getWithdrawType() == CardPaymentSystemPayment.class) && document.getType() != CardPaymentSystemPayment.class)
		{
			throw new GateException("Неверный тип платежа - ожидается CardPaymentSystemPayment или WithdrawDocument");
		}
		CardPaymentSystemPayment payment = isWithdraw ? (CardPaymentSystemPayment) ((WithdrawDocument)document).getTransferPayment()
													  : (CardPaymentSystemPayment) document;
		String pointCode = payment.getReceiverPointCode();
		//смотрим не создавали мы для такого поставщика сендер
		DocumentSender sender = getFromCache(pointCode);
		if (sender != null)
		{
			return sender;
		}
		//не создан - создаем.
		String senderClass = (String) getParameter(CUSTOM_SENDER_PARAMETER_PREFIX + pointCode);
		if (senderClass == null)
		{
			return defaultSender; // специального сендера для этого поставщика нет - юзаем дефолтный
		}
		sender = loadSender(senderClass);
		putToCache(pointCode, sender);
		return sender;
	}

	private DocumentSender loadSender(String className) throws GateException
	{
		try
		{
			Class<DocumentSender> senderClass = ClassHelper.loadClass(className);
			return senderClass.getConstructor(GateFactory.class).newInstance(getFactory());
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private DocumentSender getFromCache(String pointCode)
	{
		readLock.lock();
		try
		{
			return senders.get(pointCode);
		}
		finally
		{
			readLock.unlock();
		}
	}

	private DocumentSender putToCache(String pointCode, DocumentSender sender)
	{
		writeLock.lock();
		try
		{
			return senders.put(pointCode, sender);
		}
		finally
		{
			writeLock.unlock();
		}
	}
}
