package com.rssl.phizic.business.documents.payments.mock;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.longoffer.mock.MockLongOffer;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.config.ExternalSystemIntegrationMode;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.CommissionOptions;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * TODO переделать, текуща€ задача
 *
 * @author osminin
 * @ created 02.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class MockAbstractTransfer extends MockLongOffer implements AbstractTransfer
{
	protected static final String EMPTY_STRING = "";
	private String chargeOffCardAccount;
	private Long receiverInternalId;
	private Long sendNodeNumber;

	public void setLongOffer(LongOffer longOffer) throws GateException
	{
		super.setExternalId(longOffer.getExternalId());
		super.setNumber(longOffer.getNumber());
		super.setAmount(longOffer.getAmount());
		super.setEndDate(longOffer.getEndDate());
		super.setExecutionEventType(longOffer.getExecutionEventType());
		super.setOffice(longOffer.getOffice());
		super.setPayDay(longOffer.getPayDay());
		super.setPercent(longOffer.getPercent());
		super.setPriority(longOffer.getPriority());
		super.setStartDate(longOffer.getStartDate());
		super.setSumType(longOffer.getSumType());
		super.setType(longOffer.getType());
	}

	public Money getChargeOffAmount()
	{
		return null;
	}

	public void setChargeOffAmount(Money amount)
	{

	}

	public Money getDestinationAmount()
	{
		return null;
	}

	public void setDestinationAmount(Money amount)
	{

	}

	public InputSumType getInputSumType()
	{
		return null;
	}

	public CurrencyRate getDebetSaleRate()
	{
		return null;
	}

	public CurrencyRate getDebetBuyRate()
	{
		return null;
	}

	public CurrencyRate getCreditSaleRate()
	{
		return null;
	}

	public CurrencyRate getCreditBuyRate()
	{
		return null;
	}

	public BigDecimal getConvertionRate()
	{
		return null;
	}

	public String getExternalId()
	{
		return null;
	}

	public void setExternalId(String externalId)
	{

	}

	public State getState()
	{
		return null;
	}

	public Calendar getExecutionDate()
	{
		return null;
	}

	public void setExecutionDate(Calendar executionDate)
	{

	}

	public Long getId()
	{
		return null;
	}

	public Calendar getClientCreationDate()
	{
		return null;
	}

	public Calendar getClientOperationDate()
	{
		return null;
	}

	public void setClientOperationDate(Calendar clientOperationDate)
	{

	}

	public Calendar getAdditionalOperationDate()
	{
		return null;
	}

	public Long getInternalOwnerId()
	{
		return null;
	}

	public String getExternalOwnerId()
	{
		return null;
	}

	public void setExternalOwnerId(String externalOwnerId)
	{
	}

	public Office getOffice()
	{
		return null;
	}

	public void setOffice(Office office)
	{

	}

	public Class<? extends GateDocument> getType()
	{
		return null;
	}

	public FormType getFormType()
	{
		return null;
	}

	public Money getCommission()
	{
		return null;
	}

	public void setCommission(Money commission)
	{

	}

	public CommissionOptions getCommissionOptions()
	{
		return null;
	}

	public EmployeeInfo getCreatedEmployeeInfo() throws GateException
	{
		return null;
	}

	public EmployeeInfo getConfirmedEmployeeInfo() throws GateException
	{
		return null;
	}

	public void setConfirmedEmployeeInfo(EmployeeInfo info)
	{

	}

	public CreationType getClientCreationChannel()
	{
		return null;
	}

	public CreationType getClientOperationChannel()
	{
		return null;
	}

	public void setClientOperationChannel(CreationType channel)
	{

	}

	public CreationType getAdditionalOperationChannel()
	{
		return null;
	}

	public void setAdditionalOperationChannel(CreationType channel)
	{

	}

	public String getDocumentNumber()
	{
		return null;
	}

	public Calendar getAdmissionDate()
	{
		return null;
	}

	public boolean isTemplate()
	{
		return false;
	}

	public List<WriteDownOperation> getWriteDownOperations()
	{
		return null;
	}

	public void setWriteDownOperations(List<WriteDownOperation> list)
	{
	}

	public String getNextState()
	{
		throw new UnsupportedOperationException();
	}

	public void setNextState(String nextState)
	{
		throw new UnsupportedOperationException();
	}

	public String getOperationCode()
	{
		return EMPTY_STRING;
	}

	public String getGround()
	{
		return null;
	}

	public void setGround(String ground)
	{

	}

	public String getPayerName()
	{
		return null;
	}

	public void setTariffPlanESB(String tariffPlanESB)
	{

	}

	public String getChargeOffCardAccount()
	{
		return chargeOffCardAccount;
	}

	public void setChargeOffCardAccount(String chargeOffCardAccount)
	{
		this.chargeOffCardAccount = chargeOffCardAccount;
	}

	public Long getReceiverInternalId()
	{
		return receiverInternalId;
	}

	public void setReceiverInternalId(Long receiverInternalId)
	{
		this.receiverInternalId = receiverInternalId;
	}

	public String getMbOperCode()
	{
		throw new UnsupportedOperationException();
	}

	public void setMbOperCode(String mbOperCode)
	{
		throw new UnsupportedOperationException();
	}

	public Long getSendNodeNumber()
	{
		return sendNodeNumber;
	}

	public void setSendNodeNumber(Long sendNodeNumber)
	{
		this.sendNodeNumber = sendNodeNumber;
	}

	public ExternalSystemIntegrationMode getIntegrationMode()
	{
		throw new UnsupportedOperationException("ѕолучение режима интеграции не поддерживаетс€.");
	}
}
