package com.kh.spaceus.scheduler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled; 
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Component;

import com.kh.spaceus.host.model.service.HostService;
import com.kh.spaceus.member.model.service.MemberService;
import com.kh.spaceus.member.model.vo.Member;
import com.kh.spaceus.reservation.model.service.ReservationService;
import com.kh.spaceus.reservation.model.vo.Reservation;
import com.kh.spaceus.space.model.service.SpaceService;
import com.kh.spaceus.space.model.vo.Space;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j; 

@Controller
@Slf4j 
public class Scheduler { 
	
	@Autowired 
	private MemberService memberService;
	
	@Autowired
	private SpaceService spaceService;
	
	@Autowired
	private HostService hostService;
	
	@Autowired
	private ReservationService reservationService;
	
	@Scheduled(cron = "0 0 0 1 1 *")
	public void yearlyScheduler(){ 
		int result = hostService.insertYearlySettlement(); 
	}
	
	@Scheduled(cron = "0 0 0 1 * *")
	public void monthlyScheduler(){ 
		int result1 = memberService.insertAttend2Coupon();
		int result2 = memberService.insertAttend3Coupon();
		int result3 = memberService.deleteAttendance();
		int result4 = hostService.insertMonthlySettlement(); 
	}

	@Scheduled(cron ="0 0 0 * * *") 
	public void dailyScheduler(){ 
		int result1 = memberService.insertBtdCoupon();
		int result2 = memberService.deleteToday();
		int result3 = memberService.deleteCoupon();

		List<String> list = hostService.selectReservationSpaceNo();
		for(String str : list) {
			 int result4 = hostService.insertSettlement(str); 
		}

		int result5 = reservationService.updateComple();
	} 
	
}

