package com.rssl.phizic.operations.documents.templates;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.forms.FormInfo;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.converters.FormDataConverter;
import com.rssl.phizic.business.documents.metadata.source.TemplateFieldValueSource;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.forms.ATMTemplateFormBuilder;
import com.rssl.phizic.business.documents.templates.forms.MobileTemplateFormBuilder;
import com.rssl.phizic.business.documents.templates.forms.TemplateFormBuilder;
import com.rssl.phizic.business.documents.templates.source.TemplateDocumentSource;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.business.statemachine.documents.templates.TemplateStateMachineService;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;
import com.rssl.phizic.gate.payments.template.TemplateInfo;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.payment.PaymentOperationHelper;
import javax.xml.transform.Source;

/**
 * Базовый класс операций с шаблонами документов
 *
 * @author khudyakov
 * @ created 08.02.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class ViewTemplateOperationBase extends OperationBase implements ViewEntityOperation
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private final TemplateStateMachineService templateStateMachineService = new TemplateStateMachineService();

	protected Metadata metadata;
	protected TemplateDocument template;
	protected StateMachineExecutor executor;

	public void initialize(TemplateDocumentSource source) throws BusinessException, BusinessLogicException
	{
		metadata = source.getMetadata();
		template = source.getTemplate();

		executor = new StateMachineExecutor(templateStateMachineService.getStateMachineByFormName(template.getFormType().getName()));
		executor.initialize(template);
	}

	public Source createFormData() throws BusinessException
	{
		return createFormData(new TemplateFieldValueSource(getEntity()));
	}

	public Source createFormData(FieldValuesSource initialValueSource) throws BusinessException
	{
		FormDataConverter converter = new FormDataConverter(metadata.getForm(),
				MaskPaymentFieldUtils.isRequireMasking() ? MaskPaymentFieldUtils.wrapMaskValuesSource(metadata, initialValueSource) : initialValueSource);
		return converter.toFormData();
	}

	/**
	 * @return шаблон документа
	 */
	public TemplateDocument getEntity()
	{
		return template;
	}

	/**
	 * @return шаблон документа
	 */
	public TemplateDocument getTemplate()
	{
		return template;
	}

	/**
	 * @return дополнительная информация по шаблону
	 */
	public TemplateInfo getTemplateInfo()
	{
		return template.getTemplateInfo();
	}

	/**
	 * @return метаданные
	 */
	public Metadata getMetadata()
	{
		return metadata;
	}

	/**
	 * @return StateMachineExecutor
	 */
	public StateMachineExecutor getExecutor()
	{
		return executor;
	}

	/**
	 * @return строка идентифицирующая метаданные
	 * @throws BusinessException
	 */
	public String getMetadataPath() throws BusinessException
	{
		return PaymentOperationHelper.calculateMetadataPath(getMetadata(), getEntity());
	}

	/**
	 * Создать html описание формы документа
	 *
	 * @param transformInfo информация по преобразованию
	 * @param formInfo информация по форме
	 * @param source ресурс
	 * @return html
	 * @throws BusinessException
	 */
	public String buildFormHtml(TransformInfo transformInfo, FormInfo formInfo, FieldValuesSource source) throws BusinessException
	{
		return new TemplateFormBuilder(getEntity(), getMetadata(), transformInfo, formInfo).build(createFormData(source));
	}

	/**
	 * Создать html описание формы документа
	 *
	 * @param transformInfo информация по преобразованию
	 * @param formInfo информация по форме
	 * @return html
	 * @throws BusinessException
	 */
	public String buildMobileXml(TransformInfo transformInfo, FormInfo formInfo) throws BusinessException
	{
		return new MobileTemplateFormBuilder(getEntity(), getMetadata(), transformInfo, formInfo).build(createFormData());
	}

	/**
	 * Создать html описание формы документа
	 *
	 * @param transformInfo информация по преобразованию
	 * @param formInfo информация по форме
	 * @return html
	 * @throws BusinessException
	 */
	public String buildATMXml(TransformInfo transformInfo, FormInfo formInfo) throws BusinessException
	{
		return new ATMTemplateFormBuilder(getEntity(), getMetadata(), transformInfo, formInfo).build(createFormData());
	}
}
