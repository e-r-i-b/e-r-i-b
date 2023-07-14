package com.rssl.phizicgate.rsretailV6r20.job;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.errors.ErrorMessage;
import com.rssl.phizic.errors.ErrorMessagesMatcher;
import com.rssl.phizic.errors.ErrorSystem;
import com.rssl.phizic.errors.ErrorType;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.documents.SystemWithdrawDocument;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.service.StartupServiceDictionary;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.rsretailV6r20.senders.ExternalKeyCreator;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InvalidClassException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.transform.TransformerException;

/**
 * @author Omeliyanchuk
 * @ created 14.03.2008
 * @ $Author$
 * @ $Revision$
 */

public class CarryPaymentsJob implements StatefulJob
{
	public static final String OK_ERROR_CODE   = "0";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	/**
	 * инициализация джоба
	 */
	public CarryPaymentsJob() throws JobExecutionException, InvalidClassException
	{
		StartupServiceDictionary serviceStarter = new StartupServiceDictionary();
		serviceStarter.startMBeans();
	}

	public void execute(JobExecutionContext context)
	{
		if(!ConfigFactory.getConfig(DocumentConfig.class).isAutoPayment())
			return;

		log.debug("Запуск синхрнизации статусов");

		try
		{
			log.debug("Начинаем проводить платежи.");
			WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
			GateMessage message = service.createRequest("carryDeposit_q");
			Document response = service.sendOnlineMessage(message, null);

			UpdateDocumentService updateService = GateSingleton.getFactory().service(UpdateDocumentService.class);
			NodeList list = XmlHelper.selectNodeList(response.getDocumentElement(),"document");
			int len = list.getLength();
			DocumentService documentService = GateSingleton.getFactory().service(DocumentService.class);
			for (int i = 0; i < len; ++i)
			{
				Element document = (Element)list.item(i);
				String errorCode = XmlHelper.selectSingleNode(document, "errorCode").getTextContent();

				String applicationKind = XmlHelper.selectSingleNode(document, "applicationKind").getTextContent();
				String applicationKey = XmlHelper.selectSingleNode(document, "applicationKey").getTextContent();

				ExternalKeyCreator externalKeyCreator = new ExternalKeyCreator(applicationKind, applicationKey);
				SynchronizableDocument synchronizableDocument = updateService.find(externalKeyCreator.createId());
				Map<String, Object> additionalFields = new HashMap<String, Object>();

				if(OK_ERROR_CODE.equals( errorCode))
				{
					//if(synchronizableDocument instanceof PaymentSystemPayment) - так нельзя, в случае веб-сервисов реализуем все интерфейсы
					if(synchronizableDocument.getType()==(AccountPaymentSystemPayment.class))
					{
						//пропускаем двухшаговые платежи , из-за возможности рассинхронизации статусов(используенм только оповещения).
						log.debug("Пропустили результат автопроводки двухшагового платежа " + synchronizableDocument.getId());
						continue;
					}
					updateService.update(synchronizableDocument, new DocumentCommand(DocumentEvent.EXECUTE, additionalFields));
				}
				else
				{
					String errorText = XmlHelper.selectSingleNode(document, "errorText").getTextContent();
					ErrorMessage errorMessage = getErrorMessage(errorCode);
					//Клиентская ошибка. В этом случае, документ помечаем как отказанный. Удаляем платеж из отложенных.
					//Клиенту выводим ошибку, полученную из макроса проводки.

					if (errorMessage!=null && errorMessage.getErrorType().equals(ErrorType.Client))
					{
						additionalFields.put(DocumentCommand.ERROR_CODE, errorMessage.getMessage() == null ? errorText : errorMessage.getMessage());
						updateService.update(synchronizableDocument, new DocumentCommand(DocumentEvent.REFUSE, additionalFields));

						//сформировать WithdrawDocument
						SystemWithdrawDocument widthDrawDocument = new SystemWithdrawDocument(synchronizableDocument);
						documentService.send(widthDrawDocument);						
					}
					//Системная ошибка. В этом случае документу ставим статус Ошибка в икфл.
					//Документ не отзываем, т.к. клиент, увидев новый статус позвонит в банк
					else if (errorMessage!=null &&  errorMessage.getErrorType().equals(ErrorType.Validation))
					{
						additionalFields.put(DocumentCommand.ERROR_CODE, errorMessage.getMessage() == null ? errorText : errorMessage.getMessage());
						updateService.update(synchronizableDocument, new DocumentCommand(DocumentEvent.ERROR, additionalFields));
					}
					//Третий вид ошибки - Временная ошибка. Ошибка, которая может исчезнуть. В этом случае в ИКФЛ с документом ничего не делаем.

					log.info("Ошибка при проводке платежа c идентификатором:" + synchronizableDocument.getId() +
						  		".Код ошибки:"+ errorCode + ".Текст ошибки:" + errorText);
				}
			}
			log.debug("Проводка платежей закончена.");
		}
		//Так как джоб работает на гейте, то исключения дальше не пробрасываем.
		catch (GateException ex)
		{
			log.error(ex);
		}
		catch (GateLogicException ex)
		{
			log.error(ex);
		}
		catch (TransformerException e)
		{
			log.error( new JobExecutionException("Ошибка при обработке платежей", e));
		}
		catch (Throwable tw)
		{
			log.error(tw);
		}
	}

	private ErrorMessage getErrorMessage(String errorCode) throws JobExecutionException
	{
		// todo заглушка, пока не будет выделено необходимых кодов ошибок.
		if ("12345".equals(errorCode))
		{

		}
		try
		{
			ErrorMessagesMatcher matcher = ErrorMessagesMatcher.getInstance();
			List<ErrorMessage> errorMessages = matcher.matchMessage(errorCode, ErrorSystem.Retail);
			return (errorMessages.isEmpty())?null:errorMessages.get(0);
		}
		catch(Exception e)
		{
			throw new JobExecutionException(e);
		}
	}
}
