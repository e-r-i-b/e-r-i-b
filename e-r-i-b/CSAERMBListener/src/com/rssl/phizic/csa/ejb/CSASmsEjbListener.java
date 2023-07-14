package com.rssl.phizic.csa.ejb;

import com.rssl.auth.csa.utils.CSAResponseUtils;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.FailureIdentificationException;
import com.rssl.auth.csa.wsclient.exceptions.PhoneRegistrationNotActiveException;
import com.rssl.phizic.common.type.SmsXmlRequest;
import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.csa.exceptions.CSASmsProcessingException;
import com.rssl.phizic.csa.exceptions.CSASmsProcessingLogicException;
import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.phizic.utils.PhoneNumberFormat;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;

/**
 * @author osminin
 * @ created 24.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Слушатель очереди смс-сообщений ЦСА
 */
public class CSASmsEjbListener extends CSAEjbListenerBase
{
	private static final String FAILURE_IDENTIFICATION_ERROR_MESSAGE = "Ваш запрос не выполнен, так как к данному номеру телефона не подключен Мобильный банк. Подключите услугу самостоятельно через банкомат, устройство самообслуживания или обратитесь в ближайшее отделение Сбербанка. Вся информация и адреса на сайте www.sberbank.ru и по телефону 8-800-555-5550.";

	@Override
	protected boolean writeAvailable()
	{
		return ConfigFactory.getConfig(CSAFrontConfig.class).isSmsMessageLogAvailable();
	}

	@Override
	protected String getMessageType()
	{
		return "sos-Sms";
	}

	@Override
	protected MQInfo getMQInfo(String message) throws Exception
	{
		//получаем номер телефона из запроса
		String phoneNumber = getPhoneNumber(message);
		//получаем информацию об узле из ЦСА по номеру телефона
		NodeInfo nodeInfo = getNodeInfo(phoneNumber);
		//возвращаем информацию об очереди канала слушателя
		return getChannelMQInfo(nodeInfo);
	}

	/**
	 * Получить информацию об очереди для канала слушателя
	 * @param nodeInfo информация об узле
	 * @return информация об очереди
	 */
	protected MQInfo getChannelMQInfo(NodeInfo nodeInfo)
	{
		return nodeInfo.getSmsMQ();
	}

	/**
	 * Получить номер телефона из запроса
	 * @param message запрос
	 * @return номер телефона
	 * @throws Exception
	 */
	protected String getPhoneNumber(String message) throws Exception
	{
		SmsXmlRequest smsXmlRequest = sosMessageHelper.getSmsXmlRequest(message);
		//Пишем сообщение в лог
		writeMessageToLog(message, smsXmlRequest.getRqUID());

		return PhoneNumberFormat.MOBILE_INTERANTIONAL.format(smsXmlRequest.getPhone());
	}

    private NodeInfo getNodeInfo(String phoneNumber) throws CSASmsProcessingException, CSASmsProcessingLogicException
    {
	    try
	    {
		    Document response = CSABackRequestHelper.sendFindProfileNodeByPhoneRq(phoneNumber);
		    return CSAResponseUtils.createNodeInfo(response.getDocumentElement());
	    }
	    catch (PhoneRegistrationNotActiveException e)
	    {
		    log.error(e.getMessage(), e);
		    throw new CSASmsProcessingLogicException(e.getMessage(), phoneNumber, e);
	    }
	    catch (FailureIdentificationException e)
	    {
		    log.error("Телефон " + phoneNumber + " не зарегистрирован для работы с ЕРМБ", e);
		    throw new CSASmsProcessingLogicException(FAILURE_IDENTIFICATION_ERROR_MESSAGE, phoneNumber, e);
	    }
	    catch (BackLogicException e)
	    {
		    log.error(e.getMessage(), e);
		    throw new CSASmsProcessingLogicException(e.getMessage(), phoneNumber, e);
	    }
	    catch (BackException e)
	    {
		    log.error("Ошибка обработки запроса в ЦСА", e);
		    throw new CSASmsProcessingException(e, phoneNumber);
	    }
	    catch (TransformerException e)
	    {
		    log.error("Ошибка разбора ответа ЦСА", e);
		    throw new CSASmsProcessingException(e, phoneNumber);
	    }
    }
}
