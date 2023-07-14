package com.rssl.phizic.operations.fund.group;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.fund.initiator.FundGroup;
import com.rssl.phizic.business.fund.initiator.FundGroupService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @auhor: tisov
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 * [Краудгифтинг] Операция просмотра списка групп получателей
 */
public class ListFundGroupOperation extends OperationBase
{
	private List<FundGroup> list = new ArrayList<FundGroup>();

	public void initialize() throws BusinessException
	{
		Long loginId = PersonContext.getPersonDataProvider().getPersonData().getLogin().getId();
		FundGroupService service = new FundGroupService();
		list = service.getFundGroupsByLoginId(loginId);
		for (FundGroup item:list)
		{
			item.setPhones(service.getPhonesByGroupId(item.getId()));
		}
	}


	public List<FundGroup> getList()
	{
		return list;
	}

	public void setList(List<FundGroup> list)
	{
		this.list = list;
	}
}
