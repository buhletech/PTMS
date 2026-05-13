package za.ac.tut.ptms.controller.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Parent;
import za.ac.tut.ptms.model.Student;
import za.ac.tut.ptms.repository.AttendanceRepository;
import za.ac.tut.ptms.repository.MarkRepository;
import za.ac.tut.ptms.repository.StudentRepository;

import java.util.List;

@Controller
@RequestMapping("/parent")
public class ParentChildrenController {

    private final StudentRepository    studentRepo;
    private final MarkRepository       markRepo;
    private final AttendanceRepository attendanceRepo;

    public ParentChildrenController(StudentRepository sr,
                                    MarkRepository mr,
                                    AttendanceRepository ar) {
        studentRepo    = sr;
        markRepo       = mr;
        attendanceRepo = ar;
    }

    /** GET /parent/children — shows all children matched by surname */
    @GetMapping("/children")
    public String children(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String role = (String) session.getAttribute("userRole");
        if (!"parent".equals(role)) return "redirect:/dashboard";

        Parent parent = (Parent) session.getAttribute("loggedInUser");
        List<Student> children = studentRepo.getBySurname(parent.getSurname());

        model.addAttribute("children",   children);
        model.addAttribute("parentName", parent.getName() + " " + parent.getSurname());
        return "parentChildren";
    }

    /** GET /parent/marks?studentId=X — shows marks for one child */
    @GetMapping("/marks")
    public String marks(@RequestParam int studentId,
                        HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String role = (String) session.getAttribute("userRole");
        if (!"parent".equals(role)) return "redirect:/dashboard";

        Parent parent   = (Parent) session.getAttribute("loggedInUser");
        Student student = studentRepo.getById(studentId);

        // Security — only allow viewing if surnames match
        if (student == null ||
                !student.getSurname().equalsIgnoreCase(parent.getSurname())) {
            return "redirect:/dashboard";
        }

        model.addAttribute("student", student);
        model.addAttribute("marks",   markRepo.getByStudentId(studentId));
        return "parentMarks";
    }

    /** GET /parent/attendance?studentId=X — shows attendance for one child */
    @GetMapping("/attendance")
    public String attendance(@RequestParam int studentId,
                             HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String role = (String) session.getAttribute("userRole");
        if (!"parent".equals(role)) return "redirect:/dashboard";

        Parent parent   = (Parent) session.getAttribute("loggedInUser");
        Student student = studentRepo.getById(studentId);

        // Security — only allow viewing if surnames match
        if (student == null ||
                !student.getSurname().equalsIgnoreCase(parent.getSurname())) {
            return "redirect:/dashboard";
        }

        model.addAttribute("student",        student);
        model.addAttribute("attendanceList", attendanceRepo.getByStudentId(studentId));
        return "parentAttendance";
    }
}