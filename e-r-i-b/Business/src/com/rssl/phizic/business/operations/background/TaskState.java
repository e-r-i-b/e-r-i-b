package com.rssl.phizic.business.operations.background;

/**
 * @author krenev
 * @ created 09.08.2011
 * @ $Author$
 * @ $Revision$
 */
public enum TaskState
{
	NEW,  //новая задача
	PROCESSING, //задача в процессе выполнения
	PROCESSED,  //успешно исполненная задача
	ERROR, //при исполеннии задачи произошла непредвиденная системная ошибка
    PERIODICAL, //для использования в периодических задачах
    DISABLED //признак того что задание приостановленно(в Периодических задачах)
}
