package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author krenev
 * @ created 16.04.2010
 * @ $Author$
 * @ $Revision$
 * Базовая форма для списка сущностей, привязанных к поставщику
 */
public class ServiceProviderListFormBase extends ListFormBase
{
	private boolean editable;
    private boolean isInternetShop;
	private String providerState;
	private Long id;// Идентифкатор постащика

	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}

	public boolean isEditable()
	{
		return editable;
	}

    public boolean isInternetShop()
    {
        return isInternetShop;
    }

    public void setInternetShop(boolean internetShop)
    {
        isInternetShop = internetShop;
    }

    public void setProviderState(String providerState)
	{
		this.providerState = providerState;
	}

	public String getProviderState()
	{
		return providerState;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
}
