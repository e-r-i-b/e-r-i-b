package com.rssl.phizic.business.fraudMonitoring.utils;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.config.RSAConfig;
import com.rssl.phizic.rsa.integration.ws.notification.generated.ResolutionTypeList;
import com.rssl.phizic.rsa.notifications.FraudNotification;
import com.rssl.phizic.rsa.notifications.FraudNotificationService;
import com.rssl.phizic.rsa.senders.UpdateActivitySender;
import com.rssl.phizic.rsa.senders.initialization.UpdateActivityInitializationData;

/**
 * @author tisov
 * @ created 30.06.15
 * @ $Author$
 * @ $Revision$
 * ������ �� ����������� ����-�����������
 */
public class FraudMonitoringHelper
{
	private static final String IMSI_OPERATION_DESCRIPTION                                  = "�������� ������ ���-�����";
	private static final String IMPOSSIBLE_PERFORM_OPERATION_DESCRIPTION                    = "���������� ������";

	private static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * ��������� �� ���������� ���������� ������� ������������ ���������� ����� ��� ����������� ��������
	 * @return
	 */
	public static boolean isOfficerTextSendingActive()
	{
		return RSAConfig.getInstance().isSendingVerdictCommentActive();
	}

	/**
	 * ��������� ���������� � ����������� ���� ������
	 * @param document ��������
	 */
	public static void saveIMSIOperationNotification(BusinessDocument document)
	{
		try
		{
			FraudNotificationService.getInstance().save(getIMSIOperationNotification(document));
		}
		catch (Exception e)
		{
			LOG.error("������ ���������� ���������� � ����� ���-�����.", e);
		}
	}

	private static FraudNotification getIMSIOperationNotification(BusinessDocument document) throws GateLogicException, GateException
	{
		UpdateActivitySender sender = new UpdateActivitySender();
		sender.initialize(new UpdateActivityInitializationData(document.getClientTransactionId(), ResolutionTypeList.UNKNOWN, IMSI_OPERATION_DESCRIPTION, document.getId()));
		return new FraudNotification(sender.getRequestBody());
	}

	/**
	 * ��������� ���������� � ����������� �������������� ������
	 * @param document ��������
	 */
	public static void saveImpossiblePerformOperationNotification(BusinessDocument document)
	{
		try
		{
			FraudNotificationService.getInstance().save(getImpossiblePerformOperationNotification(document));
		}
		catch (Exception e)
		{
			LOG.error("������ ���������� ���������� � ����������� �������������� ������.", e);
		}
	}

	private static FraudNotification getImpossiblePerformOperationNotification(BusinessDocument document) throws GateLogicException, GateException
	{
		UpdateActivitySender sender = new UpdateActivitySender();
		sender.initialize(new UpdateActivityInitializationData(document.getClientTransactionId(), ResolutionTypeList.UNKNOWN, IMPOSSIBLE_PERFORM_OPERATION_DESCRIPTION, document.getId()));
		return new FraudNotification(sender.getRequestBody());
	}
}
