package com.rssl.phizic.operations.ext.sbrf.payment.templates;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.ConfigImpl;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.documents.templates.service.filters.TemplateDocumentFilter;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.access.PersonLoginSource;
import com.rssl.phizic.operations.person.PersonOperationBase;
import com.rssl.common.forms.doc.CreationType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ author: Vagin
 * @ created: 05.04.2013
 * @ $Author
 * @ $Revision
 * ќпераци€ просмотра списков(список шаблонов/настройка видимости в разрезе каналов) шаблнов клиента.
 */
public class EmployeeListTemplateOperation  extends OperationBase<UserRestriction> implements ListEntitiesOperation<UserRestriction>
{
	private ActivePerson person;
	private List<TemplateDocument> templates;
	private CreationType channel;

	/**
	 * »ницаиализаци€ операции просмтора списка шаблонов текущего клиента.
	 * @param personId - идентификатор текущего клиента.
	 * @throws BusinessException
	 */
	public void initializeList(Long personId) throws BusinessException
	{
		PersonLoginSource source = new PersonLoginSource(personId);
		person = source.getPerson();
		// ѕроверка возможности работы с пользователем
		PersonOperationBase.checkRestriction(getRestriction(), person);
	}

	/**
	 * »нициализаци€ операции дл€ редактировани€ настройки видимости шаблонов в разрезе каналов.
	 * @param personId - идентификатор текущего клиента.
	 * @param channel - канал в разрезе коорого настраиваетс€ видимость шаблона.
	 * @param currentPage - текуща€ страница пагинации
	 * @param itemsPerPage - количество записей на странице
	 * @throws BusinessException
	 */
	public void initializeShowSettings(Long personId, String channel, int currentPage, int itemsPerPage) throws BusinessException, BusinessLogicException
	{
		initializeList(personId);

		this.channel   = CreationType.valueOf(channel);
		this.templates = TemplateDocumentService.getInstance().findAllExceptDeletedWithPagination(getPerson().asClient(), currentPage, itemsPerPage);
	}

	public void initialize(Long personId, TemplateDocumentFilter ... filters) throws BusinessException, BusinessLogicException
	{
		initializeList(personId);
		templates = TemplateDocumentService.getInstance().getFiltered(getPerson().asClient(), filters);
	}

	public ActivePerson getPerson() throws BusinessException
	{
		return person;
	}

	public Long getLoginId()
	{
		return person.getLogin().getId();
	}

	/**
	 * ќбновление настройки видимости
	 * @param selectedIds - идентификаторы шаблонов - выбраные как видимые в разрезе канала шаблоны.
	 */
	public void updateTemlateShowSettings(List<Long> selectedIds) throws BusinessException
	{
		List<TemplateDocument> result = new ArrayList<TemplateDocument>();
		for (TemplateDocument template : templates)
		{
			Long currentId = template.getId();
			boolean state = getState(template);
			boolean newState = false;

			for (Long selectId : selectedIds)
			{
				if (currentId.equals((selectId)))
				{
					newState = true;
					break;
				}
			}
			if (state^newState)
			{
				updateTemplate(template, newState);
				result.add(template);
			}
		}

		TemplateDocumentService.getInstance().addOrUpdate(result);
	}

	/**
	 * ѕолучить настроку видимости в разрезе канала.
	 * @param template - шаблон.
	 * @return true-видимый/false- невидимый
	 * @throws BusinessException
	 */
	private boolean getState(TemplateDocument template) throws BusinessException
	{
		switch (channel)
		{
			case atm:
				return template.getTemplateInfo().isUseInATM();
			case internet:
				return template.getTemplateInfo().isUseInERIB();
			case sms:
				return template.getTemplateInfo().isUseInERMB();
			case mobile:
				return template.getTemplateInfo().isUseInMAPI();
			default:
				throw new BusinessException("Ќекоррекный тип канала");
		}
	}

	private void updateTemplate(TemplateDocument template, boolean newState) throws BusinessException
	{
		switch (channel)
		{
			case atm:
				template.getTemplateInfo().setUseInATM(newState);  break;
			case internet:
				template.getTemplateInfo().setUseInERIB(newState); break;
			case sms:
				template.getTemplateInfo().setUseInERMB(newState); break;
			case mobile:
				template.getTemplateInfo().setUseInMAPI(newState); break;
			default:
				throw new BusinessException("Ќекоррекный тип канала");
		}
	}

	/**
	 * ѕолучение канал в разрещзе которого мен€етс€ настрока видимости.
	 * @return канал
	 */
	public CreationType getChannel()
	{
		return channel;
	}

	protected String getInstanceName()
	{
		ConfigImpl config = ConfigFactory.getConfig(ConfigImpl.class);
		return config.getDbSourceName();
	}

	/**
	 * ѕолучение списка шаблонов.
	 * @return список шаблонов.
	 */
	public List<TemplateDocument> getTemplates()
	{
		return Collections.unmodifiableList(templates);
	}
}
