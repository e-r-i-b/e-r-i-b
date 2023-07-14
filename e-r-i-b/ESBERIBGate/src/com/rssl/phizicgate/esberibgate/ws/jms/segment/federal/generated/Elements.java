
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * √руппирующий тег дл€ передачи данных о значении €чеек 
 * 
 * <p>Java class for Element_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Element_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Value" type="{}String"/>
 *         &lt;element name="ColumnIndex" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="RowIndex" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Element_Type", propOrder = {
    "value",
    "columnIndex",
    "rowIndex"
})
@XmlRootElement(name = "Elements")
public class Elements {

    @XmlElement(name = "Value", required = true)
    protected String value;
    @XmlElement(name = "ColumnIndex", required = true)
    protected BigInteger columnIndex;
    @XmlElement(name = "RowIndex", required = true)
    protected BigInteger rowIndex;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the columnIndex property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getColumnIndex() {
        return columnIndex;
    }

    /**
     * Sets the value of the columnIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setColumnIndex(BigInteger value) {
        this.columnIndex = value;
    }

    /**
     * Gets the value of the rowIndex property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRowIndex() {
        return rowIndex;
    }

    /**
     * Sets the value of the rowIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRowIndex(BigInteger value) {
        this.rowIndex = value;
    }

}
