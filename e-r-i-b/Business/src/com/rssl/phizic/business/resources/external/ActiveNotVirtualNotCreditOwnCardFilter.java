package com.rssl.phizic.business.resources.external;

/**
 * Created by IntelliJ IDEA.
 * User: Filimonova
 * Date: 14.02.2011
 * Time: 18:44:14
 * To change this template use File | Settings | File Templates.
 *
 * Фильтр ислючает доплнительные карты, открытые на другое физическое лицо + фильтрация родителя.
 */
public class ActiveNotVirtualNotCreditOwnCardFilter extends ActiveNotVirtualNotCreditCardFilter
{
	public ActiveNotVirtualNotCreditOwnCardFilter()
	{
		super();
		addFilter(new CardOwnFilter());
	}
}
