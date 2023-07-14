package com.rssl.common.forms.doc;

import com.rssl.common.forms.state.FieldValueChange;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.utils.StringHelper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Balovtsev
 * Date: 27.12.2011
 * Time: 11:33:56
 */
public class StateMachineEvent
{
	private final Map<String, FieldValueChange> fieldValueChanges = new HashMap<String, FieldValueChange>();
	private MessageCollector messageCollector = new MessageCollector();
	private String nextState = null; //ожидаемое следующее состояние платежа, после выполнения хендлеров
	private String errorCode;       //код ошибки, связанный с событием

/**
	 *
	 * Регистрация изменения значения поля с подписью к этому полю.
	 *
	 * @param fieldName - название поля
	 * @param newValue - новое значение
	 * @param oldValue - старое значение
	 * @param fieldMessage - сообщение, которое будет отображаться в поле
	 */
	public void registerChangedField(String fieldName, Object newValue, Object oldValue, String fieldMessage)
	{
		if (StringHelper.isEmpty(fieldName))
		{
			throw new NullPointerException("Поле fieldName должно быть задано");
		}

		fieldValueChanges.put(fieldName, new FieldValueChange(fieldName, newValue, oldValue, fieldMessage));
	}

	/**
	 *
	 * Регистрация изменения значения поля.
	 *
	 * @param fieldName имя поля
	 * @param newValue  старое значение
	 * @param oldValue  новое значение
	 */
	public void registerChangedField(String fieldName, Object newValue, Object oldValue)
	{
		registerChangedField(fieldName, newValue, oldValue, null);
	}

	/**
	 *
	 * Регистрация изменения значения поля.
	 *
	 * @param fieldName имя поля
	 */
	public void registerChangedField(String fieldName)
	{
		registerChangedField(fieldName, null, null, null);
	}

	/**
	 *
	 * Проверка на наличие измененных полей
	 *
	 * @return boolean
	 */
	public boolean hasChangedFields()
	{
		return !fieldValueChanges.isEmpty();
	}

	/**
	 *
	 * Возвращает FieldValueChange по названию поля.
	 *
	 * @param fieldName название поля
	 * @return FieldValueChange
	 */
	public FieldValueChange getChangedField(String fieldName)
	{
		if (StringHelper.isEmpty(fieldName))
		{
			throw new NullPointerException("Поле fieldName должно быть задано");
		}

		return fieldValueChanges.get(fieldName);
	}

	/**
	 *
	 * Проверяет, были ли изменения поля с названием fieldName
	 *
	 * @param fieldName название поля
	 * @return boolean
	 */
	public boolean hasChangedField(String fieldName)
	{
		return fieldValueChanges.containsKey(fieldName);
	}

	/**
	 *
	 * Добавить сообщение которое необходимо отобразить пользователю
	 *
	 * @param messageCollector хранит информационные сообщения и сообщения об ошибках
	 */
	public void setMessageCollector(MessageCollector messageCollector)
	{
		this.messageCollector = messageCollector;
	}

	/**
	 *
	 * Возвращает объект хранящий информационные сообщения и сообщения об ошибках
	 *
	 * @return MessageCollector
	 */
	public MessageCollector getMessageCollector()
	{
		return messageCollector;
	}

	/**
	 *
	 * Добавить информационное сообщение.
	 * Если сообщение пустое, то оно не добавляется.
	 *
	 * @param message сообщение
	 */
	public void addMessage(String message)
	{
		if (StringHelper.isEmpty(message))
		{
			return;
		}

		messageCollector.addMessage(message);
	}

	/**
	 * Добавить сообщение об ошибке
	 * @param errorMsg - сообщение об ошибке
	 */
	public void addErrorMessage(String errorMsg)
	{
		if (StringHelper.isEmpty(errorMsg))
		{
			return;
		}

		messageCollector.addError(errorMsg);
	}

	/**
	 *
	 * Возвращает набор объектов FieldValueChange содержащих изменившиеся поля и их значения
	 *
	 * @return Collection&lt;FieldValueChange&gt;
	 */
	public Collection<FieldValueChange> getChangedFields()
	{
		return fieldValueChanges.values();
	}

    /**
     * Очищает зарегистрированные изменившиеся поля
     */
    public void clearChangedFields()
    {
        fieldValueChanges.clear();
    }

	public String getNextState()
	{
		return nextState;
	}

	public void setNextState(String nextState)
	{
		this.nextState = nextState;
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}
}
