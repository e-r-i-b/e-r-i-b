package com.rssl.phizic.business.etsm.offer;

import com.rssl.phizgate.ext.sbrf.etsm.OfferPrior;

import java.util.Calendar;

/**
 * @author EgorovaA
 * @ created 15.06.15
 * @ $Author$
 * @ $Revision$
 */
public class OfferEribPrior extends OfferPrior
{
	// ������������� ������ � ����
	private Long claimId;
	// login ID ������� � ����
	private Long clientLoginId;
	// ������ ������ (ACTIVE - �����������, DELETED - ����� ����, ��� ������ ���������� ������ ��� ��������� �� ���)
	private String state;
	// ���� ���������� ������ � ����
	private Calendar offerDate;


	public Long getClaimId()
	{
		return claimId;
	}

	public void setClaimId(Long claimId)
	{
		this.claimId = claimId;
	}

	public Long getClientLoginId()
	{
		return clientLoginId;
	}

	public void setClientLoginId(Long clientLoginId)
	{
		this.clientLoginId = clientLoginId;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public Calendar getOfferDate()
	{
		return offerDate;
	}

	public void setOfferDate(Calendar offerDate)
	{
		this.offerDate = offerDate;
	}
}
