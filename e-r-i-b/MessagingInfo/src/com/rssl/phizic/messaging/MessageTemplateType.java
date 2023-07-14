package com.rssl.phizic.messaging;

/**
 * “ип шаблона сообщени€
 * @author Rtischeva
 * @created 18.07.2013
 * @ $Author$
 * @ $Revision$
 */
public enum MessageTemplateType
{
	INFORM,//информирующее смс-сообщение

	CONFIRM,//смс-сообщение с подтверждением

	REFUSE, //сообщение, информирующее об отказе в совершении операции
	BLOCK,  //сообщение, информирующее о блокировке
	REVIEW, //сообщение, информирующее о переподтверждении
	DENY,   //сообщение, информирующее о запрете
}
