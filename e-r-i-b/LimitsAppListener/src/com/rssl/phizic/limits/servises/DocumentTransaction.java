package com.rssl.phizic.limits.servises;

import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.common.types.limits.OperationType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.limits.handlers.ProfileInfo;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author osminin
 * @ created 20.01.14
 * @ $Author$
 * @ $Revision$
 *
 * Сущность - транзакция документа
 */
public class DocumentTransaction extends ActiveRecord
{
	private String externalId;
	private String documentExternalId;
	private Long profileId;
	private Calendar operationDate;
	private OperationType operationType;
	private ChannelType channelType;
	private String limitsInfo;
	private BigDecimal amountValue;
	private String amountCurrency;

	/**
	 * ctor
	 */
	public DocumentTransaction(){}

	/**
	 * ctor
	 * @param externalId внешний идентификатор записи
	 * @param documentExternalId внешний идентификатор платжеа
	 * @param profileId идентификатор профиля
	 * @param amountCurrency валюта суммы
	 * @param amountValue значение суммы
	 * @param operationDate дата исполнения
	 * @param operationType тип операции
	 * @param channelType тип канала
	 * @param limitsInfo информация о лимитах
	 */
	public DocumentTransaction(String externalId, String documentExternalId, Long profileId, BigDecimal amountValue, String amountCurrency, Calendar operationDate, OperationType operationType, ChannelType channelType, String limitsInfo)
	{
		this.externalId = externalId;
		this.documentExternalId = documentExternalId;
		this.profileId = profileId;
		this.amountCurrency = amountCurrency;
		this.amountValue = amountValue;
		this.operationDate = operationDate;
		this.operationType = operationType;
		this.channelType = channelType;
		this.limitsInfo = limitsInfo;
	}

	/**
	 * ctor
	 * @param data транзакция документа
	 */
	public DocumentTransaction(DocumentTransaction data)
	{
		this.externalId = data.getExternalId();
		this.documentExternalId = data.getDocumentExternalId();
		this.profileId = data.getProfileId();
		this.amountValue = data.getAmountValue();
		this.amountCurrency = data.getAmountCurrency();
		this.operationDate = data.getOperationDate();
		this.operationType = data.getOperationType();
		this.channelType = data.getChannelType();
		this.limitsInfo = data.getLimitsInfo();
	}

	/**
	 * @return внешний идентификатор записи
	 */
	public String getExternalId()
	{
		return externalId;
	}

	/**
	 * @param externalId внешний идентификатор записи
	 */
	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	/**
	 * @return внешний идентификатор платжеа
	 */
	public String getDocumentExternalId()
	{
		return documentExternalId;
	}

	/**
	 * @param documentExternalId внешний идентификатор платжеа
	 */
	public void setDocumentExternalId(String documentExternalId)
	{
		this.documentExternalId = documentExternalId;
	}

	/**
	 * @return идентификатор профиля
	 */
	public Long getProfileId()
	{
		return profileId;
	}

	/**
	 * @param profileId идентификатор профиля
	 */
	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	/**
	 * @return дата исполнения
	 */
	public Calendar getOperationDate()
	{
		return operationDate;
	}

	/**
	 * @param operationDate дата исполнения
	 */
	public void setOperationDate(Calendar operationDate)
	{
		this.operationDate = operationDate;
	}

	/**
	 * @return тип операции
	 */
	public OperationType getOperationType()
	{
		return operationType;
	}

	/**
	 * @param operationType тип операции
	 */
	public void setOperationType(OperationType operationType)
	{
		this.operationType = operationType;
	}

	/**
	 * @return тип канала
	 */
	public ChannelType getChannelType()
	{
		return channelType;
	}

	/**
	 * @param channelType тип канала
	 */
	public void setChannelType(ChannelType channelType)
	{
		this.channelType = channelType;
	}

	/**
	 * @return информация о лимитах
	 */
	public String getLimitsInfo()
	{
		return limitsInfo;
	}

	/**
	 * @param limitsInfo информация о лимитах
	 */
	public void setLimitsInfo(String limitsInfo)
	{
		this.limitsInfo = limitsInfo;
	}

	/**
	 * Найти транзакцию по внешнему идентификатору платежа и дате исполнения
	 * @param documentExternalId внешний идентификатор платежа
	 * @param operationDate дата исполнения
	 * @return транзакция
	 * @throws Exception
	 */
	public static DocumentTransaction find(final String documentExternalId, final Calendar operationDate) throws Exception
	{
		return executeAtomic(new HibernateAction<DocumentTransaction>()
		{
			public DocumentTransaction run(Session session) throws Exception
			{
				return (DocumentTransaction) session.getNamedQuery(DocumentTransaction.class.getName() + ".find")
						.setParameter("document_external_id",   documentExternalId)
						.setParameter("operation_date",         operationDate)
						.uniqueResult();
			}
		});
	}

	/**
	 * Найти все транзакции для канала за последний заданный промежуток
	 * @param profileInfo профиль базы лимитов
	 * @param fromDate дата начала поиска
	 * @param channelType канал, в разрезе которого ищутся лимиты
	 * @return транзакция
	 * @throws Exception
	 */
	public static List<DocumentTransaction> find(final ProfileInfo profileInfo, final Calendar fromDate, final ChannelType channelType) throws Exception
	{
		return executeAtomic(new HibernateAction<List<DocumentTransaction>>()
		{
			public List<DocumentTransaction> run(Session session) throws Exception
			{
				Profile profile = Profile.findByProfileInfo(profileInfo);
				if(profile == null)
					return Collections.emptyList();

				Criteria criteria = session.createCriteria(DocumentTransaction.class);
				criteria.add(Expression.ge("profileId", profile.getId()));
				if(channelType != null)
					criteria.add(Expression.eq("channelType", channelType));
				criteria.add(Expression.ge("operationDate", fromDate));
				//noinspection unchecked
				return criteria.list();
			}
		});
	}

	public BigDecimal getAmountValue()
	{
		return amountValue;
	}

	public void setAmountValue(BigDecimal amountValue)
	{
		this.amountValue = amountValue;
	}

	public String getAmountCurrency()
	{
		return amountCurrency;
	}

	public void setAmountCurrency(String amountCurrency)
	{
		this.amountCurrency = amountCurrency;
	}
}
