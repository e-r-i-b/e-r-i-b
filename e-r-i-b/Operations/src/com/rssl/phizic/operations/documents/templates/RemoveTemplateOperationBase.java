package com.rssl.phizic.operations.documents.templates;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.documents.templates.source.TemplateDocumentSource;
import com.rssl.phizic.business.documents.templates.validators.OwnerTemplateValidator;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.business.statemachine.documents.templates.TemplateStateMachineService;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Evgrafov
 * @ created 14.12.2005
 * @ $Author: erkin $
 * @ $Revision: 63147 $
 */

public abstract class RemoveTemplateOperationBase extends OperationBase implements RemoveTemplateOperation
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

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		template = TemplateDocumentService.getInstance().findById(id);
		new OwnerTemplateValidator().validate(template);
	}

	public TemplateDocument getEntity()
	{
		return template;
	}
}
