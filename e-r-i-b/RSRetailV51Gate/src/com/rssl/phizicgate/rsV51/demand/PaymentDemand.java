package com.rssl.phizicgate.rsV51.demand;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 12.03.2007
 * Time: 16:57:17
 * To change this template use File | Settings | File Templates.
 */
public class PaymentDemand extends PaymentDemandBase
{
	private boolean typeDoc;
	private Long applType;
	private String bicReceiver;
    private BigDecimal currencyBought;
    private BigDecimal percTrnRest;
	private Long GKHRecipientKind;
	private Long GKHRecipientId;
	private String GKHRecipientInn;
	private Long transferLink;
	private Remittee receiver;
	private Long flag;

	public Remittee getReceiver()
	{
		return receiver;
	}

	public void setReceiver(Remittee receiver)
	{
		this.receiver = receiver;
	}

	public Long getApplType()
	{
		return applType;
	}

	public void setApplType(Long applType)
	{
		this.applType = applType;
	}

	public boolean getDocumentType()
	{
		return this.typeDoc;
	}

	public void setDocumentType(boolean tpeDoc)
	{
		this.typeDoc = tpeDoc;
	}

	public String getReceiverBic()
	{
		return bicReceiver;
	}

	public void setReceiverBic(String bicReceiver)
	{
		this.bicReceiver = bicReceiver;
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
    * @return transferLink  —сылка на список безнал зачислени€  (соответствует pstrans.AutoKey)
    */
	public Long getTransferLink()
	{
		return transferLink;
	}

	/**
    * @param transferLink —сылка на список безнал зачислени€ (соответствует pstrans.AutoKey)
    */
	public void setTransferLink(Long transferLink)
	{
		this.transferLink = transferLink;
	}

	public Long getFlag()
	{
		return flag;
	}

	public void setFlag(Long flag)
	{
		this.flag = flag;
	}
}
