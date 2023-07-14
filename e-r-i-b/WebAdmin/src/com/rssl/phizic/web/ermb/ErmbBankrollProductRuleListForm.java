package com.rssl.phizic.web.ermb;

import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * Форма просмотра списка правил включения видимости продуктов по умолчанию
 * @author Rtischeva
 * @ created 03.12.13
 * @ $Author$
 * @ $Revision$
 */
public class ErmbBankrollProductRuleListForm extends ActionFormBase
{
	private List<BankrollProductRuleView>  rules;

	private Long id;

	private Long[] selectedIds;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long[] getSelectedIds()
	{
		return selectedIds;
	}

	public void setSelectedIds(Long[] selectedIds)
	{
		this.selectedIds = selectedIds;
	}

	public List<BankrollProductRuleView>  getRules()
	{
		return rules;
	}

	public void setRules(List<BankrollProductRuleView>  rules)
	{
		this.rules = rules;
	}
}
