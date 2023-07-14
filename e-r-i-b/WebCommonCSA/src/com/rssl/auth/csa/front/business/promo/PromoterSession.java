package com.rssl.auth.csa.front.business.promo;

import com.rssl.phizic.business.promoters.PromoChannel;

import java.util.Calendar;

/**
 * Сессии промоутера
 * @ author: Gololobov
 * @ created: 21.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class PromoterSession
{
	private Long sessionId;
	private Calendar creationDate;
	private Calendar closeDate;
	private PromoChannel channel;
	private String tb;
	private String osb;
	private String office;
	private String promoter;

	public Long getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(Long sessionId)
	{
		this.sessionId = sessionId;
	}

	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	public Calendar getCloseDate()
	{
		return closeDate;
	}

	public void setCloseDate(Calendar closeDate)
	{
		this.closeDate = closeDate;
	}

	public PromoChannel getChannel()
	{
		return channel;
	}

	public void setChannel(PromoChannel channel)
	{
		this.channel = channel;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public String getOsb()
	{
		return osb;
	}

	public void setOsb(String osb)
	{
		this.osb = osb;
	}

	public String getOffice()
	{
		return office;
	}

	public void setOffice(String office)
	{
		this.office = office;
	}

	public String getPromoter()
	{
		return promoter;
	}

	public void setPromoter(String promoter)
	{
		this.promoter = promoter;
	}
}
