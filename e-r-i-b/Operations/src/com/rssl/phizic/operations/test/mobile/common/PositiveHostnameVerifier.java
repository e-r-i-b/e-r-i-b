package com.rssl.phizic.operations.test.mobile.common;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * @author Erkin
* @ created 06.05.2011
* @ $Author$
* @ $Revision$
*/
class PositiveHostnameVerifier implements HostnameVerifier
{
	public boolean verify(String string, SSLSession sSLSession)
	{
		return true;
	}
}
