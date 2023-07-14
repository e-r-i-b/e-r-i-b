package com.rssl.phizic.rsa.senders.types;

/**
 * Скорость обработки поступившего от клиента документа
 *
 * @author khudyakov
 * @ created 04.02.15
 * @ $Author$
 * @ $Revision$
 */
public enum ProcessingSpeedType
{
	SEVERAL_DAYS,                           //в течение нескольких дней
	OVER_NIGHT,                             //в течение одной ночи
	FEW_HOURS,                              //в течение нескольких часов
	REAL_TIME,                              //так скоро как это возможно
}
