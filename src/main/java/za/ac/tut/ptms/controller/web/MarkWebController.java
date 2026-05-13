package za.ac.tut.ptms.controller.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Mark;
import za.ac.tut.ptms.model.Teacher;
import za.ac.tut.ptms.repository.*;

@Controller
@RequestMapping("/marks")
public class MarkWebController {

    private final MarkRepository       repo;
    private final StudentRepository    studentRepo;
    private final AssessmentRepository assessmentRepo;
    private final SchoolClassRepository classRepo;

    public MarkWebController(MarkRepository r, StudentRepository sr,
                             AssessmentRepository ar, SchoolClassRepository cr) {
        repo = r; studentRepo = sr; assessmentRepo = ar; classRepo = cr;
    }

    @GetMapping
    public String handle(@RequestParam(required = false) String action,
                         @RequestParam(required = false) Integer id,
                         @RequestParam(required = false) Integer classId,
                         @RequestParam(required = false) Integer studentId,
                         HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        String role = (String) session.getAttribute("userRole");

        if (!"teacher".equals(role)) return "redirect:/dashboard";

        Teacher teacher = (Teacher) session.getAttribute("loggedInUser");
        int teacherId = teacher.getTeacherId();

        // Always load teacher's classes for the dropdown
        model.addAttribute("teacherClasses", classRepo.getByTeacherId(teacherId));
        model.addAttribute("selectedClassId", classId);

        if ("new".equals(action)) {
            // Pre-select the student that was clicked
            if (studentId != null) {
                model.addAttribute("preStudent", studentRepo.getById(studentId));
            }
            model.addAttribute("assessments", assessmentRepo.getByTeacherId(teacherId));
            model.addAttribute("selectedClassId", classId);
            return "markForm";
        }

        if ("edit".equals(action)) {
            Mark m = repo.getById(id);
            model.addAttribute("mark", m);
            model.addAttribute("assessments", assessmentRepo.getByTeacherId(teacherId));
            return "markForm";
        }

        if ("delete".equals(action)) {
            repo.delete(id);
            return "redirect:/marks?msg=deleted" + (classId != null ? "&classId=" + classId : "");
        }

        // List view — if class selected, load students in that class
        if (classId != null) {
            model.addAttribute("students",    studentRepo.getByClassId(classId));
            model.addAttribute("recentMarks", repo.getByClassId(classId, teacherId));
        }

        return "marks";
    }

    @PostMapping
    public String submit(@RequestParam String action,
                         @RequestParam(required = false) Integer markId,
                         @RequestParam int studentId,
                         @RequestParam int assessmentId,
                         @RequestParam int markValue,
                         @RequestParam(required = false) Integer classId) {
        Mark m = new Mark();
        m.setStudentId(studentId);
        m.setAssessmentId(assessmentId);
        m.setMarkValue(markValue);
        if ("insert".equals(action)) {
            repo.insert(m);
            return "redirect:/marks?msg=inserted" + (classId != null ? "&classId=" + classId : "");
        }
        m.setMarkId(markId);
        repo.update(m);
        return "redirect:/marks?msg=updated" + (classId != null ? "&classId=" + classId : "");
    }
}