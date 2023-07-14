package com.rssl.phizic.rsa.senders.types;

/**
 * Определяет, как быстро / часто получатель получит оплату.
 *
 * @author khudyakov
 * @ created 04.02.15
 * @ $Author$
 * @ $Revision$
 */
public enum ExecutionPeriodicityType
{
	IMMEDIATE,                                  //немедленный перевод
	SCHEDULED,                                  //запланированно на поздний срок
	RECURRING,                                  //повторяющийся перевод
}
