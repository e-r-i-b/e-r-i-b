
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SPDefFieldShort_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SPDefFieldShort_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FieldData1" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="FieldNum" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SPDefFieldShort_Type", propOrder = {
    "fieldData1",
    "fieldNum"
})
public class SPDefFieldShortType {

    @XmlElement(name = "FieldData1")
    protected long fieldData1;
    @XmlElement(name = "FieldNum")
    protected long fieldNum;

    /**
     * Gets the value of the fieldData1 property.
     * 
     */
    public long getFieldData1() {
        return fieldData1;
    }

    /**
     * Sets the value of the fieldData1 property.
     * 
     */
    public void setFieldData1(long value) {
        this.fieldData1 = value;
    }

    /**
     * Gets the value of the fieldNum property.
     * 
     */
    public long getFieldNum() {
        return fieldNum;
    }

    /**
     * Sets the value of the fieldNum property.
     * 
     */
    public void setFieldNum(long value) {
        this.fieldNum = value;
    }

}
