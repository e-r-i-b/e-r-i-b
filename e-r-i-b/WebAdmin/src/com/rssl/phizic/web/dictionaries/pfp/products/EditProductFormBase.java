package com.rssl.phizic.web.dictionaries.pfp.products;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.EnumFieldValidator;
import com.rssl.common.forms.validators.MultiFieldsValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.claims.forms.validators.RequiredMultiFieldValidator;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParameters;
import com.rssl.phizic.common.types.SegmentCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author akrenev
 * @ created 27.08.2012
 * @ $Author$
 * @ $Revision$
 *
 *  Базовая форма для редактирования продуктов ПФП
 */
public class EditProductFormBase extends PFPImageEditFormBase
{
	public static final String UNIVERSAL_PARAMETER_NAME = "universal";
	public static final String ENABLED_PARAMETER_NAME = "enabled";


	private static final List<SegmentCodeType> segmentCodeTypeList = Arrays.asList(SegmentCodeType.values()); //типы продуктов
	private static final List<Field> baseFormFields = initBaseFormFields();

	private ProductTypeParameters productTypeParameters;

	/**
	 * @return список возможных сегментов клиентов
	 */
	public List<SegmentCodeType> getSegmentCodeTypeList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return segmentCodeTypeList;
	}

	/**
	 * @return список полей для задания доходности
	 */
	protected static List<Field> getBaseFormFields()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return baseFormFields;
	}

	/**
	 * @return cущность параметров типов продуктов ПФП
	 */
	public ProductTypeParameters getProductTypeParameters()
	{
		return productTypeParameters;
	}

	/**
	 * @param productTypeParameters cущность параметров типов продуктов ПФП
	 */
	public void setProductTypeParameters(ProductTypeParameters productTypeParameters)
	{
		this.productTypeParameters = productTypeParameters;
	}

	@SuppressWarnings({"ReuseOfLocalVariable", "OverlyLongMethod"})
	protected static List<Field> initBaseFormFields()
	{
		List<Field> resultList = new ArrayList<Field>();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(UNIVERSAL_PARAMETER_NAME);
		fieldBuilder.setDescription("Универсальный продукт");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());		
		resultList.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(ENABLED_PARAMETER_NAME);
		fieldBuilder.setDescription("Продукт доступен клиентам");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		resultList.add(fieldBuilder.build());

		return resultList;
	}
}
