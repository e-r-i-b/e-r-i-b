package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.phizic.web.image.ImageEditFormBase;

/**
 * @author krenev
 * @ created 16.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditServiceProviderFormBase extends ImageEditFormBase
{
	private boolean editable;
    private boolean isInternetShop;
	private String[] selectedIds = new String[]{};

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

    public String[] getSelectedIds()
	{
		return selectedIds;
	}

	public void setSelectedIds(String[] selectedIds)
	{
		this.selectedIds = selectedIds;
	}
}
