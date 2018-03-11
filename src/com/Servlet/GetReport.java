package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBTool.DBUtil;
import com.Util.VerificationInfo;
import com.alibaba.fastjson.JSON;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.Util.Report;

/**
 * Servlet implementation class GetReport
 */
@WebServlet("/GetReport")
public class GetReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetReport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		response.setContentType("text/html; charset=UTF-8");  
        response.setCharacterEncoding("UTF-8"); 
        PrintWriter out = response.getWriter();
        
        int type = Integer.parseInt(request.getParameter("type"));
        
        
        try {
        	Connection con = DBUtil.getConnection();
        	
        	if (type == 0) {
        		Statement s = con.createStatement();
        		ResultSet rs = s.executeQuery("select * from daigo.report");
        		List<Report> list = new ArrayList<>();
        		out.print("[");
        		while (rs.next()) {
        			list.add(report(rs));
        			
        		}
        		for (int i = 0; i < list.size() - 1; i++) {
        			out.print(JSON.toJSONString(list.get(i)));
        			out.print(",");
        		}
        		out.print(JSON.toJSONString(list.get(list.size() - 1)));
        		out.print("]");
        	} else {
        		int index = Integer.parseInt(request.getParameter("index"));
        		PreparedStatement ps = con.prepareStatement("delete from daigo.report "
        				+ "where `index`=?");
        		ps.setInt(1, index);
        		ps.executeUpdate();
        		ps.close();
        	}
        	
        	con.close();
        	
        } catch(Exception e) {
        	e.printStackTrace();
        } finally {
        	DBUtil.Close();
        	
        	out.flush();
        	out.close();
        }
	}
	
	private Report report(ResultSet rs) {
		Report r = new Report();
		try {
			r.setIndex(rs.getInt("index"));
			r.setTitle(rs.getString("title"));
			r.setContent(rs.getString("content"));
			r.setModel(rs.getString("model"));
			r.setDevice(rs.getString("device"));
			r.setSdk(rs.getString("sdk"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
	}

}
