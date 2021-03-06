package cn.javaee.servlet.cleaner;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.javaee.bean.Cleaner;
import cn.javaee.bean.Floor;
import cn.javaee.dao.daoimpl.CleanerDAOImpl;
import cn.javaee.dao.daoimpl.FloorDAOImpl;
import cn.javaee.servlet.BaseHttpServlet;

@WebServlet({"/getAllCleaner","/getCleaner","/setCleaner"})
public class CommonServlet extends BaseHttpServlet {
	@Override
	public void doMyGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		
		if (uri.endsWith("getAllCleaner")) {
			CleanerDAOImpl cleanerDAOImpl = new CleanerDAOImpl();
			List<Cleaner>cleaners = cleanerDAOImpl.getAll();
			Gson gson = new Gson();
			String json = gson.toJson(cleaners);
			System.out.println("getAllCleaner");

			PrintWriter out = new PrintWriter(response.getOutputStream());
			out.print(json);
			out.flush();
		}
		
		if (uri.endsWith("getCleaner")) {
			System.out.println("getCleaner");
			int id = Integer.valueOf(request.getParameter("id"));
			CleanerDAOImpl cleanerDAOImpl = new CleanerDAOImpl();
			Cleaner cleaner = cleanerDAOImpl.getById(id);
			
			Gson gson = new Gson();
			String json = gson.toJson(cleaner);

			PrintWriter out = new PrintWriter(response.getOutputStream());
			out.print(json);
			out.flush();
		}
	}
}
