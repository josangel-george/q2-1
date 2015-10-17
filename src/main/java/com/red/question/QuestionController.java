package com.red.question;

import static com.red.MainClass.APTI;
import static com.red.MainClass.CIVIL;
import static com.red.MainClass.CS;
import static com.red.MainClass.EC;
import static com.red.MainClass.EEE;
import static com.red.MainClass.GK;
import static com.red.MainClass.MECH;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.red.admin.AdminService;

@Controller
@RequestMapping("/admin/question/")
public class QuestionController {

	@Autowired
	private QuestionRepository qRepo;
	
	@Autowired
	private AdminService adminService;
	
	List<String> categories = new ArrayList<>();
	
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
		
		if(categories.size() <= 0){
			
			categories.add(APTI);
			categories.add(GK);
			categories.add(EC);
			categories.add(EEE);
			categories.add(CIVIL);
			categories.add(MECH);
			categories.add(CS);
		}
		
		model.put("categories", categories);
		
		return "question/add";
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addQuestion(Question question, ModelMap model,
			@RequestParam("img-file") MultipartFile imageFile,
			HttpSession session){
		
		if(!adminService.verifyAdmin(session)){
			log.info("Not Logged in, redirecting to login");
			return "redirect:/admin/login";
		}

		// understand the question
		byte[] image;
		String modelImage = "";
		try {
			image = imageFile.getBytes();
			byte[] encoded = Base64.encodeBase64(image);
			modelImage = new String(encoded);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		question.setImage(modelImage);
		
		if(question.getOptions().get(4) == null || question.getOptions().get(4).isEmpty()){
			question.getOptions().remove(4);
		}
		
		log.info("Request to save question" + question);
		int questionId = question.getQuestionId();
		
		if(qRepo.findByQuestionId(questionId) != null){
			
			model.put("msg", "Question Number already exist.");
			model.put("categories", categories);
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
		System.out.println("Del Bkup: " + q);
		qRepo.delete(q);
		
		String msg = "Question deleted: " + questionId;
		redir.addFlashAttribute("msg", msg);
		log.info(msg);
		
		return "redirect:/admin/question/";
	}
}
