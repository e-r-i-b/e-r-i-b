
package com.rssl.phizic.synchronization.types;

import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;

import java.util.Calendar;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Период, за который списана абонентская плата
 * 
 * <p>Java class for PaymentPeriodType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentPeriodType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="firstDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="lastDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentPeriodType", propOrder = {
    "firstDate",
    "lastDate"
})
public class PaymentPeriodType {

    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Calendar firstDate;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Calendar lastDate;

    /**
     * Gets the value of the firstDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getFirstDate() {
        return firstDate;
    }

    /**
     * Sets the value of the firstDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstDate(Calendar value) {
        this.firstDate = value;
    }

    /**
     * Gets the value of the lastDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getLastDate() {
        return lastDate;
    }

    /**
     * Sets the value of the lastDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastDate(Calendar value) {
        this.lastDate = value;
    }

}
