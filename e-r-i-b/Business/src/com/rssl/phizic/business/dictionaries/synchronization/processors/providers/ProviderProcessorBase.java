package com.rssl.phizic.business.dictionaries.synchronization.processors.providers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.SumRestrictions;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.synchronization.SkipEntitySynchronizationException;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.business.dictionaries.synchronization.processors.aggr.PostAggregationProcessorBase;
import com.rssl.phizic.business.dictionaries.synchronization.processors.departments.DepartmentProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.region.RegionProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider.GroupRiskProcessor;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.fields.FieldValidationRuleImpl;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.common.types.RequisiteType;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRule;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.*;

/**
 * @author akrenev
 * @ created 29.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Базовый процессор записей поставщиков
 */

public abstract class ProviderProcessorBase<P extends ServiceProviderBase> extends PostAggregationProcessorBase<P>
{
	public static final String MULTI_BLOCK_RECORD_ID_FIELD_NAME = "uuid";

	private static final String ID_FIELD_NAME = "id";

	protected abstract P getNewProvider();

	@Override
	protected final P getNewEntity()
	{
		P provider = getNewProvider();
		provider.setRegions(new HashSet<Region>());
		provider.setBank(new ResidentBank());
		provider.setRestrictions(new SumRestrictions());
		return provider;
	}

	private <T> T find(Class<T> entityClass, String identityPropertyName, Object identityPropertyValue, String instanceName) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(entityClass).add(Expression.eq(identityPropertyName, identityPropertyValue));
		return simpleService.<T>findSingle(criteria, instanceName);
	}

	private <T> T findLocal(Class<T> entityClass, String identityPropertyName, String identityPropertyValue) throws BusinessException
	{
		return find(entityClass, identityPropertyName, identityPropertyValue, null);
	}

	private <T> T findGlobal(Class<T> entityClass, String identityPropertyName, Object identityPropertyValue) throws BusinessException
	{
		return find(entityClass, identityPropertyName, identityPropertyValue, CSA_ADMIN_DB_INSTANCE_NAME);
	}

	@Override
	protected P getEntity(String uuid) throws BusinessException
	{
		return findLocal(getEntityClass(), MULTI_BLOCK_RECORD_ID_FIELD_NAME, uuid);
	}

	private FieldValidationRule getUpdatedFieldValidationRule(FieldValidationRule source) throws BusinessException
	{
		if (!(source instanceof FieldValidationRuleImpl))
		    throw new BusinessException("Неизвестный тип FieldValidationRule");

		return new FieldValidationRuleImpl((FieldValidationRuleImpl) source);
	}

	private void updateFieldValidationRules(List<FieldValidationRule> source, List<FieldValidationRule> destination) throws BusinessException
	{
		destination.clear();

		if (CollectionUtils.isEmpty(source))
			return;

		for (FieldValidationRule sourceFieldValidationRule : source)
			destination.add(getUpdatedFieldValidationRule(sourceFieldValidationRule));
	}

	@SuppressWarnings("OverlyLongMethod")
	private FieldDescription getUpdatedField(FieldDescription source, FieldDescription destination) throws BusinessException
	{
		destination.setExternalId(source.getExternalId());
		destination.setName(source.getName());
		destination.setDescription(source.getDescription());
		destination.setHint(source.getHint());
		destination.setPopupHint(source.getPopupHint());
		destination.setType(source.getType());
		destination.setMaxLength(source.getMaxLength());
		destination.setMinLength(source.getMinLength());
		destination.setRequired(source.isRequired());
		destination.setEditable(source.isEditable());
		destination.setVisible(source.isVisible());
		destination.setMainSum(source.isMainSum());
		destination.setKey(source.isKey());
		destination.setDefaultValue(source.getDefaultValue());

		destination.getRequisiteTypes().clear();
		if (CollectionUtils.isNotEmpty(source.getRequisiteTypes()))
			destination.getRequisiteTypes().addAll(source.getRequisiteTypes());

		destination.getListValues().clear();
		if (CollectionUtils.isNotEmpty(source.getListValues()))
			destination.getListValues().addAll(source.getListValues());

		updateFieldValidationRules(source.getFieldValidationRules(), destination.getFieldValidationRules());
		destination.setSaveInTemplate(source.isSaveInTemplate());
		destination.setRequiredForConformation(source.isRequiredForConformation());
		destination.setRequiredForBill(source.isRequiredForBill());
		destination.setNumberPrecision(source.getNumberPrecision());
		destination.setHideInConfirmation(source.isHideInConfirmation());
		destination.setValue(source.getValue());
		destination.setError(source.getError());
		destination.setGroupName(source.getGroupName());
		destination.setGraphicTemplateName(source.getGraphicTemplateName());
		destination.setExtendedDescId(source.getExtendedDescId());
		destination.setMask(source.getMask());
		destination.setBusinessSubType(source.getBusinessSubType());
		return destination;
	}

	private FieldDescription getField(String uuid, List<FieldDescription> destinationFieldDescriptions)
	{
		for (FieldDescription destinationColumn : destinationFieldDescriptions)
		{
			if (uuid.equals(destinationColumn.getUuid()))
				return destinationColumn;
		}
		FieldDescription field = new FieldDescription();
		field.setUuid(uuid);
		field.setRequisiteTypes(new ArrayList<RequisiteType>());
		field.setListValues(new ArrayList<String>());
		field.setFieldValidationRules(new ArrayList<FieldValidationRule>());
		return field;
	}

	private void updateFields(List<FieldDescription> sourceFieldDescriptions, List<FieldDescription> destinationFieldDescriptions) throws BusinessException
	{
		List<FieldDescription> resultColumns = new ArrayList<FieldDescription>();

		for (FieldDescription sourceField : sourceFieldDescriptions)
			resultColumns.add(getUpdatedField(sourceField, getField(sourceField.getUuid(), destinationFieldDescriptions)));

		destinationFieldDescriptions.clear();
		destinationFieldDescriptions.addAll(resultColumns);
	}

	private GroupRisk getGroupRisk(GroupRisk sourceGroupRisk) throws BusinessException
	{
		return getLocalVersionByGlobal(sourceGroupRisk, GroupRiskProcessor.MULTI_BLOCK_RECORD_ID_FIELD_NAME);
	}

	private List<Region> getRegions(Set<Region> source) throws BusinessException
	{
		return getLocalVersionByGlobal(source, RegionProcessor.MULTI_BLOCK_RECORD_ID_FIELD_NAME);
	}

	private Long getDepartmentId(Long sourceId) throws BusinessException
	{
		if (sourceId == null)
			return null;

		Department source = findGlobal(Department.class, ID_FIELD_NAME, sourceId);
		if (source == null)
			throw new SkipEntitySynchronizationException("Подразделение с идентификатором " + sourceId +" не найдено в БД " + CSA_ADMIN_DB_INSTANCE_NAME);

		Department department = findLocal(Department.class, DepartmentProcessor.MULTI_BLOCK_RECORD_ID_FIELD_NAME, (String) source.getSynchKey());
		if (department == null)
			throw new BusinessException("Ошибка синхронизации справочника " + getEntityClass().getSimpleName() + ": справочник " + Department.class.getSimpleName() + " не синхронизирован!");

		return department.getId();
	}

	private ResidentBank getUpdatedBank(ResidentBank source, ResidentBank destination)
	{
		if (source == null)
			return null;

		ResidentBank updatedBank = destination;
		if (updatedBank == null)
			updatedBank = new ResidentBank();

		updatedBank.setBIC(source.getBIC());
		updatedBank.setAccount(source.getAccount());
		updatedBank.setName(source.getName());
		return updatedBank;
	}

	@Override
	@SuppressWarnings("OverlyLongMethod")
	protected void update(P source, P destination) throws BusinessException
	{
		destination.setUuid(source.getUuid());
		destination.setSynchKey(source.getSynchKey());
		destination.setCode(source.getCode());
		destination.setName(source.getName());
		destination.setDescription(source.getDescription());
		destination.setTipOfProvider(source.getTipOfProvider());
		destination.setINN(source.getINN());
		destination.setKPP(source.getKPP());
		destination.setBIC(source.getBIC());
		destination.setAccount(source.getAccount());
		destination.setBankName(source.getBankName());
		destination.setCorrAccount(source.getCorrAccount());
		destination.setNSICode(source.getNSICode());
		destination.setDepartmentId(getDepartmentId(source.getDepartmentId()));
		destination.setTransitAccount(source.getTransitAccount());
		destination.getRegions().clear();
		destination.getRegions().addAll(getRegions(source.getRegions()));

		destination.setBank(getUpdatedBank(source.getBank(), destination.getBank()));
		destination.setState(source.getState());
		destination.setBankDetails(source.isBankDetails());
		destination.setPhoneNumber(source.getPhoneNumber());
		destination.setCreationDate(source.getCreationDate());
		updateFields(source.getFieldDescriptions(), destination.getFieldDescriptions());
		destination.setGroupRisk(getGroupRisk(source.getGroupRisk()));
		destination.setStandartTemplate(source.isStandartTemplate());
		destination.setTemplateFormat(source.getTemplateFormat());
		destination.setTemplateExample(source.getTemplateFormat());
		destination.setCommissionMessage(source.getCommissionMessage());
		destination.setImageId(mergeImage(source.getImageId(), destination.getImageId()));
		destination.setImageHelpId(mergeImage(source.getImageHelpId(), destination.getImageHelpId()));

		SumRestrictions sourceRestrictions = source.getRestrictions();
		if (destination.getRestrictions() == null)
			destination.setRestrictions(new SumRestrictions());

		SumRestrictions destinationRestrictions = destination.getRestrictions();
		destinationRestrictions.setMinimumSum(sourceRestrictions == null ? null : sourceRestrictions.getMinimumSum());
		destinationRestrictions.setMaximumSum(sourceRestrictions == null ? null : sourceRestrictions.getMaximumSum());

		destination.setBarSupported(source.isBarSupported());
		destination.setOfflineAvailable(source.isOfflineAvailable());
		destination.setAvailablePaymentsForInternetBank(source.isAvailablePaymentsForInternetBank());
		destination.setAvailablePaymentsForMApi(source.isAvailablePaymentsForMApi());
		destination.setAvailablePaymentsForAtmApi(source.isAvailablePaymentsForAtmApi());
		destination.setAvailablePaymentsForSocialApi(source.isAvailablePaymentsForSocialApi());
		destination.setAvailablePaymentsForErmb(source.isAvailablePaymentsForErmb());
		destination.setVisiblePaymentsForInternetBank(source.isVisiblePaymentsForInternetBank());
		destination.setVisiblePaymentsForMApi(source.isVisiblePaymentsForMApi());
		destination.setVisiblePaymentsForAtmApi(source.isVisiblePaymentsForAtmApi());
		destination.setVisiblePaymentsForSocialApi(source.isVisiblePaymentsForSocialApi());
		destination.setCreditCardSupported(source.isCreditCardSupported());
		destination.setCreditCardSupportedTemp(source.isCreditCardSupportedTemp());
		destination.setEditPaymentSupported(source.isEditPaymentSupported());
		destination.setSubType(source.getSubType());
		destination.setSortPriority(source.getSortPriority());
	}

	@Override
	protected void doRemove(P localEntity) throws BusinessException, BusinessLogicException
	{
		removeImage(localEntity.getImageId());
		removeImage(localEntity.getImageHelpId());
		super.doRemove(localEntity);
	}
}
