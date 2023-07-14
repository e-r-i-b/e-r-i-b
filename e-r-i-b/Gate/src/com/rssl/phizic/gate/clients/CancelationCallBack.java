package com.rssl.phizic.gate.clients;

/**
 * @author Omeliyanchuk
 * @ created 30.07.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * Колбек-соеденитель для двухфазных операций расторжения договора
 */
public interface CancelationCallBack
{
	/**
	 * Идентификатор колбека.
	 * @return Идентификатор колбека.
	 */
	String getId();
}
