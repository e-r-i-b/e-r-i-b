package com.rssl.phizic.business.operations.background;

import com.rssl.phizic.business.BusinessException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

/**
 * @author krenev
 * @ created 18.08.2011
 * @ $Author$
 * @ $Revision$
 * ������� ����� ������� �����
 */
public abstract class BackgroundTaskBase<R extends TaskResult> implements BackgroundTask<R>
{
	private Long id;
	private Long ownerId;
	private String ownerFIO;
	private Calendar creationDate = Calendar.getInstance();
	private TaskState state = TaskState.NEW;
	private byte[] properties;
	private R result;

	public Long getId()
	{
		return id;
	}

	/**
	 * @return ������������� ��������� ������
	 */
	public Long getOwnerId()
	{
		return ownerId;
	}

	/**
	 * @param ownerId ������������� ��������� ������
	 */
	public void setOwnerId(Long ownerId)
	{
		this.ownerId = ownerId;
	}

	/**
	 * @return ��� ��������� ������
	 */
	public String getOwnerFIO()
	{
		return ownerFIO;
	}

	/**
	 * ������ ��� ��������� ������
	 * @param ownerFIO ���
	 */
	public void setOwnerFIO(String ownerFIO)
	{
		this.ownerFIO = ownerFIO;
	}

	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public TaskState getState()
	{
		return state;
	}

	public R getResult()
	{
		return result;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	public void setState(TaskState state)
	{
		this.state = state;
	}

	public void setProperties(Properties properties) throws BusinessException
	{
		try
		{
			if(properties == null)
			{
				this.properties = null;
			}
			else
			{
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				properties.storeToXML(os, null);

				this.properties = os.toByteArray();
			}
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public Properties getProperties() throws BusinessException
	{
		try
		{
		    Properties prors = new Properties();

			if(properties != null)
				prors.loadFromXML(new ByteArrayInputStream(properties));

			return prors;

		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	public void setResult(R result)
	{
		this.result = result;
	}

	/**
	 * ��������� �������� � ������� ������ � ������ ���������.
	 * ������� ���������� ������� ��������������� � ������� ��������������� ������.
	 * @param result ��������� ��������� ������ �� ����� ���� null
	 */
	public void executed(R result)
	{
		if (state != TaskState.PROCESSING)
		{
			throw new IllegalStateException("������������ ������ ������ " + state);
		}
		setState(TaskState.PROCESSED);
		setResult(result);
	}

	/**
	 * @return ������������� ����������� ������� ������ � ���� ��������� �������(��� ���������� � ����)
	 * @throws BusinessException
	 */
	public byte[] getInternalProperties() throws BusinessException
	{
		return properties;
	}
	
	/**
	 * �������������� ����������� ������� �� ��������� �������(��� ����������� �� ����)
	 * @param properties �������� � ���� ��������� �������
	 * @throws BusinessException
	 */
	public void setInternalProperties(byte[] properties) throws BusinessException
	{
		this.properties = properties;
	}
}
