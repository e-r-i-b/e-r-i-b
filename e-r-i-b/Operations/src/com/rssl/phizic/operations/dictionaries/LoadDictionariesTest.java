package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.business.dictionaries.config.Constants;
import com.rssl.phizic.config.*;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.files.FileHelper;
import com.rssl.phizic.utils.resources.ResourceHelper;

import java.io.IOException;
import java.io.InputStream;

public class LoadDictionariesTest extends BusinessTestCaseBase
{
	private final String BANKS_FILE_NAME = "banks.dbf";
	private final String COUNTRIES_FILE_NAME = "country.dbf";

	protected void setUp() throws Exception
	{
		super.setUp();
		initializeRsV51Gate();
	}

	public void test() {}

	public void manualLoadDictionaries() throws Exception
	{
		if (ConfigurationContext.getIntstance().getActiveConfiguration().equals("russlav"))
			prepareContact();
		SynchronizeDictionariesOperation operation = new SynchronizeDictionariesOperation();
		operation.synchronizeAll();
	}

	private void prepareContact() throws IOException
	{
		String tmpPath = System.getProperty("java.io.tmpdir");

		System.out.println("Подготовка данных для загрузки справочников CONTACT...");

		InputStream iBanks = null;
		InputStream iCounties = null;

		try
		{
			String resourcePath = "";

			System.out.println("Копирование справочников в " + tmpPath);
			iBanks = ResourceHelper.loadResourceAsStream(resourcePath + BANKS_FILE_NAME);
			FileHelper.write(iBanks, tmpPath + BANKS_FILE_NAME);

			iCounties = ResourceHelper.loadResourceAsStream(resourcePath + COUNTRIES_FILE_NAME);
			FileHelper.write(iCounties, tmpPath + COUNTRIES_FILE_NAME);

			DbPropertyService.updateProperty(Constants.CONTACT_DICTIONARY, tmpPath);

			System.out.println("Подготовка данных прошла успешно");
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage() + e.getStackTrace());
		}
		finally
		{
			if (iCounties != null)
			{
				iCounties.close();
			}
			if (iBanks != null)
			{
				iBanks.close();
			}
		}
	}
}
