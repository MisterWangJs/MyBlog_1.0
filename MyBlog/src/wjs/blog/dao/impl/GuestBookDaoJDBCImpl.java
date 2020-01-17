package wjs.blog.dao.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import wjs.blog.dao.IGuestBookDao;
import wjs.blog.domain.GuestBook;
import wjs.blog.utils.JDBCUtil;

public class GuestBookDaoJDBCImpl implements IGuestBookDao{
	
	@Override//添加留言
	public int add(GuestBook guestbook) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int n = 0;
		
		try {
			conn = JDBCUtil.getConnection();
			String sql = "insert into guestbook (guestname,guestemail,guestcontent,sendtime)values(?,?,?,?)";
		
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, guestbook.getGuestname());
			ps.setString(2, guestbook.getGuestemail());
			ps.setString(3, guestbook.getGuestcontent());
			ps.setString(4, guestbook.getSendtime());
	
			n = ps.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return n;
	}
	
	
	//////////////查看留言列表
	public List<GuestBook> findAll() {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<GuestBook> guestbookList = null;
		
		try {
			conn = JDBCUtil.getConnection();
			String sql = "select * from guestbook ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			//遍历结果集	
			if(rs != null){
				boolean flag = true;
				while(rs.next()){
					if(flag){
						guestbookList = new ArrayList<GuestBook>();
						flag = false;
					}
						
					GuestBook guestbook = new GuestBook();
					guestbook.setGuestid(rs.getInt("guestid"));
					guestbook.setGuestname(rs.getString("guestname"));
					guestbook.setGuestemail(rs.getString("guestemail"));
					guestbook.setGuestcontent(rs.getString("guestcontent"));
					guestbook.setSendtime(rs.getString("sendtime"));
				
					
					
					guestbookList.add(guestbook);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return guestbookList;
	}

	@Override
	public int findTotalCount() {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;		
		int n = 0;
		
		try {
			conn = JDBCUtil.getConnection();
			String sql = "select count(*) as totalcount from guestbook";
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs != null){
				while(rs.next()){
					n = rs.getInt("totalcount");
				}
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n;
	}

	
	@Override
	public List<GuestBook> findOnePage(int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<GuestBook> guestbookList = null;
		//计算分页相关参数
		//变量startRecordNo 存储的是当前分页第一条记录的前一个位置 
		int startRecordNo = (pageIndex - 1) * pageSize;
			
		try {
			conn = JDBCUtil.getConnection();
			String sql = "select * from guestbook order by guestid desc limit ?,?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, startRecordNo);
			ps.setInt(2, pageSize);
			
			rs = ps.executeQuery();
			
			//遍历结果集
			if(rs != null){
				boolean flag = true;
				while(rs.next()){
					if(flag){
						guestbookList = new ArrayList<GuestBook>();
						flag = false;
					}
						
					GuestBook guestbook = new GuestBook();
					guestbook.setGuestid(rs.getInt("guestid"));
					guestbook.setGuestname(rs.getString("guestname"));
					guestbook.setGuestemail(rs.getString("guestemail"));
					guestbook.setGuestcontent(rs.getString("guestcontent"));
					guestbook.setSendtime(rs.getString("sendtime"));

					
					guestbookList.add(guestbook);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return guestbookList;

	}
	
	
}
