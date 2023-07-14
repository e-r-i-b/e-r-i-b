package com.rssl.phizic.web.ext.sbrf.tariffs;

import com.rssl.phizic.business.ext.sbrf.tariffs.Tariff;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.List;

/**
 * Форма для списка тарифов на перевод в другой банк
 * @author niculichev
 * @ created 16.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListTariffTransferOtherBankForm extends ListFormBase
{
	private List<Tariff> tariffsInOtherBank;

	public List<Tariff> getTariffsInOtherBank()
	{
		return tariffsInOtherBank;
	}

	public void setTariffsInOtherBank(List<Tariff> tariffsInOtherBank)
	{
		this.tariffsInOtherBank = tariffsInOtherBank;
	}
}
