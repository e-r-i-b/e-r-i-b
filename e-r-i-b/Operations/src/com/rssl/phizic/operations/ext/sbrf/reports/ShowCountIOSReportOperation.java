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
 * �������� ��� ��������� � �������� ������ �� ������: "���������� �������� iOS" 
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
	 * ������������� ��������
	 * @param reportId - ������������� ������
	 * @throws BusinessException - �� ������� �������� �������������� ������ �� ������
	 * @throws BusinessLogicException - ��� ������ ������ � ��
	 */
	public void initialize(Long reportId) throws BusinessException, BusinessLogicException
	{
		reportAbstract = reportService.findById(reportId,getInstanceName());
		if(reportAbstract == null)
			throw new BusinessLogicException("�� ������� ����� ����� � id = " + reportId);

		Query query = createQuery("list");
		query.setParameter("id",reportAbstract.getId());
		try
		{
			reportList = query.executeList();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException("�� ������� �������� ������ �� ������ � id = " + reportId,e);
		}
	}

	/**
	 * @return �����(����� ������ � ������)
	 */
	public ReportAbstract getReportAbstract()
	{
		return reportAbstract;
	}

	/**
	 * @return ������ ������ ��������������� � ���
	 */
	public Map<String, Map<String, List<CountIOSReport>>> getReportMap()
	{
		return ReportHelper.getCountIOSReportMap(reportList);
	}

	/**
	 * @return �������� ����� ��� ��������
	 */
	public String getUnloadFileName()
	{		
		return "CountIOSReport_" + DateHelper.formatDateToString(reportAbstract.getStartDate());
	}

	/**
	 * ������������ ������ � ������� CSV
	 * @return ������ ��� ��������
	 * @throws BusinessException - �� ������� ������������ ������ ��� �������� ������ � ���������� �������� iOS
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

			Long allCount = 0L;// ������� ������ ����� �������� �� ���������
			Long allLastCount = 0L; // ������� ������ ����� �������� �� ��������� ��� ������� �� N ����
			for(String tbName : reportMap.keySet())
			{
				csvWriter.write(tbName);
				Map<String, List<CountIOSReport>> tbReportMap = reportMap.get(tbName);
				boolean firstOSB=true;
				Long allTbCount = 0L; // ������� ������ ����� �������� �� ��
				Long allTbLastCount = 0L; // ������� ������ ����� �������� �� �� ��� ������� �� N ����
				for(String osbName : tbReportMap.keySet())
				{
					if(!firstOSB) //���� ��� �� 1-� ������ � ���, �� �� ������ �������� ������ �������� ������ ������ � ��
						csvWriter.write("");
					csvWriter.write(osbName);
					List<CountIOSReport> osbReportList = tbReportMap.get(osbName);
					Long allOsbCount = 0L; // ������� ������ ����� �������� �� ���
					Long allOsbLastCount = 0L; // ������� ������ ����� �������� �� ��� ��� ������� �� N ����
					for(int i =0; i< osbReportList.size(); i++)
					{
						CountIOSReport report = osbReportList.get(i);
						if(i!=0)//���� ��� �� 1-� ������ � ���, �� �� ������ �������� ������ �������� ������ ������ � �� � ���
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
			throw new BusinessException("�� ������� ������������ ������ ��� �������� ������ � ���������� �������� iOS",e);
		}
		finally
		{
			try
			{
				outputStreamWriter.close();
			}
			catch (IOException e)
			{
				log.error("�� ������� ������� outputStreamWriter ��� ������������ ����� ��� �������� ������ � ���������� �������� iOS",e);
			}
		}

		return outputStream.toByteArray();
	}

	private void writeTotalCountForOSB(CsvWriter writer, Long totalCount, Long lastCount) throws IOException
	{
		writer.write("");
		writer.write("����� �� ���");
		writer.write("");
		writer.write(totalCount.toString());
		writer.write(lastCount.toString());
		writer.endRecord();
	}

	private void writeTotalCountForTB(CsvWriter writer, Long totalCount, Long lastCount) throws IOException
	{
		writer.write("����� �� ��");
		writer.write("");
		writer.write("");
		writer.write(totalCount.toString());
		writer.write(lastCount.toString());
		writer.endRecord();
	}

	private void writeTotalCount(CsvWriter writer, Long totalCount, Long lastCount) throws IOException
	{
		writer.write("����� �� ���������");
		writer.write("");
		writer.write("");
		writer.write(totalCount.toString());
		writer.write(lastCount.toString());
		writer.endRecord();
	}

	private void writeHeader(CsvWriter writer) throws IOException
	{
		writer.write("�����, ������������ ��");
		writer.write("������������, ����� ���/����");
		writer.write("������������, ����� ���");
		writer.write("���-�� ��������(����� �� ���� ������������)");
		writer.write("���-�� �������� �� 90 ����");
		writer.endRecord();
	}

}
