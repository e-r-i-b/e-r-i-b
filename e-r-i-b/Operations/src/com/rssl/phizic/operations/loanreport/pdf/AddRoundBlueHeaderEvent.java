package com.rssl.phizic.operations.loanreport.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.pdf.*;
import com.rssl.phizic.utils.pdf.event.HeaderFooterBase;

import java.awt.*;

/**
 * @author akrenev
 * @ created 12.10.2012
 * @ $Author$
 * @ $Revision$
 *
 * ќбработчик событи€, добавл€ющий текст слева (внизу) страницы текст
 */

public class AddRoundBlueHeaderEvent extends HeaderFooterBase
{
	//признак дл€ определени€  срабатывани€;
	private static String TRIG_KEY = "BLUE_HEADER";
	private Color color; //цвет фигур
	private float radius; //радиус закруглени€
	private String text;

	public AddRoundBlueHeaderEvent(FontStyle fontStyle, Color color, float radius)
	{
		this.color = color;
		this.radius = radius;
		setFontStyle(fontStyle);
	}

	@Override protected boolean needAddFooter(PdfWriter writer)
	{
		//ищем в объекте PdfWriter -> PdfDictionary запись с ключем  "Keywords"
		//в котором записываетс€ текстовка дл€ футера передаваема€ в процессе построени€ pdf, дл€ каждой страницы.
		PdfObject obj = writer.getInfo().get(new PdfName("Keywords"));
		if (obj != null)
		{
			//разбиваем строку по разделителю  "%"
			String[] text =  obj.toString().split("%");
			//если получилось 2 строки и перва€ = "BLUE_HEADER", значит это то что нам нужно
			if (text.length == 2 && StringHelper.equals(text[0], TRIG_KEY))
			{
				//заполн€ем футер переданм сообщением.
				this.text = text[1];
				return true;
			}
		}
		return false;
	}

	@Override protected void doEndPage(PdfWriter writer, Document document)
	{

		new Phrase(text, getFontWithStyle());

		PdfContentByte cb = writer.getDirectContent();
		Rectangle position = document.getPageSize();
		PdfGState state = new PdfGState();
		state.setFillOpacity(1);
		cb.setGState(state);
		cb.setColorStroke(new BaseColor(color));
		cb.setLineWidth(radius * 2);
		int size =  text.length() * 2 + 150;
		cb.circle(position.getLeft() + size + radius, position.getTop() - 42+ radius, radius);
		cb.stroke();
		cb.rectangle(position.getLeft(), position.getTop() - 42 , size, radius * 2);
		cb.stroke();

		Rectangle rect = document.getPageSize();
		ColumnText.showTextAligned(writer.getDirectContent(),  1,  new Phrase(text, getFontWithStyle()) , rect.getLeft() + 150, rect.getTop() - 40, 0);
	}

	public int getRecordCount()
	{
		return getPageNumber();
	}
}
