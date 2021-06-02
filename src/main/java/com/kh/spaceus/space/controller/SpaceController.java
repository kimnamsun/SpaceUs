package com.kh.spaceus.space.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spaceus.common.Utils;
import com.kh.spaceus.host.model.service.HostService;
import com.kh.spaceus.member.model.service.MemberService;
import com.kh.spaceus.member.model.vo.Coupon;
import com.kh.spaceus.member.model.vo.Member;
import com.kh.spaceus.qna.model.vo.Qna;
import com.kh.spaceus.reservation.model.service.ReservationService;
import com.kh.spaceus.reservation.model.vo.ReservationAvail;
import com.kh.spaceus.reservation.model.vo.Unselectable;
import com.kh.spaceus.space.model.service.SpaceService;
import com.kh.spaceus.space.model.vo.Attachment;
import com.kh.spaceus.space.model.vo.Option;
import com.kh.spaceus.space.model.vo.OptionList;
import com.kh.spaceus.space.model.vo.Review;
import com.kh.spaceus.space.model.vo.Space;
import com.kh.spaceus.space.model.vo.SpaceList;
import com.kh.spaceus.space.model.vo.SpaceTag;
import com.kh.spaceus.space.model.vo.Star;
import com.kh.spaceus.space.model.vo.Tag;
import com.kh.spaceus.space.model.vo.Wish;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;

@Controller
@Slf4j
@RequestMapping("/space")
public class SpaceController {

	@Autowired
	private SpaceService spaceService;

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private HostService hostService;

	@RequestMapping(value = "/insertSpace.do", method = RequestMethod.GET)
	public String insertSpace() {
		return "space/insertSpace";
	}

	@RequestMapping(value = "/insertSpace.do", method = RequestMethod.POST)
	public String insertSpace(Space space, @RequestParam String optionNo, @RequestParam String day,
			@RequestParam String[] tag, @RequestParam(value = "upFile", required = true) MultipartFile[] upFiles,
			HttpServletRequest request, RedirectAttributes redirectAttr, Principal principal) {
		space.setMemberEmail(principal.getName());

		List<Attachment> attachList = new ArrayList<>();
		String saveDirectory = request.getServletContext().getRealPath("/resources/upload/space");

		for (MultipartFile f : upFiles) {

			if (!f.isEmpty() && f.getSize() != 0) {
				String renamedFileName = Utils.getRenamedFileName(f.getOriginalFilename());

				File newFile = new File(saveDirectory, renamedFileName); // 임의의 자바파일객체를 만들고 이동시킴
				try {
					f.transferTo(newFile);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
				Attachment attach = new Attachment();
				attach.setOname(f.getOriginalFilename());
				attach.setRname(renamedFileName);
				attachList.add(attach);
			}
		}
		space.setAttachList(attachList);

		try {
			int result1 = spaceService.insertSpace(space);
			Space spaceInfo = spaceService.selectOneSpace(space.getBusinessNo());
			String spaceNo = spaceInfo.getSpaceNo();

			List<Map<String, Object>> info = new ArrayList<Map<String, Object>>();
			ReservationAvail reservationAvail = new ReservationAvail();

			info = JSONArray.fromObject(day);
			for (Map<String, Object> memberInfo : info) {
				if ((int) memberInfo.get("startHour") == -1 || (int) memberInfo.get("endHour") == -1)
					continue;
				reservationAvail.setDay((String) memberInfo.get("day"));
				reservationAvail.setSpaceNo(spaceNo);
				reservationAvail.setStartHour((int) memberInfo.get("startHour"));
				reservationAvail.setEndHour((int) memberInfo.get("endHour"));
				int result2 = reservationService.insertReservationVail(reservationAvail);
			}
			String[] array = optionNo.split(",");
			List<Option> optionList = new ArrayList<>();
			Option option = new Option();
			for (String str : array) {
				option.setOptionNo(str);
				option.setSpaceNo(spaceNo);
				int result3 = spaceService.insertOption(option);
			}

			int result4 = 0;
			SpaceTag spaceTag = new SpaceTag();
			for (String s : tag) {
				Tag tagOne = spaceService.selectOneTag(s);
				spaceTag.setSpaceNo(spaceNo);
				spaceTag.setTagNo(tagOne.getNo());
				result4 = spaceService.insertSpaceTag(spaceTag);
			}
			String msg = result4 > 0 ? "공간이 등록되었습니다! 심사 후 공개로 전환됩니다." : "등록실패";
			redirectAttr.addFlashAttribute("msg", msg);
		} catch (Exception e) {
			throw e;
		}

		return "redirect:/";
	}

	@GetMapping("/insertHashTag.do")
	public ModelAndView insertHashTag(ModelAndView mav, @RequestParam("hashTag") String hashTag) {

		Tag tag = spaceService.selectOneTag(hashTag);
		if (tag != null) {
			mav.addObject("hashTag", hashTag);
			mav.setViewName("jsonView");
		} else {
			spaceService.insertHashTag(hashTag);
			mav.addObject("hashTag", hashTag);
			mav.setViewName("jsonView");
		}
		return mav;
	}

	@RequestMapping("/spaceDetail.do")
	public String spaceDetail(Model model, @RequestParam("spaceNo") String spaceNo, Principal principal, Space space,
			@RequestParam(defaultValue = "1", value = "cPage") int cPage, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			int qnaTotal = spaceService.selectQuestionTotalContents(spaceNo);

			final int limit1 = 5 + space.getQnaPaging();
			int offset1 = (cPage - 1) * limit1;
			int qnaPaging = space.getQnaPaging();
			int width = space.getWidth();

			List<Qna> qlist = spaceService.selectQuestionList(spaceNo, limit1, offset1);

			final int limit = 5 + space.getReviewPaging();
			int offset = (cPage - 1) * limit;
			int reviewPaging = space.getReviewPaging();

			List<Review> review = spaceService.selectListReview(spaceNo, limit, offset);

			int reviewTotal = spaceService.selectReviewTotalContents(spaceNo);
			Star star = spaceService.selectStar(spaceNo);

			star.setSumStar(star.getStar1() + star.getStar2() + star.getStar3() + star.getStar4() + star.getStar5());

			String url = request.getRequestURI() + "?spaceNo=" + spaceNo;
			String pageBar = Utils.getPageBarHtml(cPage, limit, reviewTotal, url);

			Cookie[] cookies = request.getCookies();
			String spaceCookieVal = "";
			boolean hasRead = false;

			if (cookies != null) {
				for (Cookie c : cookies) {
					String name = c.getName();
					String value = c.getValue();

					if ("spaceCookie".equals(name)) {
						spaceCookieVal = value;

						if (value.contains("[" + spaceNo + "]"))
							hasRead = true;
					}
				}
			}
			if (!hasRead) {
				Cookie spaceCookie = new Cookie("spaceCookie", spaceCookieVal + "[" + spaceNo + "]");
				spaceCookie.setPath(request.getContextPath() + "/space");
				spaceCookie.setMaxAge(24 * 60 * 60);
				response.addCookie(spaceCookie);
				int result = spaceService.increaseSpaceReadCnt(spaceNo);
			}

			space = spaceService.selectOneSpace(spaceNo);

			List<Tag> tag = spaceService.selectListSpaceTag(spaceNo);
			List<SpaceList> spcList = spaceService.selectSameCategory(space);

			String cateName = spaceService.selectCateName(spaceNo);

			List<OptionList> optionList = spaceService.selectOptionList(spaceNo);

			model.addAttribute("spcList", spcList);
			model.addAttribute("cateName", cateName);
			model.addAttribute("spaceAddr", space.getAddress());
			model.addAttribute("spaceName", space.getSpaceName());
			model.addAttribute("spaceCon", space.getContent());

			model.addAttribute("qlist", qlist);
			model.addAttribute("qnaPaging", qnaPaging);
			model.addAttribute("qnaTotal", qnaTotal);

			model.addAttribute("space", space);
			model.addAttribute("tag", tag);
			model.addAttribute("loginMember", principal);

			model.addAttribute("review", review);
			model.addAttribute("reviewTotal", reviewTotal);
			model.addAttribute("reviewPaging", reviewPaging);
			model.addAttribute("star", star);
			model.addAttribute("pageBar", pageBar);
			model.addAttribute("width", width);

			model.addAttribute("optionList", optionList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "space/spaceDetail";
	}

	@RequestMapping("/spaceReviewDetail.do")
	public String spaceReviewDetail(Model model, @RequestParam("spaceNo") String spaceNo, Principal principal,
			Space space, @RequestParam(defaultValue = "1", value = "cPage") int cPage, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			int qnaTotal = spaceService.selectQuestionTotalContents(spaceNo);

			final int limit1 = 5 + space.getQnaPaging();
			int offset1 = (cPage - 1) * limit1;
			int qnaPaging = space.getQnaPaging();
			int width = space.getWidth();

			List<Qna> qlist = spaceService.selectQuestionList(spaceNo, limit1, offset1);

			final int limit = 5 + space.getReviewPaging();
			int offset = (cPage - 1) * limit;
			int reviewPaging = space.getReviewPaging();

			List<Review> review = spaceService.selectListReview(spaceNo, limit, offset);

			int reviewTotal = spaceService.selectReviewTotalContents(spaceNo);
			Star star = spaceService.selectStar(spaceNo);

			star.setSumStar(star.getStar1() + star.getStar2() + star.getStar3() + star.getStar4() + star.getStar5());

			String url = request.getRequestURI() + "?spaceNo=" + spaceNo;
			String pageBar = Utils.getPageBarHtml(cPage, limit, reviewTotal, url);

			Cookie[] cookies = request.getCookies();
			String spaceCookieVal = "";
			boolean hasRead = false;

			if (cookies != null) {
				for (Cookie c : cookies) {
					String name = c.getName();
					String value = c.getValue();

					if ("spaceCookie".equals(name)) {
						spaceCookieVal = value;

						if (value.contains("[" + spaceNo + "]"))
							hasRead = true;
					}
				}
			}
			if (!hasRead) {
				Cookie spaceCookie = new Cookie("spaceCookie", spaceCookieVal + "[" + spaceNo + "]");
				spaceCookie.setPath(request.getContextPath() + "/space");
				spaceCookie.setMaxAge(24 * 60 * 60);
				response.addCookie(spaceCookie);
				int result = spaceService.increaseSpaceReadCnt(spaceNo);
			}

			space = spaceService.selectOneSpace(spaceNo);
			List<Tag> tag = spaceService.selectListSpaceTag(spaceNo);
			List<SpaceList> spcList = spaceService.selectSameCategory(space);
			String cateName = spaceService.selectCateName(spaceNo);
			List<OptionList> optionList = spaceService.selectOptionList(spaceNo);

			model.addAttribute("spcList", spcList);
			model.addAttribute("cateName", cateName);

			model.addAttribute("qlist", qlist);
			model.addAttribute("qnaPaging", qnaPaging);
			model.addAttribute("qnaTotal", qnaTotal);

			model.addAttribute("space", space);
			model.addAttribute("tag", tag);
			model.addAttribute("loginMember", principal);

			model.addAttribute("review", review);
			model.addAttribute("reviewTotal", reviewTotal);
			model.addAttribute("reviewPaging", reviewPaging);
			model.addAttribute("star", star);
			model.addAttribute("pageBar", pageBar);

			model.addAttribute("optionList", optionList);
			model.addAttribute("bool", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "space/spaceDetail";
	}

	@RequestMapping("/spaceQnaDetail.do")
	public String spaceQnaDetail(Model model, @RequestParam("spaceNo") String spaceNo, Principal principal, Space space,
			@RequestParam(defaultValue = "1", value = "cPage") int cPage, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			int qnaTotal = spaceService.selectQuestionTotalContents(spaceNo);
			final int limit1 = 5 + space.getQnaPaging();
			int offset1 = (cPage - 1) * limit1;
			int qnaPaging = space.getQnaPaging();
			int width = space.getWidth();

			List<Qna> qlist = spaceService.selectQuestionList(spaceNo, limit1, offset1);

			final int limit = 5 + space.getReviewPaging();
			int offset = (cPage - 1) * limit;
			int reviewPaging = space.getReviewPaging();

			List<Review> review = spaceService.selectListReview(spaceNo, limit, offset);

			int reviewTotal = spaceService.selectReviewTotalContents(spaceNo);
			Star star = spaceService.selectStar(spaceNo);

			star.setSumStar(star.getStar1() + star.getStar2() + star.getStar3() + star.getStar4() + star.getStar5());

			String url = request.getRequestURI() + "?spaceNo=" + spaceNo;
			String pageBar = Utils.getPageBarHtml(cPage, limit, reviewTotal, url);

			Cookie[] cookies = request.getCookies();
			String spaceCookieVal = "";
			boolean hasRead = false;

			if (cookies != null) {
				for (Cookie c : cookies) {
					String name = c.getName();
					String value = c.getValue();

					if ("spaceCookie".equals(name)) {
						spaceCookieVal = value;

						if (value.contains("[" + spaceNo + "]"))
							hasRead = true;
					}
				}
			}
			if (!hasRead) {
				Cookie spaceCookie = new Cookie("spaceCookie", spaceCookieVal + "[" + spaceNo + "]");
				spaceCookie.setPath(request.getContextPath() + "/space");
				spaceCookie.setMaxAge(24 * 60 * 60);
				response.addCookie(spaceCookie);
				int result = spaceService.increaseSpaceReadCnt(spaceNo);
			}

			space = spaceService.selectOneSpace(spaceNo);

			List<Tag> tag = spaceService.selectListSpaceTag(spaceNo);
			List<SpaceList> spcList = spaceService.selectSameCategory(space);

			// 추천 공간 카테고리명
			String cateName = spaceService.selectCateName(spaceNo);
			List<OptionList> optionList = spaceService.selectOptionList(spaceNo);

			model.addAttribute("bool", 2);
			model.addAttribute("spcList", spcList);
			model.addAttribute("cateName", cateName);
			model.addAttribute("spaceAddr", space.getAddress());
			model.addAttribute("spaceName", space.getSpaceName());
			model.addAttribute("spaceCon", space.getContent());

			model.addAttribute("qlist", qlist);
			model.addAttribute("qnaPaging", qnaPaging);
			model.addAttribute("qnaTotal", qnaTotal);

			model.addAttribute("space", space);
			model.addAttribute("tag", tag);
			model.addAttribute("loginMember", principal);

			model.addAttribute("review", review);
			model.addAttribute("reviewTotal", reviewTotal);
			model.addAttribute("reviewPaging", reviewPaging);
			model.addAttribute("star", star);
			model.addAttribute("pageBar", pageBar);
			model.addAttribute("width", width);

			model.addAttribute("optionList", optionList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "space/spaceDetail";
	}

	@RequestMapping("/reserveSpace.do")
	public ModelAndView reserveSpace(ModelAndView mav, @RequestParam("spaceNo") String spaceNo, Principal principal) {
		Space space = spaceService.selectOneSpace(spaceNo);
		Member member = memberService.selectOneMember(principal.getName());

		List<OptionList> optionList = spaceService.selectOptionList(spaceNo);
		List<ReservationAvail> availList = reservationService.selectListAvail(spaceNo);
		List<Unselectable> unselectableList = reservationService.unselectableList(spaceNo);
		List<Coupon> couponList = memberService.selectCouponList(principal.getName());
		
		for (int i = 0; i < couponList.size(); i++) {
			if (couponList == null)
				break;

			if (couponList.get(i).getType().equals("come"))
				couponList.get(i).setType("회원가입 축하 쿠폰");
			else if (couponList.get(i).getType().equals("btd"))
				couponList.get(i).setType("생일 축하 쿠폰");
			else
				couponList.get(i).setType("기타");
		}

		mav.addObject("space", space);
		mav.addObject("member", member);
		mav.addObject("optionList", optionList);
		mav.addObject("availList", availList);
		mav.addObject("unselectableList", unselectableList);
		mav.addObject("couponList", couponList);

		mav.setViewName("space/reserveSpace");
		return mav;
	}

	@RequestMapping(value = "/heart.do", method = RequestMethod.POST)
	@ResponseBody
	public String insertWish(Wish wish, HttpServletResponse response) {
		int result = spaceService.insertWish(wish);
		String msg = (result > 0) ? "위시 추가 성공!" : "위시 추가 실패";
		return msg;
	}

	@RequestMapping(value = "/readLikeCnt.do", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> selectLikeCount(Wish wish, HttpServletResponse response) {
		int cnt = spaceService.selectLikeCnt(wish.getSpaceNo());
		String status = null;

		if (wish.getEmail() != null) {
			Wish selected = spaceService.selectOneWish(wish);
			if (selected != null) {
				status = "liked";
			} else {
				status = null;
			}
		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("cnt", cnt);

		return map;
	}

	@RequestMapping(value = "/cancelHeart.do", method = RequestMethod.POST)
	@ResponseBody
	public String deleteWishList(Wish wish, HttpServletResponse response) {
		int result = spaceService.deleteWish(wish);
		spaceService.minusLikeCnt(wish);
		String msg = (result > 0) ? "위시 삭제 성공!" : "위시 삭제 실패";
		return msg;
	}

	@RequestMapping(value = "/recentRev.do", method = RequestMethod.GET)
	@ResponseBody
	public List<Review> selectRecentReviewList() {
		List<Review> revList = spaceService.selectRecentReviewList();
		return revList;
	}

	@GetMapping("/checkIdDuplicate.do")
	public ModelAndView checkIdDuplicate1(ModelAndView mav, @RequestParam("businessNo") long businessNo) {

		Space space = spaceService.selectOneSpace(businessNo);
		boolean isUsable = space == null;

		mav.addObject("isUsable", isUsable);
		mav.setViewName("jsonView");

		return mav;
	}

	@GetMapping("/selectPopularSpaces.do")
	public ModelAndView selectPopularSpaces(ModelAndView mav) {

		List<SpaceList> popularList = spaceService.selectPopularSpaces();
		List<Attachment> imageList = new ArrayList<>();
		List<SpaceList> list = new ArrayList<>();

		for (SpaceList s : popularList) {
			Attachment att = spaceService.selectPopularImage(s.getSpaceNo());
			s.setAddress(s.getAddress());
			s.setRenamedFilename(att.getRname());
			list.add(s);
		}

		mav.addObject("list", popularList);
		mav.setViewName("jsonView");

		return mav;
	}

	@RequestMapping("/deleteSpace.do")
	public String deleteMember(@RequestParam("spaceNo") String spaceNo, Principal principal,
			RedirectAttributes redirectAttr) {
		int remainder = reservationService.confirmReservation(spaceNo);
		if (remainder != 0) {
			redirectAttr.addFlashAttribute("msg", "아직 예약이 있어 공간삭제가 불가능합니다.");
			return "redirect:/host/spaceInfo.do";
		}

		int result = spaceService.deleteSpace(spaceNo);

		if (result > 0) {
			int authResult = memberService.updateUser(principal.getName());
			redirectAttr.addFlashAttribute("msg", "성공적으로 공간을 삭제했습니다.");
		} else
			redirectAttr.addFlashAttribute("msg", "공간 삭제에 실패했습니다.");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<GrantedAuthority> updatedAuthorities = new ArrayList<>();
		updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(),
				updatedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(newAuth);

		return "redirect:/member/memberProfile.do";
	}
}
