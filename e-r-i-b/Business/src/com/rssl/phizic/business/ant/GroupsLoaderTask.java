package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.groups.GroupsLoader;
import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.business.groups.GroupLoaderSynchronizer;
import com.rssl.phizic.utils.test.SafeTaskBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.List;

/**
 * User: Balovtsev
 * Date: 25.05.2011
 * Time: 17:27:55
 */
public class GroupsLoaderTask extends SafeTaskBase
{
	private String path;

	/**
	 * @param path путь к каталогу содержащему user-groups.xml
	 */
	public void setPath(String path)
	{
		this.path = path;
	}

	public void safeExecute() throws Exception
	{
		if (StringHelper.isEmpty(path))
		{
			throw new Exception("Не установлен путь к каталогу - параметр 'path'");
		}

	    List<Group> groups = new GroupsLoader(path).load();
		new GroupLoaderSynchronizer(groups).update();
	}
}
