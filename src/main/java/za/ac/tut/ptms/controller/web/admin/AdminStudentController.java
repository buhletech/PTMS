package za.ac.tut.ptms.controller.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Student;
import za.ac.tut.ptms.repository.SchoolClassRepository;
import za.ac.tut.ptms.repository.StudentRepository;

@Controller
@RequestMapping("/admin/students")
public class AdminStudentController {

    private final StudentRepository     repo;
    private final SchoolClassRepository classRepo;

    public AdminStudentController(StudentRepository r, SchoolClassRepository cr) {
        this.repo = r; this.classRepo = cr;
    }

    @GetMapping
    public String handle(@RequestParam(required = false) String action,
                         @RequestParam(required = false) Integer id,
                         Model model) {
        if ("new".equals(action)) {
            model.addAttribute("classes", classRepo.getAll());
            return "admin/studentForm";
        }
        if ("edit".equals(action)) {
            model.addAttribute("student", repo.getById(id));
            model.addAttribute("classes", classRepo.getAll());
            return "admin/studentForm";
        }
        if ("delete".equals(action)) { repo.delete(id); return "redirect:/admin/students?msg=deleted"; }
        model.addAttribute("students", repo.getAll());
        return "admin/students";
    }

    @PostMapping
    public String submit(@RequestParam String action,
                         @RequestParam(required = false) Integer studentId,
                         @RequestParam String name,
                         @RequestParam String surname,
                         @RequestParam String grade,
                         @RequestParam int classId) {
        Student s = new Student();
        s.setName(name); s.setSurname(surname); s.setGrade(grade); s.setClassId(classId);
        if ("insert".equals(action)) {
            repo.insert(s);
            return "redirect:/admin/students?msg=inserted";
        }
        s.setStudentId(studentId);
        repo.update(s);
        return "redirect:/admin/students?msg=updated";
    }
}
