package org.zerock.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {

	@Autowired
	private BoardService service;
	
	
	@Test
	public void testExist() {
		log.info(service);
		
		assertNotNull(service);
	}
	
//	@Test
	public void testRegister() {
		
		BoardVO board = new BoardVO();
		
		board.setTitle("새로이 작성하는 글");
		board.setContent("새로 작성하는 내용");
		board.setWriter("nohbin");
		
		service.register(board);
		
		log.info("생성된 게시물의 번호 : " + board.getBno());
	}
	
//	@Test
	public void testGetList() {
		
		service.getList().forEach(x->log.info(x));
	}
	
	@Test
	public void testGetList2() {
		service.getList(new Criteria(2, 10)).forEach(x->log.info(x));
	}
	
//	@Test
	public void testGet() {
		log.info(service.get(25L));
	}
	
}
