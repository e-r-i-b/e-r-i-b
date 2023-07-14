package com.rssl.phizic.business.dictionaries.synchronization.processors.quick.pay;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.business.quick.pay.PanelBlock;
import com.rssl.phizic.business.quick.pay.QuickPaymentPanel;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author komarov
 * @ created 24.02.2014
 * @ $Author$
 * @ $Revision$
 */
public class QuickPaymentPanelProcessor extends ProcessorBase<QuickPaymentPanel>
{
	private static final SimpleService service = new SimpleService();

	@Override
	protected Class<QuickPaymentPanel> getEntityClass()
	{
		return QuickPaymentPanel.class;
	}

	@Override
	protected QuickPaymentPanel getNewEntity()
	{
		QuickPaymentPanel panel = new QuickPaymentPanel();
		panel.setDepartments(new HashSet<String>());
		panel.setPanelBlocks(new ArrayList<PanelBlock>());
		return panel;
	}

	@Override
	protected QuickPaymentPanel getEntity(String uuid) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass()).add(Expression.eq("uuid", uuid));
		return simpleService.findSingle(criteria);
	}

	@Override
	protected void update(QuickPaymentPanel source, QuickPaymentPanel destination) throws BusinessException
	{
		destination.getDepartments().clear();
		destination.getDepartments().addAll(source.getDepartments());
		destination.setName(source.getName());
		destination.setPeriodFrom(source.getPeriodFrom());
		destination.setPeriodTo(source.getPeriodTo());
		destination.setUuid(source.getUuid());
		destination.setState(source.getState());
		updateBlocks(source.getPanelBlocks(), destination.getPanelBlocks());
	}

	private void updateBlocks(List<PanelBlock> source, List<PanelBlock> destination) throws BusinessException
	{
		List<PanelBlock> result = new ArrayList<PanelBlock>();
		for(PanelBlock sourceBlock : source)
			result.add(getUpdatedBlock(sourceBlock, getBlock(sourceBlock.getUuid(), destination)));

		destination.clear();
		destination.addAll(result);
	}


	private PanelBlock getUpdatedBlock(PanelBlock source, PanelBlock destination) throws BusinessException
	{
		destination.setImage(mergeImage(source.getImage(), destination.getImage()));
		destination.setOrder(source.getOrder());
		destination.setProviderFieldAmount(source.getProviderFieldAmount());
		destination.setProviderFieldName(source.getProviderFieldName());
		destination.setProviderName(source.getProviderName());
		destination.setShow(source.getShow());
		destination.setShowName(source.getShowName());
		destination.setSumm(source.getSumm());
		destination.setProviderId(getProviderId(source.getProviderId()));

		return destination;
	}

	private String getSynchKey(long id) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceProviderBase.class).
									add(Restrictions.eq("id", id)).
									setProjection(Projections.property("synchKey"));
		return service.findSingle(criteria, CSA_ADMIN_DB_INSTANCE_NAME);
	}

	private Long getProviderId(Long id) throws BusinessException
	{

		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceProviderBase.class).
									add(Restrictions.eq("synchKey", getSynchKey(id))).
									setProjection(Projections.property("id"));
		return service.findSingle(criteria, null);
	}

	private PanelBlock getBlock(String uuid, List<PanelBlock> destinationBlocks)
	{
		for (PanelBlock block : destinationBlocks)
		{
			if (uuid.equals(block.getUuid()))
				return block;
		}
		PanelBlock block = new PanelBlock();
		block.setUuid(uuid);
		return block;
	}
}
