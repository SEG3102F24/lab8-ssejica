package seg3x02.employeeGql.resolvers

import org.springframework.stereotype.Controller
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.MutationMapping
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
import java.util.*


@Controller
class EmployeesResolver(
    private val employeesRepository: EmployeesRepository
) {

    @QueryMapping
    fun employees(): List<Employee> {
        return employeesRepository.findAll()
    }

    @QueryMapping
    fun employeeById(@Argument employeeId: String): Employee? {
        return employeesRepository.findById(employeeId).orElse(null)
    }

    @MutationMapping
    fun addEmployee(@Argument employeeInput: CreateEmployeeInput): Employee {
        val employee = Employee(
            name = employeeInput.name,
            dateOfBirth = employeeInput.dateOfBirth,
            city = employeeInput.city,
            salary = employeeInput.salary,
            gender = employeeInput.gender,
            email = employeeInput.email
        )
        employee.id = UUID.randomUUID().toString()
        val savedEmployee = employeesRepository.save(employee)
        println("Saved Employee ID: ${savedEmployee.id}")
        return savedEmployee
    }

    @MutationMapping
    fun updateEmployee(
        @Argument employeeId: String,
        @Argument employeeInput: CreateEmployeeInput
    ): Employee? {
        val employee = employeesRepository.findById(employeeId).orElse(null)
        employee?.apply {
            name = employeeInput.name
            dateOfBirth = employeeInput.dateOfBirth
            city = employeeInput.city
            salary = employeeInput.salary
            gender = employeeInput.gender ?: gender
            email = employeeInput.email ?: email
        }
        return employee?.let { employeesRepository.save(it) }
    }

    @MutationMapping
    fun deleteEmployee(@Argument employeeId: String): Boolean {
        return if (employeesRepository.existsById(employeeId)) {
            employeesRepository.deleteById(employeeId)
            true
        } else {
            false
        }
    }
}