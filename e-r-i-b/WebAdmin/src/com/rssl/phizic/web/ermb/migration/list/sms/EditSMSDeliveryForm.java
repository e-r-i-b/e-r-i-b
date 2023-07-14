package com.rssl.phizic.web.ermb.migration.list.sms;


import com.rssl.phizic.business.ermb.migration.list.Segment;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Arrays;
import java.util.List;

/**
 * @author Nady
 * @ created 10.12.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Форма создания СМС-рассылки в АС Мигратор
 */
public class EditSMSDeliveryForm extends EditFormBase
{
	public static final String NUM_CLIENTS_MESSAGE_TEMPlATE = "Клиентов для отправки сообщения: %s";
	private List<Segment> data = Arrays.asList(Segment.values());
	private String[] sendsSegments = new String[]{};
	private String[] banSendsSegments = new String[]{};
	private String text;

	/**
	 * @return сегменты для отправки смс
	 */
	public String[] getSendsSegments()
	{
		return sendsSegments;
	}

	/**
	 *  Установить сегменты для отправки смс
	 * @param sendsSegments сегменты для отправки
	 */
	public void setSendsSegments(String[] sendsSegments)
	{
		this.sendsSegments = sendsSegments;
	}

	/**
	 * @return выделенные сегменты для запрещения отправки смс
	 */
	public String[] getBanSendsSegments()
	{
		return banSendsSegments;
	}

	/**
	 *  Установить сегменты для запрещения отправки смс
	 * @param banSendsSegments - сегменты для запрещения отправки смс
	 */
	public void setBanSendsSegments(String[] banSendsSegments)
	{
		this.banSendsSegments = banSendsSegments;
	}

	/**
	 * Получить список всех сегментов
	 * @return список всех сегментов
	 */
	public List getData()
	{
		return data;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}


}
