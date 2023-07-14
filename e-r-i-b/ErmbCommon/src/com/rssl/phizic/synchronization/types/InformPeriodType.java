
package com.rssl.phizic.synchronization.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Временной интервал, в который разрешено отправлять уведомления
 * 
 * <p>Java class for InformPeriodType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InformPeriodType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dayPeriod" type="{}TimePeriodType" minOccurs="0"/>
 *         &lt;element name="enabledDays" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="day" type="{}DayOfWeekType" maxOccurs="7"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InformPeriodType", propOrder = {
    "dayPeriod",
    "enabledDays"
})
public class InformPeriodType {

    protected TimePeriodType dayPeriod;
    protected InformPeriodType.EnabledDays enabledDays;

    /**
     * Gets the value of the dayPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link TimePeriodType }
     *     
     */
    public TimePeriodType getDayPeriod() {
        return dayPeriod;
    }

    /**
     * Sets the value of the dayPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimePeriodType }
     *     
     */
    public void setDayPeriod(TimePeriodType value) {
        this.dayPeriod = value;
    }

    /**
     * Gets the value of the enabledDays property.
     * 
     * @return
     *     possible object is
     *     {@link InformPeriodType.EnabledDays }
     *     
     */
    public InformPeriodType.EnabledDays getEnabledDays() {
        return enabledDays;
    }

    /**
     * Sets the value of the enabledDays property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformPeriodType.EnabledDays }
     *     
     */
    public void setEnabledDays(InformPeriodType.EnabledDays value) {
        this.enabledDays = value;
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
     *         &lt;element name="day" type="{}DayOfWeekType" maxOccurs="7"/>
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
        "daies"
    })
    public static class EnabledDays {

        @XmlElement(name = "day", required = true)
        protected List<DayOfWeekType> daies;

        /**
         * Gets the value of the daies property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the daies property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDaies().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DayOfWeekType }
         * 
         * 
         */
        public List<DayOfWeekType> getDaies() {
            if (daies == null) {
                daies = new ArrayList<DayOfWeekType>();
            }
            return this.daies;
        }

    }

}
