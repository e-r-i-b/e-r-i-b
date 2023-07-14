package com.rssl.phizicgate.esberibgate.documents;

import com.rssl.phizic.common.types.*;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.BackRefClientService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.commission.BackRefCommissionTariffService;
import com.rssl.phizic.gate.commission.CommissionTariff;
import com.rssl.phizic.gate.commission.TransferType;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.documents.CommissionCalculator;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.payments.AbstractPhizTransfer;
import com.rssl.phizic.gate.payments.CardIntraBankPayment;
import com.rssl.phizic.gate.payments.CardRUSPayment;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.documents.senders.PaymentSenderBase;
import com.rssl.phizicgate.esberibgate.types.CurrencyHelper;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author niculichev
 * @ created 23.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class ConsiderTariffCommissionCalculator extends AbstractService implements CommissionCalculator
{
	private static final String ERROR_MESSAGE = "Не правильно установлены значения коммисии в тарифе в типам перевода %s и кодом валюты %s";
	private static final String CURRENCY_CODE_RUB = "RUB";
	private static final String CURRENCY_CODE_USD = "USD";
	private static final String UNKNOWN_CODE = "0";

	public ConsiderTariffCommissionCalculator(GateFactory factory)
	{
		super(factory);
	}

	public void setParameters(Map<String, ?> params)
	{
		// параметров нет
	}


	public void calcCommission(GateDocument transfer) throws GateException, GateLogicException
	{
		if(transfer.getType() != CardIntraBankPayment.class && transfer.getType() != CardRUSPayment.class)
			throw new GateException("Ожидался CardIntraBankPayment или CardRUSPayment");

		BackRefCommissionTariffService backRefService = getFactory().service(BackRefCommissionTariffService.class);

		AbstractPhizTransfer payment = (AbstractPhizTransfer) transfer;
		Money operationAmount = payment.getDestinationAmount();
		TransferType transferType = getTransferType(payment);

		//если счета в одном ТБ, то коммисию не начисляем.
		if (transferType != TransferType.OTHER_BANK && PaymentSenderBase.isSameTB(transfer.getOffice(), payment.getReceiverAccount()))
			return;

		CommissionTariff tariff = backRefService.getTariff(
				CurrencyHelper.getCurrencyCode(operationAmount.getCurrency().getCode()), transferType);
		// если для валюты операции задан тариф, рассчет комиссии осуществляется согласно тарифу
		if(tariff != null)
		{
			Money commissionValue = calcCommFromSpecTariff(tariff, payment);
			// не смогли посчитать
			if(commissionValue == null)
				throw new GateException(String.format(ERROR_MESSAGE, tariff.getTransferType(), tariff.getCurrencyCode()));

			payment.setCommission(commissionValue);
			return;
		}

		// если не заданы тарифы и валюта операции рубли или доллары  - прежний алгоритм работы
		if(CURRENCY_CODE_RUB.equals(CurrencyHelper.getCurrencyCode(operationAmount.getCurrency().getCode()))
				|| CURRENCY_CODE_USD.equals(operationAmount.getCurrency().getCode()))
		{
			return;
		}

		// если валюта отличная от долларов и от рублей и не заданы тарифы для валюты доллара - прежний алгоритм работы
		CommissionTariff usdTariff = backRefService.getTariff(CURRENCY_CODE_USD, transferType);
		if(usdTariff == null)
		{
			return;
		}
		
		// иначе вычисляем коммисию относительно тарифа для USD
		Money commissionValue = calcCommFromSpecTariff(usdTariff, payment);
		// не смогли посчитать
		if(commissionValue == null)
			throw new GateException(String.format(ERROR_MESSAGE, tariff.getTransferType(), tariff.getCurrencyCode()));

		payment.setCommission(commissionValue);
	}

	private TransferType getTransferType(AbstractPhizTransfer transfer) throws GateException, GateLogicException
	{
		if(transfer.getType() == CardRUSPayment.class)
		{
			return TransferType.OTHER_BANK;
		}

		if(transfer.getType() == CardIntraBankPayment.class)
		{
			return  equalFullName(transfer) ? TransferType.OTHER_TB_OWN_ACCOUNT : TransferType.OTHER_TB_ANOTHER_ACCOUNT;
		}

		throw new GateException("Тарифы комиссий только для перевода с карты на счет физ. лица в другом ТБ или в другом Банке.");
	}

	private boolean equalFullName(AbstractPhizTransfer payment) throws GateLogicException, GateException
	{
		String fullPayerName = payment.getPayerName();
		if (fullPayerName == null)
			return false;
		//проверяем совпадение ФИО
		String[] nameParams = fullPayerName.split(" ");
		if (!nameParams[1].equals(payment.getReceiverFirstName()))
			return false;

		if (!nameParams[0].equals(payment.getReceiverSurName()))
			return false;
		//если не задано отчества(имя и фамилия-обязательные параметры)
		if (nameParams.length < 3)
			return true;

		// если у отправителя и получателя заполены отчества то проверяем еще их
		if (!StringHelper.isEmpty(nameParams[2])
				&& (!StringHelper.isEmpty(payment.getReceiverPatrName())))
			return nameParams[2].equals(payment.getReceiverPatrName());

		return true;
	}

	private Money calcCommFromSpecTariff(CommissionTariff tariff, AbstractPhizTransfer transfer) throws GateException, GateLogicException
	{
		CurrencyRateService currencyRateService = getFactory().service(CurrencyRateService.class);
		CurrencyService currencyService = getFactory().service(CurrencyService.class);

		Money operationAmount = transfer.getDestinationAmount();
		Currency specTariffCurency = currencyService.findByAlphabeticCode(tariff.getCurrencyCode());
		// если валюты равны, алгоритм с конверсией не нужен
		if(specTariffCurency.compare(operationAmount.getCurrency()))
		{
			return calcValueComm(tariff.getPercent(), tariff.getMinAmount(), tariff.getMaxAmount(), operationAmount);
		}

		Currency rubCurency = currencyService.getNationalCurrency();

		// курс относительной валюты к рублю
		CurrencyRate specToRubRate = currencyRateService.getRate(specTariffCurency, rubCurency, CurrencyRateType.CB, transfer.getOffice(), UNKNOWN_CODE);
		// курс валюты операции к рублю
		CurrencyRate operCurToRubRate = currencyRateService.getRate(operationAmount.getCurrency(), rubCurency, CurrencyRateType.CB, transfer.getOffice(), UNKNOWN_CODE);

		// стоимость одной единицы валюты относительно рубля
		BigDecimal rurForOneOperCur = operCurToRubRate.getToValue().divide(
				operCurToRubRate.getFromValue(), CurrencyRate.ROUNDING_SCALE, CurrencyRate.ROUNDING_MODE);
		// стоимость доллара относительно рубля
		BigDecimal rurForOneSpecCur = specToRubRate.getToValue().divide(
				specToRubRate.getFromValue(), CurrencyRate.ROUNDING_SCALE, CurrencyRate.ROUNDING_MODE);

		// находим минимальную сумму коммиссии в валюте операции
		BigDecimal minAmount = tariff.getMinAmount() != null ? tariff.getMinAmount().multiply(
				rurForOneSpecCur.divide(rurForOneOperCur, CurrencyRate.ROUNDING_SCALE, CurrencyRate.ROUNDING_MODE)) : null;

		// находим максимальную сумму коммиссии в валюте операции
		BigDecimal maxAmount = tariff.getMaxAmount() != null ? tariff.getMaxAmount().multiply(
				rurForOneSpecCur.divide(rurForOneOperCur, CurrencyRate.ROUNDING_SCALE, CurrencyRate.ROUNDING_MODE)) : null;

		return calcValueComm(tariff.getPercent(), minAmount, maxAmount, operationAmount);
	}

	private Money calcValueComm(BigDecimal percent, BigDecimal minAmount, BigDecimal maxAmount, Money operationAmount) throws GateException
	{
		// если задан процент, вычисляем по нему, с учетом минимальной и максимальной суммы, если есть
		if(percent != null)
		{
			BigDecimal commission = operationAmount.getDecimal().multiply(percent.divide(new BigDecimal(100)));
			if(maxAmount != null && commission.compareTo(maxAmount) > 0)
			{
				commission = maxAmount;
			}
			else if(minAmount != null && commission.compareTo(minAmount) < 0)
			{
				commission = minAmount;
			}

			return new Money(commission, operationAmount.getCurrency());
		}

		// если задана только минимальная сумма, берем ее
		if(minAmount != null && maxAmount == null)
			return new Money(minAmount, operationAmount.getCurrency());

		// если заданы только максимальная сумма, берем ее
		if(minAmount == null && maxAmount != null)
			return new Money(maxAmount, operationAmount.getCurrency());

		// не смогли посчитать, ничего не возвращаем
		return null;
	}
}
