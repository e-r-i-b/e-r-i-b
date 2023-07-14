package com.rssl.phizic.operations.userprofile;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.documents.StateCode;
import com.rssl.phizic.gate.payments.template.TemplateInfo;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author osminin
 * @ created 05.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Операция редактирования настроек видимости шаблонов
 */
public class EditClientTemplatesShowSettingsOperation extends OperationBase
{
	private List<TemplateDocument> templates = new ArrayList<TemplateDocument>();

	private CreationType channelType;
	private String changedIds;


	public void initialize(CreationType channelType) throws BusinessException, BusinessLogicException
	{
		this.channelType = channelType;

		templates = TemplateDocumentService.getInstance().findAllExceptDeleted(PersonHelper.getContextPerson().asClient());
	}

	public void initialize(CreationType channelType, String changedIds) throws BusinessException, BusinessLogicException
	{
		initialize(channelType);

		this.changedIds = changedIds;
	}

	public List<TemplateDocument> getTemplates() throws BusinessException
	{
		return Collections.unmodifiableList(templates);
	}

	@Transactional
	public void save() throws BusinessException
	{
		fillTemplates();

		TemplateDocumentService.getInstance().addOrUpdate(templates);
	}

	public CreationType getChannelType()
	{
		return channelType;
	}

	private void fillTemplates() throws BusinessException
	{
		// получаем список идентификаторов измененных шаблонов
		List<String> changedIdsList = getChangedIdsList();

		for (TemplateDocument template : templates)
		{
			// если шаблон не черновик и находится в списке изменных идентификаторов, то меняем его видимость в выбранном канале на обратную
			if (!isDraft(template) && changedIdsList.contains(template.getId().toString()))
			{
				revertShowSetting(template.getTemplateInfo());
			}
		}
	}

	private boolean isDraft(TemplateDocument template)
	{
		String code = template.getState().getCode();
		return code.equals(StateCode.DRAFTTEMPLATE.name()) || code.equals(StateCode.SAVED_TEMPLATE.name());
	}

	private List<String> getChangedIdsList()
	{
		if (StringHelper.isEmpty(changedIds))
			return Collections.emptyList();

		return Arrays.asList(changedIds.split(","));
	}

	private void revertShowSetting(TemplateInfo templateInfo) throws BusinessException
	{
		if (CreationType.mobile == channelType)
		{
			templateInfo.setUseInMAPI(!templateInfo.isUseInMAPI());
		}

		if (CreationType.atm == channelType)
		{
			templateInfo.setUseInATM(!templateInfo.isUseInATM());
		}

		if (CreationType.sms == channelType)
		{
			templateInfo.setUseInERMB(!templateInfo.isUseInERMB());
		}

		if (CreationType.internet == channelType)
		{
			templateInfo.setUseInERIB(!templateInfo.isUseInERIB());
		}
	}
}
