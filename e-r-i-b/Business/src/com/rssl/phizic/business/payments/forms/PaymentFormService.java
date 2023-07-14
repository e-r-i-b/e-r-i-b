package com.rssl.phizic.business.payments.forms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.payments.forms.meta.*;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 31.10.2005
 * Time: 19:34:03
 */
public class PaymentFormService
{
    //*********************************************************************//
    //***************************  CLASS MEMBERS  *************************//
    //*********************************************************************//

    private static final SimpleService simpleService = new SimpleService();

    //*********************************************************************//
    //**************************  INSTANCE MEMBERS  ***********************//
    //*********************************************************************//

	/**
	 * Поиск Метаданных платежа по имени формы
	 * @param formName имя формы платежа
	 * @return метаданные
	 */
    public MetadataBean findByName(String formName) throws BusinessException
    {
        MetadataBean metadata = new MetadataBean();
        metadata.setName(formName);
        return findBySample(metadata);
    }

	/**
	 * Поиск метаданных списка по имени формы
	 * @param formName имя формы платежа
	 * @return метаданные списка
	 */
	public ListFormImpl findListFormByName(final String formName) throws BusinessException
    {

        try
        {
            return HibernateExecutor.getInstance().execute(new HibernateAction<ListFormImpl>()
            {
                public ListFormImpl run(Session session) throws Exception
                {
	                PaymentListMetadataBean paymentListMetadataBean = findListFormData(formName);

	                if(paymentListMetadataBean == null)
	                    return null;	                    

	                PaymentListFormParser parser = new PaymentListFormParser();

	                ListFormImpl listForm = new ListFormImpl();
	                parser.parse(listForm, new InputSource(new StringReader(paymentListMetadataBean.getDefinition())));
	                listForm.setListTransformation(paymentListMetadataBean.getListTransformation());
	                listForm.setFilterTransformation(paymentListMetadataBean.getFilterTransformation());

	                return listForm;
                }
            });
        }
        catch(BusinessException e)
        {
	        throw e;
        }
        catch (Exception e)
        {
	        throw new BusinessException(e);
        }
    }

	/**
	 *
	 * @param formName имя формы платежа
	 * @return ???
	 * @throws BusinessException
	 */
	public PaymentListMetadataBean findListFormData(final String formName) throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<PaymentListMetadataBean>()
		    {
		        public PaymentListMetadataBean run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery("GetPaymentListFormByName");
			        query.setParameter("name", formName);

			        return (PaymentListMetadataBean) query.uniqueResult();
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	private MetadataBean findBySample(MetadataBean metadata) throws BusinessException
	{
	    return simpleService.executeQuerySingle("GetPaymentFormByName", metadata);
	}

    /**
     * @return Список всех форм
     */
    public List<MetadataBean> getAllForms() throws BusinessException
    {
        return simpleService.getAll(MetadataBean.class);
    }

	/**
	 * @return список форм (id, имя формы, описание).
	 * @throws BusinessException
	 */
	public List<MetadataBean> getAllFormsLight() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<MetadataBean>>()
			    {
			        public List<MetadataBean> run(Session session) throws Exception
			        {
				        Query query = session.getNamedQuery("GetPaymentFormLight");
				        List list = query.list();
				        List<MetadataBean> forms = new ArrayList<MetadataBean>(list.size());
				        for (int i = 0; i < list.size(); i++)
				        {
					        MetadataBean bean = new MetadataBean();
					        Object[] arr = (Object[]) list.get(i);
					        bean.setId((Long) arr[0]);
					        bean.setName((String) arr[1]);
					        bean.setDescription((String) arr[2]);
					        forms.add(bean);
				        }
				        return forms;
			        }
			    });
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public MetadataBean update(MetadataBean form) throws BusinessException
	{
		return simpleService.update(form);
	}

	/**
	 * Получение списка преобразований по имени формы
	 * @param formName имя формы
	 * @return список преобразований
	 * @throws BusinessException
	 */
	public List<PaymentFormTransformation> findTransformationsByFormName(final String formName) throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<List<PaymentFormTransformation>>()
		    {
		        @SuppressWarnings({"unchecked"})
		        public List<PaymentFormTransformation> run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery("com.rssl.phizic.business.payments.forms.meta.PaymentFormTransformation.getTransformationsByFormName");
			        query.setParameter("name", formName);
			        return query.list();
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * Ищет файл импорта по его имени
	 * @param name имя файла импорта
	 * @return файл импорта
	 * @throws BusinessException
	 */
	public PaymentFormImport findImportByName(final String name) throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<PaymentFormImport>()
		    {
		        public PaymentFormImport run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery("com.rssl.phizic.business.payments.forms.meta.PaymentFormImport.findByName");
			        query.setParameter("name", name);
			        return (PaymentFormImport) query.uniqueResult();
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}
}

