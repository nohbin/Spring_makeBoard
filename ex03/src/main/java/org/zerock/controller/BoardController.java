package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {

	private BoardService service;

	// 번호로 가져오기
	@GetMapping({"/get" , "/modify"})
	public void get(@RequestParam("bno") Long bno, Model model, @ModelAttribute("cri") Criteria cri) {
		log.info("/get or /modify");
		model.addAttribute("board", service.get(bno));
	}

//	// 리스트 불러오기
//	@GetMapping("/list")
//	public void list(Model model) {
//		log.info("list");
//		model.addAttribute("list", service.getList());
//	}
	
//	@GetMapping("/list")
//	public void list(Criteria cri , Model model) {
//		log.info("list : " + cri);
//		model.addAttribute("list",service.getList(cri));
//	}
	
	@GetMapping("list")
	public void list(Criteria cri , Model model) {
		log.info("list" + cri);
		model.addAttribute("list", service.getList(cri));
//		model.addAttribute("pageMaker",new PageDTO(cri, 123));
		int total = service.getTotal(cri);
		
		log.info("total : " + total);
		model.addAttribute("pageMaker" , new PageDTO(cri, total));
	}
	

	@GetMapping("/register")
	public void register() {
	}

	// 등록하기
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		log.info("register : " + board);
		service.register(board);
		rttr.addFlashAttribute("result", board.getBno());
		return "redirect:/board/list";
	}

	//수정하기
	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr , @ModelAttribute("cri") Criteria cri) {
		log.info("modify" + board);
		
		if(service.modify(board)) {
			rttr.addFlashAttribute("result","success");
		}
		rttr.addAttribute("pageNum" , cri.getPageNum());
		rttr.addAttribute("amount" , cri.getAmount());
		rttr.addAttribute("type" , cri.getType());
		rttr.addAttribute("keyword" , cri.getKeyword());
		return "redirect:/board/list";
	}
	
	//삭제하기
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno , RedirectAttributes rttr , @ModelAttribute("cri") Criteria cri) {
		log.info("remove" + bno);
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result","success");
		}
		rttr.addAttribute("pageNum" , cri.getPageNum());
		rttr.addAttribute("amount" , cri.getAmount());
		rttr.addAttribute("type" , cri.getType());
		rttr.addAttribute("keyword" , cri.getKeyword());
		return "redirect:/board/list";
	}

}
