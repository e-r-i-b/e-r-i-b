package com.rssl.phizic.business.pfp.filters;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author akrenev
 * @ created 12.05.2012
 * @ $Author$
 * @ $Revision$
 *
 * Проверяем пустые ли портфели
 */
public class EmptyPortfolioFilter extends HandlerFilterBase
{
	public boolean isEnabled(StateObject document) throws DocumentException
	{
		if(!(document instanceof PersonalFinanceProfile))
			throw new DocumentException("Неверный тип объекта стейт машины. Должен быть PersonalFinanceProfile");

		return CollectionUtils.isEmpty(((PersonalFinanceProfile) document).getPortfolioList());
	}
}
