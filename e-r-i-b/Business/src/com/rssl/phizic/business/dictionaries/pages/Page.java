package com.rssl.phizic.business.dictionaries.pages;

import java.util.Map;

/** сущность "—траница"
 * @author akrenev
 * @ created 29.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class Page
{
	private Long id;            // id страницы
	private String key;         // уникальный ключ дл€ страницы, не должен мен€тьс€ при обновлении справочника.
	private String name;        // наименование страницы
	private String url;         // url страницы
	private Map<String,String> parameters; // параметры страницы
	private Page parent;        // если страница входит в группу, то указатель на данную группу.
	private String order;       // пор€дковый номер страницы в справочнике

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public Map<String, String> getParameters()
	{
		return parameters;
	}

	public void setParameters(Map<String, String> parameters)
	{
		this.parameters = parameters;
	}

	public Page getParent()
	{
		return parent;
	}

	public void setParent(Page parent)
	{
		this.parent = parent;
	}

	public String getOrder()
	{
		return order;
	}

	public void setOrder(String order)
	{
		this.order = order;
	}
}
