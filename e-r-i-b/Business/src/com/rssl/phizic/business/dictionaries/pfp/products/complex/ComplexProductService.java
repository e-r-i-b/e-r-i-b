package com.rssl.phizic.business.dictionaries.pfp.products.complex;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductBase;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceProduct;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author akrenev
 * @ created 24.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ComplexProductService
{
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();

	/**
	 * Получает комплексный страховой продукт и фильтрует привязанные страховые продукты по возрасту
	 * @param id идентификатор комплексного страхового продукта
	 * @param age возраст клиента
	 * @return комплексный страховой продукт
	 * @throws BusinessException
	 */
	public ComplexInsuranceProduct getByIdFilterByAge(final Long id, final Long age) throws BusinessException
	{
		ComplexInsuranceProduct product = service.findById(ComplexInsuranceProduct.class, id);
		CollectionUtils.filter(product.getInsuranceProducts(), new Predicate()
		{
			public boolean evaluate(Object object)
			{
				InsuranceProduct product = (InsuranceProduct)object;
				return (product.getMaxAge() == null || product.getMaxAge() >= age) &&
					   (product.getMinAge() == null || product.getMinAge() <= age );
			}
		});
		return product;
	}

	/**
	 * Провряем, входит ли продукт в состав комплексного
	 * @param product   - продукт
	 * @param instance имя инстанса модели БД
	 * @return true - входит/false - не входит
	 * @throws BusinessException
	 */
	public Boolean containsInComplexProduct(final ProductBase product, String instance) throws BusinessException
	{
		try
		 {
		 	 return  HibernateExecutor.getInstance(instance).execute( new HibernateAction<Boolean>()
			 {
				 public Boolean run(Session session) throws Exception
				 {
					 Query query = session.getNamedQuery(product.getClass().getName() + ".containProduct");
					 query.setLong("product_id", product.getId());
					 return (Long)query.uniqueResult() > 0;
				 }
			 });
		 }
		 catch(Exception e)
		 {
		     throw new BusinessException(e);
		 }		
	}
}
