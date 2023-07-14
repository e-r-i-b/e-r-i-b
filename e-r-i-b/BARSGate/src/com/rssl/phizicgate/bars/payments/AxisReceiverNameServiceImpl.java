package com.rssl.phizicgate.bars.payments;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.jmx.BarsConfig;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.bars.messaging.BarsMessageData;
import com.rssl.phizicgate.bars.messaging.BarsMessageGenerator;
import com.rssl.phizicgate.bars.ws.axis.*;
import org.apache.commons.lang.ArrayUtils;

import java.rmi.RemoteException;
import java.util.Date;
import javax.xml.rpc.ServiceException;

/**
 * @author osminin
 * @ created 06.04.2011
 * @ $Author$
 * @ $Revision$
 * Реализация сервиса с отправкой сообщений через AXIS
 */
public class AxisReceiverNameServiceImpl extends ReceiverNameServiceImpl
{
	public AxisReceiverNameServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Наименование получателя
	 * @param account номер счета получателя платежа
	 * @param bic бик получателя платежа
	 * @param tb  номер ТБ получателя платежа
	 * @return наименование
	 */
	public String getReceiverName(String account, String bic, String tb) throws GateException, GateLogicException
	{
		BarsMessageGenerator messageGenerator = new BarsMessageGenerator();
		BarsMessageData messageData = (BarsMessageData) messageGenerator.buildMessage(account, bic, tb);
		XsbCheckNamesSoapBindingStub stub;
		XsbParameter[] parameters;
		try
		{
			BarsConfig config = ConfigFactory.getConfig(BarsConfig.class);
			//добавить рефреш для конфига
			XsbCheckNamesServiceLocator locator = new XsbCheckNamesServiceLocator();
			stub = (XsbCheckNamesSoapBindingStub) locator.getXsbCheckNames();
			stub._setProperty(org.apache.axis.client.Stub.ENDPOINT_ADDRESS_PROPERTY, config.getBarsUrl(tb));

			parameters= new XsbParameter[1];
			parameters[0] = new XsbParameter("client", config.getBarsPartCode(tb));
		}
		catch (ServiceException e)
		{
			throw new GateException("При взаимодействии с системой БАРС произошла ошибка.", e);
		}
		BarsMessageData responce = new BarsMessageData();
		Long executionTime = null;
		Long begin =  new Date().getTime();
		try
		{
			XsbRemoteClientNameReturn barsResponse = stub.readRemoteClientName(messageData.getBodyAsString("widows-1251"), parameters);
			executionTime = new Date().getTime() - begin;
			XsbRemoteClientNameResult[] result = barsResponse.getDocuments();

			if (ArrayUtils.isEmpty(result))
			{
				//если не пришло информации о получателе платежа, БАРС должен прислать сообщение с ошибкой
				XsbExceptionItem[] exceptions = barsResponse.getExceptionItems();
				if (ArrayUtils.isEmpty(exceptions))
				{
					//в данном случае БАРС ничего не прислал
					responce = new BarsMessageData();
					throw new GateException("В ответе из системы БАРС не получено описание поставщика, и при этом ответ не содержит ошибок.");
				}

				//берем первую ошибку взаимодействия
				XsbExceptionItem exception = exceptions[0];
				responce = getExceptionResponce(exception.getExcName(), exception.getExcMessage());
				throw new GateException("В ответе из системы БАРС не получено описание поставщика.");
			}

			if (result.length > 1)
			{
				//ситуация - если пришло больше одного получателя платежа, некорректна
				XsbExceptionItem[] exceptions = barsResponse.getExceptionItems();
				if (ArrayUtils.isEmpty(exceptions))
				{
					//в данном случае БАРС ничего не прислал
					responce = new BarsMessageData();
					throw new GateException("В ответе из системы БАРС получено больше одного описания поставщика, и при этом ответ не содержит ошибок.");
				}

				//берем первую ошибку взаимодействия
				responce = getExceptionResponce(exceptions[0].getExcName(), exceptions[0].getExcMessage());
				throw new GateException("В ответе из системы БАРС получено больше одного описания поставщика.");
			}

			//корректная ситуация взаимодействия с БАРС
			responce =  getResponse(result[0].getSSName(), result[0].getSInn());
			return result[0].getSSName();
		}
		catch (RemoteException e)
		{
			throw new GateException("При взаимодействии с системой БАРС произошла ошибка.", e);
		}
		finally
		{
			writeToLog(messageData, responce, executionTime == null ? new Date().getTime() - begin : executionTime);
		}
	}
}
