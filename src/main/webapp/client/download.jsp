<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<body>  
	<a href='wap/wapindex.jspx'>首页</a>-下载
	<br /> 如意彩手机客户端下载
	<c:if test="${not empty clientList }">
	<c:forEach items="${clientList }" var="client"  >
	<br />${client.platform }(${client.description })<br />
	<a href="${client.url }"><img
		src="/w/wap/images/download.jpg" alt="立即下载"></img>立即下载</a>
	<br />
	<c:if test="${client.platform eq 'iPhone' }">
iphone3.2版本更新介绍<br />
1.增加充值方式：支付宝手机安全支付；<br />
2.时时彩、11选5、十一运夺金遗漏；<br />
3.消息服务设置（开奖、中奖、追号服务的消息、短信的接收设置）；<br />
4.竞彩足球和竞彩篮球联赛选择、预计奖金计算、发起合买和参与合买家<br />
5.福彩、体彩、高频彩投注页增加历史开奖查看；<br />
6.双色球、大乐透、福彩3D的幸运选号（生肖、星座、姓名、生日等幸运选号）；<br />
7.帮助中心内容完善。<br />
	</c:if>
	<c:if test="${client.platform eq 'android' }">
	Android 3.2版本更新介绍：<br />
1、新增充值方式：支付宝安全支付，安全快捷，免输入密码。<br />
2、新增时时彩、11选5、十一运夺金的遗漏。<br />
3、新增时时彩、11选5、十一运夺金的收益率追号功能。<br />
4、新增竞彩足球和竞彩篮球的发起合买、参与合买、预计奖金计算。<br />
5、用户中心和彩票资讯位置调换，方便用户查询。<br />
6、关于我们内容更新。<br />

如有疑问，请及时沟通。<br />
	</c:if>
	</c:forEach><br/>
	如意彩客户端是北京金软瑞彩科技有限公司为全国彩民提供的一款购彩服务软件，操作简单，涵盖彩种全面，开奖信息更新及时，支持多种在线充值方式，且有全面的专家推荐信息。
主要功能如下：<br/>
1.热销彩种：<br/>
双色球、福彩3D、七乐彩、大乐透、排列三、排列五、七星彩、22选5、时时彩、11选5、十一运夺金、足彩、彩足球、竞彩篮球，满足广大彩民需求。<br/>
2.便捷的购彩功能：<br/>
机选、自选随意切换，追号、合买、赠送任您选择，新版本中增加了幸运选号功能，让您幸运享不停。<br/>
3.积分功能：<br/>
购彩充值就送积分，积分可兑换彩金。<br/>
4.充值交易：<br/>
支持多种充值方式，充值提现无手续费，交易记录可随时查看，全面保障用户资金安全。<br/>
5.开奖信息：<br/>
福彩、体彩、高频彩、足彩开奖信息全面且更新及时，包含奖池、奖项等详细信息，还有开奖走势图表。<br/>
6.账户查询：<br/>
投注、追号、合买、赠送记录可随时查看，且可随时查看到自己的留言回复。<br/>
7.帮助中心：<br/>
彩票玩法、购彩帮助等常见问题。<br/>
8.彩民英雄榜：<br/>
彩民中奖排行榜，使您榜上有名！<br/>
9.彩票资讯：<br/>
最新的活动信息，最全面的专家推荐，还有彩民趣闻、中奖故事、足彩天地。<br/>
	*本产品为免费软件，下载使用产生的流量费用由运营商收取
	<br />
	
	</c:if>
	
</body>
