package com.rssl.phizic.business.ant;

import com.rssl.phizic.errors.ErrorMessage;
import com.rssl.phizic.errors.ErrorMessagesLoader;
import com.rssl.phizic.errors.ErrorMessagesSynchronizer;
import com.rssl.phizic.utils.test.SafeTaskBase;
import com.rssl.phizic.utils.xml.XmlFileReader;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

/**
 * @author gladishev
 * @ created 21.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class ErrorMessagesLoaderTask extends SafeTaskBase
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

		File messagesFile = readFile(new File(root), ErrorMessagesLoader.ERROR_MESSAGES_FILE_NAME);
		XmlFileReader xmlFileReader = new XmlFileReader(messagesFile);
		ErrorMessagesLoader errorMessagesLoader = new ErrorMessagesLoader(xmlFileReader.readDocument());
		errorMessagesLoader.load();
		List<ErrorMessage> errorMessages = errorMessagesLoader.getMessages();

		ErrorMessagesSynchronizer messagesSynchronizer = new ErrorMessagesSynchronizer(errorMessages);

		messagesSynchronizer.update();

		log("Updating errorMessages processed.");
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
