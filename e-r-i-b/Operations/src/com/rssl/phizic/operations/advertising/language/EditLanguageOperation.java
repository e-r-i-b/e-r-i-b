package com.rssl.phizic.operations.advertising.language;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.advertising.AdvertisingBlock;
import com.rssl.phizic.business.advertising.AdvertisingButton;
import com.rssl.phizic.business.advertising.AdvertisingService;
import com.rssl.phizic.business.advertising.locale.AdvertisingBlockResources;
import com.rssl.phizic.business.advertising.locale.AdvertisingButtonResources;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourcesBaseService;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.services.MultiInstanceEribLocaleService;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author komarov
 * @ created 24.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditLanguageOperation extends EditDictionaryEntityOperationBase implements EditLanguageResourcesOperation<AdvertisingBlockResources,Long>
{
	private static final AdvertisingService ADVERTISING_SERVICE = new AdvertisingService();
	private static final LanguageResourcesBaseService<AdvertisingBlockResources> ADVERTISING_BLOCK_SERVICE = new LanguageResourcesBaseService<AdvertisingBlockResources>(AdvertisingBlockResources.class);
	private static final LanguageResourcesBaseService<AdvertisingButtonResources> ADVERTISING_BUTTON_SERVICE = new LanguageResourcesBaseService<AdvertisingButtonResources>(AdvertisingButtonResources.class);
	private static final MultiInstanceEribLocaleService LOCALE_SERVICE = new MultiInstanceEribLocaleService();

	private AdvertisingBlock advertising;
	private ERIBLocale locale;
	private AdvertisingBlockResources advertising_res;
	private List<AdvertisingButtonResources> buttons_res = new ArrayList<AdvertisingButtonResources>();
	private Map<Long, AdvertisingButtonResources> buttonsResMap = new HashMap<Long, AdvertisingButtonResources>();

	/**
	 * Инициализация
	 * @param id идентификатор баннера
	 * @param localeId идентификатор локали
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long id, String localeId) throws BusinessException, BusinessLogicException
	{
		advertising = ADVERTISING_SERVICE.findById(id, getInstanceName());

		if (advertising == null)
			throw new BusinessLogicException("Рекламный блок с id = " + id + " не найден");

		try
		{
			locale = LOCALE_SERVICE.getById(localeId, null);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
		if(locale == null)
			throw new BusinessLogicException("Локаль с id= " + localeId + "не найдена");

		advertising_res = ADVERTISING_BLOCK_SERVICE.findResById(advertising.getUuid(), localeId, getInstanceName());
		if(advertising_res == null)
		{
			advertising_res = new AdvertisingBlockResources();
			advertising_res.setUuid(advertising.getUuid());
			advertising_res.setLocaleId(localeId);
		}

		for(AdvertisingButton button : advertising.getButtons())
		{
			AdvertisingButtonResources button_res = ADVERTISING_BUTTON_SERVICE.findResById(button.getUuid(), localeId, getInstanceName());
			if(button_res == null)
			{
				button_res = new AdvertisingButtonResources();
				button_res.setUuid(button.getUuid());
				button_res.setLocaleId(localeId);
			}
			buttons_res.add(button_res);
			buttonsResMap.put(button.getId(), button_res);
		}
	}


	public void doSave() throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					ADVERTISING_BLOCK_SERVICE.addOrUpdate(advertising_res, getInstanceName());
					ADVERTISING_BUTTON_SERVICE.addOrUpdate(buttons_res, getInstanceName());
					return null;
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	public AdvertisingBlockResources getEntity()
	{
		return advertising_res;
	}

	/**
	 * @return кнопки баннера
	 */
	public List<AdvertisingButton> getButtons()
	{
		return advertising.getButtons();
	}

	/**
	 * @return связка идентификатор кнопки -> многоязычные текстовки
	 */
	public Map<Long, AdvertisingButtonResources> getButtonsResMap()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return buttonsResMap;
	}

	/**
	 * @return локаль
	 */
	public ERIBLocale getLocale()
	{
		return locale;
	}

}
