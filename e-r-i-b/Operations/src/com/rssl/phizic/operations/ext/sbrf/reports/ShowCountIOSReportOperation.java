package com.rssl.phizic.operations.ext.sbrf.reports;

import com.csvreader.CsvWriter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.reports.CountIOSReport;
import com.rssl.phizic.business.ext.sbrf.reports.ReportAbstract;
import com.rssl.phizic.business.ext.sbrf.reports.ReportHelper;
import com.rssl.phizic.business.ext.sbrf.reports.ReportServiceSBRF;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 30.11.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция для просмотра и выгрузки данных по отчету: "Количество клиентов iOS" 
 */
public class ShowCountIOSReportOperation extends OperationBase
{
	private static ReportServiceSBRF reportService = new ReportServiceSBRF();
	private static final Log log = LogFactory.getLog(Constants.LOG_MODULE_CORE.toValue());

	private List<CountIOSReport> reportList;
	private ReportAbstract reportAbstract;

	protected String getInstanceName()
	{
		return "Report";
	}

	/**
	 * Инициализация операции
	 * @param reportId - идентификатор отчета
	 * @throws BusinessException - не удалось получить дополнительные данные по отчету
	 * @throws BusinessLogicException - нет такого отчета в БД
	 */
	public void initialize(Long reportId) throws BusinessException, BusinessLogicException
	{
		reportAbstract = reportService.findById(reportId,getInstanceName());
		if(reportAbstract == null)
			throw new BusinessLogicException("Не удалось найти отчет с id = " + reportId);

		Query query = createQuery("list");
		query.setParameter("id",reportAbstract.getId());
		try
		{
			reportList = query.executeList();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException("Не удалось получить данные по отчету с id = " + reportId,e);
		}
	}

	/**
	 * @return отчет(общие данные о отчете)
	 */
	public ReportAbstract getReportAbstract()
	{
		return reportAbstract;
	}

	/**
	 * @return данные отчета преобразованные в мап
	 */
	public Map<String, Map<String, List<CountIOSReport>>> getReportMap()
	{
		return ReportHelper.getCountIOSReportMap(reportList);
	}

	/**
	 * @return название файла для выгрузки
	 */
	public String getUnloadFileName()
	{		
		return "CountIOSReport_" + DateHelper.formatDateToString(reportAbstract.getStartDate());
	}

	/**
	 * Формирование данных ф формате CSV
	 * @return данные для выгрузки
	 * @throws BusinessException - Не удалось сформировать данные для выгрузки отчета о количестве клиентов iOS
	 */
	public byte[] getUnloadedData() throws BusinessException
	{
		Map<String, Map<String, List<CountIOSReport>>> reportMap = getReportMap();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
		try
		{
			CsvWriter csvWriter = new CsvWriter(outputStreamWriter, ';');
			writeHeader(csvWriter);

			Long allCount = 0L;// счетчик общего числа клиентов по сбербанку
			Long allLastCount = 0L; // счетчик общего числа клиентов по сбербанку для столбца за N дней
			for(String tbName : reportMap.keySet())
			{
				csvWriter.write(tbName);
				Map<String, List<CountIOSReport>> tbReportMap = reportMap.get(tbName);
				boolean firstOSB=true;
				Long allTbCount = 0L; // счетчик общего числа клиентов по ТБ
				Long allTbLastCount = 0L; // счетчик общего числа клиентов по ТБ для столбца за N дней
				for(String osbName : tbReportMap.keySet())
				{
					if(!firstOSB) //если это не 1-я запись о ОСБ, то мы должны вставить пустое значение вместо данных о ТБ
						csvWriter.write("");
					csvWriter.write(osbName);
					List<CountIOSReport> osbReportList = tbReportMap.get(osbName);
					Long allOsbCount = 0L; // счетчик общего числа клиентов по ОСБ
					Long allOsbLastCount = 0L; // счетчик общего числа клиентов по ОСБ для столбца за N дней
					for(int i =0; i< osbReportList.size(); i++)
					{
						CountIOSReport report = osbReportList.get(i);
						if(i!=0)//если это не 1-я запись о ВСП, то мы должны вставить пустые значения вместо данных о ТБ и ОСБ
						{
							csvWriter.write("");
							csvWriter.write("");
						}
						csvWriter.write(report.getVspName());
						csvWriter.write(report.getTotalCount().toString());
						csvWriter.write(report.getLastCount().toString());
						csvWriter.endRecord();

						allOsbCount += report.getTotalCount();
						allOsbLastCount += report.getLastCount();
					}
					writeTotalCountForOSB(csvWriter,allOsbCount,allOsbLastCount);
					firstOSB = false;
					allTbCount +=allOsbCount;
					allTbLastCount += allOsbLastCount;
				}
				writeTotalCountForTB(csvWriter,allTbCount,allTbLastCount);
				allCount += allTbCount;
				allLastCount += allTbLastCount;
			}
			writeTotalCount(csvWriter,allCount,allLastCount);
		}
		catch (IOException e)
		{
			throw new BusinessException("Не удалось сформировать данные для выгрузки отчета о количестве клиентов iOS",e);
		}
		finally
		{
			try
			{
				outputStreamWriter.close();
			}
			catch (IOException e)
			{
				log.error("Не удалось закрыть outputStreamWriter при формировании файла для выгрузки отчета о количестве клиентов iOS",e);
			}
		}

		return outputStream.toByteArray();
	}

	private void writeTotalCountForOSB(CsvWriter writer, Long totalCount, Long lastCount) throws IOException
	{
		writer.write("");
		writer.write("Всего по ОСБ");
		writer.write("");
		writer.write(totalCount.toString());
		writer.write(lastCount.toString());
		writer.endRecord();
	}

	private void writeTotalCountForTB(CsvWriter writer, Long totalCount, Long lastCount) throws IOException
	{
		writer.write("Всего по ТБ");
		writer.write("");
		writer.write("");
		writer.write(totalCount.toString());
		writer.write(lastCount.toString());
		writer.endRecord();
	}

	private void writeTotalCount(CsvWriter writer, Long totalCount, Long lastCount) throws IOException
	{
		writer.write("Всего по Сбербанку");
		writer.write("");
		writer.write("");
		writer.write(totalCount.toString());
		writer.write(lastCount.toString());
		writer.endRecord();
	}

	private void writeHeader(CsvWriter writer) throws IOException
	{
		writer.write("Номер, наименование ТБ");
		writer.write("Наименование, номер ОСБ/ГОСБ");
		writer.write("Наименование, номер ВСП");
		writer.write("Кол-во клиентов(всего на дату формирования)");
		writer.write("Кол-во клиентов за 90 дней");
		writer.endRecord();
	}

}
