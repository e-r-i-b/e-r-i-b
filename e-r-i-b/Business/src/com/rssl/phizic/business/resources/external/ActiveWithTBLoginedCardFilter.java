package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.jmx.BusinessSettingsConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 *
 * Фильтр допускает только действующие карты, открытые в том же ТБ, что и счет карты с помощью которой были получены данные для авторизации в ЕРИБ.
 * При проверке учитываются также синонимы ТБ и принадлежность ТБ к одной группе
 *
 * @ author: Gololobov
 * @ created: 06.09.13
 * @ $Author$
 * @ $Revision$
 */
public class ActiveWithTBLoginedCardFilter extends ActiveCardWithArrestedAccountFilter
{
	private static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final Map<String, String> IDENTICAL_TB_CODES_MAP = new HashMap<String, String>();
	private static final String GATE_PROPERTIES_FILE = "gate.properties";
	private static final String GROUP_PREFIX = "com.rssl.phizic.gate.TB.group";
	private static final String SPLITER = ",";
	static
	{
		IDENTICAL_TB_CODES_MAP.put("38","99");
		IDENTICAL_TB_CODES_MAP.put("99","99");
	}

	public boolean accept(Card card)
	{
		if (!super.accept(card))
			return false;

		try
		{
			Login login = PersonHelper.getLastClientLogin();
			String lastLogonCardTBlogin = login.getLastLogonCardTB();

			if (StringHelper.isEmpty(lastLogonCardTBlogin))
				return false;

			ExtendedCodeImpl code = new ExtendedCodeImpl(card.getOffice().getCode());
			String cardTb = StringHelper.removeLeadingZeros(code.getRegion());
			lastLogonCardTBlogin = StringHelper.removeLeadingZeros(lastLogonCardTBlogin);

			boolean result = StringHelper.equalsNullIgnore(lastLogonCardTBlogin, cardTb);

			if (!result)
			{
				//Проверка с учетом идентичных ТБ
				String cardTbFromIdentical = IDENTICAL_TB_CODES_MAP.get(cardTb);
				String lastLogonCardTBFromIdentical = IDENTICAL_TB_CODES_MAP.get(lastLogonCardTBlogin);

				result = StringHelper.isNotEmpty(cardTbFromIdentical) &&
						 StringHelper.isNotEmpty(lastLogonCardTBFromIdentical) &&
						 StringHelper.equals(cardTbFromIdentical, lastLogonCardTBFromIdentical);

				if (!result)
				{
					//Проверка с учетом синонимов ТБ. Для сравнения получаем основные ТБ.
					String mainTBBySynonymCardTb = ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonym(cardTb);
					String mainTBBySynonymLastLogonCardTB = ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonym(lastLogonCardTBlogin);

					result = StringHelper.equals(mainTBBySynonymCardTb, mainTBBySynonymLastLogonCardTB);

					//Проверка с учетом принадлежности ТБ к одной группе
					if (!result)
					{
						result = isIdenticalGroup(cardTb, lastLogonCardTBlogin);

						if (!result)
						{
							result = isIdenticalGroup(mainTBBySynonymCardTb, mainTBBySynonymLastLogonCardTB);

							if (!result)
							{
								//Проверка с учетом подмены кодов ТБ
								Map<String, String> tbReplacements = ConfigFactory.getConfig(BusinessSettingsConfig.class).getTBReplacementsMap();
								if (tbReplacements.keySet().contains(lastLogonCardTBlogin))
									lastLogonCardTBlogin = tbReplacements.get(lastLogonCardTBlogin);

								result = StringHelper.equalsNullIgnore(lastLogonCardTBlogin, cardTb);
							}
						}
					}
				}
			}
			return result;
		}
		catch (BusinessException ex)
		{
			LOG.error("Ошибка при построении списка карт для перечисления процентов.", ex);
			return false;
		}
	}

	/**
	 * Находятся ли ТБ-ки в одной группе
	 * @param curatorTB
	 * @param productTB
	 * @return
	 */
	private boolean isIdenticalGroup(String curatorTB, String productTB)
	{
		Properties groups = ConfigFactory.getReaderByFileName(GATE_PROPERTIES_FILE).getProperties(GROUP_PREFIX);

		if (CollectionUtils.isEmpty(groups.entrySet()))
			return false;

		for (Map.Entry entry : groups.entrySet())
		{
			List<String> groupList = Arrays.asList(((String) entry.getValue()).split(SPLITER));

			if (groupList.contains(curatorTB) && groupList.contains(productTB))
				return true;
		}
		return false;
	}
}
