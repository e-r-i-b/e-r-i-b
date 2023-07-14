package com.rssl.phizic.gate.clients;

/**
 * @author Omeliyanchuk
 * @ created 07.06.2008
 * @ $Author$
 * @ $Revision$
 */

public enum ClientStateCategory
{
	//договор расторгнут
	agreement_dissolve,
	//ошибка при расторженнии
	error_dissolve,
	//системная ошибка при растожении
	system_error_dissolve,
	//категория создания, когда клиент ещё не может войти в систему
	creation,
	//категоря активного использования. когда пользователь может без ограничений пользоваться системой
	active,
	//категория пользователя, который либы уже расторг договор, либо расторгает
	cancellation
}
