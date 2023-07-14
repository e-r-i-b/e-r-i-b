package com.rssl.phizic.business.payments.forms.meta;

/**
 * Файл с общими xslt-преобразованиями, импортируемый в другие xslt
 * @author Dorzhinov
 * @ created 12.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class PaymentFormImport
{
	private Long id;
	private String name; //уникальное имя импортируемого файла
	private String body; //XSLT

	public PaymentFormImport()
	{
	}

	public PaymentFormImport(String name, String body)
	{
		this.name = name;
		this.body = body;
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

	public String getBody()
	{
		return body;
	}

	public void setBody(String body)
	{
		this.body = body;
	}
}
