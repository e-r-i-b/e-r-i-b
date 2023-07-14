package com.rssl.phizic.business.fund.initiator;

import com.rssl.phizic.common.types.fund.ClosedReasonType;
import com.rssl.phizic.common.types.fund.Constants;
import com.rssl.phizic.common.types.fund.FundRequestState;
import com.rssl.phizic.gate.fund.Request;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author osminin
 * @ created 15.09.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� - ������ �� ���� �������
 */
public class FundRequest implements Request
{
	private Long id;
	private String externalId;
	private long loginId;
	private FundRequestState state;
	private BigDecimal requiredSum;
	private BigDecimal reccomendSum;
	private String message;
	private String resource;
	private Calendar expectedClosedDate;
	private Calendar closedDate;
	private ClosedReasonType closedReason;
	private Calendar createdDate;
	private int sendersCount;
	private FundRequestState viewState;
	private Calendar viewClosedDate;
	private ClosedReasonType viewClosedReason;
	private String initiatorPhones;

	/**
	 * @return ������������� �������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id ������������� �������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * ������� �� ������ ����� ����������, ����������� '@' � ����������� ��������������
	 * @return ������� ������������� �������
	 */
	public String getExternalId()
	{
		return externalId;
	}

	/**
	 * @param externalId ������� ������������� �������
	 */
	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	/**
	 * @return �����
	 */
	public long getLoginId()
	{
		return loginId;
	}

	/**
	 * @param loginId �����
	 */
	public void setLoginId(long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return ������ �������
	 */
	public FundRequestState getState()
	{
		return state;
	}

	/**
	 * @param state ������ �������
	 */
	public void setState(FundRequestState state)
	{
		this.state = state;
	}

	/**
	 * @return ��������� �����
	 */
	public BigDecimal getRequiredSum()
	{
		return requiredSum;
	}

	/**
	 * @param requiredSum ��������� �����
	 */
	public void setRequiredSum(BigDecimal requiredSum)
	{
		this.requiredSum = requiredSum;
	}

	/**
	 * @return ��������������� �����
	 */
	public BigDecimal getReccomendSum()
	{
		return reccomendSum;
	}

	/**
	 * @param reccomendSum ��������������� �����
	 */
	public void setReccomendSum(BigDecimal reccomendSum)
	{
		this.reccomendSum = reccomendSum;
	}

	/**
	 * @return ��������� ������������
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message ��������� ������������
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * @return ������ ��������
	 */
	public String getResource()
	{
		return resource;
	}

	/**
	 * @param resource ������ ��������
	 */
	public void setResource(String resource)
	{
		this.resource = resource;
	}

	/**
	 * @return ��������� ���� �������� �������
	 */
	public Calendar getExpectedClosedDate()
	{
		return expectedClosedDate;
	}

	/**
	 * @param expectedClosedDate ��������� ���� �������� �������
	 */
	public void setExpectedClosedDate(Calendar expectedClosedDate)
	{
		this.expectedClosedDate = expectedClosedDate;
	}

	/**
	 * @return ���� ��������
	 */
	public Calendar getClosedDate()
	{
		return closedDate;
	}

	/**
	 * @param closedDate ���� ��������
	 */
	public void setClosedDate(Calendar closedDate)
	{
		this.closedDate = closedDate;
	}

	/**
	 * @return ������� �������� �������
	 */
	public ClosedReasonType getClosedReason()
	{
		return closedReason;
	}

	/**
	 * @param closedReason ������� �������� �������
	 */
	public void setClosedReason(ClosedReasonType closedReason)
	{
		this.closedReason = closedReason;
	}

	/**
	 * @return ���� ��������
	 */
	public Calendar getCreatedDate()
	{
		return createdDate;
	}

	/**
	 * @param createdDate ���� ��������
	 */
	public void setCreatedDate(Calendar createdDate)
	{
		this.createdDate = createdDate;
	}

	/**
	 * @return ���������� ����������
	 */
	public int getSendersCount()
	{
		return sendersCount;
	}

	/**
	 * @param sendersCount ���������� ����������
	 */
	public void setSendersCount(int sendersCount)
	{
		this.sendersCount = sendersCount;
	}

	/**
	 * @return ��������� ������� ������ �������
	 */
	public FundRequestState getViewState()
	{
		return viewState;
	}

	/**
	 * @param viewState ��������� ������� ������ �������
	 */
	public void setViewState(FundRequestState viewState)
	{
		this.viewState = viewState;
	}

	/**
	 * @return ��������� ������� ���� ��������
	 */
	public Calendar getViewClosedDate()
	{
		return viewClosedDate;
	}

	/**
	 * @param viewClosedDate ��������� ������� ���� ��������
	 */
	public void setViewClosedDate(Calendar viewClosedDate)
	{
		this.viewClosedDate = viewClosedDate;
	}

	/**
	 * @return ��������� ������� ������� ��������
	 */
	public ClosedReasonType getViewClosedReason()
	{
		return viewClosedReason;
	}

	/**
	 * @param viewClosedReason ��������� ������� ������� ��������
	 */
	public void setViewClosedReason(ClosedReasonType viewClosedReason)
	{
		this.viewClosedReason = viewClosedReason;
	}

	/**
	 * ��������� ������� ��������� ����������, ���������� ����� �������
	 * @return ������, ���������� ������ ��������� ����� �������
	 */
	public String getInitiatorPhones()
	{
		return initiatorPhones;
	}

	/**
	 * ������������� ������� ������� ����������. �����! ������ ������ ���� ��������� ��������
	 * @param initiatorPhones ������ ���������, ���������� ��������.
	 */
	public void setInitiatorPhones(String initiatorPhones)
	{
		this.initiatorPhones = initiatorPhones;
	}

	/**
	 * @return ������ ��������� ����������
	 */
	public List<String> getInitiatorPhonesAsList()
	{
		if (StringHelper.isEmpty(initiatorPhones))
		{
			return Collections.emptyList();
		}
		return Arrays.asList(initiatorPhones.split(Constants.INITIATOR_PHONES_DELIMITER));
	}
}
