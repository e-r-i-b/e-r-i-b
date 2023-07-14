package com.rssl.phizic.person;

import com.rssl.phizic.common.types.annotation.MandatoryParameter;
import com.rssl.phizic.task.Task;

/**
 * @author Erkin
 * @ created 28.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Задача, выполняющаяся в контексте клиента
 */
public interface PersonTask extends Task
{
	/**
	 * Установить шеф-менеджер по клиенту
	 * @param person - клиент
	 */
	@MandatoryParameter
	void setPerson(Person person);

}
