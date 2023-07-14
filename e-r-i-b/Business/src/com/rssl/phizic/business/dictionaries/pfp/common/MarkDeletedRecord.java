package com.rssl.phizic.business.dictionaries.pfp.common;

/**
 * @author akrenev
 * @ created 12.09.2012
 * @ $Author$
 * @ $Revision$
 *
 * интерфейс сущности не удаляемой из БД (помечается удаленной)
 */
public interface MarkDeletedRecord
{
	/**
	 * задать признак удаленности сущности
	 * @param deleted признак удаленности сущности
	 */
	public void setDeleted(boolean deleted);
}
