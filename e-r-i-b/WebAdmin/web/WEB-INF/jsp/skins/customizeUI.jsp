<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<html:form action="/skins/customizeUI" onsubmit="return setEmptyAction(event);">
    <c:set var="form"     value="${phiz:currentForm(pageContext)}"/>
    <c:set var="person"   value="${form.activePerson}"/>
    <c:set var="personId" value="${form.person}"/>
    <c:set var="canEdit"  value="${phiz:impliesOperation('EditPersonUIOperation',null)}"/>
    <c:set var="hasPersonal" value="${form.skin != null}"/>
    

    <tiles:insert definition="personEdit" flush="false">
        <tiles:put name="submenu" type="string" value="CustomizeUI"/>
        <tiles:put name="data"    type="string">
            <script type="text/javascript">
                var initialState = null;
                function init()
                {
                    if(initialState == null)
                    {
                        initialState = ${hasPersonal} ? document.getElementById('personalSkin'):
                                document.getElementById('standartSkin');
                    }
                    initialState.checked = true;
                    changeSkinType(initialState);
                }

                function changeSkinType(element)
                {
                    if(element)
                    {
                        ensureElement('field(skin)').disabled = (element.id == 'standartSkin');
                    }
                    else
                    {
                        ensureElement('field(skin)').disabled = ensureElement('standartSkin').checked;
                    }
                }
            </script>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="skins"/>
                <tiles:put name="name">
                    <bean:message key="label.customize.userui" bundle="skinsBundle"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message key="label.customize.userui.description" bundle="skinsBundle"/>
                </tiles:put>

                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.customize.userui.clarification" bundle="skinsBundle"/>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <input id="standartSkin" name="field(skinType)" type="radio" onclick="changeSkinType(this)" value="standart" ${!canEdit ? 'disabled' : ''}>
                            <bean:message key="label.customize.userui.standart" bundle="skinsBundle"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <input id="personalSkin" name="field(skinType)" type="radio" onclick="changeSkinType(this)" value="personal" ${!canEdit ? 'disabled' : ''} ${hasPersonal ? 'checked' : ''}/>
                            <bean:message key="label.customize.userui.individual" bundle="skinsBundle"/>

                            <html:select styleId="field(skin)" property="field(skin)" disabled="${!canEdit}">
                                <c:if test="${not empty form.skins}">
                                    <logic:iterate id="skin" name="EditPersonUIForm" property="skins">
                                        <option value="${skin.id}" ${skin.id == form.skin.id ? 'selected' : ''}>${skin.name}</option>
                                    </logic:iterate>
                                </c:if>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.customize.userui.current" bundle="skinsBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <input type="text" name="field(currentSkin)" disabled="true"
                                   value='<bean:write name="form" property="field(currentSkin)"/>'/>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>

                <tiles:put name="buttons">
                    <c:if test="${canEdit}">
                        <tiles:insert definition="clientButton" flush="false">
                           <tiles:put name="commandTextKey" value="button.cancel"/>
                           <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                           <tiles:put name="bundle"         value="commonBundle"/>
                           <tiles:put name="onclick">
                             javascript:init();
                           </tiles:put>
                        </tiles:insert>
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey"         value="button.save"/>
                            <tiles:put name="commandHelpKey"     value="button.save.help"/>
                            <tiles:put name="bundle"             value="commonBundle"/>
                            <tiles:put name="isDefault"            value="true"/>
                            <tiles:put name="postbackNavigation" value="true"/>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
                
                <tiles:put name="alignTable" value="center"/>
            </tiles:insert>
            <script type="text/javascript">
                init();
            </script>
        </tiles:put>
    </tiles:insert>
</html:form>
