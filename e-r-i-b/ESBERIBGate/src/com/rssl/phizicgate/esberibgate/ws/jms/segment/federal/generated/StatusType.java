
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Статус ответа <Status>. Агрегат <Status> используется для возврата статуса сообщения. 
 * 
 * <p>Java class for Status_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Status_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}StatusCode"/>
 *         &lt;element ref="{}StatusDesc" minOccurs="0"/>
 *         &lt;element ref="{}StatusType" minOccurs="0"/>
 *         &lt;element ref="{}ServerStatusDesc" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Status_Type", propOrder = {
    "statusCode",
    "statusDesc",
    "statusType",
    "serverStatusDesc"
})
@XmlSeeAlso({
    com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.GetPrivateOperationScanRs.Status.class
})
public class StatusType {

    @XmlElement(name = "StatusCode")
    protected long statusCode;
    @XmlElement(name = "StatusDesc")
    protected String statusDesc;
    @XmlElement(name = "StatusType")
    protected String statusType;
    @XmlElement(name = "ServerStatusDesc")
    protected String serverStatusDesc;

    /**
     * Статусный код возврата.
     * 
     */
    public long getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the value of the statusCode property.
     * 
     */
    public void setStatusCode(long value) {
        this.statusCode = value;
    }

    /**
     * Описание статуса.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusDesc() {
        return statusDesc;
    }

    /**
     * Sets the value of the statusDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusDesc(String value) {
        this.statusDesc = value;
    }

    /**
     * Тип ошибки.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusType() {
        return statusType;
    }

    /**
     * Sets the value of the statusType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusType(String value) {
        this.statusType = value;
    }

    /**
     * Описание статуса системы источника
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServerStatusDesc() {
        return serverStatusDesc;
    }

    /**
     * Sets the value of the serverStatusDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServerStatusDesc(String value) {
        this.serverStatusDesc = value;
    }

}
