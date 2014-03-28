package com.example;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(asyncSupported = true, value = "/long2")
public class LongRunServlet extends HttpServlet {
	private ExecutorService executorService = Executors.newFixedThreadPool(10);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF8");
		AsyncContext ctx = req.startAsync();
		executorService.submit(new AsyncRequest(ctx));
	}

	@Override
	public void destroy() {
		executorService.shutdown();
	}
}
