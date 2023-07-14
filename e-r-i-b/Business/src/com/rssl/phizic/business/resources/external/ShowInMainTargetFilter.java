package com.rssl.phizic.business.resources.external;

import org.apache.commons.collections.Predicate;
import com.rssl.phizic.business.finances.targets.AccountTarget;

/**
 * Фильтр по целям (к которым приявязаны вклады), которые необходимо отображать на главной странице
 * @author lepihina
 * @ created 07.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class ShowInMainTargetFilter implements Predicate
{
	public boolean evaluate(Object object)
	{
		AccountTarget target = (AccountTarget) object;
		return target.getAccountLink().getShowInMain();
	}
}