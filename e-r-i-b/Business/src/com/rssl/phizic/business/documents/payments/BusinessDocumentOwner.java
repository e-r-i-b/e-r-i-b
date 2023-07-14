package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.annotation.Immutable;

/**
 * @author Rtischeva
 * @ created 18.02.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Владелец документа
 * Владельцев можно сравнивать через equals:
 * + Два владельца-гостя эквивалентны, если равны их ЦСА-коды.
 * + Два владельца-клиента эквивалентны, если равны ид их логинов.
 * + Владелец-клиент никогда не эквивалентен владельцу-гостю.
 */
@Immutable
public interface BusinessDocumentOwner
{
	/**
	 * @return учётка гостя (GuestLogin) или клиента (Login) (never null)
	 */
	Login getLogin();

	/**
	 * @return данные клиента (never null)
	 */
	ActivePerson getPerson() throws BusinessException;

	/**
	 * @return true, если владелец - гость
	 */
	boolean isGuest();

	/**
	 * @return ключ для сравнения владельцев
	 */
	String getSynchKey();
}
