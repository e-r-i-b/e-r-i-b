package com.rssl.phizic.business.ermb.migration.list.entity.migrator;

/**
 * Статус телефона в контексте разрешения конфликтов
 * @author Puzikov
 * @ created 24.01.14
 * @ $Author$
 * @ $Revision$
 */

public enum ConflictStatus
{
	UNRESOLVED,             //Неразрешенный конфликт
	RESOLVED_TO_OWNER,      //Оставить клиенту (по id)
	RESOLVED_TO_DELETE      //удалить телефон вообще
}
