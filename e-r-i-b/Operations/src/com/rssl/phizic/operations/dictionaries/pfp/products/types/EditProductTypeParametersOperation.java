package com.rssl.phizic.operations.dictionaries.pfp.products.types;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParametersService;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityWithImageOperationBase;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author akrenev
 * @ created 25.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * �������� �������������� ���������� ����������� ����� ��������� � ���
 */

public class EditProductTypeParametersOperation extends EditDictionaryEntityWithImageOperationBase
{
	public static final String PRODUCT_TYPE_IMAGE = "ProductTypeImage";

	private static final ProductTypeParametersService service = new ProductTypeParametersService();
	private ProductTypeParameters parameters;

	/**
	 * ������������� ��������
	 * @param id �������������
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		parameters = service.findById(id, getInstanceName());
		if (parameters == null)
			throw new ResourceNotFoundBusinessException("��� �������� c id=" + id + " �� �������� ��� ��������������.", ProductTypeParameters.class);

		addImage(PRODUCT_TYPE_IMAGE, parameters.getImageId());
	}

	protected void doSave() throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					//��������� ���� �������
					service.updateRiskProfiles(parameters, getInstanceName());
					//��������� ��������
					Image image = saveImage(PRODUCT_TYPE_IMAGE);
					parameters.setImageId(image != null ? image.getId() : null);
					service.update(parameters, getInstanceName());
					return null;
				}
			});
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (ConstraintViolationException cve)
		{
			throw new BusinessLogicException("���������� ��������� ��� ��������.", cve);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public ProductTypeParameters getEntity()
	{
		return parameters;
	}
}
