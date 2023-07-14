package com.rssl.phizgate.common.messaging.retail.jni.jndi;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;

/**
 * @author Omeliyanchuk
 * @ created 25.11.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * хелпер, строит имя для хранения фабрики в jndi
 */
public class RetailJNDINameBuilder
{
	public static String getRetailJniName()
	{
		ApplicationConfig config = ApplicationConfig.getIt();
		StringBuilder builder = new StringBuilder();
		builder.append("java:comp/");
		builder.append( config.getApplicationPrefixAdoptedToFileName() );
		builder.append( "/" );
		builder.append(RetailJniConstants.JNDI_PATH);
		return builder.toString();

	}
}
