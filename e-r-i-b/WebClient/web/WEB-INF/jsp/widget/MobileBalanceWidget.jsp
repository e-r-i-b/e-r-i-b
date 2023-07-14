<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="attention" value="${form.lowBalance}"/>
<c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/payments/servicesPayments/edit.do')}"/>

<tiles:insert definition="widget" flush="false">
    <tiles:put name="digitClassname" value="widget.MobileBalanceWidget"/>
    <tiles:put name="cssClassname" value="MobileBalanceWidget"/>
    <c:choose>
        <c:when test="${attention}">
            <tiles:put name="borderColor" value="orangeTop"/>
        </c:when>
        <c:otherwise>
            <tiles:put name="borderColor" value="greenTop"/>
        </c:otherwise>
    </c:choose>

    <%-- ���������  --%>
    <tiles:put name="editPanel">
        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">���:</tiles:put>
            <tiles:put name="data">
                <select field="size">
                    <option value="compact">����������</option>
                    <option value="wide">������</option>
                </select>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">������� ��������:</tiles:put>
            <tiles:put name="data">
                <select field="provider">
                    <option value="������">������</option>
                    <option value="�������">������� ������</option>
                    <option value="���">���</option>
                </select>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">����� ��������:</tiles:put>
            <tiles:put name="data"><input type="text" field="phoneNumber" size="16" maxlength="10"/></tiles:put>
        </tiles:insert>

        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">������:</tiles:put>
            <tiles:put name="data"><input type="password" field="password" size="16" maxlength="20"/></tiles:put>
        </tiles:insert>

        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">��������� ������:</tiles:put>
            <tiles:put name="data"><input type="text" field="thresholdValue" size="16" maxlength="20" /></tiles:put>
        </tiles:insert>
    </tiles:put>

    <%-- �����������  --%>
    <tiles:put name="viewPanel">
        <%-- �������� --%>
        <div class="icon">
            <img src="${skinUrl}/images/icon_mobilebalance.png" border="0"/>
        </div>

        <%-- �������� --%>
        <div class="compactPane">
            <%-- ����� �������� � �������� ������ --%>
            <div class="phone_info">
                <span label="phoneNumber" class="phoneNumber"></span><br/>
                <span label="provider" class="provider"></span>
            </div>
            <%-- ������ "�����������" ������� --%>
            <div panel="compact">
                <c:choose>
                    <c:when test="${form.balance != null}">
                        <div class="compactBalance">
                            <tiles:insert definition="roundedPlate" flush="false">
                                <tiles:put name="data">
                                    ${phiz:formatAmount(form.balance)}
                                </tiles:put>
                                <c:if test="${attention}">
                                    <tiles:put name="color" value="orange"/>
                                </c:if>
                            </tiles:insert>
                        </div>
                        <div class="enlargeBalance">
                            <a href="${url}${form.paymentUrl}">��������� ������</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <label style="float:left;">��������, ������ �������� ����������.</label>
                    </c:otherwise>
                </c:choose>
            </div>
            <%-- ������ "��������" ������� --%>
            <div panel="wide" class="widePanel">
                <c:choose>
                    <c:when test="${form.balance != null}">
                        <div>
                            <tiles:insert definition="roundedPlate" flush="false">
                                <tiles:put name="data">
                                    ${phiz:formatAmount(form.balance)}
                                </tiles:put>
                                <c:if test="${attention}">
                                    <tiles:put name="color" value="orange"/>
                                </c:if>
                            </tiles:insert>
                            <div class="enlargeBalance">
                                <a href="${url}${form.paymentUrl}">��������� ������</a>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <label style="float:right;">��������, ������ �������� ����������.</label>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </tiles:put>
</tiles:insert>
