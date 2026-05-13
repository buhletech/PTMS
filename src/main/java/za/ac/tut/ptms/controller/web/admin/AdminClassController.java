package za.ac.tut.ptms.controller.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.SchoolClass;
import za.ac.tut.ptms.repository.SchoolClassRepository;

@Controller
@RequestMapping("/admin/classes")
public class AdminClassController {

    private final SchoolClassRepository repo;
    public AdminClassController(SchoolClassRepository r) { this.repo = r; }

    @GetMapping
    public String handle(@RequestParam(required = false) String action,
                         @RequestParam(required = false) Integer id,
                         Model model) {
        if ("new".equals(action))    { return "admin/classForm"; }
        if ("edit".equals(action))   { model.addAttribute("schoolClass", repo.getById(id)); return "admin/classForm"; }
        if ("delete".equals(action)) { repo.delete(id); return "redirect:/admin/classes?msg=deleted"; }
        model.addAttribute("classes", repo.getAll());
        return "admin/classes";
    }

    @PostMapping
    public String submit(@RequestParam String action,
                         @RequestParam(required = false) Integer classId,
                         @RequestParam String className,
                         @RequestParam int gradeLevel) {
        SchoolClass c = new SchoolClass();
        c.setClassName(className);
        c.setGradeLevel(gradeLevel);
        if ("insert".equals(action)) { repo.insert(c); return "redirect:/admin/classes?msg=inserted"; }
        c.setClassId(classId);
        repo.update(c);
        return "redirect:/admin/classes?msg=updated";
    }
}