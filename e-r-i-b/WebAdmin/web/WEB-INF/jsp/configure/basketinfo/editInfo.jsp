<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<html:form action="/clientProfile/basketinfo/configure"  onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="configEdit">
        <tiles:put name="submenu" type="string" value="InfoConfig"/>
        <tiles:put name="pageTitle" type="string"><bean:message bundle="configureBundle" key="settings.basketinfo.editbasketinfotitle"/></tiles:put>
        <tiles:put name="data" type="string">

            <script type="text/javascript">
                function changeDocumentType()
                {
                    var elem = document.getElementById("field(documentType)");
                    if (elem != null)
                    {
                        switch (elem.value)
                        {
                            case "RC" :
                            {
                                hideOrShow("message_without",false);
                                var message_with = document.getElementById("message_with");
                                message_with.value = "${form.messageWithRC}";
                                var message_without = document.getElementById("message_without");
                                message_without.value = "${form.messageWithoutRC}";

                                var message_with_tittle = document.getElementById("message_with_tittle");
                                message_with_tittle.innerHTML = "${form.messageWithRCTittle}";
                                var message_without_tittle = document.getElementById("message_without_tittle");
                                message_without_tittle.innerHTML = "${form.messageWithoutRCTittle}";
                                return;
                            }
                            case "INN" :
                            {
                                var message_with = document.getElementById("message_with");
                                message_with.value= "${form.messageWithINN}";
                                var message_without = document.getElementById("message_without_tittle");
                                message_without.innerHTML = "";
                                hideOrShow("message_without",true);

                                var message_with_tittle = document.getElementById("message_with_tittle");
                                message_with_tittle.innerHTML= "${form.messageWithINNTittle}";
                                var message_without_tittle = document.getElementById("message_without");
                                message_without_tittle.innerHTML = "";
                                return;
                            }
                            case "DL" :
                            {
                                hideOrShow("message_without",false);
                                var message_with = document.getElementById("message_with");
                                message_with.value = "${form.messageWithDL}";
                                var message_without = document.getElementById("message_without");
                                message_without.value = "${form.messageWithoutDL}";

                                var message_with_tittle = document.getElementById("message_with_tittle");
                                message_with_tittle.innerHTML = "${form.messageWithDLTittle}";
                                var message_without_tittle = document.getElementById("message_without_tittle");
                                message_without_tittle.innerHTML = "${form.messageWithoutDLTittle}";
                                return;
                            }
                        }
                    }
                }
            </script>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message bundle="configureBundle" key="settings.documenttype.name"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:select property="field(documentType)" styleId="field(documentType)" onchange="changeDocumentType()" styleClass="select" style="width:500px">
                        <c:forEach var="documentType" items="${form.documentTypes}">
                            <html:option value="${documentType}" styleClass="text-grey">
                                <bean:message key="document.type.${documentType}" bundle="configureBundle"/>
                            </html:option>
                        </c:forEach>
                    </html:select>
                </tiles:put>
            </tiles:insert>
            <div class = "messageInfoDiv">
                <div class="paymentLabel" id="message_with_tittle">
                        ${form.messageWithDLTittle}
                </div>
            </div>
            <div class = "messageInfoDiv">
                <div class="paymentLabel">
                    <textarea id="message_with" name="field(messageWith)" cols="18" rows="8">${form.messageWithDL}</textarea>
                </div>
            </div>
            <div class = "messageInfoDiv">
                <div class="paymentLabel" id="message_without_tittle">
                        ${form.messageWithoutDLTittle}
                </div>
            </div>
            <div class = "messageInfoDiv">
                <div class="paymentLabel">
                    <textarea id="message_without" name="field(messageWithout)" cols="18" rows="8">${form.messageWithoutDL}</textarea>
                </div>
            </div>



            <div class="pmntFormMainButton floatRight">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle"         value="imageSettingsBundle"/>
                    <tiles:put name="isDefault"      value="true"/>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>

