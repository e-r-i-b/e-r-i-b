
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * <p>Java class for CardAcctInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CardAcctInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AdditionalCard" type="{}String" minOccurs="0"/>
 *         &lt;element name="MainCard" type="{}CardNumType" minOccurs="0"/>
 *         &lt;element name="NextReportDate" type="{}Date" minOccurs="0"/>
 *         &lt;element name="EndDtForWay" type="{}String" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardAcctInfo_Type", propOrder = {
    "additionalCard",
    "mainCard",
    "nextReportDate",
    "endDtForWay"
})
@XmlRootElement(name = "CardAcctInfo")
public class CardAcctInfo {

    @XmlElement(name = "AdditionalCard")
    protected String additionalCard;
    @XmlElement(name = "MainCard")
    protected String mainCard;
    @XmlElement(name = "NextReportDate", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar nextReportDate;
    @XmlElement(name = "EndDtForWay")
    protected String endDtForWay;

    /**
     * Gets the value of the additionalCard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalCard() {
        return additionalCard;
    }

    /**
     * Sets the value of the additionalCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalCard(String value) {
        this.additionalCard = value;
    }

    /**
     * Gets the value of the mainCard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainCard() {
        return mainCard;
    }

    /**
     * Sets the value of the mainCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainCard(String value) {
        this.mainCard = value;
    }

    /**
     * Gets the value of the nextReportDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getNextReportDate() {
        return nextReportDate;
    }

    /**
     * Sets the value of the nextReportDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNextReportDate(Calendar value) {
        this.nextReportDate = value;
    }

    /**
     * Gets the value of the endDtForWay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndDtForWay() {
        return endDtForWay;
    }

    /**
     * Sets the value of the endDtForWay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDtForWay(String value) {
        this.endDtForWay = value;
    }

}
