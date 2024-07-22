package pro.sky.stream_Option_2_8.service;

//import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.stream_Option_2_8.exceptions.EmployeeAlreadyAddException;
import pro.sky.stream_Option_2_8.exceptions.EmployeeNotFoundException;
import pro.sky.stream_Option_2_8.exceptions.EmployeeStorageIsFullException;
import pro.sky.stream_Option_2_8.model.Employee;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.capitalize;

@Service
public class EmployeeService {

    private static int SIZE = 2;  //Недопустимое количество мест в Мапе
    private final Map<String, Employee> employees = new HashMap<>();

    public void addEmployee(String firstName, String lastName, double salary, int deportmentId) {
        if (employees.size() == SIZE) {
            throw new EmployeeStorageIsFullException();
        }
        var key = makeKey(firstName, lastName);  //We create a key using the built-in method as first name + last name in the form of only lowercase letters
        if (employees.containsKey(key)) { //If there is already such a key in the Map, then we throw EmployeeAlreadyAddException().
            throw new EmployeeAlreadyAddException();
        }
        employees.put(key, new Employee(capitalize(firstName),
                capitalize (lastName),
                salary,
                deportmentId )); //we create a new employee by converting the lowercase initial letter in the first and last name to uppercase (if they were not uppercase initially)
    }

    public Employee findEmployee(String firstName, String lastName) {
        var emp = employees.get(makeKey(firstName, lastName));  //Find an employee in Map using his key
        if (emp == null) {
            throw new EmployeeNotFoundException("Такого сотрудника нет!");
        }
        return emp;
    }

    public boolean removeEmployee(String firstName, String lastName) {
        Employee removed = employees.remove(makeKey(firstName, lastName)); //We delete an element in Map by its key
        if (removed == null) {  //If there is no such element, then we throw EmployeeNotFoundException()
            throw new EmployeeNotFoundException();
        }
        return true;
    }

    public Collection<Employee> getAll() {
        return employees.values();  //We are transferring all employees to Map
    }

    private String makeKey(String firstName, String lastName) {  //Built-in method for generating a first name + last name key consisting of only lowercase letters
            return (firstName + " " + lastName).toLowerCase();
    }
}
