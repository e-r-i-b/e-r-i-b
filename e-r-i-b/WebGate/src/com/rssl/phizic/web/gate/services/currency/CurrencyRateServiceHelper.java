package com.rssl.phizic.web.gate.services.currency;

/**
 * @author: Pakhomova
 * @created: 19.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyRateServiceHelper
{

//	public com.rssl.phizic.web.gate.services.currency.generated.Money getMoney(com.rssl.phizic.common.types.Money money)
//	{
//		com.rssl.phizic.web.gate.services.currency.generated.Money moneyTo = new com.rssl.phizic.web.gate.services.currency.generated.Money();
//
//		moneyTo.setCurrency(getCurrency(money.getCurrency()));
//		moneyTo.setDecimal(money.getDecimal());
//
//		return moneyTo;
//	}
//
//	public com.rssl.phizic.web.gate.services.currency.generated.Currency getCurrency(com.rssl.phizic.common.types.Currency curr)
//	{
//		com.rssl.phizic.web.gate.services.currency.generated.Currency currTo = new com.rssl.phizic.web.gate.services.currency.generated.Currency();
//		currTo.setCode(curr.getCode());
//		currTo.setExternalId(curr.getExternalId());
//		currTo.setName(curr.getReceiverSurName());
//		currTo.setNumber(curr.getNumber());
//
//		return currTo;
//	}
//
//	public com.rssl.phizic.web.gate.services.currency.generated.Office getOffice(com.rssl.phizic.gate.dictionaries.officies.Office office)
//	{
//		com.rssl.phizic.web.gate.services.currency.generated.Office generated = new com.rssl.phizic.web.gate.services.currency.generated.Office();
//
//		generated.setAddress(office.getAddress());
//		generated.setBIC(office.getBIC());
//		generated.setCode(this.getCode(office.getCode()));
//		generated.setName(office.getReceiverSurName());
//		generated.setPostIndex(office.getPostIndex());
//		generated.setSynchKey((String)office.getSynchKey());
//		generated.setTelephone(office.getTelephone());
//
//		return generated;
//	}
//
//	public com.rssl.phizic.web.gate.services.currency.generated.Code getCode(com.rssl.phizic.gate.dictionaries.officies.Code code)
//	{
//		com.rssl.phizic.web.gate.services.currency.generated.Code generated = new com.rssl.phizic.web.gate.services.currency.generated.Code();
//		generated.setFields(code.getFields());
//		generated.setId(code.getId());
//
//		return generated;
//	}

//	public com.rssl.phizic.common.types.Money getMoney(com.rssl.phizic.web.gate.services.currency.generated.Money money)
//	{
//		return  new com.rssl.phizic.common.types.Money(money.getDecimal(), getCurrency(money.getCurrency()));
//	}
//
//
//	public com.rssl.phizic.common.types.Currency getCurrency(com.rssl.phizic.web.gate.services.currency.generated.Currency curr)
//	{
//		CurrencyImpl currTo = new CurrencyImpl();
//		currTo.setCode(curr.getCode());
//		currTo.setId(Long.decode(curr.getExternalId())); //todo проверить
//		currTo.setName(curr.getReceiverSurName());
//		currTo.setNumber(curr.getNumber());
//
//		return currTo;
//	}
//
//	public com.rssl.phizic.gate.dictionaries.officies.Office getOffice(com.rssl.phizic.web.gate.services.currency.generated.Office office)
//	{
//		OfficeImpl officeTo = new OfficeImpl();
//		officeTo.setAddress(office.getAddress());
//		officeTo.setBIC(office.getBIC());
//		officeTo.setCode(this.getCode(office.getCode()));
//		officeTo.setName(office.getReceiverSurName());
//		officeTo.setPostIndex(office.getPostIndex());
//		officeTo.setSynchKey(office.getSynchKey());
//		officeTo.setTelephone(office.getTelephone());
//
//		return officeTo;
//	}
//
//	public com.rssl.phizic.gate.dictionaries.officies.Code getCode(com.rssl.phizic.web.gate.services.currency.generated.Code generated)
//	{
//		CodeImpl code = new CodeImpl();
////		code.setFields(generated.getFields());    //todo
//		code.setId(generated.getId());
//
//		return code;
//	}
//
//	public com.rssl.phizic.web.gate.services.currency.generated.CurrencyRate convertRate(com.rssl.phizic.common.types.CurrencyRate rate)
//	{
//		//todo посмотреть, какой реальный currencyRateType получается
//		com.rssl.phizic.web.gate.services.currency.generated.CurrencyRate rateTo = new com.rssl.phizic.web.gate.services.currency.generated.CurrencyRate(
//				getCurrency(rate.getFromCurrency()), rate.getFromValue(), getCurrency(rate.getToCurrency()), rate.getToValue(), rate.getType().getDescription()
//		);
//		return rateTo;
//	}

}
