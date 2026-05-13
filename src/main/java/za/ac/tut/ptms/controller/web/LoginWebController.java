package za.ac.tut.ptms.controller.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Parent;
import za.ac.tut.ptms.model.Teacher;
import za.ac.tut.ptms.repository.ParentRepository;
import za.ac.tut.ptms.repository.TeacherRepository;

@Controller
public class LoginWebController {

    private final TeacherRepository teacherRepo;
    private final ParentRepository  parentRepo;

    public LoginWebController(TeacherRepository teacherRepo, ParentRepository parentRepo) {
        this.teacherRepo = teacherRepo;
        this.parentRepo  = parentRepo;
    }

    @GetMapping("/login")
    public String loginPage(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("loggedInUser") != null)
            return "redirect:/dashboard";
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String role,
                               @RequestParam String email,
                               @RequestParam String password,
                               HttpServletRequest req,
                               Model model) {
        if ("teacher".equals(role)) {
            Teacher t = teacherRepo.getByEmail(email);
            if (t != null && t.getPassword().equals(password)) {
                HttpSession s = req.getSession(true);
                s.setAttribute("loggedInUser", t);
                s.setAttribute("userRole",     "teacher");
                s.setAttribute("userName",     t.getName() + " " + t.getSurname());
                return "redirect:/dashboard";
            }
        } else if ("parent".equals(role)) {
            Parent p = parentRepo.getByEmail(email);
            if (p != null && p.getPassword().equals(password)) {
                HttpSession s = req.getSession(true);
                s.setAttribute("loggedInUser", p);
                s.setAttribute("userRole",     "parent");
                s.setAttribute("userName",     p.getName() + " " + p.getSurname());
                return "redirect:/dashboard";
            }
        }
        model.addAttribute("error", "Invalid email or password. Please try again.");
        model.addAttribute("role", role);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();
        return "redirect:/login";
    }
}
