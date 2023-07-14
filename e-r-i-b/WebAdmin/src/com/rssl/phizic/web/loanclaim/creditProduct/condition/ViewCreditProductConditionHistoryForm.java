package com.rssl.phizic.web.loanclaim.creditProduct.condition;

import com.rssl.phizic.business.loanclaim.creditProduct.condition.CurrencyCreditProductCondition;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * @author Moshenko
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 * Форма просмотра истории условий по кредитным продуктам.
 */
public class ViewCreditProductConditionHistoryForm extends ActionFormBase
{
	private Long id;         //id условия
	private String currCode; //Код ваюты
	private List<CurrencyCreditProductCondition> hisrotyCurrCoditions; //Исторические по валютные условия

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCurrCode()
	{
		return currCode;
	}

	public void setCurrCode(String currCode)
	{
		this.currCode = currCode;
	}

	public List<CurrencyCreditProductCondition> getHisrotyCurrCoditions()
	{
		return hisrotyCurrCoditions;
	}

	public void setHisrotyCurrCoditions(List<CurrencyCreditProductCondition> hisrotyCurrCoditions)
	{
		this.hisrotyCurrCoditions = hisrotyCurrCoditions;
	}
}
