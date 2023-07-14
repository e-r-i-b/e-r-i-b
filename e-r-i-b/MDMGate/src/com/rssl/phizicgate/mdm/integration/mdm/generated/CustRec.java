
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;


/**
 * <p>Java class for CustRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CustId" type="{}CustIdRq_Type" minOccurs="0"/>
 *         &lt;element ref="{}CustInfo" minOccurs="0"/>
 *         &lt;element ref="{}UpDt" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustRec_Type", propOrder = {
    "custId",
    "custInfo",
    "upDt"
})
@XmlRootElement(name = "CustRec")
public class CustRec {

    @XmlElement(name = "CustId")
    protected CustIdRqType custId;
    @XmlElement(name = "CustInfo")
    protected CustInfo custInfo;
    @XmlElement(name = "UpDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar upDt;

    /**
     * Gets the value of the custId property.
     * 
     * @return
     *     possible object is
     *     {@link CustIdRqType }
     *     
     */
    public CustIdRqType getCustId() {
        return custId;
    }

    /**
     * Sets the value of the custId property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustIdRqType }
     *     
     */
    public void setCustId(CustIdRqType value) {
        this.custId = value;
    }

    /**
     * Gets the value of the custInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CustInfo }
     *     
     */
    public CustInfo getCustInfo() {
        return custInfo;
    }

    /**
     * Sets the value of the custInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustInfo }
     *     
     */
    public void setCustInfo(CustInfo value) {
        this.custInfo = value;
    }

    /**
     * Gets the value of the upDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getUpDt() {
        return upDt;
    }

    /**
     * Sets the value of the upDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpDt(Calendar value) {
        this.upDt = value;
    }

}
