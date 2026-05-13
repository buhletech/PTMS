package za.ac.tut.ptms.controller.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Subject;
import za.ac.tut.ptms.repository.SchoolClassRepository;
import za.ac.tut.ptms.repository.SubjectRepository;
import za.ac.tut.ptms.repository.TeacherRepository;

@Controller
@RequestMapping("/admin/subjects")
public class AdminSubjectController {

    private final SubjectRepository     repo;
    private final TeacherRepository     teacherRepo;
    private final SchoolClassRepository classRepo;

    public AdminSubjectController(SubjectRepository r,
                                  TeacherRepository tr,
                                  SchoolClassRepository cr) {
        this.repo        = r;
        this.teacherRepo = tr;
        this.classRepo   = cr;
    }

    @GetMapping
    public String handle(@RequestParam(required = false) String action,
                         @RequestParam(required = false) Integer id,
                         Model model) {
        if ("new".equals(action)) {
            model.addAttribute("teachers", teacherRepo.getAll());
            model.addAttribute("classes",  classRepo.getAll());
            return "admin/subjectForm";
        }
        if ("edit".equals(action)) {
            model.addAttribute("subject",  repo.getById(id));
            model.addAttribute("teachers", teacherRepo.getAll());
            model.addAttribute("classes",  classRepo.getAll());
            return "admin/subjectForm";
        }
        if ("delete".equals(action)) { repo.delete(id); return "redirect:/admin/subjects?msg=deleted"; }
        model.addAttribute("subjects", repo.getAll());
        return "admin/subjects";
    }

    @PostMapping
    public String submit(@RequestParam String action,
                         @RequestParam(required = false) Integer subjectId,
                         @RequestParam String subjectName,
                         @RequestParam int teacherId,
                         @RequestParam int classId) {
        Subject s = new Subject();
        s.setSubjectName(subjectName);
        s.setTeacherId(teacherId);
        s.setClassId(classId);
        if ("insert".equals(action)) { repo.insert(s); return "redirect:/admin/subjects?msg=inserted"; }
        s.setSubjectId(subjectId);
        repo.update(s);
        return "redirect:/admin/subjects?msg=updated";
    }
}
