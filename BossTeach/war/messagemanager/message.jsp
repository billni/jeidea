<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
	<title>北京博胜天成管理咨询有限公司</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="北京博胜天成管理咨询有限公司,博胜天成,管理咨询,consultant,boss,teach,service"/>	 
	<meta name="description" content="boss teach and boss teacher"/>

	<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/css/layout.css" rel="stylesheet" type="text/css" />	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/footer.js"></script>	
</head>

<body id="index_5">
	<div id="header_tall">
		<div id="main">
			<!--header -->
			<div id="header">
				<div class="h_logo">
					<div class="left">
						<img alt="" src="img/logo.jpg" /><br />
					</div>
					<div class="right"><a href="#">RSS</a></div>
					<div class="clear"></div>
				</div>
				<div id="menu">
					<div class="rightbg">
						<div class="leftbg">
							<div class="padding">
								<ul>
									<li><a href="index.html">首页</a></li>
									<li><a href="index-1.html">新闻中心</a></li>
									<li><a href="index-2.html">博胜学院</a></li>
									<li><a href="index-3.html">咨询服务</a></li>
									<li><a href="index-4.html">产品研发</a></li>	
									<li class="last"><span>联系我们</span></li>
								</ul>
								<br class="clear" />
							</div>
						</div>
					</div>
				</div>
				<div class="content">
						<img alt="" src="img/header_t1.jpg" /><br />
						<img alt="" src="img/header_t2.jpg" /><br />
						<div class="text">
							博胜天成管理咨询的特色是基于企业实际，把握企业内外环境，融会先进的管理思想与丰富的管理实践，从发展战略入手，为企业提供个性化、可操作的管理咨询方案。
						</div>
						<a href="#">
							<div style="background:url('img/header_click_here.jpg') no-repeat;width:100px;height:50px;padding:7px 0px 0px 25px;">立即联系</div>
						</a>
						<div class="clear"></div>
				</div>
			</div>
			<!--header end-->
			<div id="middle">
				<div class="indent">
					<div class="columns1">
						<div class="column1">
							<div class="border">
								<div class="btall">
									<div class="ltall">
										<div class="rtall">
											<div class="tleft">
												<div class="tright">
													<div class="bleft">
														<div class="bright">
															<div class="ind">
																<div class="h_text">
																	<img alt="" src="img/6-t1.jpg" /><br />
																</div>
																<div class="padding">
																	<strong>北京博胜天成管理咨询有限公司</strong><br />
																	<p class="p1">
																		8901 Marmora Road,<br />
																		Glasgow, D04 89GR.<br /><br />
																		
																		Freephone: &nbsp; &nbsp; &nbsp;+010 800 559 6580<br />
																		Telephone: &nbsp; &nbsp; &nbsp; +010 959 603 6035<br />
																		FAX: &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;+1 504 889 9898<br />
																		E-mail: <a href="mailto:example@phplamp.com?subject=请尽快联系我">bossteach2012@gmail.com</a>																	</p>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="indent_column">&nbsp;</div>
						<div class="column2">
							<div class="border">
								<div class="btall">
									<div class="ltall">
										<div class="rtall">
											<div class="tleft">
												<div class="tright">
													<div class="bleft">
														<div class="bright">
															<div class="ind">
																<div class="h_text">
																	<img alt="" src="img/6-t2.jpg" /><br />
																</div>
																<div class="padding">
																	<strong>Argentina</strong><br />
																	Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Praesent vestibulum molestie lacus. Aenean nonummy hendrerit mauris. Phasellus porta.<br />
																	<strong>Denmark</strong><br />
																	Morbi nunc odio, gravida at, cursus nec, luctus a, lorem. Maecenas tristique orci ac sem. Duis ultricies pharetra magna. Donec accumsan malesuada orci.<br />
																	<strong>Finland</strong><br />
																	Quisque nulla. Vestibulum libero nisl, porta vel, scelerisque eget, malesuada at, neque. Vivamus eget nibh. Etiam cursus leo vel metus. Nulla facilisi.																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="clear"></div>
					</div>
					<div class="columns2">
						<div class="padding">
							<img alt="" src="img/6-t3.gif" /><br />
							<p class="p1">
								<strong class="b_text">Lorem ipsum dolor sit aectetuer adipiscing elit. Praesent vestibulum molestie lacus.</strong><br />
								Montes, nascetur ridiculus muulla dui. Fusce feugiat malesuada odio. Morbi nunc odio, gravida at, cursus nec, luctus a, lorem. Maecenas tristique orci ac sem. Duis ultricies pharetra mnec accumsan malesuada orci. Donec sit amet eros.<br class="clear" />
							</p>
							<form id="form" action="<%=request.getContextPath()%>/messagemanager/addMessage.action" method="post">
								<div class="column1">
									<div class="row">
										<input name="message.visitor.name" type="text" class="input" value=''/>
									</div>
									<div class="row">
										<input name="message.visitor.mail" type="text" class="input" value=''/>
									</div>
									<div class="row">
										<input name="message.visitor.fax" type="text" class="input" value=''/>
									</div>
								</div>
								<div class="column2">
									<div class="">
										<textarea cols="1" rows="1" name="message.content"></textarea><br/>
										<div class="div">
											<input type="reset" value="重置" class="button"></input>&nbsp;
											<input type="submit" value="提交" class="button"></input>
										</div>
									</div>
								</div>
								<div class="clear"></div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<!--footer -->
			<div id="footer">			
			</div>
			<!--footer end-->
		</div>
	</div>
</body>
</html>