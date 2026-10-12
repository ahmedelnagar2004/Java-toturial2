package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff")
public class StaffController {

    private final StaffRepository staffRepository;

    public StaffController(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    // LIST: GET /staff
    @GetMapping
    public String list(Model model) {
        model.addAttribute("staffList", staffRepository.findAll());
        return "staff/list";
    }

    // CREATE FORM: GET /staff/new
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("staff", new Staff());
        return "staff/form";
    }

    // CREATE SAVE: POST /staff
    @PostMapping
    public String create(@ModelAttribute Staff staff) {
        staffRepository.save(staff);
        return "redirect:/staff";
    }

    // EDIT FORM: GET /staff/edit/{id}
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid staff id: " + id));
        model.addAttribute("staff", staff);
        return "staff/form";
    }

    // EDIT SAVE: POST /staff/update/{id}
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Staff staff) {
        staff.setId(id);
        staffRepository.save(staff);
        return "redirect:/staff";
    }

    // DELETE: GET /staff/delete/{id}
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        staffRepository.deleteById(id);
        return "redirect:/staff";
    }
}
