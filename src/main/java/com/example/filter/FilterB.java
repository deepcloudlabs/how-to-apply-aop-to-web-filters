package com.example.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.aop.Profiling;

@Component
@Order(2)
@Profiling
public class FilterB implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.err.println("Entering the filter \"FilterB\"");
		chain.doFilter(request, response);
		System.err.println("Leaving the filter \"FilterB\"");
	}

}
