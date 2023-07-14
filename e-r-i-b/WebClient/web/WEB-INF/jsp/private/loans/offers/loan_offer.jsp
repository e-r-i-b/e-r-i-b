<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/private/payments/loan_offer" onsubmit="return setEmptyAction()">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="loanClaim">
           <tiles:put name="title" value="�������������� ������� ������ ��������"/>
           <tiles:put name="emptyMessage" value="����������� �� �������� �� �������"/>
           <tiles:put name="dataSize" value="${not empty form.data}"/>
           <tiles:put name="imageId" value="loanCondition"/>
           <tiles:put name="description">
                ������������ � ������ ��������, ��������� �� ��� � ������� ����� �� ������ <a class="orangeText" href="${phiz:getBankLoanLink()}" target="_blank"><span>�����</span></a>.<br/>
                ��� ���� ����� �������� ������, �������� ���������� ��� ��������� ����������� ����� � ������� �� ������ ������������.
           </tiles:put>

        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="����������� �����"/>
                <tiles:put name="action" value="/private/loan/loanoffer/show.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="�������������� ������� ������ ��������"/>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>

           <tiles:put name="data">

               <script type="text/javascript">

                   $(document).ready(function(){

                      var el = null;
                      var elCount = 0;

                     $.each($('input[name=loanId]')	,function(){
                       elCount++;
                       el = this;
                     })

                     if (elCount == 1)
                     {
                       el.onclick();
                     }
                   })

                   function setDuration(duration)
                   {
                       var durationElement = document.getElementById("duration");
                       durationElement.value = duration;
                   }
               </script>
                <input type="hidden" id="duration" name="duration"/>
                <c:set var="paramName" value="loanId"/>
                <c:set var="name" value=""/>
                <div class="loanTitle">
                 <c:forEach items="${form.data}" var="loanOffer">

                     <c:if test="${name ne loanOffer.productName}">

                        <c:if test="${name ne ''}">
                            </table>
                        </c:if>
                         <c:set var="name" value="${loanOffer.productName}"/>
                         <br/>
                         <div class="loanTitle kind"><c:out value="${name}"/></div>
                         <table class="detailInfoTable">
                             <tr>
                                 <th></th>
                                 <th>����� �������</th>
                                 <th>���������� ������</th>
                                 <th>���� �������</th>
                             </tr>
                     </c:if>
                    <c:forEach items="${loanOffer.conditions}" var="offerCondition">
                         <c:if test="${not empty offerCondition.amount and not(offerCondition.amount eq 0)}">
                             <c:set var="durationValue" value="${offerCondition.period}"/>
                             <tr>
                                 <td><input type="radio" name="${paramName}" value="${loanOffer.offerId}" onclick="setDuration('${durationValue}')"/></td>
                                 <td>�� ${phiz:formatAmountLong(offerCondition.amount)} ${phiz:getCurrencySign(loanOffer.maxLimit.currency.code)}</td>
                                 <td>${loanOffer.percentRate}%</td>
                                 <td>�� ${durationValue} ���.</td>
                             </tr>
                         </c:if>
                    </c:forEach>

                    </c:forEach>
                </table>
                </div>
           </tiles:put>
    </tiles:insert>
</html:form>