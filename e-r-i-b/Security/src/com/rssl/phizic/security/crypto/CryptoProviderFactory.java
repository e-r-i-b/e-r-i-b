package com.rssl.phizic.security.crypto;

import java.io.Serializable;

/**
 * @author Roshka
 * @ created 29.08.2006
 * @ $Author$
 * @ $Revision$
 */

public interface CryptoProviderFactory extends Serializable
{
	/**
	 * @return наименование провайдера (agava, dummy etc)
	 */
	String getName();

	/**
	 * ¬озвращает экземпл€р крипто-провайдера.
	 * @return в зависимости от реализации фабрики
	 * может быть возвращЄн либо новый экземпл€р, либо экземпл€р, созданный заранее
	 * never null
	 */
	CryptoProvider getProvider();

	void close();
}
