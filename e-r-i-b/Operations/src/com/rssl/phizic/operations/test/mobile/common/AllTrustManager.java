package com.rssl.phizic.operations.test.mobile.common;

import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/**
 * @author Erkin
* @ created 06.05.2011
* @ $Author$
* @ $Revision$
*/
class AllTrustManager implements X509TrustManager
{
	public X509Certificate[] getAcceptedIssuers()
	{
		return null;
	}

	public void checkClientTrusted(X509Certificate[] certs, String authType) {}

	public void checkServerTrusted(X509Certificate[] certs, String authType) {}
}
