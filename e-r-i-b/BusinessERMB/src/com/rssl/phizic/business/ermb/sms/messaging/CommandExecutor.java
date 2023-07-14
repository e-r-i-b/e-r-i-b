package com.rssl.phizic.business.ermb.sms.messaging;

import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.session.PersonSession;
import com.rssl.phizic.text.MessageComposer;
import com.rssl.phizic.text.TextEngine;

import java.security.AccessControlException;
import java.util.Calendar;

/**
 * @author Gulov
 * @ created 24.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����������� �������
 */
public class CommandExecutor
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	private static final ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();

	private final ExecutorRequisites requisites;

	/**
	 * �����������
	 * @param requisites - ����������� ��������� � �������� ��� ���������� �������
	 */
	public CommandExecutor(ExecutorRequisites requisites)
	{
		this.requisites = requisites;
	}

	/**
	 * ��������� �������
	 */
	public void run()
	{
		Command command = null;
		PersonSession session = null;
		try
		{
			session = requisites.authenticate();
			command = requisites.createCommand();
			command.setModule(requisites.getModule());
			command.setPerson(session.getPerson());
			command.setPhoneTransmitter(requisites.getPhone());
			executeSmsCommand(command);
			requisites.doAfterExecute(command);
		}
		catch (UserErrorException e)
		{
			// ������, ������� ��� ������ ������������
			log.error(e.getMessage(), e);
			// ���� ���������� �������, �� ������� �� �� �������
			requisites.doAfterExecute(command);
			String phone = requisites.getPhone();
			if (phone != null)
				SmsSenderUtils.notifyClientMessage(requisites.getPhone(), e.getTextMessage());
		}
		catch (AccessControlException e)
		{
			// ������ ������� � ��������
			log.error(e.getMessage(), e);
			// ���� ���������� �������, �� ������� �� �� �������
			requisites.doAfterExecute(command);
			// TODO: (����) �������� ���������. ����������� ������� �.
			// TODO: (����) ��������� -> � ��������� message-composer. ����������� ������� �.
			String phone = requisites.getPhone();
			if (phone != null)
			{
				MessageComposer messageComposer = createMessageComposer();
				SmsSenderUtils.notifyClientMessage(phone, messageComposer.buildAccessControlErrorMessage());
			}
		}
		catch (InactiveExternalSystemException e)
		{
			// ������ �� �������� ���.
			log.error(e.getMessage(), e);
			// ��������� ������� � ����������, ���� ��� �� ���������� �������
			requisites.handleInactiveExternalSystemException(command, createMessageComposer());
		}
		catch (Exception e)
		{
			// ����������� ������
			log.error(e.getMessage(), e);
			// ���� ���������� �������, �� ������� �� �� �������
			requisites.doAfterExecute(command);
			// TODO: (����) �������� ���������. ����������� ������� �.
			// TODO: (����) ��������� -> � ��������� message-composer. ����������� ������� �.
			String phone = requisites.getPhone();
			if (phone != null)
			{
				MessageComposer messageComposer = createMessageComposer();
				SmsSenderUtils.notifyClientMessage(phone, messageComposer.buildFatalErrorMessage(command));
			}
		}
	}

	private void executeSmsCommand(Command command)
	{
		if (log.isDebugEnabled())
			log.debug("��������� ���-������� " + command);

		Calendar start = Calendar.getInstance();
		try
		{
			command.execute();
		}
		finally
		{
			Calendar end = Calendar.getInstance();
			writeToUserLog(command, start, end);
		}
	}

	private MessageComposer createMessageComposer()
	{
		TextEngine textEngine = requisites.getModule().getTextEngine();
		return textEngine.createMessageComposer();
	}

	// TODO: (����) ������ � WorkManager. ����������� ����� �.
	private void writeToUserLog(Command command, Calendar start, Calendar end)
	{
		LogWriter logWriter = OperationLogFactory.getLogWriter();
		DefaultLogDataReader reader = new DefaultLogDataReader(command.toString());
		reader.setOperationKey(command.getCommandName());
		// TODO: (����) ����������� LogParametersReader, ������� ������ ��������� �� �����, ���������� ������ �������. ����������� ����� �.
//		reader.addParametersReader(new SimpleLogParametersReader(command));
		try
		{
			logWriter.writeActiveOperation(reader, start, end);
		}
		catch (Exception e)
		{
			log.error("������ ��� ������ � ������ �������� ������������", e);
		}
	}
}
