package com.rssl.phizic.business.documents;

/**
 * @author akrenev
 * @ created 25.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Интерфейс объекта, хранящего инфу о блоке
 */

public interface NodeInfoContainer
{
	/**
	 * @return идентификатор резервного блока, в котором последний раз изменялась платежка
	 */
	public Long getTemporaryNodeId();

	/**
	 * получить идентификатор резервного блока, в котором последний раз изменялась платежка
	 * @param temporaryNodeId идентификатор
	 */
	public void setTemporaryNodeId(Long temporaryNodeId);
}
