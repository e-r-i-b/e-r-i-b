package com.rssl.phizic.web.configure.basketidents;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.BasketIdentifierTypeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.dictionaries.basketident.AttributeForBasketIdentType;
import com.rssl.phizic.business.dictionaries.basketident.BasketIndetifierType;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderForFieldFormulas;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.web.common.FilterActionForm;

import java.util.List;
import java.util.Map;

/**
 * Форма редактирования идентфикаторов корзины
 *
 * @author bogdanov
 * @ created 10.11.14
 * @ $Author$
 * @ $Revision$
 */

public class EditBasketIdentTypesForm extends FilterActionForm
{
	public static final String SYSTEM_ID_FIELD = "systemId";
	private Long id;
	private Long providerId;
	private boolean add = false;
	private boolean remove = false;

	private String name;
	private String type;
	private List<Pair<AttributeForBasketIdentType, String>> attributes;
	private Map<Long, List<Object[]>>  formulas;
	private List<ServiceProviderForFieldFormulas> serviceProviders;
	private BasketIndetifierType basketIndetifierType;

	public boolean isAdd()
	{
		return add;
	}

	public void setAdd(boolean add)
	{
		this.add = add;
	}

	public List<Pair<AttributeForBasketIdentType, String>> getAttributes()
	{
		return attributes;
	}

	public void setAttributes(List<Pair<AttributeForBasketIdentType, String>> attributes)
	{
		this.attributes = attributes;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public boolean isRemove()
	{
		return remove;
	}

	public void setRemove(boolean remove)
	{
		this.remove = remove;
	}

	public Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName(SYSTEM_ID_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Системный ID");
		fb.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator(".{0,20}", "Значение поля \"Системный ID\" не должно превышать 20 символов"),
				new BasketIdentifierTypeValidator()
		);
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

	/**
	 * @return связки идентфикатора с полями ПУ
	 */
	public Map<Long, List<Object[]>>  getFormulas()
	{
		return formulas;
	}

	/**
	 * @param formulas связки идентфикатора с полями ПУ
	 */
	public void setFormulas(Map<Long, List<Object[]>>  formulas)
	{
		this.formulas = formulas;
	}

	public List<ServiceProviderForFieldFormulas> getServiceProviders()
	{
		return serviceProviders;
	}

	public void setServiceProviders(List<ServiceProviderForFieldFormulas> serviceProviders)
	{
		this.serviceProviders = serviceProviders;
	}

	public Long getProviderId()
	{
		return providerId;
	}

	public void setProviderId(Long providerId)
	{
		this.providerId = providerId;
	}

	/**
	 * @return Тип для отображения инентификатора корзины
	 */
	public BasketIndetifierType getBasketIndetifierType()
	{
		return basketIndetifierType;
	}

	/**
	 * @param basketIndetifierType Тип для отображения инентификатора корзины
	 */
	public void setBasketIndetifierType(BasketIndetifierType basketIndetifierType)
	{
		this.basketIndetifierType = basketIndetifierType;
	}
}
