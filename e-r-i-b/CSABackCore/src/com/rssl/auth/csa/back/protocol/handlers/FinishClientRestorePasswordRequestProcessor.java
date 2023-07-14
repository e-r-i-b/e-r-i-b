package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.rsa.FraudMonitoringSendersFactory;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.RestorePasswordOperation;
import com.rssl.phizic.context.HeaderContext;
import com.rssl.phizic.context.RSAContext;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.rsa.exceptions.AppendNotificationException;
import com.rssl.phizic.rsa.exceptions.BlockClientOperationFraudException;
import com.rssl.phizic.rsa.exceptions.ProhibitionOperationFraudGateException;
import com.rssl.phizic.rsa.exceptions.RequireAdditionConfirmFraudGateException;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.rsa.senders.initialization.ChangePasswordInitializationData;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;
import com.rssl.phizic.rsa.senders.types.EventsType;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author krenev
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 * ���������� ������� �� ��������� �������������� ������ startRestorePasswordRq.
 *
 * ���������� ������� �������������� �������� �� �������:
 * 1) ������ �������� �������������� ������, ����� ���� �����.
 * 2) ���� ������ startRestorePasswordRq � ��� BACK
 * 3) ���������� ������������� ������������, ������� ��� ���� �� ������������� ��������.
 *    ��� Back ���������� ������ � ����������� ������������� ���������� � ������������.
 * 4) ������������ ������ ��� �������������
 * 5) ���� ������ confirmOperationRq �� ������������� ��������
 * 6) ����� ��������� ������������� ������������ � ����������� �� ���� ������ ������������ ��������� �������������� ������ ������
 * 7) ���� ������ finishRestorePasswordRq c ��������� ������ ������ ��� ��� ��� ��������, ���� ��������� �������������� ��������� ������.
 * 8) BACK ��������� �������� �������������� ������
 *
 * ��������� �������:
 * OUID		            ������������� ��������.     [1]
 * Password		        ������ ������������	        [0..1]  ���� ������ �� ������� ���������� �������������� ��� ��������� � �������� ���.
 *                                                          ��� ������ iPas ������������� �������� ������ (������ ���������� ���������) 
 *
 * ��������� ������:
 */
public class FinishClientRestorePasswordRequestProcessor extends LogableProcessorBase
{
	public static final String REQUEST_TYPE = "finishRestorePasswordRq";
	public static final String RESPONCE_TYPE = "finishRestorePasswordRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	@Override
	protected LogableResponseInfo processRequest(RequestInfo requestInfo, IdentificationContext context) throws Exception
	{
		Document body = requestInfo.getBody();
		trace("��������� ������� ������");
		String ouid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.OUID_TAG);
		String password = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.PASSWORD_TAG);
		return finishRestorePassword(ouid, password, getIdentificationContext(requestInfo));
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String ouid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.OUID_TAG);
		return LogIdentificationContext.createByOperationUID(ouid);
	}

	private LogableResponseInfo finishRestorePassword(String ouid, String password, LogIdentificationContext logIdentificationContext) throws Exception
	{
		IdentificationContext identificationContext = logIdentificationContext.getIdentificationContext();
		final RestorePasswordOperation restorePasswordOperation = identificationContext.findOperation(RestorePasswordOperation.class, ouid, RestorePasswordOperation.getLifeTime());
		sendRSAData(logIdentificationContext, restorePasswordOperation);
		trace("������ " + restorePasswordOperation.getOuid() + " ������� ��������. ��������� ��.");
		restorePasswordOperation.execute(password);
		info("������ �������������� ������ " + restorePasswordOperation.getOuid() + " ������� ���������.");
		return new LogableResponseInfo(buildSuccessResponse());
	}

	private void sendRSAData(LogIdentificationContext logIdentificationContext, RestorePasswordOperation operation) throws ProhibitionOperationFraudGateException
	{
		try
		{
			IdentificationContext context = logIdentificationContext.getIdentificationContext();
			RSAContext.initialize(operation.getRSAData());
			HeaderContext.initialize(operation.getRSAData());

			FraudMonitoringSender sender = FraudMonitoringSendersFactory.getInstance().getSender(EventsType.CHANGE_PASSWORD);
			//noinspection unchecked
			sender.initialize(new ChangePasswordInitializationData(ClientDefinedEventType.RECOVER_PASSWORD, operation.getLogin(), Utils.getTBByCbCode(context.getCbCode()), context.getProfile().getId()));
			sender.send();
		}
		catch (ProhibitionOperationFraudGateException e)
		{
			throw e;
		}
		catch (BlockClientOperationFraudException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			log.error(e);
		}
		finally
		{
			RSAContext.clear();
			HeaderContext.clear();
		}
	}
}
