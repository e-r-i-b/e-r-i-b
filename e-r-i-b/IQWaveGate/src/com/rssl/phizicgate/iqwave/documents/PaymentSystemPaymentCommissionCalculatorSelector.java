package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizgate.common.payments.AbstractCommissionCalculator;
import com.rssl.phizic.gate.documents.CommissionCalculator;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.utils.ClassHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author khudyakov
 * @ created 21.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class PaymentSystemPaymentCommissionCalculatorSelector extends AbstractCommissionCalculator
{
	private static final String DEFAULT_CALCULATOR_NAME = "default";
	private static final String GKH_CALCULATOR_NAME     = "10";

	private static final Map<String, CommissionCalculator> calculators = new HashMap<String, CommissionCalculator>();

	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock readLock  = rwl.readLock();
	private final Lock writeLock = rwl.writeLock();


	public PaymentSystemPaymentCommissionCalculatorSelector() throws GateException
	{
		putToCache(DEFAULT_CALCULATOR_NAME, loadCalculator("com.rssl.phizgate.common.payments.DefaultBackRefCommissionCalculator"));
		putToCache(GKH_CALCULATOR_NAME,     loadCalculator("com.rssl.phizgate.common.payments.DefaultCommissionCalculator"));
	}

	public void calcCommission(GateDocument transfer) throws GateException, GateLogicException
	{
		if (transfer.getType() != CardPaymentSystemPayment.class)
			throw new GateException("Неверный тип документа, ожидался CardPaymentSystemPayment");

		AccountPaymentSystemPayment payment = (AccountPaymentSystemPayment) transfer;

		CommissionCalculator calculator = getFromCache(payment.getReceiverPointCode().equals(GKH_CALCULATOR_NAME) ? GKH_CALCULATOR_NAME : DEFAULT_CALCULATOR_NAME);
		calculator.calcCommission(transfer);
	}

	private static CommissionCalculator loadCalculator(String className) throws GateException
	{
		try
		{
			Class<CommissionCalculator> calculatorClass = ClassHelper.loadClass(className);
			return calculatorClass.newInstance();
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private CommissionCalculator getFromCache(String pointCode)
	{
		readLock.lock();
		try
		{
			return calculators.get(pointCode);
		}
		finally
		{
			readLock.unlock();
		}
	}

	private void putToCache(String pointCode, CommissionCalculator calculator)
	{
		writeLock.lock();
		try
		{
			calculators.put(pointCode, calculator);
		}
		finally
		{
			writeLock.unlock();
		}
	}
}
