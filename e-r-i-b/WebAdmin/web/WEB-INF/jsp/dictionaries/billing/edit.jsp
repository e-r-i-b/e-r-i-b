<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<html:form action="/dictionaries/billing/edit">
    <tiles:insert definition="billingEdit">
        <tiles:put name="mainmenu" value="ExternalSystems"/>
        <tiles:put name="submenu" value="Billing" type="string"/>

        <tiles:put name="menu" type="string">
       </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <html:hidden property="id"/>
                <tiles:put name="id"  value="Billing"/>
                <tiles:put name="name"><bean:message bundle="billingBundle" key="editform.name"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="billingBundle" key="editform.title"/></tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="billingBundle" key="label.name"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(name)" size="30"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="billingBundle" key="label.adapter"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <script type="text/javascript">
                                function setAdapterInfo(adapterInfo)
                                {
                                    setElement('field(adapterName)', adapterInfo["name"]);
                                    setElement('field(adapterUUID)', adapterInfo["UUID"]);
                                }
                            </script>
                            <nobr>
                                <html:text property="field(adapterName)" readonly="true" size="30"/>
                                <html:hidden property="field(adapterUUID)"/>
                                <input type="button" class="buttWhite smButt" onclick="javascript:openAdaptersDictionary(setAdapterInfo);" value="..."/>
                            </nobr>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="billingBundle" key="editpage.label.code"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(code)" size="30"/>
                        </tiles:put>
                    </tiles:insert>

                    <fieldset>
                        <legend><bean:message key="label.JBT" bundle="billingBundle"/></legend>
                        <html:radio property="field(needUploadJBT)" value="false">
                            <bean:message bundle="billingBundle" key="label.JBT.noUpload"/>
                        </html:radio>
                        <br/>
                        <html:radio property="field(needUploadJBT)" value="true">
                            <bean:message bundle="billingBundle" key="label.JBT.needUpload"/>
                        </html:radio>
                    </fieldset>

                    <fieldset>

                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="billingBundle" key="label.userName"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:text property="field(userName)" disabled="true" size="30"/>
                            </tiles:put>
                        </tiles:insert>

                        <table>
                            <tr>
                                <td>
                                    <fieldset>
                                        <legend><bean:message key="label.connectMode" bundle="billingBundle"/></legend>
                                        <html:radio property="field(connectMode)" value="ASYNC" disabled="true">
                                            <bean:message bundle="billingBundle" key="label.connectMode.async"/>
                                        </html:radio>
                                        <br/>
                                        <html:radio property="field(connectMode)" value="SYNC" disabled="true">
                                            <bean:message bundle="billingBundle" key="label.connectMode.sync"/>
                                        </html:radio>
                                    </fieldset>
                                 </td>
                                <td>
                                    <fieldset>
                                        <legend><bean:message key="label.requisites" bundle="billingBundle"/></legend>
                                        <html:radio property="field(requisites)" value="false" disabled="true">
                                            <bean:message bundle="billingBundle" key="label.requisites.IKFL"/>
                                        </html:radio>
                                        <br/>
                                        <html:radio property="field(requisites)" value="true" disabled="true">
                                            <bean:message bundle="billingBundle" key="label.requisites.BS"/>
                                        </html:radio>
                                    </fieldset>
                                 </td>
                                <td>
                                    <nobr>
                                        <html:checkbox property="field(comission)" value="true" disabled="true">
                                            <bean:message bundle="billingBundle" key="label.comission"/>
                                        </html:checkbox>
                                    </nobr>
                                 </td>
                            </tr>
                        </table>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="billingBundle" key="label.timeout"/>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                            <tiles:put name="data">
                                <html:text property="field(timeout)" disabled="true" size="5"/>
                                <bean:message bundle="billingBundle" key="label.ms"/>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>

                    <fieldset>
                        <legend><bean:message key="label.template" bundle="billingBundle"/></legend>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="billingBundle" key="label.template.state"/>
                            </tiles:put>
                            <tiles:put name="isNecessary" value="false"/>
                            <tiles:put name="data">
                                <nobr>
                                    <html:select property="field(templateState)" size="30">
                                        <html:option value="ACTIVE"><bean:message bundle="billingBundle" key="label.template.active"/></html:option>
                                        <html:option value="PLANING_FOR_DEACTIVATE"><bean:message bundle="billingBundle" key="label.template.planingForDeactivate"/></html:option>
                                        <html:option value="INACTIVE"><bean:message bundle="billingBundle" key="label.template.inactive"/></html:option>
                                    </html:select>
                                </nobr>
                            </tiles:put>
                        </tiles:insert>
                    </fieldset>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false" operation="EditBillingOperation">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"         value="billingBundle"/>
                        <tiles:put name="isDefault"        value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false" operation="ListBillingsOperation">
                       <tiles:put name="commandTextKey" value="button.cancel"/>
                       <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                       <tiles:put name="bundle"         value="billingBundle"/>
                       <tiles:put name="action"         value="/dictionaries/billing/list.do"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>