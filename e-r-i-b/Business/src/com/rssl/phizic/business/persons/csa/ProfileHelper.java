package com.rssl.phizic.business.persons.csa;

import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.service.ClientCacheKeyGenerator;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.business.persons.GUIDImpl;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientHelper;
import com.rssl.phizic.gate.clients.GUID;
import com.rssl.phizic.gate.csa.Profile;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.StoreAction;
import com.rssl.phizic.utils.store.StoreHelper;
import com.rssl.phizic.utils.store.StoreManager;

import java.util.List;

/**
 * @author khudyakov
 * @ created 14.11.14
 * @ $Author$
 * @ $Revision$
 */
public class ProfileHelper
{
	private static final ProfileService profileService = new ProfileService();

	/**
	 * Возвращает историю изменений профиля в ЦСА
	 * Важно: данные кешируются в сессии
	 *
	 * @param client клиент
	 * @return история изменений профиля
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static Pair<Profile, List<? extends GUID>> getStoredProfileHistory(final Client client) throws BusinessException, BusinessLogicException
	{
		try
		{
			String key = Constants.GATE_TEMPLATE_STORE_NAME + ClientCacheKeyGenerator.getKey(client);
			return StoreHelper.getStoredEntity(StoreManager.getCurrentStore(), key, new StoreAction<Pair<Profile, List<? extends GUID>>>()
			{
				public Pair<Profile, List<? extends GUID>> getEntity() throws Exception
				{
					Profile profile = profileService.findProfileWithHistory(getReversedGuid(client));
					if (profile == null)
					{
						return null;
					}

					return new Pair<Profile, List<? extends GUID>>(profile, profile.getUniqueHistory());
				}
			});
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Возвращает историю изменений профиля в ЦСА
	 *
	 * @param client клиент
	 * @return история изменений профиля
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static List<? extends GUID> getProfileHistory(final Client client) throws BusinessException, BusinessLogicException
	{
		try
		{
			Profile profile = profileService.findProfileWithHistory(getReversedGuid(client));
			if (profile == null)
			{
				return null;
			}

			return profile.getUniqueHistory();
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить GUID клиент по используемой информации,
	 * при этом вместо ТБ клиента подставляется главный ТБ из синонимов ТБ клиента
	 *
	 * @param client клиент
	 * @return GUID
	 * @throws GateException
	 */
	private static GUID getReversedGuid(Client client) throws GateException
	{
		String tb = new SBRFOfficeCodeAdapter(client.getOffice().getCode()).getRegion();
		return new GUIDImpl(client.getSurName(), client.getFirstName(), client.getPatrName(), ClientHelper.getClientWayDocument(client).getDocSeries(), client.getBirthDay(), getMainTb(tb));
	}

	private static String getMainTb(String tb)
	{
		String mainTb = ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonymAndIdentical(tb);
		if (StringHelper.isNotEmpty(mainTb))
		{
			return mainTb;
		}
		return tb;
	}
}
