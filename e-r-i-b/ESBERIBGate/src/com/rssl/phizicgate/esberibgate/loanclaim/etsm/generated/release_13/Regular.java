
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * ѕараметры регул€рного платежа
 * 
 * <p>Java class for Regular_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Regular_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DateBegin" type="{}Date"/>
 *         &lt;element name="DateEnd" type="{}Date"/>
 *         &lt;element name="PayDay">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Day" type="{}Long" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Priority" type="{}Long" minOccurs="0"/>
 *         &lt;element name="ExeEventCode" type="{}String" minOccurs="0"/>
 *         &lt;element name="Summa" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="SummaKindCode" type="{}String"/>
 *         &lt;element name="Percent" type="{}Decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Regular_Type", propOrder = {
    "dateBegin",
    "dateEnd",
    "payDay",
    "priority",
    "exeEventCode",
    "summa",
    "summaKindCode",
    "percent"
})
@XmlRootElement(name = "Regular")
public class Regular {

    @XmlElement(name = "DateBegin", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar dateBegin;
    @XmlElement(name = "DateEnd", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar dateEnd;
    @XmlElement(name = "PayDay", required = true)
    protected Regular.PayDay payDay;
    @XmlElement(name = "Priority")
    protected Long priority;
    @XmlElement(name = "ExeEventCode")
    protected String exeEventCode;
    @XmlElement(name = "Summa")
    protected BigDecimal summa;
    @XmlElement(name = "SummaKindCode", required = true)
    protected String summaKindCode;
    @XmlElement(name = "Percent")
    protected BigDecimal percent;

    /**
     * Gets the value of the dateBegin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getDateBegin() {
        return dateBegin;
    }

    /**
     * Sets the value of the dateBegin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateBegin(Calendar value) {
        this.dateBegin = value;
    }

    /**
     * Gets the value of the dateEnd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getDateEnd() {
        return dateEnd;
    }

    /**
     * Sets the value of the dateEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateEnd(Calendar value) {
        this.dateEnd = value;
    }

    /**
     * Gets the value of the payDay property.
     * 
     * @return
     *     possible object is
     *     {@link Regular.PayDay }
     *     
     */
    public Regular.PayDay getPayDay() {
        return payDay;
    }

    /**
     * Sets the value of the payDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link Regular.PayDay }
     *     
     */
    public void setPayDay(Regular.PayDay value) {
        this.payDay = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPriority(Long value) {
        this.priority = value;
    }

    /**
     * Gets the value of the exeEventCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExeEventCode() {
        return exeEventCode;
    }

    /**
     * Sets the value of the exeEventCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExeEventCode(String value) {
        this.exeEventCode = value;
    }

    /**
     * Gets the value of the summa property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumma() {
        return summa;
    }

    /**
     * Sets the value of the summa property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumma(BigDecimal value) {
        this.summa = value;
    }

    /**
     * Gets the value of the summaKindCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSummaKindCode() {
        return summaKindCode;
    }

    /**
     * Sets the value of the summaKindCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSummaKindCode(String value) {
        this.summaKindCode = value;
    }

    /**
     * Gets the value of the percent property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPercent() {
        return percent;
    }

    /**
     * Sets the value of the percent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPercent(BigDecimal value) {
        this.percent = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Day" type="{}Long" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "day"
    })
    public static class PayDay {

        @XmlElement(name = "Day")
        protected Long day;

        /**
         * Gets the value of the day property.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getDay() {
            return day;
        }

        /**
         * Sets the value of the day property.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setDay(Long value) {
            this.day = value;
        }

    }

}
