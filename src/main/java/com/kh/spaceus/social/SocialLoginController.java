package com.kh.spaceus.social;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.kh.spaceus.member.model.service.MemberService;
import com.kh.spaceus.member.model.vo.Member;

import antlr.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SocialLoginController {

	private NaverLoginBO naverLoginBO;
	private String apiResult = null;

	@Autowired
	private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
		this.naverLoginBO = naverLoginBO;
	}

	@Autowired
	private KakaoController kakaoController;

	@Autowired
	private MemberService memberService;

	@RequestMapping(value = "/member/memberLoginForm.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(Model model, HttpSession session) {
		String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
		String kakaoUrl = kakaoController.getAuthorizationUrl(session);

		model.addAttribute("naver_url", naverAuthUrl);
		model.addAttribute("kakao_url", kakaoUrl);

		return "/member/memberLoginForm";
	}

	@RequestMapping(value = "/callback", method = { RequestMethod.GET, RequestMethod.POST })
	public String callback(RedirectAttributes redirectAttr, Model model, @RequestParam String code,
			@RequestParam String state, HttpSession session) throws IOException, ParseException {

		OAuth2AccessToken oauthToken;
		oauthToken = naverLoginBO.getAccessToken(session, code, state);

		apiResult = naverLoginBO.getUserProfile(oauthToken);

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(apiResult);
		JSONObject jsonObj = (JSONObject) obj;
		JSONObject response_obj = (JSONObject) jsonObj.get("response");

		String email = (String) response_obj.get("email");

		model.addAttribute("email", email);
		model.addAttribute("site", "네이버");
		model.addAttribute("closeFunction", "closeFunction");

		Member member = memberService.selectOneMember(email);

		if (member != null) {
			return "/member/memberLoginForm";
		} else {
			String returnPath = "/member/socialMemberEnrollForm";
			model.addAttribute("returnPath", "returnPath");
			model.addAttribute("code", code);
			model.addAttribute("state", state);

			return returnPath;
		}
	}

	@RequestMapping("/member/kakaoLogin.do")
	public String getKakaoSignIn(RedirectAttributes redirectAttr, Model model, @RequestParam("code") String code,
			HttpSession session) throws Exception {

		JsonNode userInfo = kakaoController.getKakaoUserInfo(code);
		JsonNode accessToken = userInfo.get("access_token");

		String email = userInfo.get("kakao_account").get("email").asText();
		String nickname = userInfo.get("properties").get("nickname").asText();

		model.addAttribute("email", email);
		model.addAttribute("site", "카카오");
		model.addAttribute("code", code);
		model.addAttribute("closeFunction", "closeFunction");

		Member member = memberService.selectOneMember(email);

		if (member != null) {
			return "/member/memberLoginForm";
		}

		return "/member/socialMemberEnrollForm";
	}

	@RequestMapping("/member/googleLogin.do")
	public String getGoogleSignIn(Model model, @RequestParam("idtoken") String idtoken,
			@RequestParam(required = false) String email, @RequestParam(required = false) String tokenEmail,
			HttpSession session) throws Exception {
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, JSON_FACTORY)
				.setAudience(Collections
						.singletonList("398489879454-c5aqb8i12qv1gku3dgtt31fd8iogm2hd.apps.googleusercontent.com"))
				.build();

		GoogleIdToken idToken = verifier.verify(idtoken);
		String userId = null;
		String name = null;

		if (idToken != null) {
			Payload payload = idToken.getPayload();
			userId = payload.getSubject();
			name = (String) payload.get("name");
		} else {

		Member member = memberService.selectOneMember(email);

		if (member != null) { 
			model.addAttribute("email", email);
			return "/member/memberLoginForm";
		}
		model.addAttribute("email", email);
		model.addAttribute("site", "구글");
		return "/member/socialMemberEnrollForm";
	}

	}
