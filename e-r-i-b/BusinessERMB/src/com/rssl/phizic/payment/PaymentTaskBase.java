package com.rssl.phizic.payment;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormException;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.*;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.accounts.AccountsUtil;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.documents.exceptions.NotOwnDocumentException;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.documents.strategies.DocumentLimitManager;
import com.rssl.phizic.business.documents.strategies.ProcessDocumentStrategy;
import com.rssl.phizic.business.documents.strategies.limits.BlockDocumentOperationLimitStrategy;
import com.rssl.phizic.business.documents.strategies.limits.GroupRiskDocumentLimitStrategy;
import com.rssl.phizic.business.documents.strategies.limits.ObstructionDocumentLimitStrategy;
import com.rssl.phizic.business.ermb.ErmbPaymentType;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.auxiliary.messaging.paymentSms.SendPaymentSmsWebService;
import com.rssl.phizic.business.ermb.sms.messaging.MessageBuilder;
import com.rssl.phizic.business.limits.BusinessDocumentLimitException;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.business.limits.RestrictionType;
import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.business.operations.OperationFactoryImpl;
import com.rssl.phizic.business.operations.restrictions.RestrictionProviderImpl;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.interactive.PersonInteractManager;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.ermb.ErmbSmsContext;
import com.rssl.phizic.operations.access.NullConfirmStrategySource;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.operations.payment.CreateFormPaymentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.ConfirmableTask;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.task.PersonTaskBase;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Erkin
 * @ created 08.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ����� �������� ������
 */
abstract class PaymentTaskBase extends PersonTaskBase implements PaymentTask, ConfirmableTask
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	protected static final MessageBuilder messageBuilder = new MessageBuilder();

	private static final NotExpiredCardFilter notExpiredCardFilter = new NotExpiredCardFilter();
	protected static final NotBlockedCardFilter notBlockedCardFilter = new NotBlockedCardFilter();

	private static transient final OperationFactory operationFactory = new OperationFactoryImpl(new RestrictionProviderImpl());

	private static final SendPaymentSmsWebService sendPaymentSmsService = new SendPaymentSmsWebService();

	protected transient FieldValuesSource requestFieldValuesSource;

	protected transient DocumentSource documentSource;

	protected transient EditDocumentOperation editOperation;

	private transient ConfirmFormPaymentOperation confirmOperation;

	protected transient BusinessDocument existingDocument; //������������ ��������� �������� (� ������ ������������� �������)

	protected Long documentId;

	private transient Limit currentLimit; //����������� �����

	private transient Money accumulatedAmount;//����������� ����� �� ������
	///////////////////////////////////////////////////////////////////////////

	/**
	 * @return ��� ����� ������� (never null)
	 */
	protected abstract String getFormName();

	///////////////////////////////////////////////////////////////////////////

	public void execute()
	{
		doExecute();
	}

	public final void doExecute()
	{
		try
		{
			// ��������� ���������� ����������
			checkParameters();

			// 1. ���� ������������� ��������
			existingDocument = findIncompletePayment();

			// 2. �������� �������� �������� �����
			requestFieldValuesSource = createRequestFieldValuesSource();

			// 3. ���� �� ����� ������������� ��������, ������� �����, � ���� �����, ���������� ��� ���������
			if (existingDocument == null)
				documentSource = createNewDocumentSource();
			else
				documentSource = createExistingDocumentSource(existingDocument);

			BusinessDocument document = documentSource.getDocument();

			// 4. ������������� ������������� � �������� ����������� ����
			setExtendedDocumentFields(document);

			// 5. ������� �������� �������������� �������
			editOperation = createEditOperation();

			// 6. ���������� �����
			Map<String, Object> formData = checkFormData();
			if (formData == null)
				return;

			// 7. ��������� �����
			saveNewPayment(formData);

			// 8. ������ ��������������� �������� �������
			preliminaryCheckLimits();
			if (isBlockingLimit())
			{
				throw new UserErrorException(messageBuilder.buildPreliminaryLimitMessage(currentLimit, accumulatedAmount));
			}

			// 9. ���������, ��������� �� �������� ��� ������������� ��� ������ �������. ���� �� ���������, ����� ������������
			if (needConfirm())
			{
				// ���� ��� ������� ��������� ������� ��� �������������, �������, ����� �� �������� ��� �������������.
				// ���� ����� - ����������� � ������� ��� �������������
				if (readyForConfirm())
					getPersonConfirmManager().askForConfirm(this);
			}
			else
			{
				//���� ��� ������������� �� ��������� - ������������
				this.confirmGranted();
			}
		}
		catch (BusinessLogicException e)
		{
			throw new UserErrorException(e);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
		catch (UserErrorException e)
		{
			throw e;
		}
		catch (InactiveExternalSystemException e)
		{
			processInactiveExternalSystemException(e, getDocument());
		}
		catch (RuntimeException e)
		{
			// ��������� ������
			log.error(e.getMessage(), e);
			// TODO: (����) �������� ��������� ��� ��������� ������. ����������� ����� �.
			getPersonInteractManager().reportError("�������� �������� �� ���������. ��������� ������� �����.");
		}
	}

	public void confirmGranted()
	{
		confirmOperation = createConfirmOperation();
		BusinessDocument document = confirmOperation.getDocument();

		try
		{
			if (needCheckCardBeforeConfirm(document))
				checkCard(document.getChargeOffResourceLink(), document);
		}
		catch (DocumentException e)
		{
			throw new InternalErrorException(e);
		}

		//���� ����� ��������� � ��� mbOperCode, ���������� � ������ mbOperCode � ���������� ��������� � ���
		if (needSendMBOperCode(document))
			saveAndSendMBOperCode(document);

		confirmOperation();
        // ������������� � �������� ������������� ���-�������
		UUID smsRequestUID = ErmbSmsContext.getIncomingSMSRequestUID();
		if (smsRequestUID != null)
			document.setErmbSmsRequestId(smsRequestUID.toString());

		TextMessage message = null;
		String documentStateCode = document.getState().getCode();

		if ("DELAYED_DISPATCH".equals(documentStateCode) || "OFFLINE_DELAYED".equals(documentStateCode))
		{
			message = messageBuilder.buildNonOperatingTimeMessage(document, this);
		}
		else if ("WAIT_CONFIRM".equals(documentStateCode))
		{
			message = messageBuilder.buildLimitMessage(confirmOperation.getLimit(), confirmOperation.getLimitAccumulatedAmount());
		}
		if (message != null)
			getPersonSmsMessanger().sendSms(message);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @return ����� �������� �������� ����� �� ������� ���������� �����
	 */
	protected abstract FieldValuesSource createRequestFieldValuesSource();

	/**
	 * @return ����� �������� �������������� �������
	 */
	protected EditDocumentOperation createEditOperation()
	{
		try
		{
			String serviceKey = documentSource.getMetadata().getName();
			EditDocumentOperation operation = createOperation(CreateFormPaymentOperation.class, serviceKey);
			operation.initialize(documentSource, requestFieldValuesSource);
			return operation;
		}
		catch (TemporalBusinessException e)
		{
			String exceptionMessage = "�� ����������� �������� �������� �������� ����������. ��������� ������� �����";
			throw new UserErrorException(new TextMessage(exceptionMessage), e);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new UserErrorException(e);
		}
	}

	/**
	 * @return ����� �������� ������������� �������
	 */
	protected ConfirmFormPaymentOperation createConfirmOperation()
	{
		try
		{
			ExistingSource source = new ExistingSource(documentId, new IsOwnDocumentValidator());

			ConfirmFormPaymentOperation operation = operationFactory.create(ConfirmFormPaymentOperation.class, source.getDocument().getFormName());
			operation.initialize(source);
			operation.setStrategy(new NullConfirmStrategySource());
			operation.setUserStrategyType(ConfirmStrategyType.sms);
			return operation;
		}
		catch(NotOwnDocumentException e)
		{
		      throw new AccessException("� ������� ������������ ��� ���� �� �������� ������� � id=" + documentId, e);
		}
		catch (BusinessLogicException e)
		{
			throw new UserErrorException(e);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

	protected void confirmOperation()
	{
		try
		{
			// 1.���������� ������ �� �������������
			ConfirmationManager.sendRequest(confirmOperation);

			// 2. ������ ����� �� �������������
			List<String> errors = ConfirmationManager.readResponse(confirmOperation, new MapValuesSource(new HashMap<String, Object>()));

			// 3. ���� ��� ������ - ������������
			if (errors.isEmpty())
			{
				confirmOperation.confirm();
			}
			else
			{
				confirmOperation.getRequest().setErrorMessage(errors.get(0));
			}
		}
		catch (BusinessDocumentLimitException e)
		{
			throw new UserErrorException(messageBuilder.buildLimitMessage(e.getLimit(), e.getAmount()), e);
		}
		catch (BusinessLogicException e)
		{
			ErmbPaymentType paymentType = getPaymentType();
			BusinessDocument document = confirmOperation.getDocument();
			String errCode = e.getErrCode();

			switch (paymentType)
			{
				case CREATE_AUTOPAYMENT:
					throw new UserErrorException(messageBuilder.buildCreateAutoPaymentErrorMessage(document, errCode), e);

				case REFUSE_AUTOPAYMENT:
					throw new UserErrorException(messageBuilder.buildRefuseAutoPaymentErrorMessage(document, errCode), e);

				case CARD_TRANSFER:
					if (StringUtils.isNotEmpty(errCode))
						throw new UserErrorException(messageBuilder.buildCardTransferExternalSystemError(document), e);
					else throw new UserErrorException(e);
				case INTERNAL_TRANSFER:
					if (StringUtils.isNotEmpty(errCode))
						throw new UserErrorException(messageBuilder.buildInternalTransferExternalSystemError(document), e);
					else throw new UserErrorException(e);
				case PHONE_TRANSFER:
					if (StringUtils.isNotEmpty(errCode))
						throw new UserErrorException(messageBuilder.buildPhoneTransferExternalSystemError(document), e);
					else throw new UserErrorException(e);

				default:
					throw new UserErrorException(e);
			}
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
		catch (SecurityLogicException e)
		{
			throw new InternalErrorException(e);
		}
		catch (InactiveExternalSystemException e)
		{
			processInactiveExternalSystemException(e, confirmOperation.getDocument());
		}
	}

	/**
	 * @param operationKey ���� ��������
	 * @param serviceKey ���� ������
	 * @return ��������
	 */
	protected <T extends Operation> T createOperation(Class<T> operationKey, String serviceKey)
	{
		return this.<T>createOperation(operationKey.getSimpleName(), serviceKey);
	}

	/**
	 * @param operationKey ���� ��������
	 * @return ��������
	 */
	protected <T extends Operation> T createOperation(Class<T> operationKey)
	{
		return this.<T>createOperation(operationKey.getSimpleName(), null);
	}

	/**
	 * @param operationKey ���� ��������
	 * @return ��������
	 */
	protected <T extends Operation> T createOperation(String operationKey)
	{
		return this.<T>createOperation(operationKey, null);
	}

	/**
	 * @param operationKey ���� ��������
	 * @param serviceKey ���� ������
	 * @return ��������
	 */
	protected <T extends Operation> T createOperation(String operationKey, String serviceKey)
	{
		try
		{
			if (StringHelper.isEmpty(serviceKey))
				return operationFactory.<T>create(operationKey);
			else
				return operationFactory.<T>create(operationKey, serviceKey);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @return ����� �������� ������ �������
	 */
	protected NewDocumentSource createNewDocumentSource()
	{
		PaymentEngine paymentEngine = getModule().getPaymentEngine();

		try
		{
			String formName = getFormName();
			CreationType creationType = paymentEngine.getDocumentCreationType();
			CreationSourceType creationSourceType = CreationSourceType.ordinary;
			return new NewDocumentSource(formName, requestFieldValuesSource, creationType, creationSourceType);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new UserErrorException(e);
		}
	}

	protected ExistingSource createExistingDocumentSource(BusinessDocument document)
	{
		try
		{
			return new ExistingSource(document.getId(), new IsOwnDocumentValidator());
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new UserErrorException(e);
		}
	}

	protected BusinessDocument findIncompletePayment()
	{
		return null;
	}
	///////////////////////////////////////////////////////////////////////////

	/**
	 * ���������� ���� �������, �������� �������������
	 * @return ���� "���_���� -> ��������_����" ��� null, ���� ��������� ������������ ������� (������������)
	 */
	private Map<String, Object> checkFormData()
	{
		FormProcessor<List<String>, ?> processor = createFormProcessor();

		if (!processor.process())
		{
			PersonInteractManager interactManager = getPersonInteractManager();
			interactManager.reportErrors(processor.getErrors());
			return null;
		}
		return processor.getResult();
	}

	private FormProcessor<List<String>, ?> createFormProcessor()
	{
		Metadata metadata = editOperation.getMetadata();

		Form form = metadata.getForm();
		ValidationStrategy strategy = getValidationStrategy();
		StringErrorCollector collector = new StringErrorCollector();
		FieldValuesSource valuesSource = getValidateFieldValuesSource();
		return new FormProcessor<List<String>, StringErrorCollector>(valuesSource, form, collector, strategy);
	}

	protected FieldValuesSource getValidateFieldValuesSource()
	{
		if (documentSource != null)
		{
			try
			{
				return new CompositeFieldValuesSource(requestFieldValuesSource, new DocumentFieldValuesSource(documentSource.getMetadata(), documentSource.getDocument()));
			}
			catch (BusinessException e)
			{
				throw new InternalErrorException(e);
			}
			catch (FormException e)
			{
				throw new InternalErrorException(e);
			}
		}

		return requestFieldValuesSource;
	}

	protected ValidationStrategy getValidationStrategy()
	{
		PaymentEngine paymentEngine = getModule().getPaymentEngine();
		return paymentEngine.getDocumentValidationStrategy();
	}

	///////////////////////////////////////////////////////////////////////////

	protected void saveNewPayment(Map<String, Object> formData)
	{
		try
		{
			editOperation.updateDocument(formData);

			checkFormValidators(getDocument());

			documentId = editOperation.save();

			//�������� � ����-������� ������������� ������
			ErmbProfileBusinessService service = new ErmbProfileBusinessService();
			if (editOperation.getDocument().getState().getCode().equals("DRAFT"))
			{
				service.updateIncompletePayment(getPerson().getId(), documentId);
			}
			else
			{
				Long currentPaymentId = ((ErmbProfileImpl) getPerson().getErmbProfile()).getIncompleteSmsPayment();
				if (currentPaymentId != null)
				{
					service.updateIncompletePayment(getPerson().getId(), null);
				}
			}
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new UserErrorException(e);
		}
	}

	protected  void setExtendedDocumentFields(BusinessDocument document)
	{
		// ������������� � �������� ��� �������
		document.setErmbPaymentType(getPaymentType().toString());

		// ������������� � �������� ������������� ���-�������
		UUID smsRequestUID = ErmbSmsContext.getIncomingSMSRequestUID();
		if (smsRequestUID != null)
			document.setErmbSmsRequestId(smsRequestUID.toString());
	}

	protected void saveAndSendMBOperCode(BusinessDocument document)
	{
		String mbOperCode = RandomHelper.rand(6, RandomHelper.DIGITS + RandomHelper.ENGLISH_LETTERS);
		document.setMbOperCode(mbOperCode);

		try
		{
			String message = messageBuilder.buildPaymentSmsMessage(document);
			sendPaymentSmsService.sendPaymentSms(mbOperCode, message);
			log.info("���������� ��������� � ��� �� �������� ���������� �������. ��� ���������� �����: " + mbOperCode + " ����� ���������: " + message);
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
		}
	}

	protected boolean needSendMBOperCode(BusinessDocument document)
	{
		return false;
	}

	public <T extends BusinessDocument> T getDocument()
	{
		//noinspection unchecked
		return (T) editOperation.getDocument();
	}

    protected boolean needConfirm()
    {
	   return true;
    }

	protected boolean readyForConfirm()
	{
		State documentState = documentSource.getDocument().getState();
		if ((new State("SAVED")).equals(documentState) || (new State("OFFLINE_SAVED")).equals(documentState))
			return true;
		return false;
	}

	/**
	 * �������� ��� �������
	 * @return
	 */
	public abstract ErmbPaymentType getPaymentType();

	private void preliminaryCheckLimits() throws BusinessException, BusinessLogicException
	{
		if ("DRAFT".equals(documentSource.getDocument().getState().getCode()))
			return;

		Person person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		//noinspection unchecked
		List<Class<? extends ProcessDocumentStrategy>> strategies = new ArrayList<Class<? extends ProcessDocumentStrategy>>();

		strategies.add(ObstructionDocumentLimitStrategy.class);
		strategies.add(GroupRiskDocumentLimitStrategy.class);
		strategies.add(BlockDocumentOperationLimitStrategy.class);

		try
		{
			DocumentLimitManager.createCheckLimitManager(documentSource.getDocument(), person, strategies).checkAndThrowLimits();
		}
		catch (BusinessDocumentLimitException e)
		{
			currentLimit = e.getLimit();
			accumulatedAmount = e.getAmount();
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

	private boolean isBlockingLimit()
	{
		return currentLimit != null && currentLimit.getRestrictionType() == RestrictionType.MIN_AMOUNT;
	}

	public Limit getCurrentLimit()
	{
		return currentLimit;
	}

	public Money getAccumulatedAmount()
	{
		return accumulatedAmount;
	}

	/**
	 * ��������� ����� �����
	 * @param document - ��������� ��������
	 */
	protected void checkFormValidators(BusinessDocument document)
	{
		return;
	}

	/**
	 * �������� ������������� ����� ��������
	 * @param chargeOffLink - ���� ��������
	 * @param money - ����� ��������
	 * @return
	 */
	protected boolean checkChargeOfProductAmount(BankrollProductLink chargeOffLink, Money money)
	{
		try
		{
			if (chargeOffLink instanceof  CardLink)
			{
				CardLink cardLink = (CardLink) chargeOffLink;
				Card card = cardLink.getCard();
				if (MockHelper.isMockObject(card))
					throw new UserErrorException(messageBuilder.buildNotAvailableProductMessage(cardLink));
				return CardsUtil.hasAvailableLimit(card, money);
			}
			if (chargeOffLink instanceof AccountLink)
			{
				AccountLink accountLink = (AccountLink) chargeOffLink;
				if (MockHelper.isMockObject(accountLink.getValue()))
					throw new UserErrorException(messageBuilder.buildNotAvailableProductMessage(accountLink));
				return AccountsUtil.hasAvailableMoney(accountLink, money);
			}
			return true;
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

	/**
	 * �������� �����: �� �����������������, �� �������� ���� ��������, �� ������������� ������� ��� ��������
	 * @param product
	 * @param document
	 */
	protected void checkCard(PaymentAbilityERL product, BusinessDocument document)
	{
		if (product == null || !(product instanceof CardLink))
			return;

		CardLink cardLink = (CardLink) product;
		if (!checkCardNotBlocked(cardLink))
			throw new UserErrorException(messageBuilder.buildCardBlockedMessage(document));

		if (!checkCardExpireDate(cardLink))
			throw new UserErrorException(messageBuilder.buildExpiredCardMessage(document));

		if (!checkChargeOfProductAmount(cardLink, document.getExactAmount()))
			throw new UserErrorException(messageBuilder.buildNotEnoughMoneyError(document));
	}

	protected boolean checkCardExpireDate(CardLink cardLink)
	{
		return notExpiredCardFilter.accept(cardLink.getCard());
	}

	protected boolean checkCardNotBlocked(CardLink cardLink)
	{
	   return notBlockedCardFilter.accept(cardLink.getCard());
	}

	/**
	 * ����� �� ��������� ����� ����� ��������������
	 * @param document
	 * @return
	 */
	protected boolean needCheckCardBeforeConfirm(BusinessDocument document)
	{
		return false;
	}

	private void processInactiveExternalSystemException(InactiveExternalSystemException e, BusinessDocument document)
	{
		ErmbPaymentType paymentType = getPaymentType();
		switch (paymentType)
		{
			case CARD_TRANSFER:
			case INTERNAL_TRANSFER:
			case PHONE_TRANSFER:
			case CREATE_AUTOPAYMENT:
			case REFUSE_AUTOPAYMENT:
				throw e;

			default:
				throw new UserErrorException(messageBuilder.buildInactiveSystemErrorMessage(document), e);
		}
	}
}
