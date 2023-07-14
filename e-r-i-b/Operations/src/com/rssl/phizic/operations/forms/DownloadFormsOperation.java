package com.rssl.phizic.operations.forms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.payments.forms.PaymentFormService;
import com.rssl.phizic.business.payments.forms.meta.MetadataBean;
import com.rssl.phizic.business.payments.forms.meta.PaymentFormTransformation;
import com.rssl.phizic.business.payments.forms.meta.PaymentListMetadataBean;
import com.rssl.phizic.business.payments.forms.meta.TransformType;
import com.rssl.phizic.operations.OperationBase;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Загрузка файлов формы платежа с сервера 
 * @author Kidyaev
 * @ created 14.02.2006
 * @ $Author: dorzhinov $
 * @ $Revision: 41461 $
 */

public class DownloadFormsOperation extends OperationBase
{
	private static PaymentFormService formService = new PaymentFormService();

	private String                  paymentFormName;
	private MetadataBean            metadata;
	private Map<TransformType, String> transformMap;
	private PaymentListMetadataBean listMetadataBean;

	/**
	 * Инициализация операции
	 * @param paymentFormName имя формы платежа
	 * @throws BusinessException
	 */
	public void initialize(String paymentFormName) throws BusinessException
	{
		this.paymentFormName = paymentFormName;
		readForm();
	}

	/**
	 * @return имя формы платежа
	 */
	public String getPaymentFormName()
	{
		return paymentFormName;
	}

	/**
	 * @return описание
	 */
	public String getDescription()
	{
		return metadata.getDescription();
	}

	/**
	 * @return PFD.XML
	 */
	public String getForm()
	{
		return metadata.getDefinition();
	}

	/**
	 * @return XML.XSLT
	 */
	public String getXmlForm()
	{
		return metadata.getXmlTransformation();
	}

	/**
	 * @return HTML.XSLT
	 */
	public String getHtmlForm()
	{
		return transformMap.get(TransformType.html);
	}

	/**
	 * @return LIST PFD
	 */
	public String getListForm()
	{
		return listMetadataBean.getDefinition();
	}

	/**
	 * @return LIST PFD
	 */
	public String getHtmlListFilterForm()
	{
		return listMetadataBean.getFilterTransformation();
	}

	/**
	 * @return LIST PFD
	 */
	public String getHtmlListForm()
	{
		return listMetadataBean.getListTransformation();
	}

	/**
	 * Загружает из БД формы платежа и списка.
	 * @throws com.rssl.phizic.business.BusinessException
	 *
	 * Перед вызовом этого метода необходимо установить имя формы платежа, т.е. paymentFormName
	 */
	private void readForm() throws BusinessException
	{
		if ( paymentFormName == null || paymentFormName.length() == 0 )
			throw new BusinessException("Не установлено имя формы платежа");

		try
		{
			metadata     = formService.findByName(paymentFormName);
			transformMap = mapTransformations(formService.findTransformationsByFormName(paymentFormName));
			listMetadataBean = formService.findListFormData(paymentFormName);
		}
		catch ( BusinessException ex )
		{
			metadata        = null;
			transformMap    = null;
			listMetadataBean = null;
			paymentFormName = null;

			throw ex;
		}

		if ( metadata == null || listMetadataBean == null )
			 throw new BusinessException("Форма платежа '" + paymentFormName + "' не найдена");
	}

    /**
	 * Перекладывает преобразования из списка в карту с ключом = TranformType
	 * @param list
	 * @return
	 */
	private Map<TransformType, String> mapTransformations(List<PaymentFormTransformation> list)
	{
		Map<TransformType, String> map = new EnumMap<TransformType, String>(TransformType.class);
		for (PaymentFormTransformation pft : list)
			map.put(pft.getType(), pft.getTransformation());
		return map;
	}
}
