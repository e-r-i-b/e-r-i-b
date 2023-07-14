<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<%--
id - идентификатор
hintVal1 - подсказка для зеленой области
hintVal2 - подсказка для оранжевой области
hintVal3 - подсказка справа
--%>

<div class="battery">
    <div id="activeBattery${id}">
        <%-- Подсказка, располоденная над батарейкой --%>
        <div class="batteryHintUp" id="batteryHintUp${id}">

            <div class="batteryHint">
                <div class="batteryHintRT r-top">
                    <div class="batteryHintLT r-top-left"><div class="batteryHintRT r-top-right"><div class="batteryHintCT r-top-center"></div></div></div>
                </div>
                <div class="batteryHintLC r-center-left">
                    <div class="batteryHintCR r-center-right">
                        <div class="batteryHintCC r-content">
                            <span class="italic">
                                ${hintVal1}
                                <br/><span class="hintVal"></span>
                                <bean:message bundle="pfpBundle" key="currency.rub"/>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="batteryHintLB r-bottom-left">
                    <div class="batteryHintRB r-bottom-right">
                        <div class="batteryHintCB r-bottom-center"></div>
                    </div>
                 </div>
            </div>
            <div class="clear"></div>
            <div class="batteryHintUpBottom"></div>
        </div>

        <div class="clear"></div>

        <div class="batteryShadowUp r-top"></div>
        <div class="batteryWithHint">
            <div id="battery${id}" class="batteryContent float">

                <div class="batteryRight"></div>
                <div class="batteryLeft"></div>

                <div class="batteryCenter">
                    <div class="batteryMain">
                        <div class="innerBattery"></div>
                    </div>
                </div>

            </div>

            <div class="float">
                <%-- Подсказка, располоденная справа от батарейки --%>
                <div class="batteryHintRight" id="batteryHintRight${id}">
                    <div class="batteryHint">
                        <div class="batteryHintRT r-top">
                            <div class="batteryHintLT r-top-left"><div class="batteryHintRT r-top-right"><div class="batteryHintCT r-top-center"></div></div></div>
                        </div>
                        <div class="batteryHintLC r-center-left">
                            <div class="batteryHintCR r-center-right">
                                <div class="batteryHintCC r-content">
                                    <span class="italic">
                                        ${hintVal3}
                                        <br/><span class="hintVal"></span>
                                        <bean:message bundle="pfpBundle" key="currency.rub"/>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="batteryHintLB r-bottom-left">
                            <div class="batteryHintRB r-bottom-right">
                                <div class="batteryHintCB r-bottom-center"></div>
                            </div>
                         </div>
                    </div>
                    <div class="batteryHintRightLeft"></div>
                </div>
            </div>
        </div>
        <div class="clear"></div>
        <div class="batteryShadowDown r-top"></div>

        <%-- Подсказка, располоденная под батарейкой --%>
        <div class="batteryHintDown" id="batteryHintDown${id}">
            <div class="batteryHintDownTop r-top"></div>
            <div class="batteryHint">
                <div class="batteryHintRT r-top">
                    <div class="batteryHintLT r-top-left"><div class="batteryHintRT r-top-right"><div class="batteryHintCT r-top-center"></div></div></div>
                </div>
                <div class="batteryHintLC r-center-left">
                    <div class="batteryHintCR r-center-right">
                        <div class="batteryHintCC r-content">
                            <span class="italic">
                                ${hintVal2}
                                <br/><span class="hintVal"></span>
                                <bean:message bundle="pfpBundle" key="currency.rub"/>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="batteryHintLB r-bottom-left">
                    <div class="batteryHintRB r-bottom-right">
                        <div class="batteryHintCB r-bottom-center"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id="notActiveBattery${id}" class="notActiveBattery"></div>

</div>