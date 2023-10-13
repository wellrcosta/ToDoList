package me.wreis.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.wreis.todolist.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var servletPath = request.getServletPath();
        if(!servletPath.startsWith("/tasks/")) {
            filterChain.doFilter(request, response);
            return;
        }

        var authorization = request.getHeader("Authorization");
        var authEncoded = authorization.substring("Basic ".length());
        byte[] decode = Base64.getDecoder().decode(authEncoded);
        var authDecoded = new String(decode);
        var authSplit = authDecoded.split(":");
        var username = authSplit[0];
        var password = authSplit[1];

        var user = this.userRepository.findByUsername(username);
        if(user == null) {
            response.sendError(401);
        }
        else {
            var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            if(!passwordVerify.verified) {
                response.sendError(401);
            }
            request.setAttribute("userId", user.getId());
            filterChain.doFilter(request, response);
        }
    }
}
