package com.rssl.phizic.business.quick.pay;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.cache.CacheProvider;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.util.List;

/**
 * ������ ��� ������ � ������� ������� ������(���)
 * @author komarov
 * @ created 06.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class QuickPaymentPanelService extends MultiInstanceQuickPaymentPanelService
{
	private static Cache quickPaymentPanelCache;

	static
	{
		quickPaymentPanelCache = CacheProvider.getCache("quick-payment-panel-cache");
	}

	/**
	 * ���������� ��� ���������� ������ ������� ������(���).
	 * @param panel ���
	 * @return ������
	 * @throws BusinessException, BusinessLogicException
	 */
	public QuickPaymentPanel addOrUpdate(QuickPaymentPanel panel,  String instance) throws BusinessException, BusinessLogicException
	{
		QuickPaymentPanel quickPaymentPanel = super.addOrUpdate(panel, instance);
		quickPaymentPanelCache.removeAll();
		return quickPaymentPanel;
	}

	/**
	 * �������� ������ ������� ������(���).
	 * @param panel ���
	 * @throws BusinessException
	 */
	public void remove(QuickPaymentPanel panel, String instance) throws BusinessException
	{   		
		super.remove(panel, instance);
		quickPaymentPanelCache.removeAll();
	}

	/**
	 * ����� ������ ������� ������(���) �� ��������������.
	 * @param id �������������
	 * @return ���
	 * @throws BusinessException
	 */
	public QuickPaymentPanel findById(Long id) throws BusinessException
	{
		return super.findById(id, null);
	}

	/**
	 * ����� ������ ������ ������� ������(���) �� ��������.
	 * @param trb �������
	 * @return ����� ���
	 * @throws BusinessException
	 */
	public List<PanelBlock> findByTRB(final String trb) throws BusinessException
	{
		Element element = quickPaymentPanelCache.get(trb);
		if (element == null)
		{
			List<PanelBlock>  panelBlocks = super.findByTRB(trb, null);
			quickPaymentPanelCache.put(new Element(trb, panelBlocks));
			return panelBlocks;
		}
		return (List<PanelBlock>) element.getObjectValue();
	}

	/**
	 * ����� ��������� ��� ������� ��� ������� ���.
	 * @return ��������
	 * @throws BusinessException
	 */
	public List<Department> findTRBwithQPP() throws BusinessException
	{
	   return super.findTRBwithQPP(null);
	}
}
