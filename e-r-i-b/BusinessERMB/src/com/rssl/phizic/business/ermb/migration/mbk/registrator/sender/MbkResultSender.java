package com.rssl.phizic.business.ermb.migration.mbk.registrator.sender;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.migration.mbk.MBKRegistrationResult;
import com.rssl.phizic.business.ermb.migration.mbk.MBKRegistrationResultDatabaseService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.Collections;

/**
 * �������� � ��� ���������� ��������� ���������
 * @author Puzikov
 * @ created 09.07.14
 * @ $Author$
 * @ $Revision$
 */

public class MbkResultSender
{
	private final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private final MBKRegistrationResultDatabaseService regService = new MBKRegistrationResultDatabaseService();

	/**
	 * ��������� ������� � ��� ��������� ��������� ���������
	 * @param result ���������
	 */
	public void send(ProcessResult result)
	{
		saveResult(result);
		sendResultToMbk(result);
		deleteResult(result);
	}

	private void saveResult(ProcessResult result)
	{
		try
		{
			MBKRegistrationResult registrationResult = new MBKRegistrationResult();
			registrationResult.id = result.getRegistrationId();
			registrationResult.resultCode = result.getResultCode();
			registrationResult.errorDescr = result.getErrorDescription();
			regService.addOrUpdate(registrationResult);
		}
		catch (BusinessException e)
		{
			log.error("�� ������� �������� ������ ����������� �� id=" + result.getRegistrationId(), e);
		}
	}

	private void sendResultToMbk(ProcessResult result)
	{
		@SuppressWarnings("deprecation")
		MobileBankService service = GateSingleton.getFactory().service(MobileBankService.class);
		try
		{
			service.sendMbRegistrationProcessingResult(result.getRegistrationId(), result.getResultCode(), result.getErrorDescription());
		}
		catch (GateException e)
		{
			log.error("�� ������� ��������� ��������� ��������� ����������� � ���", e);
		}
		catch (GateLogicException e)
		{
			log.error("�� ������� ��������� ��������� ��������� ����������� � ���", e);
		}
	}

	private void deleteResult(ProcessResult result)
	{
		try
		{
			regService.removeByIds(Collections.singletonList(result.getRegistrationId()));
		}
		catch (BusinessException e)
		{
			log.error("�� ������� ������� ������ ����������� �� id=" + result.getRegistrationId(), e);
		}
	}
}
