/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.5.56
 * Generated at: 2020-09-13 13:48:51 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(4);
    _jspx_dependants.put("/WEB-INF/lib/taglibs-standard-impl-1.2.5.jar", Long.valueOf(1598936639562L));
    _jspx_dependants.put("jar:file:/C:/workspaces/spring_workspace/SpaceUs/src/main/webapp/WEB-INF/lib/taglibs-standard-impl-1.2.5.jar!/META-INF/c.tld", Long.valueOf(1425946270000L));
    _jspx_dependants.put("jar:file:/C:/workspaces/spring_workspace/SpaceUs/src/main/webapp/WEB-INF/lib/taglibs-standard-impl-1.2.5.jar!/META-INF/fn.tld", Long.valueOf(1425946270000L));
    _jspx_dependants.put("jar:file:/C:/workspaces/spring_workspace/SpaceUs/src/main/webapp/WEB-INF/lib/taglibs-standard-impl-1.2.5.jar!/META-INF/fmt.tld", Long.valueOf(1425946270000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ffmt_005frequestEncoding_0026_005fvalue_005fnobody;

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005ffmt_005frequestEncoding_0026_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005ffmt_005frequestEncoding_0026_005fvalue_005fnobody.release();
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- 한글 인코딩처리 -->\r\n");
      if (_jspx_meth_fmt_005frequestEncoding_005f0(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/WEB-INF/views/common/header.jsp", out, false);
      out.write("\r\n");
      out.write("<style>\r\n");
      out.write("h1 {\r\n");
      out.write("font-family: 'NEXON Lv1 Gothic OTF';\r\n");
      out.write("}\r\n");
      out.write("         \r\n");
      out.write("</style>\r\n");
      out.write("<!-- 컨텐츠 시작 -->\r\n");
      out.write("    <div class=\"hero-wrap ftco-degree-bg\"\r\n");
      out.write("    \t style=\"background-image: url('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/images/bg_1.jpg');\r\n");
      out.write("    \t \t\theight: 600px\"\r\n");
      out.write("    \t data-stellar-background-ratio=\"0.5\">\r\n");
      out.write("      <div class=\"overlay\"></div>\r\n");
      out.write("      <div class=\"container\">\r\n");
      out.write("        <div class=\"row no-gutters slider-text justify-content-center align-items-center\">\r\n");
      out.write("          <div class=\"col-lg-8 col-md-6 ftco-animate d-flex align-items-end\">\r\n");
      out.write("          \t<div class=\"text text-center mx-auto\" style=\"margin-bottom:25%;\">\r\n");
      out.write("\t            <h1 class=\"mb-4\">어떤 공간을<br>찾고 있나요?</h1>\r\n");
      out.write("\t            <form action=\"#\" class=\"search-location mt-md-5\">\r\n");
      out.write("\t\t        \t\t<div class=\"row justify-content-center\">\r\n");
      out.write("\t\t        \t\t\t<div class=\"col-lg-10 align-items-end\">\r\n");
      out.write("\t\t        \t\t\t</div>\r\n");
      out.write("\t\t        \t\t</div>\r\n");
      out.write("\t\t        \t</form>\r\n");
      out.write("            </div>\r\n");
      out.write("          </div>\r\n");
      out.write("        </div>\r\n");
      out.write("      </div>\r\n");
      out.write("      <div class=\"mouse\">\r\n");
      out.write("\t\t\t\t<a href=\"#\" class=\"mouse-icon\">\r\n");
      out.write("\t\t\t\t\t<div class=\"mouse-wheel\"><span class=\"ion-ios-arrow-round-down\"></span></div>\r\n");
      out.write("\t\t\t\t</a>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("    </div>\r\n");
      out.write(" \r\n");
      out.write("<!-- 검색창 시작 -->\r\n");
      out.write("<section class=\"ftco-section goto-here\">\r\n");
      out.write("<section class=\"search-section spad\">\r\n");
      out.write("    <div class=\"container\">\r\n");
      out.write("           \t\t<div class=\"row justify-content-center\">\r\n");
      out.write("      <div class=\"col-md-12 heading-section text-center ftco-animate mb-5\">\r\n");
      out.write("      \t<span class=\"subheading\">원하는 공간을 검색해보세요</span>\r\n");
      out.write("        <h2 class=\"mb-2\">공간 검색</h2>\r\n");
      out.write("      </div>\r\n");
      out.write("    </div>\r\n");
      out.write("        <div class=\"search-form-content\">\r\n");
      out.write("            <form action=\"#\" class=\"filter-form\">\r\n");
      out.write("                <select class=\"nice-select sm-width\">\r\n");
      out.write("                    <option value=\"\">Chose The City</option>\r\n");
      out.write("                </select>\r\n");
      out.write("                <select class=\"nice-select sm-width\">\r\n");
      out.write("                    <option value=\"\">Location</option>\r\n");
      out.write("                </select>\r\n");
      out.write("                <select class=\"nice-select sm-width\">\r\n");
      out.write("                    <option value=\"\">Property Status</option>\r\n");
      out.write("                </select>\r\n");
      out.write("                <select class=\"nice-select sm-width\">\r\n");
      out.write("                    <option value=\"\">Property Type</option>\r\n");
      out.write("                </select>\r\n");
      out.write("                <select class=\"nice-select sm-width\">\r\n");
      out.write("                    <option value=\"\">No Of Bedrooms</option>\r\n");
      out.write("                </select>\r\n");
      out.write("                <select class=\"nice-select sm-width\">\r\n");
      out.write("                    <option value=\"\">No Of Bathrooms</option>\r\n");
      out.write("                </select>\r\n");
      out.write("                <div class=\"room-size-range-wrap sm-width\">\r\n");
      out.write("                    <div class=\"price-text\">\r\n");
      out.write("                        <label for=\"roomsizeRange\">Size:</label>\r\n");
      out.write("                        <input type=\"text\" id=\"roomsizeRange\" readonly>\r\n");
      out.write("                    </div>\r\n");
      out.write("                    <div id=\"roomsize-range\" class=\"slider ui-slider ui-corner-all ui-slider-horizontal ui-widget ui-widget-content\">\r\n");
      out.write("                    <div class=\"ui-slider-range ui-corner-all ui-widget-header\" style=\"left: 14.2857%; width: 42.8571%;\"></div>\r\n");
      out.write("                    <span tabindex=\"0\" class=\"ui-slider-handle ui-corner-all ui-state-default\" style=\"left: 14.2857%;\"></span>\r\n");
      out.write("                    <span tabindex=\"0\" class=\"ui-slider-handle ui-corner-all ui-state-default\" style=\"left: 57.1429%;\"></span>\r\n");
      out.write("                    </div>\r\n");
      out.write("                </div>\r\n");
      out.write("                <div class=\"price-range-wrap sm-width\">\r\n");
      out.write("                    <div class=\"price-text\">\r\n");
      out.write("                        <label for=\"priceRange\">Price:</label>\r\n");
      out.write("                        <input type=\"text\" id=\"priceRange\" readonly>\r\n");
      out.write("                    </div>\r\n");
      out.write("                    <div id=\"price-range\" class=\"slider ui-slider ui-corner-all ui-slider-horizontal ui-widget ui-widget-content\">\r\n");
      out.write("                    <div class=\"ui-slider-range ui-corner-all ui-widget-header\" style=\"left: 6.66667%; width: 60%;\"></div>\r\n");
      out.write("                    <span tabindex=\"0\" class=\"ui-slider-handle ui-corner-all ui-state-default\" style=\"left: 6.66667%;\"></span>\r\n");
      out.write("                    <span tabindex=\"0\" class=\"ui-slider-handle ui-corner-all ui-state-default\" style=\"left: 66.6667%;\"></span>\r\n");
      out.write("                    </div>\r\n");
      out.write("                </div>\r\n");
      out.write("                <button type=\"button\" class=\"search-btn sm-width\">Search</button>\r\n");
      out.write("            </form>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"more-option\">\r\n");
      out.write("            <div class=\"accordion\" id=\"accordionExample\">\r\n");
      out.write("                <div class=\"card\">\r\n");
      out.write("                    <div class=\"card-heading active\">\r\n");
      out.write("                        <a data-toggle=\"collapse\" data-target=\"#collapseOne\">\r\n");
      out.write("                            더 많은 옵션보기\r\n");
      out.write("                        </a>\r\n");
      out.write("                    </div>\r\n");
      out.write("                    <div id=\"collapseOne\" class=\"collapse\" data-parent=\"#accordionExample\">\r\n");
      out.write("                        <div class=\"card-body\">\r\n");
      out.write("                            <div class=\"mo-list\">\r\n");
      out.write("                                <div class=\"ml-column\">\r\n");
      out.write("                                    <label for=\"air\">Air conditioning\r\n");
      out.write("                                        <input type=\"checkbox\" id=\"air\">\r\n");
      out.write("                                        <span class=\"checkbox\"></span>\r\n");
      out.write("                                    </label>\r\n");
      out.write("                                    <label for=\"lundry\">Laundry\r\n");
      out.write("                                        <input type=\"checkbox\" id=\"lundry\">\r\n");
      out.write("                                        <span class=\"checkbox\"></span>\r\n");
      out.write("                                    </label>\r\n");
      out.write("                                    <label for=\"refrigerator\">Refrigerator\r\n");
      out.write("                                        <input type=\"checkbox\" id=\"refrigerator\">\r\n");
      out.write("                                        <span class=\"checkbox\"></span>\r\n");
      out.write("                                    </label>\r\n");
      out.write("                                    <label for=\"washer\">Washer\r\n");
      out.write("                                        <input type=\"checkbox\" id=\"washer\">\r\n");
      out.write("                                        <span class=\"checkbox\"></span>\r\n");
      out.write("                                    </label>\r\n");
      out.write("                                </div>\r\n");
      out.write("                                <div class=\"ml-column\">\r\n");
      out.write("                                    <label for=\"barbeque\">Barbeque\r\n");
      out.write("                                        <input type=\"checkbox\" id=\"barbeque\">\r\n");
      out.write("                                        <span class=\"checkbox\"></span>\r\n");
      out.write("                                    </label>\r\n");
      out.write("                                    <label for=\"lawn\">Lawn\r\n");
      out.write("                                        <input type=\"checkbox\" id=\"lawn\">\r\n");
      out.write("                                        <span class=\"checkbox\"></span>\r\n");
      out.write("                                    </label>\r\n");
      out.write("                                    <label for=\"sauna\">Sauna\r\n");
      out.write("                                        <input type=\"checkbox\" id=\"sauna\">\r\n");
      out.write("                                        <span class=\"checkbox\"></span>\r\n");
      out.write("                                    </label>\r\n");
      out.write("                                    <label for=\"wifi\">Wifi\r\n");
      out.write("                                        <input type=\"checkbox\" id=\"wifi\">\r\n");
      out.write("                                        <span class=\"checkbox\"></span>\r\n");
      out.write("                                    </label>\r\n");
      out.write("                                </div>\r\n");
      out.write("                                <div class=\"ml-column\">\r\n");
      out.write("                                    <label for=\"dryer\">Dryer\r\n");
      out.write("                                        <input type=\"checkbox\" id=\"dryer\">\r\n");
      out.write("                                        <span class=\"checkbox\"></span>\r\n");
      out.write("                                    </label>\r\n");
      out.write("                                    <label for=\"microwave\">Microwave\r\n");
      out.write("                                        <input type=\"checkbox\" id=\"microwave\">\r\n");
      out.write("                                        <span class=\"checkbox\"></span>\r\n");
      out.write("                                    </label>\r\n");
      out.write("                                    <label for=\"pool\">Swimming Pool\r\n");
      out.write("                                        <input type=\"checkbox\" id=\"pool\">\r\n");
      out.write("                                        <span class=\"checkbox\"></span>\r\n");
      out.write("                                    </label>\r\n");
      out.write("                                    <label for=\"window\">Window Coverings\r\n");
      out.write("                                        <input type=\"checkbox\" id=\"window\">\r\n");
      out.write("                                        <span class=\"checkbox\"></span>\r\n");
      out.write("                                    </label>\r\n");
      out.write("                                </div>\r\n");
      out.write("                                <div class=\"ml-column last-column\">\r\n");
      out.write("                                    <label for=\"gym\">Gym\r\n");
      out.write("                                        <input type=\"checkbox\" id=\"gym\">\r\n");
      out.write("                                        <span class=\"checkbox\"></span>\r\n");
      out.write("                                    </label>\r\n");
      out.write("                                    <label for=\"shower\">OutdoorShower\r\n");
      out.write("                                        <input type=\"checkbox\" id=\"shower\">\r\n");
      out.write("                                        <span class=\"checkbox\"></span>\r\n");
      out.write("                                    </label>\r\n");
      out.write("                                    <label for=\"tv\">Tv Cable\r\n");
      out.write("                                        <input type=\"checkbox\" id=\"tv\">\r\n");
      out.write("                                        <span class=\"checkbox\"></span>\r\n");
      out.write("                                    </label>\r\n");
      out.write("                                </div>\r\n");
      out.write("                            </div>\r\n");
      out.write("                        </div>\r\n");
      out.write("                    </div>\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("</section>\r\n");
      out.write("<!-- 검색창 끝 -->\r\n");
      out.write("<br />\r\n");
      out.write("<br />\r\n");
      out.write("<!-- 추천공간 시작 -->\r\n");
      out.write("\t<div class=\"container\">\r\n");
      out.write("\t\t<div class=\"row justify-content-center\">\r\n");
      out.write("      <div class=\"col-md-12 heading-section text-center ftco-animate mb-5\">\r\n");
      out.write("      \t<span class=\"subheading\">오늘의 추천</span>\r\n");
      out.write("        <h2 class=\"mb-2\">인기 공간</h2>\r\n");
      out.write("      </div>\r\n");
      out.write("    </div>\r\n");
      out.write("    <div class=\"row\">\r\n");
      out.write("    \t<div class=\"col-md-4\">\r\n");
      out.write("    \t\t<div class=\"property-wrap ftco-animate\">\r\n");
      out.write("    \t\t\t<a href=\"\" class=\"img\" style=\"background-image: url(");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/images/work-1.jpg);\"></a>\r\n");
      out.write("    \t\t\t<div class=\"text\">\r\n");
      out.write("    \t\t\t\t<p class=\"price\"><span class=\"old-price\">800,000</span><span class=\"orig-price\">$3,050<small>/mo</small></span></p>\r\n");
      out.write("    \t\t\t\t<ul class=\"property_list\">\r\n");
      out.write("    \t\t\t\t\t<li><span class=\"flaticon-bed\"></span>3</li>\r\n");
      out.write("    \t\t\t\t\t<li><span class=\"flaticon-bathtub\"></span>2</li>\r\n");
      out.write("    \t\t\t\t\t<li><span class=\"flaticon-floor-plan\"></span>1,878 sqft</li>\r\n");
      out.write("    \t\t\t\t</ul>\r\n");
      out.write("    \t\t\t\t<h3><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/space/spaceDetail.do\">The Blue Sky Home</a></h3>\r\n");
      out.write("    \t\t\t\t<span class=\"location\">Oakland</span>\r\n");
      out.write("    \t\t\t\t<a href=\"#\" class=\"d-flex align-items-center justify-content-center btn-custom\">\r\n");
      out.write("    \t\t\t\t\t<span class=\"ion-ios-link\"></span>\r\n");
      out.write("    \t\t\t\t</a>\r\n");
      out.write("    \t\t\t</div>\r\n");
      out.write("    \t\t</div>\r\n");
      out.write("    \t</div>\r\n");
      out.write("    \t<div class=\"col-md-4\">\r\n");
      out.write("    \t\t<div class=\"property-wrap ftco-animate\">\r\n");
      out.write("    \t\t\t<a href=\"#\" class=\"img\" style=\"background-image: url(");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/images/work-2.jpg);\"></a>\r\n");
      out.write("    \t\t\t<div class=\"text\">\r\n");
      out.write("    \t\t\t\t<p class=\"price\"><span class=\"old-price\">800,000</span><span class=\"orig-price\">$3,050<small>/mo</small></span></p>\r\n");
      out.write("    \t\t\t\t<ul class=\"property_list\">\r\n");
      out.write("    \t\t\t\t\t<li><span class=\"flaticon-bed\"></span>3</li>\r\n");
      out.write("    \t\t\t\t\t<li><span class=\"flaticon-bathtub\"></span>2</li>\r\n");
      out.write("    \t\t\t\t\t<li><span class=\"flaticon-floor-plan\"></span>1,878 sqft</li>\r\n");
      out.write("    \t\t\t\t</ul>\r\n");
      out.write("    \t\t\t\t<h3><a href=\"#\">The Blue Sky Home</a></h3>\r\n");
      out.write("    \t\t\t\t<span class=\"location\">Oakland</span>\r\n");
      out.write("    \t\t\t\t<a href=\"#\" class=\"d-flex align-items-center justify-content-center btn-custom\">\r\n");
      out.write("    \t\t\t\t\t<span class=\"ion-ios-link\"></span>\r\n");
      out.write("    \t\t\t\t</a>\r\n");
      out.write("    \t\t\t</div>\r\n");
      out.write("    \t\t</div>\r\n");
      out.write("    \t</div>\r\n");
      out.write("    \t<div class=\"col-md-4\">\r\n");
      out.write("    \t\t<div class=\"property-wrap ftco-animate\">\r\n");
      out.write("    \t\t\t<a href=\"#\" class=\"img\" style=\"background-image: url(");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/images/work-3.jpg);\"></a>\r\n");
      out.write("    \t\t\t<div class=\"text\">\r\n");
      out.write("    \t\t\t\t<p class=\"price\"><span class=\"old-price\">800,000</span><span class=\"orig-price\">$3,050<small>/mo</small></span></p>\r\n");
      out.write("    \t\t\t\t<ul class=\"property_list\">\r\n");
      out.write("    \t\t\t\t\t<li><span class=\"flaticon-bed\"></span>3</li>\r\n");
      out.write("    \t\t\t\t\t<li><span class=\"flaticon-bathtub\"></span>2</li>\r\n");
      out.write("    \t\t\t\t\t<li><span class=\"flaticon-floor-plan\"></span>1,878 sqft</li>\r\n");
      out.write("    \t\t\t\t</ul>\r\n");
      out.write("    \t\t\t\t<h3><a href=\"#\">The Blue Sky Home</a></h3>\r\n");
      out.write("    \t\t\t\t<span class=\"location\">Oakland</span>\r\n");
      out.write("    \t\t\t\t<a href=\"#\" class=\"d-flex align-items-center justify-content-center btn-custom\">\r\n");
      out.write("    \t\t\t\t\t<span class=\"ion-ios-link\"></span>\r\n");
      out.write("    \t\t\t\t</a>\r\n");
      out.write("    \t\t\t</div>\r\n");
      out.write("    \t\t</div>\r\n");
      out.write("    \t</div>\r\n");
      out.write("    </div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</section>\r\n");
      out.write("<!-- 추천공간 끝 -->\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("<!-- 컨텐츠 끝 -->\r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/WEB-INF/views/common/footer.jsp", out, false);
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_fmt_005frequestEncoding_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  fmt:requestEncoding
    org.apache.taglibs.standard.tag.rt.fmt.RequestEncodingTag _jspx_th_fmt_005frequestEncoding_005f0 = (org.apache.taglibs.standard.tag.rt.fmt.RequestEncodingTag) _005fjspx_005ftagPool_005ffmt_005frequestEncoding_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.fmt.RequestEncodingTag.class);
    boolean _jspx_th_fmt_005frequestEncoding_005f0_reused = false;
    try {
      _jspx_th_fmt_005frequestEncoding_005f0.setPageContext(_jspx_page_context);
      _jspx_th_fmt_005frequestEncoding_005f0.setParent(null);
      // /index.jsp(7,0) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_fmt_005frequestEncoding_005f0.setValue("utf-8");
      int _jspx_eval_fmt_005frequestEncoding_005f0 = _jspx_th_fmt_005frequestEncoding_005f0.doStartTag();
      if (_jspx_th_fmt_005frequestEncoding_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005ffmt_005frequestEncoding_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005frequestEncoding_005f0);
      _jspx_th_fmt_005frequestEncoding_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_fmt_005frequestEncoding_005f0, _jsp_getInstanceManager(), _jspx_th_fmt_005frequestEncoding_005f0_reused);
    }
    return false;
  }
}