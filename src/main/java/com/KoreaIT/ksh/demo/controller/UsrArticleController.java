package com.KoreaIT.ksh.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.KoreaIT.ksh.demo.service.ArticleService;
import com.KoreaIT.ksh.demo.service.BoardService;
import com.KoreaIT.ksh.demo.service.CommentService;
import com.KoreaIT.ksh.demo.service.GenFileService;
import com.KoreaIT.ksh.demo.service.MemberService;
import com.KoreaIT.ksh.demo.service.ReactionPointService;
import com.KoreaIT.ksh.demo.util.Ut;
import com.KoreaIT.ksh.demo.vo.Article;
import com.KoreaIT.ksh.demo.vo.Board;
import com.KoreaIT.ksh.demo.vo.Comment;
import com.KoreaIT.ksh.demo.vo.Member;
import com.KoreaIT.ksh.demo.vo.ResultData;
import com.KoreaIT.ksh.demo.vo.Rq;

@Controller
public class UsrArticleController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private Rq rq;
	@Autowired
	private ReactionPointService reactionPointService;
	@Autowired
	private CommentService commentService;

	@Autowired
	private GenFileService genFileService;

	@RequestMapping("/usr/article/modify")

	public String modify(Model model, int id, int boardId, String title, String body) {

		Article article = articleService.getArticle(id);
		Board board = BoardService.getBoardById(boardId);
		if (article == null) {
			return rq.jsHistoryBackOnView(Ut.f("%d번 글은 존재하지 않습니다", id));
		}
		if (article.getMemberId() == rq.getLoginedMemberId()) {

			model.addAttribute("article", article);
			model.addAttribute("board", board);

			return "usr/article/modify";
		} else {
			return rq.jsHistoryBackOnView(Ut.f("권한이없습니다."));
		}

	}

	@RequestMapping("/usr/article/write")

	public String write(Model model, String title, String body) {

		return "usr/article/write";
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, int boardId, String title, String body) {

		Article article = articleService.getArticle(id);

		if (article == null) {
			return Ut.jsHistoryBack("F-D", id + "번 글은 존재하지 않습니다.");
		}
		articleService.modifyArticle(id, title, body);

		return Ut.jsReplace("S-1", "수정완료", Ut.f("../article/detail?id=%d&boardId=%d", id, boardId));

	}

	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(HttpSession httpSession, String title, String body,
			@RequestParam(defaultValue = "0") Integer boardId, MultipartRequest multipartRequest) {

		if (Ut.empty(title)) {
			return Ut.jsHistoryBack("F-N", "제목을 입력해주세요.");
		}
		if (Ut.empty(body)) {
			return Ut.jsHistoryBack("F-N", "내용을 입력해주세요");
		}
		if (boardId == 0) {
			return Ut.jsHistoryBack("F-N", "게시판을 선택 해주세요");
		}

		Board board = BoardService.getBoardById(boardId);
		ResultData<Integer> writeArticleRd = articleService.writeArticle(title, body, rq.getLoginedMemberId(), boardId);
		int id = (int) writeArticleRd.getData1();

		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		for (String fileInputName : fileMap.keySet()) {
			MultipartFile multipartFile = fileMap.get(fileInputName);

			if (multipartFile.isEmpty() == false) {
				genFileService.save(multipartFile, id);
			}
		}

		return Ut.jsReplace("S-1", "작성완료", Ut.f("../article/detail?id=%d&boardId=%d", id, boardId));

	}

	@RequestMapping("/usr/article/delete")
	@ResponseBody
	public String doDelete(Model model, int id, int boardId) {

		Article article = articleService.getArticle(id);
		if (article == null) {
			return Ut.jsHistoryBack("F-D", id + "번 글은 존재하지 않습니다.");
		}

		if (article.getMemberId() == rq.getLoginedMemberId()) {
			articleService.deleteArticle(id);
			model.addAttribute("article", article);
			return Ut.jsReplace("S-1", "삭제완료", Ut.f("../article/list?boardId=%d", boardId));
		} else {
			return Ut.jsHistoryBack("F-C", "권한이 없습니다.");
		}
	}

	@RequestMapping("/usr/article/list")
	public String showList(Model model, @RequestParam(defaultValue = "0") Integer boardId,
			@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "9") int itemsPerPage,
			String searchKeyword, Integer searchId) {

		Board board = BoardService.getBoardById(boardId);

		if (boardId != 0 && board == null) {
			return rq.jsHistoryBackOnView("그런 게시판은 없어");
		}
		int totalCount = articleService.getArticlesCount(boardId, searchId, searchKeyword);
		int totalPages = (int) Math.ceil((double) totalCount / itemsPerPage);
		int lastPageInGroup = (int) Math.min(((pageNum - 1) / 10 * 10 + 10), totalPages);
		int itemsInAPage = (pageNum - 1) * itemsPerPage;
		List<Article> articles = articleService.getArticles(boardId, itemsInAPage, itemsPerPage, searchKeyword,
				searchId);
		List<Article> commentsCount = articleService.getCommentsCount();

		model.addAttribute("commentsCount", commentsCount);
		model.addAttribute("board", board);
		model.addAttribute("articles", articles);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("itemsPerPage", itemsPerPage);
		model.addAttribute("lastPageInGroup", lastPageInGroup);

		if (boardId == 6 || boardId == 7 || boardId == 8) {

			return "usr/article/list2";
		}
		return "usr/article/list";
	}

	@RequestMapping("/usr/article/mylist")
	public String mylist(Model model, @RequestParam(defaultValue = "0") Integer boardId,
			@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int itemsPerPage,
			String searchKeyword, Integer searchId) {

		Board board = BoardService.getBoardById(boardId);

		if (boardId != 0 && board == null) {
			return rq.jsHistoryBackOnView("그런 게시판은 없어");
		}
		int totalCount = articleService.getArticlesCount(boardId, searchId, searchKeyword);
		int totalPages = (int) Math.ceil((double) totalCount / itemsPerPage);
		int lastPageInGroup = (int) Math.min(((pageNum - 1) / 10 * 10 + 10), totalPages);
		int itemsInAPage = (pageNum - 1) * itemsPerPage;
		List<Article> articles = articleService.getMyArticles(boardId, rq.getLoginedMemberId(), itemsInAPage,
				itemsPerPage, searchKeyword, searchId);
		List<Article> commentsCount = articleService.getCommentsCount();

		model.addAttribute("commentsCount", commentsCount);
		model.addAttribute("board", board);
		model.addAttribute("articles", articles);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("itemsPerPage", itemsPerPage);
		model.addAttribute("lastPageInGroup", lastPageInGroup);

		return "usr/mypage/mylist";
	}

	@RequestMapping("/usr/article/mylike")
	public String mylike(Model model, @RequestParam(defaultValue = "0") Integer boardId,
			@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int itemsPerPage,
			String searchKeyword, Integer searchId) {

		Board board = BoardService.getBoardById(boardId);

		if (boardId != 0 && board == null) {
			return rq.jsHistoryBackOnView("그런 게시판은 없어");
		}
		int totalCount = articleService.getArticlesCount(boardId, searchId, searchKeyword);
		int totalPages = (int) Math.ceil((double) totalCount / itemsPerPage);
		int lastPageInGroup = (int) Math.min(((pageNum - 1) / 10 * 10 + 10), totalPages);
		int itemsInAPage = (pageNum - 1) * itemsPerPage;
		List<Article> articles = articleService.getMylikes(boardId, rq.getLoginedMemberId(), itemsInAPage, itemsPerPage,
				searchKeyword, searchId);
		List<Article> commentsCount = articleService.getCommentsCount();

		model.addAttribute("commentsCount", commentsCount);
		model.addAttribute("board", board);
		model.addAttribute("articles", articles);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("itemsPerPage", itemsPerPage);
		model.addAttribute("lastPageInGroup", lastPageInGroup);

		return "usr/mypage/mylike";
	}

	@RequestMapping("/usr/article/detail")
	public String getArticle(Model model, int id, int boardId) {

		Article article = articleService.getArticle(id);
		int memberId = article.getMemberId();
		Member member = memberService.getMemberById(memberId);
		if (member.isDelStatus() == true) {
			return rq.jsHistoryBackOnView("탈퇴한 회원의 게시글입니다");

		}

		ResultData<Integer> actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				"id", id);

		model.addAttribute("article", article);
		model.addAttribute("loginedMemberId", rq.getLoginedMemberId());
		model.addAttribute("actorCanMakeReactionRd", actorCanMakeReactionRd);
		model.addAttribute("isAlreadyAddGoodRp", reactionPointService.isAlreadyAddGoodRp(id, "article"));
		model.addAttribute("isAlreadyAddBadRp", reactionPointService.isAlreadyAddBadRp(id, "article"));

		if (actorCanMakeReactionRd.isSuccess()) {
			model.addAttribute("actorCanMakeReaction", actorCanMakeReactionRd.isSuccess());
		}

		if (actorCanMakeReactionRd.getResultCode().equals("F-1")) {
			int sumReactionPointByMemberId = (int) actorCanMakeReactionRd.getData1();

			if (sumReactionPointByMemberId > 0) {
				model.addAttribute("actorCanCancelGoodReaction", true);
			} else if (sumReactionPointByMemberId < 0) {
				model.addAttribute("actorCanCancelBadReaction", true);
			}
		}
		List<Comment> getCommentById = commentService.getCommentById(rq.getLoginedMemberId(), id);
		List<Comment> comments = commentService.getComments(id);
		model.addAttribute("getCommentById", getCommentById);
		model.addAttribute("comments", comments);

		Board board = BoardService.getBoardById(boardId);

		model.addAttribute(board);

		return "usr/article/detail";
	}

	@RequestMapping("/usr/article/doIncreaseHitCountRd")
	@ResponseBody
	public ResultData doIncreaseHitCountRd(int id) {

		ResultData increaseHitCountRd = articleService.increaseHitCount(id);

		if (increaseHitCountRd.isFail()) {
			return increaseHitCountRd;
		}

		ResultData rd = ResultData.newData(increaseHitCountRd, "hitCount", articleService.getArticleHitCount(id));

		rd.setData2("id", id);

		return rd;
	}

	@RequestMapping("/usr/mylist/delete")
	@ResponseBody
	public String doDelete(Model model, @RequestParam(defaultValue = "") String ids,
			@RequestParam(defaultValue = "/usr/article/mylist") String replaceUri) {

		if (rq.isLogined()) {
			List<Integer> articleIds = new ArrayList<>();

			for (String idStr : ids.split(",")) {
				articleIds.add(Integer.parseInt(idStr));
			}

			articleService.deletemyArticles(articleIds);

			return Ut.jsReplace("해당 게시들이 삭제되었습니다.", replaceUri);
		} else {
			return Ut.jsHistoryBack("F-C", "권한이 없습니다.");
		}
	}

}