package com.rssl.phizic.web.common.mobile.finances.graphics;

import com.rssl.phizic.web.common.graphics.ShowFinancesForm;

/**
 * @ author: Gololobov
 * @ created: 24.01.14
 * @ $Author$
 * @ $Revision$
 *
 * ����� ��������� ����������� ��� ����������� ��������� "��������� ��������" � ��� � mAPI
 */
public class ShowFinanceProductsMobileForm extends ShowFinancesForm
{
	//���������� �� ������� � ����� ������� � ������
	private boolean showKopeck;
	//��� ��������� ���� � ���� � ����������� �����������: ���������� �� ����� � ������ ��������� ������� ��� ��� �����
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
