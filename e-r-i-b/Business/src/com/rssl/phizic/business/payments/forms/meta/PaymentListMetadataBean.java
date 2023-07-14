package com.rssl.phizic.business.payments.forms.meta;

/**
 * @author Roshka
 * @ created 31.01.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 2789 $
 */
public class PaymentListMetadataBean
{
    private Long id;
    private String name;

    private String definition;
    private String listTransformation;
	private String filterTransformation;

	/**
	 * @return ID
	 */
	public Long getId()
    {
        return id;
    }

	/**
	 * @param id ID (этот метод только для hibernate)
	 */
    public void setId(Long id)
    {
        this.id = id;
    }

	/**
	 * Имя платежа
	 * @return
	 */
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDefinition()
    {
        return definition;
    }

    public void setDefinition(String definition)
    {
        this.definition = definition;
    }

    public String getListTransformation()
    {
        return listTransformation;
    }

    public void setListTransformation(String htmlTarnsformation)
    {
        this.listTransformation = htmlTarnsformation;
    }

	public String getFilterTransformation()
	{
		return filterTransformation;
	}

	public void setFilterTransformation(String filterTransformation)
	{
		this.filterTransformation = filterTransformation;
	}
}
