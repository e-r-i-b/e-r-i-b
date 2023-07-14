package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.converters.FormDataConverter;
import com.rssl.phizic.business.documents.metadata.source.TemplateFieldValueSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.strategies.ProcessDocumentStrategy;
import com.rssl.phizic.business.documents.strategies.limits.Constants;
import com.rssl.phizic.business.documents.strategies.limits.OverallAmountPerDayTemplateLimitStrategy;
import com.rssl.phizic.business.documents.strategies.monitoring.*;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.forms.TemplateFormBuilder;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.documents.templates.source.TemplateDocumentSource;
import com.rssl.phizic.business.documents.templates.strategies.limits.BlockTemplateOperationLimitStrategy;
import com.rssl.phizic.business.informers.DocumentStateInformer;
import com.rssl.phizic.business.informers.ExternalServiceProviderTemplateStateInformer;
import com.rssl.phizic.business.informers.InternalServiceProviderTemplateStateInformer;
import com.rssl.phizic.business.limits.BlockDocumentOperationException;
import com.rssl.phizic.business.limits.ImpossiblePerformOperationException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.business.statemachine.documents.templates.TemplateStateMachineService;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityLogicException;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.transform.Source;

/**
 * ќпераци€ подтверждени€ шаблона документа
 *
 * @ author: filimonova
 * @ created: 16.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmTemplateOperation extends ConfirmableOperationBase
{
	private Metadata metadata;
	private TemplateDocument template;
	private BusinessDocument byDocument;
	private StateMachineExecutor executor;

	private final TemplateStateMachineService templateStateMachineService = new TemplateStateMachineService();

	public void initialize(TemplateDocument template, Metadata metadata) throws BusinessException, BusinessLogicException
	{
		this.metadata = metadata;
		this.template = template;

		executor = new StateMachineExecutor(templateStateMachineService.getStateMachineByFormName(template.getFormType().getName()));
		executor.initialize(template);

		initializeNew();
	}

	public void initialize(TemplateDocumentSource source) throws BusinessException, BusinessLogicException
	{
		initialize(source.getTemplate(), source.getMetadata());
	}

	public void initialize(TemplateDocumentSource templateSource, DocumentSource documentSource) throws BusinessException, BusinessLogicException
	{
		initialize(templateSource);

		byDocument = documentSource.getDocument();
	}

	public ConfirmableObject getConfirmableObject()
	{
		return getTemplate();
	}

	/**
	 * @return шаблон
	 */
	public TemplateDocument getTemplate()
	{
		return template;
	}

	public Metadata getMetadata()
	{
		return metadata;
	}

	public String getMetadataPath() throws BusinessException
	{
		return PaymentOperationHelper.calculateMetadataPath(getMetadata(), getTemplate());
	}

	public StateMachineExecutor getExecutor()
	{
		return executor;
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
			ProcessDocumentStrategy strategy = new FraudMonitoringPreConfirmTemplateStrategy(template);
			strategy.process(null, null);
		}
		catch (Exception e)
		{
			log.error(e);
		}
	}

	/**
	 * ѕодтверждение шаблона.
	 */
	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		try
		{
			doFraudControl();
			doSaveConfirm();
		}
		catch (BlockDocumentOperationException e)
		{
			doSendFraudNotification();
			throw e;
		}
		catch (ImpossiblePerformOperationException e)
		{
			doSendFraudNotification();
			throw e;
		}
	}

	protected void doFraudControl() throws BusinessLogicException, BusinessException
	{
		ProcessDocumentStrategy strategy = new FraudMonitoringConfirmTemplateStrategy(template);
		strategy.process(null, null);
	}

	@Transactional
	protected void doSaveConfirm() throws BusinessException, BusinessLogicException
	{
		new BlockTemplateOperationLimitStrategy(template).checkAndThrow(null);
		new OverallAmountPerDayTemplateLimitStrategy(template).checkAndThrow(null);

		try
		{
			//обрабатываем получившеес€ событие
			executor.fireEvent(new ObjectEvent(DocumentEvent.CONFIRM, ObjectEvent.CLIENT_EVENT_TYPE));
		}
		catch (BusinessTimeOutException e)
		{
			log.error(String.format(Constants.TIME_OUT_ERROR_MESSAGE, template.getId()), e);
			DocumentHelper.fireDounknowEvent(executor, ObjectEvent.CLIENT_EVENT_TYPE, e);
		}

		template.setClientOperationChannel(DocumentHelper.getChannelType());
		template.setClientOperationDate(GregorianCalendar.getInstance());

		TemplateDocumentService.getInstance().addOrUpdate(template);
	}

	protected void doSendFraudNotification() throws BusinessLogicException, BusinessException
	{
		try
		{
			FraudMonitoringDocumentStrategy strategy = new FraudMonitoringPostConfirmTemplateStrategy(template);
			strategy.process(null, null);
		}
		catch (Exception e)
		{
			log.error("ѕри оповещии ¬— ‘ћ об изменени€ вработе с шаблоном id = " + template.getId() + " произошла ошибка.", e);
		}
	}

	/**
	 * —охранить шаблон по исполенному платежу
	 */
	public void saveQuicklyCreatedTemplate() throws BusinessException, BusinessLogicException
	{
		try
		{
			doFraudControl();
			doConfirm();
		}
		catch (BlockDocumentOperationException e)
		{
			doSendFraudNotification();
			throw e;
		}
		catch (ImpossiblePerformOperationException e)
		{
			doSendFraudNotification();
			throw e;
		}
	}

	@Transactional
	protected void doConfirm() throws BusinessException, BusinessLogicException
	{
		new BlockTemplateOperationLimitStrategy(template).checkAndThrow(null);
		new OverallAmountPerDayTemplateLimitStrategy(template).checkAndThrow(null);

		template.setClientOperationDate(GregorianCalendar.getInstance());
		template.setClientOperationChannel(CreationType.internet);

		ObjectEvent objectEvent = new ObjectEvent(DocumentEvent.CONFIRM, "client");
		if (byDocument.getAdditionalOperationChannel() != null)
		{
			//если документ, по которому создан шаблон подтвержден к  ÷, то шаблон переходит в статус сверхлимитный
			objectEvent = new ObjectEvent(DocumentEvent.CONFIRM_TEMPLATE, "client");
			template.setAdditionalOperationChannel(CreationType.internet);
		}
		executor.fireEvent(objectEvent);

		TemplateDocumentService.getInstance().addOrUpdate(template);
	}

	public String buildFormHtml(TransformInfo transformInfo, FormInfo formInfo) throws BusinessException
	{
		return new TemplateFormBuilder(getTemplate(), getMetadata(), transformInfo, formInfo).build(createFormData());
	}

	private Source createFormData() throws BusinessException
	{
		//перед преобразованием, переинициализируем formDataConverter, поскольку пол€ документа могли изменитьс€
		FieldValuesSource fieldValuesSource = new TemplateFieldValueSource(getTemplate());
		return new FormDataConverter(metadata.getForm(),
			// маскируем на форме просмотра нужные пол€
			MaskPaymentFieldUtils.isRequireMasking() ? MaskPaymentFieldUtils.wrapMaskValuesSource(metadata, fieldValuesSource) : fieldValuesSource).toFormData();

	}

	/**
	 * —формировать информационные сообщени€ клиенту на данном шаге создани€ документа
	 * @return информационные сообщени€
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
		return new DocumentStateInformer[]{new InternalServiceProviderTemplateStateInformer(getTemplate()), new ExternalServiceProviderTemplateStateInformer(getTemplate())};
	}
}
