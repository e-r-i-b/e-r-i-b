package com.rssl.phizicgate.bars.payments;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.jmx.BarsConfig;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.bars.messaging.BarsMessageData;
import com.rssl.phizicgate.bars.messaging.BarsMessageGenerator;
import com.rssl.phizicgate.bars.ws.jaxrpc.*;
import org.apache.commons.lang.ArrayUtils;

import java.rmi.RemoteException;
import java.util.Date;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.Stub;

/**
 * @author osminin
 * @ created 06.04.2011
 * @ $Author$
 * @ $Revision$
 * ���������� ������� � ��������� ��������� ����� JAXRPC
 */
public class JAXRPCReceiverNameServiceImpl extends ReceiverNameServiceImpl
{
	public JAXRPCReceiverNameServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * ������������ ����������
	 * @param account ����� ����� ���������� �������
	 * @param bic ��� ���������� �������
	 * @param tb  ����� �� ���������� �������
	 * @return ������������
	 */
	public String getReceiverName(String account, String bic, String tb) throws GateException, GateLogicException
	{
		BarsMessageGenerator messageGenerator = new BarsMessageGenerator();
		BarsMessageData messageData = (BarsMessageData) messageGenerator.buildMessage(account, bic, tb);
		ArrayOfXsbParameter arrayParameter;
		XsbCheckNames stub;
		try
		{
			BarsConfig config = ConfigFactory.getConfig(BarsConfig.class);

			XsbCheckNamesService service = new XsbCheckNamesService_Impl();
			stub = service.getXsbCheckNames();
			((com.sun.xml.rpc.client.StubBase) stub)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, config.getBarsUrl(tb));

			XsbParameter[] parameters= new XsbParameter[1];
			parameters[0] = new XsbParameter("client", config.getBarsPartCode(tb));
			arrayParameter = new ArrayOfXsbParameter(parameters);
		}
		catch (ServiceException e)
		{
			throw new GateException("��� �������������� � �������� ���� ��������� ������.", e);
		}
		BarsMessageData responce = new BarsMessageData();
		Long executionTime = null;
		Long begin =  new Date().getTime();
		try
		{
			XsbRemoteClientNameReturn barsResponse = stub.readRemoteClientName(messageData.getBodyAsString("widows-1251"), arrayParameter);
			executionTime = new Date().getTime() - begin;
			XsbRemoteClientNameResult[] result = barsResponse.getDocuments().getXsbRemoteClientNameResult();

			if (ArrayUtils.isEmpty(result))
			{
				//���� �� ������ ���������� � ���������� �������, ���� ������ �������� ��������� � �������
				XsbExceptionItem[] exceptions = barsResponse.getExceptionItems().getXsbExceptionItem();
				if (ArrayUtils.isEmpty(exceptions))
				{
					//� ������ ������ ���� ������ �� �������
					responce = new BarsMessageData();
					throw new GateException("� ������ �� ������� ���� �� �������� �������� ����������, � ��� ���� ����� �� �������� ������.");
				}

				//����� ������ ������ ��������������
				XsbExceptionItem exception = exceptions[0];
				responce = getExceptionResponce(exception.getExcName(), exception.getExcMessage());
				throw new GateException("� ������ �� ������� ���� �� �������� �������� ����������.");
			}

			if (result.length > 1)
			{
				//�������� - ���� ������ ������ ������ ���������� �������, �����������
				XsbExceptionItem[] exceptions = barsResponse.getExceptionItems().getXsbExceptionItem();
				if (ArrayUtils.isEmpty(exceptions))
				{
					//� ������ ������ ���� ������ �� �������
					responce = new BarsMessageData();
					throw new GateException("� ������ �� ������� ���� �������� ������ ������ �������� ����������, � ��� ���� ����� �� �������� ������.");
				}

				//����� ������ ������ ��������������
				responce = getExceptionResponce(exceptions[0].getExcName(), exceptions[0].getExcMessage());
				throw new GateException("� ������ �� ������� ���� �������� ������ ������ �������� ����������.");
			}

			//���������� �������� �������������� � ����
			responce = getResponse(result[0].getSSName(), result[0].getSInn());
			return result[0].getSSName();
		}
		catch (RemoteException e)
		{
			throw new GateException("��� �������������� � �������� ���� ��������� ������.", e);
		}
		finally
		{
			writeToLog(messageData, responce, executionTime == null ? new Date().getTime() - begin : executionTime);
		}
	}
}
