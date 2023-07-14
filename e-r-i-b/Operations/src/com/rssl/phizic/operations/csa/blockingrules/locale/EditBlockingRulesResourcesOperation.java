package com.rssl.phizic.operations.csa.blockingrules.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.Constants;
import com.rssl.phizic.business.ext.sbrf.csa.blockingrules.BlockingRules;
import com.rssl.phizic.business.ext.sbrf.csa.blockingrules.BlockingRulesService;
import com.rssl.phizic.business.ext.sbrf.csa.blockingrules.locale.BlockingRulesResources;
import com.rssl.phizic.business.locale.EribLocaleService;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourceService;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;

/**
 * Операция редактирования локалезависимых текстовок блокировок входа
 * @author komarov
 * @ created 18.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class EditBlockingRulesResourcesOperation extends OperationBase implements EditLanguageResourcesOperation<BlockingRulesResources,Long>
{
	private static final BlockingRulesService blockingRulesService = new BlockingRulesService();
	private static final LanguageResourceService<BlockingRulesResources> BLOCKING_RULES_LANGUAGE_RESOURCE_SERVICE = new LanguageResourceService<BlockingRulesResources>(BlockingRulesResources.class);
	private static final EribLocaleService LOCALE_SERVICE = new EribLocaleService();

	private ERIBLocale locale;
	private BlockingRulesResources entity;

	public void initialize(Long id, String localeId) throws BusinessException, BusinessLogicException
	{
		BlockingRules rule = blockingRulesService.findById(id);
		if (rule == null)
			throw new BusinessLogicException("Техперерыв с id = " + id + " не найден");

		locale = LOCALE_SERVICE.getById(localeId, getInstanceName());

		if(locale == null)
			throw new BusinessLogicException("Локаль с id= " + localeId + "не найдена");

		entity = BLOCKING_RULES_LANGUAGE_RESOURCE_SERVICE.findResById(id, localeId, getInstanceName());
		if(entity == null)
		{
			entity = new BlockingRulesResources();
			entity.setId(id);
			entity.setLocaleId(localeId);
		}
	}

	public ERIBLocale getLocale()
	{
		return locale;
	}

	public BlockingRulesResources getEntity()
	{
		return entity;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		BLOCKING_RULES_LANGUAGE_RESOURCE_SERVICE.addOrUpdate(entity, getInstanceName());
	}

	protected String getInstanceName()
	{
		return Constants.DB_CSA;
	}
}
