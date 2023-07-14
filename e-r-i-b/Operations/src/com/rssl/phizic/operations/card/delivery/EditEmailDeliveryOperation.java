package com.rssl.phizic.operations.card.delivery;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.FormException;
import com.rssl.common.forms.MessageDocumentLogicException;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.doc.DocumentSignature;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.common.forms.xslt.FormDataBuilder;
import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.MessageBusinessLogicException;
import com.rssl.phizic.business.documents.CardReportDeliveryClaim;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.converters.FormDataConverter;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.messaging.info.PersonalSubscriptionData;
import com.rssl.phizic.business.messaging.info.SubscriptionService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.CardState;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.operations.payment.PaymentOperationHelper;
import com.rssl.phizic.operations.payment.support.DbDocumentTarget;
import com.rssl.phizic.operations.payment.support.DocumentTarget;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;

/**
 * @author akrenev
 * @ created 05.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * Операция работы с заявкой на изменение параметров подписки
 */

public class EditEmailDeliveryOperation extends ConfirmableOperationBase
{
	private static final SubscriptionService subscriptionService = new SubscriptionService();
	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();
	private DocumentTarget target = new DbDocumentTarget();

	private Metadata metadata;
	private CardReportDeliveryClaim document;
	private StateMachineExecutor executor;
	private ConfirmableObject confirmObject;

	private void initializeDocument(DocumentSource source) throws BusinessException, BusinessLogicException
	{
		document = (CardReportDeliveryClaim) source.getDocument();
		metadata = source.getMetadata();
	}

	private void initializeExecutor() throws BusinessException, BusinessLogicException
	{
		executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()));
		executor.setStateMachineEvent(getStateMachineEvent());
		executor.initialize(document);
	}

	/**
	 * инициализация операции
	 * @param source объект для чтения документа и метаданных
	 * @throws BusinessException
	 */
	public void initialize(DocumentSource source) throws BusinessException, BusinessLogicException
	{
		initializeDocument(source);
		initializeExecutor();
	}

	/**
	 * инициализация операции
	 * @param source объект для чтения документа и метаданных
	 * @param cardId идентификатор карты
	 * @throws BusinessException
	 */
	public void initialize(DocumentSource source, Long cardId) throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		CardLink cardLink = personData.getCard(cardId);

		CardState cardState = cardLink.getCard().getCardState();
		if (cardState == CardState.blocked || cardState == CardState.delivery)
			throw new BusinessException("По карте со статусом " + cardState.getDescription() + " запрещено создавать подписку.");

		initializeDocument(source);

		document.setCardIdReportDelivery(cardLink.getId());
		document.setCardClientIdReportDelivery(cardLink.getClientId());
		document.setCardExternalIdReportDelivery(cardLink.getExternalId());
		document.setCardNumberReportDelivery(cardLink.getNumber());
		document.setCardNameReportDelivery(cardLink.getName());
		document.setContractNumber(cardLink.getContractNumber());

		BusinessDocumentOwner documentOwner = document.getOwner();
		if (documentOwner.isGuest())
			throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
		confirmObject = new CardReportDeliveryClaimConfirmObject(documentOwner.getLogin().getId());

		initializeExecutor();
	}

	/**
	 * @return документ
	 */
	public CardReportDeliveryClaim getDocument()
	{
		return document;
	}

	/**
	 * @return строка идентифицирующая метаданные
	 */
	public String getMetadataPath() throws BusinessException
	{
		return PaymentOperationHelper.calculateMetadataPath(metadata, document);
	}

	/**
	 * @return метаданные
	 */
	public Metadata getMetadata()
	{
		return metadata;
	}

	/**
	 * Источник данных документа
	 * @return  Источник значеий полей
	 * @throws BusinessException
	 */
	public FieldValuesSource getFieldValuesSource() throws BusinessException
	{
		return new DocumentFieldValuesSource(metadata, document);
	}

	/**
	 * Обновить платеж введенными данными
	 * @param formDataMap введенные данные
	 * @throws BusinessException
	 */
	public void updateDocument(Map<String, Object> formDataMap) throws BusinessException, BusinessLogicException
	{
		try
		{
			FormDataBuilder dataBuilder = new FormDataBuilder();
			dataBuilder.appentAllFileds(metadata.getForm(), formDataMap);

			XmlConverter converter = metadata.createConverter("xml");
			converter.setData(dataBuilder.getFormData());

			DOMResult result = new DOMResult();
			converter.convert(result);

			document.readFromDom((Document) result.getNode(), null);
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
		catch (MessageDocumentLogicException e)
		{
			throw new MessageBusinessLogicException(e);
		}
		catch (DocumentLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	/**
	 * Строит экранную форму платежа
	 * @param fieldValuesSource - источник значений полей
	 * @param info параметры преобразования
	 * @return HTML-описание экранной формы платежа
	 */
	public String buildFormHtml(FieldValuesSource fieldValuesSource, TransformInfo info) throws BusinessException
	{
		Source formData = new FormDataConverter(metadata.getForm(), fieldValuesSource).toFormData();
		XmlConverter converter = metadata.createConverter(info.getTransformMode());
		converter.setData(formData);
		converter.setParameter("mode", info.getTemplateMode());

		try
		{
			return converter.convert().toString();
		}
		catch (FormException e)
		{
			throw new BusinessException("Ошибка при создании HTML с экранной формой платежа " + metadata.getName() + ", id документа " + document.getId(), e);
		}
	}

	@Override
	@Transactional
	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		executor.fireEvent(new ObjectEvent(DocumentEvent.CONFIRM, ObjectEvent.CLIENT_EVENT_TYPE));
		Calendar operationDate = GregorianCalendar.getInstance();
		document.setOperationDate(operationDate);
		document.setSessionId(LogThreadContext.getSessionId());
		document.setClientOperationChannel(DocumentHelper.getChannelType());

		if (isSignatureSaveRequired())
		{
			DocumentSignature signature = getSignature();
			document.setSignature(signature);
		}

		BusinessDocumentOwner documentOwner = document.getOwner();
		if (documentOwner.isGuest())
			throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
		PersonalSubscriptionData subscriptionData = subscriptionService.findPersonalData(documentOwner.getLogin());
		if (StringHelper.isEmpty(subscriptionData.getEmailAddress()))
		{
			subscriptionData.setEmailAddress(document.getEmailReportDelivery());
			subscriptionService.changePersonalData(subscriptionData);
		}

		target.save(document);
	}

	public ConfirmableObject getConfirmableObject()
	{
		return confirmObject;
	}

	@Override
	public void setUserStrategyType(ConfirmStrategyType type)
	{
		super.setUserStrategyType(type);
		document.setConfirmStrategyType(type);
	}
}
