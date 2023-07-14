package com.rssl.phizic.module.loader;

/**
 * @author Erkin
 * @ created 16.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� �������� ������� ������
 */
public enum LoadOrder
{
	HIBERNATE_ENGINE_ORDER,

	LOG_ENGINE_ORDER,

	STARTUP_ENGINE_ORDER,

	TEXT_ENGINE_ORDER,

	GATE_ENGINE_ORDER,

	INTERACTIVE_ENGINE_ORDER,

	FORMS_ENGINE_ORDER,

	BANKROLL_ENGINE_ORDER,

	AUTH_ENGINE_ORDER,

	SESSION_ENGINE_ORDER,

	PERMISSION_ENGINE_ORDER,

	CONFIRM_ENGINE,

	SCHEDULER_ENGINE_ORDER,
	
    MESSAGING_ENGINE_ORDER,

	PAYMENT_ENGINE_ORDER,

	;
}
