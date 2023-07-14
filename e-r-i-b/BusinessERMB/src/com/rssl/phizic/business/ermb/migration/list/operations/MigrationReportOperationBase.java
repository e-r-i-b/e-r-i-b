package com.rssl.phizic.business.ermb.migration.list.operations;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.migration.list.Segment;
import com.rssl.phizic.business.ermb.migration.list.task.hibernate.ClientService;
import com.rssl.phizic.dataaccess.common.counters.Counter;
import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @author Gulov
 * @ created 28.01.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ����� �������� ��� �������� ������� ��������
 */
abstract class MigrationReportOperationBase extends OperationBase
{
	private static final CounterService counterService = new CounterService();
	static final ClientService service = new ClientService();
	private static final int FIRST_ROW = 0;
	private static final int FIRST_CELL = 0;

	/**
	 * ��������� ��������
	 */
	private Segment[] segments;

	/**
	 * �������� ������ �����
	 * @return - ������ �����
	 */
	abstract String getFileTemplate();

	/**
	 * �������� ������� ��� ������������ ����� �����
	 * @return - �������
	 */
	abstract Counter getCounter();

	/**
	 * �������� ������ ���������� ������� ������
	 * @return - ������ ����������
	 */
	abstract String[] getHeader();

	/**
	 * ������������ ���������� ������
	 * @param data ������ ��������
	 * @param sheet excel �������
	 * @param firstRow ��������� ������ ������
	 * @param firstCell ��������� ������� ������
	 */
	abstract void makeContent(List data, HSSFSheet sheet, int firstRow, int firstCell) throws BusinessException;

	/**
	 * �������� ������� � excel
	 */
	abstract String getSheetTitle();

	/**
	 * ����� ������ ��� ������������ ������
	 * @return ������ ������
	 * @throws Exception
	 */
	abstract List findData() throws Exception;

	public void setSegments(String[] segments)
	{
		this.segments = convert(segments);
	}

	Segment[] getSegments()
	{
		return segments;
	}

	/**
	 * ������������ �������� �����
	 * @return - ������ �������� �����
	 * @throws BusinessException
	 */
	public String createFileName() throws BusinessException
	{
		String currentDate = new SimpleDateFormat("dd_MM_yyyy").format(Calendar.getInstance().getTime());
		long counter = 0;
		try
		{
			counter = counterService.getNext(getCounter(), "Migration");
			String fileName = String.format(getFileTemplate(), currentDate, counter);
			return StringUtils.from1251To1252(fileName);
		}
		catch (CounterException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� �����
	 * @param fileName - ��� �����
	 * @return ���� ������
	 */
	public File makeReport(String fileName) throws Exception
	{
		Calendar startTime = Calendar.getInstance();
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(getSheetTitle());
		int firstRow = FIRST_ROW;
		int firstCell = FIRST_CELL;
		makeHeader(sheet, firstRow++, firstCell);
		List clients = findData();
		if (!CollectionUtils.isEmpty(clients))
			makeContent(clients, sheet, firstRow, firstCell);
		for (int i = 0; i < getHeader().length; i++)
			sheet.autoSizeColumn(i + firstCell);
		File file = new File(System.getProperty("java.io.tmpdir") + fileName);
		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		out.close();
		writeLog(startTime);
		return file;
	}

	private void makeHeader(HSSFSheet sheet, int firstRow, int firstCell)
	{
		String[] header = getHeader();
		Row row = sheet.createRow(firstRow);
		for (int i = 0; i < header.length; i++)
			row.createCell(firstCell++).setCellValue(header[i]);
	}

	private Segment[] convert(String[] segments)
	{
		Segment[] result = new Segment[segments.length];
		for (int i = 0; i < segments.length; i++)
			result[i] = Segment.fromValue(segments[i]);
		return result;
	}

	/**
	 * ������ � ������ ���������������� ��������
	 * @param startTime - ����� ������ ��������
	 * @throws Exception
	 */
	protected abstract void writeLog(Calendar startTime) throws Exception;
}
