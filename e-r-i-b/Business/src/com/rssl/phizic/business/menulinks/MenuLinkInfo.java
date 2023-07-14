package com.rssl.phizic.business.menulinks;

/**
 * @author mihaylov
 * @ created 19.11.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Класс обёртка для ссылки главного меню
 * В этом классе храним дополнительные данные для ссылки
 */
public class MenuLinkInfo
{
	private MenuLink link; // ссылка главного меню
	private String text; // текст ссылки
	private String action; // переход по ссылке
	private String activity;
	private String module; // модуль
	private String service; // сервис
	private String operation; // операция
	private boolean novelty;  // новинка

	public MenuLinkInfo(MenuLink link)
	{
		this.link = link;
	}

	public MenuLink getLink()
	{
		return link;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getAction()
	{
		return action;
	}

	public void setAction(String action)
	{
		this.action = action;
	}

	public String getActivity()
	{
		return activity;
	}

	public void setActivity(String activity)
	{
		this.activity = activity;
	}

	public String getModule()
	{
		return module;
	}

	public void setModule(String module)
	{
		this.module = module;
	}

	public String getService()
	{
		return service;
	}

	public void setService(String service)
	{
		this.service = service;
	}

	public String getOperation()
	{
		return operation;
	}

	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	public void setNovelty(boolean novelty)
	{
		this.novelty = novelty;
	}

	public boolean isNovelty()
	{
		return novelty;
	}
}
