package com.rssl.phizic.gate.depo;

import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 20.01.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ћаркер ценной бумаги
 */
public interface SecurityMarker extends Serializable
{
	/**
	 * @return маркер ценной бумаги
	 */
	String getMarker();

	/**
	 * @return кол-во ценных бумаг с данным маркером на счете(шт.) 
	 */
	Long getRemainder();
}
