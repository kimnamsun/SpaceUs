package com.kh.spaceus.social;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class GoogleController {

	private final static String CLIENT_ID = "c90a57f3e423118bdb575be6e3083e18";
	private final static String REDIRECT_URI = "http://localhost:9090/spaceus/member/kakaoLogin.do";

	public String getAuthorizationUrl(HttpSession session) {

		String kakaoUrl = "https://kauth.kakao.com/oauth/authorize?" + "client_id=" + CLIENT_ID + "&redirect_uri="
				+ REDIRECT_URI + "&response_type=code";
		return kakaoUrl;
	}

	public String getAccessToken(String autorize_code) {

		final String RequestUrl = "https://kauth.kakao.com/oauth/token";
		final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
		postParams.add(new BasicNameValuePair("client_id", CLIENT_ID));
		postParams.add(new BasicNameValuePair("redirect_uri", REDIRECT_URI));
		postParams.add(new BasicNameValuePair("code", autorize_code));

		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(RequestUrl);
		JsonNode returnNode = null;

		try {

			post.setEntity(new UrlEncodedFormEntity(postParams));
			final HttpResponse response = client.execute(post);
			final int responseCode = response.getStatusLine().getStatusCode();

			ObjectMapper mapper = new ObjectMapper();
			returnNode = mapper.readTree(response.getEntity().getContent());

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} finally {
		}
		return returnNode.get("access_token").toString();
	}

	public JsonNode getKakaoUserInfo(String autorize_code) {

		final String RequestUrl = "https://kapi.kakao.com/v2/user/me";
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(RequestUrl);
		String accessToken = getAccessToken(autorize_code);

		post.addHeader("Authorization", "Bearer " + accessToken);

		JsonNode returnNode = null;

		try {

			final HttpResponse response = client.execute(post);
			final int responseCode = response.getStatusLine().getStatusCode();
			ObjectMapper mapper = new ObjectMapper();
			returnNode = mapper.readTree(response.getEntity().getContent());
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
		}
		return returnNode;
	}
}
