package com.rssl.phizic.web.common.mobile.deposits;

import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.operations.deposits.GetDepositProductsListOperation;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import com.rssl.phizic.web.common.client.ext.sbrf.deposits.ListDepositProductsExtendedAction;

import java.util.*;
import javax.xml.transform.Source;

/**
 * @author Pankin
 * @ created 12.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListDepositProductsMobileAction extends ListDepositProductsExtendedAction
{
	protected Source getTemplateSource(GetDepositProductsListOperation op) throws BusinessException
	{
		return op.getMobileTemplateSource();
	}

	protected void setAdditionalParams(XmlConverter converter)
	{
		if (MobileApiUtil.isMobileApiGE(MobileAPIVersions.V8_00))
		{
			converter.setParameter("showNewDeposits", true);
		}

		super.setAdditionalParams(converter);
	}

	/**
	 * Для мапи < 8 нужно выбрать только те вкладные продукты, что прописаны в настройках
	 * @param entities - вкладные продукты для проверки
	 * @return
	 */
	protected List<DepositProductEntity> filterDepositProductEntities(List<DepositProductEntity> entities)
	{
		if ((MobileApiUtil.isMobileApiLT(MobileAPIVersions.V8_00)) && !entities.isEmpty())
		{
			MobileApiConfig mobileApiConfig = ConfigFactory.getConfig(MobileApiConfig.class);
			Map<String, String> allowedCodes = mobileApiConfig.getOldDepositCodesList();
			List<DepositProductEntity> filteredEntities = new ArrayList<DepositProductEntity>();

			for (DepositProductEntity entity : entities)
			{
				String entityGroupCode = entity.getGroupCode().toString();
				String groupCode = StringHelper.equals(entityGroupCode, "0") ? "-22" : entityGroupCode;

				if (StringHelper.equalsNullIgnore(allowedCodes.get(entity.getDepositType().toString()), groupCode))
					filteredEntities.add(entity);
			}
			return filteredEntities;
		}
		return entities;
	}
}
