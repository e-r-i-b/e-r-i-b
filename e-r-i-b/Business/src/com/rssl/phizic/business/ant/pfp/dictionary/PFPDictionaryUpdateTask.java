package com.rssl.phizic.business.ant.pfp.dictionary;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.dataaccess.config.CSAAdminDataSourceConfig;
import com.rssl.phizic.utils.test.JUnitDatabaseConfig;
import com.rssl.phizic.utils.test.JUnitSimpleDatabaseConfig;
import com.rssl.phizic.utils.test.SafeTaskBase;

import java.io.File;

/**
 * @author akrenev
 * @ created 13.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * таск для обновления записей справочников ПФП
 */
public class PFPDictionaryUpdateTask extends SafeTaskBase
{
	private boolean deleteUnknownProduct; // нужно ли удалять не заданные в файле продукты
	private String file;                 // имя файла справочника продуктов

	/**
	 * задать обязательность удаления неизвестных продуктов
	 * @param deleteUnknownProduct нужно ли удалять не заданные в файле продукты
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void setDeleteUnknownProduct(boolean deleteUnknownProduct)
	{
		this.deleteUnknownProduct = deleteUnknownProduct;
	}

	/**
	 * задать имя файла справочника продуктов
	 * @param file имя файла справочника продуктов
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void setFile(String file)
	{
		this.file = file;
	}

	public void safeExecute() throws BusinessException, BusinessLogicException
	{
		PFPDictionaryLoader dictionaryLoader = new PFPDictionaryLoader();
		dictionaryLoader.setDbInstanceName(MultiBlockModeDictionaryHelper.getDBInstanceName());
		dictionaryLoader.setDeleteUnknownProduct(deleteUnknownProduct);
		dictionaryLoader.setSourceFile(new File(file));
		dictionaryLoader.load();
	}

	@Override
	protected JUnitDatabaseConfig getDatabaseConfig()
	{
		JUnitSimpleDatabaseConfig config = new JUnitSimpleDatabaseConfig();
		CSAAdminDataSourceConfig dbConfig = new CSAAdminDataSourceConfig();
		config.setDataSourceName(dbConfig.getDataSourceName());
		config.setURI(dbConfig.getURI());
		config.setDriver(dbConfig.getDriver());
		config.setLogin(dbConfig.getLogin());
		config.setPassword(dbConfig.getPassword());
		return config;
	}

	public PFPDictionaryUpdateTask clone() throws CloneNotSupportedException
	{
		return (PFPDictionaryUpdateTask) super.clone();
	}
}
