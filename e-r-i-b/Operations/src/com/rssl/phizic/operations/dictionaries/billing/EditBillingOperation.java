package com.rssl.phizic.operations.dictionaries.billing;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.billing.MultiInstanceBillingService;
import com.rssl.phizic.gate.ConnectMode;
import com.rssl.phizic.gate.GateConfiguration;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.routing.AdapterService;

/**
 * @author akrenev
 * @ created 10.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditBillingOperation extends EditDictionaryEntityOperationBase
{
	private Billing billing;
	private static final MultiInstanceBillingService billingService = new MultiInstanceBillingService();
	private static AdapterService adapterService = new AdapterService();
	protected static final GateInfoService gateInfoService = GateSingleton.getFactory().service(GateInfoService.class);
	private String userName     = null;
	private ConnectMode connectMode  = null;
	private Boolean requisites  = null;
	private Boolean comission   = null;
	private Long timeout     = null;

	/**
	 * �������������� ��������
	 * @param id ����������� �������
	 * @exception BusinessException
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		billing = billingService.getById(id, getInstanceName());
		if (billing == null)
		{
			throw new BusinessLogicException("����������� ������� � id=" + id + " �� �������.");
		}
		try
		{
			GateConfiguration gateConfiguration = gateInfoService.getConfiguration(billing);
			userName = gateConfiguration.getUserName();
			connectMode = gateConfiguration.getConnectMode();
			requisites = gateInfoService.isRecipientExtedendAttributesAvailable(billing);
			comission = gateInfoService.isPaymentCommissionAvailable(billing);
			timeout = gateConfiguration.getConnectionTimeout();
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	/**
	 * �������������� �������� ����� ����������� ��������
	 */
	public void initializeNew()
	{
		billing = new Billing();
	}

	public void doSave() throws BusinessException, BusinessLogicException
	{
		billingService.addOrUpdate(billing, getInstanceName());
	}

	public Billing getEntity() throws BusinessException, BusinessLogicException
	{
		return billing;
	}

	/**
	 * @return �������
	 * @throws BusinessException
	 */
	public Adapter getAdapter() throws BusinessException
	{
		try
		{
			return adapterService.getAdapterByUUID(billing.getAdapterUUID());
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ���������� � �������������
	 * @param adapterUUID UUID ��������
	 * @exception BusinessException
	 * */
	public void addAdapter(String adapterUUID) throws BusinessException
	{
		billing.setAdapterUUID(adapterUUID);
	}

	/**
	 * ��� ������������, �� ����� �������� ��������������� ����������
	 * @return ��� ������������
	 * @exception BusinessException, BusinessLogicException
	 * */
	public String getUserName() throws BusinessException, BusinessLogicException
	{
		return userName;
	}

	/**
	 * ����� ������: ����������� ��� ����������
	 * @return ASYNC - �����������, SYNC - ����������
	 * @exception BusinessException, BusinessLogicException
	 * */
	public ConnectMode getConnectMode() throws BusinessException, BusinessLogicException
	{
		return connectMode;
	}

	/**
	 * ��������� �������� �� ����������� �����: ����� �� ����������� ����������� � ����, ����� �� ����������� �������
	 * @return true - ����� �� ����������� �������
	 * @exception BusinessException, BusinessLogicException
	 * */
	public Boolean getRequisites() throws BusinessException, BusinessLogicException
	{
		return requisites;
	}

	/**
	 *  ����� �� ����������� ������� ���������� ����� �������� �� �������� ������� ��� ���
	 * @return true - ��������� �������� �� ��
	 * @exception BusinessException, BusinessLogicException
	 * */
	public Boolean getComission() throws BusinessException, BusinessLogicException
	{
		return comission;
	}

	/**
	 * @return ����� �������� ������ �� ������� � �������������
	 * @exception BusinessException, BusinessLogicException
	 * */
	public Long getTimeout() throws BusinessException, BusinessLogicException
	{
		return timeout;
	}
}
