package com.red.question;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.red.admin.AdminService;

import static com.red.MainClass.EEE;
import static com.red.MainClass.CS;
import static com.red.MainClass.EC;
import static com.red.MainClass.CIVIL;
import static com.red.MainClass.MECH;
import static com.red.MainClass.APTI;
import static com.red.MainClass.GK;

@Controller
@RequestMapping("/admin/question/")
public class QuestionController {

	@Autowired
	private QuestionRepository qRepo;
	
	@Autowired
	private AdminService adminService;
	
	private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
	
	@RequestMapping(value = "")
	public String home(ModelMap model, HttpSession session){
		
		if(!adminService.verifyAdmin(session)){
			log.info("Not Logged in, redirecting to login");
			return "redirect:/admin/login";
		}
		
		List<Question> questions = qRepo.findAll();
		
		model.put("questions", questions);
		return "question/home";
	}
	
	@RequestMapping(value = "add")
	public String addQuestionPage(Question question, 
			ModelMap model, HttpSession session){
		
		if(!adminService.verifyAdmin(session)){
			log.info("Not Logged in, redirecting to login");
			return "redirect:/admin/login";
		}
		
		List<String> category = new ArrayList<>();
		category.add(APTI);
		category.add(GK);
		category.add(EC);
		category.add(EEE);
		category.add(CIVIL);
		category.add(MECH);
		category.add(CS);
		
		model.put("category", category);
		
		return "question/add";
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addQuestion(Question question, ModelMap model,
			HttpSession session){
		
		if(!adminService.verifyAdmin(session)){
			log.info("Not Logged in, redirecting to login");
			return "redirect:/admin/login";
		}

		// understand the question
		log.info("Request to save question" + question);
		int questionId = question.getQuestionId();
		
		if(qRepo.findByQuestionId(questionId) != null){
			
			model.put("msg", "Question Number already exist.");
			model.put("question", question);
			return "question/add";
		};
		
		qRepo.save(question);
	
		model.put("msg", "Question " + questionId + " added to DB");
		return "redirect:/admin/question/";
	}
	
	@RequestMapping(value = "{questionId}")
	public String viewQuestion(@PathVariable int questionId, ModelMap model,
			HttpSession session){
		
		if(!adminService.verifyAdmin(session)){
			log.info("Not Logged in, redirecting to login");
			return "redirect:/admin/login";
		}
		
		Question q = qRepo.findByQuestionId(questionId);
		
		model.put("question", q);
		return "question/view";
	}
	
	@RequestMapping(value = "delete/{questionId}")
	public String deleteQuestion(@PathVariable int questionId, RedirectAttributes redir,
			HttpSession session){
		
		if(!adminService.verifyAdmin(session)){
			log.info("Not Logged in, redirecting to login");
			return "redirect:/admin/login";
		}
		
		Question q = qRepo.findByQuestionId(questionId);
		qRepo.delete(q);
		
		String msg = "Question deleted: " + questionId;
		redir.addFlashAttribute("msg", msg);
		log.info(msg);
		
		return "redirect:/admin/question/";
	}
}
