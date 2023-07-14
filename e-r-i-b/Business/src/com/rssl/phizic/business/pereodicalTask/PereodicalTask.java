package com.rssl.phizic.business.pereodicalTask;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.background.BackgroundTaskBase;
import com.rssl.phizic.business.operations.background.TaskState;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

/**
 * User: Moshenko
 * Date: 01.11.2011
 * Time: 15:44:00
 * Задание для периодического  выполнения 
 */
public class PereodicalTask
{
	private Long id;
	private CommonLogin owner;
	private Calendar creationDate = Calendar.getInstance();
	private TaskState state = TaskState.NEW;
	private byte[] properties;
	private PereodicalTaskResult result;
	private String triggerName;     //Имя триггера
	private String operationName;   //Имя операции для таска(полное)
	private String cronExp;         //Крон выражение, для триггеров
	private Long timeInterval;      //Время интервала запуска в часах


	public Long getId()
	{
		return id;
	}

	public CommonLogin getOwner()
	{
		return owner;
	}

	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public TaskState getState()
	{
		return state;
	}

	public PereodicalTaskResult getResult()
	{
		return result;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setOwner(CommonLogin owner)
	{
		this.owner = owner;
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

	public void setResult(PereodicalTaskResult result)
	{
		this.result = result;
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

	public String getOperationClassName()
	{
		return operationName;
	}

	public String getTriggerName()
	{
		return triggerName;
	}

	public void setTriggerName(String triggerName)
	{
		this.triggerName = triggerName;
	}

	public String getOperationName()
	{
		return operationName;
	}

	public void setOperationName(String operationName)
	{
		this.operationName = operationName;
	}

	public String getCronExp()
	{
		return cronExp;
	}

	public void setCronExp(String cronExp)
	{
		this.cronExp = cronExp;
	}

	public Long getTimeInterval()
	{
		return timeInterval;
	}

	public void setTimeInterval(Long timeInterval)
	{
		this.timeInterval = timeInterval;
	}

	public void executed(PereodicalTaskResult result)
	{
		setState(TaskState.PERIODICAL);
		setResult(result);
	}
}
