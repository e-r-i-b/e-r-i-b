package com.rssl.phizic.business.dictionaries.synchronization.processors.providers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAlias;
import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAliasField;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 03.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей СМС псевдонимов постовщика услуг
 */

public class ServiceProviderSmsAliasProcessor extends ProcessorBase<ServiceProviderSmsAlias>
{
	@Override
	protected Class<ServiceProviderSmsAlias> getEntityClass()
	{
		return ServiceProviderSmsAlias.class;
	}

	@Override
	protected ServiceProviderSmsAlias getNewEntity()
	{
		ServiceProviderSmsAlias alias = new ServiceProviderSmsAlias();
		alias.setSmsAliaseFields(new ArrayList<ServiceProviderSmsAliasField>());
		return alias;
	}

	@Override
	protected ServiceProviderSmsAlias getEntity(String uuid) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass()).add(Expression.eq("uuid", uuid));
		return simpleService.findSingle(criteria);
	}

	@Override
	protected void update(ServiceProviderSmsAlias source, ServiceProviderSmsAlias destination) throws BusinessException
	{
		destination.setUuid(source.getUuid());
		destination.setName(source.getName());
		destination.setServiceProvider(getLocalVersionByGlobal(source.getServiceProvider(), ProviderProcessorBase.MULTI_BLOCK_RECORD_ID_FIELD_NAME));
		updateFields(source.getSmsAliaseFields(), destination.getSmsAliaseFields());
	}

	private ServiceProviderSmsAliasField getField(ServiceProviderSmsAliasField source, List<ServiceProviderSmsAliasField> destination)
	{
		for (ServiceProviderSmsAliasField field : destination)
			if (field.getField().getUuid().equals(source.getField().getUuid()))
				return field;

		return new ServiceProviderSmsAliasField();
	}

	private ServiceProviderSmsAliasField getUpdatedField(ServiceProviderSmsAliasField source, ServiceProviderSmsAliasField destination) throws BusinessException
	{
		destination.setValue(source.getValue());
		destination.setEditable(source.isEditable());
		destination.setField(getLocalVersionByGlobal(source.getField(), "uuid"));
		return destination;
	}

	private void updateFields(List<ServiceProviderSmsAliasField> source, List<ServiceProviderSmsAliasField> destination) throws BusinessException
	{
		List<ServiceProviderSmsAliasField> resultColumns = new ArrayList<ServiceProviderSmsAliasField>();

		for (ServiceProviderSmsAliasField sourceField : source)
			resultColumns.add(getUpdatedField(sourceField, getField(sourceField, destination)));

		destination.clear();
		destination.addAll(resultColumns);
	}
}
