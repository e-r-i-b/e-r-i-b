package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.common.types.commission.WriteDownOperationHelper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author krenev
 * @ created 13.09.2010
 * @ $Author$
 * @ $Revision$
 * ������� ����� ��� ������� ������������� ����������
 */

public abstract class ConvertionSenderBase extends PaymentSenderBase
{

	public static final String DISCOUNT_COMMISSION_PREFIX = "��� �� ";

	public ConvertionSenderBase(GateFactory factory) throws GateException
	{
		super(factory);
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		super.validate(document);
		if (!(document instanceof AbstractTransfer))
		{
			throw new GateException("�������� ��� ���������, ������ ���� - AbstractTransfer.");
		}
		AbstractTransfer transfer = (AbstractTransfer) document;
		if (!isConvertion(transfer) || !needRates(transfer) || isLongOfferMode())
		{
			return;
		}
		//���������, ��� ���� ��� �����
		if (transfer.getCreditBuyRate() == null)
		{
			throw new TemporalGateException("�� ����� CreditBuyRate");
		}
		if (transfer.getCreditSaleRate() == null)
		{
			throw new TemporalGateException("�� ����� CreditSaleRate");
		}
		if (transfer.getDebetBuyRate() == null)
		{
			throw new TemporalGateException("�� ����� DebetBuyRate");
		}
		if (transfer.getDebetSaleRate() == null)
		{
			throw new TemporalGateException("�� ����� DebetSaleRate");
		}
	}

	/**
	 * ���������� �� ������� ������ ��� ������� ��������
	 * ������������ �������� ��������(��������)
	 * @param transfer �������
	 * @return ��/���
	 */
	protected abstract boolean needRates(AbstractTransfer transfer);

	/**
	 * ��������� �������� �� ������� ����������.
	 * @param transfer �������
	 * @return ��/���
	 */
	protected boolean isConvertion(AbstractTransfer transfer) throws GateException, GateLogicException
	{
		Currency chargeOffCurrency = getChargeOffCurrency(transfer);
		Currency destinationCurrency = getDestinationCurrency(transfer);
		return !chargeOffCurrency.compare(destinationCurrency);
	}

	protected XferInfo_Type createBody(AbstractTransfer transfer) throws GateException, GateLogicException
	{
		XferInfo_Type xferInfo = new XferInfo_Type();
		xferInfo.setCurAmtConv(getCurAmtConvType(transfer));

		return xferInfo;
	}

	protected void fillCommissions(GateDocument document, IFXRs_Type ifxRs) throws GateLogicException, GateException
	{
		SrcLayoutInfo_Type srcLayoutInfo_type = getSrcLayoutInfo(ifxRs);
		List<WriteDownOperation> result = new ArrayList<WriteDownOperation>();
		AbstractTransfer transfer = (AbstractTransfer) document;
		OperName_Type operationNameType = getOperationName(transfer);
		com.rssl.phizic.common.types.Currency commissionCurrency = getChargeOffCurrency(transfer);
		if (srcLayoutInfo_type != null)
		{
			WriteDownOperation_Type[] commissions = srcLayoutInfo_type.getWriteDownOperation();
			if (commissions != null)
			{
				for (WriteDownOperation_Type microOperation : commissions)
				{
					WriteDownOperation op = new WriteDownOperation();
					String operationName = microOperation.getOperationName();
					op.setOperationName(operationName);
					op.setCurAmount(new Money(microOperation.getCurAmt(), commissionCurrency));
					op.setTurnOver(microOperation.getTurnover().getValue());
					result.add(op);
					if (!StringHelper.isEmpty(operationName) && operationName.contains(DISCOUNT_COMMISSION_PREFIX) &&
							(operationNameType.equals(OperName_Type.TDC) || operationNameType.equals(OperName_Type.TCD) || operationNameType.equals(OperName_Type.TDD) || operationNameType.equals(OperName_Type.TCP)))
					{
						int prefixIndex = operationName.indexOf(DISCOUNT_COMMISSION_PREFIX);
						transfer.setTariffPlanESB(operationName.substring(prefixIndex + DISCOUNT_COMMISSION_PREFIX.length()));
					}

				}
			}
		}
		transfer.setWriteDownOperations(result);
		transfer.setCommission(WriteDownOperationHelper.getCommissionsSum(result));
	}

	protected CurAmtConv_Type getCurAmtConvType(AbstractTransfer transfer)  throws GateException, GateLogicException
	{
		if (needRates(transfer) && isConvertion(transfer) && !isLongOfferMode())
		{
			CurrencyRate debetSaleRate  = transfer.getDebetSaleRate();
			CurrencyRate debetBuyRate   = transfer.getDebetBuyRate();
			CurrencyRate creditSale = transfer.getCreditSaleRate();
			CurrencyRate creditBuy  = transfer.getCreditBuyRate();

			CurAmtConv_Type curAmtConv = new CurAmtConv_Type();
			//����� �������� � �������� ��������, � �� 1.
			curAmtConv.setDebet_sale(debetSaleRate != null ? debetSaleRate.getToValue() : null);
			curAmtConv.setDebet_buy(debetBuyRate != null ? debetBuyRate.getToValue() : null);
			curAmtConv.setCredit_sale(creditSale != null ? creditSale.getToValue() : null);
			curAmtConv.setCredit_buy(creditBuy != null ? creditBuy.getToValue() : null);

			return curAmtConv;
		}
		return null;
	}

	/**
	 * �������� ������ ��������� ����������. ����� ���������� null ���� �������� ���������� �����������.
	 * @param transfer �������
	 * @return ������
	 */
	protected abstract Currency getDestinationCurrency(AbstractTransfer transfer) throws GateException, GateLogicException;

	/**
	 * �������� ������ ��������� ��������
	 * @param transfer �������
	 * @return ������
	 */
	public abstract Currency getChargeOffCurrency(AbstractTransfer transfer) throws GateException, GateLogicException;
}
