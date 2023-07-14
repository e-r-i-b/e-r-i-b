package com.rssl.phizicgate.esberibgate.documents.services.PaymentSystemPayment;

import com.rssl.phizgate.common.payments.RestrictionSender;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.AbstractService;
import com.rssl.phizicgate.esberibgate.documents.commissionCalculators.PaymentSystemPayment.CardPaymentSystemPaymentCommissionCalculator;
import com.rssl.phizicgate.esberibgate.documents.senders.PaymentSystemPayment.CardPaymentSystemPaymentSender;

import java.util.Collections;

/**
 * @author akrenev
 * @ created 09.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Сервис платежа "Оплата товаров и услуг с карты."
 */

public class CardPaymentSystemPaymentService extends AbstractService implements DocumentService
{
	private final CommissionCalculator commissionCalculator;
	private final DocumentSender sender;
	private final DocumentUpdater updater;

	/**
	 * конструктор
	 * @param factory фабрика гейта
	 */
	public CardPaymentSystemPaymentService(GateFactory factory)
	{
		super(factory);
		commissionCalculator = new CardPaymentSystemPaymentCommissionCalculator();
		sender = new CardPaymentSystemPaymentSender(factory);
		updater = new RestrictionSender(factory);
		updater.setParameters(Collections.singletonMap("message", "Обновление документа не поддерживается."));
	}

	private CommissionCalculator getCommissionCalculator()
	{
		return commissionCalculator;
	}

	private DocumentSender getDocumentSender()
	{
		return sender;
	}

	private DocumentUpdater getDocumentUpdater()
	{
		return updater;
	}

	public void calcCommission(GateDocument document) throws GateException, GateLogicException
	{
		getCommissionCalculator().calcCommission(document);
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		getDocumentSender().send(document);
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		getDocumentSender().repeatSend(document);
	}

	public StateUpdateInfo update(GateDocument document) throws GateException, GateLogicException
	{
		return getDocumentUpdater().execute(document);
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		getDocumentSender().prepare(document);
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		getDocumentSender().confirm(document);
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		getDocumentSender().validate(document);
	}

	public void recall(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Отзыв документа не поддерживается.");
	}
}
