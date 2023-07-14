package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.XmlReplicaSourceBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.files.FileHelper;
import com.rssl.phizic.utils.xml.EmptyResolver;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;

/**
 * User: Omeliyanchuk
 * Date: 28.08.2006
 * Time: 13:30:48
 */
public class ResidentBankReplicaSourceSAX extends XmlReplicaSourceBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String DEFAULT_FILE_NAME = "banks.xml";
	public List<ResidentBank> banks = new ArrayList<ResidentBank>();
	public List<String> banksDel = new ArrayList<String>();

	protected void clearData ()
	{
		banks.clear();
		banksDel.clear();
	}

	protected InputStream getDefaultStream ()
	{
		return getResourceReader(DEFAULT_FILE_NAME);
	}

	protected XMLFilter getDefaultFilter () throws ParserConfigurationException, SAXException
	{
		return new CompositeSaxFilter(XmlHelper.newXMLReader());
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public Iterator iterator () throws GateException, GateLogicException
	{
		internalParse();
		return banks.iterator();
	}

	private class ResidentBankComparator implements Comparator<ResidentBank>
	{

		public int compare(ResidentBank bank1, ResidentBank bank2)
		{
			if (bank1 == null && bank2 == null)
			{
				return 0;
			}
			if (bank1 == null && bank2 != null)
			{
				return 1;
			}
			if (bank1 != null && bank2 == null)
			{
				return -1;
			}

			return bank1.getSynchKey().compareTo( bank2.getSynchKey() );
		}
	}

	private class ResidentBankBIKComparator implements Comparator<ResidentBank>
	{
		public int compare(ResidentBank bank1, ResidentBank bank2)
		{
			if (bank1 == null && bank2 == null)
			{
				return 0;
			}
			if (bank1 == null && bank2 != null)
			{
				return 1;
			}
			if (bank1 != null && bank2 == null)
			{
				return -1;
			}

			return bank1.getBIC().compareTo( bank2.getBIC() );
		}
	}


	public XMLReader chainXMLReader ( XMLReader xmlReader )
	{
		return new CompositeSaxFilter(super.chainXMLReader(xmlReader));
	}


	private class CompositeSaxFilter extends XMLFilterImpl
	{
		private XMLReader mainInfoFilter;
		private XMLReader addInfoFilter;
		private Comparator<ResidentBank>  mainInfoComparator = new ResidentBankBIKComparator();
		private Comparator<ResidentBank> addInfoComparator = new ResidentBankComparator();

		public CompositeSaxFilter ( XMLReader parent)
		{
			super(parent);
			mainInfoFilter = new SaxFilter(parent);
			mainInfoFilter.setEntityResolver(new EmptyResolver());
			addInfoFilter = new AdditionalSaxFilter(parent);
			addInfoFilter.setEntityResolver(new EmptyResolver());
		}

		public void parse(InputSource input) throws SAXException, IOException
		{
			File tmp = FileHelper.writeTmp(input.getByteStream());
			InputStream input1 = new FileInputStream(tmp);
			try
			{
				InputSource inputSource1 = new InputSource(input1);
				mainInfoFilter.parse(inputSource1);
			}
			finally
			{
				input1.close();
			}

			if (banks.isEmpty())
				return;

			Collections.sort(banks, mainInfoComparator);
			 //удаляем из загрузки банки со "старыми" БИК
			if (!banksDel.isEmpty())
				removeOldBank(banks, banksDel);

			InputStream input2 = new FileInputStream(tmp);
			try
			{
				InputSource inputSource2 = new InputSource(input2);

				addInfoFilter.parse(inputSource2);
				Collections.sort(banks, addInfoComparator);
			}
			finally
			{
				input2.close();
			}
			if (!tmp.delete())
			{
				log.info("Не удалось удалить временный файл со справочником банков ( " + tmp.getName() + " ).");
			}
		}
		private void removeOldBank(List<ResidentBank> banks, List<String> banksDel)
		{
			List<ResidentBank> removeBanks = new ArrayList<ResidentBank>();
			for (ResidentBank bank : banks)
			{
				if (banksDel.contains(bank.getBIC()))
					removeBanks.add(bank);
			}
			banks.removeAll(removeBanks);
		}
	}

	private class SaxFilter extends XMLFilterImpl
	{
		private Boolean insideSection = false;
		private ResidentBank tmpBank;
		private String index = null;
		private String address = null;
		private boolean doSkipElement = false;  // признак того, что данный элем. tmp не нужно добавлять в список
		private String vkey = new String();
		private boolean vkeydel = false;

		public SaxFilter ( XMLReader parent )
		{
			super(parent);
		}

		public void startElement ( String uri, String localName, String qName, Attributes attributes )
				throws SAXException
		{
			if (qName.equalsIgnoreCase("table") && attributes.getValue("name").equalsIgnoreCase("BNKSEEK"))
			{
				insideSection = true;
			}
			else if (insideSection)
			{
				if (qName.equalsIgnoreCase("record"))
				{
					tmpBank = new ResidentBank();
				}
				else if (qName.equalsIgnoreCase("field"))
				{
					if (tmpBank==null)
					{
						return;
					}
					String name = attributes.getValue("name");
					if (name.equals("NEWNUM"))
					{
						tmpBank.setBIC(attributes.getValue("value"));
						tmpBank.setSynchKey(attributes.getValue("value"));
					}
					if (name.equals("NNP"))
					{
						tmpBank.setPlace(attributes.getValue("value"));
					}
					if (name.equals("IND"))
					{
						index = attributes.getValue("value");
					}
					if (name.equals("ADR"))
					{
						address = attributes.getValue("value");
					}
					if (name.equals("NAMEP"))
					{
						tmpBank.setName(attributes.getValue("value"));
					}
					if (name.equals("NAMEN"))
					{
						tmpBank.setShortName(attributes.getValue("value"));
					}
					if (name.equals("KSNP"))
					{
						tmpBank.setAccount(attributes.getValue("value"));
					}
					if (name.equals("VKEY"))
					{
						vkey = attributes.getValue("value");
					}
					if (name.equals("VKEYDEL"))
					{
						//отмечаем элемент, у которого признак преемника не пуст
						if ("false".equals(attributes.getValue("null")))
						{
							vkeydel = true;
						}
					}
					if (name.equals("DATE_CH"))
					{
						//если контрольная дата заполнена запоминаем ее
						if ("false".equals(attributes.getValue("null")))
						{
							String val = attributes.getValue("value");
							try
							{
								tmpBank.setDateCh(DateHelper.parseCalendar(val, "yyyy.MM.dd"));
							}
							catch (ParseException e){
								log.info("Банк с VKEY = "+vkey+ " будет проигнорирован!");
							}
						}
					}
					if (name.equals("REAL"))
					{
						String val = attributes.getValue("value");
						if ("ЛИКВ".equals(val))
						{
							doSkipElement = true;
							//напишем в лог, что мы пропустили такую запись, у которой REAL="ЛИКВ"
							log.info("Банк с VKEY = "+vkey+ " будет проигнорирован!");
						}
					}
					if (name.equals("PZN"))
					{
						tmpBank.setParticipantCode(attributes.getValue("value"));
					}
				}
			}
			if (!insideSection)
			{
				super.startElement(uri, localName, qName, attributes);
			}
		}

		public void endElement ( String uri, String localName, String qName ) throws SAXException
		{
			if (insideSection)
			{
				if (qName.equalsIgnoreCase("table"))
				{
					insideSection = false;
				}
				else if (qName.equalsIgnoreCase("record"))
				{
					if (!doSkipElement)
					{
						//Если признак правопреемника установлен и контрольная дата меньше или равна текущему дню, то запись не загружается
						if (vkeydel && tmpBank.getDateCh().compareTo(DateHelper.getCurrentDate()) <=0 )
						{
							banksDel.add(tmpBank.getBIC());
							log.info("Банк с VKEY = "+vkey+ " будет проигнорирован!");
						}
						else
						{
							if (tmpBank.getSynchKey() != null)
							{
								StringBuilder addressBuilder = new StringBuilder(StringHelper.getEmptyIfNull(index)).append(", ")
										.append(StringHelper.getEmptyIfNull(tmpBank.getPlace())).append(", ").append(StringHelper.getEmptyIfNull(address));
								tmpBank.setAddress(addressBuilder.toString());
								banks.add(tmpBank);
							}
						}
					}
					else
					{
						doSkipElement = false;
					}
					tmpBank = null;
					address = null;
					index = null;
					vkeydel = false;
				}
			}
			else
			{
				super.endElement(uri, localName, qName);
			}
		}

	}

	private class AdditionalSaxFilter extends XMLFilterImpl
	{
		private Boolean insideSection = false;
		private Boolean our = false;
		private String INN = null; //ИНН
		private String KPP = null; //КПП
		private String PZN = null; //Код типа участника расчетов в сети БР
		private String BIC = null;

		public AdditionalSaxFilter ( XMLReader parent )
		{
			super(parent);
		}

		public void startElement ( String uri, String localName, String qName, Attributes attributes )
				throws SAXException
		{
			if (qName.equalsIgnoreCase("table") && attributes.getValue("name").equalsIgnoreCase("SBIKN"))
			{
				insideSection = true;
			}
			else if (insideSection)
			{
				if (qName.equalsIgnoreCase("field"))
				{
					String name = attributes.getValue("name");
					if (name.equals("NEWNUM"))
					{
						BIC = attributes.getValue("value");
					}
					if (name.equals("IDENT"))
					{
						if (attributes.getValue("null").equals("true"))
							our = false;
						else
							our = true;
					}
					if (name.equals("INN"))
					{
						INN = attributes.getValue("value");
					}
					if (name.equals("KPP"))
					{
						KPP = attributes.getValue("value");
					}
					if (name.equals("PZN"))
					{
						PZN = attributes.getValue("value");
					}
				}
			}
			if (!insideSection)
			{
				super.startElement(uri, localName, qName, attributes);
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			if (insideSection)
			{
				if (qName.equalsIgnoreCase("table"))
				{
					insideSection = false;
				}
				else if (qName.equalsIgnoreCase("record"))
				{
					ResidentBank tmpBank = new ResidentBank();
					tmpBank.setBIC(BIC);
					int bankIndex = Collections.binarySearch(banks, tmpBank, new ResidentBankBIKComparator());
					if (bankIndex > 0)
					{
						ResidentBank bank = banks.get(bankIndex);
						bank.setOur(our);
						if (StringHelper.equalsNullIgnore(PZN, bank.getParticipantCode()))
						{
							bank.setINN(INN);
							bank.setKPP(KPP);
						}
					}

					BIC = null;
					our = false;
					INN = null;
					KPP = null;
					PZN = null;
				}
			}
			else
			{
				super.endElement(uri, localName, qName);
			}
		}

	}

}
