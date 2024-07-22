package pro.sky.stream_Option_2_8.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.stream_Option_2_8.exceptions.EmployeeNotFoundException;
import pro.sky.stream_Option_2_8.exceptions.WrongNameException;
import pro.sky.stream_Option_2_8.model.Employee;
import pro.sky.stream_Option_2_8.service.EmployeeService;

import java.util.Collection;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/add")  //Adding elements to Mapa. Key - first name + last name, value - object.
    public boolean add(@RequestParam String firstName,
                    @RequestParam String lastName,
                    @RequestParam double salary,
                    @RequestParam int departmentId) {
        check(firstName, lastName);  //Built-in method that detects non-alphabetic characters in first and last name strings. If they are present, there will be a WrongNameException()
        service.addEmployee(firstName, lastName, salary,departmentId);
        return true;
    }

    @GetMapping("/get")
    public String get(@RequestParam String firstName, @RequestParam String lastName) {  //Finding a Map element
        try {
            check(firstName, lastName);  //Built-in method that detects non-alphabetic characters in first and last name strings. If they are present, there will be a WrongNameException()
            return "" + service.findEmployee(firstName, lastName);
        } catch (WrongNameException e) {
            return e.getMessage();
        } catch (EmployeeNotFoundException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/remove")
    public boolean remove(@RequestParam String firstName, @RequestParam String lastName) {  //Удаление элемента Мапы по его имени и фамилии
        check(firstName, lastName);  //Built-in method that detects non-alphabetic characters in first and last name strings. If they are present, there will be a WrongNameException()
        return service.removeEmployee(firstName, lastName);
    }

    @GetMapping("/all")
    public Collection<Employee> getAll() {  //Вывод только значений элементов Мапы
        return service.getAll();
    }

    private void check(String... args) {
        for (String arg : args) {
            if (!StringUtils.isAlpha(arg)) {
                throw new WrongNameException("В параметрах имени и(или) фамилии сотрудника есть небуквенные символы");
            }
        }
    }
}
