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
 * Базовый класс фоновых задач
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
	 * @return идентификатор создателя задачи
	 */
	public Long getOwnerId()
	{
		return ownerId;
	}

	/**
	 * @param ownerId идентификатор создателя задачи
	 */
	public void setOwnerId(Long ownerId)
	{
		this.ownerId = ownerId;
	}

	/**
	 * @return ФИО создателя задачи
	 */
	public String getOwnerFIO()
	{
		return ownerFIO;
	}

	/**
	 * задать ФИО создателя задачи
	 * @param ownerFIO ФИО
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
	 * Выполняет установу и перевод задачи в статус исполнено.
	 * Очистку наследники обязаны реализовываться с пополью переопределения метода.
	 * @param result результат исполения задачи не может быть null
	 */
	public void executed(R result)
	{
		if (state != TaskState.PROCESSING)
		{
			throw new IllegalStateException("Некорректный статус задачи " + state);
		}
		setState(TaskState.PROCESSED);
		setResult(result);
	}

	/**
	 * @return представление специфичных свойств задачи в виде байтового массива(для сохранения в базе)
	 * @throws BusinessException
	 */
	public byte[] getInternalProperties() throws BusinessException
	{
		return properties;
	}
	
	/**
	 * Инициализиация специфичных свойств из байтового массива(при ициализации из базы)
	 * @param properties свойства в виде байтового массива
	 * @throws BusinessException
	 */
	public void setInternalProperties(byte[] properties) throws BusinessException
	{
		this.properties = properties;
	}
}
