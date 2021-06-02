package com.kh.spaceus.community.group.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spaceus.community.group.model.service.GroupService;
import com.kh.spaceus.community.group.model.vo.GBComment;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/community/comment")
public class CommentController {

	@Autowired
	private GroupService groupService;

	@PostMapping("/insertComment/{groupBoardRef}.do")
	public void insertComment(@PathVariable("groupBoardRef") String groupBoardRef, @ModelAttribute GBComment param1) {
		try {
			int result = groupService.insertComment(param1);
		} catch (Exception e) {
		}
	}

	@PostMapping("/updateComment.do")
	public void updateComment(@ModelAttribute GBComment param1) {

		try {
			int result = groupService.updateComment(param1);
		} catch (Exception e) {
		}
	}

	@PostMapping("/deleteComment.do")
	public void deleteComment(@ModelAttribute GBComment param1) {

		try {
			int result = groupService.deleteComment(param1);
		} catch (Exception e) {
		}
	}

	@PostMapping("/alertComment.do")
	public void alertComment(@RequestParam String groupBoardCommentNo, Principal principal) {
		String memberEmail = principal.getName();

		Map<String, String> map = new HashMap<>();
		map.put("groupBoardCommentNo", groupBoardCommentNo);
		map.put("memberEmail", memberEmail);

		try {
			int result1 = groupService.alertComment(map);
			int result2 = groupService.updateReportCnt(groupBoardCommentNo);

		} catch (Exception e) {
		}

	}
}
