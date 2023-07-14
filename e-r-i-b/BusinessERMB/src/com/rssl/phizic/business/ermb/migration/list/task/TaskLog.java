package com.rssl.phizic.business.ermb.migration.list.task;

import com.rssl.phizic.business.ermb.migration.list.config.ErmbListMigrationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.utils.ExceptionUtil;
import com.rssl.phizic.utils.files.FileHelper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * ���������������� ������ ������
 * @author Puzikov
 * @ created 15.01.14
 * @ $Author$
 * @ $Revision$
 */

public class TaskLog
{
	private static final String FILE_FORMAT = "%s_%s_%s.log";
	private static final SimpleDateFormat logDateFormat = new SimpleDateFormat("HH:mm:ss");
	private final ErmbListMigrationConfig config = ConfigFactory.getConfig(ErmbListMigrationConfig.class);
	private final CounterService counterService = new CounterService();

	private TaskType taskType;
	private File logFile;
	private StringBuilder data;

	private DateFormat getLogDateFormatter()
	{
		return (DateFormat) logDateFormat.clone();
	}

	/**
	 * ctor
	 * @param taskType ��� ������
	 * @throws IOException
	 * @throws CounterException
	 */
	public TaskLog(TaskType taskType) throws IOException, CounterException
	{
		this.taskType = taskType;
		logFile = new File(createLogFilename(taskType));
		FileUtils.touch(logFile);
		if (!FileHelper.canWrite(logFile))
			throw new IOException("���������� ������ � ���� " + getLogFileName());
		data = new StringBuilder();
	}

	/**
	 * �������� ������ � ��������
	 * @param line ������
	 * @return this
	 */
	public TaskLog add(String line)
	{
		data.append(getPrefix())
		.append(line).append('\n');
		return this;
	}

	/**
	 * �������� ���������� � ��������
	 * @param e ����������
	 * @return this
	 */
	public TaskLog add(Exception e)
	{
		data.append(getPrefix())
		.append(ExceptionUtil.printStackTrace(e));
		return this;
	}

	/**
	 * �������� ���������� ��������� �� ����
	 */
	public void flush()
	{
		try
		{
			FileUtils.writeStringToFile(logFile, data.toString());
		}
		catch (IOException ignored)
		{
		}
	}

	/**
	 * @return �������� ����� ����
	 */
	public final String getLogFileName()
	{
		return logFile.getName();
	}

	private String createLogFilename(TaskType taskType) throws CounterException
	{
		String currentDate = new SimpleDateFormat("dd_MM_yyyy").format(Calendar.getInstance().getTime());
		long logId = counterService.getNext(taskType.getCounter(), "Migration");

		//Load_��_��_����_N.log
		return config.getLogDir() +
				String.format(FILE_FORMAT,
						taskType.getValue(),
						currentDate,
						logId);
	}

	private String getPrefix()
	{
		StringBuilder result = new StringBuilder();

		String currentDate = getLogDateFormatter().format(Calendar.getInstance().getTime());
		result.append(currentDate).append(" [").append(taskType.getValue()).append("] ");

		return result.toString();
	}
}
