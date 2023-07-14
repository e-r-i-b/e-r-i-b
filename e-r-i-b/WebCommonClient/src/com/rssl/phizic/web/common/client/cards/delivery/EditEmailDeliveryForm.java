package com.rssl.phizic.web.common.client.cards.delivery;

import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 05.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * форма редактирования подписки
 */

public class EditEmailDeliveryForm extends ActionFormBase
{
	private Long cardId;
	private Long claimId;
	private boolean needSave;
	private boolean use;
	private String email;
	private String type;
	private String language;
	private String html;

	private BusinessDocument document;
	private ConfirmableObject confirmableObject;
	private ConfirmStrategy confirmStrategy;
	private ConfirmStrategyType confirmStrategyType;

	private Map<String,Object> fields = new HashMap<String, Object>();

	private boolean anotherStrategyAvailable;
	private Metadata metadata;
	private String metadataPath;

	/**
	 * @return идентификатор карты
	 */
	public Long getCardId()
	{
		return cardId;
	}

	/**
	 * задать идентификатор карты
	 * @param cardId идентификатор карты
	 */
	public void setCardId(Long cardId)
	{
		this.cardId = cardId;
	}

	/**
	 * @return идентификатор заявки
	 */
	public Long getClaimId()
	{
		return claimId;
	}

	/**
	 * задать идентификатор заявки
	 * @param claimId идентификатор заявки
	 */
	public void setClaimId(Long claimId)
	{
		this.claimId = claimId;
	}

	/**
	 * @return необходимо ли сохранять данные
	 */
	public boolean isNeedSave()
	{
		return needSave;
	}

	/**
	 * задать необходимость сохранения данных
	 * @param needSave необходимо ли сохранять данные
	 */
	public void setNeedSave(boolean needSave)
	{
		this.needSave = needSave;
	}

	/**
	 * @return использовать ли подписку
	 */
	public boolean isUse()
	{
		return use;
	}

	/**
	 * задать признак использования подписки
	 * @param use использовать ли подписку
	 */
	public void setUse(boolean use)
	{
		this.use = use;
	}

	/**
	 * @return адрес подписки
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * задать адрес подписки
	 * @param email адрес подписки
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @return тип данных подписки
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * задать тип данных подписки
	 * @param type тип данных подписки
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * @return язык данных подписки
	 */
	public String getLanguage()
	{
		return language;
	}

	/**
	 * задать язык данных подписки
	 * @param language язык данных подписки
	 */
	public void setLanguage(String language)
	{
		this.language = language;
	}

	/**
	 * @return html-представление заявки
	 */
	public String getHtml()
	{
		return html;
	}

	/**
	 * задать html-представление заявки
	 * @param html html-представление заявки
	 */
	public void setHtml(String html)
	{
		this.html = html;
	}

	/**
	 * @return заявка
	 */
	public BusinessDocument getDocument()
	{
		return document;
	}

	/**
	 * задать документ заявки
	 * @param document заявка
	 */
	public void setDocument(BusinessDocument document)
	{
		this.document = document;
	}

	/**
	 * @return объект для подтверждения
	 */
	public ConfirmableObject getConfirmableObject()
	{
		return confirmableObject;
	}

	/**
	 * задать объект для подтверждения
	 * @param confirmableObject объект для подтверждения
	 */
	public void setConfirmableObject(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	/**
	 * @return стратегия подтверждения
	 */
	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	/**
	 * задать стратегию подтверждения
	 * @param confirmStrategy стратегия подтверждения
	 */
	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
	}

	/**
	 * @return дополнительные поля формы
	 */
	public Map<String,Object> getFields()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return fields;
	}

	/**
	 * задать дополнительные поля формы
	 * @param fields дополнительные поля формы
	 */
	public void setFields(Map<String, Object> fields)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.fields = fields;
	}

	/**
	 * @param key ключ дополнительного поля формы
	 * @return значение дополнительного поля формы
	 */
	public Object getField(String key)
	{
		return fields.get(key);
	}

	/**
	 * задать дополнительное поле формы
	 * @param key ключ дополнительного поля формы
	 * @param obj значение дополнительного поля формы
	 */
	public void setField(String key, Object obj)
	{
		fields.put(key, obj);
	}

	public boolean isAnotherStrategyAvailable()
	{
		return anotherStrategyAvailable;
	}

	public void setAnotherStrategyAvailable(boolean anotherStrategyAvailable)
	{
		this.anotherStrategyAvailable = anotherStrategyAvailable;
	}
}
