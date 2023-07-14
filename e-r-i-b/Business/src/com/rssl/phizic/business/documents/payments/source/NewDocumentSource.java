package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.DocumentService;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.validators.DocumentValidator;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.documents.templates.validators.CompositeTemplateValidator;
import com.rssl.phizic.business.documents.templates.validators.TemplateValidator;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.payments.forms.meta.NewDocumentHandler;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.common.types.csa.ProfileType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.logging.operations.context.OperationContextUtil;
import com.rssl.phizic.utils.MaskUtil;

/**
 * @author Evgrafov
 * @ created 13.12.2005
 * @ $Author: saharnova $
 * @ $Revision: 75755 $
 * Источник нового документа. поддерживает работу в режимах:
 * 1) создание нового чистого документа
 * 2) создание нового документа на основе существующего источника
 */
public class NewDocumentSource extends PersonDocumentSourceBase implements DocumentSource
{
	protected static final ExternalResourceService resourceService = new ExternalResourceService();
	protected static final DocumentService documentService = new DocumentService();
	protected static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();


	protected BusinessDocument document;
	protected Metadata metadata;

	protected NewDocumentSource(){}

	/**
	 * Констуктор источника нового чистого докумена
	 * @param formName имя формы
	 * @param initialValuesSource начальные данные
	 * @param type тип создания докумена - смс/интернет/мобильное приложение
	 * @param creationSourceType тип создания операции
	 */
	public NewDocumentSource(String formName, FieldValuesSource initialValuesSource, CreationType type, CreationSourceType creationSourceType) throws BusinessException, BusinessLogicException
	{
		metadata = MetadataCache.getExtendedMetadata(formName, initialValuesSource);
		document = documentService.createDocument(metadata, initialValuesSource, null);
		initDocument(type, creationSourceType);
	}

	/**
	 * Констуктор источника нового чистого докумена
	 * @param formName имя формы
	 * @param initialValuesSource начальные данные
	 * @param type тип создания докумена - смс/интернет/мобильное приложение
	 * @param creationSourceType тип создания операции
	 * @param messageCollector хранилище ошибок, может быть null
	 */
	public NewDocumentSource(String formName, FieldValuesSource initialValuesSource, CreationType type, CreationSourceType creationSourceType, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		metadata = MetadataCache.getExtendedMetadata(formName, initialValuesSource);
		document = documentService.createDocument(metadata, initialValuesSource, messageCollector);
		initDocument(type, creationSourceType);
	}

	/**
	 * Констуктор источника нового докумена на основе существующего шаблона
	 * @param templateId идентификатор шаблона
	 * @param validators валидаторы шаблона документа
	 * @param creationType тип создания докумена - смс/интернет/мобильное приложение
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public NewDocumentSource(Long templateId, CreationType creationType, TemplateValidator ... validators) throws BusinessException, BusinessLogicException
	{
		this(templateId, creationType, false, validators);
	}

	/**
	 * Констуктор источника нового докумена на основе существующего шаблона
	 * @param templateId идентификатор шаблона
	 * @param reminder создание через выставленный счет
	 * @param validators валидаторы шаблона документа
	 * @param creationType тип создания докумена - смс/интернет/мобильное приложение
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public NewDocumentSource(Long templateId, CreationType creationType, boolean reminder, TemplateValidator ... validators) throws BusinessException, BusinessLogicException
	{
		this(TemplateDocumentService.getInstance().findById(templateId), creationType, validators);
		document.setMarkReminder(reminder);
	}

	/**
	 * Констуктор источника нового докумена на основе существующего шаблона
	 * @param template шаблон на основе которого создается новой документ
	 * @param validators валидаторы шаблона документа
	 * @param creationType тип создания докумена - смс/интернет/мобильное приложение
	 */
	public NewDocumentSource(TemplateDocument template, CreationType creationType, TemplateValidator ... validators) throws BusinessException, BusinessLogicException
	{
		if (template == null)
		{
			throw new BusinessException("Не задан шаблон документа");
		}

		new CompositeTemplateValidator(validators).validate(template);

		metadata = MetadataCache.getExtendedMetadata(null, template);
		document = documentService.createDocument(metadata, template);

		initDocument(creationType, CreationSourceType.template);
	}

	/**
	 * Констуктор источника нового докумена на основе существующего документа
	 * @param paymentId id существующего документа, на основе которого создается новой документ
	 * @param documentValidator стратегия провеки допустимости редактирования данного документа
	 * @param creationType создания докумена - смс/интернет/мобильное приложение
	 */
	public NewDocumentSource(Long paymentId, DocumentValidator documentValidator, CreationType creationType) throws BusinessException, BusinessLogicException
	{
		this(businessDocumentService.findById(paymentId), documentValidator, creationType);
	}

	/**
	 * Констуктор источника нового докумена на основе существующего источника
	 * @param source id существующего шаблона на основе которого создается новой документ
	 * @param documentValidator стратегия провеки допустимости редактирования данного документа
	 * @param type тип создания докумена - смс/интернет/мобильное приложение
	 */
	public NewDocumentSource(BusinessDocument source, DocumentValidator documentValidator, CreationType type) throws BusinessException, BusinessLogicException
	{
		if (source == null)
		{
			throw new BusinessException("Документ, на основе которого создается новый документ, не может быть null");
		}

		documentValidator.validate(source);

		metadata = MetadataCache.getExtendedMetadata(source);
		document = HelperSource.copyPersonDocument(source);

		initDocument(type, CreationSourceType.copy);
	}

	protected void initDocument(CreationType type, CreationSourceType creationSourceType) throws BusinessException, BusinessLogicException
	{
		try
		{
			document.setDepartment(getDepartment());
			document.setCreationType(type);
			document.setState(new State("INITIAL"));
			document.setCreationSourceType(creationSourceType);
			document.setStateMachineName(metadata.getStateMachineInfo().getName());
			document.setLoginType(getLoginType());
			document.setTemporaryNodeId(getNodeTemporaryNumber());

			if (!CreationType.system.equals(type))
			{
				if (CreationType.internet.equals(type))
				{
					document.setDeviceInfo(AuthenticationContext.getContext().getBrowserInfo());
				}
				else
				{
					document.setDeviceInfo(AuthenticationContext.getContext().getDeviceInfo());
				}
			}
			// если документ создает сотрудник, то сеттим данные сотрудника
			if (isEmployeeCreate())
			{
				Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
				document.setCreatedEmployeeLoginId(employee.getLogin().getId());
			}

			getNewDocumentHandler().process(document, new StateMachineEvent());
			OperationContextUtil.synchronizeObjectAndOperationContext(document);
		}
		catch (DocumentLogicException e)
		{
			throw new BusinessException(e);
		}
		catch (DocumentException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	public BusinessDocument getDocument()
	{
		return document;
	}

	public Metadata getMetadata()
	{
		return metadata;
	}

	/**
	 * Получить ресурс списания - карту
	 * @param loginId логин
	 * @param number номер карты
	 * @return линк
	 * @throws BusinessException
	 */
	protected CardLink getFromResource(Long loginId, String number) throws BusinessException
	{
		CardLink cardLink = resourceService.findLinkByNumber(loginId, ResourceType.CARD, number);
		if(cardLink == null)
			throw new BusinessException("Не найдена карта c номером " + MaskUtil.getCutCardNumber(number) + " для логина с id = " + loginId);

		return cardLink;
	}

	/**
	 * @return тип аутентификации клиента
	 */
	protected LoginType getLoginType()
	{
		return AuthenticationContext.getContext().getLoginType();
	}

	/**
	 * @return тип аутентификации клиента
	 */
	protected Long getNodeTemporaryNumber()
	{
		ProfileType type = AuthenticationContext.getContext().getProfileType();
		if(type != ProfileType.TEMPORARY)
			return null;

		ApplicationAutoRefreshConfig config = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class);
		return config.getNodeNumber();
	}

	/**
	 * @return true - создание документа из АРМ сотрудника
	 */
	protected boolean isEmployeeCreate()
	{
		return AccessType.employee.equals(AuthModule.getAuthModule().getPrincipal().getAccessType());
	}

	protected NewDocumentHandler getNewDocumentHandler() throws DocumentException, DocumentLogicException
	{
		return new NewDocumentHandler();
	}
}
