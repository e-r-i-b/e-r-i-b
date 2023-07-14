package com.rssl.phizic.business.exception.report;

import com.csvreader.CsvWriter;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.exceptions.ExceptionCounter;
import com.rssl.phizic.logging.exceptions.ExceptionEntry;
import com.rssl.phizic.logging.exceptions.report.ExceptionReportRecordBase;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * @author akrenev
 * @ created 22.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * билдер отчета по ошибкам системы
 */

public abstract class ReportBuilderBase<R extends ExceptionReportRecordBase, E extends ExceptionEntry>
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final DateSpan DATE_SPAN = new DateSpan(0, 0, 1);

	private static final String NUMBER_COLUMN_NAME = "Порядковый номер";
	private static final String ID_COLUMN_NAME = "Уникальный идентификатор ошибки";
	private static final String DETAIL_COLUMN_NAME = "Текст ошибки";
	private static final char CSV_CELL_DELIMITER = ';';
	private static final String DEFAULT_EXCEPTION_COUNT = "0";

	private final Calendar date;
	private CsvWriter writer;

	protected ReportBuilderBase(Calendar date) throws IOException
	{
		this.date = date;
	}

	protected abstract List<R> getData(Calendar date) throws Exception;
	protected abstract void addExceptionAdditionalHeader() throws IOException;
	protected abstract void addExceptionAdditionalInfo(E entry) throws IOException;

	protected void addHeader() throws IOException
	{
		addCell(NUMBER_COLUMN_NAME);
		addCell(ID_COLUMN_NAME);
		addCell(DETAIL_COLUMN_NAME);
		addExceptionAdditionalHeader();
		addDateHeader();
		writer.endRecord();
	}

	private void addDateHeader() throws IOException
	{
		Calendar startDate = DateHelper.getFirstDayOfMonth(date);
		Calendar currentIterationDate = startDate;
		Calendar currentDate = Calendar.getInstance();
		//шагаем по дням месяца, но не дальше текущей даты
		while(startDate.get(Calendar.MONTH) == currentIterationDate.get(Calendar.MONTH) && DateHelper.nullSafeCompare(currentIterationDate, currentDate) != 1)
		{
			addCell(DateHelper.formatDateToStringWithPoint(currentIterationDate));
			currentIterationDate = DateHelper.add(currentIterationDate, DATE_SPAN);
		}
	}


	private void addExceptionInfo(R record, int index) throws IOException
	{
		//noinspection unchecked
		E entry = (E) record.getEntry();
		addCell(String.valueOf(index));
		addCell(String.valueOf(entry.getId()));
		addCell(entry.getDetail());
		addExceptionAdditionalInfo(entry);
		//noinspection unchecked
		addDateInfo(record.getCounters());
		writer.endRecord();
	}

	private void addDateInfo(List<ExceptionCounter> counters) throws IOException
	{
		Calendar startDate = DateHelper.getFirstDayOfMonth(date);
		Calendar currentIterationDate = startDate;
		Iterator<ExceptionCounter> countersIterator = counters.iterator();
		ExceptionCounter currentCounter = countersIterator.next();
		Calendar currentDate = Calendar.getInstance();
		//шагаем по дням месяца, но не дальше текущей даты
		while(startDate.get(Calendar.MONTH) == currentIterationDate.get(Calendar.MONTH) && DateHelper.nullSafeCompare(currentIterationDate, currentDate) != 1)
		{
			String cellValue = DEFAULT_EXCEPTION_COUNT;
			if (DateUtils.isSameDay(currentIterationDate, currentCounter.getDate()))
			{
				cellValue = String.valueOf(currentCounter.getCount());
				if (countersIterator.hasNext())
					currentCounter = countersIterator.next();
			}
			currentIterationDate = DateHelper.add(currentIterationDate, DATE_SPAN);
			addCell(cellValue);
		}
	}

	protected void addCell(String data) throws IOException
	{
		writer.write(data);
	}

	/**
	 * сформировать данные в формате csv-файла
	 * @return массив байт
	 * @throws IOException
	 */
	public byte[] build() throws Exception
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
		try
		{
			writer = new CsvWriter(outputStreamWriter, CSV_CELL_DELIMITER);
			List<R> data = getData(date);
			addHeader();
			int i = 0;
			boolean emptyReport = true;
			for (R record : data)
			{
				if (CollectionUtils.isNotEmpty(record.getCounters()))
					addExceptionInfo(record, ++i);
				emptyReport = emptyReport && record.getCounters().isEmpty();
			}
			if (emptyReport)
			{
				return new byte[0];
			}
		}
		finally
		{
			try
			{
				outputStreamWriter.close();
			}
			catch (IOException e)
			{
				log.error("Не удалось закрыть outputStreamWriter при формировании файла для выгрузки отчета об ошибках.", e);
			}
		}
		return outputStream.toByteArray();
	}
}
