package com.rssl.phizic.operations.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCode;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCodeComparator;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCodeService;
import com.rssl.phizic.business.deposits.DepositEntityService;
import com.rssl.phizic.business.deposits.DepositEntityVisibilityService;
import com.rssl.phizic.business.dictionaries.promoCodesDeposit.PromoCodeDeposit;
import com.rssl.phizic.business.dictionaries.promoCodesDeposit.PromoCodeDepositService;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.*;
import com.rssl.phizic.business.ext.sbrf.dictionaries.SynchronizeSettingsUtil;
import com.rssl.phizic.common.types.MinMax;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.security.PermissionUtil;

import java.util.*;

/**
 * @author Evgrafov
 * @ created 15.05.2006
 * @ $Author: balovtsev $
 * @ $Revision: 84940 $
 */

public class GetDepositProductsListOperation extends GetDepositProductsListOperationBase
{
	private static final ClientPromoCodeService service = new ClientPromoCodeService();
	private static final PromoCodeDepositService promoCodeDepositService = new PromoCodeDepositService();
	private static final DepositEntityService depositEntityService = new DepositEntityService();
	private static final DepositEntityVisibilityService visibilityServiceService = new DepositEntityVisibilityService();

	// промо-коды клиента
	private List<PromoCodeDeposit> promoCodes = new ArrayList<PromoCodeDeposit>();
	// тарифные планы, содержащиеся в справочнике. Если ставки с клиентским ТП будут не найдены, то ТП == 0 будет считаться любой код, не содержащийся в справочнике
	private List<Long> dictionaryTariffPlanCodes;
	// является ли клиент пенсионером
	private boolean isPensioner;
	// является ли клиент пенсионером
	private Long clientTariffPlan;

	public void initialize(boolean isPensioner, Long clientTariffPlan) throws BusinessException
	{
		if(PersonContext.isAvailable() && PermissionUtil.impliesService("CreatePromoAccountOpeningClaimService"))
		{
			List<ClientPromoCode> activeClientPromoCodes = service.getActiveClientPromoCodes(PersonContext.getPersonDataProvider().getPersonData().getLogin().getId());
			Collections.sort(activeClientPromoCodes, new ClientPromoCodeComparator());
			List<PromoCodeDeposit> clientPromoCodeDeposits = new ArrayList<PromoCodeDeposit>();
			for (ClientPromoCode clientPromoCode : activeClientPromoCodes)
			{
				clientPromoCodeDeposits.add(clientPromoCode.getPromoCodeDeposit());
			}
			this.promoCodes = clientPromoCodeDeposits;
		}
		else if (ApplicationConfig.getIt().getApplicationInfo().isAdminApplication())
		{
			this.promoCodes = promoCodeDepositService.getActualPromoCodesDepositList();
		}
		this.dictionaryTariffPlanCodes = TariffPlanHelper.getDictionaryTariffCodes();
		this.isPensioner = isPensioner;
		this.clientTariffPlan = clientTariffPlan;
	}

	/**
	 * Получить список вкладных продуктов.
	 * Клиенту отображаем только те, что доступны в его ТБ.
	 * Сотруднику - все, но нужно отсеять карточные и ОМС типы вкладов (лишнее в справочнике ЦАС НСИ)
	 * @param tb - код тербанка
	 * @param showWithInitialFeeOnly - показывать только вклады с начальным взносом (для открытия с закрытием)
	 * @return список вкладов
	 * @throws BusinessException
	 */
	public List<DepositProductEntity> getDepositEntities(String tb, Boolean showWithInitialFeeOnly) throws BusinessException
	{

		ApplicationConfig config = ApplicationConfig.getIt();
		boolean admin = config.getApplicationInfo().isAdminApplication();

		List<Long> depositTypeList = new ArrayList<Long>();
		// для сотрудника выбираем все типы из справочника ЦАС НСИ. Исключаем карточные и ОМС
		if (admin)
		{
			List<Long> imaTypes = SynchronizeSettingsUtil.getIMAProductTypeValues();
			MinMax<Long> cardTypes = SynchronizeSettingsUtil.getMinMaxCardProductKindValues();
			List<Long> actualDepositTypeList = visibilityServiceService.getAllTypes();
			for (Long type : actualDepositTypeList)
			{
				if (type > 42 && !imaTypes.contains(type) && !(cardTypes.getMin() <= type &&  type <= cardTypes.getMax()) && type != 48 && type != 49)
					depositTypeList.add(type);
			}
		}
		// клиенту отображаем строго с учетом видимости в ТБ.
		// карточных и ОМС быть не может, т.к. сотрудник не может их разрешить для открытия (не видит их в списке)
		else
			depositTypeList = visibilityServiceService.getOnlineAvailableTypes(tb);

		if (depositTypeList.isEmpty())
		{
			return Collections.emptyList();
		}

		return depositEntityService.getDepositEntities(depositTypeList, promoCodes, dictionaryTariffPlanCodes, isPensioner, clientTariffPlan, admin, tb, showWithInitialFeeOnly);
	}
}