package com.rssl.phizic.business.documents.templates.source;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.validators.CompositeDocumentValidator;
import com.rssl.phizic.business.documents.payments.validators.DocumentValidator;
import com.rssl.phizic.business.documents.templates.TemplateBuilder;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.business.statemachine.documents.templates.TemplateStateMachineService;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.documents.StateCode;
import com.rssl.phizic.logging.operations.context.OperationContextUtil;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.counter.NamingStrategy;

/**
 * @author Evgrafov
 * @ created 13.12.2005
 * @ $Author: puzikov $
 * @ $Revision: 79773 $
 */
public class NewTemplateSource implements TemplateDocumentSource
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	private final TemplateStateMachineService templateStateMachineService = new TemplateStateMachineService();

	private Metadata metadata;
	private BusinessDocument document;
	private TemplateDocument template;


	/**
	 * ���������� ��������� ������ ������� �� ������ ������ ���������.
	 *
	 * @param source ��������, �� ������ �������� ����� ������ ������
	 * @param channelType ����� �������� �������
	 */
	public NewTemplateSource(BusinessDocument source, CreationType channelType) throws BusinessException, BusinessLogicException
	{
		metadata = MetadataCache.getExtendedMetadata(document);
		template = new TemplateBuilder(metadata, document).setClientCreationChannel(channelType).build();
		document = source;

		initialize(channelType);
	}

	/**
	 * ���������� ��������� ������ �������
	 *
	 * @param formName ��� �����
	 * @param initialValuesSource ��������� ������
	 * @param channelType ����� �������� �������
	 */
	public NewTemplateSource(String formName, FieldValuesSource initialValuesSource, CreationType channelType) throws BusinessException, BusinessLogicException
	{
		metadata = MetadataCache.getExtendedMetadata(formName, initialValuesSource);
		template = new TemplateBuilder(metadata).setInitialValuesSource(initialValuesSource).setClientCreationChannel(channelType).build();

		initialize(channelType);
	}

	/**
	 * ���������� ��������� ������ �������
	 *
	 * @param formName ��� �����
	 * @param initialValuesSource ��������� ������
	 * @param channelType ����� �������� �������
	 * @param person �������� �������
	 * @param templateName �������� �������
	 */
	public NewTemplateSource(String formName, FieldValuesSource initialValuesSource, CreationType channelType, Person person, String templateName) throws BusinessException, BusinessLogicException
	{
		metadata = MetadataCache.getExtendedMetadata(formName, initialValuesSource);
		template = new TemplateBuilder(metadata, person, templateName).setInitialValuesSource(initialValuesSource).setClientCreationChannel(channelType).build();

		initialize(channelType);
	}

	/**
	 * ���������� ��������� ������ �������
	 *
	 * @param formName ��� �����
	 * @param initialValuesSource ��������� ������
	 * @param channelType ����� �������� �������
	 * @param person �������� �������
	 * @param namingStrategy ��������� ����������
	 */
	public NewTemplateSource(String formName, FieldValuesSource initialValuesSource, CreationType channelType, Person person, NamingStrategy namingStrategy) throws BusinessException, BusinessLogicException
	{
		metadata = MetadataCache.getExtendedMetadata(formName, initialValuesSource);
		template = new TemplateBuilder(metadata, person, namingStrategy).setInitialValuesSource(initialValuesSource).setClientCreationChannel(channelType).build();

		initialize(channelType);
	}

	/**
	 * ���������� ��������� ������ ������� �� ������ ������ �������������� ���������.
	 *
	 * @param paymentId ������������ �������
	 * @param validators ��������� ������������ ���������
	 * @param channelType ����� �������� �������
	 */
	public NewTemplateSource(Long paymentId, CreationType channelType, DocumentValidator ... validators) throws BusinessException, BusinessLogicException
	{
		if (paymentId == null)
		{
			throw new BusinessException("�� ����� ������������� �������");
		}

		BusinessDocument source = businessDocumentService.findById(paymentId);
		if (source == null)
		{
			throw new BusinessException("�� ������ �������� � ��������������� " + paymentId);
		}

		new CompositeDocumentValidator(validators).validate(source);

		//���������� ����������
		metadata = MetadataCache.getExtendedMetadata(source);
		template = new TemplateBuilder(metadata, source).setClientCreationChannel(channelType).build();
		document = source;

		initialize(channelType);
	}

	protected State getInitialState(CreationType channelType) throws BusinessLogicException, BusinessException
	{
		if (FormType.isPaymentSystemPayment(template.getFormType()))
		{
			return new State(StateCode.INIT_TEMPLATE.name());
		}

		return new State(StateCode.DRAFTTEMPLATE.name());
	}

	private void initialize(CreationType channelType) throws BusinessException, BusinessLogicException
	{
		template.setState(getInitialState(channelType));
		fireEvent();

		OperationContextUtil.synchronizeObjectAndOperationContext(template);
	}

	protected void fireEvent() throws BusinessException, BusinessLogicException
	{
		//���������� ��������� �������� �� ���������
		StateMachineExecutor executor = new StateMachineExecutor(templateStateMachineService.getStateMachineByObject(template));
		executor.initialize(template);
	}

	public TemplateDocument getTemplate()
	{
		return template;
	}

	public BusinessDocument getDocument()
	{
		return document;
	}

	public Metadata getMetadata()
	{
		return metadata;
	}
}