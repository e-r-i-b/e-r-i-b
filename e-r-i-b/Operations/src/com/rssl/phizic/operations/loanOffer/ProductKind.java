package com.rssl.phizic.operations.loanOffer;

/**
 * User: Moshenko
 * Date: 22.06.2011
 * Time: 12:27:36
 */
public enum ProductKind
{
	LOAN_OFFER("PR"),             //заявками на предварительно одобренные кредиты
	LOAN_CARD_OFFER("PK"),        //пзаявками на предодобренные кредитные карты
	LOAN_PRODUCT("KR"),           //заявками на получение кредита.
	LOAN_CARD_PRODUCT("KK"),      //заявками на получение кредитной карты.
	VIRTUAL_CARD(""),             //заявки на виртуальные карты
	PREAPPROVED_LOAN_CARD_CLAIM(""),     //заявки на предодобренные кредитные карты(новый механизм)
	LOAN_CARD_CLAIM("");                 //заявки на кредитные карты(новый механизм)

	private String value;

	ProductKind(String value)
	{
		this.value = value;
	}

	public String toValue() { return value; }


}
