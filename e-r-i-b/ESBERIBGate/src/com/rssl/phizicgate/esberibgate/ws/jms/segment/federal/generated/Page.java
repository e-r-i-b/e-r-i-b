
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Параметры страницы
 * 
 * <p>Java class for Page_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Page_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PageSize" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="StartRowNum" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="RecordCountNeeded" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Page_Type", propOrder = {
    "pageSize",
    "startRowNum",
    "recordCountNeeded"
})
@XmlRootElement(name = "Page")
public class Page {

    @XmlElement(name = "PageSize")
    protected BigInteger pageSize;
    @XmlElement(name = "StartRowNum")
    protected BigInteger startRowNum;
    @XmlElement(name = "RecordCountNeeded")
    protected Boolean recordCountNeeded;

    /**
     * Gets the value of the pageSize property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPageSize() {
        return pageSize;
    }

    /**
     * Sets the value of the pageSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPageSize(BigInteger value) {
        this.pageSize = value;
    }

    /**
     * Gets the value of the startRowNum property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStartRowNum() {
        return startRowNum;
    }

    /**
     * Sets the value of the startRowNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStartRowNum(BigInteger value) {
        this.startRowNum = value;
    }

    /**
     * Gets the value of the recordCountNeeded property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getRecordCountNeeded() {
        return recordCountNeeded;
    }

    /**
     * Sets the value of the recordCountNeeded property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRecordCountNeeded(Boolean value) {
        this.recordCountNeeded = value;
    }

}
