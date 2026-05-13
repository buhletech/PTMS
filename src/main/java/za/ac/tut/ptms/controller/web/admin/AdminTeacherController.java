package za.ac.tut.ptms.controller.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Teacher;
import za.ac.tut.ptms.repository.StudentRepository;
import za.ac.tut.ptms.repository.TeacherRepository;
import za.ac.tut.ptms.repository.TeacherStudentRepository;

import java.util.List;

@Controller
@RequestMapping("/admin/teachers")
public class AdminTeacherController {

    private final TeacherRepository        repo;
    private final StudentRepository        studentRepo;
    private final TeacherStudentRepository tsRepo;

    public AdminTeacherController(TeacherRepository r,
                                  StudentRepository sr,
                                  TeacherStudentRepository tsr) {
        this.repo        = r;
        this.studentRepo = sr;
        this.tsRepo      = tsr;
    }

    @GetMapping
    public String handle(@RequestParam(required = false) String action,
                         @RequestParam(required = false) Integer id,
                         Model model) {
        if ("new".equals(action))    { return "admin/teacherForm"; }
        if ("edit".equals(action))   { model.addAttribute("teacher", repo.getById(id)); return "admin/teacherForm"; }
        if ("delete".equals(action)) { repo.delete(id); return "redirect:/admin/teachers?msg=deleted"; }
        model.addAttribute("teachers", repo.getAll());
        return "admin/teachers";
    }

    @PostMapping
    public String submit(@RequestParam String action,
                         @RequestParam(required = false) Integer teacherId,
                         @RequestParam String name,
                         @RequestParam String surname,
                         @RequestParam String email,
                         @RequestParam(required = false) String password) {
        Teacher t = new Teacher();
        t.setName(name); t.setSurname(surname); t.setEmail(email);
        if ("insert".equals(action)) {
            t.setPassword(password);
            repo.insert(t);
            return "redirect:/admin/teachers?msg=inserted";
        }
        t.setTeacherId(teacherId);
        if (password != null && !password.trim().isEmpty()) t.setPassword(password);
        repo.update(t);
        return "redirect:/admin/teachers?msg=updated";
    }

    /** GET /admin/teachers/{id}/students — show allocation page */
    @GetMapping("/{id}/students")
    public String allocatePage(@PathVariable int id, Model model) {
        model.addAttribute("teacher",          repo.getById(id));
        model.addAttribute("allStudents",      studentRepo.getAll());
        model.addAttribute("allocatedIds",     tsRepo.getStudentIdsByTeacherId(id));
        return "admin/teacherStudents";
    }

    /** POST /admin/teachers/{id}/students — save allocations */
    @PostMapping("/{id}/students")
    public String saveAllocations(@PathVariable int id,
                                  @RequestParam(required = false) List<Integer> studentIds) {
        tsRepo.removeAll(id);
        if (studentIds != null) {
            for (int studentId : studentIds) {
                tsRepo.allocate(id, studentId);
            }
        }
        return "redirect:/admin/teachers/" + id + "/students?msg=saved";
    }
}