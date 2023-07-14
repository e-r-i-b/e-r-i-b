package com.rssl.phizic.operations.ext.sbrf.mobilebank.register;

import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.resources.external.*;

/**
 * @author Erkin
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Фильтрует карты, не подходящие для подключения к МБ
 * карты клиента, за исключением:
 *  - закрытых карт,
 *  - заблокированных карт,
 *  - дополнительных карт клиента,
 *  - корпоративных,
 *  - с истекшим сроком действия,
 *  - открытых на другое физическое лицо.
 * По данным картам отсутствует подключение в АС Мобильный банк
 */
class RegistrationCardFilter extends CardFilterConjunction
{
	RegistrationCardFilter(Person person)
	{
		// за исключением закрытых карт, заблокированных карт
		addFilter(new ActiveCardFilter());

		// за исключением дополнительных карт
		addFilter(new MainCardFilter());

		// за исключением карт с истекшим сроком действия
		addFilter(new NotExpiredCardFilter());

		// за исключением карт, открытых на другое физическое лицо
		addFilter(new CardOwnFilter(person));
	}
}
