package com.kh.spaceus.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

	public static String getRenamedFileName(String oname) {

		int beginIndex = oname.lastIndexOf(".");
		String ext = oname.substring(beginIndex);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS_");
		int rndNum = (int) (Math.random() * 1000);
		String rname = sdf.format(new Date()) + rndNum + ext;

		return rname;
	}

	public static String getPageBarHtml(int cPage, int numPerPage, int totalContents, String url) {
		String pageBar = "";
		int pageBarSize = 5;
		int totalPage = (int) Math.ceil((double) totalContents / numPerPage);
		int pageStart = ((cPage - 1) / pageBarSize) * pageBarSize + 1;
		int pageEnd = pageStart + pageBarSize - 1;

		int pageNo = pageStart;

		if (pageNo == 1) {
		} else {
			pageBar += "<a class=\"page-link\" href='" + url + "cPage=" + (pageNo - 1) + "'>[Previous]</a>";
		}
		while (pageNo <= pageEnd && pageNo <= totalPage) {
			if (pageNo == cPage) {
				pageBar += "<span class='cPage align-self-center'>" + pageNo + "</span>";
			} else {
				pageBar += "<a class='page-link' href='" + url + "cPage=" + pageNo + "'>" + pageNo + "</a>";
			}
			pageNo++;
		}
		if (pageNo > totalPage) {
		} else {
			pageBar += "<a class='page-link' href='" + url + "cPage=" + pageNo + "'>[Next]</a>";
		}

		return pageBar;
	}
}