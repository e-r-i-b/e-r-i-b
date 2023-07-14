package com.rssl.phizic.business.documents.strategies.limits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.limits.*;
import com.rssl.phizic.business.limits.link.LimitPaymentsLink;
import com.rssl.phizic.business.limits.link.LimitPaymentsLinkService;
import com.rssl.phizic.business.limits.users.LimitDocumentInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;

import java.util.List;

/**
 * Стратегия для высокорисковых поставщиков в Light схеме mAPI.
 * @author Dorzhinov
 * @ created 13.09.2013
 * @ $Author$
 * @ $Revision$
 */
public class MobileLightPlusLimitStrategy implements CheckDocumentStrategy
{
	private static final LimitPaymentsLinkService LIMIT_PAYMENTS_LINK_SERVICE = new LimitPaymentsLinkService();
	private static final DepartmentService DEPARTMENT_SERVICE = new DepartmentService();
	private BusinessDocument document;

	public MobileLightPlusLimitStrategy(BusinessDocument document)
	{
		this.document = document;
	}

	public boolean check(ClientAccumulateLimitsInfo amountInfo) throws BusinessException
	{
		//не mAPI
		if (!MobileApiUtil.isMobileApiGE(MobileAPIVersions.V6_00))
			return true;
		//не light
		if (!MobileApiUtil.isLightScheme())
			return true;
		//не перевод частному лицу и не оплата услуг
		if (!(document instanceof RurPayment))
			return true;
		//оплата услуг
		if (document instanceof JurPayment)
		{
			JurPayment servicePayment = (JurPayment) document;

			//определяем группу риска
			GroupRisk groupRisk = DocumentHelper.getGroupRisk(servicePayment.getReceiverInternalId());
			if (groupRisk == null)
			{
				List<LimitPaymentsLink> limitPaymentsLinks = LIMIT_PAYMENTS_LINK_SERVICE.findAll(((Department)document.getDepartment()).getRegion());
				for (LimitPaymentsLink link : limitPaymentsLinks)
				{
					if (link.getPaymentTypes().indexOf(servicePayment.getType()) >= 0)
						groupRisk = link.getGroupRisk();
				}
			}

			if (groupRisk == null)
				return true;
			GroupRiskRank groupRiskRank = groupRisk.getRank();
			//низкорисковых ПУ пропускаем
			if (groupRiskRank == GroupRiskRank.LOW)
				return true;
		}
		return ConfigFactory.getConfig(MobileApiConfig.class).isFieldDictInLightEnabled();
	}

	public boolean checkAndThrow(ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
	{
		return check(amountInfo);
	}

	public void process(LimitDocumentInfo limitDocumentInfo, ClientAccumulateLimitsInfo amountInfo) throws BusinessException, BusinessLogicException
	{
		if (!check(amountInfo))
		{
			throw new RequireAdditionConfirmLimitException("В light схеме запрещена оплата по справочнику доверенных получателей", RestrictionType.AMOUNT_IN_DAY, null);
		}
	}

	public void rollback() throws BusinessException, BusinessLogicException
	{
		//ничего не уменьшали, поэтому здесь ничего не делаем
	}
}
