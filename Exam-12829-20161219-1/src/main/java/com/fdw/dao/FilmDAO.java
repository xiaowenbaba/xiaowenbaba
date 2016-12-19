package com.fdw.dao;


import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;

import com.fdw.entity.Film;
import com.fdw.entity.Language;
import com.fdw.util.JdbcTemplate;
import com.fdw.util.PageUtil;

public class FilmDAO {
	public  List<Film>  showAllFilm() throws Exception {
		String sql = "select f.film_id ,f.title,f.description,l.name from  `language` l , film f where f.language_id= l.language_id";
		System.out.println(sql);
	return 	JdbcTemplate.queryData(sql, null,Film.class);
	}
	public int deleteById(Film film) throws Exception {
		String sql ="delete from film where film_id = ?";
		return  JdbcTemplate.insertOrUpdateOrDelete2(sql, new Object[]{film.getFilm_id()});
	}
	public List<Film> selectById(Film film) throws Exception{
		String sql = "select * from film where film_id = ? ";
		return JdbcTemplate.queryData(sql, new Object[]{film.getFilm_id()},Film.class);
	}
	public int  insertFilm(Film film) throws SQLException{
		String sql="insert into film (title,description,language_id) VALUES(?,?,?)";
		return JdbcTemplate.insertOrUpdateOrDelete2(sql, new Object[]{film.getTitle(),film.getDescription(),film.getOriginalLanguageId()});
	}
	public   List<Language>   getLanguage() throws Exception{
		String sql ="select language_id,name from `language`";
		return  JdbcTemplate.queryData(sql, null, Language.class);
	}
	public int  modifyFilm(Film film) throws SQLException{
		String sql="update  film set title=?,description=?,language_id=? where film_id=?";
		return JdbcTemplate.insertOrUpdateOrDelete2(sql, new Object[]{film.getTitle(),film.getDescription(),film.getOriginalLanguageId(),film.getFilm_id()});
	}
}
