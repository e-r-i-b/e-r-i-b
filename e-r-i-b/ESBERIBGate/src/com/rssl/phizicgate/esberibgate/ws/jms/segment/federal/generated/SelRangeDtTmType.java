
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип для временного периода (дата и время)
 * 
 * <p>Java class for SelRangeDtTm_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SelRangeDtTm_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StartDtTm" type="{}DateTime"/>
 *         &lt;element name="EndDtTm" type="{}DateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SelRangeDtTm_Type", propOrder = {
    "startDtTm",
    "endDtTm"
})
public class SelRangeDtTmType {

    @XmlElement(name = "StartDtTm", required = true)
    protected String startDtTm;
    @XmlElement(name = "EndDtTm", required = true)
    protected String endDtTm;

    /**
     * Gets the value of the startDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartDtTm() {
        return startDtTm;
    }

    /**
     * Sets the value of the startDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDtTm(String value) {
        this.startDtTm = value;
    }

    /**
     * Gets the value of the endDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndDtTm() {
        return endDtTm;
    }

    /**
     * Sets the value of the endDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDtTm(String value) {
        this.endDtTm = value;
    }

}
