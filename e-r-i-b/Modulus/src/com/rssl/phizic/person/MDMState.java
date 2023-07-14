package com.rssl.phizic.person;

/**
 * Информация об отправке данных по клиенту в МДМ
 @author Pankin
 @ created 14.02.2011
 @ $Author$
 @ $Revision$
 */
public enum MDMState
{
	// Данные отправлены, информация по клиенту добавлена в МДМ
	ADDED,

	// Данные по клиенту еще не отправлялись в МДМ
	NOT_SENT,

	// Данные отослались с ошибкой
	ERROR_SENT
}
