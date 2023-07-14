package com.rssl.phizic.web.ermb.tariff;

import com.rssl.phizic.business.ermb.ErmbTariff;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author Moshenko
 * @ created 12.12.13
 * @ $Author$
 * @ $Revision$
 * ����� ��� ����� ������ ����
 */
public class ErmbChangeTariffListForm extends ListFormBase
{
	private Long id;                 //id ��������� ������
	private ErmbTariff changeTariff; //����� ������� ����� ������

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public ErmbTariff getChangeTariff()
	{
		return changeTariff;
	}

	public void setChangeTariff(ErmbTariff changeTariff)
	{
		this.changeTariff = changeTariff;
	}
}
