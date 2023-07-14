package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.doc.DocumentSignature;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.forms.DocumentContext;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.converters.FormDataConverter;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.form.AtmPaymentFormBuilder;
import com.rssl.phizic.business.documents.payments.form.MobilePaymentFormBuilder;
import com.rssl.phizic.business.documents.payments.form.PaymentFormBuilder;
import com.rssl.phizic.business.documents.payments.form.SocialPaymentFormBuilder;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.documents.strategies.DocumentAction;
import com.rssl.phizic.business.documents.strategies.DocumentLimitManager;
import com.rssl.phizic.business.documents.strategies.ProcessDocumentStrategy;
import com.rssl.phizic.business.documents.strategies.limits.*;
import com.rssl.phizic.business.documents.strategies.monitoring.FraudMonitoringConfirmDocumentStrategy;
import com.rssl.phizic.business.documents.strategies.monitoring.FraudMonitoringDocumentStrategy;
import com.rssl.phizic.business.documents.strategies.monitoring.FraudMonitoringPreConfirmDocumentStrategy;
import com.rssl.phizic.business.fraudMonitoring.exceptions.ProhibitionOperationFraudException;
import com.rssl.phizic.business.fraudMonitoring.exceptions.RequireAdditionConfirmFraudException;
import com.rssl.phizic.business.fraudMonitoring.utils.FraudMonitoringHelper;
import com.rssl.phizic.business.informers.DocumentStateInformer;
import com.rssl.phizic.business.informers.ExternalServiceProviderByTemplateDocumentStateInformer;
import com.rssl.phizic.business.informers.InternalServiceProviderByTemplateDocumentStateInformer;
import com.rssl.phizic.business.limits.*;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.payments.BusinessOfflineDocumentException;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.statemachine.MachineState;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;
import com.rssl.phizic.business.util.MaskingInfo;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.exceptions.PostConfirmCalcCommission;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.messaging.MessageTemplateType;
import com.rssl.phizic.messaging.MessagingHelper;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.operations.payment.support.DbDocumentTarget;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.web.util.MoneyFunctions;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.w3c.dom.Document;

import java.util.*;
import javax.xml.transform.Source;

import static com.rssl.phizic.business.documents.strategies.limits.Constants.TIME_OUT_ERROR_MESSAGE;

/**
 * Подтверждение платежа сделанного по форме
 *
 * @author Evgrafov
 * @ created 06.12.2005
 * @ $Author: khudyakov $
 * @ $Revision: 85996 $
 */
public class ConfirmFormPaymentOperation extends ConfirmableOperationBase implements ConfirmFormOperation
{
	private boolean FNS = false;
	private boolean guest;
	private boolean mobileBankExist;

	protected StateMachineExecutor executor;
	protected BusinessDocument document;
	protected Metadata metadata;
	protected ClientAccumulateLimitsInfo limitsInfo;
	private static final SimpleService simpleService = new SimpleService();
	protected Limit limit; //лимит, который сработал при подтверждении
	protected Money limitAccumulatedAmount; //накопленная сумма по лимиту

	@SuppressWarnings("ProtectedField")
	protected final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();

	public ConfirmableObject getConfirmableObject()
	{
		return document;
	}

	public BusinessDocument getDocument()
	{
		return document;
	}

	public void initialize(DocumentSource source) throws BusinessException, BusinessLogicException
	{
		document = source.getDocument();
		metadata = source.getMetadata();
		if (metadata.getForm().getName().equals(FormConstants.FNS_PAYMENT_FORM))
			setFNS(true);
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		guest = personData.isGuest() && "ExtendedLoanClaim".equals(document.getFormName());
		if (guest)
		{
			mobileBankExist = personData.isMB();
		}

		executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(document.getFormName()), getMessageCollector(), getStateMachineEvent());
		executor.initialize(document);

		// предварительно нужно посчитать лимиты для разных текстовочек на форме
		if (!guest)
			limitsInfo = DocumentLimitManager.buildLimitAmountInfoByPerson(getPerson(), LimitHelper.getChannelType(document));
	}

	public Metadata getMetadata()
	{
		return metadata;
	}

	/**
	 * @return предварительно расчитанные суммы по лимитам для текущего документа
	 */
	public ClientAccumulateLimitsInfo getClientAccumulateLimitsInfo()
	{
		return limitsInfo;
	}

	public String getMetadataPath() throws BusinessException
	{
		return PaymentOperationHelper.calculateMetadataPath(metadata, document);
	}

	/** Преобразование платежа в данные формы */
	public Source createFormData() throws BusinessException
	{
		//перед преобразованием, переинициализируем formDataConverter, поскольку поля документа могли измениться
		FieldValuesSource fieldValuesSource = new DocumentFieldValuesSource(metadata, document);
		if(MaskPaymentFieldUtils.isRequireMasking(document))
			fieldValuesSource = MaskPaymentFieldUtils.wrapMaskValuesSource(getMaskingInfo(fieldValuesSource), fieldValuesSource);

		return new FormDataConverter(metadata.getForm(),fieldValuesSource).toFormData();
	}

	public Document createDocumentXml() throws BusinessException
	{
		try
		{
			return document.convertToDom();
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * выполнить предварительные проверки перед подтверждением по смс/чеку/...
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void doPreFraudControl() throws BusinessLogicException, BusinessException
	{
		try
		{
			//проверка на мошейничество
			FraudMonitoringDocumentStrategy strategy = new FraudMonitoringPreConfirmDocumentStrategy(getDocument());
			strategy.process(null, null);
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}

	/**
	 * Подтверждение платежа
	 *
	 *  Выполняется следующий алгоритм (при изменении просьба актуализировать):
	 *   1. Проверка документа на мошейничество:
	 *      Важно: при проверке ответа от ВС ФМ может бросаться исключение RequireAdditionConfirmException, переводящее операцию на подтверждение в КЦ
	 *       1. если операция с подтверждением по смс/чеку/..., то на данном этапе из очереди приложения FraudMonitoringBackGateApp вычитываем результат ответа от ВС ФМ
	 *       2. если без подтверждения, то отправляем сообщение во ВС ФМ и вычитываем результат из очереди приложения FraudMonitoringBackGateApp
	 *   2. Проверка на лимиты
	 *   3. Оповещение ВС ФМ о срабовавших лимитах
	 *   4. Сохранение документа
	 *
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 * @throws SecurityLogicException
	 */
	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		final Calendar operationDate = GregorianCalendar.getInstance();

		try
		{
			//проверка операции во ВС ФМ на мошейничество
			doFraudControl(document);
		}
		catch (RequireAdditionConfirmFraudException ignore)
		{
			//требуется доп подтверждение
			doAdditionalConfirm(operationDate);
			return;
		}

		try
		{
			//проверка на лимиты, сохранение
			doProcess(operationDate);
		}
		catch (ImpossiblePerformOperationException e)
		{
			//сохранение оповещения о стработавшем заградительном лимите
			FraudMonitoringHelper.saveImpossiblePerformOperationNotification(document);
			throw e;
		}
		catch (RequireAdditionConfirmLimitException e)
		{
			if (RestrictionType.IMSI == e.getRestrictionType())
			{
				//сохранение оповещения о стработавшем имси лимите (зафиксирован случай клонирования сим-карты)
				FraudMonitoringHelper.saveIMSIOperationNotification(document);
			}
		}
		catch (PostConfirmCalcCommission ignore)
		{
			//удалять нельзя, т.к. new DbDocumentTarget().save(document); уже выполнен
		}
	}

	protected void doFraudControl(BusinessDocument document) throws BusinessLogicException, BusinessException
	{
		try
		{
			FraudMonitoringDocumentStrategy strategy = new FraudMonitoringConfirmDocumentStrategy(document);
			strategy.process(null, null);
		}
		catch (RequireAdditionConfirmFraudException e)
		{
			MessagingHelper.sendMessage(document, MessageTemplateType.REVIEW.name() + ".FM." + document.getClass().getName());
			throw e;
		}
		catch (ProhibitionOperationFraudException e)
		{
			MessagingHelper.sendMessage(document, MessageTemplateType.DENY.name() + ".FM." + document.getClass().getName());
			throw e;
		}
	}

	@Transactional
	protected void doProcess(Calendar operationDate) throws BusinessLogicException, BusinessException, SecurityLogicException
	{
		try
		{
			createDocumentLimitManager(document, document.getOperationUID(), operationDate).processLimits(new DocumentAction()
			{
				public void action(BusinessDocument document) throws BusinessLogicException, BusinessException
				{
					processConfirmDocument();
				}
			});
		}
		// если требуется доп подтверждение то переводим в статус WAITCONFIRM
		catch (RequireAdditionConfirmLimitException e)
		{
			if (e.getRestrictionType() != null)
				//устанавливаем в платеж причину дополнительного подтверждения
				document.setReasonForAdditionalConfirm(e.getRestrictionType().name());

			//устанавливаем лимит и накопленную сумму по лимиту (нужно для смс клиенту в ЕРМБ)
			limit = e.getLimit();
			limitAccumulatedAmount = e.getAmount();

			// требуется доп подтверждение
			executor.fireEvent(new ObjectEvent(DocumentEvent.DOWAITCONFIRM, ObjectEvent.CLIENT_EVENT_TYPE));
			doSaveConfirm(document, operationDate);
			throw e;
		}
		catch (PostConfirmCalcCommission e)
		{
			//расчет комиссии после подтверждения. Второй раз не подтверждаем документ(смс/карта).
			document.setClientOperationChannel(getClientOperationChannel());
			document.setSessionId(LogThreadContext.getSessionId());
			new DbDocumentTarget().save(document);
			if (ApplicationUtil.isApi())
			{
				getStateMachineEvent().addErrorMessage(DocumentHelper.getCommissionMessage(MoneyFunctions.getFormatAmount(((BusinessDocumentBase) document).getCommission()), ((BusinessDocumentBase) document).getTariffPlanESB()));
			}
			throw e;
		}

		//сохранение состояния документа
		doSaveConfirm(document, operationDate);
	}

	@Transactional
	protected void doAdditionalConfirm(final Calendar operationDate) throws BusinessLogicException, BusinessException, SecurityLogicException
	{
		try
		{
			//проверяем документ по лимитам
			createDocumentLimitManager(document, document.getOperationUID(), operationDate).processLimits(new DocumentAction()
			{
				public void action(BusinessDocument document) throws BusinessLogicException, BusinessException
				{
					executor.fireEvent(new ObjectEvent(DocumentEvent.DOWAITCONFIRM, ObjectEvent.CLIENT_EVENT_TYPE));
				}
			});
		}
		catch (RequireAdditionConfirmLimitException ignore)
		{
			//требуется доп подтверждение
			executor.fireEvent(new ObjectEvent(DocumentEvent.DOWAITCONFIRM, ObjectEvent.CLIENT_EVENT_TYPE));
		}

		document.setReasonForAdditionalConfirm(BusinessDocumentBase.FRAUD_MONITORING_CONFIRM_REASON);
		doSaveConfirm(document, operationDate);
	}

	protected void processConfirmDocument() throws BusinessLogicException, BusinessException
	{
		try
		{
			executor.fireEvent(new ObjectEvent(DocumentEvent.CONFIRM, ObjectEvent.CLIENT_EVENT_TYPE));
		}
		catch (BusinessTimeOutException e)
		{
			log.error(String.format(TIME_OUT_ERROR_MESSAGE, document.getId()), e);
			DocumentHelper.fireDounknowEvent(executor, ObjectEvent.CLIENT_EVENT_TYPE, e);
		}
		catch (BusinessOfflineDocumentException e)
		{
			if (DocumentHelper.isExternalPayment(document))
				throw new BusinessLogicException(e.getMessage(), e);

			DocumentHelper.repeatOfflineDocument(executor, new ObjectEvent(DocumentEvent.CONFIRM, ObjectEvent.CLIENT_EVENT_TYPE), document, e.getMessage());
		}
		catch (BusinessLogicException e)
		{
			DocumentHelper.decrementPromoCode(document);
			throw e;
		}
		catch (BusinessException e)
		{
			DocumentHelper.decrementPromoCode(document);
			throw e;
		}
	}

	protected void doSaveConfirm(BusinessDocument document, Calendar operationDate) throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		document.setOperationDate(operationDate);
		document.setSessionId(LogThreadContext.getSessionId());
		document.setClientOperationChannel(getClientOperationChannel());

		// если не задана стратегия подтверждения документа, то ставим "Не требуется подтверждение паролем"
		if (getConfirmStrategyTypeForDocument() == null)
			setConfirmStrategyTypeForDocument(ConfirmStrategyType.none);

		if (isSignatureSaveRequired())
		{
			DocumentSignature signature = getSignature();
			document.setSignature(signature);
		}

		new DbDocumentTarget().save(document);

		FillingAddressBookHelper.fill(document);
	}

	protected CreationType getClientOperationChannel()
	{
		return DocumentHelper.getChannelType();
	}

	/**
	 * Проверка на отключенные счета,карты,кредиты
	 * @throws BusinessException Ошибка работы с сервисом
	 */

	public void edit() throws BusinessException, BusinessLogicException
	{
		executor.fireEvent(new ObjectEvent(DocumentEvent.EDIT, "client"));

		new DbDocumentTarget().save(document);
	}

	/**
	 * @return Является ли подтверждаемый документ длительным поручением
	 */
	public boolean isLongOffer()
	{
		if (!(document instanceof AbstractLongOfferDocument))
		{
			return false;
		}
		return document.isLongOffer();
	}

	/**
	 * Сохранение документа
	 */
	public void save() throws BusinessException
	{
		new DbDocumentTarget().save(document);
	}

	public boolean getExternalPayment() throws BusinessException
	{
		return DocumentHelper.isExternalPayment(document);
	}

	public boolean getFNS()
	{
		return FNS;
	}

	public void setFNS(boolean FNS)
	{
		this.FNS = FNS;
	}

	public boolean isGuest()
	{
		return guest;
	}

	public boolean isMobileBankExist()
	{
		return mobileBankExist;
	}

	/**
	 * @return получить статус документа в терминах машины состояний. или null в случае некорректного состояния объекта.
	 */
	public MachineState getDocumentSate()
	{
		return executor.getCurrentState();
	}

	public void setUserStrategyType(ConfirmStrategyType type)
	{
		super.setUserStrategyType(type);
		setConfirmStrategyTypeForDocument(type);
	}

	/*
		устанавливает тип стратегии, по которой был подтвержден документ
	 */
	public void setConfirmStrategyTypeForDocument(ConfirmStrategyType strategyType)
	{
		if (document != null)
			document.setConfirmStrategyType(strategyType);
	}

	public ConfirmStrategyType getConfirmStrategyTypeForDocument()
	{
		if (document == null)
			return null;
		return document.getConfirmStrategyType();
	}

	/**
	 * Строит экранную форму платежа
     * @param transformInfo информация по преобразованию
	 * @param formInfo информация по форме
	 * @return HTML-описание экранной формы платежа
	 */
	public String buildFormHtml(TransformInfo transformInfo, FormInfo formInfo) throws BusinessException
	{
		return new PaymentFormBuilder(getDocumentContext(), transformInfo, formInfo).build(createFormData());
	}

	protected DocumentContext getDocumentContext()
	{
		return new DocumentContext(getDocument(), getMetadata());
	}

	/**
	 * Строит экранную форму платежа
	 * @param transformInfo информация по преобразованию
	 * @param formInfo информация по форме
	 * @return HTML-описание экранной формы платежа
	 */
	public String buildMobileXml(TransformInfo transformInfo, FormInfo formInfo) throws BusinessException
	{
		return new MobilePaymentFormBuilder(getDocumentContext(), transformInfo, formInfo).build(createFormData());
	}

	/**
	 * Строит экранную форму платежа
	 * @param transformInfo информация по преобразованию
	 * @param formInfo информация по форме
	 * @return HTML-описание экранной формы платежа
	 */
	public String buildSocialXml(TransformInfo transformInfo, FormInfo formInfo) throws BusinessException
	{
		return new SocialPaymentFormBuilder(getDocumentContext(), transformInfo, formInfo).build(createFormData());
	}

	/**
	 * Строит экранную форму платежа
	 * @param transformInfo информация по преобразованию
	 * @param formInfo информация по форме
	 * @return HTML-описание экранной формы платежа
	 */
	public String buildATMXml(TransformInfo transformInfo, FormInfo formInfo) throws BusinessException
	{
		return new AtmPaymentFormBuilder(getDocumentContext(), transformInfo, formInfo).build(createFormData());
	}

	protected DocumentLimitManager createDocumentLimitManager(BusinessDocument document, String operationUID, Calendar operationDate) throws BusinessException, BusinessLogicException
	{
		List<Class<? extends ProcessDocumentStrategy>> strategies = new ArrayList<Class<? extends ProcessDocumentStrategy>>();

		if (document.getCreationType() == CreationType.mobile)
		{
			strategies.add(RecipientByCardDocumentMobileChannelLimitStrategy.class);
		}
		//для смс-канала не нужно проверять лимит IMSI [BUG071380]
		if (document.getCreationType() != CreationType.sms)
			strategies.add(IMSIDocumentLimitStrategy.class);

		strategies.add(ObstructionDocumentLimitStrategy.class);
		strategies.add(OverallAmountPerDayDocumentLimitStrategy.class);
		strategies.add(RecipientByPhoneDocumentLimitStrategy.class);
		strategies.add(RecipientByCardDocumentLimitStrategy.class);
		strategies.add(GroupRiskDocumentLimitStrategy.class);
		strategies.add(MobileLightPlusLimitStrategy.class);

		return DocumentLimitManager.createProcessLimitManager(document, operationUID, operationDate, getPerson(), strategies);
	}

	/**
	 * Получение внешнего илентификатора провайдера по внутреннему
	 * @param internalId - внутренний идентификатор
	 * @return внешний идентификатор
	 */
	public String getProviderExternalIdByInternalId(Long internalId)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceProviderBase.class).add(Expression.eq("id", internalId)).setProjection(Projections.property("synchKey"));
		try
		{
			return simpleService.findSingle(criteria);
		}
		catch (BusinessException e)
		{
			log.error("Ошибка при определении мобильного оператора", e);
			return null;
		}
	}

	protected MaskingInfo getMaskingInfo(FieldValuesSource fieldValuesSource)
	{
		List<PersonDocument> personDocuments = PersonHelper.getDocumentForProfile(getPerson().getPersonDocuments());
		Map<String, String> documentFieldsInfo =
				MaskPaymentFieldUtils.buildDocumentSeriesAndNumberValues(personDocuments);

		return new MaskingInfo(metadata, fieldValuesSource, documentFieldsInfo.values());
	}

	protected Person getPerson()
	{
		return PersonContext.getPersonDataProvider().getPersonData().getPerson();
	}

	public Limit getLimit()
	{
		return limit;
	}

	public Money getLimitAccumulatedAmount()
	{
		return limitAccumulatedAmount;
	}

	/**
	 * Сформировать информационные сообщения клиенту на данном шаге создания документа
	 * @return информационные сообщения
	 */
	public List<String> collectInfo() throws BusinessException
	{
		List<String> messages = new ArrayList<String>();
		for (DocumentStateInformer informer : getStateInformers())
		{
			if (informer.isActive())
			{
				messages.addAll(informer.inform());
			}
		}
		return messages;
	}

	private DocumentStateInformer[] getStateInformers()
	{
		return new DocumentStateInformer[]{new InternalServiceProviderByTemplateDocumentStateInformer(document), new ExternalServiceProviderByTemplateDocumentStateInformer(document)};
	}

	/**
	 * Проверка и актуализация документа
	 * @return true - актуализация произведена (требуется редирект), false - нет
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public boolean upgrade() throws BusinessLogicException, BusinessException
	{
		// проверка поддержки документом актуализации, если не поддерживает редирект не требуется
		if (!document.isUpgradable())
			return false;
		String oldClientForm = executor.getCurrentState().getClientForm();
		// запускаем событие актализации
		executor.fireEvent(new ObjectEvent(DocumentEvent.UPGRADE, ObjectEvent.CLIENT_EVENT_TYPE));
		String newClientForm = executor.getCurrentState().getClientForm();
		// если client-form не изменился - актулизация произведена, редирект не требуется
		return !oldClientForm.equals(newClientForm);
	}
}
