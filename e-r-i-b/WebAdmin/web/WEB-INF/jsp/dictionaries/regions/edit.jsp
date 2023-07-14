<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/dictionaries/regions/edit">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="providersMain">

        <tiles:put name="submenu" value="Regions"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="editRegion"/>

                <tiles:put name="name">
                    <bean:message bundle="regionsBundle" key="edit.title"/>
                </tiles:put>

                <tiles:put name="description">
                    <bean:message bundle="regionsBundle" key="edit.description"/>
                </tiles:put>

                <tiles:put name="data">
                    <html:hidden property="id"/>
                    <html:hidden property="parentId"/>
                    <%--Идентификатор--%>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="regionsBundle" key="label.code"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(synchKey)" size="40" maxlength="20"/>
                        </tiles:put>
                    </tiles:insert>
                    <%--Наименование--%>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="regionsBundle" key="label.name"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(name)" size="40" maxlength="128"/>
                        </tiles:put>
                    </tiles:insert>
                    <%--Родительский узел--%>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="regionsBundle" key="label.parent"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(parent)" size="40" readonly="true"/>
                        </tiles:put>
                    </tiles:insert>
                    <%--Код ТБ--%>
                    <c:if test="${form.parentId == null || form.parentId == ''}">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="regionsBundle" key="label.tb"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:text property="field(codeTB)" size="10" maxlength="2"/>
                            </tiles:put>
                        </tiles:insert>
                    </c:if>
                    <fieldset>
                        <legend class="servProvCodeLegend">Коды ПУ для оплаты по штрих-кодам</legend>
                        <%--Код для оплаты МП (mAPI)--%>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="regionsBundle" key="label.providerCodeMAPI"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:text property="field(providerCodeMAPI)" size="40" maxlength="128"/>
                            </tiles:put>
                        </tiles:insert>
                        <%--<tr>--%>
                            <%--<td nowrap="true" class="LabelAll">--%>
                                <%--&nbsp;<bean:message bundle="regionsBundle" key="label.providerCodeMAPI"/>&nbsp;--%>
                            <%--</td>--%>
                            <%--<td><html:text property="field(providerCodeMAPI)" size="40" maxlength="128"/></td>--%>
                        <%--</tr>--%>
                        <%--Код для оплаты УС(atmAPI)--%>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title">
                                <bean:message bundle="regionsBundle" key="label.providerCodeATM"/>
                            </tiles:put>
                            <tiles:put name="data">
                                <html:text property="field(providerCodeATM)" size="40" maxlength="128"/>
                            </tiles:put>
                        </tiles:insert>
                        <%--<tr>--%>
                            <%--<td nowrap="true" class="LabelAll">--%>
                                <%--&nbsp;<bean:message bundle="regionsBundle" key="label.providerCodeATM"/>&nbsp;--%>
                            <%--</td>--%>
                            <%--<td><html:text property="field(providerCodeATM)" size="40" maxlength="128"/></td>--%>
                        <%--</tr>--%>
                    </fieldset>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandHelpKey"     value="button.save.help"/>
                        <tiles:put name="bundle"             value="regionsBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"         value="regionsBundle"/>
                        <tiles:put name="action"         value="/private/dictionary/regions/list.do"/>
                    </tiles:insert>
                    <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/dictionaries/regions/locale/save')}"/>
                    <tiles:insert definition="languageSelectForEdit" flush="false">
                        <tiles:put name="selectId" value="chooseLocale"/>
                        <tiles:put name="entityId" value="${form.id}"/>
                        <tiles:put name="styleClass" value="float"/>
                        <tiles:put name="selectTitleOnclick" value="openEditLocaleWin(this);"/>
                        <tiles:put name="editLanguageURL" value="${editLanguageURL}"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
            <div style="float: left">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.back.to.list"/>
                    <tiles:put name="commandHelpKey" value="button.back.to.list"/>
                    <tiles:put name="bundle"         value="regionsBundle"/>
                    <tiles:put name="viewType"       value="simpleLink"/>
                    <tiles:put name="action"         value="/private/dictionary/regions/list.do"/>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>
