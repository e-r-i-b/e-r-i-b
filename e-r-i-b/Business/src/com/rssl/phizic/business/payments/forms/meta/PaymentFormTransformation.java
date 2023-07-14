package com.rssl.phizic.business.payments.forms.meta;

/**
 * xslt-преобразование платежной формы
 * Преобразование знает все о MetadataBean-е, но MetadataBean ничего не знает о преобразовании.
 * @author Dorzhinov
 * @ created 31.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class PaymentFormTransformation
{
	private Long id;
	private MetadataBean form; //форма
	private TransformType type; //тип преобразования
	private String transformation; //преобразование

	public PaymentFormTransformation()
	{
	}

	public PaymentFormTransformation(MetadataBean form, TransformType type, String transformation)
	{
		this.form = form;
		this.type = type;
		this.transformation = transformation;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public MetadataBean getForm()
	{
		return form;
	}

	public void setForm(MetadataBean form)
	{
		this.form = form;
	}

	public TransformType getType()
	{
		return type;
	}

	public void setType(TransformType type)
	{
		this.type = type;
	}

	public String getTransformation()
	{
		return transformation;
	}

	public void setTransformation(String transformation)
	{
		this.transformation = transformation;
	}

	/**
	 * Ключевые поля: form.name + type
	 * @param obj
	 * @return
	 */
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;

		if (!(obj instanceof PaymentFormTransformation))
			return false;

		PaymentFormTransformation o = (PaymentFormTransformation) obj;

		if (form == null ^ o.form == null)
			return false;

		if (form != null && o.form != null)
			if (!(form.getName() == null ? o.form.getName() == null : form.getName().equals(o.form.getName())))
				return false;

		if (type != o.type)
			return false;

		return true;
	}

	public int hashCode()
	{
		int result = 17;
		result = 31 * result + (form == null ? 0 : (form.getName() == null ? 0 : form.getName().hashCode()));
		result = 31 * result + (type == null ? 0 : type.hashCode());
		return result;
	}
}
