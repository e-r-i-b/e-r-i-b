package com.rssl.phizic.business.rates;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.documents.AccountClosingPayment;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.ExchangeCurrencyTransferBase;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.common.types.DynamicExchangeRate;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.util.ApplicationUtil;

import java.math.BigDecimal;

/**
 * @author gulov
 * @ created 30.11.2010
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Класс helper для курсов валют
 */
public class CurrencyRateHelper
{
	public static final String RATE_CHANGED_MESSAGE_KEY = "RATE_CHANGED_MESSAGE_KEY";

	private static final String WARNING_MESSAGE_FOR_USER = "Курс валюты банка изменился! Пожалуйста, отредактируйте платеж.";
	private static final String METAL_COURSE_MESSAGE = "Курс покупки/продажи металла изменился. Вам необходимо создать новый платеж.";
    private static final String BUY_AMOUNT_CHANGED_MESSAGE = "Обратите внимание: изменился курс конверсии банка, поэтому пересчитана сумма зачисления.";
    private static final String SELL_AMOUNT_CHANGED_MESSAGE = "Обратите внимание: изменился курс конверсии банка, поэтому пересчитана сумма списания.";
	private static final DepartmentService departmentService = new DepartmentService();
	/**
	 * Определяет динамику изменения курсов валют по объектам CurrencyRate
	 * @param oldRate - курс, относительно которого определяется динамика изменения
	 * @param newRate - курс, для которого необходимо определить динамику изменения
	 * @return - динамика изменения курсов валют
	 */
	static DynamicExchangeRate getDynamicExchangeRate(Rate oldRate, Rate newRate)
	{
		return getDynamicExchangeRate(makeCurrencyRate(oldRate), makeCurrencyRate(newRate));
	}

	/**
	 * Сформировать объект CurrencyRate из объекта Rate
	 * @param rate - курс из таблицы
	 * @return - курс валюты
	 */
	public static CurrencyRate makeCurrencyRate(Rate rate)
	{
		TariffPlanHelper tariffPlanHelper = new TariffPlanHelper();
		return rate == null ? null: new CurrencyRate(rate.getFromCurrency(), rate.getFromValue(),
			rate.getToCurrency(), rate.getToValue(), rate.getCurrencyRateType(), tariffPlanHelper.getCodeBySynonym(rate.getTarifPlanCodeType()), rate.getExpireDate());
	}

	private static DynamicExchangeRate getDynamicExchangeRate(CurrencyRate oldCurrencyRate, CurrencyRate newCurrencyRate)
	{
		if (oldCurrencyRate == null || newCurrencyRate == null)
			return DynamicExchangeRate.NONE;

		int cmp = compareRates(newCurrencyRate, oldCurrencyRate);
		if (cmp > 0)
			return DynamicExchangeRate.UP;
		if (cmp < 0)
			return DynamicExchangeRate.DOWN;
		return DynamicExchangeRate.NONE;
	}

	/**
	 * Сравнивает два курса валют в предположении, что курсы относятся к одной валюте.
	 * @param rate1 - курс 1 (never null)
	 * @param rate2 - курс 2 (never null)
	 * @return 0 = курсы равны, >0 = <rate1> > <rate2>, <0 = <rate1> < <rate2>
	 */
	private static int compareRates(CurrencyRate rate1, CurrencyRate rate2)
	{
		// num(numerator) - числитель, denom(denominator) - знаменатель дроби
		BigDecimal num1     = rate1.getToValue();
		BigDecimal denom1   = rate1.getFromValue();
		BigDecimal num2     = rate2.getToValue();
		BigDecimal denom2   = rate2.getFromValue();

		// Сравниваем дроби rate1 и rate2 избегая операции деления
		// результат сравнения (?) в выражении
		//   num1/denom1 ? num2/denom2
		// равен результату сравнения в выражении
		//   num1*denom2 ? num2*denom1
		BigDecimal mul1 = num1.multiply(denom2);
		BigDecimal mul2 = num2.multiply(denom1);
		return mul1.compareTo(mul2);
	}

	/**
	 * Обработка события изменения курсов валют (для V6)
	 * @param document - документ
	 * @param stateMachineEvent событие
	 */
	public static void processRateChangeEvent(StateObject document, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (!(document instanceof ExchangeCurrencyTransferBase))
			return;

		try
		{
			ExchangeCurrencyTransferBase transfer = (ExchangeCurrencyTransferBase) document;

			CurrencyRate currencyRate = new CurrencyRate(transfer.getChargeOffCurrency(), BigDecimal.ONE,
					transfer.getDestinationCurrency(), BigDecimal.ONE, CurrencyRateType.BUY_REMOTE, transfer.getTarifPlanCodeType());
			CacheService cacheService = GateSingleton.getFactory().service(CacheService.class);
			cacheService.clearCurrencyRateCache(currencyRate, transfer.getOffice());

			try
			{
				transfer.calcConvertionRates();
			}
			catch (DocumentLogicException e)
			{
				throw new BusinessException(e);
			}
			throw new BusinessLogicException(getRateChangeMessage(document, stateMachineEvent));
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	/**
	 * выкидывает сообщение об изменении курсов валют
	 * @param document - документ
	 * @param stateMachineEvent событие
	 */
	public static String getRateChangeMessage(StateObject document, StateMachineEvent stateMachineEvent)
	{
		if (!(document instanceof ExchangeCurrencyTransferBase))
			return null;

		ExchangeCurrencyTransferBase payment = (ExchangeCurrencyTransferBase) document;
            final boolean isMobileApi = ApplicationUtil.isMobileApi();

            if (!isMobileApi && (payment.getChargeOffResourceType() == ResourceType.IM_ACCOUNT ||
					payment.getDestinationResourceType() == ResourceType.IM_ACCOUNT))
			{
				return METAL_COURSE_MESSAGE;
			}

            if (isMobileApi)
            {
                InputSumType inputSumType = payment.getInputSumType();
                if (InputSumType.CHARGEOFF == inputSumType)
                {
                    stateMachineEvent.registerChangedField(PaymentFieldKeys.CONVERSION_RATE, null, null, BUY_AMOUNT_CHANGED_MESSAGE);
                    final String destinationAmountField;
                    if (document instanceof AccountClosingPayment)
                        destinationAmountField = "destinationAmount";
                    else
                        destinationAmountField = PaymentFieldKeys.BUY_AMOUNT;
                    stateMachineEvent.registerChangedField(destinationAmountField, null, null, BUY_AMOUNT_CHANGED_MESSAGE);
                }
                else if (InputSumType.DESTINATION == inputSumType)
                {
                    stateMachineEvent.registerChangedField(PaymentFieldKeys.CONVERSION_RATE, null, null, SELL_AMOUNT_CHANGED_MESSAGE);
                    stateMachineEvent.registerChangedField(PaymentFieldKeys.SELL_AMOUNT, null, null, SELL_AMOUNT_CHANGED_MESSAGE);
                }
            }

			return WARNING_MESSAGE_FOR_USER;
	}
}
