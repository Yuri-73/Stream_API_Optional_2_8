package pro.sky.Stream_Option_2_8.service;

import org.springframework.stereotype.Service;
import pro.sky.Stream_Option_2_8.exceptions.EmployeeAlreadyAddException;
import pro.sky.Stream_Option_2_8.exceptions.EmployeeNotFoundException;
import pro.sky.Stream_Option_2_8.exceptions.EmployeeStorageIsFullException;
import pro.sky.Stream_Option_2_8.model.Employee;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.StringUtils.capitalize;

@Service
public class EmployeeService {

    private static int SIZE = 10;  //Недопустимое количество мест в Мапе
    private final Map<String, Employee> employees = new HashMap<>();

    public void addEmployee(String firstName, String lastName, double salary, int deportmentId) {
        if (employees.size() == SIZE) {  //Если количество элементов Мапы сравнялось с предельным, то выбрасываем EmployeeStorageIsFullException()
            throw new EmployeeStorageIsFullException();
        }
        var key = makeKey(firstName, lastName);  //Создаём ключ по встроенному методу как имя+фамилия в виде только строчных букв
        if (employees.containsKey(key)) { //Если уже имеется такой ключ в Мапе, то выбрасываем EmployeeAlreadyAddException().
            // Но почему оно не фиксируется в консоли Спринга, а только как BadRequest со статусом 400 в браузер?                      **спросить у наставника**
            throw new EmployeeAlreadyAddException();
        }
        employees.put(key, new Employee(capitalize(firstName),
                capitalize (lastName),
                salary,
                deportmentId )); //создаем нового сотрудника с обращением строчной начальной буквы в имени и фамилии в прописную (если они не были прописными изначально)
    }

    public Employee findEmployee(String firstName, String lastName) {
        var emp = employees.get(makeKey(firstName, lastName));  //Находим сотрудника в Мапе по его ключу
        if (emp == null) {
            throw new EmployeeNotFoundException("Такого сотрудника нет!");
        }
        return emp;
    }

    public boolean removeEmployee(String firstName, String lastName) {
        Employee removed = employees.remove(makeKey(firstName, lastName)); //Удаляем элемент в Мапе по его ключу
        if (removed == null) {  //Если такого элемента нет, то кидаем EmployeeNotFoundException()
            // Но почему оно не фиксируется в консоли Спринга, а только как BadRequest со статусом 400 в браузер?                      **спросить у наставника**
            throw new EmployeeNotFoundException();
        }
        return true;
    }

    public Collection<Employee> getAll() {
        return employees.values();  //Выводим всех сотрудников в Мапе
    }

    private String makeKey(String firstName, String lastName) {  //Встроенный метод для формирования ключа имя+фамилия, состоящего только из строчных букв
        return (firstName + " " + lastName).toLowerCase();
    }
}
