package com.rssl.phizic.business.dictionaries.synchronization.processors.banks;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author komarov
 * @ created 03.02.2014
 * @ $Author$
 * @ $Revision$
 */
public class BankProcessor extends ProcessorBase<ResidentBank>
{
	private static final BankDictionaryService service = new BankDictionaryService();

	@Override
	protected Class<ResidentBank> getEntityClass()
	{
		return ResidentBank.class;
	}

	@Override
	protected ResidentBank getNewEntity()
	{
		return new ResidentBank();
	}

	@Override
	protected ResidentBank getEntity(String uuid) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass()).add(Expression.eq("synchKey", uuid));
		return simpleService.findSingle(criteria);
	}

	@Override
	protected void doSave(ResidentBank localEntity) throws BusinessException, BusinessLogicException
	{
		service.updateResidentBank(localEntity);
	}

	@Override
	protected void update(ResidentBank source, ResidentBank destination) throws BusinessException
	{
		destination.setAddress(source.getAddress());
		destination.setBIC(source.getBIC());
		destination.setINN(source.getINN());
		destination.setKPP(source.getKPP());
		destination.setOur(source.getOur());
		destination.setParticipantCode(source.getParticipantCode());
		destination.setShortName(source.getShortName());
		destination.setAccount(source.getAccount());
		destination.setName(source.getName());
		destination.setPlace(source.getPlace());
		destination.setSynchKey(source.getSynchKey());
		destination.setDateCh(source.getDateCh());
	}
}
