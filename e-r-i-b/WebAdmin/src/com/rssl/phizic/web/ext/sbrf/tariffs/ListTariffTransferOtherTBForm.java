package com.rssl.phizic.web.ext.sbrf.tariffs;

import com.rssl.phizic.business.ext.sbrf.tariffs.Tariff;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.List;

/**
 * Форма для списка тарифов на перевод в другой ТБ
 * @author niculichev
 * @ created 16.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListTariffTransferOtherTBForm extends ListFormBase
{
	private List<Tariff> tariffsOnOwnAccount;
	private List<Tariff> tariffsOnAnotherAccount;

	public List<Tariff> getTariffsOnOwnAccount()
	{
		return tariffsOnOwnAccount;
	}

	public void setTariffsOnOwnAccount(List<Tariff> tariffsOnOwnAccount)
	{
		this.tariffsOnOwnAccount = tariffsOnOwnAccount;
	}

	public List<Tariff> getTariffsOnAnotherAccount()
	{
		return tariffsOnAnotherAccount;
	}

	public void setTariffsOnAnotherAccount(List<Tariff> tariffsOnAnotherAccount)
	{
		this.tariffsOnAnotherAccount = tariffsOnAnotherAccount;
	}
}
