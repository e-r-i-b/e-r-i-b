package com.rssl.phizic.business.smsbanking;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.xslt.FormDataBuilder;
import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.DepositLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.smsbanking.pseudonyms.Pseudonym;
import com.rssl.phizic.business.smsbanking.pseudonyms.PseudonymService;
import com.rssl.phizic.business.smsbanking.resolver.AccountResolver;
import com.rssl.phizic.business.smsbanking.resolver.CardResolver;
import com.rssl.phizic.business.smsbanking.resolver.DepositResolver;
import com.rssl.phizic.business.smsbanking.resolver.PseudonymResolver;
import com.rssl.phizic.context.PersonContext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

/**
 * @author gladishev
 * @ created 07.10.2008
 * @ $Author$
 * @ $Revision$
 */

public class SmsBankingService
{
	private PseudonymService pseudonymService = new PseudonymService();
	public  final static String openingMarker = "<#";
	public  final static String closingMarker = "#>";

	private static final Map<Class, PseudonymResolver> linksMap = new HashMap<Class, PseudonymResolver>();

	static
	{
		linksMap.put(AccountLink.class, new AccountResolver());
		linksMap.put(CardLink.class,    new CardResolver());
		linksMap.put(DepositLink.class, new DepositResolver());
	}

	/**
	 * Возвращает ExternalResourceLink по псевдониму
	 * @param login - клиент
	 * @param pseudonym - псевдоним
	 * @return ExternalResourceLink
	 */
	public ExternalResourceLink getResource(CommonLogin login, String pseudonym, Class<? extends ExternalResourceLink> clazz) throws BusinessException, BusinessLogicException
	{
		Pseudonym pseudo = pseudonymService.findByPseudonymName(login, pseudonym);
		if (pseudo == null)
		{
			pseudonymService.synchronize(login); // если не нашли псевдоним, то делаем синхронизацию
			pseudo = pseudonymService.findByPseudonymName(login, pseudonym);
		}
		if (pseudo == null)
		{
			return null;
		}
		return linksMap.get(clazz).getLink(pseudo);
	}

	/**
	 * Возвращает список незаполненных полей шаблона платежа
	 * в том порядке, в котором они расположены на экране.
	 * @param document - платеж
	 * @return - список незаполненных полей
	 */
	public List<Field> getSmsCommandFields(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		Metadata metadata = MetadataCache.getExtendedMetadata(document);

		FieldValuesSource source = new DocumentFieldValuesSource(metadata, document);
		Map<String, Field> emptyFields = getEmptyFields(metadata.getForm().getFields(), source);

		List<Field> result = new ArrayList<Field>();
		List<String> htmlFields = getFieldsFromHtml(createPaymentHtml(metadata, source));
		for (String fieldName : htmlFields)
		{
			Field field = emptyFields.get(fieldName);
			if (field != null)
			{
				result.add(field);
			}
		}

		return result;
	}

	/**
	 * Возвращает количество необходимых параметров
	 * @param document - платеж
	 * @return - количество параметров для заполнения шаблона
	 */
	public int getSmsCommandFieldsCount(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		Metadata metadata = MetadataCache.getExtendedMetadata(document);
		FieldValuesSource source = new DocumentFieldValuesSource(metadata, document);

		List<Field> formFields = metadata.getForm().getFields();
		Map<String, Field> emptyFields = getEmptyFields(formFields, source);

		List<String> htmlFields = getFieldsFromHtml(createPaymentHtml(metadata, source));
		int cnt = 0;
		for (String fieldName : htmlFields)
		{
			Field field = emptyFields.get(fieldName);
			if (field != null)
			{
				cnt++;
			}
		}

		// количество маркеров в поле ground
		for (Field field : formFields)
		{
			if ("ground".equals(field.getName()))
			{
				String value = source.getValue("ground");
				try
				{
					cnt += checkMarkers(value);
				}
				catch (Exception e)
				{
					// ничего делать не надо, т.к. мы используем уже сохраненный шаблон.. и.. по идее.. в нем нет ошибок
				}
				break;
			}
		}
		return cnt;
	}

	private Map<String, Field> getEmptyFields(List<Field> formFields, FieldValuesSource source)
	{
		Map<String, Field> emptyFields = new HashMap<String, Field>();
		for(Field field : formFields)
		{
			String fieldName = field.getName();
			String value = source.getValue(fieldName);
			if ((value == null || value.equals("")) && mandatory(field, formFields, source) && field.getValueExpression() == null)
			{
				emptyFields.put(fieldName, field);
			}
		}
		return emptyFields;
	}

	private boolean mandatory(Field field, List<Field> formFields, FieldValuesSource source)
	{
		for (FieldValidator validator : field.getValidators())
		{
			if (validator instanceof RequiredFieldValidator)
			{
				Expression expression = validator.getEnabledExpression();
				if (expression != null)
				{
					Map <String, Object> map = new HashMap<String, Object>();
					for (Field f: formFields)
					{
						map.put(f.getName(), source.getValue(f.getName()));
					}
					return (Boolean) expression.evaluate(map);
				}
				return true;
			}
		}
		return false;
	}

	private String createPaymentHtml(Metadata metadata, FieldValuesSource source)
	{
		FormDataBuilder builder = new FormDataBuilder();
		builder.appentAllFields(metadata.getForm(), source);
		XmlConverter converter = metadata.createConverter("html");
		converter.setData(builder.getFormData());
		converter.setParameter("mode"           , "edit");
		converter.setParameter("personAvailable", PersonContext.isAvailable());
		return converter.convert().toString();
	}

	private List<String> getFieldsFromHtml(String html) throws BusinessException
	{
		try
		{
			List<String> result = new ArrayList<String>();
			HTMLEditorKit kit = new HTMLEditorKit();
			HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
			doc.putProperty("IgnoreCharsetDirective",Boolean.TRUE);
			InputStream is = new ByteArrayInputStream(html.getBytes());
			kit.read(is, doc, 0);
			ElementIterator ei = new ElementIterator(doc);
			Element element;
			while ((element = ei.next()) != null)
			{
				String elementName = element.getName();
				if (!elementName.equalsIgnoreCase(HTML.Tag.INPUT.toString()) &&
					!elementName.equalsIgnoreCase(HTML.Tag.SELECT.toString()))
					continue;

				AttributeSet attrs = element.getAttributes();
				String id = (String) attrs.getAttribute(HTML.Attribute.ID);
				if (id != null)
					result.add(id);
			}
			return result;
		}
        catch (BadLocationException e)
        {
            throw new BusinessException(e);
        }
        catch (IOException e)
        {
            throw new BusinessException(e);
        }
	}

	public String getTemplateGround(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		Metadata metadata = MetadataCache.getExtendedMetadata(document);
		FieldValuesSource source = new DocumentFieldValuesSource(metadata, document);

		List<Field> formFields = metadata.getForm().getFields();
		for(Field field : formFields)
		{
			if ("ground".equals(field.getName()))
			{
				return source.getValue("ground");
			}
		}
		return null;
	}

	private String parseGround(String ground)
	{
		if (ground == null || ground.length() == 0)
		{
			return null;
		}
		StringBuffer result = new StringBuffer();
		int startIndex = ground.indexOf(openingMarker);
		int endIndex = ground.indexOf(closingMarker);
		while (startIndex != -1 && endIndex != -1)
		{
			String name = ground.substring(startIndex+2, endIndex);

			appendAtom(result, name);
			result.append(" ");

			startIndex = ground.indexOf(openingMarker, endIndex);
			endIndex = ground.indexOf(closingMarker, endIndex+1);
		}
		return result.toString();
	}

	private void appendAtom(StringBuffer result, String parmeter)
	{
		result.append("<");
		result.append(parmeter);
		result.append(">");
	}

	/**
	 * проверяет корректность заполнения маркерами
	 * @param ground - назначение платежа, в котором, возможно, есть маркеры
	 * @return количество дополнительных параметров и поле "Назначение платежа"
	 * @throws Exception
	 */
	public static int checkMarkers(String ground) throws Exception
	{
		if (ground == null || ground.length() == 0)
		{
			return 0;
		}
		int startIndex = ground.indexOf(openingMarker);
		int endIndex = ground.indexOf(closingMarker);
		int cnt = 0;
		while (startIndex != -1 && endIndex != -1)
		{
			startIndex = ground.indexOf(openingMarker, startIndex+1);
			if (startIndex != -1 && startIndex < endIndex)
			{
				// новый открывающий маркер расположен внутри пары предыдущих маркеров
				throw new Exception("Некорректно расставлены маркеры");
			}
			endIndex = ground.indexOf(closingMarker, endIndex+1);
			if (endIndex != -1 && startIndex > endIndex)
			{
				// ледующий открывающий маркер расположен после закрывающего
				throw new Exception("Некорректно расставлены маркеры");
			}
			cnt++;
		}
		if (startIndex == -1 && endIndex == -1)
		{
			return cnt;
		}
		throw new Exception("Есть непарные маркеры.");
	}
}
