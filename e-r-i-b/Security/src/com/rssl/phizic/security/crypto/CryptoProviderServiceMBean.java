package com.rssl.phizic.security.crypto;

/**
 * @author Roshka
 * @ created 30.08.2006
 * @ $Author$
 * @ $Revision$
 */

public interface CryptoProviderServiceMBean
{
	Boolean getStarted();

	String getFactoryClassName();

	void setFactoryClassName(String factoryClassName);

	void setStarted(Boolean value) throws CryptoProviderServiceException;

	void start() throws CryptoProviderServiceException;

	void stop();
}
