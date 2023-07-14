package com.rssl.phizic.web.atm.deposits;

import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.atm.AtmApiConfig;
import com.rssl.phizic.operations.deposits.GetDepositProductsListOperation;
import com.rssl.phizic.web.common.client.ext.sbrf.deposits.ListDepositProductsExtendedAction;

import javax.xml.transform.Source;

/**
 * @author Pankin
 * @ created 12.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListDepositProductsATMAction extends ListDepositProductsExtendedAction
{
	protected Source getTemplateSource(GetDepositProductsListOperation op) throws BusinessException
	{
		return op.getMobileTemplateSource();
	}

	protected void setAdditionalParams(XmlConverter converter)
	{
		AtmApiConfig atmApiConfig = ConfigFactory.getConfig(AtmApiConfig.class);
		converter.setParameter("showNewDeposits", atmApiConfig.isShowDepositCodes());

		super.setAdditionalParams(converter);
	}
}
