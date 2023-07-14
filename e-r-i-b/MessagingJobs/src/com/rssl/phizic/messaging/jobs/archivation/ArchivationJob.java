package com.rssl.phizic.messaging.jobs.archivation;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.log.ArchiveLogOperationBase;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author eMakarov
 * @ created 13.04.2009
 * @ $Author$
 * @ $Revision$
 */
public abstract class ArchivationJob implements StatefulJob
{
	public ArchivationJob() throws JobExecutionException
	{
	}

	abstract boolean isEnabled();

	abstract int getLastDays();

	abstract String getPath();

	abstract ArchiveLogOperationBase getOperation();

	abstract String getLogArchiveName();

	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			if (!isEnabled())
			{
				return;
			}

			ArchiveLogOperationBase operation = getOperation();
			int days = getLastDays();
			Calendar calendarTo = createCalendarTo(days);
			operation.initialize(calendarTo);

			archive(operation, calendarTo);
		}
		catch (Exception e)
		{
			throw new JobExecutionException(e);
		}
	}

	private Calendar createCalendarTo(int days)
	{
		Calendar result = Calendar.getInstance();

		result.set(Calendar.MILLISECOND, 0);
		result.add(Calendar.DAY_OF_MONTH, -days);

		return result;
	}

	private void archive(ArchiveLogOperationBase operation, Calendar calendarTo) throws IOException
	{
		String path = getArchivePath(calendarTo);

		ZipOutputStream zipOutputStream = null;
		try
		{
			zipOutputStream = new ZipOutputStream(new FileOutputStream(path, false));
			ZipEntry entry = new ZipEntry(getXMLFileName());
			zipOutputStream.putNextEntry(entry);
			operation.archive(zipOutputStream);
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}
		catch (FileNotFoundException e)
		{
			throw new RuntimeException(e);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			if (zipOutputStream != null)
			{
				zipOutputStream.close();
			}
		}
	}

	private String getXMLFileName()
	{
		return getLogArchiveName() + ".xml";
	}

	private String getArchivePath(Calendar calendarTo)
	{
		String path = getPath();

		String toDate = String.format("%1$td_%1$tm_%1$tY", calendarTo.getTime());
		String toTime = String.format("%1$tH_%1$tM_%1$tS", calendarTo.getTime());

		StringBuffer fileNameBuffer = new StringBuffer(getLogArchiveName());
		fileNameBuffer.append("_").append(toDate).append("_").append(toTime);
		fileNameBuffer.append(".zip");
		String fileNamePrefix = fileNameBuffer.toString();

		if (path == null)
		{
			throw new RuntimeException("path не может быть null");
		}

		path = path.trim();
		if (!path.endsWith(File.separator))
		{
			path += File.separator;
		}

		path += fileNamePrefix;

		return path;
	}
}
