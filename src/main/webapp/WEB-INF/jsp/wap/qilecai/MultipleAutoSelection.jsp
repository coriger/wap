<%@ page pageEncoding="UTF-8"%>
<%@ page import="com.ruyicai.wap.util.*,java.util.*,org.apache.log4j.Logger"%><%
	String path = CommonUtil.removeTrailingSlash(request.getContextPath());
%>
	<title>七乐彩复式机选</title>
<body>

		<%
			ResourceBundle rbint = ResourceBundle.getBundle("jrtWAPSite");
			String addNumber = "";
			if (rbint.getString("addNumberSwitch").equals("1")) {
				addNumber = (String)request.getAttribute("addNumber");
				if (addNumber==null) addNumber="1";
			}
			String message = (String)request.getAttribute("message");
			String zhushu = (String)request.getAttribute("zhushu");
			if(zhushu==null) zhushu = "5";
			String beishu = (String)request.getAttribute("beishu");
			if(beishu==null) beishu = "1";
			String deadline = CommonUtil.getDeadline("F47102", 0);
			if (deadline==null) deadline = "";
		%>
		<a href="/wap/wapindex.jspx">首页</a>-<a href="<%=response.encodeURL(path+"/wap/buyLottery.jspx")%>"><%
			out.print(CommonUtil.printHall());
		%></a><%
			out.print(CommonUtil.printChar());
		%>七乐彩<br/>
		<a href="/w/wap/qilecai/SingleSelfSelection.jspx">自选</a>|机选<br/>
		<a href="/wap/qilecai/SingleAutoSelection.jspx">单式机选</a>|复式机选|<a href="/wap/qilecai/DingDanAutoSelection.jspx">定胆机选</a><br/>
		
			<%if (deadline!=null) { %>
			<%out.print(deadline); %><br/>
		 	<%} %>
			<a style="color: red"><% if(message!=null){
					out.print(CommonUtil.printWarningMessage(message)+"<br/>");
			   }%></a>
			   <form action="<%=response.encodeURL(path+"/qilecai/multipleAutoSelection.jspx")%>" method="post">
			   选择号码个数<br/>
			<select name="num">
				<option value="8">8个</option>
				<option value="9">9个</option>
				<option value="10">10个</option>
				<option value="11">11个</option>
				<option value="12">12个</option>
				<option value="13">13个</option>
				<option value="14">14个</option>
				<option value="15">15个</option>
				<option value="16">16个</option>
			</select><br/>
			倍数:<input name="beishu" type="text" emptyok="false" maxlength="2" size="2" format="*N" value="<%=beishu %>" />(最多50倍)<br/>
			<%if (rbint.getString("addNumberSwitch").equals("1")) { %>
			追号:<input name="addNumber" type="text" emptyok="true" maxlength="2" size="2" format="*N" value="<%=addNumber %>" />期<br/>
			(最高99期,默认追1期,即买当前期)<br/>
			<%} %>
			<input type="hidden" name="type" value="JXFS"/>
			<input type="submit" value="提交投注"/>
			   </form>
			<br/>
			<%	List<String> list = CommonUtil.getBetCode("F47102","5"); %>
			【最新开奖】<br/>
		 <% String   code ="";
		    for(int i=0;i<list.size();i++){ 
		    	code = list.get(i) ;
		  %> 
		   <%out.print(code) ;%> <br/>  
		   <% } %>
		      <br/>
		   <a href="<%=response.encodeURL(path + "/wap/qilecai/QilecaiIndex.jspx"
								)%>">返回上一页</a>
		</body>	