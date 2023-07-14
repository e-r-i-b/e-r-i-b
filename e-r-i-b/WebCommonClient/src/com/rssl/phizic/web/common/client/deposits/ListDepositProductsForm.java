package com.rssl.phizic.web.common.client.deposits;

import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 12.05.2006
 * @ $Author: egorovaav $
 * @ $Revision: 78194 $
 */

public class ListDepositProductsForm extends ListFormBase
{
	private String category;
	private String form;
	private String listHtml;
    private int promoDivMaxLength;
	private List<DepositProductEntity> depositProductEntities;

	public String getListHtml()
	{
		return listHtml;
	}

	public void setListHtml(String listHtml)
	{
		this.listHtml = listHtml;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getForm()
	{
		return form;
	}

	public void setForm(String form)
	{
		this.form = form;
	}

    public int getPromoDivMaxLength()
    {
        return promoDivMaxLength;
    }

    public void setPromoDivMaxLength(int promoDivMaxLength)
    {
        this.promoDivMaxLength = promoDivMaxLength;
	}

	public List<DepositProductEntity> getDepositProductEntities()
	{
		return depositProductEntities;
	}

	public void setDepositProductEntities(List<DepositProductEntity> depositProductEntities)
	{
		this.depositProductEntities = depositProductEntities;
	}
}