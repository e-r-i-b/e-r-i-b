package com.rssl.phizic.operations.log;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.io.*;
import java.util.Calendar;

/**
 * @author Krenev
 * @ created 03.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class ArchiveOperationsTest extends BusinessTestCaseBase
{
	public void testSystemLogOperation() throws Exception
	{
		ArchiveLogOperationBase operation = new ArchiveSystemLogOperation();

		Calendar fromDate = Calendar.getInstance();
		fromDate.add(Calendar.YEAR, -1);
		Calendar toDate = Calendar.getInstance();
		toDate.add(Calendar.DAY_OF_MONTH, -1);

		operation.initialize(toDate);
		archive(operation, "C:\\ArchiveSystemLogOperation.xml");

		operation.initialize(toDate);
		assertEquals(false, operation.hasRecords());

		unarchive(operation, "C:\\ArchiveSystemLogOperation.xml");

		operation.initialize(toDate);
		archive(operation, "C:\\ArchiveSystemLogOperation2.xml");
		unarchive(operation, "C:\\ArchiveSystemLogOperation2.xml");
		FileComparator fileComparator = new FileComparator("C:\\ArchiveSystemLogOperation2.xml", "C:\\ArchiveSystemLogOperation.xml");
		assertEquals(true, fileComparator.compare());
	}

	public void testOperationLogOperation() throws Exception
	{
		ArchiveLogOperationBase operation = new ArchiveOperationsLogOperation();
		Calendar fromDate = Calendar.getInstance();
		fromDate.add(Calendar.YEAR, -1);
		Calendar toDate = Calendar.getInstance();

		operation.initialize(toDate);
		archive(operation, "C:\\ArchiveOperationsLogOperation.xml");

		operation.initialize(toDate);
		assertEquals(false, operation.hasRecords());

		unarchive(operation, "C:\\ArchiveOperationsLogOperation.xml");

		operation.initialize(toDate);
		archive(operation, "C:\\ArchiveOperationsLogOperation2.xml");
		unarchive(operation, "C:\\ArchiveOperationsLogOperation2.xml");
		FileComparator fileComparator = new FileComparator("C:\\ArchiveOperationsLogOperation2.xml", "C:\\ArchiveOperationsLogOperation.xml");
		assertEquals(true, fileComparator.compare());
	}

	public void testMessagesLogOperation() throws Exception
	{
		ArchiveLogOperationBase operation = new ArchiveMessagesLogOperation();
		Calendar fromDate = Calendar.getInstance();
		fromDate.add(Calendar.YEAR, -1);
		Calendar toDate = Calendar.getInstance();

		operation.initialize(toDate);
		archive(operation, "C:\\ArchiveMessagesLogOperation.xml");
		operation.initialize(toDate);
		assertEquals(false, operation.hasRecords());

		unarchive(operation, "C:\\ArchiveMessagesLogOperation.xml");

		operation.initialize(toDate);
		archive(operation, "C:\\ArchiveMessagesLogOperation2.xml");
		unarchive(operation, "C:\\ArchiveMessagesLogOperation.xml");
		FileComparator fileComparator = new FileComparator("C:\\ArchiveMessagesLogOperation2.xml", "C:\\ArchiveMessagesLogOperation.xml");
		assertEquals(true, fileComparator.compare());
	}

	private void archive(ArchiveLogOperationBase operation, String file) throws BusinessException, IOException
	{
		OutputStream os = null;
		try
		{
			os = new FileOutputStream(file);
			operation.archive(os);
		}
		finally
		{
			os.close();
		}
	}

	private void unarchive(ArchiveLogOperationBase operation, String filename) throws BusinessException, BusinessLogicException, IOException
	{
		InputStream inputStream = null;
		try
		{
			inputStream = new FileInputStream(filename);
			operation.initialize(inputStream);
			operation.unarchive();
		}
		finally
		{
			inputStream.close();
		}
	}

	private class FileComparator
	{
		private String filename1;
		private String filename2;

		FileComparator(String filename1, String filename2)
		{
			this.filename1 = filename1;
			this.filename2 = filename2;
		}

		boolean compare() throws Exception
		{
			Reader reader1 = null;
			Reader reader2 = null;
			try
			{
				reader1 = new FileReader(filename1);
				reader2 = new FileReader(filename2);
				for (; ;)
				{
					int i1 = reader1.read();
					int i2 = reader2.read();
					if (i1 != i2)
					{
						return false;
					}
					if (i1 == -1)
					{
						return true;
					}
				}
			}
			finally
			{
				reader1.close();
				reader2.close();
			}
		}
	}
}
