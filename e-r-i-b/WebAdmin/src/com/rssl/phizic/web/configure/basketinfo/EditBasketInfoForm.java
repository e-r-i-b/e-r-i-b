package com.rssl.phizic.web.configure.basketinfo;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.BasketIdentifierTypeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.dictionaries.basketident.AttributeForBasketIdentType;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessage;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.web.common.FilterActionForm;

import java.util.List;

/**
 * Форма редактирования идентфикаторов корзины
 *
 * @author muhin
 * @ created 30.06.15
 * @ $Author$
 * @ $Revision$
 */

public class EditBasketInfoForm extends FilterActionForm
{
	public static final String DOCUMENT_TYPE = "documentType";
	public static final String MESSAGE_WITH = "messageWith";
	public static final String MESSAGE_WITHOUT = "messageWithout";
	private Long id;

	private String name;
	private String type;
	private String messageWithoutRC = "Документ успешно сохранен. Мы будем автоматически проверять наличие задолженности по ПУ «Штрафы ГИБДД» и показывать счета в разделе Платежи и Переводы. Добавьте «Водительское удостоверение» и мы сможем проверять наличие штрафов, оформленные инспекторами ДПС";
	private String messageWithoutDL = "Документ успешно сохранен. Мы будем автоматически проверять наличие задолженности по ПУ «Штрафы ГИБДД» и показывать счета в разделе Платежи и Переводы. Добавьте «Свидетельство о регистрации транспортного средства» и мы сможем проверять наличие штрафов за неправильную парковку и штрафы, зафиксированные камерами видеофиксации";
	private String messageWithRC = "Документ успешно сохранен. Мы будем автоматически проверять наличие задолженности по ПУ «Штрафы ГИБДД» и показывать счета в разделе Платежи и Переводы";
	private String messageWithDL = "Документ успешно сохранен. Мы будем автоматически проверять наличие задолженности по ПУ «Штрафы ГИБДД» и показывать счета в разделе Платежи и Переводы";
	private String messageWithINN = "Документ успешно сохранен. Мы будем автоматически проверять наличие просроченной задолженности по ПУ «Поиск и оплата налогов ФНС» и показывать счета для оплаты в разделе переводы и платежи.";

	private static final String messageWithRCTittle = "Информационное сообщение в случае, если клиент уже имеет в профиле «ВУ»:";
	private static final String messageWithDLTittle = "Информационное сообщение в случае, если клиент уже имеет в профиле «СТС»:";
	private static final String messageWithoutRCTittle = "Информационное сообщение в случае, если клиент не имеет в профиле «ВУ» и «СТС»:";
	private static final String messageWithoutDLTittle = "Информационное сообщение в случае, если клиент не имеет в профиле «ВУ» и «СТС»:";
	private static final String messageWithINNTittle = "Информационное сообщение в случае, если клиент подтвердил добавление идентификатора «ИНН»:";


	public String getMessageWithRC(){
		return messageWithRC;
	}
	public String getMessageWithDL(){
		return messageWithDL;
	}
	public String getMessageWithINN(){
		return messageWithINN;
	}
	public String getMessageWithoutRC(){
		return messageWithoutRC;
	}
	public String getMessageWithoutDL(){
		return messageWithoutDL;
	}

	public String getMessageWithRCTittle(){
		return messageWithRCTittle;
	}
	public String getMessageWithDLTittle(){
		return messageWithDLTittle;
	}
	public String getMessageWithINNTittle(){
		return messageWithINNTittle;
	}
	public String getMessageWithoutRCTittle(){
		return messageWithoutRCTittle;
	}
	public String getMessageWithoutDLTittle(){
		return messageWithoutDLTittle;
	}

	public void setMessageWithRC(String messageWithRC){
		this.messageWithRC = messageWithRC;
	}
	public void setMessageWithDL(String messageWithDL){
		this.messageWithDL = messageWithDL;
	}
	public void setMessageWithINN(String messageWithINN){
		this.messageWithINN = messageWithINN;
	}
	public void setMessageWithoutRC(String messageWithoutRC){
		this.messageWithoutRC = messageWithoutRC;
	}
	public void setMessageWithoutDL(String messageWithoutDL){
		this.messageWithoutDL = messageWithoutDL;
	}

	private List<String> documentTypes;

	public List<String> getDocumentTypes()
	{
		return documentTypes;
	}

	public void setDocumentTypes(List<String> documentTypes)
	{
		this.documentTypes = documentTypes;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName(MESSAGE_WITH);
		fb.setType(StringType.INSTANCE.getName());

		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName(MESSAGE_WITHOUT);
		fb.setType(StringType.INSTANCE.getName());

		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName(DOCUMENT_TYPE);
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}
