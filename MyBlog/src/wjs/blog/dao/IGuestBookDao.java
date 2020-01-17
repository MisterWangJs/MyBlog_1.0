package wjs.blog.dao;

import java.util.List;

import wjs.blog.domain.GuestBook;

public interface IGuestBookDao {

	public int add(GuestBook guestbook);

	List<GuestBook> findOnePage(int pageIndex, int pageSize);

	int findTotalCount();
 
}
