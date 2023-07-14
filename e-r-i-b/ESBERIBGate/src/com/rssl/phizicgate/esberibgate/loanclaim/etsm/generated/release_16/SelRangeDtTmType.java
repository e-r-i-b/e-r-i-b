
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeNoMillisecondsAdapter;


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

    @XmlElement(name = "StartDtTm", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeNoMillisecondsAdapter.class)
    protected Calendar startDtTm;
    @XmlElement(name = "EndDtTm", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeNoMillisecondsAdapter.class)
    protected Calendar endDtTm;

    /**
     * Gets the value of the startDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getStartDtTm() {
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
    public void setStartDtTm(Calendar value) {
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
    public Calendar getEndDtTm() {
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
    public void setEndDtTm(Calendar value) {
        this.endDtTm = value;
    }

}
