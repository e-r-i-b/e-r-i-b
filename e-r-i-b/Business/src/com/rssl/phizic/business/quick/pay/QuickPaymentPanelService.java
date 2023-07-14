package com.rssl.phizic.business.quick.pay;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.cache.CacheProvider;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.util.List;

/**
 * Сервис для работы с Панелью Быстрой Оплаты(ПБО)
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
	 * Добавление или обновление панели быстрой оплаты(ПБО).
	 * @param panel ПБО
	 * @return баннер
	 * @throws BusinessException, BusinessLogicException
	 */
	public QuickPaymentPanel addOrUpdate(QuickPaymentPanel panel,  String instance) throws BusinessException, BusinessLogicException
	{
		QuickPaymentPanel quickPaymentPanel = super.addOrUpdate(panel, instance);
		quickPaymentPanelCache.removeAll();
		return quickPaymentPanel;
	}

	/**
	 * Удаление Панели Быстрой Оплаты(ПБО).
	 * @param panel ПБО
	 * @throws BusinessException
	 */
	public void remove(QuickPaymentPanel panel, String instance) throws BusinessException
	{   		
		super.remove(panel, instance);
		quickPaymentPanelCache.removeAll();
	}

	/**
	 * Поиск Панели Быстрой Оплаты(ПБО) по идентификатору.
	 * @param id идентификатор
	 * @return ПБО
	 * @throws BusinessException
	 */
	public QuickPaymentPanel findById(Long id) throws BusinessException
	{
		return super.findById(id, null);
	}

	/**
	 * Поиск блоков панели быстрой оплаты(ПБО) по тербанку.
	 * @param trb тербанк
	 * @return блоки ПБО
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
	 * Поиск тербанков для которых уже созданы ПБО.
	 * @return Тербанки
	 * @throws BusinessException
	 */
	public List<Department> findTRBwithQPP() throws BusinessException
	{
	   return super.findTRBwithQPP(null);
	}
}
