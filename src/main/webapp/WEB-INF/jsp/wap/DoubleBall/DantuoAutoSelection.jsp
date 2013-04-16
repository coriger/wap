<%@ page pageEncoding="UTF-8"%>
<%@ page import="com.ruyicai.wap.util.*,java.util.*"%>
<%
	String path = CommonUtil.removeTrailingSlash(request.getContextPath());
%>
<title>双色球胆拖机选</title>
<body>

		<%
			ResourceBundle rbint = ResourceBundle.getBundle("jrtWAPSite");
			String addNumber = "";
			if (rbint.getString("addNumberSwitch").equals("1")) {
				addNumber = (String)request.getAttribute("addNumber");
				if (addNumber==null) addNumber="1";
			}
			String message = (String)request.getAttribute("message");
			String redballDanmaStr = (String)request.getAttribute("redballDanmaStr");
			String redballTuomaStr = (String)request.getAttribute("redballTuomaStr");
			String blueballCount = (String)request.getAttribute("blueballCount");
			String beishu = (String)request.getAttribute("beishu");
			if (redballDanmaStr==null) redballDanmaStr="";
			if (redballTuomaStr==null) redballTuomaStr="";
			if (blueballCount==null) blueballCount="";
			if (beishu==null) beishu="1";
			String deadline = CommonUtil.getDeadline("F47104", 0);
			if (deadline==null) deadline = "";
		%>
		<a href="/wap/wapindex.jspx">首页</a>-<a href="<%=response.encodeURL(path+"/wap/buyLottery.jspx")%>"><%
			out.print(CommonUtil.printHall());
		%></a><%
			out.print(CommonUtil.printChar());
		%>双色球
		<br/>
		<a href="/w/wap/DoubleBall/SingleSelfSelection.jspx">自选</a>|机选<br/>
		<a href="/wap/DoubleBall/SingleAutoSelection.jspx">单式机选</a>|<a href="/wap/DoubleBall/MultipleAutoSelection.jspx">复式机选</a>|胆拖机选|<a href="/wap/DoubleBall/DantuoDingdanSelection.jspx">定胆机选</a><br/>	
		
			胆码1-5个，拖码2-20个，胆码与拖码总数不能小于7、不能大于25<br/>
			<%if (deadline!=null) { %>
			<%out.print(deadline); %><br/>
		 	<%} %>
			<a style="color: red"><%if (message!=null) { %>
				<% out.print("提示：" + message); %><br/>
			<%} %></a>
			<form action="<%=response.encodeURL(path+"/doubleBallDantuo/showAutoBetDetails.jspx")%>" method="post" >
			红胆码个数:<input name="redballDanma" type="text" emptyok="false" maxlength="1" size="3" format="*N" value="<%=redballDanmaStr %>" />(1-5个)<br/>
			红拖码个数:<input name="redballTuoma" type="text" emptyok="false" maxlength="2" size="3" format="*N" value="<%=redballTuomaStr %>" />(2-20个)<br/>
			蓝球个数:　<input name="blueballCount" type="text" emptyok="false" maxlength="2" size="3" format="*N" value="<%=blueballCount %>" />(1-16个)<br/>
			倍　　数:　<input name="beishu" type="text" emptyok="false" maxlength="2" size="3" format="*N" value="<%=beishu %>" />(最多50倍)<br/>
			<%if (rbint.getString("addNumberSwitch").equals("1")) { %>
			追　　号:　<input name="addNumber" type="text" emptyok="true" maxlength="2" size="2" format="*N" value="<%=addNumber %>" />期<br/>
			(最高99期,默认追1期,即买当前期)<br/>
			<%} %>
				<input type="hidden" name="pageType" value="autoSelection" />
			<input type="submit" value="提交投注" />
			</form>
			　<br/>
			<%	List<String> list = CommonUtil.getBetCode("F47104","5"); %>
			【最新开奖】<br/>
		 <% String   code ="";
		    for(int i=0;i<list.size();i++){ 
		    	code = list.get(i) ;
		  %> 
		   <%out.print(code);%><br/>
		   <%}%><br/>
		   <a href="<%=response.encodeURL(path + "/wap/DoubleBall/DoubleBallIndex.jspx"
								)%>">返回上一页</a>
</body>