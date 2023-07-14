package com.rssl.phizic.operations.finances.targets;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.resources.external.ShowOperationLinkFilter;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.ListEntitiesOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * ќпераци€ просмотра списка целей клиента
 * @author lepihina
 * @ created 20.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class ListTargetOperation extends BaseTargetOperation implements ListEntitiesOperation
{
	private static final ShowOperationLinkFilter operationLinkFilter = new ShowOperationLinkFilter();
	private List<AccountTarget> targets = new ArrayList<AccountTarget>();

	/**
	 * »нициализаци€ операции
	 * @throws BusinessException
	 */
	public void initialize() throws BusinessException
	{
		try
		{
			targets = PersonContext.getPersonDataProvider().getPersonData().getAccountTargets();
		}
		catch (BusinessLogicException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Ќеобходимо ли получать выписку по счету, к которому прив€зана цель
	 * @param target - цель
	 * @return true - выписка необходима
	 */
	public boolean needAbstruct(AccountTarget target)
	{
		return target.getAccountLink() != null && operationLinkFilter.evaluate(target.getAccountLink());
	}

	/**
	 * ¬озвращает список целей клиента
	 * @return список целей клиента
	 */
	public List<AccountTarget> getTargets()
	{
		return targets;
	}
}
