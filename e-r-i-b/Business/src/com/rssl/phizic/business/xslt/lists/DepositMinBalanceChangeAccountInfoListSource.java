package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.xslt.lists.builder.Entity;
import com.rssl.phizic.business.xslt.lists.builder.Field;

import java.util.Map;

/**
 *
 * Предоставляет данные о вкладе
 *
 * @author Balovtsev
 * @version 18.09.13 16:22
 */
public class DepositMinBalanceChangeAccountInfoListSource extends AbstractDepositMinBalanceChangeListSource<Entity>
{
	public Entity buildEntity(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		AccountLink accountLink = getAccountLink(Long.parseLong(params.get(ACCOUNT_ID)));

		Entity entity = new Entity(accountLink.getId().toString(), null);
		entity.addField(new Field("accountNumber",      accountLink.getNumber()));
		entity.addField(new Field("accountDescription", accountLink.getDescription()));
		entity.addField(new Field("accountCurrency",    accountLink.getCurrency().getCode()));
		return entity;
	}
}
