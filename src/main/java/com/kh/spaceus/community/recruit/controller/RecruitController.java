package com.kh.spaceus.community.recruit.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.kh.spaceus.common.Utils;
import com.kh.spaceus.community.recruit.model.service.RecruitService;
import com.kh.spaceus.community.recruit.model.vo.Recruit;
import com.kh.spaceus.community.recruit.model.vo.RecruitComment;
import com.kh.spaceus.community.recruit.model.vo.ReportComment;
import com.kh.spaceus.community.recruit.model.vo.ReportRecruit;
import com.kh.spaceus.member.model.service.MemberService;
import com.kh.spaceus.member.model.vo.Member;
import com.kh.spaceus.space.model.vo.Review;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/community/recruit")
public class RecruitController {

	@Autowired
	private RecruitService recruitService;

	@Autowired
	private MemberService memberService;

	@RequestMapping("/recruitList.do")
	public ModelAndView recruitList(ModelAndView mav, @RequestParam(defaultValue = "1", value = "cPage") int cPage,
			HttpServletRequest request) {
		final int limit = 10;
		int offset = (cPage - 1) * limit;

		List<Recruit> list = recruitService.selectRecruitList(limit, offset);

		int totalContents = recruitService.selectRecruitTotalContents();
		String url = request.getRequestURI() + "?";
		String pageBar = Utils.getPageBarHtml(cPage, limit, totalContents, url);

		mav.addObject("totalContents", totalContents);
		mav.addObject("list", list);
		mav.addObject("pageBar", pageBar);
		mav.addObject("category", "all");
		mav.setViewName("community/recruit/recruitList");
		return mav;
	}

	@RequestMapping("/recruitEnrollForm.do")
	public String recruitEnrollFrom() {
		return "community/recruit/recruitEnrollForm";
	}

	@GetMapping("/recruitDetail.do")
	public String recruitDetail(@RequestParam("no") String no, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Cookie[] cookies = request.getCookies();
			String recruitCookieVal = "";
			boolean hasRead = false;

			if (cookies != null) {
				for (Cookie c : cookies) {
					String name = c.getName();
					String value = c.getValue();

					if ("recruitCookie".equals(name)) {
						recruitCookieVal = value;

						if (value.contains("[" + no + "]"))
							hasRead = true;
					}
				}
			}
			if (!hasRead) {
				Cookie recruitCookie = new Cookie("recruitCookie", recruitCookieVal + "[" + no + "]");
				recruitCookie.setPath(request.getContextPath() + "/community/recruit");
				recruitCookie.setMaxAge(24 * 60 * 60);
				response.addCookie(recruitCookie);
				int result = recruitService.increaseRecruitReadCnt(no);
			}

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			authentication.getName();
			RecruitComment comment = new RecruitComment();
			comment.setNo(no);
			comment.setReporter(authentication.getName());

			Recruit recruit = recruitService.selectOneRecruit(no);
			List<RecruitComment> commentList = recruitService.selectCommentList(comment);
			int commentTotal = recruitService.selectCommentTotalContents(no);

			model.addAttribute("recruit", recruit);
			model.addAttribute("commentList", commentList);
			model.addAttribute("commentTotal", commentTotal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "community/recruit/recruitDetail";
	}

	@RequestMapping(value = "/insertRecruit.do", method = RequestMethod.POST)
	public String insertRecruit(RedirectAttributes redirectAttributes, Principal principal, Recruit recruit) {

		Member member = memberService.selectOneMember(principal.getName());
		recruit.setEmail(principal.getName());
		recruit.setNickName(member.getNickName());
		int result = recruitService.insertRecruit(recruit);
		String msg = result > 0 ? "등록 성공!" : "등록실패";
		redirectAttributes.addFlashAttribute("msg", msg);

		return "redirect:/community/recruit/recruitList.do";
	}

	@RequestMapping("/recruitModify.do")
	public String recruitModify(@RequestParam("no") String no, Model model) {
		Recruit recruit = recruitService.selectOneRecruit(no);
		model.addAttribute("recruit", recruit);
		return "community/recruit/updateRecruit";
	}

	@RequestMapping(value = "/updateRecruit.do", method = RequestMethod.POST)
	public ModelAndView updateRecruit(Recruit recruit, HttpServletRequest request, ModelAndView mav) {
		int result = recruitService.updateRecruit(recruit);

		String msg = "";
		if (result > 0) {
			msg = "게시물 수정성공!";
			Recruit updateRecruit = recruitService.selectOneRecruit(recruit.getNo());
			mav.addObject("recruit", updateRecruit);
		} else
			msg = "게시물 수정실패!";

		FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
		flashMap.put("msg", msg);
		mav.setViewName("community/recruit/recruitDetail");
		return mav;
	}

	@RequestMapping(value = "/deleteRecruit.do", method = RequestMethod.GET)
	public String deleteRecruit(@RequestParam String no, RedirectAttributes redirectAttr) {
		int result = recruitService.deleteRecruit(no);
		String msg = (result > 0) ? "삭제 성공" : "삭제 실패";
		redirectAttr.addFlashAttribute("msg", msg);

		return "redirect:/community/recruit/recruitList.do";
	}

	@GetMapping("/recruitReport.do")
	public ModelAndView recruitReport(ModelAndView mav, Principal principal, @RequestParam("no") String no,
			@RequestParam("nickName") String nickName, @RequestParam("reportReason") String reportReason) {

		ReportRecruit report = new ReportRecruit();
		report.setBoardNo(no);
		report.setMemberEmail(principal.getName());
		report.setReportReason(reportReason);

		String memberEmail = principal.getName();

		ReportRecruit reportResult = recruitService.selectOneReport(no, memberEmail);

		if (reportResult == null) {
			int resultReport = recruitService.insertReport(report);
			int result = recruitService.updateReport(no);
			mav.addObject("duplication", 0);
			mav.setViewName("jsonView"); // /WEB-INF/views/jsonView.jsp
		} else {
			mav.addObject("duplication", 1);
			mav.setViewName("jsonView"); // /WEB-INF/views/jsonView.jsp

		}
		return mav;
	}

	@GetMapping("/insertComment.do")
	public ModelAndView insertComment(ModelAndView mav, @RequestParam("recruitNo") String recruitNo,
			@RequestParam("email") String email, @RequestParam("secret") int secret,
			@RequestParam("content") String content) {

		Member member = memberService.selectOneMember(email);

		RecruitComment comment = new RecruitComment();
		comment.setNickName(member.getNickName());
		comment.setRecruitNo(recruitNo);
		comment.setSecret(secret);
		comment.setContent(content);
		comment.setCommentRef(null);
		comment.setLevel(1);

		int result = recruitService.insertComment(comment);

		mav.setViewName("jsonView");

		return mav;
	}

	@GetMapping("/insertReply.do")
	public ModelAndView insertReply(ModelAndView mav, @RequestParam("recruitNo") String recruitNo,
			@RequestParam("email") String email, @RequestParam("secret") int secret,
			@RequestParam("content") String content, @RequestParam("commentRef") String commentRef) {

		Member member = memberService.selectOneMember(email);

		RecruitComment comment = new RecruitComment();
		comment.setNickName(member.getNickName());
		comment.setRecruitNo(recruitNo);
		comment.setSecret(secret);
		comment.setContent(content);
		comment.setCommentRef(commentRef);
		comment.setLevel(2);

		int result = recruitService.insertComment(comment);

		mav.setViewName("jsonView");

		return mav;
	}

	@GetMapping("/updateComment.do")
	public ModelAndView updateComment(ModelAndView mav, @RequestParam("content") String content,
			@RequestParam("commentNo") String commentNo) {

		RecruitComment comment = new RecruitComment();
		comment.setContent(content);
		comment.setNo(commentNo);

		int result = recruitService.updateComment(comment);

		mav.setViewName("jsonView");

		return mav;
	}

	@GetMapping("/deleteComment.do")
	public ModelAndView deleteComment(ModelAndView mav, @RequestParam("commentNo") String commentNo) {

		int result = recruitService.deleteComment(commentNo);
		mav.setViewName("jsonView");

		return mav;
	}

	@GetMapping("/insertReportComment.do")
	public ModelAndView insertReportComment(ModelAndView mav, @RequestParam("commentNo") String commentNo,
			Principal principal) {

		ReportComment reportComment = new ReportComment();
		reportComment.setBoardCommentNo(commentNo);
		reportComment.setEmail(principal.getName());

		int result = recruitService.insertReportComment(reportComment);
		mav.setViewName("jsonView");

		return mav;
	}

	@GetMapping("/searchRecruit.do")
	public ModelAndView searchRecruit(ModelAndView mav, @RequestParam("keyWord") String keyWord,
			@RequestParam(defaultValue = "1", value = "cPage") int cPage, HttpServletRequest request) {
		final int limit = 10;
		int offset = (cPage - 1) * limit;

		List<Recruit> list = recruitService.searchRecruit(keyWord, limit, offset);

		int totalContents = recruitService.selectRecruitTotalSearch(keyWord);
		String url = request.getRequestURI() + "?";
		String pageBar = Utils.getPageBarHtml(cPage, limit, totalContents, url);

		mav.addObject("totalContents", totalContents);
		mav.addObject("list", list);
		mav.addObject("pageBar", pageBar);
		mav.setViewName("community/recruit/recruitList");
		return mav;
	}

	@RequestMapping("/recruitHire.do")
	public ModelAndView recruitHire(ModelAndView mav, @RequestParam(defaultValue = "1", value = "cPage") int cPage,
			HttpServletRequest request) {
		final int limit = 10;
		int offset = (cPage - 1) * limit;

		List<Recruit> list = recruitService.recruitHeaderSearch("구인", limit, offset);

		int totalContents = recruitService.selectRecruitTotalHeader("구인");
		String url = request.getRequestURI() + "?";
		String pageBar = Utils.getPageBarHtml(cPage, limit, totalContents, url);

		mav.addObject("totalContents", totalContents);
		mav.addObject("list", list);
		mav.addObject("pageBar", pageBar);
		mav.addObject("category", "hire");
		mav.setViewName("community/recruit/recruitList");
		return mav;
	}

	@RequestMapping("/recruitJobSearch.do")
	public ModelAndView recruitJobSearch(ModelAndView mav, @RequestParam(defaultValue = "1", value = "cPage") int cPage,
			HttpServletRequest request) {
		final int limit = 10;
		int offset = (cPage - 1) * limit;

		List<Recruit> list = recruitService.recruitHeaderSearch("구직", limit, offset);

		int totalContents = recruitService.selectRecruitTotalHeader("구직");
		String url = request.getRequestURI() + "?";
		String pageBar = Utils.getPageBarHtml(cPage, limit, totalContents, url);

		mav.addObject("totalContents", totalContents);
		mav.addObject("list", list);
		mav.addObject("pageBar", pageBar);
		mav.addObject("category", "jobSearch");
		mav.setViewName("community/recruit/recruitList");
		return mav;
	}

}
