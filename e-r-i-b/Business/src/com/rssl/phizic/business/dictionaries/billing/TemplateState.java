package com.rssl.phizic.business.dictionaries.billing;

/**
 * Состояние шаблонов для оплаты по свободным реквизитам.
 *
 * @author bogdanov
 * @ created 09.06.14
 * @ $Author$
 * @ $Revision$
 */

public enum TemplateState
{
	//включены
	ACTIVE,
	//планируются к отключению
	PLANING_FOR_DEACTIVATE,
	//отключены
	INACTIVE;
}
