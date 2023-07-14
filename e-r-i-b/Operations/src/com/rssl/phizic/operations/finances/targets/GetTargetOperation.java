package com.rssl.phizic.operations.finances.targets;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.finances.targets.AccountTargetService;
import com.rssl.phizic.business.resources.external.ShowInMainTargetFilter;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lepihina
 * @ created 03.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class GetTargetOperation extends OperationBase
{
	private static final AccountTargetService targetService = new AccountTargetService();

	/**
	 * ¬озвращает цель, прив€занную ко вкладу с идентификатором равным id
	 * @param id - идентификатор вклада
	 * @return цель
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public AccountTarget getTargetByAccountId(Long id) throws BusinessException, BusinessLogicException
	{
		AccountTarget target = targetService.findTargetByAccountId(id);

		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		if (target != null && !login.getId().equals(target.getLoginId()))
			throw new AccessException(" лиент с id = " + login.getId() + " не имеет доступа к цели с id = " + target.getId());

		return target;
	}

	/**
	 * ¬озвращает список целей, у которых есть прив€зка к вкладу
	 * @return список целей
	 * @throws BusinessException
	 */
	public List<AccountTarget> getTargetsWithAccounts() throws BusinessException
	{
		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		return targetService.findTargetsWithAccounts(login);
	}

	/**
	 * ¬озвращает список целей, у которых есть прив€зка к вкладу и которые необходимо отображать на главной странице
	 * @return список целей
	 * @throws BusinessException
	 */
	public List<AccountTarget> getShowInMainTargets() throws BusinessException
	{
		List<AccountTarget> targets = new ArrayList<AccountTarget>(getTargetsWithAccounts());
		ShowInMainTargetFilter filter = new ShowInMainTargetFilter();
		CollectionUtils.filter(targets, filter);
		return targets;
	}
}
