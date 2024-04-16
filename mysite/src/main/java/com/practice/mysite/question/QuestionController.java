package com.practice.mysite.question;
import com.practice.mysite.answer.AnswerForm;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RequestMapping("/question")
@RequiredArgsConstructor //final이 붙은 생성자를 자동으로 만들어줌->questionRepository에 자동으로 모델 주입
@Controller
public class QuestionController {
    
    private final QuestionService questionService;
    
    @GetMapping("/list")
    public String list(Model model){//모델객체는 알아서 생김, 탬플릿에 전달해주는 것, django에서는 context
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList",questionList);
        return "question_list";
    }
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm){
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question",question);
        return "question_detail";
    }
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm){
        return "question_form";
    }
    
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, Errors errors){
        if (errors.hasErrors()){
            return "question_form";
        }
        this.questionService.createQuestion(questionForm.getSubject(),questionForm.getContent());
        return "redirect:/question/list";
    }
    
}
