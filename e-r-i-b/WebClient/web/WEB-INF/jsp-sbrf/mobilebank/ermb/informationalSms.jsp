 <%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>

 <div id="inSystem-items">
    <div class="interface-items" >
        <c:if test="${not empty form.cards}">
            <div class="user-products simpleTable">
                <fieldset>
                    <table width="100%">
                        <tr class="tblInfHeader">
                            <th class="align-left titleTable">
                                <bean:message bundle="ermbBundle" key="title.cards"/>
                            </th>
                            <th class="align-left titleTable">
                                <bean:message bundle="ermbBundle" key="title.balance"/>
                                <img src="${imagePath}/sms.png" alt="" border="0"/>
                            </th>
                            <th class="align-left titleTable">
                                <bean:message bundle="ermbBundle" key="title.last.operations"/>
                                <img src="${imagePath}/sms.png" alt="" border="0"/>
                            </th>
                            <th class="align-left titleTable">
                                <bean:message bundle="ermbBundle" key="title.block"/>
                                <img src="${imagePath}/sms.png" alt="" border="0"/>
                            </th>
                        </tr>

                        <logic:iterate id="listItem" name="form" property="cards">
                            <c:set var="state" value="${listItem.value.cardState}"/>

                            <c:choose>
                                <c:when test="${listItem.ermbSmsAlias != null}">
                                     <c:set var="alias" value="${listItem.ermbSmsAlias}"/>
                                </c:when>
                                <c:otherwise>
                                   <c:set var="alias" value="${listItem.autoSmsAlias}"/>
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${not(state eq 'closed' or state eq 'blocked' )}">
                                <tr>
                                    <td class="align-left inSystem">
                                        <div class="products-text-style">
                                            <span class="word-wrap"><bean:write name="listItem" property="name"/></span>
                                            <span class="card-number">${phiz:getCutCardNumber(listItem.number)}</span>&nbsp;
                                            <c:set var="spanClass" value="text-green"/>
                                        </div>
                                    </td>
                                    <td class="align-left" style="width:20%">
                                        <div class="products-text-style">
                                            <b>SMS:</b> Баланс ${alias}
                                        </div>
                                    </td>
                                    <td class="align-left" style="width:26%">
                                        <div class="products-text-style">
                                            <b>SMS:</b> История ${alias}
                                        </div>
                                    </td>
                                    <td class="align-left" style="width:19%;">
                                        <div class="products-text-style">
                                            <b>SMS:</b> Блок ${alias}
                                        </div>
                                    </td>
                                </tr>
                            </c:if>
                        </logic:iterate>
                        <tr>
                            <td colspan="4" class="align-left">
                                <div class="products-text-style">

                                    <input id="CARD" type="checkbox" onclick="hideOrShowClosed(this, 'tableClosedCardsInSys', this.id)" name="selectedClosedProducts" value="CARD" ${not empty confirmRequest ? 'disabled' : ''}/>

                                    <c:if test="${not empty confirmRequest}">
                                        <input id="hiddenCARD" type="hidden" name="selectedClosedProducts"/>
                                    </c:if>
                                    <b>
                                        <span>Показать закрытые</span>
                                    </b>
                                </div>
                            </td>
                        </tr>
                        <logic:iterate id="item" name="form" property="cards">
                            <c:set var="state" value="${item.value.cardState}"/>
                            <c:choose>
                                <c:when test="${item.ermbSmsAlias != null}">
                                     <c:set var="alias" value="${item.ermbSmsAlias}"/>
                                </c:when>
                                <c:otherwise>
                                   <c:set var="alias" value="${item.autoSmsAlias}"/>
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${state eq 'closed' or state eq 'blocked'}">
                                <tr id="tableClosedCardsInSys">
                                    <td class="align-left inSystem">
                                        <div class="products-text-style">
                                            <span class="word-wrap"><bean:write name="item" property="name"/></span>
                                            <span class="card-number">${phiz:getCutCardNumber(item.number)}</span>&nbsp;
                                            <c:set var="spanClass" value="text-green"/>
                                        </div>
                                    </td>
                                    <td class="align-left" style="width:20%">
                                        <div class="products-text-style">
                                            <b>SMS:</b> Баланс ${alias}
                                        </div>
                                    </td>
                                    <td class="align-left" style="width:25%">
                                        <div class="products-text-style">
                                            <b>SMS:</b> История ${alias}
                                        </div>
                                    </td>
                                </tr>
                                <script type="text/javascript">
                                    addClosed('CARD', '${item.id}');
                                </script>
                            </c:if>
                        </logic:iterate>
                        <script type="text/javascript">
                            hideOrShowClosed(null, 'tableClosedCardsInSys', 'CARD');
                        </script>
                    </table>
                </fieldset>
            </div>
        </c:if>      
    </div>
</div>
