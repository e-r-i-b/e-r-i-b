package com.rssl.phizic.operations.dictionaries.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.business.operations.restrictions.ServiceProvidersRestriction;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.operations.EditEntityOperation;
import org.hibernate.Session;

/**
 * Операция установки группы риска у поставщика услуг
 * @author niculichev
 * @ created 10.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class SetupGroupRiskServiceProvidersOperation extends EditServiceProvidersOperationBase implements EditEntityOperation<ServiceProviderBase, ServiceProvidersRestriction>
{
	private static final String NOT_FOUND_GROUP_RISK = "Невозможно изменить поставщика потому, что группа риска с id: %d не найдена";
	private static final SimpleService simpleService = new SimpleService();

	public void initialize(Long id) throws BusinessException
	{
		if(id == null)
			throw new BusinessException("Не задан id поставщика услуг для установки группы риска");

		ServiceProviderBase temp = providerService.findById(id, getInstanceName());

		if (temp == null)
			throw new BusinessException("Поставщик услуг с id = " + id + " не найден");

		if (!getRestriction().accept(temp))
			throw new RestrictionViolationException("Поставщик ID= " + temp.getId());

		provider = temp;
	}

	public void setGroupRisk(Long groupRiskId) throws BusinessException
	{
		if (groupRiskId == null)
		{
			provider.setGroupRisk(null);
			return;
		}

		GroupRisk groupRisk = simpleService.findById(GroupRisk.class, groupRiskId, getInstanceName());
		if (groupRisk == null)
			throw new ResourceNotFoundBusinessException(String.format(NOT_FOUND_GROUP_RISK, groupRiskId), GroupRisk.class);

		provider.setGroupRisk(groupRisk);
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		executeTransactional(new HibernateAction<Object>()
		{
			public Object run(Session session) throws Exception
			{
				providerService.addOrUpdate(provider, getInstanceName());
				return null;
			}
		});
	}

	public ServiceProviderBase getEntity() throws BusinessException, BusinessLogicException
	{
		return provider;
	}
}
