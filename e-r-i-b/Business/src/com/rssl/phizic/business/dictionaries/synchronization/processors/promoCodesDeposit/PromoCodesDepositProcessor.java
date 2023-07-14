package com.rssl.phizic.business.dictionaries.synchronization.processors.promoCodesDeposit;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.promoCodesDeposit.PromoCodeDeposit;
import com.rssl.phizic.business.dictionaries.promoCodesDeposit.PromoCodeDepositService;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;

/**
 * @author Gololobov
 * @ created 15.01.15
 * @ $Author$
 * @ $Revision$
 *
 * Процессор для синхронизации спрвочника промо-кодов
 */
public class PromoCodesDepositProcessor extends ProcessorBase<PromoCodeDeposit>
{

	private static final PromoCodeDepositService promoCodeDepositService = new PromoCodeDepositService();

	protected Class<PromoCodeDeposit> getEntityClass()
	{
		return PromoCodeDeposit.class;
	}

	protected PromoCodeDeposit getNewEntity()
	{
		return new PromoCodeDeposit();
	}

	protected PromoCodeDeposit getEntity(String uuid) throws BusinessException
	{
		return promoCodeDepositService.getPromoCodeDepositByUUID(uuid);
	}

	protected void update(PromoCodeDeposit source, PromoCodeDeposit destination) throws BusinessException
	{
		destination.setCode(source.getCode());
		destination.setCodeG(source.getCodeG());
		destination.setMask(source.getMask());
		destination.setCodeS(source.getCodeS());
		destination.setDateBegin(source.getDateBegin());
		destination.setDateEnd(source.getDateEnd());
		destination.setSrokBegin(source.getSrokBegin());
		destination.setSrokEnd(source.getSrokEnd());
		destination.setNumUse(source.getNumUse());
		destination.setPrior(source.getPrior());
		destination.setAbRemove(source.getAbRemove());
		destination.setActiveCount(source.getActiveCount());
		destination.setHistCount(source.getHistCount());
		destination.setNameAct(source.getNameAct());
		destination.setNameS(source.getNameS());
		destination.setNameF(source.getNameF());
        destination.setUuid(source.getUuid());
	}
}
