package com.rssl.phizic.web.common.client.favourite;

import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Collections;
import java.util.List;

/**
 * @author vagin
 * @ created 16.01.15
 * @ $Author$
 * @ $Revision$
 * Форма списка копилок клиента.
 */
public class ListMoneyBoxForm extends EditFormBase
{
	private List<AutoSubscriptionLink> data;
	private Long accountId;
	private Long cardId;

	public List<AutoSubscriptionLink> getData()
	{
		return Collections.unmodifiableList(data);
	}

	public void setData(List<AutoSubscriptionLink> data)
	{
		this.data = data;
	}

	public Long getAccountId()
	{
		return accountId;
	}

	public void setAccountId(Long accountId)
	{
		this.accountId = accountId;
	}

	public Long getCardId()
	{
		return cardId;
	}

	public void setCardId(Long cardId)
	{
		this.cardId = cardId;
	}
}
