
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Параметры предложений
 * 
 * <p>Java class for ProposalParameters_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProposalParameters_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TableName" type="{}TableName_Type" minOccurs="0"/>
 *         &lt;element ref="{}Columns" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Rows" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Elements" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="RateMin" type="{}RateOffer_Type" minOccurs="0"/>
 *         &lt;element name="RateMax" type="{}RateOffer_Type" minOccurs="0"/>
 *         &lt;element name="LimitMin" type="{}Limit_Type" minOccurs="0"/>
 *         &lt;element name="LimitMax" type="{}Limit_Type" minOccurs="0"/>
 *         &lt;element name="PeriodMin" type="{}Period_Type" minOccurs="0"/>
 *         &lt;element name="PeriodMax" type="{}Period_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProposalParameters_Type", propOrder = {
    "tableName",
    "columns",
    "rows",
    "elements",
    "rateMin",
    "rateMax",
    "limitMin",
    "limitMax",
    "periodMin",
    "periodMax"
})
@XmlRootElement(name = "ProposalParameters")
public class ProposalParameters {

    @XmlElement(name = "TableName")
    protected String tableName;
    @XmlElement(name = "Columns")
    protected List<TableType> columns;
    @XmlElement(name = "Rows")
    protected List<TableType> rows;
    @XmlElement(name = "Elements")
    protected List<Elements> elements;
    @XmlElement(name = "RateMin")
    protected String rateMin;
    @XmlElement(name = "RateMax")
    protected String rateMax;
    @XmlElement(name = "LimitMin")
    protected String limitMin;
    @XmlElement(name = "LimitMax")
    protected String limitMax;
    @XmlElement(name = "PeriodMin")
    protected String periodMin;
    @XmlElement(name = "PeriodMax")
    protected String periodMax;

    /**
     * Gets the value of the tableName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Sets the value of the tableName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTableName(String value) {
        this.tableName = value;
    }

    /**
     * Gets the value of the columns property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the columns property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getColumns().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TableType }
     * 
     * 
     */
    public List<TableType> getColumns() {
        if (columns == null) {
            columns = new ArrayList<TableType>();
        }
        return this.columns;
    }

    /**
     * Gets the value of the rows property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rows property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRows().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TableType }
     * 
     * 
     */
    public List<TableType> getRows() {
        if (rows == null) {
            rows = new ArrayList<TableType>();
        }
        return this.rows;
    }

    /**
     * Gets the value of the elements property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the elements property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getElements().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Elements }
     * 
     * 
     */
    public List<Elements> getElements() {
        if (elements == null) {
            elements = new ArrayList<Elements>();
        }
        return this.elements;
    }

    /**
     * Gets the value of the rateMin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRateMin() {
        return rateMin;
    }

    /**
     * Sets the value of the rateMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRateMin(String value) {
        this.rateMin = value;
    }

    /**
     * Gets the value of the rateMax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRateMax() {
        return rateMax;
    }

    /**
     * Sets the value of the rateMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRateMax(String value) {
        this.rateMax = value;
    }

    /**
     * Gets the value of the limitMin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimitMin() {
        return limitMin;
    }

    /**
     * Sets the value of the limitMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLimitMin(String value) {
        this.limitMin = value;
    }

    /**
     * Gets the value of the limitMax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLimitMax() {
        return limitMax;
    }

    /**
     * Sets the value of the limitMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLimitMax(String value) {
        this.limitMax = value;
    }

    /**
     * Gets the value of the periodMin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPeriodMin() {
        return periodMin;
    }

    /**
     * Sets the value of the periodMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPeriodMin(String value) {
        this.periodMin = value;
    }

    /**
     * Gets the value of the periodMax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPeriodMax() {
        return periodMax;
    }

    /**
     * Sets the value of the periodMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPeriodMax(String value) {
        this.periodMax = value;
    }

}
