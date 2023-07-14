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
 * ����� �������������� ��������
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
	 * @return ������������� �����
	 */
	public Long getCardId()
	{
		return cardId;
	}

	/**
	 * ������ ������������� �����
	 * @param cardId ������������� �����
	 */
	public void setCardId(Long cardId)
	{
		this.cardId = cardId;
	}

	/**
	 * @return ������������� ������
	 */
	public Long getClaimId()
	{
		return claimId;
	}

	/**
	 * ������ ������������� ������
	 * @param claimId ������������� ������
	 */
	public void setClaimId(Long claimId)
	{
		this.claimId = claimId;
	}

	/**
	 * @return ���������� �� ��������� ������
	 */
	public boolean isNeedSave()
	{
		return needSave;
	}

	/**
	 * ������ ������������� ���������� ������
	 * @param needSave ���������� �� ��������� ������
	 */
	public void setNeedSave(boolean needSave)
	{
		this.needSave = needSave;
	}

	/**
	 * @return ������������ �� ��������
	 */
	public boolean isUse()
	{
		return use;
	}

	/**
	 * ������ ������� ������������� ��������
	 * @param use ������������ �� ��������
	 */
	public void setUse(boolean use)
	{
		this.use = use;
	}

	/**
	 * @return ����� ��������
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * ������ ����� ��������
	 * @param email ����� ��������
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @return ��� ������ ��������
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * ������ ��� ������ ��������
	 * @param type ��� ������ ��������
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * @return ���� ������ ��������
	 */
	public String getLanguage()
	{
		return language;
	}

	/**
	 * ������ ���� ������ ��������
	 * @param language ���� ������ ��������
	 */
	public void setLanguage(String language)
	{
		this.language = language;
	}

	/**
	 * @return html-������������� ������
	 */
	public String getHtml()
	{
		return html;
	}

	/**
	 * ������ html-������������� ������
	 * @param html html-������������� ������
	 */
	public void setHtml(String html)
	{
		this.html = html;
	}

	/**
	 * @return ������
	 */
	public BusinessDocument getDocument()
	{
		return document;
	}

	/**
	 * ������ �������� ������
	 * @param document ������
	 */
	public void setDocument(BusinessDocument document)
	{
		this.document = document;
	}

	/**
	 * @return ������ ��� �������������
	 */
	public ConfirmableObject getConfirmableObject()
	{
		return confirmableObject;
	}

	/**
	 * ������ ������ ��� �������������
	 * @param confirmableObject ������ ��� �������������
	 */
	public void setConfirmableObject(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	/**
	 * @return ��������� �������������
	 */
	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	/**
	 * ������ ��������� �������������
	 * @param confirmStrategy ��������� �������������
	 */
	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
	}

	/**
	 * @return �������������� ���� �����
	 */
	public Map<String,Object> getFields()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return fields;
	}

	/**
	 * ������ �������������� ���� �����
	 * @param fields �������������� ���� �����
	 */
	public void setFields(Map<String, Object> fields)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.fields = fields;
	}

	/**
	 * @param key ���� ��������������� ���� �����
	 * @return �������� ��������������� ���� �����
	 */
	public Object getField(String key)
	{
		return fields.get(key);
	}

	/**
	 * ������ �������������� ���� �����
	 * @param key ���� ��������������� ���� �����
	 * @param obj �������� ��������������� ���� �����
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
