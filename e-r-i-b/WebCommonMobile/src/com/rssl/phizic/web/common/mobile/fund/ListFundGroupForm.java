package com.rssl.phizic.web.common.mobile.fund;

import com.rssl.phizic.business.fund.initiator.FundGroup;
import org.apache.struts.action.ActionForm;

import java.util.ArrayList;
import java.util.List;

/**
 * @auhor: tisov
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 * [Краудгифтинг] Форма просмотра списка групп получателей
 */
public class ListFundGroupForm extends ActionForm
{
	private List<FundGroup> list = new ArrayList<FundGroup>();   //Список групп получателей
	private Long status;          //статус обработки запроса из мАпи

	public List<FundGroup> getList()
	{
		return list;
	}

	public void setList(List<FundGroup> list)
	{
		this.list = list;
	}

	public Long getStatus()
	{
		return status;
	}

	public void setStatus(Long status)
	{
		this.status = status;
	}

}
