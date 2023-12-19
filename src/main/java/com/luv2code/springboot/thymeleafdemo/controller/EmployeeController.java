package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService ;
//    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    // add mapping for "/list"
    @GetMapping("/list")
    public String listEmployee(Model theModel)
    {
        // get the employee from db
        List<Employee> theEmployees = employeeService.findAll();
        // add to the spring model
        theModel.addAttribute("employees",theEmployees);
        return"employees/list-employees";

    }
    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel)
    {
        // create mode attribute to bind form data
        Employee theEmployee = new Employee();
        theModel.addAttribute("employee",theEmployee);
        return "employees/employee-form";
    }
    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId")int theId , Model theModel)
    {
        // get the employee form the service
        Employee theEmployee = employeeService.findById(theId);
        //set emplyee in the model to prepoulate the form
        theModel.addAttribute("employee",theEmployee);
        // send over to our form

        return "employees/employee-form";
    }
    @PostMapping("/save")
    public String SaveEmployee(@ModelAttribute("employee")Employee theEmployee)
    {
        // save the employee
        employeeService.save(theEmployee);
        // use a redirect to prevent duplicate submission
        return "redirect:/employees/list";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId")int theId)
    {
        // delete the employee
        employeeService.deleteById(theId);
        // redirect to the /employees/list
        return "redirect:/employees/list";
    }
}
