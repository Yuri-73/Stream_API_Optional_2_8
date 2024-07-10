package pro.sky.Stream_Option_2_8.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.Stream_Option_2_8.exceptions.EmployeeNotFoundException;
import pro.sky.Stream_Option_2_8.model.Employee;
import pro.sky.Stream_Option_2_8.service.DepartmentService;

import java.util.List;
import java.util.Map;

    @RestController
    @RequestMapping("/department")
    public class DepartmentController {
        private final DepartmentService service;

        public DepartmentController(DepartmentService service) {
            this.service = service;
        }
        @GetMapping("{deptId}/salary/sum")
        //localhost:8090/department/1/salary/sum
        //localhost:8090/department/2/salary/sum
        //localhost:8090/department/3/salary/sum
        //localhost:8090/department/4/salary/sum
        public double sumByDept(@PathVariable int deptId) {  //Сумма зарплат по выбранному департаменту
            return service.sum(deptId);
        }
        @GetMapping("{deptId}/salary/max")
        //localhost:8090/department/1/salary/max
        //localhost:8090/department/2/salary/max
        //localhost:8090/department/3/salary/max
        //localhost:8090/department/4/salary/max
        public double max(@PathVariable int deptId) {  //Максимальная зарплата по выбранному департаменту
            return service.maxSalary(deptId);
        }
        @GetMapping("{deptId}/salary/min")
        //localhost:8090/department/1/salary/min
        //localhost:8090/department/2/salary/min
        //localhost:8090/department/3/salary/min
        //localhost:8090/department/4/salary/min
        public String min(@PathVariable int deptId) {  //Минимальная зарплата по выбранному департаменту
            try {
                return "" + service.minSalary(deptId);
            } catch (EmployeeNotFoundException e) {
                return e.getMessage();  //Если сотрудников нет в Списке, то выдаётся сообщение: "Таких сотрудников нет!"
            }
        }
        @GetMapping("{deptId}/employees")
        //localhost:8090/department/1/employees
        //localhost:8090/department/2/employees
        //localhost:8090/department/3/employees
        //localhost:8090/department/4/employees
        public List<Employee> find(@PathVariable int deptId) {  //Вывод списка сотрудников выбранного департамента
            return service.findAllByDept(deptId);
        }
        @GetMapping("/employees")
        //localhost:8090/department/employees
        public Map<Integer, List<Employee>> group() {  //Вывод сотрудников всех департаментов друг за другом
            return service.groupDeDept();
        }
}
