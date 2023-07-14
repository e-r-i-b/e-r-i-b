
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Список типов Автоплатежей
 * 
 * <p>Java class for AutopayTypeList_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AutopayTypeList_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ExeEventCode" type="{}ExeEventCodeASAP_Type"/>
 *         &lt;element name="SummaKindCode" type="{}SummaKindCodeASAP_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AutopayTypeList_Type", propOrder = {
    "exeEventCode",
    "summaKindCode"
})
public class AutopayTypeListType {

    @XmlElement(name = "ExeEventCode", required = true)
    protected ExeEventCodeASAPType exeEventCode;
    @XmlElement(name = "SummaKindCode", required = true)
    protected SummaKindCodeASAPType summaKindCode;

    /**
     * Gets the value of the exeEventCode property.
     * 
     * @return
     *     possible object is
     *     {@link ExeEventCodeASAPType }
     *     
     */
    public ExeEventCodeASAPType getExeEventCode() {
        return exeEventCode;
    }

    /**
     * Sets the value of the exeEventCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExeEventCodeASAPType }
     *     
     */
    public void setExeEventCode(ExeEventCodeASAPType value) {
        this.exeEventCode = value;
    }

    /**
     * Gets the value of the summaKindCode property.
     * 
     * @return
     *     possible object is
     *     {@link SummaKindCodeASAPType }
     *     
     */
    public SummaKindCodeASAPType getSummaKindCode() {
        return summaKindCode;
    }

    /**
     * Sets the value of the summaKindCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link SummaKindCodeASAPType }
     *     
     */
    public void setSummaKindCode(SummaKindCodeASAPType value) {
        this.summaKindCode = value;
    }

}
