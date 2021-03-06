package com.kh.spaceus.community.group.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spaceus.common.Utils;
import com.kh.spaceus.community.group.model.service.GroupService;
import com.kh.spaceus.community.group.model.vo.Board;
import com.kh.spaceus.community.group.model.vo.CmtReport;
import com.kh.spaceus.community.group.model.vo.GBComment;
import com.kh.spaceus.community.group.model.vo.GroupBoard;
import com.kh.spaceus.community.group.model.vo.Report;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/community/group")
public class GroupController {

	@Autowired
	private GroupService groupService;

	@GetMapping("/groupList.do")
	public String groupList(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "1", value = "cPage") int cPage) {

		List<Board> boardList = groupService.selectListBoard();

		final int limit = 10;
		int offset = (cPage - 1) * limit;

		List<GroupBoard> groupBoardList = groupService.selectListGroupBoard(limit, offset);

		int totalCnt = groupService.selectTotalCnt();
		String url = request.getRequestURI() + "?";
		String pageBar = Utils.getPageBarHtml(cPage, limit, totalCnt, url);

		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("boardList", boardList);
		model.addAttribute("groupBoardList", groupBoardList);
		model.addAttribute("pageBar", pageBar);
		return "community/group/groupBoard";
	}

	@GetMapping("/groupList/{boardNo}/{boardRef}.do")
	public String groupBoardList(@PathVariable("boardNo") String boardNo, @PathVariable("boardRef") String boardRef,
			Model model, HttpServletRequest request, @RequestParam(defaultValue = "1", value = "cPage") int cPage) {

		List<Board> boardList = groupService.selectListBoard();

		Map<String, String> listMap = new HashMap<>();
		listMap.put("boardNo", boardNo);
		listMap.put("boardRef", boardRef);

		final int limit = 10;
		int offset = (cPage - 1) * limit;

		List<GroupBoard> groupBoardList = groupService.selectSortedListGroupBoard(listMap, limit, offset);

		int totalCnt = groupService.selectSortedListCnt(listMap);
		String url = request.getRequestURI() + "?";
		String pageBar = Utils.getPageBarHtml(cPage, limit, totalCnt, url);

		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("boardList", boardList);
		model.addAttribute("groupBoardList", groupBoardList);
		model.addAttribute("pageBar", pageBar);

		return "community/group/groupBoard";
	}

	@RequestMapping("/groupDetail/{groupBoardNo}.do")
	public String groupDetail(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("groupBoardNo") String groupBoardNo, Model model) {
		try {

			Cookie[] cookies = request.getCookies();
			String boardCookieVal = "";
			boolean hasRead = false;

			if (cookies != null) {
				for (Cookie c : cookies) {
					String name = c.getName();
					String value = c.getValue();

					if ("boardCookie".equals(name)) {
						boardCookieVal = value;

						if (value.contains("[" + groupBoardNo + "]"))
							hasRead = true;
					}
				}
			}
			if (!hasRead) {
				Cookie boardCookie = new Cookie("boardCookie", boardCookieVal + "[" + groupBoardNo + "]");
				boardCookie.setPath(request.getContextPath() + "/community/group");
				boardCookie.setMaxAge(-1);
				response.addCookie(boardCookie);

			}

			if (!hasRead) {
				int result = groupService.increaseBoardReadCnt(groupBoardNo);
			}

			List<GroupBoard> list = groupService.selectDetailBoard(groupBoardNo);
			List<Board> boardList = groupService.selectBoardOne(groupBoardNo);
			List<GBComment> commentList = groupService.selectAllComment(groupBoardNo);
			int commentCnt = groupService.selectCommentCnt(groupBoardNo);
			List<CmtReport> report = groupService.selectReport();

			model.addAttribute("list", list);
			model.addAttribute("boardList", boardList);
			model.addAttribute("commentList", commentList);
			model.addAttribute("commentCnt", commentCnt);
			model.addAttribute("report", report);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "community/group/groupDetail";
	}

	@RequestMapping("/groupEnrollForm.do")
	public String groupEnrollForm(Model model) {

		List<Board> boardList = groupService.selectListBoard();

		model.addAttribute("boardList", boardList);
		return "/community/group/groupEnrollForm";
	}

	@PostMapping("/insertBoard.do")
	public String insertBoard(GroupBoard gb, Model model, RedirectAttributes redirectAtt) {

		int result = groupService.insertBoard(gb);

		String msg = (result > 0) ? "게시물 등록!" : "등록 실패!";

		redirectAtt.addFlashAttribute("msg", msg);

		return "redirect:/community/group/groupList.do";
	}

	@RequestMapping("/modifyBoard/{groupBoardNo}.do")
	public String modifyBoard(@PathVariable("groupBoardNo") String groupBoardNo, Model model) {

		List<GroupBoard> gb = groupService.selectDetailBoard(groupBoardNo);

		List<Board> boardList = groupService.selectListBoard();

		model.addAttribute("List", boardList);
		model.addAttribute("gb", gb);
		return "/community/group/groupModifyForm";
	}

	@RequestMapping("/updateBoard/{groupBoardNo}.do")
	public String updateBoard(GroupBoard gb, RedirectAttributes redirectAtt, Model model) {

		int result = groupService.updateBoard(gb);
		redirectAtt.addFlashAttribute("msg", result > 0 ? "수정성공!" : "수정실패!");

		return "redirect:/community/group/groupDetail/{groupBoardNo}.do";
	}

	@RequestMapping("/deleteBoard.do")
	public String deleteBoard(@RequestParam("groupBoardNo") String groupBoardNo, RedirectAttributes redirectAtt,
			Model model) {

		int result = groupService.deleteBoard(groupBoardNo);
		List<Board> boardList = groupService.selectListBoard();

		String msg = (result > 0) ? "게시물 삭제 성공!" : "게시물 삭제 실패!";
		redirectAtt.addFlashAttribute("msg", msg);
		model.addAttribute("boardList", boardList);
		return "redirect:/community/group/groupList.do";
	}

	@RequestMapping("/alertBoard.do")
	public String alertBoard(@RequestParam("groupBoardNo") String groupBoardNo, Report report, Principal principal,
			RedirectAttributes redirectAtt, Model model) {

		report.setBoardNo(groupBoardNo);
		report.setMemberEmail(principal.getName());

		Map<Object, Object> map = new HashMap<>();
		map.put("memberEmail", principal.getName());
		map.put("boardNo", groupBoardNo);

		List<Report> gbReport = groupService.selectOne(map);

		String msg = "";

		if (gbReport.isEmpty()) {
			int result1 = groupService.insertReport(report);
			int result2 = groupService.updateCnt(map);

			msg = "성공적으로 신고 접수를 하였습니다";
		} else {
			msg = "이미 신고 접수 건이 존재합니다";
		}

		redirectAtt.addFlashAttribute("msg", msg);

		return "redirect:/community/group/groupList.do";
	}

}
