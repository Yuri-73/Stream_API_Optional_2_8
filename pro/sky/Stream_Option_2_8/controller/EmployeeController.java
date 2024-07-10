package pro.sky.Stream_Option_2_8.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.Stream_Option_2_8.exceptions.EmployeeNotFoundException;
import pro.sky.Stream_Option_2_8.model.Employee;
import pro.sky.Stream_Option_2_8.exceptions.WrongNameException;
import pro.sky.Stream_Option_2_8.service.EmployeeService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/add")
    //localhost:8090/employee/add?firstName=Владимир&lastName=Гуляницкий&salary=25000&departmentId=2
    //localhost:8090/employee/add?firstName=Виктор&lastName=Судоплатов&salary=55500&departmentId=1
    //localhost:8090/employee/add?firstName=андрей&lastName=Полуянов&salary=44000&departmentId=3
    //localhost:8090/employee/add?firstName=Олег&lastName=Зудин&salary=24000&departmentId=4
    public boolean add(@RequestParam String firstName,
                    @RequestParam String lastName,
                    @RequestParam double salary,
                    @RequestParam int departmentId) { //Добавление в Мапу элементов. Ключ - имя+фамилия, значение - объект.
        check(firstName, lastName);  //Встроенный метод, выявляющий небуквенные символы в строках имени и фамилии. Если они есть, то возникнет WrongNameException()
        service.addEmployee(firstName, lastName, salary,departmentId);
        return true;
    }

    @GetMapping("/get")
    //localhost:8090/employee/get?firstName=Владимир&lastName=Гуляницкий
    public String get(@RequestParam String firstName, @RequestParam String lastName) {  //Нахождение элемента Мапы
        try {
            check(firstName, lastName);  //Встроенный метод, выявляющий небуквенные символы в строках имени и фамилии. Если они имеются, то будет исключение WrongNameException()
            return "" + service.findEmployee(firstName, lastName);
        } catch (WrongNameException e) {
            return e.getMessage();
        } catch (EmployeeNotFoundException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/remove")
    //localhost:8090/employee/remove?firstName=Владимир&lastName=Гуляницкий
    public boolean remove(@RequestParam String firstName, @RequestParam String lastName) {  //Удаление элемента Мапы по его имени и фамилии
        check(firstName, lastName);  //Встроенный метод, выявляющий небуквенные символы в строках имени и фамилии. Если они имеются, то будет исключение WrongNameException()
        return service.removeEmployee(firstName, lastName);
    }

    @GetMapping("/all")
    //localhost:8090/employee/all
    public Collection<Employee> getAll() {  //Вывод только значений элементов Мапы
        return service.getAll();
    }

    private void check(String... args) {
        for (String arg : args) {
            if (!StringUtils.isAlpha(arg)) {  //Если в параметре есть небуквенные символы
                throw new WrongNameException("В параметрах имени и(или) фамилии сотрудника есть небуквенные символы");
            }
        }
    }
}
