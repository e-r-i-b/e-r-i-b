package com.rssl.phizic.gate.depomobilebank;

/**
 * User: Moshenko
 * Date: 12.09.13
 * Time: 14:17
 * Кода оветов MBV
 */
public enum MBVResultEnum
{
    SUCCESS("000_Success"),                 //Успех
    PARAMETER_NOT_FIND("001_No_Parameter"), //Не найден запрашиваемый параметр
    NO_CONNECTION("002_No_Connect"),        //Нет связи с одним из узлов системы
    OBJECT_READY("003_Already_Done"),       //Запрашиваемый к изменению объект уже находится в нужном статусе;
    PROGRAM_ERROR("004_Exception");         //Код программной ошибки;

    private String returnCode;

    MBVResultEnum(String returnCode)
    {
        this.returnCode = returnCode;
    }

    public String toValue()
    {
        return returnCode;
    }

    public String getValue()
    {
        return returnCode;
    }

	public static MBVResultEnum fromValue(String value)
	{
		for(MBVResultEnum code : values())
			if(code.getValue().equals(value))
				return code;

		throw new IllegalArgumentException("Неизвестный код ответа МБВ[" + value + "]");
	}
}
