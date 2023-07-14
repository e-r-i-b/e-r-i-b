package com.rssl.phizic.business.security;

import com.rssl.phizic.business.schemes.DbAccessSchemesConfig;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.business.schemes.AccessSchemesConfig;
import com.rssl.phizic.business.schemes.SharedAccessScheme;

/**
 * @author Evgrafov
 * @ created 14.08.2007
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */
@SuppressWarnings({"JavaDoc"})
public class AnonymousClientCreatorTest extends BusinessTestCaseBase
{
	public void test() throws Exception
	{
		AccessSchemesConfig schemesConfig = ConfigFactory.getConfig(DbAccessSchemesConfig.class);
		SharedAccessScheme scheme = schemesConfig.getSchemes().get(0); // first one
		String userId = "ano" + System.currentTimeMillis();
		AnonymousClientCreator clientCreator = new AnonymousClientCreator(userId, scheme);

		clientCreator.create(); // создать
		clientCreator.create(); // повторное создание не должно давать ошибок
	}
}