package com.rssl.auth.csa.back.servises.restrictions.operations.blockingRules;

import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.dataaccess.query.MultiLocaleBeanQuery;

import java.util.List;

/**
 * @author osminin
 * @ created 04.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Правило блокировки для мАпи
 */
public class MAPIBlockingRule extends ERIBBlockingRule
{
	/**
	 * @return все (активные) ограничения
	 */
	public List<ERIBBlockingRule> findAll() throws Exception
	{
		return getQuery(MAPIBlockingRule.class.getName() + ".findAllActive").executeListInternal();
	}

	private static BeanQuery getQuery(String queryName)
	{
		if(MultiLocaleContext.isDefaultLocale())
			return new BeanQuery(new Object(), queryName, null);
		return new MultiLocaleBeanQuery(new Object(), queryName, null, MultiLocaleContext.getLocaleId());
	}

}
