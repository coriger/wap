<%@ page pageEncoding="UTF-8"%>
<%@ page import="com.ruyicai.wap.util.*,java.util.*"%>
<%
	String path = CommonUtil.removeTrailingSlash(request
			.getContextPath());
%>
<title>七星彩投注确认</title>
<body>
<%
	Random rdm = new Random();
	int transctionId = rdm.nextInt();
	request.getSession().setAttribute(transctionId + "", "false");
	ResourceBundle rbint = ResourceBundle.getBundle("jrtWAPSite");
	//页面跳转参数
	String addNumber = "";
	if (rbint.getString("addNumberSwitch").equals("1")) {
		addNumber = (String) request.getAttribute("addNumber");
	}
	String autoMethod = (String) request.getAttribute("autoMethod");
	int zhushu = Integer.parseInt(request.getAttribute("zhushu").toString());
	String newzhuma = (String) request.getAttribute("newzhuma");
	int amount = Integer.parseInt(request.getAttribute("amount")
			.toString());
	String beishu = (String) request.getAttribute("beishu");
	int zhushuAll = zhushu*Integer.parseInt(beishu);
	String betNo = (String) request.getAttribute("betNo");
	String term = (String) request.getAttribute("term");
	String type = (String) request.getAttribute("type");
	String num1 = (String) request.getAttribute("num1"); // 第1位注数
	String num2 = (String) request.getAttribute("num2"); // 第2位注数
	String num3 = (String) request.getAttribute("num3"); // 第3位注数
	String num4 = (String) request.getAttribute("num4"); // 第4位注数
	String num5 = (String) request.getAttribute("num5"); // 第5位注数
	String num6 = (String) request.getAttribute("num6"); // 第6位注数
	String num7 = (String) request.getAttribute("num7"); // 第7位注数
	String certErr = null;
	if (request.getAttribute("certErr") != null) {
		certErr = (String) request.getAttribute("certErr");
	}
%>
 <a href="/wap/wapindex.jspx">首页</a>-<a
	href="<%=response.encodeURL(path + "/wap/buyLottery.jspx"
							)%>">
<%
	out.print(CommonUtil.printHall());
%> </a> <%
 	out.print(CommonUtil.printChar());
 %> <%
 	if (type.trim().equals("DS")) {
 %> <a
	href="<%=response.encodeURL(path
								+ "/wap/sevenStar/SingleAutoSelection.jspx"
								)%>">七星彩单式机选</a>
<%
	}
%> <%
 	if (type.trim().equals("FS")) {
 %> <a
	href="<%=response.encodeURL(path
								+ "/wap/sevenStar/MulipleAutoSelection.jspx"
								)%>">七星彩复式机选</a>
<%
	}
%> <%
 	out.print(CommonUtil.printChar());
 %>确认投注<br />
 您的投注:<br />
七星彩第<%
	out.print(term);
%>期<br />
金额: <%
	out.print(amount);
%>元<br />
注数: <%
	out.print(zhushuAll);
%>注<br />
倍数: <%
	out.print(beishu);
%>倍<br />
<%
	if (rbint.getString("addNumberSwitch").equals("1")) {
%> <%
 	if (addNumber != null && !addNumber.trim().equals("")) {
 %> 追号: <%
 	out.print(addNumber);
 %>期<br />
<%
	}
%> <%
 	}
 %> 投注号码: <%

				String s[] = newzhuma.split("<br />");
			%>
 			<%	if(type.trim().equals("DS")){	
				if(s.length<=10){
			  %>
			  <form action="/wap/sevenStar/7starAutoModify.jspx" method="post">
			  	<input type="hidden" name="zhushu" value="<%=zhushu%>" />
				<input type="hidden" name="zhuma" value="<%=betNo %>" />
				<input type="hidden" name="amount" value="<%=amount%>" />
				<input type="hidden" name="beishu" value="<%=beishu %>" />
				<input type="hidden" name="type" value="<%=type%>"/>
				<input type="hidden" name="autoMethod" value="<%=autoMethod%>"/>
				<%
					if (rbint.getString("addNumberSwitch").equals("1")) {
				%>
				<input type="hidden" name="addNumber" value="<%=addNumber%>" />
				<%
					}
				%>
			  	<input type="submit" value="修改" />
			  </form>
				<%
					}
 			}else{
 				if(s.length<=10){
 			%>
 			<form action="/wap/sevenStar/7starMulipleAutoModify.jspx" method="post">
 				<input type="hidden" name="zhushu" value="<%=zhushu%>" />
				<input type="hidden" name="zhuma" value="<%=betNo %>" />
				<input type="hidden" name="amount" value="<%=amount%>" />
				<input type="hidden" name="beishu" value="<%=beishu %>" />
				<input type="hidden" name="type" value="<%=type%>"/>
				<input type="hidden" name="autoMethod" value="<%=autoMethod%>"/>
				<input type="hidden" name="num1" value="<%=num1%>" />
				<input type="hidden" name="num2" value="<%=num2%>" />
				<input type="hidden" name="num3" value="<%=num3%>" />
				<input type="hidden" name="num4" value="<%=num4%>" />
				<input type="hidden" name="num5" value="<%=num5%>" />
				<input type="hidden" name="num6" value="<%=num6%>" />
				<input type="hidden" name="num7" value="<%=num7%>" />
				<%
					if (rbint.getString("addNumberSwitch").equals("1")) {
				%>
				<input type="hidden" name="addNumber" value="<%=addNumber%>" />
				<%
					}
				%>
 				<input type="submit" value="修改" />
 			
 			</form>
 			<%
 				}
 			}
			%><br />
<%
	if (beishu.trim().equals("")) {
		beishu = "1";
	}
	if (addNumber.trim().equals("")) {
		addNumber = "0";
	}
%> <%
 	out.print(newzhuma);
 %><br/>
 <form action="/sevenStar/placeBet.jspa" method="post">

<input type="hidden" name="zhushu" value="<%=zhushuAll%>" />
<input type="hidden" name="beishu" value="<%=beishu%>" />
<input type="hidden" name="amount" value="<%=amount%>" />
<input type="hidden" name="betNo" value="<%=betNo%>" />
<input type="hidden" name="term" value="<%=term%>" />
<input type="hidden" name="type" value="<%=type%>" />
<%
	if (rbint.getString("addNumberSwitch").equals("1")) {
%>
<input type="hidden" name="addNumber" value="<%=addNumber%>" />
<%}%>
<input type="hidden" name="token" value="<%=transctionId%>" />
<%if("1".equals(addNumber)){ %>
		<p style="color: red;">
			<input type="radio" checked="checked" value="daigou" name="buyways">普通投注
		</p>
		<p style="color: red;">
			<input type="radio" value="presented" name="buyways">我要赠送给他人
		</p>
		<p style="color: red;">
			<input type="radio" value="hemai" name="buyways">发起合买
		</p>
		<%}else{ %>
		<p style="color: red;">您已经选择了追期，无法应用赠彩、合买功能</p>
		<input type="hidden" name="buyways" value="daigou" />
		<%} %>
<input type="submit" value="确认投注" />
</form>
 <%
 	if (type.trim().equals("DS")) {
 %> 
 <%	
				if(rbint.getString("addNumberSwitch").equals("1")){
					String act = "/sevenStar/"+autoMethod+".jspx";
			%>
			<form action="<%=act %>" method="post">
			<input type="hidden" name="beishu" value="<%=beishu%>" />
			<input type="hidden" name="zhushu" value="<%=zhushu%>" />
			<input type="hidden" name="addNumber" value="<%=addNumber%>" />
			<input type="hidden" name="type" value="<%=type%>" />
			<input type="submit" value="重新选号" />
			</form>
			<%
				}else{
					String act = "/sevenStar/"+autoMethod+".jspx";
			%>
			<form action="<%=act %>" method="post">
			<input type="hidden" name="beishu" value="<%=beishu%>" />
			<input type="hidden" name="zhushu" value="<%=zhushu%>" />
			<input type="hidden" name="type" value="<%=type%>" />
			<input type="submit" value="重新选号" />
			</form>
			<%
				}
			%>
 <%
 	} else if (type.trim().equals("FS")) {
 %>
 <%String act = "/sevenStar/"+autoMethod+".jspx";
					if(rbint.getString("addNumberSwitch").equals("1")){
				%>
			<form action="<%=act %>" method="post">
			<input type="hidden" name="beishu" value="<%=beishu%>" />
			<input type="hidden" name="zhushu" value="<%=zhushu%>" />
			<input type="hidden" name="addNumber" value="<%=addNumber%>" />
			<input type="hidden" name="num1" value="<%=num1%>" />
			<input type="hidden" name="num2" value="<%=num2%>" />
			<input type="hidden" name="num3" value="<%=num3%>" />
			<input type="hidden" name="num4" value="<%=num4%>" />
			<input type="hidden" name="num5" value="<%=num5%>" />
			<input type="hidden" name="num6" value="<%=num6%>" />
			<input type="hidden" name="num7" value="<%=num7%>" />
			<input type="hidden" name="type" value="<%=type%>" />
			<input type="submit" value="重新选号" />
			</form>
				<%
					}else{
				%>
			<form action="<%=act %>" method="post">
			<input type="hidden" name="beishu" value="<%=beishu%>" />
			<input type="hidden" name="zhushu" value="<%=zhushu%>" />
			<input type="hidden" name="num1" value="<%=num1%>" />
			<input type="hidden" name="num2" value="<%=num2%>" />
			<input type="hidden" name="num3" value="<%=num3%>" />
			<input type="hidden" name="num4" value="<%=num4%>" />
			<input type="hidden" name="num5" value="<%=num5%>" />
			<input type="hidden" name="num6" value="<%=num6%>" />
			<input type="hidden" name="num7" value="<%=num7%>" />
			<input type="hidden" name="type" value="<%=type%>" />
			<input type="submit" value="重新选号" />
			</form>
				<%
					}
				%>
  <%
 	}
 %> <br />
 <%	List<String> list =CommonUtil.getBetCode("T01009","5"); %>
			【最新开奖】<br/>
		 	<% String   code ="";
		    	for(int i=0;i<list.size();i++){ 
		    	code = list.get(i) ;
		 	%> 
		   	<%out.print(code) ;%> <br/> 
			<% } %>
</body>