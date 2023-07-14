<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<tiles:insert definition="leftMenuInset" operation="EditSegmentAvailableOperation" flush="false">
    <tiles:put name="enabled" value="${submenu != 'editAvailableSegment'}"/>
    <tiles:put name="action"  value="/pfp/configure/segments.do"/>
    <tiles:put name="text">
        <bean:message bundle="pfpConfigureBundle" key="segments.label.leftMenu"/>
    </tiles:put>
    <tiles:put name="title">
        <bean:message bundle="pfpConfigureBundle" key="segments.label.leftMenu"/>
    </tiles:put>
</tiles:insert>
<tiles:insert definition="leftMenuInsetGroup" service="PFPManagment" flush="false">
    <tiles:put name="text">
        <bean:message bundle="pfpTargetsBundle" key="left.menu.group"/>
    </tiles:put>
    <tiles:put name="name" value="personalData"/>
    <tiles:put name="name"    value="pfp_dictionaries"/>
    <tiles:put name="enabled" value="${submenu != 'loanKindProductList' && submenu != 'loanKindProductEdit' &&
                                       submenu != 'targetList' && submenu != 'targetEdit' &&
                                       submenu != 'targetCountEdit' &&
                                       submenu != 'riskProfileQuestionList' && submenu != 'riskProfileQuestionEdit' &&
                                       submenu != 'ageCategoryProductList' && submenu != 'ageCategoryProductEdit' &&
                                       submenu != 'riskProfileList' && submenu != 'riskProfileEdit' &&
                                       submenu != 'pfpConfigure' &&
                                       submenu != 'editDefaultPeriod' &&
                                       submenu != 'listChannel' && submenu != 'editChannel'}"/>
    <tiles:put name="data">
        <tiles:insert definition="leftMenuInset" operation="EditDisplayingRecommendationsOperation" flush="false">
            <tiles:put name="enabled" value="${submenu != 'pfpConfigure'}"/>
            <tiles:put name="action"  value="/pfp/configure.do"/>
            <tiles:put name="text">
                <bean:message bundle="pfpConfigureBundle" key="left.menu.title"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpConfigureBundle" key="left.menu.title"/>
            </tiles:put>
            <tiles:put name="parentName" value="pfp_dictionaries"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListLoanKindProductsOperation" flush="false">
            <tiles:put name="enabled" value="${submenu != 'loanKindProductList' && submenu != 'loanKindProductEdit'}"/>
            <tiles:put name="action"  value="/pfp/loanKind/list.do"/>
            <tiles:put name="text">
                <bean:message bundle="pfpProductBundle" key="loanKind.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpProductBundle" key="loanKind.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="parentName" value="pfp_dictionaries"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListTargetsOperations" flush="false">
            <tiles:put name="enabled" value="${submenu != 'targetList' && submenu != 'targetEdit'}"/>
            <tiles:put name="action"  value="/pfp/targets/list.do"/>
            <tiles:put name="text">
                <bean:message bundle="pfpTargetsBundle" key="label.leftMenu"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpTargetsBundle" key="label.leftMenu"/>
            </tiles:put>
            <tiles:put name="parentName" value="pfp_dictionaries"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="EditTargetCountOperation" flush="false">
            <tiles:put name="enabled" value="${submenu != 'targetCountEdit'}"/>
            <tiles:put name="action"  value="/pfp/targets/count/edit.do"/>
            <tiles:put name="text">
                <bean:message bundle="pfpTargetsBundle" key="targetCount.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpTargetsBundle" key="targetCount.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="parentName" value="pfp_dictionaries"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListRiskProfileQuestionOperation" flush="false">
            <tiles:put name="enabled" value="${submenu != 'riskProfileQuestionList' && submenu != 'riskProfileQuestionEdit'}"/>
            <tiles:put name="action"  value="/pfp/riskProfile/question/list.do"/>
            <tiles:put name="text">
                <bean:message bundle="pfpRiskProfileBundle" key="question.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpRiskProfileBundle" key="question.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="parentName" value="pfp_dictionaries"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListAgeCategoriesOperation" flush="false">
            <tiles:put name="enabled" value="${submenu != 'ageCategoryProductList' && submenu != 'ageCategoryProductEdit'}"/>
            <tiles:put name="action"  value="/pfp/riskProfile/ageCategory/list.do"/>
            <tiles:put name="text">
                <bean:message bundle="pfpRiskProfileBundle" key="ageCategory.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpRiskProfileBundle" key="ageCategory.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="parentName" value="pfp_dictionaries"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListRiskProfileOperation" flush="false">
            <tiles:put name="enabled" value="${submenu != 'riskProfileList' && submenu != 'riskProfileEdit'}"/>
            <tiles:put name="action"  value="/pfp/riskProfile/list.do"/>
            <tiles:put name="text">
                <bean:message bundle="pfpRiskProfileBundle" key="label.leftMenu"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpRiskProfileBundle" key="label.leftMenu"/>
            </tiles:put>
            <tiles:put name="parentName" value="pfp_dictionaries"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="EditDefaultPeriodSettingOperation" flush="false">
            <tiles:put name="enabled" value="${submenu != 'editDefaultPeriod'}"/>
            <tiles:put name="action"  value="/pfp/configure/period.do"/>
            <tiles:put name="text">
                <bean:message bundle="pfpConfigureBundle" key="period.default.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpConfigureBundle" key="period.default.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="parentName" value="pfp_dictionaries"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="EditChannelOperation" flush="false">
            <tiles:put name="enabled" value="${submenu != 'listChannel' && submenu != 'editChannel'}"/>
            <tiles:put name="action"  value="/pfp/channel/list.do"/>
            <tiles:put name="text">
                <bean:message bundle="pfpChannelBundle" key="left.menu.item"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpChannelBundle" key="left.menu.item"/>
            </tiles:put>
            <tiles:put name="parentName" value="pfp_dictionaries"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
<tiles:insert definition="leftMenuInsetGroup" service="PFPManagment" flush="false">
    <tiles:put name="text">
        <bean:message bundle="pfpInsuranceBundle" key="left.menu.group"/>
    </tiles:put>
    <tiles:put name="name" value="products"/>
    <tiles:put name="enabled" value="${submenu != 'insuranceTypeList' && submenu != 'insuranceTypeEdit' &&
                                       submenu != 'insurancePeriodTypeList' && submenu != 'insurancePeriodTypeEdit' &&
                                       submenu != 'companiesList' && submenu != 'companyEdit' &&
                                       submenu != 'insuranceList' && submenu != 'insuranceEdit' &&
                                       submenu != 'accountProductList' && submenu != 'accountProductEdit' &&
                                       submenu != 'fundProductList' && submenu != 'fundProductEdit' &&
                                       submenu != 'imaProductList' && submenu != 'imaProductEdit' &&
                                       submenu != 'trustManagingProductList' && submenu != 'trustManagingProductEdit' &&
                                       submenu != 'pensionProductList' && submenu != 'pensionProductEdit' &&
                                       submenu != 'complexInsuranceProductList' && submenu != 'complexInsuranceProductEdit' &&
                                       submenu != 'complexFundInvestmentProductList' && submenu != 'complexFundInvestmentProductEdit' &&
                                       submenu != 'complexIMAInvestmentProductList' && submenu != 'complexIMAInvestmentProductEdit' &&
                                       submenu != 'listProductType' && submenu != 'editProductType' &&
                                       submenu != 'editCardRecommendation' &&
                                       submenu != 'listCardProduct' && submenu != 'editCardProduct' &&
                                       submenu != 'listRisk' && submenu != 'editRisk' &&
                                       submenu != 'listInvestmentPeriod' && submenu != 'editInvestmentPeriod' &&
                                       submenu != 'pensionFundList' && submenu != 'pensionFundEdit'}"/>
    <tiles:put name="data">
        <tiles:insert definition="leftMenuInset" operation="ListPFPCardOperation" flush="false">
            <tiles:put name="enabled" value="${submenu ne 'listCardProduct' and submenu ne 'editCardProduct'}"/>
            <tiles:put name="action"  value="/pfp/products/card/list"/>
            <tiles:put name="text">
                <bean:message bundle="pfpCardBundle" key="left.menu.item"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpCardBundle" key="left.menu.item"/>
            </tiles:put>
            <tiles:put name="parentName" value="products"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="EditUseCreditCardRecommendationOperation" flush="false">
            <tiles:put name="enabled" value="${submenu ne 'editCardRecommendation'}"/>
            <tiles:put name="action"  value="/pfp/products/card/recommendation/edit"/>
            <tiles:put name="text">
                <bean:message bundle="pfpCardRecommendationBundle" key="left.menu.item"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpCardRecommendationBundle" key="left.menu.item"/>
            </tiles:put>
            <tiles:put name="parentName" value="products"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListRiskOperation" flush="false">
            <tiles:put name="enabled" value="${submenu ne 'listRisk' and submenu ne 'editRisk'}"/>
            <tiles:put name="action"  value="/pfp/risk/list"/>
            <tiles:put name="text">
                <bean:message bundle="pfpRiskBundle" key="left.menu.item"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpRiskBundle" key="left.menu.item"/>
            </tiles:put>
            <tiles:put name="parentName" value="products"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListInvestmentPeriodOperation" flush="false">
            <tiles:put name="enabled" value="${submenu ne 'listInvestmentPeriod' and submenu ne 'editInvestmentPeriod'}"/>
            <tiles:put name="action"  value="/pfp/period/investment/list"/>
            <tiles:put name="text">
                <bean:message bundle="pfpInvestmentPeriodBundle" key="left.menu.item"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpInvestmentPeriodBundle" key="left.menu.item"/>
            </tiles:put>
            <tiles:put name="parentName" value="products"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListProductTypeParametersOperation" flush="false">
            <tiles:put name="enabled" value="${submenu ne 'listProductType' and submenu ne 'editProductType'}"/>
            <tiles:put name="action"  value="/pfp/products/types/list"/>
            <tiles:put name="text">
                <bean:message bundle="pfpProductTypeBundle" key="left.menu.item"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpProductTypeBundle" key="left.menu.item"/>
            </tiles:put>
            <tiles:put name="parentName" value="products"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListPensionFundOperation" flush="false">
            <tiles:put name="enabled" value="${submenu != 'pensionFundList' && submenu != 'pensionFundEdit'}"/>
            <tiles:put name="action"  value="/pfp/products/pension/fund/list"/>
            <tiles:put name="text">
                <bean:message bundle="pfpPensionFundBundle" key="label.leftMenu"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpPensionFundBundle" key="label.leftMenu"/>
            </tiles:put>
            <tiles:put name="parentName" value="products"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListInsuranceTypeOperation" flush="false">
            <tiles:put name="enabled" value="${submenu != 'insuranceTypeList' && submenu != 'insuranceTypeEdit'}"/>
            <tiles:put name="action"  value="/pfp/insurance/typeList.do"/>
            <tiles:put name="text">
                <bean:message bundle="pfpInsuranceBundle" key="type.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpInsuranceBundle" key="type.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="parentName" value="products"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListInsurancePeriodTypeOperation" flush="false">
            <tiles:put name="enabled" value="${submenu != 'insurancePeriodTypeList' && submenu != 'insurancePeriodTypeEdit'}"/>
            <tiles:put name="action"  value="/pfp/insurance/periodTypeList"/>
            <tiles:put name="text">
                <bean:message bundle="pfpInsuranceBundle" key="periodType.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpInsuranceBundle" key="periodType.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="parentName" value="products"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListInsuranceCompaniesOperation" flush="false">
            <tiles:put name="enabled" value="${submenu != 'companiesList' && submenu != 'companyEdit'}"/>
            <tiles:put name="action"  value="/pfp/insurance/companiesList.do"/>
            <tiles:put name="text">
                <bean:message bundle="pfpInsuranceBundle" key="company.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpInsuranceBundle" key="company.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="parentName" value="products"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListInsuranceProductOperation" flush="false">
            <tiles:put name="enabled" value="${submenu != 'insuranceList' && submenu != 'insuranceEdit'}"/>
            <tiles:put name="action"  value="/pfp/insurance/productList.do"/>
            <tiles:put name="text">
                <bean:message bundle="pfpInsuranceBundle" key="label.leftMenu"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpInsuranceBundle" key="label.leftMenu"/>
            </tiles:put>
            <tiles:put name="parentName" value="products"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListPensionProductOperation" flush="false">
            <tiles:put name="enabled" value="${submenu != 'pensionProductList' && submenu != 'pensionProductEdit'}"/>
            <tiles:put name="action"  value="/pfp/products/pension/list"/>
            <tiles:put name="text">
                <bean:message bundle="pfpPensionBundle" key="label.leftMenu"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpPensionBundle" key="label.leftMenu"/>
            </tiles:put>
            <tiles:put name="parentName" value="products"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListAccountProductsOperation" flush="false">
            <tiles:put name="enabled" value="${submenu != 'accountProductList' && submenu != 'accountProductEdit'}"/>
            <tiles:put name="action"  value="/pfp/products/account/list.do"/>
            <tiles:put name="text">
                <bean:message bundle="pfpProductBundle" key="account.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpProductBundle" key="account.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="parentName" value="products"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListFundProductsOperation" flush="false">
            <tiles:put name="enabled" value="${submenu != 'fundProductList' && submenu != 'fundProductEdit'}"/>
            <tiles:put name="action"  value="/pfp/products/fund/list.do"/>
            <tiles:put name="text">
                <bean:message bundle="pfpProductBundle" key="fund.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpProductBundle" key="fund.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="parentName" value="products"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListIMAProductsOperation" flush="false">
            <tiles:put name="enabled" value="${submenu != 'imaProductList' && submenu != 'imaProductEdit'}"/>
            <tiles:put name="action"  value="/pfp/products/ima/list.do"/>
            <tiles:put name="text">
                <bean:message bundle="pfpProductBundle" key="ima.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpProductBundle" key="ima.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="parentName" value="products"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListTrustManagingProductOperation" flush="false">
            <tiles:put name="enabled" value="${submenu != 'trustManagingProductList' && submenu != 'trustManagingProductEdit'}"/>
            <tiles:put name="action"  value="/pfp/products/trustManaging/list"/>
            <tiles:put name="text">
                <bean:message bundle="pfpTrustManagingProductBundle" key="label.leftMenu"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpTrustManagingProductBundle" key="label.leftMenu"/>
            </tiles:put>
            <tiles:put name="parentName" value="products"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListComplexInsuranceProductsOperation" flush="false">
            <tiles:put name="enabled" value="${submenu != 'complexInsuranceProductList' && submenu != 'complexInsuranceProductEdit'}"/>
            <tiles:put name="action"  value="/pfp/products/complex/insurance/list.do"/>
            <tiles:put name="text">
                <bean:message bundle="pfpProductBundle" key="products.complex.insurance.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpProductBundle" key="products.complex.insurance.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="parentName" value="products"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListComplexFundInvestmentProductsOperation" flush="false">
            <tiles:put name="enabled" value="${submenu != 'complexFundInvestmentProductList' && submenu != 'complexFundInvestmentProductEdit'}"/>
            <tiles:put name="action"  value="/pfp/products/complex/investment/fund/list.do"/>
            <tiles:put name="text">
                <bean:message bundle="pfpProductBundle" key="products.complex.investment.fund.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpProductBundle" key="products.complex.investment.fund.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="parentName" value="products"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" operation="ListComplexIMAInvestmentProductsOperation" flush="false">
            <tiles:put name="enabled" value="${submenu != 'complexIMAInvestmentProductList' && submenu != 'complexIMAInvestmentProductEdit'}"/>
            <tiles:put name="action"  value="/pfp/products/complex/investment/ima/list.do"/>
            <tiles:put name="text">
                <bean:message bundle="pfpProductBundle" key="products.complex.investment.ima.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="pfpProductBundle" key="products.complex.investment.ima.label.leftMenu"/>
            </tiles:put>
            <tiles:put name="parentName" value="products"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
