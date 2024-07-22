package pro.sky.Stream_Option_2_8.service;

import org.springframework.stereotype.Service;
import pro.sky.Stream_Option_2_8.model.Employee;
import pro.sky.Stream_Option_2_8.exceptions.EmployeeNotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public double maxSalary(int deptId) {  //Метод определения максимальной зарплаты по выбранному департаменту
        return employeeService.getAll()  //Список сотрудников из Мапы
                .stream()
                .filter(e -> e.getDepartment() == deptId)  //Фильтрация Мапы только по нужному департаменту
                .map(Employee::getSalary)  //Превращение Мапы в Список зарплат
                .max(Comparator.comparingDouble(o->o))//Определение максимальной зарплаты из Списка с типом данных Double (это уже Option пошёл)

                .orElseThrow(() -> new EmployeeNotFoundException("Нет значений"));  //Если Список пустой, то выбросывается исключение.
        // Эту ошибку через try/catch не обрабатывал (достаточно тренировки с методом minSalary())

        // НО почему не выводится эта ошибка EmployeeNotFoundException() с сообщением в консоль багов???????? Спросить у наставника.
    }
    public double minSalary(int deptId) {  //Метод определения минимальной зарплаты по выбранному департаменту
        return employeeService.getAll()
                .stream()
                .filter(e -> e.getDepartment() == deptId)
                .map(Employee::getSalary)
                .min(Comparator.comparingDouble(o->o))
                .orElseThrow(() -> new EmployeeNotFoundException("Нет сотрудников в департаменте!"));
    }
    public List<Employee> findAllByDept(int deptId) {
        return employeeService.getAll()  //Список сотрудников из Мапы
                .stream()
                .filter(e -> e.getDepartment() == deptId)  //Фильтрация Списка только по нужному департаменту
                .collect(Collectors.toList());  //Вывод Списка всех сотрудников департамента
    }

    public Map<Integer, List<Employee>> groupDeDept() {  //Формирование Мапы, где ключ - номер департамента, а значение - сотрудник этого департамента
        Map<Integer, List<Employee>> map = employeeService.getAll()  //Список сотрудников из Мапы
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));  //Группировка сотрудников по ключам-номерам департаментов последовательно от 1 до 4
        return map;
    }

    public double sum(int deptId) {
        return employeeService.getAll() //Список сотрудников из Мапы
                .stream()
                .filter(e -> e.getDepartment() == deptId)  //Фильтрация Списка только по нужному департаменту
                .mapToDouble(Employee::getSalary)  //Вывод значений зарплат с типом данных Double
                .sum();  //Суммирование зарплат
    }
}
