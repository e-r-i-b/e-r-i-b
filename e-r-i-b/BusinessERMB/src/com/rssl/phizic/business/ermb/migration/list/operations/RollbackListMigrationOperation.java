package com.rssl.phizic.business.ermb.migration.list.operations;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.migration.list.config.ErmbListMigrationConfig;
import com.rssl.phizic.business.ermb.migration.list.event.ReverseMigrationEvent;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.OperationBase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * �������� ������ �������� �������� �� ���/��� � ����
 * @author Puzikov
 * @ created 09.12.13
 * @ $Author$
 * @ $Revision$
 */

public class RollbackListMigrationOperation extends OperationBase
{
	private static final String SUCCESS_MESSAGE = "����� �������� ������� �������. ���������� �� ���������� " +
			"�������� � ����� ��������� ���� Revert_%s_N.log ��� ������� ����� ���������� ��������";

	//���� ������
	private Calendar rollbackDate;

	/**
	 * ������������������� ��������
	 * @param rollbackDate ���� ������
	 */
	public void initialize(Calendar rollbackDate)
	{
		this.rollbackDate = rollbackDate;
	}

	/**
	 * ��������� ������� ������
	 */
	public void rollback() throws BusinessLogicException, BusinessException
	{
		if (!ConfigFactory.getConfig(ErmbListMigrationConfig.class).getRollbackAccess())
			throw new BusinessLogicException("��� ������� � �������� ������ ��������");

		try
		{
			EventSender.getInstance().sendEvent(new ReverseMigrationEvent(rollbackDate));
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		writeLog(rollbackDate);
	}

	/**
	 * @return ������ ������ � ������ ��������� ����������
	 */
	public String getStatus()
	{
		String currentDateString = new SimpleDateFormat("dd_MM_yyyy").format(Calendar.getInstance().getTime());
		return String.format(SUCCESS_MESSAGE, currentDateString);
	}

	private void writeLog(Calendar startTime) throws BusinessException
	{
		try
		{
			LogWriter logWriter = OperationLogFactory.getLogWriter();
			DefaultLogDataReader reader = new DefaultLogDataReader("�� ��������. �������� ������ ��������.");
			String rollbackDateString = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(rollbackDate.getTime());
			SimpleLogParametersReader parametersReader = new SimpleLogParametersReader("����� �������� �� ���� " + rollbackDateString);
			reader.addParametersReader(parametersReader);
			logWriter.writeActiveOperation(reader, startTime, Calendar.getInstance());
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
