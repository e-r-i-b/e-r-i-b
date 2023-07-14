package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.documents.templates.service.filters.ChannelActivityTemplateFilter;
import com.rssl.phizic.business.documents.templates.service.filters.ReadyToPaymentTemplateFilter;
import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.business.ermb.sms.config.SmsConfig;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.config.ConfigFactory;

import java.math.BigDecimal;
import java.util.Set;

/**
 * ������ ��� ���-������� "������ ����� (�������)"
 * @author Rtischeva
 * @ created 18.09.13
 * @ $Author$
 * @ $Revision$
 */
public class TemplatePaymentCommandParser extends CommandParserBase
{
	private final TemplateDocumentService templateService = TemplateDocumentService.getInstance();

	///////////////////////////////////////////////////////////////////////////

	public Command parseCommand()
	{
		// ������_��_������� = ������
		//                   | ������ ����������� �����
		//                   | ������ ����������� ����� ����������� �����

		Command command = parseTemplatePaymentCommandV1();

		if (command == null)
			command = parseTemplatePaymentCommandV2();

		if (command == null)
			command = parseTemplatePaymentCommandV3();

		return command;
	}

	private Command parseTemplatePaymentCommandV1()
	{
		// ������_��_������� = ������

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		TemplateDocument template = parseTemplate();
		if (template != null && parseEOF())
		{
			// SUCCESS. ������ ������ ��� ����������
			completeLexeme(tx);
			return commandFactory.createTemplatePaymentCommand(template, null, null);
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private Command parseTemplatePaymentCommandV2()
	{
		// ������_��_������� = ������ ����������� �����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		TemplateDocument template = parseTemplate();
		if (template != null && parseDelim())
		{
			BigDecimal amount = parseAmount();
			if (amount != null && parseEOF())
			{
				// SUCCESS. ������ ������ � �����
				completeLexeme(tx);
				return commandFactory.createTemplatePaymentCommand(template, amount, null);
			}

			// FAIL-B. �� ������� ��� ����������� ������� �����
			addError(messageBuilder.buildCommandFormatError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private Command parseTemplatePaymentCommandV3()
	{
		// ������_��_������� = ������ ����������� ����� ����������� �����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		TemplateDocument template = parseTemplate();
		if (template != null && parseDelim())
		{
			BigDecimal amount = parseAmount();
			if (amount != null && parseDelim())
			{
				String card = parseProduct();
				if (card != null && parseEOF())
				{
					// SUCCESS. ������ ������, ����� � �����
					completeLexeme(tx);
					return commandFactory.createTemplatePaymentCommand(template, amount, card);
				}

				// FAIL-C. �� ������� ��� ����������� ������� ����� ���� �� ������ ��� �����-�� �����
				addError(messageBuilder.buildServicePaymentBadCardError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. �� ������� ��� ����������� ������� �����
			addError(messageBuilder.buildCommandFormatError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private TemplateDocument parseTemplate()
	{
		// ������ = �����

		ParseTransaction tx = beginLexeme(Lexeme.RECIPIENT);

		//��� ������� ����� ���� ��������� (�� �����), ���������� ������ ������� � ���-������ ����������.
		String template = parseAlias();
		if (template != null)
		{
			TemplateDocument templateDocument = findTemplateDocument(template);
			if (templateDocument != null)
			{
				completeLexeme(tx);
				return templateDocument;
			}
		}

		cancelLexeme(tx);
		return null;
	}

	private TemplateDocument findTemplateDocument(String name)
	{
		try
		{
			TemplateDocument template = templateService.findByTemplateName(new ClientImpl(getPerson()), name);
			if (template == null)
				return null;

			if (!(new ChannelActivityTemplateFilter(CreationType.sms).accept(template)))
			{
				log.error("���-������� ������ �� �������. ������ �  id " + template.getId() + " ���������� � ���-������");
				return null;
			}

			if (!(new ReadyToPaymentTemplateFilter().accept(template)))
			{
				log.error("���-������� ������ �� �������. ������ ������� �  id " + template.getId() + " �� �������� ��� ������ � ���-������. activityCode: " + template.getTemplateInfo().getState().getCode() +
							", stateCode: " + template.getState().getCode());
				return null;
			}

			Set<String> smsCommandAliases = ConfigFactory.getConfig(SmsConfig.class).getAllCommandAliases();
			if (smsCommandAliases.contains(name.toUpperCase()))
			{
				return null;
			}
			return template;
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new UserErrorException(e);
		}
	}

}
