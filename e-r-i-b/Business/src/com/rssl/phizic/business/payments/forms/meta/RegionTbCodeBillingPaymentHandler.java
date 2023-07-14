package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Хелпер для платежей по штрих-коду. Добавление дополнитеьных полей перед отправкой запроса в шину
 * @ author: Gololobov
 * @ created: 27.06.14
 * @ $Author$
 * @ $Revision$
 */
public class RegionTbCodeBillingPaymentHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof JurPayment))
			return;

		JurPayment payment = (JurPayment) document;
		Long providerId = payment.getReceiverInternalId();
		try
		{
			if (providerId != null)
			{
				ServiceProviderShort barCodeProvider = RegionHelper.getBarCodeProvider();
				//Если поставщик по штрих-коду
				if (barCodeProvider != null && providerId.equals(barCodeProvider.getId()))
				{
					List<Field> newExtendedFields = new ArrayList<Field>();
					newExtendedFields.addAll(payment.getExtendedFields());
					//Добавление дополнительного поля "Кода ТБ региона" для БС (его наличие означает что платеж создан по штрих-коду).
					CommonField regionTBCodeField = getRegionTBCodeField();
					if (regionTBCodeField != null && !newExtendedFields.contains(regionTBCodeField))
						newExtendedFields.add(regionTBCodeField);

					payment.setExtendedFields(newExtendedFields);
				}
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Возвращает дополнительное поле кода ТБ региона для платежей по штрих-коду
	 * @return
	 * @throws BusinessException
	 */
	private CommonField getRegionTBCodeField() throws BusinessException
	{
		String regionTBCode = StringHelper.removeLeadingZeros(getRegionTBCode(RegionHelper.getRegionApi()));

		if (StringHelper.isEmpty(regionTBCode))
			return null;

		CommonField tbCodeField = new CommonField();
		tbCodeField.setExternalId(ConfigFactory.getConfig(PaymentsConfig.class).getRegionTBCode());
		tbCodeField.setName("Код ТБ региона");
		tbCodeField.setDescription(tbCodeField.getName());
		tbCodeField.setHint(tbCodeField.getName());
		tbCodeField.setType(FieldDataType.string);
		tbCodeField.setMaxLength(4L);
		tbCodeField.setMinLength(1L);
		tbCodeField.setRequired(false);
		tbCodeField.setEditable(false);
		tbCodeField.setVisible(false);
		tbCodeField.setMainSum(false);
		tbCodeField.setKey(false);
		tbCodeField.setRequiredForConformation(false);
		tbCodeField.setSaveInTemplate(false);
		tbCodeField.setRequiredForBill(false);
		tbCodeField.setHideInConfirmation(true);
		tbCodeField.setValue(regionTBCode);

		return tbCodeField;
	}

	/**
	 * Возвращает код ТБ региона с учетом вложенности
	 * @param region
	 * @return
	 */
	public String getRegionTBCode(Region region)
	{
		if (region == null)
			return null;

		return StringHelper.isEmpty(region.getCodeTB()) ? getRegionTBCode(region.getParent()) : region.getCodeTB();
	}
}
