package com.rssl.phizic.gate.templates.impl;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.gate.documents.CommissionOptions;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.gate.documents.attribute.ExtendedAttribute;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.money.CurrencyImpl;
import com.rssl.phizic.gate.payments.ReceiverCardType;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.BooleanUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * ������� ����� �������� ����������
 *
 * @author khudyakov
 * @ created 17.04.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class TransferTemplateBase extends PaymentTemplateBase
{
	private String chargeOffResource;               //������ ��������
	private String destinationResource;             //������ ����������
	private InputSumType inputSumType;              //��� �����
	private Money chargeOffAmount;                  //����� � ������ ����� ��������
	private Money destinationAmount;                //����� � ������ ����� ����������
	private String ground;                          //���������� ������� �������


	public String getChargeOffAccount()
	{
		return getChargeOffResource();
	}

	public void setChargeOffAccount(String chargeOffAccount)
	{
		setChargeOffResource(chargeOffAccount);
	}

	public String getChargeOffCard()
	{
		return getChargeOffResource();
	}

	public void setChargeOffCard(String chargeOffCard)
	{
		setChargeOffResource(chargeOffCard);
	}

	public String getChargeOffResource()
	{
		return chargeOffResource;
	}

	public void setChargeOffResource(String chargeOffResource)
	{
		this.chargeOffResource = chargeOffResource;
	}

	public String getDestinationResource()
	{
		return destinationResource;
	}

	public void setDestinationResource(String destinationResource)
	{
		this.destinationResource = destinationResource;
	}

	public String getReceiverAccount()
	{
		return getDestinationResource();
	}

	public void setReceiverAccount(String receiverAccount)
	{
		setDestinationResource(receiverAccount);
	}

	public String getReceiverCard()
	{
		return getDestinationResource();
	}

	public void setReceiverCard(String receiverCard)
	{
		setDestinationResource(receiverCard);
	}

	public Calendar getReceiverCardExpireDate()
	{
		//�� ������������ � ����
		return null;
	}

	public ReceiverCardType getReceiverCardType()
	{
		//�� ������������ � ����
		return null;
	}

	protected ResourceType getChargeOffResourceType()
	{
		return ResourceType.fromValue(getNullSaveAttributeStringValue(Constants.FROM_RESOURCE_TYPE_ATTRIBUTE_NAME));
	}

	protected ResourceType getDestinationResourceType()
	{
		return ResourceType.fromValue(getNullSaveAttributeStringValue(Constants.TO_RESOURCE_TYPE_ATTRIBUTE_NAME));
	}

	public Money getChargeOffAmount()
	{
		return chargeOffAmount;
	}

	public void setChargeOffAmount(Money chargeOffAmount)
	{
		this.chargeOffAmount = chargeOffAmount;
	}

	public Money getDestinationAmount()
	{
		return destinationAmount;
	}

	public void setDestinationAmount(Money destinationAmount)
	{
		this.destinationAmount = destinationAmount;
	}

	public InputSumType getInputSumType()
	{
		return inputSumType;
	}

	public void setInputSumType(InputSumType inputSumType)
	{
		this.inputSumType = inputSumType;
	}

	public void setInputSumType(String inputSumType)
	{
		if (StringHelper.isEmpty(inputSumType))
		{
			this.inputSumType = null;
		}
		else
		{
			this.inputSumType = InputSumType.valueOf(inputSumType);
		}
	}

	public CurrencyRate getDebetSaleRate()
	{
		//�� ������������ � ����
		return null;
	}

	public CurrencyRate getDebetBuyRate()
	{
		//�� ������������ � ����
		return null;
	}

	public CurrencyRate getCreditSaleRate()
	{
		//�� ������������ � ����
		return null;
	}

	public CurrencyRate getCreditBuyRate()
	{
		//�� ������������ � ����
		return null;
	}

	public BigDecimal getConvertionRate()
	{
		return getNullSaveAttributeDecimalValue(Constants.CONVERTION_RATE_ATTRIBUTE_NAME);
	}

	public List<WriteDownOperation> getWriteDownOperations()
	{
		//�� ������������ � ����
		return null;
	}

	public void setWriteDownOperations(List<WriteDownOperation> writeDownOperations)
	{
		//�� ������������ � ����
	}

	public Money getCommission()
	{
		//�� ������������ � ����
		return null;
	}

	public void setCommission(Money commission)
	{
		//�� ������������ � ����
	}

	public CommissionOptions getCommissionOptions()
	{
		//�� ������������ � ����
		return null;
	}

	public String getChargeOffCardAccount()
	{
		return null;
	}

	public Calendar getChargeOffCardExpireDate()
	{
		return getNullSaveAttributeCalendarValue(Constants.CHARGEOFF_CARD_EXPIRE_DATE_ATTRIBUTE_NAME);
	}

	public String getChargeOffCardDescription()
	{
		//�� ������������ � ����
		return null;
	}

	public String getOperationCode()
	{
		return getNullSaveAttributeStringValue(Constants.OPERATION_CODE_ATTRIBUTE_NAME);
	}

	public String getGround()
	{
		return ground;
	}

	public void setGround(String ground)
	{
		this.ground = ground;
	}

	public Currency getDestinationCurrency() throws GateException
	{
		Money amount = getDestinationAmount();
		return amount == null ? null : amount.getCurrency();
	}

	public Currency getChargeOffCurrency() throws GateException
	{
		Money amount = getChargeOffAmount();
		return amount == null ? null : amount.getCurrency();
	}

	protected Currency getNationalCurrency()
	{
		//TODO ��������� ������ ��������� ������
		return new CurrencyImpl("RUB");
	}

	protected boolean isOurBank()
	{
		ExtendedAttribute attribute = getAttribute(Constants.IS_OUR_BANK_ATTRIBUTE_NAME);
		if (attribute == null)
		{
			return false;
		}

		return BooleanUtils.toBoolean(attribute.getStringValue());
	}
}
