
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * ƒополнительные параметры дл€ счета (договора)
 * 
 * <p>Java class for BankAcctInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BankAcctInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="Ann" type="{}FlagAnn_Type"/>
 *           &lt;element name="IsAnn" type="{}FlagAnn_Type"/>
 *         &lt;/choice>
 *         &lt;element ref="{}StartDt"/>
 *         &lt;element ref="{}ExpDt"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankAcctInfo_Type", propOrder = {
    "isAnn",
    "ann",
    "startDt",
    "expDt"
})
@XmlRootElement(name = "BankAcctInfo")
public class BankAcctInfo {

    @XmlElement(name = "IsAnn")
    protected Boolean isAnn;
    @XmlElement(name = "Ann")
    protected Boolean ann;
    @XmlElement(name = "StartDt", required = true)
    protected String startDt;
    @XmlElement(name = "ExpDt", required = true)
    protected String expDt;

    /**
     * Gets the value of the isAnn property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsAnn() {
        return isAnn;
    }

    /**
     * Sets the value of the isAnn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsAnn(Boolean value) {
        this.isAnn = value;
    }

    /**
     * Gets the value of the ann property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getAnn() {
        return ann;
    }

    /**
     * Sets the value of the ann property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAnn(Boolean value) {
        this.ann = value;
    }

    /**
     * Gets the value of the startDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartDt() {
        return startDt;
    }

    /**
     * Sets the value of the startDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDt(String value) {
        this.startDt = value;
    }

    /**
     * Gets the value of the expDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpDt() {
        return expDt;
    }

    /**
     * Sets the value of the expDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpDt(String value) {
        this.expDt = value;
    }

}
