package com.rssl.phizic.operations.log;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LoggingHelper;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.files.FileHelper;
import com.sun.org.apache.xml.internal.serialize.Method;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author gladishev
 * @ created 28.07.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class ArchiveLogOperationBase extends OperationBase
{
	public static final String ARCHIVE_TAG_NAME = "archive";
	public static final String RECORD_TAG_NAME = "record";
	public static final String DATE_FORMAT_STRING = "%1$td.%1$tm.%1$tY";
	public static final String TIME_FORMAT_STRING = "%1$tH:%1$tM:%1$tS";
	public static final String APP_SERVER_INFO = "add-info";

	private InputStream stream;
	private int duplicate;
	private Iterator logEntries;
	private Calendar toDate;

	/**
	 * Инициализация операции (разархивации)
	 * @param stream - поток для посторения списка logEntries
	 */
	public void initialize(InputStream stream) throws BusinessLogicException, BusinessException
	{
		this.stream = stream;
	}

	/**
	 * Инициализация операции архивации.
	 * период архивации определяется из настроек.
	 */
	public void initialize() throws BusinessException
	{
		initialize(getEndDate());
	}

	/**
	 * Инициализация операции (архивации)
	 * @param toDate конечная дата
	 */
	public void initialize(Calendar toDate) throws BusinessException
	{
		this.toDate = toDate;
		Query query = createQuery("getLogEntries");
		query.setParameter("toDate", toDate);
		try
		{
			logEntries = query.executeIterator();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return конечная дата архивации из настроек.
	 */
	protected Calendar getEndDate()
	{
		int days = Integer.parseInt(ConfigFactory.getConfig(LoggingHelper.class).getProperty(getSettingPrefix() + Constants.AUTO_ARCHIVE_LEAVING_SUFFIX));
		Calendar result = Calendar.getInstance();
		result.set(Calendar.MILLISECOND, 0);
		result.add(Calendar.DAY_OF_MONTH, -days);
		return result;
	}

	protected abstract String getSettingPrefix();

	/**
	 * Создает xml-документ (байтовый массив) из списка log-элементов
	 * @param stream поток для записи
	 */
	public void archive(final OutputStream stream) throws BusinessException
	{
		try
		{
			//архивируем в транзакции
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					XMLSerializer serializer = new XMLSerializer(stream, new OutputFormat(Method.XML, "windows-1251", true));
					serializer.startDocument();
					serializer.startElement(ARCHIVE_TAG_NAME, null);

					for (Iterator iterator = logEntries; iterator.hasNext();)
					{
						createRecord(serializer, iterator.next());
						iterator.remove();
					}

					serializer.endElement(ARCHIVE_TAG_NAME);
					serializer.endDocument();
					return null;
				}
			});
		}
		catch (SAXException e)
		{
			throw new BusinessException("Ошибка при создании документа", e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Создает запись (<record>...</record>) в xml-документе
	 * @param serializer - ...
	 * @param element - элемент из которого берутся данные для построения записи
	 */
	abstract void createRecord(XMLSerializer serializer, Object element) throws SAXException;

	/**
	 * Занесение logEntries в базу данных
	 * @return количество записей, не записанных в таблицу (потому как они там уже есть)
	 */
	public long unarchive() throws BusinessException
	{
		duplicate = 0;
		try
		{
			//разархивируем в транзакции
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					DefaultHandler handler = getHandler();
					SAXParserFactory factory = SAXParserFactory.newInstance();
					SAXParser saxParser = factory.newSAXParser();
					saxParser.parse(stream, handler);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		return duplicate;
	}

	/**
	 * @return хендлер
	 */
	protected abstract DefaultHandler getHandler();

	protected void insertRecord(final Object log) throws ConstraintViolationException, BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.save(log);
					session.flush();
					return null;
				}
			});
		}
		catch (ConstraintViolationException cve)
		{
			duplicate++;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	protected String emptyValueSafeGet(String str)
	{
		if (str == null)
		{
			return "";
		}
		return str;
	}

	public boolean hasRecords()
	{
		return logEntries.hasNext();
	}

	public void archive() throws BusinessException, IOException
	{
		String path = getArchiveName();
		ZipOutputStream zipOutputStream = null;
		try
		{
			zipOutputStream = new ZipOutputStream(new FileOutputStream(path, false));
			ZipEntry entry = new ZipEntry(getLogArchiveName() + ".xml");
			zipOutputStream.putNextEntry(entry);
			archive(zipOutputStream);
		}
		catch (IOException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		finally
		{
			if (zipOutputStream != null)
			{
				zipOutputStream.close();
			}
		}
	}

	protected abstract String getLogArchiveName();

	private String getArchiveName()
	{
		String path = FileHelper.normalizePath(ConfigFactory.getConfig(LoggingHelper.class).getProperty(getSettingPrefix() + Constants.AUTO_ARCHIVE_FOLDER_SUFFIX));

		String toDateString = formatDate(toDate);

		StringBuilder filename = new StringBuilder(getLogArchiveName());

		if (toDateString != null)
		{
			filename.append("_").append(toDateString);
		}
		filename.append(".zip");

		path = path.trim();
		if (!path.endsWith(File.separator))
		{
			path += File.separator;
		}
		return path + filename.toString();
	}

	private String formatDate(Calendar date)
	{
		if (date == null)
		{
			return null;
		}
		return String.format("%1$td_%1$tm_%1$tY_%1$tH_%1$tM_%1$tS", date.getTime());
	}

	protected String getInstanceName()
	{
		return Constants.DB_INSTANCE_NAME;
	}
}
