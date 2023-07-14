package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.CardFilter;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 13.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * В дополнении к CardListSource прибалена информация о владельце  
 */
public class CardListSourceWithOwnerInfo extends CardListSource
{
	public static final String NEED_OWNER_INFO_PARAM = "needOwnerInfo";

	protected boolean needOwnerInfo;

	/**
	 * Инициализирует параметры.
	 *
	 * @param params список параметров.
	 */
	private void init(Map<String, String> params)
	{
		String needOwnerInfoStr = params.get(NEED_OWNER_INFO_PARAM);

		if (StringHelper.isEmpty(needOwnerInfoStr))
		{
			needOwnerInfo = true;
			return;
		}

		needOwnerInfo = Boolean.parseBoolean(needOwnerInfoStr);
	}

	public CardListSourceWithOwnerInfo(EntityListDefinition definition)
	{
		super(definition);
		init(definition.getParameters());
	}

    public CardListSourceWithOwnerInfo(EntityListDefinition definition, CardFilter cardFilter)
    {
        super(definition, cardFilter);
	    init(definition.getParameters());
    }

    public CardListSourceWithOwnerInfo(EntityListDefinition definition, Map parameters) throws BusinessException
    {
	   	super(definition, parameters);
	    init(definition.getParameters());
    }
	
	protected void addOwnerInfo(EntityListBuilder builder, CardLink cardLink) throws BusinessException, BusinessLogicException
	{
		if (!needOwnerInfo)
			return;

		Client cardOwnerInfo = cardLink.getCardClient();
		if (cardOwnerInfo == null)
			throw new BusinessException("Не получили владельца карты №" + cardLink.getNumber());

		builder.appentField("cardAccountOwnerId",cardOwnerInfo.getId());
		builder.appentField("cardOwner", PersonHelper.getFormattedPersonName(
				cardOwnerInfo.getFirstName(), cardOwnerInfo.getSurName(), cardOwnerInfo.getPatrName()));
	}
}
