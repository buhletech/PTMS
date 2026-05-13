package za.ac.tut.ptms.controller.web.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminLoginController {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    /** GET /admin  → redirect to /admin/login */
    @GetMapping
    public String root() { return "redirect:/admin/login"; }

    /** GET /admin/login */
    @GetMapping("/login")
    public String loginPage(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("adminUser") != null)
            return "redirect:/admin/dashboard";
        return "admin/login";
    }

    /** POST /admin/login */
    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username,
                               @RequestParam String password,
                               HttpServletRequest req,
                               Model model) {
        if (adminUsername.equals(username) && adminPassword.equals(password)) {
            HttpSession session = req.getSession(true);
            session.setAttribute("adminUser", username);
            return "redirect:/admin/dashboard";
        }
        model.addAttribute("error", "Invalid username or password.");
        return "admin/login";
    }

    /** GET /admin/logout */
    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();
        return "redirect:/admin/login";
    }
}
