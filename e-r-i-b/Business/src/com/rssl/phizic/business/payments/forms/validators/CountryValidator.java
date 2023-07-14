package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.gate.dictionaries.Country;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.hibernate.Session;
import org.hibernate.Query;

/**
 * Проверка наличия страны в справочнике.
 * User: novikov_a
 * Date: 08.08.2009
 * Time: 15:54:47
 */
public class CountryValidator  extends FieldValidatorBase
{
    public CountryValidator()
	{
		this.setMessage("Страна не найдена в справочнике. Задайте корректное значение.");
	}

	public CountryValidator(String message)
	{
		super.setMessage(message);
	}

    public boolean validate(final String value) throws TemporalDocumentException
    {
       if(isValueEmpty(value))
            return true;

	    Country country = null;

	    try
		{
			country = HibernateExecutor.getInstance().execute(new HibernateAction<Country>()
			{
				public Country run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.getCountryByIntCode");
					query.setParameter("intCode", value);
					return (Country) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
		    return false;
		}

	    return country != null;
    }
}
