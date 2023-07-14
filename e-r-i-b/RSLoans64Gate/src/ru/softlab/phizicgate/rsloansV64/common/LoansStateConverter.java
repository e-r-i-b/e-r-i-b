package ru.softlab.phizicgate.rsloansV64.common;

import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.gate.exceptions.GateException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Maleyev
 * @ created 11.04.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoansStateConverter
{
	private static final Object LOANS_STATE_CONVERTER_LOCKER = new Object();
	private static volatile LoansStateConverter instance = null;
	private static Map<String,State> list = null;

	private String getValue(Element from,String tag)
	{
	  Element element = (Element) from.getElementsByTagName(tag).item(0);
	  if (element!=null)
	  {
	    Text text = (Text) element.getFirstChild();
	    if (text!=null)
	      return text.getNodeValue();
		return element.getAttribute("value");
	  }
	  return "";
	}

	private LoansStateConverter () throws GateException
	{
		try
		{
			list = new HashMap<String,State>();
			Document doc = XmlHelper.loadDocumentFromResource("ru/softlab/phizicgate/rsloansV64/config/LoansState.xml");
			NodeList elements = doc.getElementsByTagName("state");
			int listLength = elements.getLength();
			for (int i = 0; i< listLength; i++)
			{
				String loans = getValue((Element)elements.item(i),"loans");
				String ikflstatus = getValue((Element)elements.item(i),"ikflstatus");
				list.put(loans,new State(ikflstatus,loans));
			}
		}
		catch(IOException e)
		{
			throw new GateException(e);
		}
		catch(SAXException e)
		{
			throw new GateException(e);
		}
		catch(ParserConfigurationException e)
		{
			throw new GateException(e);
		}
	}

	private static LoansStateConverter getInstance() throws GateException
	{
		LoansStateConverter localInstance = instance;
		if (localInstance == null)
		{
			synchronized (LOANS_STATE_CONVERTER_LOCKER)
			{
				if (instance == null)
					instance = new LoansStateConverter();

				localInstance = instance;
			}
		}
		return localInstance;
	}

	/**
	 * Преобразовать статус лоанс в икфл
	 * @param loansState статус лоанс
	 * @return статус икфл
	 */
	public static State getIKFLState(String loansState) throws GateException
	{
		if (getInstance()!=null)
		  return list.get(loansState);
		throw new GateException("Ошибка при обработке статуса: "+loansState);
	}
}
