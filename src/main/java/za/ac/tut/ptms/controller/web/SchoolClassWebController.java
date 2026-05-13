package za.ac.tut.ptms.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.SchoolClass;
import za.ac.tut.ptms.repository.SchoolClassRepository;

@Controller @RequestMapping("/classes")
public class SchoolClassWebController {
    private final SchoolClassRepository repo;
    public SchoolClassWebController(SchoolClassRepository r) { repo = r; }

    @GetMapping
    public String handle(@RequestParam(required=false) String action,
                         @RequestParam(required=false) Integer id, Model m) {
        if ("new".equals(action))  { return "classForm"; }
        if ("edit".equals(action)) { m.addAttribute("schoolClass", repo.getById(id)); return "classForm"; }
        if ("delete".equals(action)) { repo.delete(id); return "redirect:/classes?msg=deleted"; }
        m.addAttribute("classes", repo.getAll()); return "classes";
    }

    @PostMapping
    public String submit(@RequestParam String action,
                         @RequestParam(required=false) Integer classId,
                         @RequestParam String className, @RequestParam int gradeLevel) {
        SchoolClass c = new SchoolClass();
        c.setClassName(className); c.setGradeLevel(gradeLevel);
        if ("insert".equals(action)) { repo.insert(c); return "redirect:/classes?msg=inserted"; }
        c.setClassId(classId); repo.update(c); return "redirect:/classes?msg=updated";
    }
}
