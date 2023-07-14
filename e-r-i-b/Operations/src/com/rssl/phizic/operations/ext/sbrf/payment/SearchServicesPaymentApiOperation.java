package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.locale.MultiLocaleQueryHelper;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;

/**
 * Операция <b>поиска</b> поставщиков.
 * searchMApiLT52.ftl - mAPI [3.01; 5.20). Старая иерархия услуг (< 11 релиз)
 * searchServices - atmAPI (с настройкой "как в браузерной версии"). Поиск по ГУ. Возвращаются ГУ.
 * @author Dorzhinov
 * @ created 28.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class SearchServicesPaymentApiOperation extends ListServicesPaymentSearchOperation
{
	private boolean isIQWaveAutoPaymentPermit;
	private boolean isESBAutoPaymentPermit;

	public boolean isIQWaveAutoPaymentPermit()
	{
		return isIQWaveAutoPaymentPermit;
	}

	public void setIQWaveAutoPaymentPermit(boolean IQWaveAutoPaymentPermit)
	{
		isIQWaveAutoPaymentPermit = IQWaveAutoPaymentPermit;
	}

	public boolean isESBAutoPaymentPermit()
	{
		return isESBAutoPaymentPermit;
	}

	public void setESBAutoPaymentPermit(boolean ESBAutoPaymentPermit)
	{
		isESBAutoPaymentPermit = ESBAutoPaymentPermit;
	}

	/**
	 * Проверяет доступность шинных автоплатежей начиная с версии 5.10. До версии 5.10 всегда возвращает false.
	 * @return
	 */
	public boolean isESBAutoPaymentPermitSince51() throws BusinessException
	{
		VersionNumber apiVersion = getClientAPIVersion();
		if (apiVersion == null)
			throw new BusinessException("apiVersion is null");
		return isESBAutoPaymentPermit && apiVersion.ge(MobileAPIVersions.V5_10);
	}

	public Query createQuery(String name)
	{
		return MultiLocaleQueryHelper.getOperationQuery(this, name, null);
	}
}
