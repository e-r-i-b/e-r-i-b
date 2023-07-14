package com.rssl.phizic.operations.dictionaries.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAlias;
import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAliasField;
import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAliasService;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * User: Moshenko
 * Date: 23.05.2013
 * Time: 16:47:35
 * Операция редактирования полей платежа в разрезе смс алиасов
 */
public class EditSmsAliasFieldOperation extends EditDictionaryEntityOperationBase
{
	private static final ServiceProviderSmsAliasService service = new ServiceProviderSmsAliasService();
	private ServiceProviderSmsAlias alias;
	private List<ServiceProviderSmsAliasField>  fields;


	public void initialize(Long aliasId) throws BusinessException
	{
		alias = service.findById(aliasId, getInstanceName());
		if (alias == null)
			throw new BusinessException("Алиас поставщика с id " + aliasId + " не найден в системе.");
		fields = alias.getSmsAliaseFields();
	}

	public void doSave() throws BusinessException, BusinessLogicException
	{
		service.update(alias, getInstanceName());
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return alias;
	}

	/**
	 * Обновляем информацию о полях.
	 * Признак редактируемости передается только в случаи если он установлен в true на форме, если значение нет то считаем false
	 * @param editableArr признаки редактируемость (вида idПоля)
	 * @param values Значения
	 */
	public void updateFields(String[] editableArr, Map<String, Object> values)
	{
		List<Long> editableIdList = new ArrayList<Long>(fields.size());
		if (editableArr != null)
			for(String editableEl: Arrays.asList(editableArr))
			{
				Long fieldId = Long.valueOf(editableEl);
				editableIdList.add(fieldId);
			}

		for (ServiceProviderSmsAliasField field:fields)
		{
			String fieldName = field.getField().getExternalId();
			field.setValue((String)values.get(fieldName));
			field.setEditable(editableIdList.contains(field.getId()));
		}
	}

}
