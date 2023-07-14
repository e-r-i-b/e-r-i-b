package com.rssl.phizicgate.wsgate.services.template.builders;

import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.templates.ActivityInfo;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.attributes.ExtendedAttributeFactory;
import com.rssl.phizic.business.documents.templates.attributes.ExtendedAttributeFactoryImpl;
import com.rssl.phizic.business.documents.templates.impl.TransferTemplateBase;
import com.rssl.phizic.business.documents.templates.impl.activity.DefaultTransferTemplateInformer;
import com.rssl.phizic.business.documents.templates.impl.activity.FailTransferTemplateInformer;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.documents.attribute.Type;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.wsgate.services.template.CorrelationHelper;
import com.rssl.phizicgate.wsgate.services.template.generated.ExtendedAttribute;
import com.rssl.phizicgate.wsgate.services.template.generated.GateTemplate;
import org.apache.commons.collections.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Билдер шаблонов документов (общие поля)
 *
 * @author khudyakov
 * @ created 04.08.14
 * @ $Author$
 * @ $Revision$
 */
abstract class TemplateBuilderBase<T extends TransferTemplateBase> implements TemplateBuilder
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private static final ExtendedAttributeFactory attributeFactory = new ExtendedAttributeFactoryImpl();

	public TemplateDocument build(GateTemplate generated) throws GateException, GateLogicException
	{
		T template = newInstance(FormType.valueOf(generated.getFormType()).getName());
		doBuild(template, generated);
		return template;
	}

	protected abstract void doBuild(T template, GateTemplate generated) throws GateException, GateLogicException;

	protected void setBaseData(T template, GateTemplate generated) throws GateException, GateLogicException
	{
		//проставляем дополнительные аттрибуты шаблона платежа
		Map attributes = generated.getExtendedAttributes();
		if (MapUtils.isNotEmpty(attributes))
		{
			setExtendedAttributes(template, attributes);
		}

		//id
		template.setId(generated.getId());

		//operationUID
		template.setOperationUID(generated.getOperationUID());

		//номер документа
		template.setDocumentNumber(generated.getDocumentNumber());

		//дата создания клиентом
		template.setClientCreationDate(generated.getClientCreationDate());

		//дата подтверждения клиентом
		template.setClientOperationDate(generated.getClientOperationDate());

		//дата дополнительного подтверждения (сотрудником)
		template.setAdditionalOperationDate(generated.getAdditionalOperationDate());

		//информация по сотруднику
		template.setCreatedEmployeeInfo(CorrelationHelper.toGate(generated.getCreatedEmployeeInfo()));

		//информация по сотруднику
		template.setConfirmedEmployeeInfo(CorrelationHelper.toGate(generated.getConfirmedEmployeeInfo()));

		//канал создания клиентом
		template.setClientCreationChannel(generated.getClientCreationChannel());

		//канал подтверждения клиентом
		String clientOperationChannel = generated.getClientOperationChannel();
		if (StringHelper.isNotEmpty(clientOperationChannel))
		{
			template.setClientOperationChannel(clientOperationChannel);
		}

		//канал подтверждения сотрудником
		String additionalOperationChannel = generated.getAdditionalOperationChannel();
		if (StringHelper.isNotEmpty(additionalOperationChannel))
		{
			template.setAdditionalOperationChannel(additionalOperationChannel);
		}

		//дополнительная информация по шаблону
		template.setTemplateInfo(CorrelationHelper.toGate(generated.getTemplateInfo()));

		//externalId
		template.setExternalId(generated.getExternalId());

		//статус
		template.setState(new State(generated.getState().getCode(), generated.getState().getDescription()));

		//владелец
		template.setClientOwner(CorrelationHelper.toGate(generated.getClientOwner()));

		//информация о напоминании
		template.setReminderInfo(CorrelationHelper.toGate(generated.getReminderInfo()));
	}

	protected void setActivityInfo(T template)
	{
		template.setActivityInfo(getActivityInfo(template));
	}

	protected ActivityInfo getActivityInfo(T template)
	{
		try
		{
			return new DefaultTransferTemplateInformer(template).inform();
		}
		catch (Exception e)
		{
			log.error("При расчете активности шаблона id = " + template.getId() + " произошла ошибка.", e);
			return new FailTransferTemplateInformer().inform();
		}
	}

	protected T newInstance(String formName)
	{
		//noinspection unchecked
		return (T) MetadataCache.getBasicMetadata(formName).createTemplate();
	}

	private void setExtendedAttributes(T template, Map attributes)
	{
		Map<String, com.rssl.phizic.gate.documents.attribute.ExtendedAttribute> result = new HashMap<String, com.rssl.phizic.gate.documents.attribute.ExtendedAttribute>(attributes.size());
		for (Object object : attributes.values())
		{
			ExtendedAttribute attribute = (ExtendedAttribute) object;
			result.put(attribute.getName(), attributeFactory.create(attribute.getId(), attribute.getName(), Type.valueOf(attribute.getType()), attribute.getValue()));
		}
		template.setExtendedAttributes(result);
	}
}
