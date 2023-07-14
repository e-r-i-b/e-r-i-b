package com.rssl.phizic.web.erkcemployee;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author akrenev
 * @ created 13.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Форма для экшена сотрудников ЕРКЦ срабатывающего после смены блока
 */

public class ERKCEmployeeAfterChangeBlockForm extends ListFormBase<ActivePerson>
{
	private Long id;

	/**
	 * @return идентификатор выбранного клиента
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор выбранного клиента
	 * @param id идентификатор клиента
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
}
