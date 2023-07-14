package com.rssl.phizic.business.deposits;

import com.rssl.phizic.utils.xml.XmlFileReader;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Чтение {@link com.rssl.phizic.business.deposits.DepositGlobal} из файла.
 * @author Roshka
 * @ created 10.05.2006
 * @ $Author$
 * @ $Revision$
 */

public class DepositGlobalLoader
{
	private static final String DEPOSIT_LIST_TRANSFORMATION_FILE_NAME             = "list.xslt";
    private static final String DEPOSIT_CALCULATOR_TRANSFORMATION_FILE_NAME       = "calculator.xslt";
	private static final String DEPOSIT_ADMIN_LIST_TRANSFORMATION_FILE_NAME       = "admin-list.xslt";
	private static final String DEPOSIT_ADMIN_EDIT_TRANSFORMATION_FILE_NAME       = "admin-edit.xsl";
	private static final String DEPOSIT_DEFAULT_DETAILS_TRANSFORMATION_FILE_NAME  = "default-details.xslt";
	private static final String DEPOSIT_PERCENT_RATE_TRANSFORMATION_FILE_NAME     = "deposit-percent-rates.xsl";
	private static final String DEPOSIT_MOBILE_LIST_TRANSFORMATION_FILE_NAME = "mobile-list.xslt";
	private static final String DEPOSIT_MOBILE_DETAILS_TRANSFORMATION_FILE_NAME = "mobile-details.xslt";
	private static final String DEPOSIT_VISIBILITY_DETAILS_TRANSFORMATION_FILE_NAME = "visibility-details.xslt";

    private String path;

	public DepositGlobalLoader(String path)
	{
		this.path = path;
	}

	/**
	 * Прочитать общие настройки для ДП
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 */
	public DepositGlobal load() throws IOException, SAXException
	{
		return read(new File(path));
	}

	private DepositGlobal read(File currentDir) throws IOException, SAXException
	{
		File fileList                     = readFile(currentDir, DEPOSIT_LIST_TRANSFORMATION_FILE_NAME);
        File fileCalculatorTransformation = readFile(currentDir, DEPOSIT_CALCULATOR_TRANSFORMATION_FILE_NAME);
		File fileAdminListTransformation  = readFile(currentDir, DEPOSIT_ADMIN_LIST_TRANSFORMATION_FILE_NAME);
		File fileAdminEditTransformation  = readFile(currentDir, DEPOSIT_ADMIN_EDIT_TRANSFORMATION_FILE_NAME);
		File fileDefaultDetailsTransformation = readFile(currentDir, DEPOSIT_DEFAULT_DETAILS_TRANSFORMATION_FILE_NAME);
		File fileDepositPercentRateTransformation = readFile(currentDir, DEPOSIT_PERCENT_RATE_TRANSFORMATION_FILE_NAME);
		File mobileListTransformation = readFile(currentDir, DEPOSIT_MOBILE_LIST_TRANSFORMATION_FILE_NAME);
		File mobileDetailsTransformation = readFile(currentDir, DEPOSIT_MOBILE_DETAILS_TRANSFORMATION_FILE_NAME);
		File visibilityDetailsTransformation = readFile(currentDir, DEPOSIT_VISIBILITY_DETAILS_TRANSFORMATION_FILE_NAME);

		DepositGlobal global = new DepositGlobal();
		global.setListTransformation(new XmlFileReader(fileList).readString());
		global.setDefaultDetailsTransformation(new XmlFileReader(fileDefaultDetailsTransformation).readString());

		if (fileCalculatorTransformation!=null)
	        global.setCalculatorTransformation(new XmlFileReader(fileCalculatorTransformation).readString());
        if (fileAdminListTransformation!=null)
	        global.setAdminListTransformation(new XmlFileReader(fileAdminListTransformation).readString());
		if (fileAdminEditTransformation!=null)
			global.setAdminEditTransformation(new XmlFileReader(fileAdminEditTransformation).readString());
		if (mobileListTransformation != null)
			global.setMobileListTransformation(new XmlFileReader(mobileListTransformation).readString());
		if (mobileDetailsTransformation != null)
			global.setMobileDetailsTransformation(new XmlFileReader(mobileDetailsTransformation).readString());
		if (visibilityDetailsTransformation != null)
			global.setVisibilityDetailsTransformation(new XmlFileReader(visibilityDetailsTransformation).readString());

		if (fileDepositPercentRateTransformation != null)
		{
			global.setDepositPercentRateTransformation(new XmlFileReader(fileDepositPercentRateTransformation).readString());
		}

		return global;
	}

	private File readFile(File currentDir, final String fileName)
	{
		File[] files = currentDir.listFiles(new FilenameFilter()
		{
			public boolean accept(File dir, String name)
			{
				return name.equals(fileName);
			}
		});

		return files.length != 0 ? files[0] : null;
	}
}