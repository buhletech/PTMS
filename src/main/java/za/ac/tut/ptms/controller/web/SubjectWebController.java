package za.ac.tut.ptms.controller.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Teacher;
import za.ac.tut.ptms.repository.SchoolClassRepository;
import za.ac.tut.ptms.repository.SubjectRepository;
import za.ac.tut.ptms.repository.TeacherRepository;

@Controller
@RequestMapping("/subjects")
public class SubjectWebController {

    private final SubjectRepository     repo;
    private final TeacherRepository     teacherRepo;
    private final SchoolClassRepository classRepo;

    public SubjectWebController(SubjectRepository r,
                                TeacherRepository tr,
                                SchoolClassRepository cr) {
        this.repo        = r;
        this.teacherRepo = tr;
        this.classRepo   = cr;
    }

    @GetMapping
    public String handle(@RequestParam(required = false) String action,
                         @RequestParam(required = false) Integer id,
                         HttpServletRequest request,
                         Model model) {

        HttpSession session = request.getSession(false);
        String role = (String) session.getAttribute("userRole");

        // Teachers see only their own subjects — read only
        if ("teacher".equals(role)) {
            Teacher t = (Teacher) session.getAttribute("loggedInUser");
            model.addAttribute("subjects", repo.getByTeacherId(t.getTeacherId()));
            model.addAttribute("readOnly", true);
            return "subjects";
        }

        // Parents should not access subjects
        if ("parent".equals(role)) {
            return "redirect:/dashboard";
        }

        return "redirect:/dashboard";
    }
}