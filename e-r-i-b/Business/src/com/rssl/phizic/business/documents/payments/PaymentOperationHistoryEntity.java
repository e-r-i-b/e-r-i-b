package com.rssl.phizic.business.documents.payments;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author Rtischeva
 * @ created 18.03.15
 * @ $Author$
 * @ $Revision$
 */
public class PaymentOperationHistoryEntity
{
	private Long entityId;
	private PaymentDocumentEntity document;
	private Long pfpId;
	private Calendar pfpDate;
	private String pfpState;
	private String pfpEmployee;
	private Long firId;
	private BigDecimal firSum;
	private String firCard;
	private Calendar firDate;
	private Long claimId;

	public Long getEntityId()
	{
		return entityId;
	}

	public void setEntityId(Long entityId)
	{
		this.entityId = entityId;
	}

	public PaymentDocumentEntity getDocument()
	{
		return document;
	}

	public void setDocument(PaymentDocumentEntity document)
	{
		this.document = document;
	}

	public Long getPfpId()
	{
		return pfpId;
	}

	public void setPfpId(Long pfpId)
	{
		this.pfpId = pfpId;
	}

	public Calendar getPfpDate()
	{
		return pfpDate;
	}

	public void setPfpDate(Calendar pfpDate)
	{
		this.pfpDate = pfpDate;
	}

	public String getPfpState()
	{
		return pfpState;
	}

	public void setPfpState(String pfpState)
	{
		this.pfpState = pfpState;
	}

	public String getPfpEmployee()
	{
		return pfpEmployee;
	}

	public void setPfpEmployee(String pfpEmployee)
	{
		this.pfpEmployee = pfpEmployee;
	}

	public Long getFirId()
	{
		return firId;
	}

	public void setFirId(Long firId)
	{
		this.firId = firId;
	}

	public BigDecimal getFirSum()
	{
		return firSum;
	}

	public void setFirSum(BigDecimal firSum)
	{
		this.firSum = firSum;
	}

	public String getFirCard()
	{
		return firCard;
	}

	public void setFirCard(String firCard)
	{
		this.firCard = firCard;
	}

	public Calendar getFirDate()
	{
		return firDate;
	}

	public void setFirDate(Calendar firDate)
	{
		this.firDate = firDate;
	}

	public Long getClaimId()
	{
		return claimId;
	}

	public void setClaimId(Long claimId)
	{
		this.claimId = claimId;
	}
}
