package com.rssl.phizic.business.resources.external;

import org.apache.commons.collections.Predicate;

/**фильтр для определения линков, отображаемых на главной странице
 * @author akrenev
 * @ created 21.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class ShowInMainLinkFilter implements Predicate
{
	public boolean evaluate(Object object)
	{
		if (object instanceof EditableExternalResourceLink)
		{
			EditableExternalResourceLink cardLink = (EditableExternalResourceLink) object;
			return cardLink.getShowInMain();
		}
		throw new IllegalArgumentException("Неизвестный тип " + object + ". Ожидается EditableExternalResourceLink.");
	}
}
