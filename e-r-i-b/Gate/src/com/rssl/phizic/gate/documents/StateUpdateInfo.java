package com.rssl.phizic.gate.documents;

import com.rssl.phizic.common.types.documents.State;

import java.util.Calendar;

/**
 * »нтерфейс дл€ передачи информации об обновлении документа во внешней системе.
 * @author Maleyev
 * @ created 07.04.2008
 * @ $Author$
 * @ $Revision$
 */
public interface StateUpdateInfo
{
	/**
	 * Ќовый статус документа
	 * @return State
	 */
	State getState();

	/**
	 * @return дата следующей обработки документа
	 */
	Calendar getNextProcessDate();
}
