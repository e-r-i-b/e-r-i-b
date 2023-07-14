package com.rssl.phizic.business.menulinks;

/**
 * @author lukina
 * @ created 19.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class MenuLink
{
	private Long     id;        // идентификатор
	private int      linkId;    // id ссылки (из iccs.properties)
	private Long     loginId;   // id пользователя
	private int      orderInd;  // порядок отображения
    private boolean  use;       // отображать/не отображать в системе

	public boolean isUse()
	{
		return use;
	}

	public void setUse(boolean use)
	{
		this.use = use;
	}

	public MenuLink()
	{
	}

	public MenuLink(Long id, int linkId, Long loginId,int orderInd, boolean use)
	{
		this.id = id;
		this.linkId = linkId;
		this.loginId = loginId;
		this.orderInd = orderInd;
		this.use = use;
	}
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public int getLinkId()
	{
		return linkId;
	}

	public void setLinkId(int linkId)
	{
		this.linkId = linkId;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public int getOrderInd()
	{
		return orderInd;
	}

	public void setOrderInd(int orderInd)
	{
		this.orderInd = orderInd;
	}
}
