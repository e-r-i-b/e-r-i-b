package com.rssl.phizic.gate.ermb;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.mobilebank.MBKRegistrationResultCode;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.PhoneNumber;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * @author Nady
 * @ created 02.07.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Связка (запрос МБК->ЕРИБ) по изменению параметров услуги "Мобильный Банк" в профиле ЕРМБ
 * Задача «Обработка подключений к МБК в ЕРМБ» (миграция на лету)
 * Связка валидна, если поле wellParsed = true
 */
@PlainOldJavaObject
@SuppressWarnings("PublicField")
public class MBKRegistration implements Serializable
{
	/**
	 * ID связки
	 * Заполнен всегда
	 * Передаётся из МБК
	 */
	private long id;

	/**
	 * Вид связки
	 * Заполнен всегда, если связка валидна
	 * Передаётся из МБК
	 */
	private RegAction regAction;

	/**
	 * Типа транзакции связки
	 * Заполнен всегда, если связка валидна
	 * Передаётся из МБК
	 */
	private RegTranType regTranType;

	/**
	 * Причина, по которой связка определена как подлежащее передаче в ЕРМБ в процессе фильтрации.
	 * Заполнен всегда, если связка валидна
	 * Передаётся из МБК
	 */
	private FiltrationReason filtrationReason;

	/**
	 * Телефон, который регистрируется в регистрации
	 * Заполнен всегда, если связка валидна
	 * Передаётся из МБК
	 */
	private PhoneNumber phoneNumber;

	/**
	 * Идентификатор организации - получателя платежа
	 * Заполнен всегда, если связка валидна и regTranType = MOPS
	 * Передаётся из МБК
	 */
	private String pp;

	/**
	 * Список Идентификаторов платежей для ПП
	 * Может быть заполнен, если связка валидна и regTranType = MOPS
	 * Передаётся из МБК
	 */
	private List<String> ipList;

	/**
	 * Список активных (информационных) карт
	 * Может быть заполнен, если связка валидна и regTranType = MOBI
	 * Передаётся из МБК
	 */
	private List<String> activeCards;

	/**
	 * Отделение, которое инициировало регистрацию
	 * Заполнен всегда, если связка валидна
	 * Передаётся из МБК
	 */
	private Code officeCode;

	/**
	 * Тариф
	 * Тип тарифа (0-без уведомлений, 1 - с уведомлениями)
	 * Заполнен всегда, если связка валидна и regTranType = MOBI
	 * Передаётся из МБК
	 */
	private MbkTariff tariff;

	/**
	 * Номер платёжной карты
	 * Заполнен всегда, если связка валидна
	 * Передаётся из МБК
	 */
	private String paymentCardNumber;

	/**
	 * Данные клиента
	 * Нужны для определения ЕРИБ-блока и для обработки связки в блоке
	 * Вычисляются по платёжной карте связки
	 */
	private UserInfo owner;

	/**
	 * Блок ЕРИБ, в котором обрабатывается связка
	 * Не указан, если связка:
	 * или не валидна
	 * или была обработана ранее
	 * или блок не найден
	 * Вычисляется по owner
	 *
	 * Важно! Поле не передаётся в блок ЕРИБ
	 */
	private transient NodeInfo node;

	/**
	 * Флажок "связка успешно прочитана из резальт-сета" (т.е. валидна)
	 */
	private boolean wellParsed;

	/**
	 * Флажок "МБК принял результаты обработки связки"
	 * Не указан, если результаты не передавались в МБК
	 */
	private Boolean returnedToMBK;

	/**
	 * Флажок "Связка передана в блок ЕРИБ"
	 * Не указан, если связка не передавалась в блок
	 */
	private Boolean passedToNode;

	/**
	 * Код результата обработки
	 * Не указан, если связка не обработана
	 */
	private MBKRegistrationResultCode resultCode;

	/**
	 * Описание ошибки
	 * Не указан, если связка не обработана или связка обработана без ошибок
	 */
	private String errorDescr;

	///////////////////////////////////////////////////////////////////////////

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public RegAction getRegAction()
	{
		return regAction;
	}

	public void setRegAction(RegAction regAction)
	{
		this.regAction = regAction;
	}

	public void setRegAction(String regAction)
	{
		if(regAction == null || regAction.trim().length() == 0)
			return;
		this.regAction = Enum.valueOf(RegAction.class, regAction);
	}

	public RegTranType getRegTranType()
	{
		return regTranType;
	}

	public void setRegTranType(RegTranType regTranType)
	{
		this.regTranType = regTranType;
	}

	public void setRegTranType(String regTranType)
	{
		if(regTranType == null || regTranType.trim().length() == 0)
			return;
		this.regTranType = Enum.valueOf(RegTranType.class, regTranType);
	}

	public FiltrationReason getFiltrationReason()
	{
		return filtrationReason;
	}

	public void setFiltrationReason(FiltrationReason filtrationReason)
	{
		this.filtrationReason = filtrationReason;
	}

	public void setFiltrationReason(String filtrationReason)
	{
		if(filtrationReason == null || filtrationReason.trim().length() == 0)
			return;
		this.filtrationReason = Enum.valueOf(FiltrationReason.class, filtrationReason);
	}

	public PhoneNumber getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getPp()
	{
		return pp;
	}

	public void setPp(String pp)
	{
		this.pp = pp;
	}

	public List<String> getIpList()
	{
		return ipList;
	}

	public void setIpList(List<String> ipList)
	{
		this.ipList = ipList;
	}

	public List<String> getActiveCards()
	{
		return activeCards;
	}

	public void setActiveCards(List<String> activeCards)
	{
		this.activeCards = activeCards;
	}

	public Code getOfficeCode()
	{
		return officeCode;
	}

	public void setOfficeCode(Code officeCode)
	{
		this.officeCode = officeCode;
	}

	public MbkTariff getTariff()
	{
		return tariff;
	}

	public void setTariff(MbkTariff tariff)
	{
		this.tariff = tariff;
	}

	public void setTariff(String tariff)
	{
		if(tariff == null || tariff.trim().length() == 0)
			return;
		this.tariff = Enum.valueOf(MbkTariff.class, tariff);
	}

	public String getPaymentCardNumber()
	{
		return paymentCardNumber;
	}

	public void setPaymentCardNumber(String paymentCardNumber)
	{
		this.paymentCardNumber = paymentCardNumber;
	}

	public UserInfo getOwner()
	{
		return owner;
	}

	public void setOwner(UserInfo owner)
	{
		this.owner = owner;
	}

	public NodeInfo getNode()
	{
		return node;
	}

	public void setNode(NodeInfo node)
	{
		this.node = node;
	}

	public boolean isWellParsed()
	{
		return wellParsed;
	}

	public void setWellParsed(boolean wellParsed)
	{
		this.wellParsed = wellParsed;
	}

	public Boolean getReturnedToMBK()
	{
		return returnedToMBK;
	}

	public void setReturnedToMBK(Boolean returnedToMBK)
	{
		this.returnedToMBK = returnedToMBK;
	}

	public Boolean getPassedToNode()
	{
		return passedToNode;
	}

	public void setPassedToNode(Boolean passedToNode)
	{
		this.passedToNode = passedToNode;
	}

	public MBKRegistrationResultCode getResultCode()
	{
		return resultCode;
	}

	public void setResultCode(MBKRegistrationResultCode resultCode)
	{
		this.resultCode = resultCode;
	}

	public void setResultCode(String resultCode)
	{
		if(resultCode == null || resultCode.trim().length() == 0)
			return;
		this.resultCode = Enum.valueOf(MBKRegistrationResultCode.class, resultCode);
	}

	public String getErrorDescr()
	{
		return errorDescr;
	}

	public void setErrorDescr(String errorDescr)
	{
		this.errorDescr = errorDescr;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("id",                   id)
				.append("regAction",            regAction)
				.append("regTranType",          regTranType)
				.append("filtrationReason",     filtrationReason)
				.append("phoneNumber",          (phoneNumber != null) ? phoneNumber.hideAbonent() : null)
				.append("paymentCardNumber",    MaskUtil.getCutCardNumber(paymentCardNumber))
				.append("officeCode",           officeCode)
				.append("tariff",               tariff)
				.append("pp",                   pp)
				.append("ipList",               ipList)
				.append("activeCards",          (activeCards != null) ? MaskUtil.getCutCardNumber(activeCards) : null)
				.append("owner",                owner)
				.append("node",                 (node != null) ? node.getName() : null)
				.append("wellParsed",           wellParsed)
				.append("returnedToMBK",        returnedToMBK)
				.append("passedToNode",         passedToNode)
				.append("resultCode",           resultCode)
				.append("errorDescr",           errorDescr)
				.toString();
	}
}
