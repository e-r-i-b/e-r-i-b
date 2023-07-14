package com.rssl.phizic.gate.loyalty;

import java.io.Serializable;

/**
 * Спецпредложение по программе лояльности
 * @author gladishev
 * @ created 02.08.2012
 * @ $Author$
 * @ $Revision$
 */
public interface LoyaltyOffer extends Serializable
{
	/**
	 * @return описание спецпредложения 
	 */
	String getDescription();
}
