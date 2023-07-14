<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/loanclaim/credit/product/edit" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="tbNumbers" value="${form.tbNumbers}"/>

    <tiles:insert definition="loanclaimEdit">

        <tiles:put name="submenu" value="CreditProduct"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name" value="Кредитный продукт"/>
                <tiles:put name="description" value="Используйте данную форму для редактирования «Кредитного продукта»"/>
                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.loan.claim.name" bundle="loanclaimBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(name)" size="25" maxlength="25"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.loan.claim.credit.product.code" bundle="loanclaimBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="false"/>
                        <tiles:put name="data">
                            <html:text property="field(code)" size="3" maxlength="3"/>
                            <html:text property="field(description)" size="50" maxlength="50"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.loan.claim.ensuring" bundle="loanclaimBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="field(ensuring)">
                                <html:option value="true">
                                    <bean:message key="label.loan.claim.required" bundle="loanclaimBundle"/>
                                </html:option>
                                <html:option value="false">
                                    <bean:message key="label.loan.claim.not.required" bundle="loanclaimBundle"/>
                                </html:option>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <div class="textTitle"><bean:message key="label.loan.claim.credit.sub.product.code" bundle="loanclaimBundle"/></div>
                    <table width="100%" cellspacing="0" cellpadding="0" align="CENTER" class="standartTable">
                        <tbody>
                        <tr class="tblInfHeader">
                            <th class="titleTable">
                                <bean:message key="label.loan.claim.tb.name" bundle="loanclaimBundle"/>
                            </th>
                            <th class="titleTable">
                                <bean:message key="label.loan.claim.rub" bundle="loanclaimBundle"/>
                            </th>
                            <th class="titleTable">
                                <bean:message key="label.loan.claim.usd" bundle="loanclaimBundle"/>
                            </th>
                            <th class="titleTable">
                                <bean:message key="label.loan.claim.eur" bundle="loanclaimBundle"/>
                            </th>
                        </tr>
                            <c:forEach var="tbNumber" items="${tbNumbers}" varStatus="li">
                                <tr>
                                    <td class="ListLine">
                                        <c:set var="fieldTbName" value="tbName${tbNumber}"/>
                                        <c:out value="${form.fields[fieldTbName]}"/>
                                    </td>
                                    <td class="ListLine">
                                        <html:text property="field(RUB${tbNumber})" size="10" maxlength="10"/>
                                    </td>
                                    <td class="ListLine">
                                        <html:text property="field(USD${tbNumber})" size="10" maxlength="10"/>
                                    </td>
                                    <td class="ListLine">
                                        <html:text property="field(EUR${tbNumber})" size="10" maxlength="10"/>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel"/>
                        <tiles:put name="bundle"         value="loanclaimBundle"/>
                        <tiles:put name="action" value="/loanclaim/credit/product/list.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save"/>
                        <tiles:put name="bundle"         value="loanclaimBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>


</html:form>
