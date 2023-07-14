package com.rssl.phizicgate.iqwave.documents;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizgate.common.payments.documents.AbstractDocumentSenderBase;
import com.rssl.phizgate.common.payments.documents.StateUpdateInfoImpl;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.config.SpecificGateConfig;
import com.rssl.phizic.gate.documents.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.impl.MessageHeadImpl;
import com.rssl.phizic.gate.payments.ExternalCardsTransferToOtherBank;
import com.rssl.phizic.gate.payments.ExternalCardsTransferToOurBank;
import com.rssl.phizic.gate.payments.InternalCardsTransfer;
import com.rssl.phizic.gate.payments.autopayment.CreateAutoPayment;
import com.rssl.phizic.gate.payments.autopayment.EditAutoPayment;
import com.rssl.phizic.gate.payments.autopayment.RefuseAutoPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.listener.ExecutionResultProcessorBase;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import com.rssl.phizicgate.iqwave.messaging.IQWaveMessageHandler;
import com.rssl.phizicgate.iqwave.utils.DocumentOfflineHandlerHelper;
import com.rssl.phizgate.common.payments.PaymentCompositeId;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;

/**
 * @author krenev
 * @ created 14.05.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class IQWaveAbstractDocumentSender extends AbstractDocumentSenderBase
{
	/**
	 * список разрешенных классов, для выполнения обновления.
	 */
    private static final Set<Class> allowedClasses = new HashSet<Class>();
    private static final Map<String, Pair<DocumentEvent, String>> CODE_2_EVENTS = new HashMap<String, Pair<DocumentEvent, String>>();

	static
	{
		allowedClasses.add(CardPaymentSystemPayment.class);
		allowedClasses.add(InternalCardsTransfer.class);
		allowedClasses.add(ExternalCardsTransferToOurBank.class);
		allowedClasses.add(ExternalCardsTransferToOtherBank.class);
		allowedClasses.add(CreateAutoPayment.class);
		allowedClasses.add(EditAutoPayment.class);
		allowedClasses.add(RefuseAutoPayment.class);

		CODE_2_EVENTS.put("2", new Pair<DocumentEvent, String>(DocumentEvent.EXECUTE, null));
		CODE_2_EVENTS.put("3", new Pair<DocumentEvent, String>(DocumentEvent.REFUSE, "Вам отказано в исполнении операции. Обратитесь, пожалуйста, в банк."));
	}

	protected IQWaveAbstractDocumentSender(GateFactory factory)
	{
		super(factory);
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		//nothing to do
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Отзыв не поддерживается.");
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		//nothing to do
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		//nothing to do
	}

	public StateUpdateInfo execute(GateDocument document) throws GateException, GateLogicException
	{
		if (ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).isMultiblockMode())
		{
			StateUpdateInfo stateUpdateInfo = super.execute(document);
			if (stateUpdateInfo != null)
				return stateUpdateInfo;
		}

		Calendar allowedUpdateDate = getAllowedUpdateDate(document.getClientOperationDate(), ConfigFactory.getConfig(SpecificGateConfig.class).getDocumentUpdateWaitingTime());
		if (allowedUpdateDate != null)
			return new StateUpdateInfoImpl(allowedUpdateDate);

		if (!allowedClasses.contains(document.getType()))
			throw new GateException("Неверный тип платежа - ожидается CardPaymentSystemPayment");

		AbstractCardTransfer payment = (AbstractCardTransfer) document;
		WebBankServiceFacade serviceFacade   = GateSingleton.getFactory().service(WebBankServiceFacade.class);

		GateMessage message = serviceFacade.createRequest(Constants.OPER_STATUS_REQUEST);

		PaymentCompositeId compositeId = new  PaymentCompositeId(payment.getExternalId());
		Document response   = serviceFacade.sendOnlineMessage(message, new MessageHeadImpl(null, null, null, compositeId.getMessageId(), null, null));

		Pair<DocumentEvent, String> event = CODE_2_EVENTS.get(XmlHelper.getSimpleElementValue(response.getDocumentElement(), "StatusCode"));
		return event != null ? new StateUpdateInfoImpl(event.getFirst().name(), event.getSecond()) : null;
	}

	/**
	 * получить внешний идентифкатор документа из ответа
	 * @param response ответ биллнга
	 * @return внешний  идентифкатор документа
	 * todo смотри BUG016540: (None)Доработать MessagingServiceSupport для передачи все информации.
	 */
	protected String getExternalId(Document response)
	{
		Element root = response.getDocumentElement();
		return new PaymentCompositeId(
				XmlHelper.getSimpleElementValue(root, IQWaveMessageHandler.PARENT_MESSAGE_ID_TAG),
				XmlHelper.getSimpleElementValue(root, IQWaveMessageHandler.PARENT_MESSAGE_DATE_TAG))
				.toString();
	}

	/**
	 * Установить в сообщение поле "код мобильного банка"
	 * Перед вызовом метода нужно проставить в документ код мобильного банка
	 * Для этого нужно прописать в стейт-машине вызов хендлера, который проставляет код мобильного банка com.rssl.phizic.business.ermb.auxiliary.messaging.paymentSms.MBOperCodeSettingHandler
	 * @param message - сообщение
	 * @param payment - документ
	 */
	protected void fillMBOperCodeField(GateMessage message, SynchronizableDocument payment)
	{
		String mbOperCode = payment.getMbOperCode();
		if (StringHelper.isNotEmpty(mbOperCode))
			message.addParameter(Constants.MB_OPER_CODE_FIELD_NAME, mbOperCode);
	}

	protected void fillPaymentData(GateDocument document, String additInfo) throws GateException, GateLogicException
	{
		if (!(document instanceof SynchronizableDocument))
			throw new GateException(String.format(NOT_SYNCH_DOC_ERROR_TEMPLATE, document.getId()));

		SynchronizableDocument synchronizableDocument = (SynchronizableDocument) document;
		ExecutionResultProcessorBase processor = DocumentOfflineHandlerHelper.getExecutionOfflineRequestHandler(getConfirmRequestName(document));
		try
		{
			processor.fillPaymentData(synchronizableDocument, XmlHelper.parse(additInfo));
		}
		catch (Exception e)
		{
			log.error("Ошибка при разборе информации о документе.", e);
			return;
		}
	}

	/**
	 * @return название запроса на исполнение документа, присылаемое ВС
	 */
	protected abstract String getConfirmRequestName(GateDocument document);
}
