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
	private static final String ERROR_MESSAGE = "�� ��������� ����������� �������� �������� � ������ � ����� �������� %s � ����� ������ %s";
	private static final String CURRENCY_CODE_RUB = "RUB";
	private static final String CURRENCY_CODE_USD = "USD";
	private static final String UNKNOWN_CODE = "0";

	public ConsiderTariffCommissionCalculator(GateFactory factory)
	{
		super(factory);
	}

	public void setParameters(Map<String, ?> params)
	{
		// ���������� ���
	}


	public void calcCommission(GateDocument transfer) throws GateException, GateLogicException
	{
		if(transfer.getType() != CardIntraBankPayment.class && transfer.getType() != CardRUSPayment.class)
			throw new GateException("�������� CardIntraBankPayment ��� CardRUSPayment");

		BackRefCommissionTariffService backRefService = getFactory().service(BackRefCommissionTariffService.class);

		AbstractPhizTransfer payment = (AbstractPhizTransfer) transfer;
		Money operationAmount = payment.getDestinationAmount();
		TransferType transferType = getTransferType(payment);

		//���� ����� � ����� ��, �� �������� �� ���������.
		if (transferType != TransferType.OTHER_BANK && PaymentSenderBase.isSameTB(transfer.getOffice(), payment.getReceiverAccount()))
			return;

		CommissionTariff tariff = backRefService.getTariff(
				CurrencyHelper.getCurrencyCode(operationAmount.getCurrency().getCode()), transferType);
		// ���� ��� ������ �������� ����� �����, ������� �������� �������������� �������� ������
		if(tariff != null)
		{
			Money commissionValue = calcCommFromSpecTariff(tariff, payment);
			// �� ������ ���������
			if(commissionValue == null)
				throw new GateException(String.format(ERROR_MESSAGE, tariff.getTransferType(), tariff.getCurrencyCode()));

			payment.setCommission(commissionValue);
			return;
		}

		// ���� �� ������ ������ � ������ �������� ����� ��� �������  - ������� �������� ������
		if(CURRENCY_CODE_RUB.equals(CurrencyHelper.getCurrencyCode(operationAmount.getCurrency().getCode()))
				|| CURRENCY_CODE_USD.equals(operationAmount.getCurrency().getCode()))
		{
			return;
		}

		// ���� ������ �������� �� �������� � �� ������ � �� ������ ������ ��� ������ ������� - ������� �������� ������
		CommissionTariff usdTariff = backRefService.getTariff(CURRENCY_CODE_USD, transferType);
		if(usdTariff == null)
		{
			return;
		}
		
		// ����� ��������� �������� ������������ ������ ��� USD
		Money commissionValue = calcCommFromSpecTariff(usdTariff, payment);
		// �� ������ ���������
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

		throw new GateException("������ �������� ������ ��� �������� � ����� �� ���� ���. ���� � ������ �� ��� � ������ �����.");
	}

	private boolean equalFullName(AbstractPhizTransfer payment) throws GateLogicException, GateException
	{
		String fullPayerName = payment.getPayerName();
		if (fullPayerName == null)
			return false;
		//��������� ���������� ���
		String[] nameParams = fullPayerName.split(" ");
		if (!nameParams[1].equals(payment.getReceiverFirstName()))
			return false;

		if (!nameParams[0].equals(payment.getReceiverSurName()))
			return false;
		//���� �� ������ ��������(��� � �������-������������ ���������)
		if (nameParams.length < 3)
			return true;

		// ���� � ����������� � ���������� �������� �������� �� ��������� ��� ��
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
		// ���� ������ �����, �������� � ���������� �� �����
		if(specTariffCurency.compare(operationAmount.getCurrency()))
		{
			return calcValueComm(tariff.getPercent(), tariff.getMinAmount(), tariff.getMaxAmount(), operationAmount);
		}

		Currency rubCurency = currencyService.getNationalCurrency();

		// ���� ������������� ������ � �����
		CurrencyRate specToRubRate = currencyRateService.getRate(specTariffCurency, rubCurency, CurrencyRateType.CB, transfer.getOffice(), UNKNOWN_CODE);
		// ���� ������ �������� � �����
		CurrencyRate operCurToRubRate = currencyRateService.getRate(operationAmount.getCurrency(), rubCurency, CurrencyRateType.CB, transfer.getOffice(), UNKNOWN_CODE);

		// ��������� ����� ������� ������ ������������ �����
		BigDecimal rurForOneOperCur = operCurToRubRate.getToValue().divide(
				operCurToRubRate.getFromValue(), CurrencyRate.ROUNDING_SCALE, CurrencyRate.ROUNDING_MODE);
		// ��������� ������� ������������ �����
		BigDecimal rurForOneSpecCur = specToRubRate.getToValue().divide(
				specToRubRate.getFromValue(), CurrencyRate.ROUNDING_SCALE, CurrencyRate.ROUNDING_MODE);

		// ������� ����������� ����� ��������� � ������ ��������
		BigDecimal minAmount = tariff.getMinAmount() != null ? tariff.getMinAmount().multiply(
				rurForOneSpecCur.divide(rurForOneOperCur, CurrencyRate.ROUNDING_SCALE, CurrencyRate.ROUNDING_MODE)) : null;

		// ������� ������������ ����� ��������� � ������ ��������
		BigDecimal maxAmount = tariff.getMaxAmount() != null ? tariff.getMaxAmount().multiply(
				rurForOneSpecCur.divide(rurForOneOperCur, CurrencyRate.ROUNDING_SCALE, CurrencyRate.ROUNDING_MODE)) : null;

		return calcValueComm(tariff.getPercent(), minAmount, maxAmount, operationAmount);
	}

	private Money calcValueComm(BigDecimal percent, BigDecimal minAmount, BigDecimal maxAmount, Money operationAmount) throws GateException
	{
		// ���� ����� �������, ��������� �� ����, � ������ ����������� � ������������ �����, ���� ����
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

		// ���� ������ ������ ����������� �����, ����� ��
		if(minAmount != null && maxAmount == null)
			return new Money(minAmount, operationAmount.getCurrency());

		// ���� ������ ������ ������������ �����, ����� ��
		if(minAmount == null && maxAmount != null)
			return new Money(maxAmount, operationAmount.getCurrency());

		// �� ������ ���������, ������ �� ����������
		return null;
	}
}
