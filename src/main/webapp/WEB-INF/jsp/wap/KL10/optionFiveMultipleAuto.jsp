<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<title>广东快乐十分</title>
<body>
<a href="/w/wap/wapindex.jspx">首页</a>-<a href="/w/wap/buyLottery.jspx">购彩大厅</a>-广东快乐十分<br/>
		任选|<a 
		   href="/w/guangDongHappyTenMinutesIndex/selectTwoLinkDirectSelf.jspx">直选</a>|<a 
		   href="/w/guangDongHappyTenMinutesIndex/selectTwoLinkGroupSelf.jspx">组选</a>|<a 
		   href="/w/guangDongHappyTenMinutesIndex/optionTwoDanTuoSelf.jspx">胆拖</a><br/>
		<a 
		   href="/w/guangDongHappyTenMinutesIndex/selectOneNumberSelf.jspx">前一数投</a>|<a 
		   href="/w/guangDongHappyTenMinutesIndex/selectOneRedSelf.jspx">前一红投</a>|<a 
		   href="/w/guangDongHappyTenMinutesIndex/optionTwoSelf.jspx">任二</a>|<a 
		   href="/w/guangDongHappyTenMinutesIndex/optionThreeSelf.jspx">任三</a>|<a 
		   href="/w/guangDongHappyTenMinutesIndex/optionFourSelf.jspx">任四</a>|任五<br/>
		 <a 
		   href="/w/guangDongHappyTenMinutesIndex/optionFiveSelf.jspx">自选</a>|<a 
		   href="/w/guangDongHappyTenMinutesIndex/optionFiveSingleAuto.jspx">单式机选</a>|复式机选<br/>
		 ${headLine }
 		<form action="/guangDongHappyTenMinutesIndex/optionFiveMultipleAuto.jspx" method="post">
 		<input type="submit" value="刷新" />
 		</form><br />
		  
 	<c:if test="${not empty messageError }">
 		提示信息：<span style="color: red;"> ${messageError }</span><br/>
 	</c:if>

	<form action="/guangDongHappyTenMinutes/optionMultipleAutoDetail.jspx" method="post">
	机选: <input type="text" name="geShu"  maxlength="2" size="2" value="${geShu }">个(6-18)<br/>
	倍数: <input type="text" name="beiShu"  maxlength="2" size="2" value="${beiShu }">倍(最多99倍) <br/>
	追号: <input type="text" name="addNumber"  maxlength="2" size="2" value="${addNumber }">期(最多99期,追1期即买当前期) <br/>
	<input type="hidden" name="type" value="${type }">
	<input type="hidden" name="batchCode" value="${batchCode }">
	<input type="submit"   value="提交投注"> <br/>
	</form>
		
	任五：投注号码与开奖号任意5个号码一致，奖金320元<br/>	
		【最新开奖】<br/>	
	<c:if test="${not empty winCodeList }">
		<c:forEach items="${winCodeList }" var="winCode">
			${winCode }<br/>
		</c:forEach>
	</c:if>
</body>