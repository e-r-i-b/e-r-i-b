package com.rssl.phizic.business.favouritelinks;

import com.rssl.phizic.common.types.FavouriteTypeLink;

/**
 * @author mihaylov
 * @ created 28.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class FavouriteLink
{
	private Long        id;
	private String      name;
	private String      link;
	private Long        loginId;
	private int         orderInd;
	private String      pattern;   // паттерн названия ссылки для элемента личного меню, с указанием части для замены названия для случаев переименования продукта
	private String      onclickFunction;
	private FavouriteTypeLink typeLink; //тип ссылки

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getLink()
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
		removeSessionData();
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

	public String getPattern()
	{
		return pattern;
	}

	public void setPattern(String pattern)
	{
		this.pattern = pattern;
	}

	public FavouriteTypeLink getTypeLink()
	{
		return typeLink;
	}

	public void setTypeLink(FavouriteTypeLink  typeLink)
	{
		this.typeLink = typeLink;
	}

	public String getOnclickFunction()
	{
		return onclickFunction;
	}

	public void setOnclickFunction(String onclickFunction)
	{
		this.onclickFunction = onclickFunction;
	}

	private void removeSessionData()
	{
		if(link==null)
			return;
		int beginSession = link.indexOf("sessionData");

		// если нет параметра sessionData в строке
		if (beginSession == -1)
			return;

		int endSession = link.indexOf('&', link.indexOf("sessionData"))+1;

		// если sessionData - это последний параметр
		if(endSession==0)
		{
			link = link.substring(0, beginSession);
			return;
		}

		link = link.substring(0, beginSession) + link.substring(endSession);
	}
}
