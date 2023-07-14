
package com.rssl.phizic.synchronization.types;

import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.jaxb.MobileInternationalPhoneNumberXmlAdapter;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Список телефонов клиента
 * 
 * <p>Java class for ClientPhonesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClientPhonesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="phone" type="{}PhoneType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClientPhonesType", propOrder = {
    "phones"
})
public class ClientPhonesType {

    @XmlElement(name = "phone", required = true, type = String.class)
    @XmlJavaTypeAdapter(MobileInternationalPhoneNumberXmlAdapter.class)
    protected List<PhoneNumber> phones;

    /**
     * Gets the value of the phones property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the phones property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPhones().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<PhoneNumber> getPhones() {
        if (phones == null) {
            phones = new ArrayList<PhoneNumber>();
        }
        return this.phones;
    }

}
