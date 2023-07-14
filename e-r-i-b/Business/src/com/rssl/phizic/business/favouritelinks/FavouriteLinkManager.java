package com.rssl.phizic.business.favouritelinks;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;

import java.util.ArrayList;
import java.util.List;

/**
 * менеджер для работы с любимыми ссылками. кэширует результаты в сессии
 * @author basharin
 * @ created 05.06.14
 * @ $Author$
 * @ $Revision$
 */

public class FavouriteLinkManager
{
	private static final String STORE_FAVOURITE_LINK_LIST_KEY = "storeFavouriteLinkListKey";
	private FavouriteLinkService favouriteLinkService = new FavouriteLinkService();


	/**
	 * Сохранение ссылки
	 * @param link - ссылка
	 * @return сохраненная ссылка
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void add( final FavouriteLink link) throws BusinessException, BusinessLogicException
	{
		favouriteLinkService.addOrUpdate(link);
		clearCache();
	}

	/**
	 * Сохранение ссылки
	 * @param link - ссылка
	 * @return сохраненная ссылка
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void update(final FavouriteLink link) throws BusinessException, BusinessLogicException
	{
		favouriteLinkService.addOrUpdate(link);
		clearCache();
	}

	/**
	 * Проверка есть ли такая ссылка в бд
     * @param url ссылка
	 * @return есть ли такая ссылка в бд
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public boolean isLinkInFavourite(String url, final long loginId) throws BusinessException
	{
		List<FavouriteLink> links = getLinks(loginId);
		for (FavouriteLink link : links)
			if (link.getLink().equals(url))
				return true;
		return false;
	}

	/**
	 * Поиск ссылок пользователя
	 * @param loginId - логин пользователя
	 * @return List ссылок
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<FavouriteLink> findByUserId(final long loginId) throws BusinessException
	{
		return getLinks(loginId);
	}

	/**
	 * Поиск  ссылок пользователя, включающих в себя заданный паттерн
	 * @param loginId - логин пользователя
	 * @param pattern - паттерн для поиска
	 * @return List ссылок
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<FavouriteLink> findByUserIdPattern(final long loginId, final String pattern) throws BusinessException
	{
		List<FavouriteLink> links = getLinks(loginId);
		List<FavouriteLink> list = new ArrayList<FavouriteLink>();
		for (FavouriteLink link : links)
			if (link.getPattern() != null && link.getPattern().contains(pattern))
				list.add(link);
		return list;
	}

	/**
	 * Удаление ссылки
	 * @param id - идентификатор
	 * @param loginId - логин пользователя
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void removeById(Long id, long loginId) throws BusinessException
	{
		favouriteLinkService.removeById(id, loginId);
		clearCache();
	}

	/**
	 * Удаление ссылки
	 * @param link - идентификатор
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void remove(FavouriteLink link) throws BusinessException
	{
		favouriteLinkService.remove(link);
		clearCache();
	}

	/**
	 * Максимальный порядковый индекс для ссылки пользователя
	 * @param loginId - логин пользователя
	 * @return максимальный индекс
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public int maxLinkInd(final long loginId) throws BusinessException
	{
		List<FavouriteLink> list = getLinks(loginId);
		if (list.size() == 0)
			return 0;
		return list.get(list.size() - 1).getOrderInd();
	}

	private List<FavouriteLink> getLinks(long loginId) throws BusinessException
	{
		Store store = StoreManager.getCurrentStore();
		List<FavouriteLink> list = (List<FavouriteLink>) store.restore(STORE_FAVOURITE_LINK_LIST_KEY);
		if (list == null)
		{
			list = favouriteLinkService.findByUserId(loginId);
			store.save(STORE_FAVOURITE_LINK_LIST_KEY, list);
		}
		return list;
	}

	private void clearCache()
	{
		Store store = StoreManager.getCurrentStore();
		store.remove(STORE_FAVOURITE_LINK_LIST_KEY);
	}
}
