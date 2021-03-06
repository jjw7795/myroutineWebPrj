package com.myroutine.web.controller.api.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myroutine.web.service.IsNumberService;
import com.myroutine.web.service.TimeService;
import com.myroutine.web.service.admin.member.MemberService;

// == /api/member/edit?id=489&nickname=asdaaaaaaa
@WebServlet("/api/member/edit")
public class EditController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.setStatus(404);
		// 테스트용 넘기기
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// JSON SETTING ---------------------------------------------
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String idCheck = request.getParameter("id");

		// DISCONNECT CHECK -----------------------------------------
		if( idCheck == null || idCheck.equals("") ) {
			out.print("{\"result\": \"fail\"}");
			return;
		}

		
		// SETTING --------------------------------------------------
		int result = 0;
		int id = Integer.parseInt(idCheck);
		MemberService service = new MemberService();

		{ // 삭제하는거일경우, 이곳 제거하면 삭제기능 불가능.
			String value = request.getParameter("delete");
			if( value != null && value.equals("1")) {
				out.printf("{\"result\":%d}", service.delete(id));
				return;
			}
		}
		
		String[] actions = {"email","name","nickname","phone","birthday","rule","open_info","last_login"};
		List<String> keys = new ArrayList<String>();
		Map<String, String> datas = new HashMap<String, String>();
		
//		키들 넣기
		for(String action : actions)
			keys.add(action);

		// 람다식 
		// 받아온 데이터가 없다면 keys에서 제거함
		keys.removeIf( key -> {
			String temp = request.getParameter(key);
			if( temp == null || temp.equals("") ) {
				return true;
			}
			
			if( key.equals("last_login")) {
				TimeService.setNowDate();
				temp = TimeService.getDateNoSeparator();
			}
			datas.put(key, temp);
			return false;
		});

		List<String> results = new ArrayList<String>();

		// UPDATE --------------------------------------------
		result = service.updateAll(id, datas);

		// RESULT --------------------------------------------
		if( result == 0 ) {
			out.print("{\"result\": \"fail\"}");
			out.close();
			return;
		}
		
		if(datas.get("last_login") != null)
			results.add("\"last_login\":\"" + TimeService.getFullDate() + "\"");
		
		results.add("\"updateKeys\":[\"" + String.join("\",\"",keys) + "\"]");
		results.add("\"updateLines\":" + result);

		// JSON OUT -------------------------------------------------
		out.print("{");
		out.print("\"result\":\"sussess\",");
		out.print("\"datas\":{");
		out.print(String.join(",",results));
		out.print("}");
		out.print("}");
		
		out.close();
	}
}
