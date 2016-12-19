package com.fdw.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fdw.dao.FilmDAO;
import com.fdw.entity.Film;
import com.fdw.entity.Language;
import com.fdw.util.PageUtil;

/**
 * Servlet implementation class FilmServlet
 */
@WebServlet("/FilmServlet")
public class FilmServlet extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String map  = request.getParameter("action");
		if (map.equals("showAllFilm")){
			this.showAllFilm(request, response);
		}else if (map.equals("deleteById")){
			this.deleteById(request, response);
		}else if (map.equals("selectById")){
			this.selectById(request, response);
		}else if (map.equals("insertByid")){
			this.insertByid(request, response);
		}else if(map.equals("getLanguage")){
			this.getLanguage(request, response);
		}else if(map.equals("modify")){
			this.modify(request, response);
		}
	}
	
	protected void showAllFilm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FilmDAO filmDAO = new FilmDAO();
		Film film = new Film();
		try {
			List<Film> list = filmDAO.showAllFilm();
			request.setAttribute("list", list);
			request.getRequestDispatcher("/jsp/Film/showAllFilm.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void deleteById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fString=request.getParameter("film_id");
		Film  film  = new Film();
		if(!fString.equals("")||fString!=null){
			Integer film_id=Integer.valueOf(fString);
			film.setFilm_id(film_id);
		}
		FilmDAO filmDAO = new FilmDAO();
		try {
		int result=filmDAO.deleteById(film);
		
		request.getRequestDispatcher("/FilmServlet?action=showAllFilm").forward(request, response);
		
		if(result>0){
			System.out.println("删除成功！");
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	protected void selectById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String film_id=request.getParameter("film_id");
		Integer fInteger = null;
		if(!film_id.equals("")||film_id!=null){
			fInteger=Integer.valueOf(film_id);
		}
		Film film = new Film();
		film.setFilm_id(fInteger);
		FilmDAO filmDAO  = new FilmDAO();
		try {
			List<Language> list1=filmDAO.getLanguage();
			request.setAttribute("list",list1);
			List<Film> list =filmDAO.selectById(film);
			for(int i= 0 ;i<list.size();i++){
			 Film film2  = list.get(i);
			 request.setAttribute("film", film2);
			}
			request.getRequestDispatcher("/jsp/Film/update_film.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
	protected void insertByid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String language = request.getParameter("language");
		Byte.valueOf(language);
		FilmDAO filmDAO = new FilmDAO();
		Film film = new Film();
		film.setOriginalLanguageId(Byte.valueOf(language));
		film.setDescription(description);
		film.setTitle(title);
		try {
			int result = filmDAO.insertFilm(film);
			if (result > 0) {
				System.out.println("添加成功！");
			}
			request.getRequestDispatcher("/FilmServlet?action=showAllFilm").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	protected void getLanguage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FilmDAO filmDAO = new FilmDAO();
		try {
			List<Language> list=filmDAO.getLanguage();
			request.setAttribute("list",list);
			System.out.println(list.toString());
			request.getRequestDispatcher("/jsp/Film/insert_film.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void modify(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String film_id=request.getParameter("film_id");
		Integer fInteger=null;
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String language = request.getParameter("language");
		Byte.valueOf(language);
		if(!"".equals(film_id)||film_id!=null){
			fInteger=Integer.valueOf(film_id);
		}
		FilmDAO filmDAO = new FilmDAO();
		Film film = new Film();
		film.setOriginalLanguageId(Byte.valueOf(language));
		film.setDescription(description);
		film.setTitle(title);
		film.setFilm_id(fInteger);
		try {
			int result=filmDAO.modifyFilm(film);
			if(result>0){
				System.out.println("更新成功！");
			}
			request.getRequestDispatcher("/FilmServlet?action=showAllFilm").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
