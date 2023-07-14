package com.rssl.phizic.business.ant;

import com.rssl.phizic.utils.test.SafeTaskBase;
import com.rssl.phizic.business.skins.SkinsLoader;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.business.skins.SkinsLoaderSynchronizer;

import java.util.List;

/**
 * Загрузка скинов в базу.
 * 
 * @author egorova
 * @ created 18.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class SkinsLoaderTask  extends SafeTaskBase
{
	private String root;

	public void setRoot(String root)
	{
		this.root = root;
	}

	public void safeExecute() throws Exception
	{
		if (root == null || root.length() == 0)
			throw new Exception("Не установлен параметр 'root'");

		log("Updating of skins descriptions in path + " + root + " ...");

		SkinsLoader skinsLoader = new SkinsLoader(root);
        List<Skin> skins =  skinsLoader.load();

        SkinsLoaderSynchronizer skinsSynchronizer = new SkinsLoaderSynchronizer(skins);

        skinsSynchronizer.update();

		log("Updating skins processed.");
	}
}
