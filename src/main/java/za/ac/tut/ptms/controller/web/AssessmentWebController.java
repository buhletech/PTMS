package za.ac.tut.ptms.controller.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Assessment;
import za.ac.tut.ptms.model.Teacher;
import za.ac.tut.ptms.repository.AssessmentRepository;
import za.ac.tut.ptms.repository.SubjectRepository;

@Controller
@RequestMapping("/assessments")
public class AssessmentWebController {

    private final AssessmentRepository repo;
    private final SubjectRepository    subjectRepo;

    public AssessmentWebController(AssessmentRepository r, SubjectRepository sr) {
        repo = r; subjectRepo = sr;
    }

    @GetMapping
    public String handle(@RequestParam(required = false) String action,
                         @RequestParam(required = false) Integer id,
                         HttpServletRequest request, Model model) {

        HttpSession session  = request.getSession(false);
        String role          = (String) session.getAttribute("userRole");
        boolean isTeacher    = "teacher".equals(role);

        Teacher teacher = isTeacher
                ? (Teacher) session.getAttribute("loggedInUser") : null;
        int teacherId   = isTeacher ? teacher.getTeacherId() : -1;

        if ("new".equals(action)) {
            // Teacher only sees THEIR subjects — with class name to avoid confusion
            model.addAttribute("subjects", isTeacher
                    ? subjectRepo.getByTeacherId(teacherId)
                    : subjectRepo.getAll());
            return "assessmentForm";
        }

        if ("edit".equals(action)) {
            model.addAttribute("assessment", repo.getById(id));
            model.addAttribute("subjects", isTeacher
                    ? subjectRepo.getByTeacherId(teacherId)
                    : subjectRepo.getAll());
            return "assessmentForm";
        }

        if ("delete".equals(action)) {
            repo.delete(id);
            return "redirect:/assessments?msg=deleted";
        }

        // List — teacher only sees their own assessments
        model.addAttribute("assessments", isTeacher
                ? repo.getByTeacherId(teacherId)
                : repo.getAll());
        return "assessments";
    }

    @PostMapping
    public String submit(@RequestParam String action,
                         @RequestParam(required = false) Integer assessmentId,
                         @RequestParam String title,
                         @RequestParam String assessmentDate,
                         @RequestParam int subjectId) {
        Assessment a = new Assessment();
        a.setTitle(title);
        a.setAssessmentDate(assessmentDate);
        a.setSubjectId(subjectId);
        if ("insert".equals(action)) {
            repo.insert(a);
            return "redirect:/assessments?msg=inserted";
        }
        a.setAssessmentId(assessmentId);
        repo.update(a);
        return "redirect:/assessments?msg=updated";
    }
}