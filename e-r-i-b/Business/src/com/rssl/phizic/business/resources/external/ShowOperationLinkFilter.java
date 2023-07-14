package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.ext.sbrf.dictionaries.ShowOperationsSettings;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;
import org.apache.commons.collections.Predicate;

import java.util.Set;

/** фильтр для определения линков, с отображаемыми операциями
 * @author akrenev
 * @ created 19.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class ShowOperationLinkFilter implements Predicate
{
	public boolean evaluate(Object object)
	{
		if (object instanceof EditableExternalResourceLink)
		{
			EditableExternalResourceLink link = (EditableExternalResourceLink) object;
			if (ShowOperationsSettings.isDataBaseSetting())
				return link.getShowOperations();

			ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
			Set<String> showOperations = person.getShowOperations();
			
			return showOperations.contains(link.getCode());
		}
		throw new IllegalArgumentException("Неизвестный тип " + object + ". Ожидается EditableExternalResourceLink.");
	}
}
