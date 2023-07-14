package com.rssl.phizic.business.ermb.migration.list.operations;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.ermb.migration.list.config.ErmbListMigrationConfig;
import com.rssl.phizic.business.ermb.migration.list.task.service.TaskWebService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.files.FileHelper;

import java.util.Calendar;

/**
 * @author Gulov
 * @ created 10.12.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� �������� ������ �������� ��� ��������
 */
public class LoadClientListOperation extends OperationBase
{
	/**
	 * ����, ���������� ������ �������� ��� ��������
	 */
	private String fileName;

	/**
	 * ����������� ���������� � csv
	 */
	private String sharedPath = ConfigFactory.getConfig(ErmbListMigrationConfig.class).getCsvSharedDir();

	/**
	 * ���-�� ������� ���������� ������������ ��������
	 */
	private int monthCount;

	/**
	 * ������ ������
	 */
	private String status;

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getFileName()
	{
		return fileName;
	}

	public String getSharedPath()
	{
		return sharedPath;
	}

	public void setMonthCount(int monthCount)
	{
		this.monthCount = monthCount;
	}

	/**
	 * ��������� ������ ��������
	 */
	public void save() throws BusinessException
	{
		try
		{
			Calendar startTime = Calendar.getInstance();
			TaskWebService service = new TaskWebService();
			status = service.loadClients(FileHelper.getCurrentFilePath(sharedPath, fileName), monthCount);
			writeLog(startTime);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public String getStatus()
	{
		return status;
	}

	/**
	 * @return ������������ ������ ������������ ����� � ����������
	 */
	public int getMaxFileSize()
	{
		return ConfigFactory.getConfig(ErmbListMigrationConfig.class).getCsvFileMaxSize();
	}

	private void writeLog(Calendar startTime) throws Exception
	{
		Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		LogWriter logWriter = OperationLogFactory.getLogWriter();
		DefaultLogDataReader reader = new DefaultLogDataReader("������ �������� ������ �������� � �� ��������");
		LogThreadContext.setLogin(employee.getLogin().toString());
		LogThreadContext.setLoginId(employee.getLogin().getId());
		LogThreadContext.setFirstName(employee.getFirstName());
		LogThreadContext.setSurName(employee.getSurName());
		LogThreadContext.setPatrName(employee.getPatrName());
		LogThreadContext.setUserId(employee.getLogin().getUserId());
		logWriter.writeActiveOperation(reader, startTime, Calendar.getInstance());
		LogThreadContext.clear();
	}
}
