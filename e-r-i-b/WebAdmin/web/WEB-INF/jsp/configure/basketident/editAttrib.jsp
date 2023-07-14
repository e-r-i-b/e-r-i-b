<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<html:form action="/clientProfile/ident/editAttrib"  onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="configEdit">
        <tiles:put name="submenu" type="string" value="IdentConfig"/>
        <tiles:put name="pageTitle" type="string"><bean:message bundle="configureBundle" key="settings.clientident.editattribtitle"/></tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message bundle="configureBundle" key="settings.clientident.name"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:text property="name" value="${form.name}"/>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message bundle="configureBundle" key="settings.clientident.systemid"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:select property="systemId" styleId="systemId" onchange="setMandatoryIfNumberSelected()">
                        <c:choose>
                            <c:when test="${form.inn}">
                                <html:option value="Inn">Inn</html:option>
                            </c:when>
                            <c:when test="${form.rc}">
                                <html:option value="Series">Series</html:option>
                                <html:option value="Number">Number</html:option>
                            </c:when>
                            <c:otherwise>
                                <html:option value="Series">Series</html:option>
                                <html:option value="Number">Number</html:option>
                                <html:option value="Idate">Idate</html:option>
                                <html:option value="Edate">Edate</html:option>
                                <html:option value="Issuer">Issuer</html:option>
                            </c:otherwise>
                        </c:choose>
                    </html:select>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message bundle="configureBundle" key="settings.clientident.type"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:select property="type">
                        <html:option value="TEXT">Текст</html:option>
                        <html:option value="DATE">Дата</html:option>
                        <html:option value="NUMBER">Целое</html:option>
                        <html:option value="MONEY">Финансовое</html:option>
                    </html:select>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message bundle="configureBundle" key="settings.clientident.regexp"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:text property="regexp" value="${form.regexp}"/>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message bundle="configureBundle" key="settings.clientident.mandatory"/>
                </tiles:put>
                <tiles:put name="needMargin" value="true"/>
                <tiles:put name="data">
                    <html:checkbox property="mandatory" value="${form.mandatory}" onchange="check(this);"/>
                </tiles:put>
            </tiles:insert>
            <script type="text/javascript">
                function fillCheckboxOnLoad()
                {
                    var elements = $("input[name=mandatory]");
                    $(elements).val(${form.mandatory});
                    $(elements).attr("checked", ${form.mandatory})
                    var fieldName = "${form.systemId}";
                    if(fieldName == 'Number')
                    {
                        $(elements).val(true);
                        $(elements).attr('checked', 'checked');
                        $(elements).attr('disabled', 'disabled');
                    }
                }

                function check(elem)
                {
                    elem.checked ? elem.value = true : elem.value = false;

                }
                fillCheckboxOnLoad();

                function setMandatoryIfNumberSelected()
                {
                    var newVal = $('#systemId').val() ? $('#systemId').val() :  "";
                    var mandatory = $("input[name=mandatory]");
                    if(newVal == 'Number')
                    {
                        $(mandatory).val(true);
                        $(mandatory).attr('checked', 'checked');
                        $(mandatory).attr('disabled', 'disabled');
                    }
                    else
                    {
                        $(mandatory).val(false);
                        $(mandatory).attr('checked', false);
                        $(mandatory).removeAttr('disabled');
                    }
                }
            </script>
            <html:hidden property="id" value="${form.id}"/>
            <html:hidden property="identId" value="${form.identId}"/>
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
