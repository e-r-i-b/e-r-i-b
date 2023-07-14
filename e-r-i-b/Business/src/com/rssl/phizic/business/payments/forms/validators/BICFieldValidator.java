package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

/**
 * User: Zhuravleva
 * Date: 13.06.2006
 * Time: 14:01:41
 */
public class BICFieldValidator  extends FieldValidatorBase
{
	public BICFieldValidator()
	{
		this.setMessage("БИК банка не найден в справочнике банков. Задайте корректное значение.");
	}

	public BICFieldValidator(String message)
	{
		super.setMessage(message);
	}

    public boolean validate(final String value) throws TemporalDocumentException
    {
       if(isValueEmpty(value))
            return true;

	    List<Object> banks;
	    try
	    {
		    banks = HibernateExecutor.getInstance().execute(new HibernateAction<List<Object>>()
		    {
			    public List<Object> run(Session session) throws Exception
			    {
				    Query query = session.getNamedQuery("com.rssl.phizic.business.getBankByBIC");
				    query.setParameter("BIC", value);
				    query.setParameter("CUR_DATE", Calendar.getInstance());
				    //noinspection unchecked
				    return query.list();
			    }
		    });
	    }
	    catch (Exception e)
	    {
		    return false;
	    }

	    return banks != null && banks.size() > 0;
    }
}
