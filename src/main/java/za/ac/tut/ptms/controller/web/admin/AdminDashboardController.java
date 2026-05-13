package za.ac.tut.ptms.controller.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import za.ac.tut.ptms.repository.ParentRepository;
import za.ac.tut.ptms.repository.StudentRepository;
import za.ac.tut.ptms.repository.TeacherRepository;

@Controller
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

    private final TeacherRepository teacherRepo;
    private final ParentRepository  parentRepo;
    private final StudentRepository studentRepo;

    public AdminDashboardController(TeacherRepository tr,
                                     ParentRepository  pr,
                                     StudentRepository sr) {
        this.teacherRepo = tr;
        this.parentRepo  = pr;
        this.studentRepo = sr;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("teacherCount", teacherRepo.getAll().size());
        model.addAttribute("parentCount",  parentRepo.getAll().size());
        model.addAttribute("studentCount", studentRepo.getAll().size());
        return "admin/dashboard";
    }
}
