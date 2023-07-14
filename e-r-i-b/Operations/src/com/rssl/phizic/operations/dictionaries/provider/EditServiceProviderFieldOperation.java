package com.rssl.phizic.operations.dictionaries.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.business.operations.restrictions.ServiceProvidersRestriction;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;
import org.hibernate.Session;

/**
 * @author krenev
 * @ created 05.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditServiceProviderFieldOperation extends EditDictionaryEntityOperationBase<FieldDescription, ServiceProvidersRestriction>
{
	private static final SimpleService simpleService = new SimpleService();
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private FieldDescription description;
	private BillingServiceProviderBase provider;
	private int countKeyFieldDescription;

	private boolean isNew;
	private static String MESSAGE_ACTIVE = "���������� ��������� ��������� �� ��������� ����������";
	private static String MESSAGE_KEY_FIELD_AUTOPAY = "��� ����������, ��������������� ����������, ������ ���� ������ ���� �������� ����";

	@Override
	protected Class<?> getEntityClass()
	{
		return provider.getClass();
	}

	/**
	 * ���������������� �������� ��� �������� ������ ����
	 * @param id ������������ ���������� �����
	 * @throws BusinessException
	 */
	public void initializeNew(Long id) throws BusinessException
	{
		initializeProvider(id);
		for(FieldDescription fieldDescription : provider.getFieldDescriptions())
			if(fieldDescription.isKey()) countKeyFieldDescription++;
		description = new FieldDescription();
		description.setHolderId(id);
		isNew = true;
	}

	/**
	 * ���������������� �������� ��� �������������� ����
	 * @param id ������������ ����
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		FieldDescription temp = simpleService.findById(FieldDescription.class, id, getInstanceName());
		if (temp == null)
		{
			throw new BusinessException("���� � id = " + id + " �� �������");
		}

		initializeProvider(temp.getHolderId());
		//������� ���� � ����������.
		for (FieldDescription d : provider.getFieldDescriptions())
		{
			if (d.getId().equals(id))
			{
				description = d;
				break;
			}
		}
		isNew = false;
	}

	private void initializeProvider(Long providerId) throws BusinessException
	{
		BillingServiceProviderBase temp = (BillingServiceProviderBase) providerService.findById(providerId, getInstanceName());
		if (temp == null)
		{
			throw new BusinessException("��������� ����� � providerId = " + providerId + " �� ������");
		}

		if (!getRestriction().accept(temp))
		{
			throw new RestrictionViolationException("��������� ID= " + temp.getId());
		}
		provider = temp;
	}

	public void doSave() throws BusinessException, BusinessLogicException
	{
		//��������� �� ������� �� ���������
		if (provider.getState().equals(ServiceProviderState.ACTIVE))
			throw new BusinessLogicException(MESSAGE_ACTIVE);

		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					if (isNew)
					{
						// �������� ��� ������������ (������ ���� ���� �������� ���� ��� �� iqwave)
						if(provider instanceof BillingServiceProvider && AutoPaymentHelper.isIQWProvider(provider)&&
								(( (BillingServiceProvider) provider).isAutoPaymentSupported()||
										((BillingServiceProvider) provider).isAutoPaymentSupportedInApi()||
										((BillingServiceProvider) provider).isAutoPaymentSupportedInATM())
								&& countKeyFieldDescription >= 1 && description.isKey())
							throw new BusinessLogicException(MESSAGE_KEY_FIELD_AUTOPAY);

						provider.addField(description);
					}
					providerService.addOrUpdate(provider, getInstanceName());
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
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public FieldDescription getEntity()
	{
		return description;
	}

	public String getProviderName()
	{
		return provider.getName();
	}

	public String getBillingName()
	{
		return provider.getBilling().getName();
	}

	public String getServiceName()
	{
		return provider.getNameService();
	}
}
