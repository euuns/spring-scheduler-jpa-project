package com.example.scheduleserver.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

public class LoginFilter implements Filter {

    // 로그인 인증이 필요하지 않은 path
    private static final String[] WHITE_LIST = {"/", "/user/signup", "/user/login"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Servlet을 HttpServlet으로 down casting
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // 요청한 path를 반환
        String requestURI = httpServletRequest.getRequestURI();


        // 로그인 인증이 필요한 path인 경우
        if (!isWhiteList(requestURI)) {

            // session 확인
            HttpSession session = httpServletRequest.getSession(false);

            // session이 없는 경우 -> 로그인을 하지 않음
            if (session == null || session.getAttribute("login") == null) {
                // 상태코드 지정
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                // 클라이언트에게 전달할 메세지 작성 후 반환
                httpServletResponse.setContentType("application/json");
                httpServletResponse.getWriter().write("Please log in.");
                return;
            }
        }


        // 다음 필터 호출
        chain.doFilter(request, response);
    }


    private boolean isWhiteList(String requestURI) {
        // 요청 path가 앞에 선언한 WHITE_LIST에 일치하지 않으면 false 반환
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
