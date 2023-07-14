package com.rssl.phizic.business.loans;

import com.rssl.phizic.utils.xml.XmlFileReader;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;

import java.io.IOException;
import java.io.File;
import java.io.FilenameFilter;

import org.xml.sax.SAXException;

/**
 * @author gladishev
 * @ created 10.01.2008
 * @ $Author$
 * @ $Revision$
 */

public class LoanGlobalLoader
{
	private static final String LOAN_LIST_TRANSFORMATION_FILE_NAME             = "list.xslt";
    private static final String LOAN_CALCULATOR_TRANSFORMATION_FILE_NAME       = "calculator.xslt";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

    private String path;

	public LoanGlobalLoader(String path)
	{
		this.path = path;
	}

	/**
	 * Прочитать общие настройки для КП
	 * @return
	 * @throws java.io.IOException
	 * @throws org.xml.sax.SAXException
	 */
	public LoanGlobal load() throws IOException, SAXException
	{
		return read(new File(path));
	}

	private LoanGlobal read(File currentDir) throws IOException, SAXException
	{
		File fileList                     = readFile(currentDir, LOAN_LIST_TRANSFORMATION_FILE_NAME);
        File fileCalculatorTransformation = readFile(currentDir, LOAN_CALCULATOR_TRANSFORMATION_FILE_NAME);

		if(fileList==null || fileCalculatorTransformation==null)
		{
			log.error("Не найдено описание кредитов. Кредиты не загруженны");
			return null;
		}

		LoanGlobal global = new LoanGlobal();
		global.setListTransformation(new XmlFileReader(fileList).readString());
        global.setCalculatorTransformation(new XmlFileReader(fileCalculatorTransformation).readString());

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

		return (files!=null && files.length!=0) ? files[0] : null;
	}


}
