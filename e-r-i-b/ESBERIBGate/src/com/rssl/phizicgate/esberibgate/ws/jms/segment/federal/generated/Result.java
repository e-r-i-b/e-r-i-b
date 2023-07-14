
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип для возврата результата о состоянии выписки при запросе ПФР
 * 
 * <p>Java class for PfrResult_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PfrResult_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResponseExists" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SendTime" type="{}DateTime"/>
 *         &lt;element name="ReceiveTime" type="{}DateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PfrResult_Type", propOrder = {
    "responseExists",
    "sendTime",
    "receiveTime"
})
@XmlRootElement(name = "Result")
public class Result {

    @XmlElement(name = "ResponseExists")
    protected boolean responseExists;
    @XmlElement(name = "SendTime", required = true)
    protected String sendTime;
    @XmlElement(name = "ReceiveTime")
    protected String receiveTime;

    /**
     * Gets the value of the responseExists property.
     * 
     */
    public boolean isResponseExists() {
        return responseExists;
    }

    /**
     * Sets the value of the responseExists property.
     * 
     */
    public void setResponseExists(boolean value) {
        this.responseExists = value;
    }

    /**
     * Gets the value of the sendTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendTime() {
        return sendTime;
    }

    /**
     * Sets the value of the sendTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendTime(String value) {
        this.sendTime = value;
    }

    /**
     * Gets the value of the receiveTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiveTime() {
        return receiveTime;
    }

    /**
     * Sets the value of the receiveTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiveTime(String value) {
        this.receiveTime = value;
    }

}
