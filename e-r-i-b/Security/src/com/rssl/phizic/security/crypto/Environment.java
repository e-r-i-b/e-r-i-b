package com.rssl.phizic.security.crypto;

/**
 * @author Roshka
 * @ created 30.08.2006
 * @ $Author$
 * @ $Revision$
 */

interface Environment
{
	/**
	 * JNDI имя для доступа к пулу фабрик крипто-провайдеров
	 * Содержит экземпляр класса com.rssl.phizic.security.crypto.CryptoProviderFactoryPool
	 */
	static final String CRYPTO_PROVIDER_FACTORY_POOL = "rssl/crypto/provider-factory-pool";
}