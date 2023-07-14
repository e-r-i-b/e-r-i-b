package com.rssl.phizic.operations.mail.archive;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.config.*;
import com.rssl.phizic.operations.OperationBase;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.util.Map;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author mihaylov
 * @ created 04.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class UnArchiveMailOperation extends OperationBase
{	
	private Map<String, Object> parameters;
	private long recoverMailCounter;

	public void initialize(Map<String, Object> parameters)
	{
		this.parameters = parameters;
	}

	public long getCountUnArchivedMails()
	{
		return recoverMailCounter;
	}

	/**
	 * �������������� ���� ����� �� ����� � ��������
	 * @throws BusinessException
	 */
	public void process() throws BusinessException, BusinessLogicException
	{
		try
		{
			String filePath = (String)parameters.get("folder");
			File file = new File(filePath);
			if(!file.exists())
				throw new BusinessLogicException("������� ������������ �� ����������");
			//������ ��� ����� �� ����� � �������� �����
			for(String str : file.list())
				//���������, ��� ���� ���� ��� ������������� ��� ��������� �����
				if(str.startsWith(ArchiveXMLTags.ZIP_ARCHIVE_FILE_PREFIX) && str.endsWith(ArchiveXMLTags.ZIP_ARCHIVE_FILE_SUFIX))
					unArchiveFromZip(filePath + "\\" + str);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			if(e.getCause() instanceof BusinessException)
			{
				throw (BusinessException)e.getCause();
			}
			else
			{
				throw new BusinessLogicException("�������� ������ ������.", e);
			}

		}
	}

	/**
	 * ������ ������, �������������� ����� �� ����� ������
	 * @param filePath - ���� �� .zip ����� � �������
	 * @throws IOException
	 * @throws SAXException
	 * @throws BusinessException
	 */
	private void unArchiveFromZip(String filePath) throws IOException, SAXException, BusinessException
	{
		InputStream inputStream = null;
		ZipInputStream zipInputStream = null;
		try
		{
			inputStream = new FileInputStream(filePath);
			zipInputStream = new ZipInputStream(inputStream);
			zipInputStream.getNextEntry();
			unArchiveFromXML(zipInputStream);
		}
		catch (FileNotFoundException e)
		{
			throw new BusinessException(e);
		}
		finally
		{
			if(inputStream != null)
				inputStream.close();
			if(zipInputStream != null)
				zipInputStream.close();						
		}

	}

	/**
	 * ������ xml ����� � ��������, �������������� ����� �� xml
	 * @param inputStream - ������� ����� �� �������� ������ xml
	 * @throws BusinessException
	 */
	public void unArchiveFromXML(final InputStream inputStream) throws IOException, SAXException, BusinessException
	{
		try
		{
			MailUnArcihiveValidator validator = new MailUnArcihiveValidator(parameters);
			RecipientUnArchiveValidator recipientValidator = new RecipientUnArchiveValidator(parameters);
			Boolean showUnArchivingMailToClient = parameters.get("showUnArchivingMailToClient") == null ? false : (Boolean)parameters.get("showUnArchivingMailToClient");
			DefaultHandler handler = new MailArchiveXMLHandler(validator, recipientValidator, showUnArchivingMailToClient);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(inputStream, handler);
			recoverMailCounter += ((MailArchiveXMLHandler)handler).getRecoverMailCounter();
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ���� ��� ������������ �����, �������� � ��������� ���������.
	 * @return
	 */
	public String getDefaultFilePath()
	{
		return ConfigFactory.getConfig(MailConfig.class).getArchivePath();
	}	
}
