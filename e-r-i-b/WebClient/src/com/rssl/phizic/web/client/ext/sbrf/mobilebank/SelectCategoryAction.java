package com.rssl.phizic.web.client.ext.sbrf.mobilebank;

import com.rssl.phizic.web.actions.payments.CatalogActionBase;
import com.rssl.phizic.web.actions.payments.IndexAction;
import com.rssl.phizic.web.actions.payments.IndexForm;

/**
 * @author lukina
 * @ created 03.03.2011
 * @ $Author$
 * @ $Revision$
 * @deprecated ���������� �� ���
 */
@Deprecated
//todo CHG059738 �������
public class SelectCategoryAction extends CatalogActionBase
{
	protected boolean isMobileBank()
	{
		return true;
	}
	protected boolean isInternetBank()
	{
		return true;
	}

	protected String getChanel()
	{
		return "WEB";
	}

	protected String[] getFunctionality(IndexForm frm)
	{
		return new String[]{"MB_TEMPLATES"};
	}
}

