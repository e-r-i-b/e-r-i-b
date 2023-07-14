
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * Тип <Информация об операции>
 * 
 * <p>Java class for OperInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OperInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DocumentDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="DocumentNumber" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperInfo_Type", propOrder = {
    "documentDate",
    "documentNumber"
})
public class OperInfoType {

    @XmlElement(name = "DocumentDate", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Calendar documentDate;
    @XmlElement(name = "DocumentNumber")
    protected long documentNumber;

    /**
     * Gets the value of the documentDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getDocumentDate() {
        return documentDate;
    }

    /**
     * Sets the value of the documentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentDate(Calendar value) {
        this.documentDate = value;
    }

    /**
     * Gets the value of the documentNumber property.
     * 
     */
    public long getDocumentNumber() {
        return documentNumber;
    }

    /**
     * Sets the value of the documentNumber property.
     * 
     */
    public void setDocumentNumber(long value) {
        this.documentNumber = value;
    }

}
