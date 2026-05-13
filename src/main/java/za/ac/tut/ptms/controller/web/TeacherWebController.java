package za.ac.tut.ptms.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Teacher;
import za.ac.tut.ptms.repository.TeacherRepository;

@Controller @RequestMapping("/teachers")
public class TeacherWebController {
    private final TeacherRepository repo;
    public TeacherWebController(TeacherRepository r) { repo = r; }

    @GetMapping
    public String handle(@RequestParam(required=false) String action,
                         @RequestParam(required=false) Integer id, Model m) {
        if ("new".equals(action))  { return "teacherForm"; }
        if ("edit".equals(action)) { m.addAttribute("teacher", repo.getById(id)); return "teacherForm"; }
        if ("delete".equals(action)) { repo.delete(id); return "redirect:/teachers?msg=deleted"; }
        m.addAttribute("teachers", repo.getAll()); return "teachers";
    }

    @PostMapping
    public String submit(@RequestParam String action,
                         @RequestParam(required=false) Integer teacherId,
                         @RequestParam String name, @RequestParam String surname,
                         @RequestParam String email,
                         @RequestParam(required=false) String password) {
        Teacher t = new Teacher();
        t.setName(name); t.setSurname(surname); t.setEmail(email);
        if ("insert".equals(action)) { t.setPassword(password); repo.insert(t); return "redirect:/teachers?msg=inserted"; }
        t.setTeacherId(teacherId);
        if (password != null && !password.trim().isEmpty()) t.setPassword(password);
        repo.update(t); return "redirect:/teachers?msg=updated";
    }
}
