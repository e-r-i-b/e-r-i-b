package com.rssl.phizicgate.rsV51.demand;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 12.03.2007
 * Time: 16:57:17
 * To change this template use File | Settings | File Templates.
 */
public class ExpandedPaymentDemand extends PaymentDemandBase
{
	private boolean typeDoc;
	private Long applType;
	private String ground;
	private String bicReceiver;
    private BigDecimal currencyBought;
    private BigDecimal percTrnRest;
	private Remittee receiver;
    private Remittee destination;
	private Long GKHRecipientKind = 0L;
	private Long GKHRecipientId = 0L;
	private String GKHRecipientInn = "";
	private BigDecimal CBRest;

	public String getReceiverBic()
	{
		return bicReceiver;
	}

	public void setReceiverBic(String bicReceiver)
	{
		this.bicReceiver = bicReceiver;
	}

	public Long getCur()
	{
		return getIsCur();
	}

	public Long getApplicationType()
	{
		return 0L;
	}

	public void setCurrencyCode(Long codeCurrency)
	{
		super.setCurrencyCode(codeCurrency);
		setReceiverCurrencyCode(codeCurrency);
	}

	public void setApplicationType(Long applType)
	{
		this.applType=applType;
	}

	public boolean getDocumentType()
	{
		return this.typeDoc;
	}

	public void setDocumentType(boolean tpeDoc)
	{
		this.typeDoc = tpeDoc;
	}

	public BigDecimal getCurrencyBought()
	{
		return currencyBought;
	}

	public void setCurrencyBought(BigDecimal currencyBought)
	{
		this.currencyBought = currencyBought;
	}

	public BigDecimal getOperationRest()
	{
		return percTrnRest;
	}

	public void setOperationRest(BigDecimal percTrnRest)
	{
		this.percTrnRest = percTrnRest;
	}

	public Remittee getDestination()
	{
		return destination;
	}

	public Remittee getReceiver()
	{
		return receiver;
	}

	public Long getApplType()
	{
		return applType;
	}

	public void setApplType(Long applType)
	{
		this.applType = (applType == null ? 0L : applType);
	}

	public void setReceiver(Remittee receiver)
	{
		receiver.setId(getId());
		receiver.setBranch(getDepartment());
		receiver.setBic(this.bicReceiver);
		receiver.setCorAccount(getReceiverCorAccount());
		receiver.setReceiverAccount(getReceiverAccount());
		receiver.setAttributID("РЕКВ_ПОЛ");

		this.receiver = receiver;
	}

	public void setDestination(Remittee remittee)
	{
		Remittee receiver = new Remittee();
		receiver.setId(remittee.getId());
		receiver.setBranch(remittee.getBranch());
		receiver.setBic(remittee.getBic());
		receiver.setCorAccount(remittee.getCorAccount());
		receiver.setReceiverAccount(remittee.getReceiverAccount());
		receiver.setBank(remittee.getBank());
		receiver.setReceiverInn(remittee.getReceiverInn());
		receiver.setReceiverKpp(remittee.getReceiverKpp());
		receiver.setBankCode(remittee.getBankCode());
		receiver.setReceiverName(remittee.getReceiverName());
		receiver.setGround(remittee.getGround());
		receiver.setAttributID("НАЗН_ПЛАТ");

        this.destination = receiver;
	}

    public Long getGKHRecipientKind()
	{
		return GKHRecipientKind;
	}

	public void setGKHRecipientKind(Long GKHRecipientKind)
	{
		this.GKHRecipientKind = GKHRecipientKind;
	}

	public Long getGKHRecipientId()
	{
		return GKHRecipientId;
	}

	public void setGKHRecipientId(Long GKHRecipientId)
	{
		this.GKHRecipientId = GKHRecipientId;
	}

	public String getGKHRecipientInn()
	{
		return GKHRecipientInn;
	}

	public void setGKHRecipientInn(String GKHRecipientInn)
	{
		this.GKHRecipientInn = GKHRecipientInn;
	}
	 /**
	 * Курс ЦБ на момент операци
	*/
    public BigDecimal getOperationCBRest()
	{
		return CBRest;
	}

	/**
	 * Курс ЦБ на момент операци
	 * @param CBRest курс
	*/
	public void setOperationCBRest(BigDecimal CBRest)
	{
		this.CBRest = CBRest;
	}
}
