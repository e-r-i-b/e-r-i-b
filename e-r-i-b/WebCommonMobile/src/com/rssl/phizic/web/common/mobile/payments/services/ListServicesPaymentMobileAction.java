package com.rssl.phizic.web.common.mobile.payments.services;

import com.rssl.phizic.operations.ext.sbrf.payment.ListServicesPaymentOperation;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import com.rssl.phizic.web.actions.payments.ListPaymentServiceFormBase;
import com.rssl.phizic.web.common.client.ext.sbrf.payment.services.ListServicesPaymentAction;
import com.rssl.phizic.web.common.client.ext.sbrf.payment.services.ListServicesPaymentForm;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;

/**
 * Получение списка подуслуг группы услуг (если таковые имеются) либо списка поставщиков группы услуги.
 * В mAPI < 5.20 используется для получения как списка подуслуг группы услуг, так и списка поставщиков группы услуги.
 * В mAPI >= 5.20 используется для получения списка поставщиков категории верхнего уровня.
 * @author Dorzhinov
 * @ created 17.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListServicesPaymentMobileAction extends ListServicesPaymentAction
{
	protected boolean isMobileApi()
	{
		return true;
	}

	protected boolean isInternetBank()
	{
		return false;
	}

	protected void updateServicesList(ListServicesPaymentOperation operation, ListServicesPaymentForm frm) throws Exception
	{
		ListServicesPaymentMobileForm form = (ListServicesPaymentMobileForm) frm;

		boolean getLeaves = getFinalDescendants(operation);

		super.updateServicesList(operation, frm);

		if ( (getLeaves || operation.isLeaf()) &&
			 (!isIncludeServices(frm) || CollectionUtils.isEmpty(frm.getServices())))
		{
			form.setType("provider");
			if (!CollectionUtils.isEmpty(form.getProviders()))
				form.getList().addAll(form.getProviders());
		}
		else
		{
			form.setType("service");
			if (!CollectionUtils.isEmpty(form.getServices()))
				form.getList().addAll(form.getServices());
		}
	}

	protected boolean isAutoPayProvider(ListPaymentServiceFormBase form)
	{
		return ((ListServicesPaymentMobileForm) form).isAutoPaymentOnly();
	}

	protected boolean getFinalDescendants(ListServicesPaymentOperation operation)
	{
		return MobileApiUtil.isMobileApiGE(MobileAPIVersions.V5_20);
	}

	/**
	 * Признак что необходимо передать подкатегории
	 * @param form
	 * @return
	 */
	protected boolean isIncludeServices(ListPaymentServiceFormBase form)
	{
		return ((ListServicesPaymentMobileForm) form).isIncludeServices();
	}
}
