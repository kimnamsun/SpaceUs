package com.kh.spaceus.space.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spaceus.space.model.service.SpaceService;
import com.kh.spaceus.space.model.vo.Category;
import com.kh.spaceus.space.model.vo.OptionList;
import com.kh.spaceus.space.model.vo.SpaceList;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/space")
public class SearchSpaceController {

	@Autowired
	private SpaceService spaceSerive;

	@RequestMapping(value = "/searchSpace.do", method = RequestMethod.GET)
	public ModelAndView searchSpace(ModelAndView mav, @RequestParam("keyword") String keyword,
			@RequestParam String sort) {
		List<String> spaceNoList = spaceSerive.selectSpaceNoList(keyword, sort);
		List<SpaceList> space = null;
		List<SpaceList> spaceList = new ArrayList<>();

		for (int i = 0; i < spaceNoList.size(); i++) {
			List<String> spaceNo = spaceNoList.subList(i, i + 1);
			String searchSpace = spaceNo.toString();
			searchSpace = searchSpace.replace("[", "");
			searchSpace = searchSpace.replace("]", "");

			space = spaceSerive.selectSearchSpaceList(searchSpace);
			spaceList.addAll(space);

		}
		mav.addObject("keyword", keyword);
		mav.addObject("spaceList", spaceList);
		mav.addObject("sort", sort);
		return mav;
	}

	@GetMapping("/searchDetailSpace.do")
	public ModelAndView searchDetailSpace(ModelAndView mav, @RequestParam String category,
			@RequestParam String location, @RequestParam String option, @RequestParam String sort) {

		String keyword = location + " " + category + " " + option;

		Map<String, String> map = new HashMap<>();
		map.put("category", category);
		map.put("location", location);
		map.put("option", option);

		List<String> spaceNo = spaceSerive.selectSearchDetailSpaceNo(map);
		List<SpaceList> space = null;
		List<SpaceList> spaceList = new ArrayList<>();

		for (int i = 0; i < spaceNo.size(); i++) {
			List<String> spaceNo1 = spaceNo.subList(i, i + 1);
			String spaceNo2 = spaceNo1.toString();
			spaceNo2 = spaceNo2.replace("[", "");
			spaceNo2 = spaceNo2.replace("]", "");

			space = spaceSerive.selectSearchSpaceList(spaceNo2);
			spaceList.addAll(space);

		}
		mav.setViewName("space/searchSpace");
		mav.addObject("category", category);
		mav.addObject("option", option);
		mav.addObject("location", location);
		mav.addObject("keyword", keyword);
		mav.addObject("spaceList", spaceList);
		mav.addObject("spaceNo", spaceNo);
		mav.addObject("sort", sort);
		return mav;
	}
}
