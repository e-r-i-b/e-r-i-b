package com.rssl.phizic.business.skins;

import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.List;

/**
 * @author egorova
 * @ created 18.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class SkinsLoaderTest extends BusinessTestCaseBase
{
	private static final String PATH = "D:\\projects\\trunk/Settings/configs/demo";

	public void testSkinsLoader()
	{
		try
		{
		SkinsLoader skinsLoader = new SkinsLoader(PATH);
        List<Skin> skins =  skinsLoader.load();

        SkinsLoaderSynchronizer skinsSynchronizer = new SkinsLoaderSynchronizer(skins);

       // skinsSynchronizer.update();
		}
		catch(Exception e)
		{
			log.info("Troubles with updating skins.");
		}
	}

}
