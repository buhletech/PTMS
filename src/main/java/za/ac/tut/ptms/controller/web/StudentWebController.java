package za.ac.tut.ptms.controller.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Student;
import za.ac.tut.ptms.model.Teacher;
import za.ac.tut.ptms.repository.SchoolClassRepository;
import za.ac.tut.ptms.repository.StudentRepository;

@Controller
@RequestMapping("/students")
public class StudentWebController {

    private final StudentRepository     repo;
    private final SchoolClassRepository classRepo;

    public StudentWebController(StudentRepository r, SchoolClassRepository cr) {
        this.repo = r;
        this.classRepo = cr;
    }

    @GetMapping
    public String handle(@RequestParam(required = false) String action,
                         @RequestParam(required = false) Integer id,
                         HttpServletRequest request,
                         Model model) {

        HttpSession session = request.getSession(false);
        String role = (String) session.getAttribute("userRole");

        // Teachers only see their own students — no CRUD
        if ("teacher".equals(role)) {
            Teacher teacher = (Teacher) session.getAttribute("loggedInUser");
            model.addAttribute("students", repo.getByTeacherId(teacher.getTeacherId()));
            model.addAttribute("readOnly", true);
            return "students";
        }

        // Parents should not access this page
        if ("parent".equals(role)) {
            return "redirect:/dashboard";
        }

        return "redirect:/dashboard";
    }
}