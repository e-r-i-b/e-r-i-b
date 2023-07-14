package com.rssl.phizic.operations.ext.sbrf.technobreaks;

import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreak;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.technobreaks.TechnoBreakHelper;
import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreakStatus;
import com.rssl.phizic.business.ext.sbrf.technobreaks.TechnoBreaksService;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.externalsystem.AutoTechnoBreakConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.routing.AdapterService;

import java.util.Calendar;

/**
 * @author niculichev
 * @ created 10.10.2011
 * @ $Author$
 * @ $Revision$
 *
 * �������� �������������� ���.���������
 */

public class EditTechnoBreakOperation extends OperationBase<Restriction> implements EditEntityOperation<TechnoBreak, Restriction>
{
	private static final String NOT_FOUNT_EXTERNAL_SYSTEM_ERROR_MESSAGE = "������� ������� � ��������������� %s �� ������� � ���� ������";
	private static final String DATE_CHANGED_ERROR_MESSAGE = "���� ������ ������������ �������� �� ����� ���� ��������";

	private static final TechnoBreaksService technoBreaksService = new TechnoBreaksService();
	private static final AdapterService adapterService= new AdapterService();

	private boolean isNew;
	private TechnoBreak technoBreak;
	private Calendar lastFromDate;

	/**
	 * ������������� �������� ������������ ���.���������
	 * @param id ������������� ���.��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		technoBreak = technoBreaksService.findById(id);
		if (technoBreak == null)
		{
			throw new BusinessException("��������������� ������� � id = " + id + " �� ������ � �������.");
		}

		if(!TechnoBreakHelper.isActive(technoBreak))
		{
			throw new BusinessLogicException("�� �� ������ ��������������� ��� ������� ������������ ��������������� �������.");
		}

		isNew = false;
		lastFromDate = technoBreak.getFromDate();
	}

	/**
	 * ������������� �������� ����� ���.���������
	 */
	public void initializeNew()
	{
		technoBreak = new TechnoBreak();
		technoBreak.setUuid(new RandomGUID().toUUID());
		technoBreak.setFromDate(Calendar.getInstance());
		technoBreak.setStatus(TechnoBreakStatus.ENTERED);
		isNew = true;
		technoBreak.setAllowOfflinePayments(ConfigFactory.getConfig(AutoTechnoBreakConfig.class).isAllowOfflinePayments());
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		//���� ����� ���. ������� � ��������� ���� ������ ������ �������,
		//�� ���������� � ���� ������ ���������� ������� ����.
		if (isNew && DateHelper.diff(technoBreak.getFromDate(), Calendar.getInstance()) <= 0)
		{
			technoBreak.setFromDate(Calendar.getInstance());
			if(DateHelper.diff(technoBreak.getToDate(), technoBreak.getFromDate())<=0)
				throw new BusinessLogicException("���� ��������� ������ ���� ������ ���� ������ ������� �������� ���������������� ��������. ����������, ������� ������ ����.");
		}
		//��� ������������� ���. ���������, ���� ������ �� ������ ����������.
		else if (!isNew && lastFromDate != null && Math.abs(DateHelper.diff(lastFromDate, technoBreak.getFromDate())) >= 1000)
		{
			throw new BusinessLogicException(DATE_CHANGED_ERROR_MESSAGE);
		}

		technoBreaksService.saveOrUpdate(technoBreak);
	}

	public TechnoBreak getEntity()
	{
		return technoBreak;
	}

	/**
	 * ������ ������������� ������� �������
	 * @param uuid ������������� ������� �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void setExternalSystem(String uuid) throws BusinessException, BusinessLogicException
	{
		try
		{
			Adapter adapter = adapterService.getAdapterByUUID(uuid);
			if(adapter == null)
				throw new BusinessLogicException(String.format(NOT_FOUNT_EXTERNAL_SYSTEM_ERROR_MESSAGE, uuid));

			technoBreak.setAdapterUUID(uuid);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}

	}
}
