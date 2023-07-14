<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<tiles:importAttribute/>
<html:form action="/autopayment/freeDetatilAutoSub">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="autoSubscriptions">
        <tiles:put name="submenu" value="AutoSubscriptions"/>
        <tiles:put name="menu" type="string">
            <%@ include file="/WEB-INF/jsp-sbrf/autopayments/resetClientInfoButton.jsp" %>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value=""/>
                <tiles:put name="name" value="������ �� ���������� �����������"/>
                <tiles:put name="description" value="��� ���� ����� �������� ������ �� ���������� ������� ��������� ���������� ������� � ������� �� ������ ������������."/>
                <tiles:put name="data">
                    <html:hidden property="copying"/>
                    <html:hidden property="id"/>
                    <html:hidden property="template"/>
                    <html:hidden property="operationUID"/>
                    <html:hidden property="field(operationCode)"/>

                    <div class="pmntInfAreaTitle">����������</div>

                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title">����� �����:</tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="description">������� ����� ����� ���������� (�� 20 �� 25 ���� ��� ����� � ��������).</tiles:put>
                        <tiles:put name="data"><html:text property="field(receiverAccount)" maxlength="25" size="25"/></tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title">���:</tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="description">������� ����������������� ����� �����������������. � ����������� ��� ������� �� 10 ����, � �������������� ���������������� � �� 12 ����.</tiles:put>
                        <tiles:put name="data"><html:text property="field(receiverINN)" maxlength="12" size="12"/></tiles:put>
                    </tiles:insert>

                    <script type="text/javascript">
                        function setBankInfo ( bankInfo )
                        {
                            setElement('field(receiverBIC)',bankInfo["BIC"]);
                        }
                    </script>

                    <div class="pmntInfAreaTitle">
                        ���� ���������� <span class="blueGrayLinkUnderline" onclick="javascript:openNationalBanksDictionary(setBankInfo, '', '');">
                        ������� �� �����������</span>
                    </div>

                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title">���:</tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="description">������� ���������� ����������������� ���. ��� ����� �������� ������ �� 9 ����.</tiles:put>
                        <tiles:put name="data"><html:text property="field(receiverBIC)" maxlength="9" size="9"/></tiles:put>
                    </tiles:insert>

                    <div class="pmntInfAreaTitle">�������</div>

                    <tiles:insert definition="formRow" flush="false">
                        <tiles:put name="title">���� ��������:</tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="fromResource" styleId="fromResource">
                                <c:if test="${phiz:size(form.chargeOffResources)==0}">
                                    <html:option value="">��� ���� ��� �����������</html:option>
                                </c:if>
                                <c:forEach items="${form.chargeOffResources}" var="resource">
                                    <html:option value="${resource.code}">
                                        <c:choose>
                                            <c:when test="${resource['class'].name == 'com.rssl.phizic.business.resources.external.CardLink'}">
                                                <c:out value="${phiz:getCutCardNumber(resource.number)} [${resource.name}] ${resource.rest.decimal} ${phiz:getCurrencySign(resource.currency.code)}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${resource.number} [${resource.name}] ${resource.rest.decimal} ${phiz:getCurrencySign(resource.currency.code)}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </html:option>
                                </c:forEach>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="window" flush="false">
                        <tiles:put name="id" value="providersList"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <div class="buttonsArea">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.cancel"/>
                            <tiles:put name="commandHelpKey" value="button.cancel"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="action" value="/autopayment/providers"/>
                        </tiles:insert>
                        <tiles:insert definition="commandButton" flush="false" service="CreateEmployeeAutoPayment">
                            <tiles:put name="commandKey"     value="button.next"/>
                            <tiles:put name="commandHelpKey" value="button.next.help"/>
                            <tiles:put name="bundle"  value="autopaymentsBundle"/>
                            <tiles:put name="isDefault" value="true"/>
                            <tiles:put name="postbackNavigation" value="true"/>
                        </tiles:insert>
                    </div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
