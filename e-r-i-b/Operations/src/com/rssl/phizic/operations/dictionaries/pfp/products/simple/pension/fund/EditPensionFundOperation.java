package com.rssl.phizic.operations.dictionaries.pfp.products.simple.pension.fund;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.fund.PensionFund;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.fund.PensionFundService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityWithImageOperationBase;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author akrenev
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * �������� �������������� ����������� �����
 */

public class EditPensionFundOperation extends EditDictionaryEntityWithImageOperationBase
{
	private static final PensionFundService service = new PensionFundService();
	private PensionFund pensionFund;

	/**
	 * ������������� �������� ����� ���������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize() throws BusinessException, BusinessLogicException
	{
		pensionFund = new PensionFund();
		setImage(pensionFund.getImageId());
	}

	/**
	 * ������������� �������� ������������ ���������
	 * @param id ������������� ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		pensionFund = service.findById(id, getInstanceName());
		if (pensionFund == null)
			throw new ResourceNotFoundBusinessException("���������� ���� c id=" + id + " �� �������� ��� ��������������.", PensionFund.class);

		setImage(pensionFund.getImageId());
	}

	public Object getEntity()
	{
		return pensionFund;
	}

	protected void doSave() throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					pensionFund.setImageId(saveImage());
					service.addOrUpdate(pensionFund, getInstanceName());
					return null;
				}
			}
			);
		}
		catch (ConstraintViolationException cve)
		{
			 throw new BusinessLogicException("���������� ���� � ����� ��������� ��� ���������� � �������. ����������, ������� ������ �������� ����������� �����.", cve);
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
