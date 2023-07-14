package com.rssl.phizic.business.groups;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * User: Balovtsev
 * Date: 26.05.2011
 * Time: 12:00:40
 */
public class GroupsLoaderTest extends BusinessTestCaseBase
{
	private String path = "C:\\Java\\Projects\\PhizICv1.18\\Settings\\configs\\sbrf";

	public void testGroupsLoader() throws BusinessException
	{
		GroupLoaderSynchronizer synchronizer = new GroupLoaderSynchronizer(new GroupsLoader(path).load());
		synchronizer.update();
	}
}
