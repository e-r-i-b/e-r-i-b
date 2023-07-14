package com.rssl.phizic.utils;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerException;

/**
 * @author sergunin
 * @ created 19.05.14
 * @ $Author$
 * @ $Revision$
 */

public class Report
{
    protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

    private String op;
    private String id;
    private String curatorId;
    private String tb;
    private String osb;
    private String vsp;
    private String description;
    private String openIn;
    private boolean error;
    private boolean dataValid;
	//Номер счета карты
	private String cardPrimaryAccount;

    public Report()
    {
        error = false;
        dataValid = false;
    }

    public Report(String reportAsString)
    {
        this();
        if(StringHelper.isNotEmpty(reportAsString)){
            Element recordElement = null;
            DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
            Document record = documentBuilder.newDocument();
            try
            {
                record = XmlHelper.parse(reportAsString);
                recordElement = XmlHelper.selectSingleNode(record.getDocumentElement(),"/record");
                setId(XmlHelper.getSimpleElementValue(recordElement, "id"));
                setOp(XmlHelper.getSimpleElementValue(recordElement, "op"));
                setCuratorId(XmlHelper.getSimpleElementValue(recordElement, "curator-id"));
                setTb(XmlHelper.getSimpleElementValue(recordElement, "tb"));
                setOsb(XmlHelper.getSimpleElementValue(recordElement, "osb"));
                setVsp(XmlHelper.getSimpleElementValue(recordElement, "vsp"));
                setDescription(XmlHelper.getSimpleElementValue(recordElement, "description"));
                setOpenIn(XmlHelper.getSimpleElementValue(recordElement, "open-in"));
	            setCardPrimaryAccount(XmlHelper.getSimpleElementValue(recordElement, "card-primary-account"));
            }
            catch (Exception e)
            {
                log.error("При построении отчёта об открытии вклада произошла ошибка", e);
                return;
            }
        }
    }

    public String getOp()
    {
        return op;
    }

    public void setOp(String op)
    {
        this.op = op;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getCuratorId()
    {
        return curatorId;
    }

    public void setCuratorId(String curatorId)
    {
        this.curatorId = curatorId;
    }

    public String getTb()
    {
        return tb;
    }

    public void setTb(String tb)
    {
        this.tb = tb;
    }

    public String getOsb()
    {
        return osb;
    }

    public void setOsb(String osb)
    {
        this.osb = osb;
    }

    public String getVsp()
    {
        return vsp;
    }

    public void setVsp(String vsp)
    {
        this.vsp = vsp;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getOpenIn()
    {
        return openIn;
    }

    public void setOpenIn(String openIn)
    {
        this.openIn = openIn;
    }

    public boolean isError()
    {
        return error;
    }

    public void setError(boolean error)
    {
        this.error = error;
    }

    public boolean isDataValid()
    {
        return dataValid;
    }

    public void setDataValid(boolean dataValid)
    {
        this.dataValid = dataValid;
    }

	public String getCardPrimaryAccount()
	{
		return cardPrimaryAccount;
	}

	public void setCardPrimaryAccount(String cardPrimaryAccount)
	{
		this.cardPrimaryAccount = cardPrimaryAccount;
	}

	public String getErrorMsg() throws TransformerException
    {
		//Сообщения нет если нет прав на "Открытие вклада на балансе ВСП ..." для данного канала
	    if (!haveErrorMsg())
		    return null;
        Document record;
        Element recordElement;
        DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();

        record = documentBuilder.newDocument();

        recordElement = record.createElement("record");
	    if (StringHelper.isNotEmpty(op))
            XmlHelper.appendSimpleElement(recordElement,"op", op);
	    if (StringHelper.isNotEmpty(id))
            XmlHelper.appendSimpleElement(recordElement,"id", id);
	    if (StringHelper.isNotEmpty(curatorId))
            XmlHelper.appendSimpleElement(recordElement,"curator-id", curatorId);
	    if (StringHelper.isNotEmpty(tb))
            XmlHelper.appendSimpleElement(recordElement,"tb", tb);
	    if (StringHelper.isNotEmpty(osb))
            XmlHelper.appendSimpleElement(recordElement,"osb", osb);
	    if (StringHelper.isNotEmpty(vsp))
            XmlHelper.appendSimpleElement(recordElement,"vsp", vsp);
	    if (StringHelper.isNotEmpty(description))
            XmlHelper.appendSimpleElement(recordElement,"description", description);
	    if (StringHelper.isNotEmpty(openIn))
            XmlHelper.appendSimpleElement(recordElement,"open-in", openIn);
	    if (StringHelper.isNotEmpty(cardPrimaryAccount))
		    XmlHelper.appendSimpleElement(recordElement,"card-primary-account", cardPrimaryAccount);

        record.appendChild(recordElement);

        return XmlHelper.convertDomToText(record, "windows-1251");
    }

	/**
	 * Добавлялось ли сообщение об ошибке
	 * @return
	 */
	private boolean haveErrorMsg()
	{
		return StringHelper.isNotEmpty(op)||
				StringHelper.isNotEmpty(id)||
				StringHelper.isNotEmpty(curatorId)||
				StringHelper.isNotEmpty(tb)||
				StringHelper.isNotEmpty(osb)||
				StringHelper.isNotEmpty(vsp)||
				StringHelper.isNotEmpty(description)||
				StringHelper.isNotEmpty(openIn)||
				StringHelper.isNotEmpty(cardPrimaryAccount);
	}
}
