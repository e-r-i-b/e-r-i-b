package com.rssl.phizic.business.skins;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.business.ant.SkinsSyncTask;

/**
 * @author egorova
 * @ created 27.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class SkinsSyncTest extends BusinessTestCaseBase
{
	private static final String PATH = "D:\\projects\\trunk/Settings/configs/demo";
	private static final String TO_PATH = "D:\\projects\\trunk/Exploded.ear/WebResources.war/skins";
	private static final String BASE_PATH = "D:\\projects\\trunk/WebResources/web/skins";

	public void testSkinsSync()
	{
		try
		{
			SkinsSyncTask task = new SkinsSyncTask();
			task.setToDirPath(TO_PATH);
			task.setRoot(PATH);
			task.setBaseSourcePath(BASE_PATH);
			task.execute();
		}
		catch(Exception e)
		{
			log.info("Troubles with updating skins.");
		}
	}
}
