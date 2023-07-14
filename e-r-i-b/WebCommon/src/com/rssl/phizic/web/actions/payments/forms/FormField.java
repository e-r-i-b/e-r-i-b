package com.rssl.phizic.web.actions.payments.forms;

import java.io.Serializable;

/**
 * @author Erkin
 * @ created 27.11.2010
 * @ $Author$
 * @ $Revision$
 */
public interface FormField extends Serializable
{
	/**
	 * ¬озвращает им€ пол€,
	 * ключ, по которому можно обратитьс€ к полю в Jsp и т.п.
	 * @return код пол€
	 */
	String getName();

	/**
	 * ¬озвращает тип пол€
	 * @return тип пол€
	 */
	FormFieldType getType();

	/**
	 * ¬озвращает название пол€ дл€ отображени€ пользователю
	 * @return им€ пол€ дл€ пользовател€
	 */
	String getUserName();

	void setUserName(String userName);

	/**
	 * ¬озвращает маркер пол€
	 * ћаркер используетс€ дл€ группировки полей по какому-то признаку
	 * @return
	 */
	String getMarker();

	void setMarker(String marker);
}
