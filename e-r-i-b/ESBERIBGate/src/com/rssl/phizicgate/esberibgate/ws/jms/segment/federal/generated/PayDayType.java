
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Расписание  исполнения АП.
 * 
 * <p>Java class for PayDay_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayDay_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Day" type="{}String" minOccurs="0"/>
 *         &lt;element name="MonthInQuarter" type="{}C" minOccurs="0"/>
 *         &lt;element name="MonthInYear" type="{}String" minOccurs="0"/>
 *         &lt;element name="WeekDay" type="{}WeekDay_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayDay_Type", propOrder = {
    "day",
    "monthInQuarter",
    "monthInYear",
    "weekDay"
})
public class PayDayType {

    @XmlElement(name = "Day")
    protected String day;
    @XmlElement(name = "MonthInQuarter")
    protected String monthInQuarter;
    @XmlElement(name = "MonthInYear")
    protected String monthInYear;
    @XmlElement(name = "WeekDay")
    protected WeekDayType weekDay;

    /**
     * Gets the value of the day property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDay() {
        return day;
    }

    /**
     * Sets the value of the day property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDay(String value) {
        this.day = value;
    }

    /**
     * Gets the value of the monthInQuarter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonthInQuarter() {
        return monthInQuarter;
    }

    /**
     * Sets the value of the monthInQuarter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonthInQuarter(String value) {
        this.monthInQuarter = value;
    }

    /**
     * Gets the value of the monthInYear property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonthInYear() {
        return monthInYear;
    }

    /**
     * Sets the value of the monthInYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonthInYear(String value) {
        this.monthInYear = value;
    }

    /**
     * Gets the value of the weekDay property.
     * 
     * @return
     *     possible object is
     *     {@link WeekDayType }
     *     
     */
    public WeekDayType getWeekDay() {
        return weekDay;
    }

    /**
     * Sets the value of the weekDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link WeekDayType }
     *     
     */
    public void setWeekDay(WeekDayType value) {
        this.weekDay = value;
    }

}
