//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.10.01 at 06:00:36 PM MSD 
//


package com.rssl.phizic.business.ermb.newclient.message.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClientSToAdd_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClientSToAdd_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ClientToAdd" type="{}ClientToAdd_Type" maxOccurs="1000"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClientSToAdd_Type", propOrder = {
    "clientToAdd"
})
public class ClientSToAddType {

    @XmlElement(name = "ClientToAdd", required = true)
    protected List<ClientToAddType> clientToAdd;

    /**
     * Gets the value of the clientToAdd property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clientToAdd property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClientToAdd().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClientToAddType }
     * 
     * 
     */
    public List<ClientToAddType> getClientToAdd() {
        if (clientToAdd == null) {
            clientToAdd = new ArrayList<ClientToAddType>();
        }
        return this.clientToAdd;
    }

}
