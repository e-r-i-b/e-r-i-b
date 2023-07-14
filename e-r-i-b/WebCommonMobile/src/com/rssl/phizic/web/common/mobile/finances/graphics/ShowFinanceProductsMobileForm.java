package com.rssl.phizic.web.common.mobile.finances.graphics;

import com.rssl.phizic.web.common.graphics.ShowFinancesForm;

/**
 * @ author: Gololobov
 * @ created: 24.01.14
 * @ $Author$
 * @ $Revision$
 *
 * Форма получения иинформации для отображения диаграммы "Доступные средства" в АЛФ в mAPI
 */
public class ShowFinanceProductsMobileForm extends ShowFinancesForm
{
	//показывать ли копейки в сумме остатка в рублях
	private boolean showKopeck;
	//для кредитных карт и карт с разрешенным овердрафтом: показывать ли сумму с учетом кредитных средств или без учета
	private boolean showWithOverdraft;

	public boolean isShowKopeck()
	{
		return showKopeck;
	}

	public void setShowKopeck(boolean showKopeck)
	{
		this.showKopeck = showKopeck;
	}

	public boolean isShowWithOverdraft()
	{
		return showWithOverdraft;
	}

	public void setShowWithOverdraft(boolean showWithOverdraft)
	{
		this.showWithOverdraft = showWithOverdraft;
	}
}
